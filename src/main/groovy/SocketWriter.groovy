package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import akka.actor.UntypedAbstractActor;
import akka.actor.Props;
import akka.japi.Creator;

import groovy.json.JsonSlurper
import java.nio.charset.StandardCharsets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.*;


public class SocketWriter extends UntypedAbstractActor {
  ServerSocket server;
  Logger logger;
  Socket socket;

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

  private createSocket() {
    try {
      String serverName = InetAddress.getByName("visualization-server.host").getHostAddress(); 
      int serverPort = Parameters.parameters.visualizationPort
      this.socket = new Socket(serverName, serverPort);
      Thread.sleep(100);
      logger.info("Created a socket to ")
      } catch (Exception e) {
      logger.info("Server Closed..Something went Wrong..");
          e.printStackTrace();
      }
  }

  private writeSocket(String event) {
    logger.info("Writing event {} to socket {}",event,socket)
    if (socket.isConnected()) {
        socket.getOutputStream().write(event.getBytes("UTF-8"));
        logger.info("Wrote event {} to socket {}", event, socket);
        Thread.sleep(20)
        socket.getOutputStream().flush();      
    } else {
      logger.info("Cannot send event {} --- socket {} disconnected", event, socket)
    }
  }
   
}
