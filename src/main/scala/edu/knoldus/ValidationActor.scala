package edu.knoldus

import akka.actor.{ActorRef, Actor}
import edu.knoldus.PurchaseRequestHandler.Customer

class ValidationActor(purchaseRef: ActorRef) extends Actor with akka.actor.ActorLogging {

  var quantityOfItem = 5

  override def receive = {
    case Customer =>
      log.info("Checking for existence of item in ValidationActor")
      self ! checkItem
  }

  private def checkItem = {
    if(quantityOfItem > 0) {
      quantityOfItem -= 1
      purchaseRef.forward(0)
    } else {
      log.error("Out of stock")
      purchaseRef.forward(-1)
    }
  }

}
