package services

import java.io.{File, FileWriter, PrintWriter, StringWriter}
import java.util
import java.util.Calendar

import scala.io.Source
import scala.util.Random
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

/**
  * Created by stevanusandrianta on 7/24/16.
  */
object testMain extends App{

  Random.setSeed(Calendar.getInstance().getTimeInMillis)

  val trainingData = converter.toListDouble("trainingData/trainingData.csv")
  val trainingPca90Data = converter.toListDouble("trainingData/trainingData_pca90.csv")
  val trainingPca95Data = converter.toListDouble("trainingData/trainingData_pca95.csv")

  val testData = converter.toListDouble("testData/testData.csv")
  val testPca90Data = converter.toListDouble("testData/testData_pca90.csv")
  val testPca95Data = converter.toListDouble("testData/testData_pca95.csv")

  //testing.initateTesting

  //training.initiateTraining_r
  testing.initateTesting_r

  /*
  val pca90max = (0 until trainingPca90Data.head.size).map{
    i => trainingPca90Data.map(_(i)).max
  }.toList

  val pca90min = (0 until trainingPca90Data.head.size).map{
    i => trainingPca90Data.map(_(i)).min
  }.toList

  val pca95max = (0 until trainingPca95Data.head.size).map{
    i => trainingPca95Data.map(_(i)).max
  }.toList

  val pca95min = (0 until trainingPca95Data.head.size).map{
    i => trainingPca95Data.map(_(i)).min
  }.toList

  val normalizedArray = testPca95Data.map(r =>
    r.zipWithIndex.map( el =>
      ((el._1 - pca95min(el._2)) / (pca95max(el._2) - pca95min(el._2)))
    )
  )

  normalizedArray.foreach{
    a => a.foreach {
      b => print(s"$b,")
    }
      println()
  }*/

}

object converter{

  def toListDouble(filename: String) : List[List[Double]] = {
    Source.fromFile(filename).getLines.map{ str =>
      str.split(",").map(_.toDouble).toList
    }.toList
  }

}

object training{

  val weightGenerator = WeightGenerator1
  val activationFunction = new Sigmoid

  def initiateTraining = {

    val sampleData = Source.fromFile("trainingData/trainingData.csv")
    val sampleDataArray = sampleData.getLines.map{ str =>
      str.split(",").map(_.toDouble).toList
    }.toList

    val pca90Data = Source.fromFile("trainingData/trainingData_pca90.csv")
    val pca90DataArray = pca90Data.getLines.map{ str =>
      str.split(",").map(_.toDouble).toList
    }.toList

    val pca95Data = Source.fromFile("trainingData/trainingData_pca95.csv")
    val pca95DataArray = pca95Data.getLines.map{ str =>
      str.split(",").map(_.toDouble).toList
    }.toList

    training.runTraining(sampleDataArray, List(25), "trainingResult/sampleData25h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(26), "trainingResult/sampleData26h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(27), "trainingResult/sampleData27h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(28), "trainingResult/sampleData28h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(29), "trainingResult/sampleData29h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(30), "trainingResult/sampleData30h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(31), "trainingResult/sampleData31h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(32), "trainingResult/sampleData32h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(33), "trainingResult/sampleData33h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(34), "trainingResult/sampleData34h", 1000, 0.0001)

    training.runTraining(sampleDataArray, List(25), "trainingResult/sampleData25h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(26), "trainingResult/sampleData26h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(27), "trainingResult/sampleData27h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(28), "trainingResult/sampleData28h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(29), "trainingResult/sampleData29h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(30), "trainingResult/sampleData30h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(31), "trainingResult/sampleData31h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(32), "trainingResult/sampleData32h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(33), "trainingResult/sampleData33h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(34), "trainingResult/sampleData34h_2", 1000, 0.0001)

    training.runTraining(sampleDataArray, List(25), "trainingResult/sampleData25h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(26), "trainingResult/sampleData26h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(27), "trainingResult/sampleData27h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(28), "trainingResult/sampleData28h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(29), "trainingResult/sampleData29h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(30), "trainingResult/sampleData30h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(31), "trainingResult/sampleData31h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(32), "trainingResult/sampleData32h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(33), "trainingResult/sampleData33h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(34), "trainingResult/sampleData34h_3", 1000, 0.0001)

    training.runTraining(pca90DataArray, List(7), "trainingResult/sampleDataPca907h", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(8), "trainingResult/sampleDataPca908h", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(9), "trainingResult/sampleDataPca909h", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(10), "trainingResult/sampleDataPca9010h", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(11), "trainingResult/sampleDataPca9011h", 1000, 0.0001)

    training.runTraining(pca90DataArray, List(7), "trainingResult/sampleDataPca907h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(8), "trainingResult/sampleDataPca908h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(9), "trainingResult/sampleDataPca909h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(10), "trainingResult/sampleDataPca9010h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(11), "trainingResult/sampleDataPca9011h_2", 1000, 0.0001)

    training.runTraining(pca90DataArray, List(7), "trainingResult/sampleDataPca907h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(8), "trainingResult/sampleDataPca908h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(9), "trainingResult/sampleDataPca909h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(10), "trainingResult/sampleDataPca9010h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(11), "trainingResult/sampleDataPca9011h_3", 1000, 0.0001)

    training.runTraining(pca95DataArray, List(7), "trainingResult/sampleDataPca957h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(8), "trainingResult/sampleDataPca958h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(9), "trainingResult/sampleDataPca959h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(10), "trainingResult/sampleDataPca9510h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(11), "trainingResult/sampleDataPca9511h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(12), "trainingResult/sampleDataPca9512h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(13), "trainingResult/sampleDataPca9513h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(14), "trainingResult/sampleDataPca9514h", 1000, 0.0001)

    training.runTraining(pca95DataArray, List(7), "trainingResult/sampleDataPca957h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(8), "trainingResult/sampleDataPca958h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(9), "trainingResult/sampleDataPca959h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(10), "trainingResult/sampleDataPca9510h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(11), "trainingResult/sampleDataPca9511h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(12), "trainingResult/sampleDataPca9512h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(13), "trainingResult/sampleDataPca9513h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(14), "trainingResult/sampleDataPca9514h_2", 1000, 0.0001)

    training.runTraining(pca95DataArray, List(7), "trainingResult/sampleDataPca957h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(8), "trainingResult/sampleDataPca958h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(9), "trainingResult/sampleDataPca959h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(10), "trainingResult/sampleDataPca9510h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(11), "trainingResult/sampleDataPca9511h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(12), "trainingResult/sampleDataPca9512h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(13), "trainingResult/sampleDataPca9513h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(14), "trainingResult/sampleDataPca9514h_3", 1000, 0.0001)

  }

  def initiateTraining_r = {

    val sampleData = Source.fromFile("trainingData/trainingData_n.csv")
    val sampleDataArray = sampleData.getLines.map{ str =>
      str.split(",").map(_.toDouble).toList
    }.toList

    val pca90Data = Source.fromFile("trainingData/trainingData_pca90_n.csv")
    val pca90DataArray = pca90Data.getLines.map{ str =>
      str.split(",").map(_.toDouble).toList
    }.toList

    val pca95Data = Source.fromFile("trainingData/trainingData_pca95_n.csv")
    val pca95DataArray = pca95Data.getLines.map{ str =>
      str.split(",").map(_.toDouble).toList
    }.toList

    training.runTraining(sampleDataArray, List(25), "trainingResult_2/sampleData25h", 1000, 0.0001)
    /*training.runTraining(sampleDataArray, List(26), "trainingResult_2/sampleData26h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(27), "trainingResult_2/sampleData27h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(28), "trainingResult_2/sampleData28h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(29), "trainingResult_2/sampleData29h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(30), "trainingResult_2/sampleData30h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(31), "trainingResult_2/sampleData31h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(32), "trainingResult_2/sampleData32h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(33), "trainingResult_2/sampleData33h", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(34), "trainingResult_2/sampleData34h", 1000, 0.0001)*/

    //training.runTraining(sampleDataArray, List(25), "trainingResult_2/sampleData25h_2", 1000, 0.0001)
    /*training.runTraining(sampleDataArray, List(26), "trainingResult_2/sampleData26h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(27), "trainingResult_2/sampleData27h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(28), "trainingResult_2/sampleData28h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(29), "trainingResult_2/sampleData29h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(30), "trainingResult_2/sampleData30h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(31), "trainingResult_2/sampleData31h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(32), "trainingResult_2/sampleData32h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(33), "trainingResult_2/sampleData33h_2", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(34), "trainingResult_2/sampleData34h_2", 1000, 0.0001)*/

    //training.runTraining(sampleDataArray, List(25), "trainingResult_2/sampleData25h_3", 1000, 0.0001)
    /*training.runTraining(sampleDataArray, List(26), "trainingResult_2/sampleData26h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(27), "trainingResult_2/sampleData27h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(28), "trainingResult_2/sampleData28h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(29), "trainingResult_2/sampleData29h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(30), "trainingResult_2/sampleData30h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(31), "trainingResult_2/sampleData31h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(32), "trainingResult_2/sampleData32h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(33), "trainingResult_2/sampleData33h_3", 1000, 0.0001)
    training.runTraining(sampleDataArray, List(34), "trainingResult_2/sampleData34h_3", 1000, 0.0001)*/

    training.runTraining(pca90DataArray, List(7), "trainingResult_2/sampleDataPca907h", 1000, 0.0001)
    /*
    training.runTraining(pca90DataArray, List(8), "trainingResult_2/sampleDataPca908h", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(9), "trainingResult_2/sampleDataPca909h", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(10), "trainingResult_2/sampleDataPca9010h", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(11), "trainingResult_2/sampleDataPca9011h", 1000, 0.0001)

    training.runTraining(pca90DataArray, List(7), "trainingResult_2/sampleDataPca907h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(8), "trainingResult_2/sampleDataPca908h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(9), "trainingResult_2/sampleDataPca909h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(10), "trainingResult_2/sampleDataPca9010h_2", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(11), "trainingResult_2/sampleDataPca9011h_2", 1000, 0.0001)

    training.runTraining(pca90DataArray, List(7), "trainingResult_2/sampleDataPca907h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(8), "trainingResult_2/sampleDataPca908h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(9), "trainingResult_2/sampleDataPca909h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(10), "trainingResult_2/sampleDataPca9010h_3", 1000, 0.0001)
    training.runTraining(pca90DataArray, List(11), "trainingResult_2/sampleDataPca9011h_3", 1000, 0.0001)
    */

    training.runTraining(pca95DataArray, List(7), "trainingResult_2/sampleDataPca957h", 1000, 0.0001)
    /*
    training.runTraining(pca95DataArray, List(8), "trainingResult_2/sampleDataPca958h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(9), "trainingResult_2/sampleDataPca959h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(10), "trainingResult_2/sampleDataPca9510h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(11), "trainingResult_2/sampleDataPca9511h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(12), "trainingResult_2/sampleDataPca9512h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(13), "trainingResult_2/sampleDataPca9513h", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(14), "trainingResult_2/sampleDataPca9514h", 1000, 0.0001)

    training.runTraining(pca95DataArray, List(7), "trainingResult_2/sampleDataPca957h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(8), "trainingResult_2/sampleDataPca958h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(9), "trainingResult_2/sampleDataPca959h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(10), "trainingResult_2/sampleDataPca9510h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(11), "trainingResult_2/sampleDataPca9511h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(12), "trainingResult_2/sampleDataPca9512h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(13), "trainingResult_2/sampleDataPca9513h_2", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(14), "trainingResult_2/sampleDataPca9514h_2", 1000, 0.0001)

    training.runTraining(pca95DataArray, List(7), "trainingResult_2/sampleDataPca957h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(8), "trainingResult_2/sampleDataPca958h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(9), "trainingResult_2/sampleDataPca959h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(10), "trainingResult_2/sampleDataPca9510h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(11), "trainingResult_2/sampleDataPca9511h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(12), "trainingResult_2/sampleDataPca9512h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(13), "trainingResult_2/sampleDataPca9513h_3", 1000, 0.0001)
    training.runTraining(pca95DataArray, List(14), "trainingResult_2/sampleDataPca9514h_3", 1000, 0.0001)
    */
  }


  def runTraining(data: List[List[Double]], hiddenNode: List[Int], fileName: String, maxIter: Int, minMse: Double) = {
    val timeStart = Calendar.getInstance()
    val netw = NeuralNetwork.initiateNetwork(data, 0.1, data.head.size-1, hiddenNode, 1, weightGenerator, activationFunction)
    val trainedNetwork = NeuralNetwork.initiateTraining(netw, data, 0.1, 0.9, maxIter, minMse)
    val timeEnd = Calendar.getInstance()

    val pw = new PrintWriter(new File(s"${fileName}_time"))
    pw.println((timeEnd.getTimeInMillis - timeStart.getTimeInMillis)/1000)
    pw.close()

    NeuralNetwork.saveNetwork(trainedNetwork, fileName)
  }

}

object testing{

  def initateTesting = {

    val testData = converter.toListDouble("testData/testData.csv")
    val testPca90Data = converter.toListDouble("testData/testData_pca90.csv")
    val testPca95Data = converter.toListDouble("testData/testData_pca95.csv")

    setTesting(testData, "sampleData25h")
    setTesting(testData, "sampleData26h")
    setTesting(testData, "sampleData27h")
    setTesting(testData, "sampleData28h")
    setTesting(testData, "sampleData29h")
    setTesting(testData, "sampleData30h")
    setTesting(testData, "sampleData31h")
    setTesting(testData, "sampleData32h")
    setTesting(testData, "sampleData33h")
    setTesting(testData, "sampleData34h")

    setTesting(testPca90Data, "sampleDataPca907h")
    setTesting(testPca90Data, "sampleDataPca908h")
    setTesting(testPca90Data, "sampleDataPca909h")
    setTesting(testPca90Data, "sampleDataPca9010h")

    setTesting(testPca95Data, "sampleDataPca957h")
    setTesting(testPca95Data, "sampleDataPca958h")
    setTesting(testPca95Data, "sampleDataPca959h")
    setTesting(testPca95Data, "sampleDataPca9510h")
    setTesting(testPca95Data, "sampleDataPca9511h")
    setTesting(testPca95Data, "sampleDataPca9512h")
    setTesting(testPca95Data, "sampleDataPca9513h")
    setTesting(testPca95Data, "sampleDataPca9514h")

    setTesting(testData, "sampleData25h_2")
    setTesting(testData, "sampleData26h_2")
    setTesting(testData, "sampleData27h_2")
    setTesting(testData, "sampleData28h_2")
    setTesting(testData, "sampleData29h_2")
    setTesting(testData, "sampleData30h_2")
    setTesting(testData, "sampleData31h_2")
    setTesting(testData, "sampleData32h_2")
    setTesting(testData, "sampleData33h_2")
    setTesting(testData, "sampleData34h_2")

    setTesting(testPca90Data, "sampleDataPca907h_2")
    setTesting(testPca90Data, "sampleDataPca908h_2")
    setTesting(testPca90Data, "sampleDataPca909h_2")
    setTesting(testPca90Data, "sampleDataPca9010h_2")

    setTesting(testPca95Data, "sampleDataPca957h_2")
    setTesting(testPca95Data, "sampleDataPca958h_2")
    setTesting(testPca95Data, "sampleDataPca959h_2")
    setTesting(testPca95Data, "sampleDataPca9510h_2")
    setTesting(testPca95Data, "sampleDataPca9511h_2")
    setTesting(testPca95Data, "sampleDataPca9512h_2")
    setTesting(testPca95Data, "sampleDataPca9513h_2")
    setTesting(testPca95Data, "sampleDataPca9514h_2")

    setTesting(testData, "sampleData25h_3")
    setTesting(testData, "sampleData26h_3")
    setTesting(testData, "sampleData27h_3")
    setTesting(testData, "sampleData28h_3")
    setTesting(testData, "sampleData29h_3")
    setTesting(testData, "sampleData30h_3")
    setTesting(testData, "sampleData31h_3")
    setTesting(testData, "sampleData32h_3")
    setTesting(testData, "sampleData33h_3")
    setTesting(testData, "sampleData34h_3")

    setTesting(testPca90Data, "sampleDataPca907h")
    setTesting(testPca90Data, "sampleDataPca908h")
    setTesting(testPca90Data, "sampleDataPca909h")
    setTesting(testPca90Data, "sampleDataPca9010h")

    setTesting(testPca95Data, "sampleDataPca957h")
    setTesting(testPca95Data, "sampleDataPca958h")
    setTesting(testPca95Data, "sampleDataPca959h")
    setTesting(testPca95Data, "sampleDataPca9510h")
    setTesting(testPca95Data, "sampleDataPca9511h")
    setTesting(testPca95Data, "sampleDataPca9512h")
    setTesting(testPca95Data, "sampleDataPca9513h")
    setTesting(testPca95Data, "sampleDataPca9514h")

  }

  def initateTesting_r = {

    //val testData = converter.toListDouble("testData/testData_n.csv")
    //val testPca90Data = converter.toListDouble("testData/testData_pca90_n.csv")
    //val testPca95Data = converter.toListDouble("testData/testData_pca95_n.csv")

    val testData = converter.toListDouble("trainingData/trainingData_n.csv")
    val testPca90Data = converter.toListDouble("trainingData/trainingData_pca90_n.csv")
    val testPca95Data = converter.toListDouble("trainingData/trainingData_pca95_n.csv")

    setTesting(testData, "sampleData25h")
    setTesting(testData, "sampleData26h")
    setTesting(testData, "sampleData27h")
    setTesting(testData, "sampleData28h")
    setTesting(testData, "sampleData29h")
    setTesting(testData, "sampleData30h")
    setTesting(testData, "sampleData31h")
    setTesting(testData, "sampleData32h")
    setTesting(testData, "sampleData33h")
    setTesting(testData, "sampleData34h")

    setTesting(testPca90Data, "sampleDataPca907h")
    setTesting(testPca90Data, "sampleDataPca908h")
    setTesting(testPca90Data, "sampleDataPca909h")
    setTesting(testPca90Data, "sampleDataPca9010h")

    setTesting(testPca95Data, "sampleDataPca957h")
    setTesting(testPca95Data, "sampleDataPca958h")
    setTesting(testPca95Data, "sampleDataPca959h")
    setTesting(testPca95Data, "sampleDataPca9510h")
    setTesting(testPca95Data, "sampleDataPca9511h")
    setTesting(testPca95Data, "sampleDataPca9512h")
    setTesting(testPca95Data, "sampleDataPca9513h")
    setTesting(testPca95Data, "sampleDataPca9514h")

    setTesting(testData, "sampleData25h_2")
    setTesting(testData, "sampleData26h_2")
    setTesting(testData, "sampleData27h_2")
    setTesting(testData, "sampleData28h_2")
    setTesting(testData, "sampleData29h_2")
    setTesting(testData, "sampleData30h_2")
    setTesting(testData, "sampleData31h_2")
    setTesting(testData, "sampleData32h_2")
    setTesting(testData, "sampleData33h_2")
    setTesting(testData, "sampleData34h_2")

    setTesting(testPca90Data, "sampleDataPca907h_2")
    setTesting(testPca90Data, "sampleDataPca908h_2")
    setTesting(testPca90Data, "sampleDataPca909h_2")
    setTesting(testPca90Data, "sampleDataPca9010h_2")

    setTesting(testPca95Data, "sampleDataPca957h_2")
    setTesting(testPca95Data, "sampleDataPca958h_2")
    setTesting(testPca95Data, "sampleDataPca959h_2")
    setTesting(testPca95Data, "sampleDataPca9510h_2")
    setTesting(testPca95Data, "sampleDataPca9511h_2")
    setTesting(testPca95Data, "sampleDataPca9512h_2")
    setTesting(testPca95Data, "sampleDataPca9513h_2")
    setTesting(testPca95Data, "sampleDataPca9514h_2")

    setTesting(testData, "sampleData25h_3")
    setTesting(testData, "sampleData26h_3")
    setTesting(testData, "sampleData27h_3")
    setTesting(testData, "sampleData28h_3")
    setTesting(testData, "sampleData29h_3")
    setTesting(testData, "sampleData30h_3")
    setTesting(testData, "sampleData31h_3")
    setTesting(testData, "sampleData32h_3")
    setTesting(testData, "sampleData33h_3")
    setTesting(testData, "sampleData34h_3")

    setTesting(testPca90Data, "sampleDataPca907h_3")
    setTesting(testPca90Data, "sampleDataPca908h_3")
    setTesting(testPca90Data, "sampleDataPca909h_3")
    setTesting(testPca90Data, "sampleDataPca9010h_3")

    setTesting(testPca95Data, "sampleDataPca957h_3")
    setTesting(testPca95Data, "sampleDataPca958h_3")
    setTesting(testPca95Data, "sampleDataPca959h_3")
    setTesting(testPca95Data, "sampleDataPca9510h_3")
    setTesting(testPca95Data, "sampleDataPca9511h_3")
    setTesting(testPca95Data, "sampleDataPca9512h_3")
    setTesting(testPca95Data, "sampleDataPca9513h_3")
    setTesting(testPca95Data, "sampleDataPca9514h_3")

  }

  def setTesting(testData: List[List[Double]], fileName: String) = {
    testing.test(
      testData.map(a => a.take(a.size-1)),
      testData.map(_.last),
      fileName
    )
  }

  def test(data: List[List[Double]], flag: List[Double], fileName: String) = {

    lazy val network = NeuralNetwork.loadNetwork(s"trainingResult_2/${fileName}")


    val prediction = data.map { r =>
      Math.round(NeuralNetwork.feedForward(network, r).head).toDouble
    }.toList

    /*println(fileName)

    val predictionWithoutRound = data.zipWithIndex.map{ r =>
      Tuple2(flag(r._2), NeuralNetwork.feedForward(network, r._1).head)
    }.map(d => Math.pow(d._1 - d._2,2)).sum / flag.size
    println(predictionWithoutRound)*/


    val predictionComparison = prediction.zipWithIndex.map{ d =>
      if(d._1 == flag(d._2)) true else false
    }

    val isFraudAndCorrect = flag.zipWithIndex.count(a => a._1 == 1 && predictionComparison(a._2) == true)
    val isFraudAndFalse = flag.zipWithIndex.count( a => a._1 == 1 && predictionComparison(a._2) == false)
    val notFraudAndCorrect = flag.zipWithIndex.count( a => a._1 == 0 && predictionComparison(a._2) == true)
    val notFraudAndFalse = flag.zipWithIndex.count( a => a._1 == 0 && predictionComparison(a._2) == false)

    /*val pw = new PrintWriter(new File(s"testingResult_2/${fileName}.csv"))
    pw.println(s"is fraud and correct = $isFraudAndCorrect")
    pw.println(s"is fraud and false = $isFraudAndFalse")
    pw.println(s"not fraud and correct = $notFraudAndCorrect")
    pw.println(s"not fraud and false = $notFraudAndFalse")
    pw.println(s"tpr = ${(isFraudAndCorrect.toDouble/(isFraudAndCorrect + isFraudAndFalse).toDouble) * 100}")
    pw.println(s"accuracy = ${((isFraudAndCorrect + notFraudAndCorrect).toDouble/flag.size.toDouble) * 100}")
    pw.close()*/

    val accuracy = predictionComparison.count(_ == true).toDouble / predictionComparison.size.toDouble
    println(s"$fileName $accuracy")

  }

}