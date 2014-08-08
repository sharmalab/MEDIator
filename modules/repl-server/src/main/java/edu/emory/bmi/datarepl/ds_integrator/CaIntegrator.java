/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_integrator;

/**
 * Integrates with CA Microscope
 */
public class CaIntegrator extends DataSourcesIntegrator {
    /**
     * Get patient studies from CA Microscope
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        if (edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getCaMetaMap().get(patientID)!=null) {
            return edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getCaMetaMap().get(patientID);
        } else {
            String url = edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.CA_MICROSCOPE_BASE_URL +
                    edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.CA_MICROSCOPE_QUERY_URL +
                    patientID;
            edu.emory.bmi.datarepl.ds_impl.CSVInfDai.getCaMetaMap().put(patientID, url);
            S3Integrator.updateExistenceInDataSource(patientID,
                    edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.CA_META_POSITION, true);
            return url;
        }
    }
}
