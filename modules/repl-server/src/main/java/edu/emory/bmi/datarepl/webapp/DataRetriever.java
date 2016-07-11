/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.webapp;

import edu.emory.bmi.datarepl.ds_mgmt.TciaDSManager;
import edu.emory.bmi.datarepl.tcia.TciaInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import org.apache.catalina.LifecycleException;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());
    private static TciaDSManager tciaDSManager;


    public static void main(String[] args) throws FileNotFoundException, LifecycleException {
        TciaInitializer logInInitiator = new TciaInitializer();
        logInInitiator.init();

        tciaDSManager = logInInitiator.getTciaDSManager();
        logger.info("Starting Tomcat ..");
        new TomcatEmbeddedRunner().startServer();
    }

    public static TciaDSManager getTciaDSManager() {
        return tciaDSManager;
    }

}
