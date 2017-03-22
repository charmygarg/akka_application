import akka.actor.{ActorSystem, Props}
import akka.testkit._
import com.typesafe.config.ConfigFactory
import edu.knoldus.{Customer, ValidationActor}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

object ValidationActorSpec {
  val testSystem = {

    val config = ConfigFactory.parseString(
      """
        |akka.loggers = [akka.testkit.TestEventListener]
      """.stripMargin
    )
    ActorSystem("test-system", config)
  }
}

import ValidationActorSpec._

class ValidationActorSpec extends TestKit(testSystem) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll() = {
    system.terminate()
  }

  "ValidationActor" must {
    "log Validation request handler when receives a request" in {
      val dispatcherId = CallingThreadDispatcher.Id
      val props = Props(classOf[ValidationActor], testActor).withDispatcher(dispatcherId)

      val ref = system.actorOf(props)

      EventFilter.info(message = "Checking for existence of item in ValidationActor", occurrences = 1)
        .intercept {
          ref ! Customer("Charmy", "Mzn", 3425162745L, 7685948576L)
        }
    }

    "forward to PurchaseHandler Actor" in {

      val dispatcherId = CallingThreadDispatcher.Id
      val props = Props(classOf[ValidationActor], testActor).withDispatcher(dispatcherId)

      val ref = system.actorOf(props)

      ref ! Customer("Charmy", "Noida", 6781932L, 9876543210L)

      expectMsg(0)
    }
  }

}