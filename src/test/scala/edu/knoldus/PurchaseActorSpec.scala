package edu.knoldus

import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestActorRef, TestKit}
import akka.util.Timeout
import org.scalatest.{MustMatchers, BeforeAndAfterAll, WordSpecLike}
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

class PurchaseActorSpec extends TestKit(ActorSystem("test-system")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "Purchase Actor " must {
    "return Item booked" in {
      val ref = system.actorOf(Props[PurchaseActor])
      implicit val timeout = Timeout(15 seconds)
      val future = ref.ask(0)
      Await.result(future, Duration.Inf) must be("Your item has been booked")
    }
  }

  "Purchase Actor " must {
    "return out of stock" in {
      val ref = system.actorOf(Props[PurchaseActor])
      implicit val timeout = Timeout(15 seconds)
      val future = ref.ask(-1)
      Await.result(future, Duration.Inf) must be("Sorry!! Out of Stock")
    }
  }

}
