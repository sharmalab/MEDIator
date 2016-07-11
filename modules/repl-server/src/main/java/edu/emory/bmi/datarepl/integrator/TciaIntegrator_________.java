/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.integrator;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.constants.DataSourcesConstants;
import edu.emory.bmi.datarepl.ds_mgmt.TciaDSManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Integrate with TCIA
 */
public class TciaIntegrator_________ extends RsIntegratorCore {
    private static TciaDSManager tciaDSManager = new TciaDSManager();
    private static Logger logger = LogManager.getLogger(TciaIntegrator_________.class.getName());

    /**
     * Get patient studies from TCIA
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        String output = "";
        try {
            tciaDSManager.getPatientStudy("json", null, patientID, null);
            output = tciaDSManager.getStudiesOfThePatientString("json", null, patientID, null);
            RsIntegratorCore.updateExistenceInDataSource(patientID, DataSourcesConstants.TCIA_META_POSITION, true);
        } catch (UnirestException e) {
            logger.info("UniRest Exception while invoking the patient study retrieval", e);
        } catch (IOException e) {
            logger.info("IO Exception while invoking the patient study retrieval", e);
        }
        return output;
    }

}
