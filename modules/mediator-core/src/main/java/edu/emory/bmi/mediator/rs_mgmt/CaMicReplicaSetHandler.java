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
 * Replica sets management for CaMicroscope.
 */
public class CaMicReplicaSetHandler {
    private static Logger logger = LogManager.getLogger(CaMicReplicaSetHandler.class.getName());

    /**
     * Get patient studies from CA Microscope
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        if (RsIntegratorCore.doesExistInDataSource(patientID, DataSourcesConstants.CA_META_POSITION)) {
            return ReplicaSetsIntegrator.getCaMetaMap().get(patientID);
        } else {
            String url = DataSourcesConstants.CA_MICROSCOPE_BASE_URL + DataSourcesConstants.CA_MICROSCOPE_QUERY_URL +
                    patientID;
            ReplicaSetsIntegrator.getCaMetaMap().put(patientID, url);
            RsIntegratorCore.updateExistenceInDataSource(patientID, DataSourcesConstants.CA_META_POSITION, true);
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
        ReplicaSetsIntegrator.getCaMetaMap().put(key, object);
    }

    /**
     * /GET Get meta data for CA Microscope
     *
     * @param id, id of the meta data
     */
    public static String getMetaData(String id) {
        if (RsIntegratorCore.doesExistInDataSource(id, DataSourcesConstants.CA_META_POSITION)) {
            return ReplicaSetsIntegrator.getCaMetaMap().get(id);
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
        RsIntegratorCore.updateExistenceInDataSource(key, DataSourcesConstants.CA_META_POSITION, true);
    }

    /**
     * /DELETE Delete meta data for CA Microscope
     *
     * @param id, id of the meta data
     */
    public static void deleteMetaData(String id) {
        if (RsIntegratorCore.doesExistInDataSource(id, DataSourcesConstants.CA_META_POSITION)) {
            ReplicaSetsIntegrator.getCaMetaMap().remove(id);
            RsIntegratorCore.updateExistenceInDataSource(id, DataSourcesConstants.CSV_META_POSITION, false);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
        }
    }
}
