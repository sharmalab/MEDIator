/*
 * Title:        Cloud2Sim
 * Description:  Distributed and Concurrent Cloud Simulation
 *                Toolkit for Modeling and Simulation
 *                of Clouds - Enhanced version of CloudSim.
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.csv;

import edu.emory.bmi.datarepl.core.InfDataAccessIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiates Infinispan instance with the meta data read from the csv file.
 */
public class CSVInitiator {
    private static Logger logger = LogManager.getLogger(CSVInitiator.class.getName());

    public static void main(String[] args) {
        InfDataAccessIntegration infDataAccessIntegration = CSVInfDai.getInfiniCore();
        logger.info("Infinispan Initiator instance started..");

        Reader.readCSV();
    }

}
