package controllers

import commons.node.Client
import errors.ExceptionThrowable
import play.api.libs.circe.Circe
import play.api.mvc._

abstract class ErgoApiController(
  val client: Client,
  val controllerComponents: ControllerComponents
) extends BaseController
    with Circe
    with ExceptionThrowable {

  def test(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      println("Ergo API Controller Test")
      Ok("cool").as("application/json")
  }
}

class ErgoApiControllerImpl(
  val client: Client,
  val controllerComponents: ControllerComponents
) extends BaseController
    with Circe
    with ExceptionThrowable {

  def test(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      println("Ergo API Controller Test")
      Ok("cool").as("application/json")
  }
}
