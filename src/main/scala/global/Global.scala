package global

import com.rabbitmq.client.ConnectionFactory
import system.AMQPConnection
import system.AMQPActions
import com.rabbitmq.client.QueueingConsumer
import java.util.UUID
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

object Global extends App {
  val QueueName = "test"
  val Host = "localhost"
  var counter : Long = 0
  val manager = new AMQPConnection(Host)
  val channel = manager.createChannel
  val process : Seq[Future[Boolean]]=for(i <- 1 to 10) yield Future({
    for (v <- 0 to 100000) {
      counter += 1
      AMQPActions.sendMessage(UUID.randomUUID().toString(), QueueName, channel)
    }
    true
  })
  val workers = Future.sequence(process)
  Await.result(workers, 5.minutes)
  manager.terminate(List(channel))
  println(s"$counter Msg Sent")
}

 