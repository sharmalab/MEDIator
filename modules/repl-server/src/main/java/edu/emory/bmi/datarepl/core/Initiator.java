/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiates an Infinispan instance and keeps it connected to the cluster.
 */
public class Initiator {
    private static Logger logger = LogManager.getLogger(Initiator.class.getName());

    public static void main(String[] args) {
        InfDataAccessIntegration infDataAccessIntegration = InfDataAccessIntegration.getInfiniCore();
        logger.info("Infinispan Initiator instance started..");
    }
}
