package io.singularitynet.offernet

import groovy.json.*

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.codehaus.groovy.runtime.StackTraceUtils

import static org.junit.Assert.*

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.json.internal.LazyMap

import org.json.JSONArray

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.apache.tinkerpop.gremlin.structure.io.IoCore

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*
import static org.apache.tinkerpop.gremlin.process.traversal.Pop.*
import static org.apache.tinkerpop.gremlin.process.traversal.Scope.*
import static org.apache.tinkerpop.gremlin.process.traversal.Compare.eq;

public class InMemoryUtils {
    static Logger logger = LoggerFactory.getLogger('Utils.class');

    public static GraphTraversalSource importGraphML(String file) {
      final Graph aGraph = TinkerGraph.open();
      aGraph.io(IoCore.graphml()).readGraph(file);

      GraphTraversalSource g = aGraph.traversal()//.withComputer()
    }

    public static List getAllVertices(GraphTraversalSource g, String vertexType) {
      List allVertices = g.V().has('type',vertexType).toList()
      logger.debug('Returned list of vertices: {}',allVertices)
      return allVertices

    }

    public static List getAllVertices(GraphTraversalSource g) {
      List allVertices = g.V().toList()
      logger.debug('Returned list of all vertices: {}',allVertices)
      return allVertices
    }

    public static Integer getDiameter(GraphTraversalSource g, List allVertices, String... edgeLabel) {
      def diameter = 0;
      allVertices.size().times() {
        def vertex =  allVertices.pop()
        allVertices.each { anotherVertex ->
            def sp = []
            if (edgeLabel) {
              sp = shortestPath(g,vertex,anotherVertex,edgeLabel);
            } else {
              sp = shortestPath(g,vertex,anotherVertex);
            }

            diameter = diameter < sp ? sp : diameter
        }
      }
      logger.debug('diameter of the graph is {}', diameter)
      return diameter
    }

    public static Integer shortestPath(GraphTraversalSource g, Vertex vertexOne, Vertex vertexTwo,String... edgeLabel) {
      List shortestPath;
      if (edgeLabel) {
        shortestPath = g.V(vertexOne.id()).repeat(bothE(edgeLabel).otherV().simplePath()).until(is(vertexTwo)).path().toList()
      } else {
        shortestPath = g.V(vertexOne.id()).repeat(bothE().otherV().simplePath()).until(is(vertexTwo)).path().toList()
      }  
      logger.debug('shortestPath between {} and {} is {}',vertexOne, vertexTwo, shortestPath)
      def lengthOfShortestPath = (shortestPath.get(0).size()-1)/2 
      logger.debug('lengthOfShortestPath between {} and {} is {}',vertexOne, vertexTwo, lengthOfShortestPath)
      return lengthOfShortestPath
    }

    public static Map degreeCentrality(GraphTraversalSource g, String vertexType, String edgeLabel) {
      def degreeCentrality = g.withComputer().V().has('type',vertexType).groupCount().by(bothE(edgeLabel).count()).next()
      logger.debug('degreeCentrality map of the {} -> {} subgraph is {}.',vertexType,edgeLabel,degreeCentrality)
      return degreeCentrality
    }

    public static Map betweenessCentrality(GraphTraversalSource g, String vertexType, String edgeLabel) {
      def betweennessCentrality = g.withComputer().V().has('type',vertexType).as("v"). //1
           repeat(bothE(edgeLabel).has('type',vertexType).simplePath().as("v")).emit(). //2\
           filter(project("x","y","z").by(select(first, "v")). //3\
                                       by(select(last, "v")).
                                       by(select(all, "v").count(local)).as("triple").
                  coalesce(select("x","y").as("a"). //4\
                             select("triples").unfold().as("t").
                             select("x","y").where(eq("a")).
                             select("t"),
                           store("triples")). //5\
                  select("z").as("length").
                  select("triple").select("z").where(eq("length"))). //6\
           select(all, "v").unfold(). //7\
           groupCount().next()
      logger.debug('betweenessCentrality of the {} -> {} subgraph is {}.',vertexType,edgeLabel,betweennessCentrality)
    }
}
