package io.singularitynet.offernet

import groovy.json.*

import com.datastax.driver.dse.graph.GraphResultSet
import com.datastax.driver.dse.graph.GraphNode

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.datastax.driver.dse.graph.Edge
import com.datastax.driver.dse.graph.Vertex

import org.codehaus.groovy.runtime.StackTraceUtils

import static org.junit.Assert.*

import com.fasterxml.jackson.databind.ObjectMapper;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

public class Utils {
    static Logger logger = LoggerFactory.getLogger('Utils.class');

    public static String generateBinaryString(int length) { //implemented in python
        String randomString="";
        for (int i=0;i<length;i++) {
            Random randomNum = new Random();
            randomString = randomString.concat(randomNum.nextInt(2).toString());
        }
        return randomString;
    }

    public static List toList(Iterator iterator) {
    	List list=[]
    	while (iterator.hasNext()) {
    		list.add(iterator.next());
    	}
    	return list;

    }

    public static List createChain(int length) {
        List chain = []
        length.times {
            chain.add(generateBinaryString(Parameters.parameters.binaryStringLength))
        }
        logger.info("Created chain of length {} with: end: {}, start: {} ",length, chain.last(),chain.first())
        return chain
    }

    public static List<String> getWarnings(GraphResultSet rs) {
      List<String> executionWarnings = rs.getExecutionInfo().getWarnings();
      return executionWarnings;
    }

    public static String getStatement(GraphResultSet rs) {
      String executionStatement = rs.getExecutionInfo().getStatement().toString();
      return executionStatement;
    }

    public static String getStatement(GraphResultSet rs, Map params) {
      String executionStatement = rs.getExecutionInfo().getStatement().toString();
      params.each { labelName,labelValue ->
        executionStatement = executionStatement.replaceAll(labelName.toString(), labelValue.toString());
      }
      return executionStatement;
    }

    public static Object calculateSimilarity(Vertex itemOne, Vertex itemTwo) {
        return Utils.cosineSimilarity(itemOne.getProperty("value").getValue().asString(), itemTwo.getProperty("value").getValue().asString())
//  		return Utils.veitasSimilarity(itemOne.getProperty("value").getValue().asString(), itemTwo.getProperty("value").getValue().asString())
  	}

    public static Object calculateSimilarity(String left, String right) {
        return Utils.cosineSimilarity(left,right)
//      return Utils.veitasSimilarity(left,right)
    }

    public static Integer veitasSimilarity(CharSequence left, CharSequence right) {
      // this is simply a count of positions in the string where both strings have values
      // the logic behind being that positions encode features and 1 means that feature exists

  		if (left == null || right == null) {
  			throw new IllegalArgumentException("Strings must not be null");
  		}

  		if (left.length() > right.length()) {
        def temp = left;
        left=right;
        right=left;
  			// strings can have different lengths, but the measuring should be based on the shorter string
        // -- so we have to know which one is shorter..
  		}

  		int similarity = 0;

  		for (int i = 0; i < left.length(); i++) {
  			if (left.charAt(i) == right.charAt(i)) {
  				similarity++;
  			}
  		}
      logger.info("Calculated veitas similarity between {} and {}: {}", left,right,similarity)
  		return similarity;
  	}


    private static Object edgePropertyValue(Edge edge,String propertyName) {
        logger.warn("Returning integer value of the property {} on edge {}", propertyName,edge.getId())
        //def value = edge.getProperty('similarity').getValue().asInt()
        def value = edge.getProperty('similarity').getValue().asDouble()
        return value
    }

    private static String getCurrentMethodName(){
      def marker = new Throwable()
      return StackTraceUtils.sanitize(marker).stackTrace[1].methodName
    }

    public static boolean convertToDotNotation(Object path, String label, String dotFilePath) {
        
        def dotFile = new File(dotFilePath);
        dotFile.delete()
        // print heading
        dotFile << "graph G {\n"
        dotFile << "\trankdir=\"TD\";\n"
        dotFile << "\tsubgraph cluster_0 {\n"
        dotFile << "\t\tcolor=lightgrey;\n"
        dotFile << "\t\tnode [style=filled];\n"
        dotFile << "\t\tlabel = \""+label+"\";\n"

        logger.warn("Path object class: {}",path.getClass())
        logger.info("Path object contents: {}", path)
        for (int i = 0; i < path.size() - 1; i+=2) {
          def vertex1 = path[i].isVertex() ? path[i].asVertex() : null;
          logger.info("Got vertex1: {} with class {}",vertex1,vertex1.getClass())
          assertNotNull(vertex1)
          def vertex2 = path[i+2].isVertex() ? path[i+2].asVertex() : null;
          logger.info("Got vertex2: {} with class {}",vertex2,vertex2.getClass())
          assertNotNull(vertex2)
          def edge = path[i+1].isEdge() ? path[i+1].asEdge() : null;
          logger.info("Got edge: {} with class {}",edge,edge.getClass())
          assertNotNull(edge)
          dotFile << "\t\t\t\""+dotString(vertex1)+"\" -- \""+ dotString(vertex2) +"\" [label=\" "+dotString(edge)+"\"];\n"
        }

        dotFile << "\t}\n"
        dotFile << "}\n"
        logger.info("Saved dotNotationFile to {}",dotFilePath);
    }

    public static String dotString(Vertex vertex) {
        def id = vertex.getId()
        def dotString = vertex.getLabel()+":"+id.community_id+":"+id.member_id;
        if (vertex.getLabel().contains("item")) { dotString += "\nvalue: "+vertex.getProperty("value").getValue().asString()}
        logger.info("Constructed {} dotString from vertex {}",dotString,id);
        return dotString;
    }

    public static String dotString(Edge edge) {
        def id = edge.getId()
        def dotString = edge.getLabel()
        if (edge.getLabel().contains('similarity')) {
            dotString = dotString +": "+edge.getProperty('similarity').getValue().asString()
        }
        logger.info("Constructed {} dotString from edge {}",dotString,id);
        return dotString;
    }

    public static Object getMatchingOfferDemandPairs(Object offerItemPairs, Object  demandItemPairs) {
        def matchingPairs = [:]
        Map offerPairs = listToMap(offerItemPairs);
        Map demandPairs = listToMap(demandItemPairs);
        offerPairs.each { key,value -> 
          def matchingDemandPairs = demandPairs.get(key);
          if (matchingDemandPairs!=null) {
            Map matches = [:]
            matches.put("offers",[value])
            matches.put("demands",[matchingDemandPairs])
            matchingPairs.find{ it.key == key } \
              ? { matchingPairs.get(key).get('offers').add(value); matchingPairs.get(key).get('demands').add(matchingDemandPairs) } \
              : matchingPairs.put(key,matches)
          }
        }
        logger.warn("Found {} matching offer-demand pairs", matchingPairs.size())
        return matchingPairs
    }

    public static Map listToMap(List listOfItemPairs) {
        Map map = [:]
        listOfItemPairs.each { pair -> 
            def v = pair.get('v').get('value');
            def d = pair.get('d');
            map.find{it.key==v} ? map.find{it.key==v}.value.add(d) : map.put(v,d)
            logger.info("Added item {}",pair)
        }
        logger.info("Constructed map {}",map)
        return map;
    }

    public static double cosineSimilarity(String left, String right) {
      def arrayLeft = (String[]) left;
      arrayLeft = arrayLeft.collect {it.toInteger()}
      def arrayRight = (String[]) right;  
      arrayRight = arrayRight.collect {it.toInteger()}

      double dotProduct = 0.0;
      double normA = 0.0;
      double normB = 0.0;
      for (int i = 0; i < arrayLeft.size(); i++) {
          dotProduct += arrayLeft[i] * arrayRight[i];
          normA += Math.pow(arrayLeft[i], 2)+0.0000000001;
          normB += Math.pow(arrayRight[i], 2)+0.0000000001;
      }
      def similarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
      logger.info("Calculated cosine similarity between {} and {}: {}", left,right,similarity)
      return similarity
    }

    public static String createEvent(String eventType, Object object) {
      logger.info("Got a call to format event {}: {}",eventType,object)
      def json = [ eventType: eventType,
                   data: []
                 ]
      switch(eventType) {
        case "newVertex":
          def id = formatVertexLabel(object.id)
          json.data.add([
            label: object.label,
            id: id
          ])
          break;
        case "newEdge":
          def outVid = formatVertexLabel(object.outV)
          def inVid = formatVertexLabel(object.inV)
          def edgeId = formatEdgeLabel(object.id)
          json.data.add([
            label: object.label,
            id: edgeId,
            source: outVid,
            target: inVid
          ])
        break;
      }
      def result = JsonOutput.toJson(json)
      logger.info("Constructed event data structure: {}",result)
      return result;
    }

    public static String formatVertexLabel(Object nodeId) {
      def jsonSlurper = new JsonSlurper()
      logger.info("Parsing {} to json.",nodeId)
      def fieldNames = jsonSlurper.parseText(nodeId.toString());
      def vertexLabel = fieldNames.get('~label')+fieldNames.community_id+fieldNames.member_id
      logger.info("Formatted vertex {} label as {}", nodeId.toString(), vertexLabel.toString())
      return vertexLabel
    }

    public static String formatEdgeLabel(Object edgeId) {
      def jsonSlurper = new JsonSlurper()
      def fieldNames = jsonSlurper.parseText(edgeId.toString());
      def edgeLabel = fieldNames.get('~local_id');
      logger.info("Formatted vertex {} label as {}", edgeId.toString(),edgeLabel.toString())
      return edgeLabel
    }
}
