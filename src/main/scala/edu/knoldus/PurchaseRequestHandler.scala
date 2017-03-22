package edu.knoldus

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import edu.knoldus.PurchaseRequestHandler.Customer
import akka.pattern.ask
import scala.concurrent.duration._

class PurchaseRequestHandler(validationRef: ActorRef) extends Actor with akka.actor.ActorLogging {

  override def receive = {
    case Customer(_, _, _, _) =>
      log.info("Validating item availability in PurchaseRequestHandler")
      validationRef.forward(Customer)
  }

}

object PurchaseRequestHandler extends App {

  case class Customer(name: String, address: String, creditNo: Long, mobileNo: Long)

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

  val purchaseActorRef = system.actorOf(FromConfig.props(Props[PurchaseActor]), "poolRouter")
  def props = Props(classOf[ValidationActor], purchaseActorRef)
  val validationActorRef = system.actorOf(props)
  val requestHandlerRef = system.actorOf(Props(classOf[PurchaseRequestHandler], validationActorRef))

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val result0 = requestHandlerRef ? Customer("Charmy", "Mzn", 3425162745L, 7685948576L)
  val result1 = requestHandlerRef ? Customer("Simar", "Delhi", 3425162745L, 7685948576L)
  val result2 = requestHandlerRef ? Customer("Ashish", "Badhot", 3425162745L, 7685948576L)
  val result3 = requestHandlerRef ? Customer("Shubra", "Dehradun", 3425162745L, 7685948576L)
  val result4 = requestHandlerRef ? Customer("Himanshu", "Pahadi", 3425162745L, 7685948576L)
  val result5 = requestHandlerRef ? Customer("Vandana", "Noida", 3425162745L, 7685948576L)
  val result6 = requestHandlerRef ? Customer("Vandana", "Noida", 3425162745L, 7685948576L)
  result0 foreach println
  result1 foreach println
  result2 foreach println
  result3 foreach println
  result4 foreach println
  result5 foreach println
  result6 foreach println

}
