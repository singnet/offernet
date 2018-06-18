package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import groovy.json.JsonSlurper

import akka.actor.UntypedAbstractActor;
import akka.actor.Props;
import akka.japi.Creator;

import org.junit.BeforeClass;

public class DummySocketServer extends UntypedAbstractActor {
    ServerSocket server;
    Logger logger;

	  public static Props props() {
      return Props.create(new Creator<DummySocketServer>() {
		    @Override
  		  public DummySocketServer create() throws Exception {
    		  return new DummySocketServer();
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
            getSender().tell(reply,getSelf());
            break;
        }
      } else if (message instanceof String) {
        logger.info("Agent {} received message {}",this.id(), message)
      } else { 
        getSender().tell("Cannot process the message",getSelf()) 
      }
    }

    private DummySocketServer() {
        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('DummySocketServer.class');
    }

    private startClient() {
      String visualizationServer = InetAddress.getByName("visualization-server.host").getHostAddress(); 
      def socket = new Socket(visualizationServer, Parameters.parameters.visualizationPort)
      logger.info("connected to server from client with socket: {}", socket)
      BufferedReader bin =new BufferedReader(new InputStreamReader(socket.getInputStream()));        
      while(true) {
        def buffer = bin.readLine()
        //def message = (Map) new JsonSlurper().parseText(buffer);
        logger.warn("Received buffer {}", buffer)
      }
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
    
}

