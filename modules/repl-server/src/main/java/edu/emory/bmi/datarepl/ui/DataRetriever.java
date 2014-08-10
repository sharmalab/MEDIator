/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ui;

import edu.emory.bmi.datarepl.container.TomcatEmbeddedRunner;
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import edu.emory.bmi.datarepl.tcia.TciaLogInInitiator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import org.apache.catalina.LifecycleException;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());
    private static TciaInvoker tciaInvoker;


    public static void main(String[] args) throws FileNotFoundException, LifecycleException {
        TciaLogInInitiator logInInitiator = new TciaLogInInitiator();
        logInInitiator.init();

        tciaInvoker = logInInitiator.getTciaInvoker();
        logger.info("Starting Tomcat ..");
        new TomcatEmbeddedRunner().startServer();
    }

    public static TciaInvoker getTciaInvoker() {
        return tciaInvoker;
    }

}
