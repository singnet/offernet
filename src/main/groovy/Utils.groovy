package net.vveitas.offernet

import groovy.json.*

import com.datastax.driver.dse.graph.GraphResultSet

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.datastax.driver.dse.graph.Edge

import org.codehaus.groovy.runtime.StackTraceUtils

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
            chain.add(generateBinaryString(Parameters.parameters.binaryStringLength))
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

    public static Integer calculateSimilarity(Item itemOne, Item itemTwo) {
  		return Utils.veitasSimilarity(itemOne.getValue(), itemTwo.getValue())
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


    private static Integer edgePropertyValueAsInteger(Edge edge,String propertyName) {
        logger.warn("Returning integer value of the property {} on edge {}", propertyName,edge.getId())
        def intValue = edge.getProperty('similarity').getValue().asInt()
        return intValue
    }

    private static String getCurrentMethodName(){
      def marker = new Throwable()
      return StackTraceUtils.sanitize(marker).stackTrace[1].methodName
    }
}
