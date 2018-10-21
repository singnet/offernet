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
