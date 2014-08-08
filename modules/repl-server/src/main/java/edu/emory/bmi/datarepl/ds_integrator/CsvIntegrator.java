/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_integrator;

/**
 * Integrates with CSV
 */
public class CsvIntegrator extends DataSourcesIntegrator {
    /**
     * Update meta data with CSV entry
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaData(String key, String[] metaArray) {
        if (!key.contains(edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.NA)) {
            edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getCsvMetaMap().put(key, metaArray);
            updateExistenceInDataSource(key,
                    edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.CSV_META_POSITION, true);
        }
    }

    /**
     * Get patient studies from CA Microscope
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        return null; //todo return meta complete string <- strig array
    }

}
