package edu.knoldus

import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestActors, ImplicitSender, DefaultTimeout, TestKit}
import edu.knoldus.PurchaseRequestHandler.Customer
import org.scalatest.{Matchers, MustMatchers, BeforeAndAfterAll, WordSpecLike}
import scala.concurrent.duration._

class ValidationActorSpec extends TestKit(ActorSystem("ValidationActorSpec"))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  val echoRef = system.actorOf(TestActors.echoActorProps)
  val ref = system.actorOf(Props(classOf[ValidationActor], testActor))

  "validation Actor " must {
    "validate quantity of items and forward for 0 " in {
      within(500 millis) {
        echoRef ! Customer
        expectMsg(Customer)
      }
    }
  }


}
