/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_integrator;

import edu.emory.bmi.datarepl.constants.DataSourcesConstants;
import edu.emory.bmi.datarepl.core.InfDataAccessIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Integrates with S3
 */
public class S3Integrator extends DataSourcesIntegrator {
    private static Logger logger = LogManager.getLogger(S3Integrator.class.getName());


    /**
     * Get patient studies from S3
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        String url = retrieveUrl(patientID);
        updateExistenceInDataSource(patientID, DataSourcesConstants.S3_META_POSITION, true);
        return url;
    }

    /**
     * Update meta data with CSV entry
     *
     * @param longKey,   contains patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaData(String longKey, String[] metaArray) {
        String patientID = longKey.substring(0, 12);
        InfDataAccessIntegration.getS3MetaMap().put(patientID, metaArray[0]);
        updateExistenceInDataSource(patientID, DataSourcesConstants.S3_META_POSITION, true);
    }

    /**
     * Update meta data with CSV entry
     *
     * @param key, id of the meta data
     * @param meta, meta to be stored
     */
    public static void updateMetaData(String key, String meta) {
        InfDataAccessIntegration.getS3MetaMap().put(key, meta);
    }

    /**
     * Gets the S3 storage url for the given patient
     * @param id, id of the meta data
     * @return url.
     */
    public static String retrieveUrl(String id) {
        String fileName = InfDataAccessIntegration.getS3MetaMap().get(id);
        String url;
        if (fileName.contains(DataSourcesConstants.S3_LEVEL2)) {
            url = DataSourcesConstants.S3_BASE_URL + DataSourcesConstants.S3_LEVEL2 +
                    DataSourcesConstants.URL_SEPARATOR + fileName;
        } else if (fileName.contains(DataSourcesConstants.S3_LEVEL3)) {
            url = DataSourcesConstants.S3_BASE_URL + DataSourcesConstants.S3_LEVEL3 +
                    DataSourcesConstants.URL_SEPARATOR + fileName;
        } else {
            url = DataSourcesConstants.S3_BASE_URL + DataSourcesConstants.S3_LEVEL1 +
                    DataSourcesConstants.URL_SEPARATOR + fileName;
        }
        return url;
    }


    /**
     * /GET Get meta data for CA Microscope
     *
     * @param id, id of the meta data
     */
    public static String getMetaData(String id) {
        if (DataSourcesIntegrator.doesExistInDataSource(id, DataSourcesConstants.S3_META_POSITION)) {
            return InfDataAccessIntegration.getS3MetaMap().get(id);
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
        updateExistenceInDataSource(key, DataSourcesConstants.S3_META_POSITION, true);
    }

    /**
     * /DELETE Delete meta data for CA Microscope
     *
     * @param id, id of the meta data
     */
    public static void deleteMetaData(String id) {
        if (DataSourcesIntegrator.doesExistInDataSource(id, DataSourcesConstants.S3_META_POSITION)) {
            InfDataAccessIntegration.getS3MetaMap().remove(id);
            updateExistenceInDataSource(id, DataSourcesConstants.S3_META_POSITION, false);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
        }
    }

}
