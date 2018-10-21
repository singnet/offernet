/*
Copyright (c) 2018 SingularityNET

Distributed under the MIT software license, see LICENSE file
*/

package io.singularitynet.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource

public class InMemoryUtilsTests {
    static private Logger logger;

	@BeforeClass
	static void initLogging() {
	    def config = new ConfigSlurper().parse(new File('configs/log4j-properties.groovy').toURL())
		PropertyConfigurator.configure(config.toProperties())
		logger = LoggerFactory.getLogger('SimpleTests.class');
	}

	@Ignore
	@Test 
	void getGraphVertices() {
		String experimentDataDir = "/home/kabir/offernet/analysis/experimentData"
		String experimentId = "EXP10-13-12-56-ZEvsxw"
		String simulationId = "SIM10-13-12-56-bmib3J--DV"
		String graphFileName = "graph.graphml"
		String graphPath = experimentDataDir + "/" + 
						   experimentId + "/" + 
						   simulationId + "/" +
						   graphFileName
		GraphTraversalSource g = InMemoryUtils.importGraphML(graphPath);
		List allVertices = InMemoryUtils.getAllShortestPaths(g, 'agent', 'knows')
		logger.debug('Returned list of vertices: {}',allVertices)
	}

	@Ignore
	@Test 
	void getDiameterTestAgents() {
		String experimentDataDir = "/home/kabir/offernet/analysis/experimentData"
		String experimentId = "EXP10-13-12-56-ZEvsxw"
		String simulationId = "SIM10-13-12-56-bmib3J--DV"
		String graphFileName = "graph.graphml"
		String graphPath = experimentDataDir + "/" + 
						   experimentId + "/" + 
						   simulationId + "/" +
						   graphFileName
		GraphTraversalSource g = InMemoryUtils.importGraphML(graphPath);
		List allVertices = InMemoryUtils.getAllVertices(g, 'agent')
		def diameter = InMemoryUtils.getDiameter(g,allVertices,'knows')
	}

	@Ignore
	@Test 
	void getDiameterTestAll() {
		String experimentDataDir = "/home/kabir/offernet/analysis/experimentData"
		String experimentId = "EXP10-13-12-56-ZEvsxw"
		String simulationId = "SIM10-13-12-56-bmib3J--DV"
		String graphFileName = "graph.graphml"
		String graphPath = experimentDataDir + "/" + 
						   experimentId + "/" + 
						   simulationId + "/" +
						   graphFileName
		GraphTraversalSource g = InMemoryUtils.importGraphML(graphPath);
		List allVertices = InMemoryUtils.getAllVertices(g)
		def diameter = InMemoryUtils.getDiameter(g,allVertices)
	}

	@Ignore
	@Test 
	void getDegreeCentralityTest() {
		String experimentDataDir = "/home/kabir/offernet/analysis/experimentData"
		String experimentId = "EXP10-13-12-56-ZEvsxw"
		String simulationId = "SIM10-13-12-56-bmib3J--DV"
		String graphFileName = "graph.graphml"
		String graphPath = experimentDataDir + "/" + 
						   experimentId + "/" + 
						   simulationId + "/" +
						   graphFileName
		GraphTraversalSource g = InMemoryUtils.importGraphML(graphPath);
		Map getDegreeCentralityAgent = InMemoryUtils.degreeCentrality(g,'agent','knows')
		Map getDegreeCentralityItem = InMemoryUtils.degreeCentrality(g,'item','similarity')
		Map getDegreeCentralityAgentOwnsWork = InMemoryUtils.degreeCentrality(g,'agent','owns')
		Map getDegreeCentralityWorkDemands = InMemoryUtils.degreeCentrality(g,'work','demands')
		Map getDegreeCentralityWorkOffers = InMemoryUtils.degreeCentrality(g,'work','offers')
	}

	@Ignore
	@Test 
	void betweenessCentralityTest() {
		String experimentDataDir = "/home/kabir/offernet/analysis/experimentData"
		String experimentId = "EXP10-13-12-56-ZEvsxw"
		String simulationId = "SIM10-13-12-56-bmib3J--DV"
		String graphFileName = "graph.graphml"
		String graphPath = experimentDataDir + "/" + 
						   experimentId + "/" + 
						   simulationId + "/" +
						   graphFileName
		GraphTraversalSource g = InMemoryUtils.importGraphML(graphPath);
		Map betweenessCentraliztyAgent = InMemoryUtils.betweenessCentrality(g,'agent','knows')
	}
}