package edu.knoldus

import akka.actor.{Props, ActorSystem, Actor}
import akka.util.Timeout
import edu.knoldus.PurchaseRequestHandler.Customer
import akka.pattern.ask
import scala.concurrent.duration._

class PurchaseRequestHandler extends Actor with akka.actor.ActorLogging {

  override def receive = {
    case Customer(_, _, _, _) =>
      log.info("Validating item availability")
      context.actorOf(Props[ValidationActor]).forward(Customer)
  }

}

object PurchaseRequestHandler extends App {

  case class Customer(name: String, address: String, creditNo: Long, mobileNo: Long)

  val system = ActorSystem("Purchase")
  val ref = system.actorOf(Props[PurchaseRequestHandler])

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val result = ref ? Customer("Charmy", "Mzn", 3425162745L, 7685948576L)
  val result1 = ref ? Customer("Charmy", "Mzn", 3425162745L, 7685948576L)
  result foreach println
  result1 foreach println

}
