/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_integrator;

import edu.emory.bmi.datarepl.ds_impl.DSInfDai;
import edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Integrates with CSV
 */
public class CsvIntegrator extends DataSourcesIntegrator {
    private static Logger logger = LogManager.getLogger(CsvIntegrator.class.getName());

    /**
     * Update meta data with CSV entry
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaData(String key, String[] metaArray) {
        if (!key.contains(DataSourcesConstants.NA)) {
            DSInfDai.getCsvMetaMap().put(key, metaArray);
            updateExistenceInDataSource(key,
                    DataSourcesConstants.CSV_META_POSITION, true);
        }
    }

    /**
     * /GET Get meta data
     *
     * @param id, id of the patient
     */
    public static String[] getMetaData(String id) {
        if (DataSourcesIntegrator.doesExistInDataSource(id, DataSourcesConstants.CSV_META_POSITION)) {
            return DSInfDai.getCsvMetaMap().get(id);
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
        updateExistenceInDataSource(key, DataSourcesConstants.CSV_META_POSITION, true);
    }

    /**
     * /DELETE Delete meta data
     *
     * @param id, id of the patient
     */
    public static void deleteMetaData(String id) {
        if (DataSourcesIntegrator.doesExistInDataSource(id, DataSourcesConstants.CSV_META_POSITION)) {
            DSInfDai.getCaMetaMap().remove(id);
            updateExistenceInDataSource(id, DataSourcesConstants.CSV_META_POSITION, false);
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
