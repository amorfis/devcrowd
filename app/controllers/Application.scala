package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.templates.Html
import views.html
import controllers.Application.Participants

object Application extends Controller {
  
  case class Participants(firstName: String, surname: String, email: String, city: String, profession: String)

  val professionsList = List("Uczeń", "Student", "Pracujący w branży IT", "Pracujący w innej branży")

  val userForm = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "email" -> email,
      "city" -> text,
      "profession" -> text
    )(Participants.apply)(Participants.unapply)
  )
  
  def index = Action {
    Ok(views.html.index(userForm, professionsList))
  }

  def confirm = Action { implicit request =>
//    Ok(views.html.main("Confirmation")(Html("Hello")))

    userForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.index(formWithErrors, professionsList)),
      Participant => Ok(views.html.main("Confirmation")(Html("Hello")))
    )
  }
  
}