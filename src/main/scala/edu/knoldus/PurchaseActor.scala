package edu.knoldus

import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

class PurchaseActor extends Actor with akka.actor.ActorLogging {

  override def receive = {
    case 0 =>
      //context.actorOf(Props[ValidationActor]) ! -1
      sender() ! "Your item has been booked"
  }

}

//object PurchaseActor {
//
//  val config = ConfigFactory.parseString(
//    """
//      |akka.actor.deployment {
//      | /poolRouter {
//      |   router = balancing-pool
//      |   nr-of-instances = 5
//      | }
//      |}
//    """.stripMargin
//  )
//
//  val system = ActorSystem("RouterSystem", config)
//  val router = system.actorOf(FromConfig.props(Props[PurchaseActor]), "poolRouter")
//
//  implicit val timeout = Timeout(1000 seconds)
//
//  router ! -1
//}
