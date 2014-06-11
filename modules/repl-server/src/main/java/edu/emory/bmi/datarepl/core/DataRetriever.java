/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.infinispan.InfDataAccessIntegration;
import edu.emory.bmi.datarepl.mashape.TciaInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) {
        InfDataAccessIntegration.getInfiniCore();

        LogInInitiator logInInitiator = new LogInInitiator();
        String userId = "USER__1001";
        logInInitiator.login(userId);

        TciaInvoker tciaInvoker = logInInitiator.getTciaInvoker();

        HttpResponse response;
        try {
            response = tciaInvoker.getCollectionValues("json");
            logger.info("\n\n[getCollectionValues]: " + response.getBody());

            response = tciaInvoker.getPatient("json", "TCGA-GBM");
            logger.info("\n\n[getPatient]: " + response.getBody());

            response = tciaInvoker.getPatientStudy("json", "TCGA-GBM", "TCGA-06-6701",
                    "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895");
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getPatientStudy("json", "TCGA-GBM", "TCGA-06-6701", null);
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getPatientStudy(null, null, "TCGA-06-6701", null);
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getPatientStudy("json", "TCGA-GBM", null, null);
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getSeries("json", "TCGA-GBM",
                    "TCGA-06-6701","1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", null);
            logger.info("\n\n[getSeries]: " + response.getBody());

            response = tciaInvoker.getSeries("json", "TCGA-GBM", "TCGA-06-6701", null, null);
            logger.info("\n\n[getSeries]: " + response.getBody());

            tciaInvoker.getImage("1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440");

            response = tciaInvoker.getBodyPartValues("json", null, null, "MR");
            logger.info("\n\n[getBodyPartValues]: " + response.getBody());

            response = tciaInvoker.getModalityValues("json", null, "BRAIN", "MR");
            logger.info("\n\n[getModalityValues]: " + response.getBody());

            response = tciaInvoker.getManufacturerValues("json", null, "BRAIN", "MR");
            logger.info("\n\n[getManufacturerValues]: " + response.getBody());
        } catch (UnirestException e) {
            logger.error("Failed invoking the request", e);
        }
    }
}
