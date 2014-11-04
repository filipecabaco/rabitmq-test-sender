package system

import com.rabbitmq.client.Channel
import com.rabbitmq.client.QueueingConsumer


object AMQPActions {
	def sendMessage(msg:String,queue:String,channel : Channel){
	  channel.queueDeclare(queue, false, false, false, null)
	  channel.basicPublish("",queue,null,queue.getBytes())
	}
	def queueListener(method: (String) => Unit ,consumer: QueueingConsumer){
	  val v = new String(consumer.nextDelivery.getBody())
	  println(v)
	  method(v)
	}
}