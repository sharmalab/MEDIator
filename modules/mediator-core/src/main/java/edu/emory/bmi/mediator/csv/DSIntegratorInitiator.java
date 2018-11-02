/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.csv;

import edu.emory.bmi.mediator.integrator.ReplicaSetsIntegrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiates Infinispan instance with the meta data read from the csv file.
 */
public class DSIntegratorInitiator {
    private static Logger logger = LogManager.getLogger(DSIntegratorInitiator.class.getName());

    public static void main(String[] args) {
        @SuppressWarnings({"UnusedDeclaration"})
        ReplicaSetsIntegrator replicaSetsIntegrator = ReplicaSetsIntegrator.getInfiniCore();
        logger.info("Infinispan Initiator instance started..");

        MetaDataLoader.parseCSV(edu.emory.bmi.mediator.constants.DataSourcesConstants.META_CSV_FILE,
                edu.emory.bmi.mediator.constants.DataSourcesConstants.CSV_META_POSITION,
                edu.emory.bmi.mediator.constants.DataSourcesConstants.CSV_META_INDEX,
                edu.emory.bmi.mediator.constants.DataSourcesConstants.CSV_SPLIT_BY);

        MetaDataLoader.parseCSV(edu.emory.bmi.mediator.constants.DataSourcesConstants.S3_META_CSV_FILE,
                edu.emory.bmi.mediator.constants.DataSourcesConstants.S3_META_POSITION,
                edu.emory.bmi.mediator.constants.DataSourcesConstants.S3_META_INDEX,
                edu.emory.bmi.mediator.constants.DataSourcesConstants.S3_SPLIT_BY);
    }
}
