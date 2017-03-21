package edu.knoldus

import akka.actor.{Props, Actor}
import edu.knoldus.PurchaseRequestHandler.Customer

class ValidationActor extends Actor with akka.actor.ActorLogging {

  var quantityOfItem = 5

  override def receive = {
    case Customer =>
      log.info("Checking for existence of item")
      self ! checkItem

   // case -1 => quantityOfItem -= 1
  }

  private def checkItem = {
    if(quantityOfItem > 0) {
      println(quantityOfItem)

      quantityOfItem -= 1
      context.actorOf(Props[PurchaseActor]).forward(0)
    } else {
      log.error("Sorry! Out Of Stock!! ")
    }
  }

}
