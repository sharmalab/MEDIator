/*
 * Title:        Cloud2Sim
 * Description:  Distributed and Concurrent Cloud Simulation
 *                Toolkit for Modeling and Simulation
 *                of Clouds - Enhanced version of CloudSim.
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.infinispan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.Cache;

public class Initiator {
    private static Logger logger = LogManager.getLogger(Initiator.class.getName());

    public static void main(String[] args) {
        InfDataAccessIntegration infDataAccessIntegration = InfDataAccessIntegration.getInfiniCore();
        logger.info("Infinispan Initiator instance started..");
    }
}
