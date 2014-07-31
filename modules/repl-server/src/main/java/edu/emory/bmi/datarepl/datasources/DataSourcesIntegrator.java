/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.datasources;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Integrate with the data sources
 */
public class DataSourcesIntegrator {
    private static TciaInvoker tciaInvoker = new TciaInvoker();
    private static Logger logger = LogManager.getLogger(DataSourcesIntegrator.class.getName());

    /**
     * Integrate with TCIA
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudiesFromTCIA(String patientID) {
        String output = "";
        try {
            tciaInvoker.getPatientStudy("json", null, patientID, null);
            output = tciaInvoker.getStudiesOfThePatientString("json", null, patientID, null);
            updateExistenceInDataSource(patientID, ParsingConstants.TCIA_META_POSITION, true);
        } catch (UnirestException e) {
            logger.info("UniRest Exception while invoking the patient study retrieval", e);
        } catch (IOException e) {
            logger.info("IO Exception while invoking the patient study retrieval", e);
        }
        return output;
    }

    /**
     * Integrate with CA Microscope
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudiesFromCAMicroscope(String patientID) {
        String url = ParsingConstants.CA_MICROSCOPE_BASE_URL + ParsingConstants.CA_MICROSCOPE_QUERY_URL + patientID;
        updateExistenceInDataSource(patientID, ParsingConstants.CA_META_POSITION, true);
        return url;
    }

    /**
     * Integrate with S3
     *
     * @param patientID, id of the patient
     */
    public static void getPatientStudiesFromS3(String patientID) {
        S3Retriever.retrieveData(patientID);
        updateExistenceInDataSource(patientID, ParsingConstants.S3_META_POSITION, true);
    }

    /**
     * Does the data source exists.
     *
     * @param key,            patientId
     * @param metaArrayIndex, index in the meta array
     * @return if exists.
     */
    public static boolean doesExistInDataSource(String key, int metaArrayIndex) {
        if (CSVInfDai.getMetaMap().get(key) == null) {
            return false;
        } else {
            Boolean[] temp = CSVInfDai.getMetaMap().get(key);
            return temp[metaArrayIndex];
        }
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

        if (CSVInfDai.getMetaMap().get(key) == null) {
            CSVInfDai.getMetaMap().put(key, newEntry);
            logger.info("Adding new entry to the meta map.." + key);
        } else {
            Boolean[] existingEntry = CSVInfDai.getMetaMap().get(key);
            existingEntry[metaArrayIndex] = existence;
            CSVInfDai.getMetaMap().put(key, existingEntry);
            logger.info("Existing entry is updated in the map.." + key);

        }
    }
}
