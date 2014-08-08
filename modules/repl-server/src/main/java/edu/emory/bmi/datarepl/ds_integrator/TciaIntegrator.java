/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_integrator;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Integrate with TCIA
 */
public class TciaIntegrator {
    private static TciaInvoker tciaInvoker = new TciaInvoker();
    private static Logger logger = LogManager.getLogger(TciaIntegrator.class.getName());

    /**
     * Get patient studies from TCIA
     *
     * @param patientID, id of the patient
     */
    public static String getPatientStudies(String patientID) {
        String output = "";
        try {
            tciaInvoker.getPatientStudy("json", null, patientID, null);
            output = tciaInvoker.getStudiesOfThePatientString("json", null, patientID, null);
            DataSourcesIntegrator.updateExistenceInDataSource(patientID,
                    edu.emory.bmi.datarepl.ds_impl.DataSourcesConstants.TCIA_META_POSITION, true);
        } catch (UnirestException e) {
            logger.info("UniRest Exception while invoking the patient study retrieval", e);
        } catch (IOException e) {
            logger.info("IO Exception while invoking the patient study retrieval", e);
        }
        return output;
    }

}
