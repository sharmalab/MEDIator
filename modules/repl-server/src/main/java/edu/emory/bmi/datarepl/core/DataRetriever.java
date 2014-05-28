/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.infinispan.InfDataAccessIntegration;
import edu.emory.bmi.datarepl.mashape.TciaInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) {
        InfDataAccessIntegration.getInfiniCore();

        LogInInitiator logInInitiator = new LogInInitiator();
        long userId = 1001;
        logInInitiator.login(userId);

        TciaInvoker tciaInvoker = logInInitiator.getTciaInvoker();

        try {
            tciaInvoker.getImage("1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440");
        } catch (UnirestException e) {
            logger.error("Failed invoking the request", e);
        }
    }
}
