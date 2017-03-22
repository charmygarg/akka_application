package edu.knoldus

import akka.actor.{ActorSystem, Props}
import akka.testkit.{DefaultTimeout, ImplicitSender, TestActors, TestKit}
import edu.knoldus.PurchaseRequestHandler.Customer
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._

class PurchaseHandlerSpec extends TestKit(ActorSystem("PurchaseHandlerSpec"))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  val echoRef = system.actorOf(TestActors.echoActorProps)
  val ref = system.actorOf(Props(classOf[PurchaseRequestHandler], testActor))

  "Purchase Request Handler " must {
    "forward Customer details to Validation Actor" in {
      within(500 millis) {
        echoRef ! Customer
        expectMsg(Customer)
      }
    }
  }

}
