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
 * Replica set maanagement for S3.
 */
public class S3ReplicaSetHandler {
    private static Logger logger = LogManager.getLogger(S3ReplicaSetHandler.class.getName());


    /**
     * Get patient studies from S3
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        String url = retrieveUrl(patientID);
        RsIntegratorCore.updateExistenceInDataSource(patientID, DataSourcesConstants.S3_META_POSITION, true);
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
        ReplicaSetsIntegrator.getS3MetaMap().put(patientID, metaArray[0]);
        RsIntegratorCore.updateExistenceInDataSource(patientID, DataSourcesConstants.S3_META_POSITION, true);
    }

    /**
     * Update meta data with CSV entry
     *
     * @param key, id of the meta data
     * @param meta, meta to be stored
     */
    public static void updateMetaData(String key, String meta) {
        ReplicaSetsIntegrator.getS3MetaMap().put(key, meta);
    }

    /**
     * Gets the S3 storage url for the given patient
     * @param id, id of the meta data
     * @return url.
     */
    public static String retrieveUrl(String id) {
        String fileName = ReplicaSetsIntegrator.getS3MetaMap().get(id);
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
        if (RsIntegratorCore.doesExistInDataSource(id, DataSourcesConstants.S3_META_POSITION)) {
            return ReplicaSetsIntegrator.getS3MetaMap().get(id);
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
        RsIntegratorCore.updateExistenceInDataSource(key, DataSourcesConstants.S3_META_POSITION, true);
    }

    /**
     * /DELETE Delete meta data for CA Microscope
     *
     * @param id, id of the meta data
     */
    public static void deleteMetaData(String id) {
        if (RsIntegratorCore.doesExistInDataSource(id, DataSourcesConstants.S3_META_POSITION)) {
            ReplicaSetsIntegrator.getS3MetaMap().remove(id);
            RsIntegratorCore.updateExistenceInDataSource(id, DataSourcesConstants.S3_META_POSITION, false);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
        }
    }

}
