import akka.actor.{ActorSystem, Props}
import akka.testkit.{CallingThreadDispatcher, EventFilter, TestKit}
import com.typesafe.config.ConfigFactory
import edu.knoldus.PurchaseRequestHandler
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
      val props = Props(classOf[PurchaseRequestHandler], testActor).withDispatcher(dispatcherId)

      val ref = system.actorOf(props)

      EventFilter.info(message = "Checking for existence of item in ValidationActor", occurrences = 1)
//        .intercept{
//          ref ! Customer
//        }
    }
  }

}