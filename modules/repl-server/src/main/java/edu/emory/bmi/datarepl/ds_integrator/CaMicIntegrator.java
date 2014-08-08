/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_integrator;

import edu.emory.bmi.datarepl.ds_impl.CSVInfDai;
import edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Integrates with CA Microscope
 */
public class CaMicIntegrator extends DataSourcesIntegrator {
    private static Logger logger = LogManager.getLogger(CaMicIntegrator.class.getName());

    /**
     * Get patient studies from CA Microscope
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        if (DataSourcesIntegrator.doesExistInDataSource(patientID, DataSourcesConstants.CA_META_POSITION)) {
            return CSVInfDai.getCaMetaMap().get(patientID);
        } else {
            String url = DataSourcesConstants.CA_MICROSCOPE_BASE_URL + DataSourcesConstants.CA_MICROSCOPE_QUERY_URL +
                    patientID;
            CSVInfDai.getCaMetaMap().put(patientID, url);
            updateExistenceInDataSource(patientID, DataSourcesConstants.CA_META_POSITION, true);
            return url;
        }
    }

    /**
     * /PUSH Update meta data with CA entry
     *
     * @param key,   id of the meta data
     * @param object, object value
     */
    public static void updateMetaData(String key, String object) {
        CSVInfDai.getCaMetaMap().put(key, object);
    }

    /**
     * /GET Get meta data for CA Microscope
     *
     * @param id, id of the meta data
     */
    public static String getMetaData(String id) {
        if (DataSourcesIntegrator.doesExistInDataSource(id, DataSourcesConstants.CA_META_POSITION)) {
            return CSVInfDai.getCaMetaMap().get(id);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
            return null;
        }
    }

    /**
     * /PUT Create meta data with CA entry
     *
     * @param key,       id of the meta data
     * @param object, object value
     */
    public static void putMetaData(String key, String object) {
        updateMetaData(key, object);
        updateExistenceInDataSource(key, DataSourcesConstants.CA_META_POSITION, true);
    }

    /**
     * /DELETE Delete meta data for CA Microscope
     *
     * @param id, id of the meta data
     */
    public static void deleteMetaData(String id) {
        if (DataSourcesIntegrator.doesExistInDataSource(id, DataSourcesConstants.CA_META_POSITION)) {
            CSVInfDai.getCaMetaMap().remove(id);
            updateExistenceInDataSource(id, DataSourcesConstants.CSV_META_POSITION, false);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
        }
    }
}
