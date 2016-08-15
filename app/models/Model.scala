package models

/**
  * Created by stevanusandrianta on 7/26/16.
  */

case class Model(id: String, modelName: String, timestamp: String, accuracy: Double, isActive: String)

object Model {

  import play.api.libs.json._

  implicit object ModelWrites extends OWrites[Model] {
    def writes(model: Model): JsObject = Json.obj(
      "_id" -> model.id,
      "modelName" -> model.modelName,
      "timestamp" -> model.timestamp,
      "accuracy" -> model.accuracy,
      "isActive" -> model.isActive
    )
  }

  implicit object ModelReads extends Reads[Model] {
    def reads(json: JsValue): JsResult[Model] = json match {
      case obj: JsObject => try {
        val id = (obj \ "_id").as[String]
        val modelName = (obj \ "modelName").as[String]
        val timestamp = (obj \ "timestamp").as[String]
        val accuracy = (obj \ "accuracy").as[Double]
        val isActive = (obj \ "isActive").as[String]

        JsSuccess(Model(id, modelName, timestamp, accuracy, isActive))

      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }

      case _ => JsError("expected.jsobject")
    }
  }

}

