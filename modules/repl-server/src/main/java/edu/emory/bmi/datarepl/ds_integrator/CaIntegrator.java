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
public class CaIntegrator extends DataSourcesIntegrator {
    private static Logger logger = LogManager.getLogger(CaIntegrator.class.getName());

    /**
     * Get patient studies from CA Microscope
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        if (CSVInfDai.getCaMetaMap().get(patientID)!=null) {
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
     * Update meta data with CA entry
     *
     * @param key,       patient ID
     * @param object, object value
     */
    public static void updateMetaData(String key, String object) {
        CSVInfDai.getCaMetaMap().put(key, object);
    }

    /**
     * /PUT Create meta data with CA entry
     *
     * @param key,       patient ID
     * @param object, object value
     */
    public static void putMetaData(String key, String object) {
        updateMetaData(key, object);



    }

    /**
     * Get meta data for CA Microscope
     *
     * @param id, id of the patient
     */
    public static String getMetaData(String id) {
        if (CSVInfDai.getCaMetaMap().get(id)!=null) {
            return CSVInfDai.getCaMetaMap().get(id);
        } else {
            logger.info("Meta data does not exist in the map for the key, " + id);
            return null;
        }
    }

}
