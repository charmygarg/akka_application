package edu.knoldus

import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory

class PurchaseActor extends Actor with akka.actor.ActorLogging {

  override def receive = {
    case 0 =>
      sender() ! "Your item has been booked"
    case -1 =>
      sender() ! "Sorry!! Out of Stock"
  }

}

object PurchaseActor {

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /poolRouter {
      |   router = balancing-pool
      |   nr-of-instances = 5
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)
  val router = system.actorOf(FromConfig.props(Props[PurchaseActor]), "poolRouter")

}
