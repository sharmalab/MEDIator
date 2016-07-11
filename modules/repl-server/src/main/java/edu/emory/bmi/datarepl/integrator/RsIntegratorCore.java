/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.integrator;

import edu.emory.bmi.datarepl.constants.DataSourcesConstants;
import edu.emory.bmi.datarepl.rs_mgmt.CsvReplicaSetHandler;
import edu.emory.bmi.datarepl.rs_mgmt.S3ReplicaSetHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Integrate with the data sources
 */
public abstract class RsIntegratorCore implements IIntegrator {
    private static Logger logger = LogManager.getLogger(RsIntegratorCore.class.getName());


    /**
     * Does the data source exists.
     *
     * @param key,            patientId
     * @param metaArrayIndex, index in the meta array
     * @return if exists.
     */
    public static boolean doesExistInDataSource(String key, int metaArrayIndex) {
        if (ReplicaSetsIntegrator.getMetaMap().get(key) == null) {
            return false;
        } else {
            Boolean[] existence = ReplicaSetsIntegrator.getMetaMap().get(key);
            return existence[metaArrayIndex];
        }
    }

    /**
     * Update meta data
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     * @param meta,      location in the meta array
     */
    public static void updateMetaData(String key, String[] metaArray, int meta) {
        if (meta == DataSourcesConstants.CSV_META_POSITION) {
            CsvReplicaSetHandler.updateMetaData(key, metaArray);
        } else if (meta == DataSourcesConstants.S3_META_POSITION) {
            S3ReplicaSetHandler.updateMetaData(key, metaArray);
        }
    }

    /**
     * Get patient studies
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        return null;
    }

    /**
     * Updating the existing data source
     *
     * @param key,            patientID
     * @param metaArrayIndex, index in the meta array
     * @param existence,      if exists
     */
    public static void updateExistenceInDataSource(String key, int metaArrayIndex, boolean existence) {
        Boolean[] newEntry = {false, false, false, false};
        newEntry[metaArrayIndex] = existence;

        if (ReplicaSetsIntegrator.getMetaMap().get(key) == null) {
            ReplicaSetsIntegrator.getMetaMap().put(key, newEntry);
            logger.info("Adding new entry to the meta map.." + key);
        } else {
            Boolean[] existingEntry = ReplicaSetsIntegrator.getMetaMap().get(key);
            existingEntry[metaArrayIndex] = existence;
            ReplicaSetsIntegrator.getMetaMap().put(key, existingEntry);
            logger.info("Existing entry is updated in the map.." + key);

        }
    }
}
