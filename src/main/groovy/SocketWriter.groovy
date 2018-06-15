package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import akka.actor.UntypedAbstractActor;
import akka.actor.Props;
import akka.japi.Creator;

public class SocketWriter extends UntypedAbstractActor {
  Socket s;

  public static Props props() {
    return Props.create(new Creator<SocketWriter>() {
      @Override
      public SocketWriter create() throws Exception {
        return new SocketWriter();
      }
    });
  }

  public void onReceive(Object message) throws Exception {
    if (message instanceof Event) {
      this.sendSocket(message);
      logger.info("received event {} from {}", message, getSender());
    } else if (message instanceof Method) {
      logger.info("received Method message: {}",message.getMethodString())
      switch (message) {
        default: 
          def args = message.args
          def reply = this."$message.name"(*args)
          getSender().tell(reply,getSelf());
          break;
      }
    } else if (message instanceof String) {
      logger.info("Agent {} received message {}",this.id(), message)
    } else { 
      getSender().tell("Cannot process the message",getSelf()) 
    }
  }


  private SocketWriter() {
    Logger logger = LoggerFactory.getLogger('SocketWriter.class');
    String visualizationServer = InetAddress.getByName("visualization-server.host").getHostAddress(); 
    s = new Socket(visualizationServer, Parameters.parameters.visualizationPort);
    logger.info("Created SocketWriter {}", s.toString());
  }

  private writeSocket(Event event) {
      OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8)
      out.write(event.toJson());
      logger.info("Wrote JSON string of an event {} to socket.", eventJson);
    }
}
