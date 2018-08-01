package io.singularitynet.offernet

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphOptions
import com.datastax.driver.dse.auth.DsePlainTextAuthProvider;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.HostDistance;

import com.datastax.driver.dse.graph.Vertex
import com.datastax.driver.dse.graph.Edge

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.Creator;
import groovy.json.JsonSlurper;
import groovy.json.JsonOutput;

public class Analyst extends UntypedAbstractActor {
	  private DseSession session;
    private Logger logger;

  static Props props(DseSession session) {
    return Props.create(new Creator<Analyst>() {
      @Override
      public Analyst create() throws Exception {
        return new Analyst(session);
      }
    });
  }

  public void onReceive(Object message) throws Exception {
    logger.debug("{} : {} : analyst {} received message: {} of {}",
      'onReceive',
      Global.parameters.simulationId,
      message,
      message.getClass())
  
    if (message instanceof Method) {
      switch (message) {
        default: 
          def args = message.args
          def reply = this."$message.name"(*args)
          if (reply) { 
            getSender().tell(reply,getSelf());
          }
          break;
      }
    }
  }

	public Analyst(DseSession session) {

        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('Analyst.class');

        this.session = session;

	}

  private degreeDistribution(String vertexType, String edgeLabel, String direction, String message) {
      def start = System.currentTimeMillis();
      Map params = new HashMap();
      params.put("propertyKey", "type");
      params.put("vertexType",vertexType);
      params.put('edgeLabel',edgeLabel);

      String query = ""
      switch (direction) {
        case "in":
          query = "g.V().has(propertyKey,vertexType).groupCount().by(inE(edgeLabel).count())"
          break;
        case "out":
          query = "g.V().has(propertyKey,vertexType).groupCount().by(outE(edgeLabel).count())"
          break;
        case "both":
          query = "g.V().has(propertyKey,vertexType).groupCount().by(bothE(edgeLabel).count())"
          break;
      }

      SimpleGraphStatement s = new SimpleGraphStatement(query, params)
      GraphResultSet rs = session.executeGraph(s);
      def results = rs.one().asMap();
      def resultsJson =  JsonOutput.toJson(results);

      logger.info('method={} :  simulationId={} : vertexType={} ; edgeLabel={} : direction={} : results={} : message=[{}] : wallTime_ms={} msec.', 
        'degreeDistribution', 
        Global.parameters.simulationId,
        vertexType,
        edgeLabel,
        direction,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))
  }

  private allEdgesByLabel(String message) {
      def start = System.currentTimeMillis();

      String query = "g.V().has('type',within(['agent','item','work'])).outE().dedup().groupCount().by(label)"

      SimpleGraphStatement s = new SimpleGraphStatement(query)
      GraphResultSet rs = session.executeGraph(s);
      def results = rs.one().asMap();
      def resultsJson =  JsonOutput.toJson(results);

      logger.info('method={} :  simulationId={} : results={} : message=[{}] : wallTime_ms={} msec.', 
        'allEdgesByLabel', 
        Global.parameters.simulationId,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))
  }

  private allVerticesByLabel(String message) {
      def start = System.currentTimeMillis();

      String query = "g.V().has('type',within(['agent','item','work'])).dedup().groupCount().by(label)"

      SimpleGraphStatement s = new SimpleGraphStatement(query)
      GraphResultSet rs = session.executeGraph(s);
      def results = rs.one().asMap();
      def resultsJson =  JsonOutput.toJson(results);

      logger.info('method={} :  simulationId={} : results={} : message=[{}] : wallTime_ms={} msec.', 
        'allVerticesByLabel', 
        Global.parameters.simulationId,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))
  }


  private similarityEdgesByWeight(String message) {
      def start = System.currentTimeMillis();

      String query = "g.V().has('type','item').dedup().outE().has(label,'similarity').groupCount().by(values('similarity').math('floor (_*1000)/1000'))"

      SimpleGraphStatement s = new SimpleGraphStatement(query)
      GraphResultSet rs = session.executeGraph(s);
      def results = rs.one().asMap();
      def resultsJson =  JsonOutput.toJson(results);

      logger.info('method={} :  simulationId={} : results={} : message=[{}] : wallTime_ms={} msec.', 
        'similarityEdgesByWeight', 
        Global.parameters.simulationId,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))
  }

}
