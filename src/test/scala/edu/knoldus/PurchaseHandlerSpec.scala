import akka.actor.{ActorSystem, Props}
import akka.testkit.{CallingThreadDispatcher, EventFilter, TestKit}
import com.typesafe.config.ConfigFactory
import edu.knoldus.PurchaseRequestHandler
import edu.knoldus.PurchaseRequestHandler.Customer
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

object PurchaseHandlerSpec {
  val testSystem = {
    val config = ConfigFactory.parseString(
      """
        |akka.loggers = [akka.testkit.TestEventListener]
      """.stripMargin
    )
    ActorSystem("test-system", config)
  }
}

import PurchaseHandlerSpec._

class PurchaseHandlerSpec extends TestKit(testSystem) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll() = {
    system.terminate()
  }

  "PurchaseHandler" must {
    "log Purchase request handler when receives a request" in {
      val dispatcherId = CallingThreadDispatcher.Id
      val props = Props(classOf[PurchaseRequestHandler], testActor).withDispatcher(dispatcherId)

      val ref = system.actorOf(props)

      EventFilter.info(message = "Validating item availability in PurchaseRequestHandler", occurrences = 1)
        .intercept{
          ref ! Customer("Charmy", "Noida", 6781932L, 9876543210L)
        }
    }
  }

}