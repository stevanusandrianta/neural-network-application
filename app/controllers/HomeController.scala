package controllers

import java.io._
import javax.inject._

import play.api._
import play.api.mvc._
import java.text.SimpleDateFormat
import java.util
import java.util.{Calendar, UUID}

import models.User
import User._
import models.Model
import Model._
import forms.LoginForm
import play.api.libs.json.{JsObject, Json}
import services._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.io.Source
import scala.util.Random
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json._
import play.modules.reactivemongo.json.collection._

import scala.util.parsing.json.JSONObject
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller
    with MongoController
    with ReactiveMongoComponents{

  def userCollection = reactiveMongoApi.database.map(_.collection[JSONCollection]("user"))
  def modelCollection = reactiveMongoApi.database.map(_.collection[JSONCollection]("model"))
  def modelCollection1 = db[JSONCollection]("model");
  def userCollection1 = db[JSONCollection]("user");

  def loginForm = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def addUserForm = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def index = Action {
    Ok(views.html.template.render(views.html.login.render(loginForm, null), null, ""))
  }

  def doLogin = Action(parse.form(loginForm)) { request =>
    val username = request.body.username
    val password = request.body.password
    if(username == "" || password == ""){
      Ok(views.html.template.render(views.html.login.render(loginForm, "Please fill all the form"), null, ""))
    }else{
      var successLogin = true
      def futureUser = userCollection.flatMap(_.find(Json.obj("username" -> username, "password" -> password)).one[User])
      futureUser.onComplete( u => u.get match{
        case Some(user) =>
          successLogin = true
          Session.user = user
        case None =>
          successLogin = false
      })

      Await.result(futureUser, 2000 millisecond)

      println(successLogin)
      if(successLogin == true){
        Redirect("/home")
      }else{
        Ok(views.html.template.render(views.html.login.render(loginForm, "Username atau password salah"), null, ""))
      }
    }
  }

  def home = Action {
    Ok(views.html.template.render(views.html.index.render(Session.user), Session.user, ""))
  }

  def classifyView(message: String) = Action {
    Ok(views.html.template.render(views.html.classifyView.render(message), Session.user, "classify"))
  }

  def readTextFile(filename: String): Option[List[List[Double]]] = {
    try {
      val fileStream = scala.io.Source.fromFile(filename)
      val array = fileStream.getLines.map { str =>
        str.split(",").map(_.toDouble).toList
      }.toList
      Some(array)
    } catch {
      case e: Exception => None
    }
  }

  def doClassify = Action(parse.multipartFormData) { request =>
    val file = new File("result/result.csv")
    file.delete()
    val file1 = new File("fileupload/file.csv")
    file1.delete()

    request.body.file("picture").map { picture =>
      import java.io.File

      val filename = picture.filename
      picture.ref.moveTo(new File("fileupload/file.csv"))

      val testRead = readTextFile("fileupload/file.csv")
      testRead match {
        case Some(array) =>

          var network : Network = null
          val futureModel = modelCollection.flatMap(_.find(Json.obj("isActive" -> "true")).one[Model])

          futureModel.onComplete{ m =>
            m.get match {

              case Some(model) =>
                println(s"model/${model.timestamp}.json")
                network = NeuralNetwork.loadNetwork(s"model/${model.modelName}.json")
                val classified = array.map{d => NeuralNetworkMain.classify(network, d)}.map(_.head).map(Math.round(_).toDouble)
                val pw = new PrintWriter(new File("result/result.csv"))
                array.zipWithIndex.foreach{ c =>
                  pw.print(s"${classified(c._2)}")
                  c._1.foreach{ r =>
                    pw.print(s",$r")
                  }
                  pw.print("\n")
                }
                pw.close()
              case None =>

                network = NeuralNetwork.loadNetwork("model/network.json")
                val classified = array.map{d => NeuralNetworkMain.classify(network, d)}.map(_.head).map(Math.round(_).toDouble)
                val pw = new PrintWriter(new File("result/result.csv"))
                array.zipWithIndex.foreach{ c =>
                  pw.print(s"${classified(c._2)}")
                  c._1.foreach{ r =>
                    pw.print(s",$r")
                  }
                  pw.print("\n")
                }
                pw.close()
            }
          }

          Await.result(futureModel, 5000 millisecond)

          Thread.sleep(2000)

          val resultCsv = scala.io.Source.fromFile("result/result.csv")
          val arrayResult = resultCsv.getLines.map{ str =>
            str.split(",").map(_.toDouble).toList
          }.toList

          val zippedArray = arrayResult.zip(Stream from 1)

          Ok(views.html.template.render(views.html.classifyResult.render(zippedArray), Session.user, "classify"))

        case None =>
          Redirect("/classify?message=file upload gagal, mohon periksa kembali file anda")
      }

    }.getOrElse {
      Redirect(routes.HomeController.index).flashing(
        "error" -> "Missing file")
    }
  }

  def donwloadCsv = Action {
    Ok.sendFile(new File("result/result.csv"))
  }

  def downloadData = Action {
    Ok.sendFile(new File("data/testingData.csv"))
  }

  def modelView(message: String) = Action {

    val modelListFuture = modelCollection1.find(Json.obj()).cursor[Model]().collect[List]()
    val modelList = Await.result(modelListFuture, 2000 milliseconds)

    Ok(views.html.template.render(views.html.modelView.render(modelList, message), Session.user, "model"))

  }

  def doLearnModel = Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>

      import java.io.File
      val filename = picture.filename
      val timestamp = Calendar.getInstance()
      picture.ref.moveTo(new File(s"uploadedTrainingData/${timestamp.getTimeInMillis}.csv"))

      val fileStream = scala.io.Source.fromFile(s"uploadedTrainingData/${timestamp.getTimeInMillis}.csv")
      val array = fileStream.getLines.map{ str =>
        str.split(",").map(_.toDouble).toList
      }.toList

      val max = (0 until array.head.size).map{
        i => array.map(_(i)).max
      }.toList

      val min = (0 until array.head.size).map{
        i => array.map(_(i)).min
      }.toList

      val normalizedArray = array.map(r =>
        r.zipWithIndex.map( el =>
          ((el._1 - min(el._2)) / (max(el._2) - min(el._2)))
        )
      )

      val netw = NeuralNetwork.initiateNetwork(normalizedArray, 0.1,
        normalizedArray.head.size-1, List(24), 1, WeightGenerator1, new Sigmoid)
      val trainedNetwork = NeuralNetwork.initiateTraining(netw, normalizedArray, 0.1, 0.9, 10, 0.001)

      NeuralNetwork.saveNetwork(trainedNetwork, s"model/${timestamp.getTimeInMillis}.json")

      val fileTestData = scala.io.Source.fromFile("data/testingData.csv")
      val testData = fileTestData.getLines.map{ str =>
        str.split(",").map(_.toDouble).toList
      }.toList

      val testDataNormalized = testData.map(r =>
        r.zipWithIndex.map( el =>
          ((el._1 - min(el._2)) / (max(el._2) - min(el._2)))
        )
      )

      val prediction = testDataNormalized.map { r =>
        Math.round(NeuralNetwork.feedForward(trainedNetwork, r).head).toDouble
      }.zipWithIndex.map{ d =>
        if(d._1 == testDataNormalized(d._2).last) true else false
      }

      val accuracy : Double = prediction.count(_ == true).toDouble / prediction.size.toDouble

      val timeformat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss")
      val modelCountFuture = modelCollection1.count()
      val modelCount = Await.result(modelCountFuture, 1000 millisecond)
      val id = s"model${modelCount+1}"

      val addModel = modelCollection.flatMap(_.insert(Json.obj(
        "_id" -> id,
        "modelName" -> timestamp.getTimeInMillis.toString,
        "timestamp" -> timeformat.format(timestamp.getTimeInMillis),
        "accuracy" -> accuracy,
        "isActive" -> "false"
      )))

      Await.result(addModel, 10000 millisecond)
      val message = s"model dengan id $id telah dibuat"

      Redirect(s"/model?message=$message")

    }.getOrElse {
      Redirect("/model?message=please upload file")
    }
  }

  def activateModel(modelId: String) = Action {

    val setActiveToFalse = modelCollection1.update(
      Json.obj(),
      Json.obj("$set" -> Json.obj("isActive" -> "false")),
      multi = true
    )
    Await.result(setActiveToFalse, 5000 milliseconds)

    val setActiveToTrue = modelCollection1.findAndUpdate(
      Json.obj("_id" -> modelId),
      Json.obj(
        "$set" -> Json.obj("isActive" -> "true")
      ))

    Await.result(setActiveToTrue, 5000 milliseconds)
    val message = s"model ${modelId} telah diaktivasi"

    Redirect(s"/model?message=$message")
  }

  def deleteModel(modelId: String) = Action {

    val deleteModel = modelCollection.map(_.remove(Json.obj(
      "_id" -> modelId
    )))

    Await.result(deleteModel, 5000 millisecond)
    val message = s"model $modelId berhasil dihapus"
    Redirect(s"/model?message=$message")
  }

  def userList(message: String) = Action {

    val userListFuture = userCollection1.find(Json.obj()).cursor[User]().collect[List]()
    val userList = Await.result(userListFuture, 2000 millisecond)

    Ok(views.html.template.render(views.html.user.render(userList, message), Session.user, "user"))

  }

  def addUser = Action(parse.form(addUserForm)){ request =>
    val username = request.body.username
    val password = request.body.password
    if(username != "" && password != ""){

      val userCountFuture = userCollection1.count()
      val userCount = Await.result(userCountFuture, 1000 millisecond)
      val id = s"user${userCount+1}"
      val insertUser = userCollection.flatMap(_.insert(Json.obj(
        "_id" -> id,
        "username" -> username,
        "password" -> password,
        "role" -> "user"
      )))
      Await.result(insertUser, 1000 millisecond)
      val message = s"user dengan id $id berhasil ditambahan"
      Redirect(s"/userList?message=$message")
    }else{
      Redirect("/userList?message=form harus diisi")
    }
  }

  def deleteUser(userId: String) = Action {
    val deletedUser = userCollection1.findAndRemove(Json.obj("_id" -> userId))
    Await.result(deletedUser, 1000 milliseconds)
    val message = s"user dengan id $userId telah dihapus"
    Redirect(s"/userList?message=$message")
  }

  def dataView(message: String) = Action{
    Ok(views.html.template.render(views.html.data.render(message), Session.user, "data"))
  }

  def updateData = Action(parse.multipartFormData) { request =>
    val file = new File("data/testingData.csv")
    file.delete()
    request.body.file("picture").map { picture =>

      import java.io.File
      val filename = picture.filename
      val timestamp = Calendar.getInstance()
      picture.ref.moveTo(new File(s"data/testingData.csv"))

      val modelListFuture = modelCollection1.find(Json.obj()).cursor[Model]().collect[List]()
      val modelList = Await.result(modelListFuture, 2000 milliseconds)

      modelList.foreach{ model =>
        val loadedNetwork = NeuralNetwork.loadNetwork(s"model/${model.modelName}.json")
        val fileTestData = scala.io.Source.fromFile("data/testingData.csv")
        val testData = fileTestData.getLines.map{ str =>
          str.split(",").map(_.toDouble).toList
        }.toList

        val prediction = testData.map { r =>
          Math.round(NeuralNetwork.feedForward(loadedNetwork, r).head).toDouble
        }.zipWithIndex.map{ d =>
          if(d._1 == testData(d._2).last) true else false
        }

        val accuracy : Double = prediction.count(_ == true).toDouble / prediction.size.toDouble

        val updateAccuracy = modelCollection1.findAndUpdate(
          Json.obj("_id" -> model.id),
          Json.obj("$set" -> Json.obj(
            "accuracy" -> accuracy
          ))
        )

        println(accuracy)

        Await.result(updateAccuracy, 5000 milliseconds)
      }

      Redirect("/data?message=data telah sukses diubah")

    }.getOrElse {
      Redirect("/data")
    }
  }

  def logout = Action {
    Session.user = null
    Redirect("/")
  }




}
