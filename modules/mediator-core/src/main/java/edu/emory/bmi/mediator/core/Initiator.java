/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.core;

import edu.emory.bmi.mediator.integrator.ReplicaSetsIntegrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiates an Infinispan instance and keeps it connected to the cluster.
 */
public class Initiator {
    private static Logger logger = LogManager.getLogger(Initiator.class.getName());

    public static void main(String[] args) {
        @SuppressWarnings({"UnusedDeclaration"})
        ReplicaSetsIntegrator replicaSetsIntegrator = ReplicaSetsIntegrator.getInfiniCore();
        logger.info("Infinispan Initiator instance started..");
    }
}
