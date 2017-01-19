package net.vveitas.offernet

import org.apache.log4j.PropertyConfigurator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.junit.Assert.assertNotNull

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class Tests {
		private OfferNet on = new OfferNet();

		/*
		* Item.groovy
		*/

		@Test
		void createItemTest() {
				def item = new Item(on.session);
				assertNotNull(item)
				println "createdItem: "+item;
		}

		@Test
		void getItemIdTest() {
			def id = new Item(on.session).id();
			assertNotNull(id);
			println "got Item.id: "+id;
		}

		@Test
		void getItemPropertiesTest() {
			def properties = new Item(on.session).getProperties();
			assertNotNull(properties);
			println "got item properties: "+properties;
		}

		@Test
		void getItemValueTest() {
			def value = new Item(on.session).getValue();
			assertNotNull(value);
			println "got item value: "+value;
		}


		/*
		* Agent.groovy
		*/

		@Test
    void createAgentTest() {
        def agent1 = new Agent(on.session);
        assertNotNull(agent1);
    }

		@Test
		void knowsAgentTest() {
        def agent1 = new Agent(on.session);
        assertNotNull(agent1);
        def agent2 = new Agent(on.session);
        assertNotNull(agent2);
        def edge = agent1.knowsAgent(agent2);
        assertNotNull(edge);
    }

		/*
		* Work.groovy
		*/

		@Test
    void createWorkTest() {
        def work = new Work(on.session);
        assertNotNull(work);
    }

		@Test
		void ownsNewWorkTest() {
        def agent1 = new Agent(on.session);
        assertNotNull(agent1);

        def work = agent1.ownsWork();
        assertNotNull(work);
    }

		@Test
    void ownsKnownWorkTest() {
        def agent1 = new Agent(on.session);
        assertNotNull(agent1);

        def work = new Work(on.session);
        assertNotNull(work);

        def edge = agent1.ownsWork(work);
        assertNotNull(edge);
    }

		@Test
		void addDemandTest() {
        // not implemented
    }
}
