/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_impl;

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

        CSVParser.parseCSV(DataSourcesConstants.META_CSV_FILE, DataSourcesConstants.CSV_META_POSITION,
                DataSourcesConstants.CSV_META_INDEX, DataSourcesConstants.CSV_SPLIT_BY);

        CSVParser.parseCSV(DataSourcesConstants.S3_META_CSV_FILE, DataSourcesConstants.S3_META_POSITION,
                DataSourcesConstants.S3_META_INDEX, DataSourcesConstants.S3_SPLIT_BY);
    }
}
