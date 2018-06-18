package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import akka.actor.UntypedAbstractActor;
import akka.actor.Props;
import akka.japi.Creator;

import groovy.json.JsonSlurper
import java.nio.charset.StandardCharsets

public class SocketWriter extends UntypedAbstractActor {
  ServerSocket server;
  Logger logger;

  public static Props props() {
    return Props.create(new Creator<SocketWriter>() {
      @Override
      public SocketWriter create() throws Exception {
        return new SocketWriter();
      }
    });
  }

  public void onReceive(Object message) throws Exception {
    if (message instanceof Method) {
      logger.info("received Method message: {}",message.getMethodString())
      switch (message) {
        default: 
          def args = message.args
          def reply = this."$message.name"(*args)
          //getSender().tell(reply,getSelf());
          break;
      }
    } else if (message instanceof String) {
      logger.info("Agent {} received message {}",this.id(), message)
    } else { 
      getSender().tell("Cannot process the message",getSelf()) 
    }
  }

  private SocketWriter() {
    logger = LoggerFactory.getLogger('SocketWriter.class');
//    startServer();
  }

  private startServer() {
    server = new ServerSocket(Parameters.parameters.visualizationPort)
    logger.info('Started Socket Server {}', server)

    while(true) {
      server.accept { socket ->
        logger.info("processing new connection...")
        socket.withStreams { input, output ->
          def reader = input.newReader()
          def buffer = reader.readLine()
          logger.info("server received: $buffer")
          output << buffer+'\n'
          logger.info("end echoed: $buffer")
        }
        logger.info("processing/thread complete.")
      }
    }

  }

  private writeSocket(Object event) {
      String visualizationServer = InetAddress.getByName("visualization-server.host").getHostAddress(); 
      logger.info("visualizationServer address is {}",visualizationServer)
      def s = new Socket(visualizationServer, Parameters.parameters.visualizationPort);
      s.withStreams { input, output ->
        output << event
        //def buffer = input.newReader().readLine()
        //logger.info("response = $buffer")
      }
      logger.info("Wrote JSON string of an event {} to socket.", event);
  }

      
}
