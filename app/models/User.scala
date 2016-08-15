package models

/**
  * Created by stevanusandrianta on 7/25/16.
  */
case class User(id: String, username: String, password: String, role: String)

object User {

  import play.api.libs.json._

  implicit object UserWrites extends OWrites[User] {
    def writes(user: User): JsObject = Json.obj(
      "_id" -> user.id,
      "username" -> user.username,
      "password" -> user.password,
      "role" -> user.role
    )
  }

  implicit object ArticleReads extends Reads[User] {
    def reads(json: JsValue): JsResult[User] = json match {
      case obj: JsObject => try {
        val id = (obj \ "_id").as[String]
        val username = (obj \ "username").as[String]
        val password = (obj \ "password").as[String]
        val role = (obj \ "role").as[String]

        JsSuccess(User(id, username, password, role))

      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }

      case _ => JsError("expected.jsobject")
    }
  }

}

