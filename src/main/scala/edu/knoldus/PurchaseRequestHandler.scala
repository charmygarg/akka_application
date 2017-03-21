package edu.knoldus

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.util.Timeout
import edu.knoldus.PurchaseRequestHandler.Customer
import akka.pattern.ask
import scala.concurrent.duration._

class PurchaseRequestHandler(ref: ActorRef) extends Actor with akka.actor.ActorLogging {

  override def receive = {
    case Customer(_, _, _, _) =>
      log.info("Validating item availability")
      ref.forward(Customer)
  }

}

object PurchaseRequestHandler extends App {

  case class Customer(name: String, address: String, creditNo: Long, mobileNo: Long)

  val system = ActorSystem("Purchase")

  val purchaseActor = system.actorOf(Props[PurchaseActor])
  val validationActor = system.actorOf(Props(classOf[ValidationActor], purchaseActor))
  val ref = system.actorOf(Props(classOf[PurchaseRequestHandler], validationActor))

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val result0 = ref ? Customer("Charmy", "Mzn", 3425162745L, 7685948576L)
  val result1 = ref ? Customer("Simar", "Delhi", 3425162745L, 7685948576L)
  val result2 = ref ? Customer("Ashish", "Badhot", 3425162745L, 7685948576L)
  val result3 = ref ? Customer("Shubra", "Dehradun", 3425162745L, 7685948576L)
  val result4 = ref ? Customer("Himanshu", "Pahadi", 3425162745L, 7685948576L)
  val result5 = ref ? Customer("Vandana", "Noida", 3425162745L, 7685948576L)
  val result6 = ref ? Customer("Vandana", "Noida", 3425162745L, 7685948576L)
  result0 foreach println
  result1 foreach println
  result2 foreach println
  result3 foreach println
  result4 foreach println
  result5 foreach println
  result6 foreach println

}
