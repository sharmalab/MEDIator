package edu.emory.bmi.datarepl.datasources;

import edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Integrates with S3
 */
public class S3Integrator extends DataSourcesIntegrator {
    private static Logger logger = LogManager.getLogger(S3Integrator.class.getName());


    /**
     * Update meta data with CSV entry
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaDataWithCSV(String key, String[] metaArray) {
        if (!key.contains(edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.NA)) {
            edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getCsvMetaMap().put(key, metaArray);
            updateExistenceInDataSource(key, edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.CSV_META_POSITION, true);
        }
    }

    /**
     * Integrate with S3
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudiesFromS3(String patientID) {
        String url = S3Retriever.retrieveUrl(patientID);
        updateExistenceInDataSource(patientID, edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_META_POSITION, true);
        return url;
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

        if (edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getMetaMap().get(key) == null) {
            edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getMetaMap().put(key, newEntry);
            logger.info("Adding new entry to the meta map.." + key);
        } else {
            Boolean[] existingEntry = edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getMetaMap().get(key);
            existingEntry[metaArrayIndex] = existence;
            edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getMetaMap().put(key, existingEntry);
            logger.info("Existing entry is updated in the map.." + key);

        }
    }

    /**
     * Update meta data with CSV entry
     *
     * @param longKey,   contains patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaDataWithS3Entry(String longKey, String[] metaArray) {
        String patientID = longKey.substring(0, 12);
        edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getS3MetaMap().put(patientID, metaArray[0]);
        updateExistenceInDataSource(patientID, DataSourcesConstants.S3_META_POSITION, true);
    }
}
