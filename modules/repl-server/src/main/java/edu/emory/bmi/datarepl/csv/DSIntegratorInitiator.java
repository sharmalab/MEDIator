/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.csv;

import edu.emory.bmi.datarepl.integrator.ReplicaSetsIntegrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiates Infinispan instance with the meta data read from the csv file.
 */
public class DSIntegratorInitiator {
    private static Logger logger = LogManager.getLogger(DSIntegratorInitiator.class.getName());

    public static void main(String[] args) {
        ReplicaSetsIntegrator replicaSetsIntegrator = ReplicaSetsIntegrator.getInfiniCore();
        logger.info("Infinispan Initiator instance started..");

        MetaDataLoader.parseCSV(edu.emory.bmi.datarepl.constants.DataSourcesConstants.META_CSV_FILE, edu.emory.bmi.datarepl.constants.DataSourcesConstants.CSV_META_POSITION,
                edu.emory.bmi.datarepl.constants.DataSourcesConstants.CSV_META_INDEX, edu.emory.bmi.datarepl.constants.DataSourcesConstants.CSV_SPLIT_BY);

        MetaDataLoader.parseCSV(edu.emory.bmi.datarepl.constants.DataSourcesConstants.S3_META_CSV_FILE, edu.emory.bmi.datarepl.constants.DataSourcesConstants.S3_META_POSITION,
                edu.emory.bmi.datarepl.constants.DataSourcesConstants.S3_META_INDEX, edu.emory.bmi.datarepl.constants.DataSourcesConstants.S3_SPLIT_BY);
    }
}
