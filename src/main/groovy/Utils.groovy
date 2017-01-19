package net.vveitas.offernet

import groovy.json.*

import com.datastax.driver.dse.graph.GraphResultSet

public class Utils {

    public static String generateBinaryString(int length) {
        String randomString="";
        for (int i=1;i<length;i++) {
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

    public List createChain(int length) {
        List chain = []
        lenght.times {
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


}
