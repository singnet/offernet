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

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.json.internal.LazyMap

import org.json.JSONArray

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

    public static Double generateDouble() {
      Random randomNum = new Random();
      return randomNum.nextDouble();
    }

    public static String generateRandomString(int length) {
      String alphabet = (('A'..'N')+('P'..'Z')+('a'..'k')+('m'..'z')+('2'..'9')).join() 
      def randomString = new Random().with {
                (1..length).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
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
            chain.add(generateDouble())
            //chain.add(generateBinaryString(Global.parameters.binaryStringLength))
        }
        logger.debug("Created chain {} of length {} with: end: {}, start: {} ",chain, length, chain.last(),chain.first())
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
    private static Object edgePropertyValue(Edge edge,String propertyName) {
        logger.debug("Returning integer value of the property {} on edge {}", propertyName,edge.getId())
        //def value = edge.getProperty('similarity').getValue().asInt()
        def value = edge.getProperty('similarity').getValue().asDouble()
        return value
    }

    private static String getCurrentMethodName(){
      def marker = new Throwable()
      return StackTraceUtils.sanitize(marker).stackTrace[1].methodName
    }

    /* produces a path file for cytoscape.js -- to be visualized */
    private static Object convertToCYNotation(Object path, String keyword) {
      String simulationId = Global.parameters.simulationId
      String fileName = generateRandomString(6)+".json"
      String cyFileDir = System.getProperty("user.dir")+"/"+Global.parameters.experimentDataDir + "/" + simulationId +"/"+ keyword
      def cyFilePath = cyFileDir +"/" +fileName 

      if (Global.parameters.reportMode) {
        logger.debug('creating an experiment directory {}',cyFileDir)
        new File(cyFileDir).mkdirs();
      }

      logger.debug("Converting a {} path {} of {} to cytoscape notation",simulationId, path, path.class)
      def json = [ ]
      path.each { chain ->
        chain.each { item -> 
          logger.debug('got an item {} of class',item)
          switch(item.get('type')) {
            case "vertex":
              def vertexData = [  group: "nodes",
                                  data: [
                                    label: item.get('label'),
                                    id: formatVertexLabel(item.get('id')),
                                    description: formatVertexLabel(item.get('id'))
                                  ]
                              ]
              def js = new JsonSlurper()

              def properties = js.parseText(item.get("properties").toString())
              properties.each { property ->
                switch(property.getKey().toString()) {
                  case "value":
                    vertexData.put(property.getKey(), property.getValue())
                    vertexData.data.description = vertexData.data.description + '\n' + property.getValue().value.toString()
                    break;
                }
              }
              json.add(vertexData)
              logger.debug("Added vertex {} to cy",vertexData)
              break;
            case "edge":
              def edgeData = [  group: "edges",
                                data: [
                                  label: item.get('label'),
                                  id: formatEdgeLabel(item.get('id')),
                                  source: formatVertexLabel(item.get('outV')),
                                  target: formatVertexLabel(item.get('inV')),
                                  description: item.get('label')
                                ],
                              ]
              def js = new JsonSlurper()
              def properties;
              if (item.has("properties")) {
                properties = js.parseText(item.get("properties").toString())
                properties.each { property ->
                  logger.debug("got property {}",property)
                  switch(property.getKey().toString()) {
                    case "similarity":
                      edgeData.put(property.getKey(), property.getValue())
                      edgeData.data.description = edgeData.data.description + '\n' + property.getValue()
                      break;
                  }
                }
              }
            json.add(edgeData)
            logger.debug("Added edge {} to cy",edgeData)
            break;
          }
        }
      }

      def sortedJSON = json.sort { a,b -> b.group <=> a.group}
      if (Global.parameters.reportMode) {
        new File(cyFilePath).write(JsonOutput.toJson(json))
        logger.debug('Wrote path to file {}', cyFilePath)
      }
      return JsonOutput.toJson(json)
    }

    public static String dotString(Vertex vertex) {
        def id = vertex.getId()
        def dotString = vertex.getLabel()+":"+id.community_id+":"+id.member_id;
        if (vertex.getLabel().contains("item")) { dotString += "\nvalue: "+vertex.getProperty("value").getValue().asString()}
        logger.debug("Constructed {} dotString from vertex {}",dotString,id);
        return dotString;
    }

    public static String dotString(Edge edge) {
        def id = edge.getId()
        def dotString = edge.getLabel()
        if (edge.getLabel().contains('similarity')) {
            dotString = dotString +": "+edge.getProperty('similarity').getValue().asString()
        }
        logger.debug("Constructed {} dotString from edge {}",dotString,id);
        return dotString;
    }

    public static Map listToMap(List listOfItemPairs) {
        Map map = [:]
        listOfItemPairs.each { pair -> 
            def v = pair.get('v').get('value');
            def d = pair.get('d');
            map.find{it.key==v} ? map.find{it.key==v}.value.add(d) : map.put(v,d)
            logger.debug("Added item {}",pair)
        }
        logger.debug("Constructed map {}",map)
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
      logger.debug("Calculated cosine similarity between {} and {}: {}", left,right,similarity)
      return similarity
    }

    public static String createEvent(String eventType, Object object) {
      logger.debug("Got a call to format event {}: {}",eventType,object)
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
      logger.debug("Constructed event data structure: {}",result)
      return result;
    }

    public static String formatVertexLabel(String nodeId) {
      return nodeId
    }

    public static String formatVertexLabel(LazyMap nodeId) {
      def vertexLabel = nodeId.get('~label')+":"+nodeId.get('community_id')+":"+nodeId.get('member_id')
      logger.debug("Formatted vertex {} label as {}", nodeId.toString(), vertexLabel.toString())
      return vertexLabel
    }

    public static String formatVertexLabel(Object nodeId) {
      def jsonSlurper = new JsonSlurper()
      logger.debug("Parsing {} to json.",nodeId)
      def fieldNames = jsonSlurper.parseText(nodeId.toString());
      def vertexLabel = fieldNames.get('~label')+":"+fieldNames.community_id+":"+fieldNames.member_id
      logger.debug("Formatted vertex {} label as {}", nodeId.toString(), vertexLabel.toString())
      return vertexLabel.toString()
    }

    public static String formatEdgeLabel(LazyMap edgeId) {
      def edgeLabel = edgeId.get('~local_id');
      logger.debug("Formatted edge {} label as {}", edgeId.toString(), edgeLabel.toString())
      return edgeLabel
    }

    public static String formatEdgeLabel(Object edgeId) {
      def jsonSlurper = new JsonSlurper()
      def fieldNames = jsonSlurper.parseText(edgeId.toString());
      def edgeLabel = fieldNames.get('~local_id');
      logger.debug("Formatted edge {} label as {}", edgeId.toString(),edgeLabel.toString())
      return edgeLabel
    }

    public static Object vertexToJson(Vertex vertex) {
      def properties = [:]
      logger.debug('converting to Json: {}', vertex)
      logger.debug("vertex.getPropertyNames().toArray() {}",vertex.getPropertyNames().toArray().getClass())
      vertex.getPropertyNames().each{ propertyName ->
        properties.put(propertyName,vertex.getProperty(propertyName).getValue().asString())
      }
      def mapVertex = [ id: formatVertexLabel(vertex.getId()),
                         label: vertex.getLabel(),
                         type: 'vertex',
                         properties: properties
                        ]
      logger.debug('mapVertex is {}',mapVertex)
      def builder = new groovy.json.JsonBuilder()
      def root = builder 

      JSONArray jsonVertex = new JSONArray()
      jsonVertex.put(mapVertex)
      logger.debug('jsonVertex is {}',jsonVertex)          
      return jsonVertex
    }

    public static boolean pathContainsChain(Object uniquePathJson, Object chainedWorksJson) {
      def start = System.currentTimeMillis();
      logger.debug('checking if path contains the chain...')
      logger.debug('uniquePathJson is {}',uniquePathJson)
      logger.debug('chainedWorksJson is {}', chainedWorksJson)
      // a chain is actually a list of works which are related via similar {offer,demands} items
      // we check by if a path contains it by looking if the path contains all works listed in the chain;
      def sequenceInChain = 0;
      def allMatches = [];
      chainedWorksJson.each { chainedWork ->
        sequenceInChain +=1;
        logger.debug("checking if chainedWork {} of sequence {} exists in the path",chainedWork, sequenceInChain);
        // uniquePaths are formatted as a list of lists of {edges and vertexes on both end} of the path
        // so we iterate the path each time and find a respective work and then check if the items it holds are the ones 
        // indicated in the chain 
        def matches = 0;
        uniquePathJson.each { singleEdge -> 
          logger.debug('looking if singleEdge {} contains pathWorkId {}', singleEdge, chainedWork.work)
          def pathedWorks = singleEdge.findAll{it.id==chainedWork.work}
          pathedWorks.each { pathedWork ->
            logger.debug("found pathedWork {} in on singleEdge {}", pathedWork.id, singleEdge)
            def pathedItem = singleEdge.findAll{it.label=='item'}
            logger.debug('item of the edge is {}',pathedItem)
            def pathedItemId = pathedItem.id[0]
            logger.debug('having Id {}',pathedItemId)
            def pathedEdgeLabel = singleEdge.findAll{it.type=='edge'}.label[0]
            logger.debug('with pathedEdgeLabel {}',pathedEdgeLabel)

            boolean itemIdsMatch = chainedWork.get("$pathedEdgeLabel").item == pathedItemId
            if (itemIdsMatch) {
              logger.debug('pathedWork {} item {} exists in chainedWork {}',pathedWork, pathedItemId, chainedWork)
            } else {
              logger.debug('pathedWork {} item {} does NOT exist in chainedWork {}',pathedWork, pathedItemId, chainedWork)
            }
            matches = matches + (itemIdsMatch ? 1:0)
          }
          logger.debug('singleEdge of sequence {} has {} matches in the path', sequenceInChain,matches)
        }
        allMatches.add(matches)
      }
      logger.debug('allMatches follow a pattern: {}',allMatches) 

      // checking if the matching pattern is correct:
      // first, the number of matches in the list should be the same as the number of chained works
      boolean size = chainedWorksJson.size() == allMatches.size()
      logger.debug('{} that chainedWorksJson.size() == allMatches.size()', size, chainedWorksJson.size(), allMatches.size())
      // then, the first item in the list should contain at least one matching item:
      boolean firstItem = allMatches.take(1)[0] <= 2
      logger.debug('{} that firstItem {}  <= 2', firstItem, allMatches.take(1)[0])
      allMatches = allMatches.drop(1) // we do not need this item any more
      // then, the last item in the list should contain at least one matching item:
      boolean lastItem = allMatches.pop() <= 2
      logger.debug('{} that lastItem {} <= 2', lastItem, allMatches.take(1)[0])
      // finally, all remaining items in the list should be equal to 2..:
      boolean innerItems = true
      allMatches.collect {
        boolean innerItem = it == 2;
        innerItems = innerItems & innerItem
        logger.debug("{} that innerItem {} ==2", innerItem, it)
      }
      logger.debug('{} that innerItems {} are all equal to 2', innerItems, allMatches)

      // a path contains chain if all these conditions are true:

      def pathContainsChain = size & firstItem & lastItem & innerItems
      logger.debug('{} that path {} contains chain {}',pathContainsChain,uniquePathJson,chainedWorksJson)
      logger.info('method={} : simulationId={} :  result={}: path={} : chain={} : wallTime_ms={} msec.', 
          'pathContainsChain', 
          Global.parameters.simulationId,
          pathContainsChain,
          uniquePathJson,
          chainedWorksJson,
          (System.currentTimeMillis()-start))
      return pathContainsChain
    }

    public static Object calculateSimilarity(Vertex itemOne, Vertex itemTwo) {
        return Utils.kabirSimilarity(itemOne.getProperty("value").getValue().asDouble(), itemTwo.getProperty("value").getValue().asDouble())
//      return Utils.cosineSimilarity(itemOne.getProperty("value").getValue().asString(), itemTwo.getProperty("value").getValue().asString())
    }

    public static Object kabirSimilarity(Double valueOne, Double valueTwo) {
        return 1-Math.abs(valueOne - valueTwo)
    }
}
