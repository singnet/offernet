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
import org.json.JSONArray
import groovy.json.JsonOutput;

public class Analyst extends UntypedAbstractActor {
	  private DseSession session;
    private Logger logger;
    private OfferNet on;

  static Props props(DseSession session, OfferNet on) {
    return Props.create(new Creator<Analyst>() {
      @Override
      public Analyst create() throws Exception {
        return new Analyst(session, on);
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

	public Analyst(DseSession session, OfferNet on) {

        def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
        PropertyConfigurator.configure(config.toProperties())
        logger = LoggerFactory.getLogger('Analyst.class');

        this.session = session;
        this.on = on;

	}

  private degreeDistribution(String vertexType, String edgeLabel, String direction, String message, String simulationId) {
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
        simulationId,
        vertexType,
        edgeLabel,
        direction,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))
  }

  private Object allEdgesByLabel(String message, String simulationId) {
      def start = System.currentTimeMillis();

      String query = "g.V().has('type',within(['agent','item','work'])).outE().dedup().groupCount().by(label)"

      SimpleGraphStatement s = new SimpleGraphStatement(query)
      GraphResultSet rs = session.executeGraph(s);
      def results = rs.one().asMap();
      def resultsJson =  JsonOutput.toJson(results);

      logger.info('method={} :  simulationId={} : results={} : message=[{}] : wallTime_ms={} msec.', 
        'allEdgesByLabel', 
        simulationId,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))

      return resultsJson
  }

  private allVerticesByLabel(String message, String simulationId) {
      def start = System.currentTimeMillis();

      String query = "g.V().has('type',within(['agent','item','work'])).dedup().groupCount().by(label)"

      SimpleGraphStatement s = new SimpleGraphStatement(query)
      GraphResultSet rs = session.executeGraph(s);
      def results = rs.one().asMap();
      def resultsJson =  JsonOutput.toJson(results);

      logger.info('method={} :  simulationId={} : results={} : message=[{}] : wallTime_ms={} msec.', 
        'allVerticesByLabel', 
        simulationId,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))
  }


  private similarityEdgesByWeight(String message, String simulationId) {
      def start = System.currentTimeMillis();

      String query = "g.V().has('type','item').dedup().outE().has(label,'similarity').groupCount().by(values('similarity').math('floor (_*1000)/1000'))"

      SimpleGraphStatement s = new SimpleGraphStatement(query)
      GraphResultSet rs = session.executeGraph(s);
      def results = rs.one().asMap();
      def resultsJson =  JsonOutput.toJson(results);

      logger.info('method={} :  simulationId={} : results={} : message=[{}] : wallTime_ms={} msec.', 
        'similarityEdgesByWeight', 
        simulationId,
        resultsJson,
        message,
        (System.currentTimeMillis()-start))
  }

  int checkFoundPaths(List cycle, List chain) {
      def start = System.currentTimeMillis();
      def currentMethodName = 'checkFoundPaths'
      def jsonSlurper = new JsonSlurper()
      def cycleJson = jsonSlurper.parseText(cycle.toString());
      def foundCyclesCount = 0;

      def richCycle = getVerticesBelongingToSubgraph(cycleJson)
      logger.debug("Got cycle enriched by vertices {}",richCycle)
      def keyword = "";
      if (richCycle.size()!=0) {
          def cycleContainsChain = Utils.pathContainsChain(richCycle, chain)
          if (cycleContainsChain) {
            foundCyclesCount += 1;
            keyword = "foundCycle";
            Global.parameters.terminate_all = true
          } else {
            keyword = "foundPath"
          }
      }
   
      logger.info('method={} : simulationId={} : agentId={} : keyword={} : cyGraph={} : wallTime_ms={} msec.', 
        currentMethodName, 
        Global.parameters.simulationId,
        getSender(),
        keyword,
        Utils.convertToCYNotation(richCycle,keyword),
        (System.currentTimeMillis()-start)
      )
    return foundCyclesCount;
  }

  Object getVerticesBelongingToSubgraph(Object subgraph) {
      def start = System.currentTimeMillis()
      logger.debug("subgraph is {}",subgraph)
      JSONArray uniquePath = new JSONArray()
      subgraph.each { edge ->
        JSONArray singleChain = new JSONArray()
        singleChain.put(edge)
        logger.debug("Getting vertexes of the edge {}",edge)
        def inV = Utils.formatVertexLabel(edge.id.get('~in_vertex'))
        logger.debug("Getting vertex {}",inV)
        def vertexIn = this.on.getVertex(inV)
        logger.debug('got vertexIn {}',vertexIn)
        def outV = Utils.formatVertexLabel(edge.id.get('~out_vertex'))
        logger.debug("Getting vertex {}",outV)
        def vertexOut = this.on.getVertex(outV)
        logger.debug('got vertexOut {}',vertexOut)
        singleChain.put(vertexIn[0])
        singleChain.put(vertexOut[0])
        uniquePath.put(singleChain)
      }
      logger.debug("formed a uniquePath with edges and vertices {}",uniquePath)
      logger.debug('{} : {} : wallTime_ms={} msec.', 
        'getVerticesBelongingToSubgraph', 
        Global.parameters.simulationId,
        (System.currentTimeMillis()-start))

      return uniquePath
  }


}
