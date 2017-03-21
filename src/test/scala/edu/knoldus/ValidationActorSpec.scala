package edu.knoldus

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{MustMatchers, BeforeAndAfterAll, WordSpecLike}

/**
  * Created by knodus on 21/3/17.
  */
class ValidationActorSpec extends TestKit(ActorSystem("test-system")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

}
