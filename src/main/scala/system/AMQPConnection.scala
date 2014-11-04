package system

import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory

class AMQPConnection(host: String) {
  val factory = new ConnectionFactory
  factory.setHost(host)
  val connection = factory.newConnection()
  
  def createChannel: Channel = {
    connection.createChannel()
  }
  
  def terminate(channels : List[Channel]){
    for(c <- channels) c.close()
    connection.close
  }
}