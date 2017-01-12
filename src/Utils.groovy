static class Utils {


    public static String generateBinaryString(int length) {
        String randomString="";
        for (var i=1;i<lenght;i++) {
            Random randomNum = new Random();
            randomString = randomString.concat(randomNum.nextInt(2));
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

	
}