package org.globalbraininstitute.offernet
import com.tinkerpop.pipes.Pipe
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.pipes.sideeffect.SideEffectFunctionPipe
import com.tinkerpop.pipes.transform.TransformFunctionPipe
import com.tinkerpop.pipes.util.Pipeline

import org.globalbraininstitute.challprop.frames.DataItem
import org.globalbraininstitute.challprop.frames.FramedAgent
import org.globalbraininstitute.challprop.frames.FramedProcess
import org.globalbraininstitute.challprop.global.Globals
import org.globalbraininstitute.challprop.global.Network;
import org.globalbraininstitute.challprop.global.Parameters
import org.globalbraininstitute.challprop.utils.Utils
import org.globalbraininstitute.challprop.functions.GetAgentsDataItems
import org.globalbraininstitute.challprop.functions.IterateDataItemList

import groovy.util.logging.Log4j

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

//loading log4j properties
def config = new ConfigSlurper().parse(new File('configs/log4j-searchSimilarities.groovy').toURL())
PropertyConfigurator.configure(config.toProperties())
Logger logger = LoggerFactory.getLogger('scripts/searchSimilarities.groovy');

def framedAgentsList = Globals.network.g.getVertices("type", "agent", FramedAgent.class)
logger.info("Got {} agents in the network", framedAgentsList.size())
Pipe getAgentsDataItems = new TransformFunctionPipe(new GetAgentsDataItems())
Pipe iterateDataItemsList = new SideEffectFunctionPipe(new IterateDataItemList())
Pipeline pipeline = new Pipeline(getAgentsDataItems,iterateDataItemsList)
pipeline.setStarts(framedAgentsList)
while (pipeline.hasNext()) {
    pipeline.next()
}


Globals.network.commit()