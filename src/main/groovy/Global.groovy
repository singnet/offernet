package io.singularitynet.offernet

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Global {
	/*
	 * Following two lines are hardcoded absolute path and filename of the configuration file
	 * It is possible to make searching relative to source folder, yet I am still not sure which way is better....
	 * There is one possible threat for this, explained somewhere here:
	 * http://stackoverflow.com/questions/4646577/global-variables-in-java
	 * (garbage collector may unload the class and then all the variables will be lost...)
	 *
	 */

	// read global parameters from the config file

	static ClassLoader classloader = Thread.currentThread().getContextClassLoader()
	public static Map dummyParameters = [:]
	// containter for parameters to pass around globall - good for testing purposes (and not only)
	static tempConfigFileName = System.getProperty('user.dir')+"/configs/offernet.conf"
	public static Map parameters = new ConfigSlurper('config').parse(new File(tempConfigFileName).toURI().toURL())
	public static Map simulationParameters = new HashMap<>()

}
