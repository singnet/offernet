package net.vveitas.offernet

import groovy.json.*

import com.datastax.driver.dse.graph.GraphResultSet

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.datastax.driver.dse.graph.Edge

public class Utils {
    static Logger logger = LoggerFactory.getLogger('Utils.class');

    public static String generateBinaryString(int length) {
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
            chain.add(generateBinaryString(16))
        }
        logger.info("Created chain of length with: { end: {}, start: {} }",chain.last(),chain.first())
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

    public static Integer calculateDistance(Item itemOne, Item itemTwo) {
  		return Utils.hammingDistance(itemOne.getValue(), itemTwo.getValue())
  	}

    public static Integer hammingDistance(CharSequence left, CharSequence right) {
  		/*
  		 * Licensed to the Apache Software Foundation (ASF) under one or more
  		 * contributor license agreements.  See the NOTICE file distributed with
  		 * this work for additional information regarding copyright ownership.
  		 * The ASF licenses this file to You under the Apache License, Version 2.0
  		 * (the "License"); you may not use this file except in compliance with
  		 * the License.  You may obtain a copy of the License at
  		 *
  		 *      http://www.apache.org/licenses/LICENSE-2.0
  		 *
  		 * Unless required by applicable law or agreed to in writing, software
  		 * distributed under the License is distributed on an "AS IS" BASIS,
  		 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  		 * See the License for the specific language governing permissions and
  		 * limitations under the License.
  		 */

  		// taken from https://commons.apache.org/sandbox/commons-text/jacoco/org.apache.commons.text.similarity/HammingDistance.java.html

  		if (left == null || right == null) {
  			throw new IllegalArgumentException("Strings must not be null");
  		}

  		if (left.length() != right.length()) {
  			throw new IllegalArgumentException("Strings must have the same length");
  		}

  		int distance = 0;

  		for (int i = 0; i < left.length(); i++) {
  			if (left.charAt(i) != right.charAt(i)) {
  				distance++;
  			}
  		}
      logger.info("Calculated humming distance between {} and {}: {}", left,right,distance)
  		return distance;
  	}


    private static Integer edgePropertyValueAsInteger(Edge edge,String propertyName) {
        logger.warn("Returning integer value of the property {} on edge {}", propertyName,edge.getId())
        def intValue = ((String) edge.getProperty(propertyName).getValue()).replace("\"", "").toInteger()
        return intValue
    }
}
