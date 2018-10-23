/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.rs_mgmt;

import edu.emory.bmi.mediator.constants.DataSourcesConstants;
import edu.emory.bmi.mediator.integrator.RsIntegratorCore;
import edu.emory.bmi.mediator.integrator.ReplicaSetsIntegrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Replica sets management for meta data from CSV files.
 */
public class CsvReplicaSetHandler {
    private static Logger logger = LogManager.getLogger(CsvReplicaSetHandler.class.getName());

    /**
     * Update meta data with CSV entry
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaData(String key, String[] metaArray) {
        if (!key.contains(DataSourcesConstants.NA)) {
            ReplicaSetsIntegrator.getCsvMetaMap().put(key, metaArray);
            RsIntegratorCore.updateExistenceInDataSource(key,
                    DataSourcesConstants.CSV_META_POSITION, true);
        }
    }

    /**
     * /GET Get meta data
     *
     * @param id, id of the patient
     */
    public static String[] getMetaData(String id) {
        if (RsIntegratorCore.doesExistInDataSource(id, DataSourcesConstants.CSV_META_POSITION)) {
            return ReplicaSetsIntegrator.getCsvMetaMap().get(id);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
            return null;
        }
    }

    /**
     * /PUT Create meta data
     *
     * @param key,      id of the meta data
     * @param metaArray, object value
     */
    public static void putMetaData(String key, String metaArray[]) {
        updateMetaData(key, metaArray);
        RsIntegratorCore.updateExistenceInDataSource(key, DataSourcesConstants.CSV_META_POSITION, true);
    }

    /**
     * /DELETE Delete meta data
     *
     * @param id, id of the patient
     */
    public static void deleteMetaData(String id) {
        if (RsIntegratorCore.doesExistInDataSource(id, DataSourcesConstants.CSV_META_POSITION)) {
            ReplicaSetsIntegrator.getCaMetaMap().remove(id);
            RsIntegratorCore.updateExistenceInDataSource(id, DataSourcesConstants.CSV_META_POSITION, false);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
        }
    }


    /**
     * Get patient studies from CA Microscope
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        String metaAsString = "";
        String[] metaArray = getMetaData(patientID);
        for (String metaEntry : metaArray) {
            metaAsString += metaEntry;
        }
        return metaAsString;
    }
}
