/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.datasources;

import edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants;

/**
 * Retrieve data from S3
 */
public class S3Retriever {
    /**
     * Gets the S3 storage url for the given patient
     * @param patientID, the patient ID
     * @return url.
     */
    public static String retrieveUrl(String patientID) {
        String fileName = edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getS3MetaMap().get(patientID);
        String url;
        if (fileName.contains(edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_LEVEL2)) {
            url = edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_BASE_URL + edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_LEVEL2 + edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.URL_SEPARATOR + fileName;
        } else if (fileName.contains(edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_LEVEL3)) {
            url = edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_BASE_URL + edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_LEVEL3 + edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.URL_SEPARATOR + fileName;
        } else {
            url = edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_BASE_URL + edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.S3_LEVEL1 + DataSourcesConstants.URL_SEPARATOR + fileName;
        }
        return url;
    }
}
