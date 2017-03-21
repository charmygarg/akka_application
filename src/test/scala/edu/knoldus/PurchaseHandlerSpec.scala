package edu.knoldus

import akka.actor.{Props, ActorSystem}
import akka.testkit.TestKit
import akka.util.Timeout
import edu.knoldus.PurchaseRequestHandler.Customer
import org.scalatest.{MustMatchers, BeforeAndAfterAll, WordSpecLike}
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

/**
  * Created by knodus on 21/3/17.
  */
class PurchaseHandlerSpec extends TestKit(ActorSystem("test-system")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "Purchase Request Handler " must {
    "forward Customer details to Validation Actor" in {
      val ref = system.actorOf(Props(classOf[PurchaseRequestHandler], testActor))
      implicit val timeout = Timeout(15 seconds)
      val future = ref.ask(Customer("Charmy", "Mzn", 3425162745L, 7685948576L))
      Await.result(future, Duration.Inf) must be(Customer)
    }
  }

}
