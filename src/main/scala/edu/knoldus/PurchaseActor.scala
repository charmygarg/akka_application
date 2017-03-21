package edu.knoldus

import akka.actor.Actor

class PurchaseActor extends Actor with akka.actor.ActorLogging {

  override def receive = {
    case 0 =>
      sender() ! "Your item has been booked"
    case -1 =>
      sender() ! "Sorry!! Out of Stock"
  }

}

