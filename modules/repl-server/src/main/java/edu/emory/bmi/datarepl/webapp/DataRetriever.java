/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.webapp;

import edu.emory.bmi.datarepl.core.TciaInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import org.apache.catalina.LifecycleException;

/**
 * The core class of MEDIator webapp based on Embedded Tomcat.
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) throws FileNotFoundException, LifecycleException {
        TciaInitializer tciaInitializer = new TciaInitializer();
        tciaInitializer.init();
        logger.info("Starting Tomcat ..");
        new TomcatEmbeddedRunner().startServer();
    }
}
