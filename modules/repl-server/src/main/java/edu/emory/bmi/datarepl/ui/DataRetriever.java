/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ui;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.container.TomcatEmbeddedRunner;
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import edu.emory.bmi.datarepl.tcia.DataProSpecs;
import edu.emory.bmi.datarepl.tcia.TciaLogInInitiator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import org.apache.catalina.LifecycleException;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());
    private static TciaLogInInitiator logInInitiator;
    private static TciaInvoker tciaInvoker;
    private static String userId = "USER__1001";
    private static DataProSpecs dataProSpecs;


    public static void main(String[] args) throws FileNotFoundException, LifecycleException {
        logInInitiator = new TciaLogInInitiator();
        logInInitiator.login(userId);

        tciaInvoker = logInInitiator.getTciaInvoker();

        HttpResponse response;
        try {
            response = tciaInvoker.getSeries("json", "TCGA-GBM", "TCGA-06-6701", null, null);
            UIGenerator.printSeries(response);
            logger.info("\n\n[getSeries]: " + response.getBody());

            response = tciaInvoker.getPatientStudy(null, null, "TCGA-06-6701", null);
            UIGenerator.printStudies(response);
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getCollectionValues("json");
            logger.info("\n\n[getCollectionValues]: " + response.getBody());

            response = tciaInvoker.getPatient("json", "TCGA-GBM");
            logger.info("\n\n[getPatient]: " + response.getBody());


            response = tciaInvoker.getPatientStudy("json", "TCGA-GBM", "TCGA-06-6701",
                    "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895");
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getPatientStudy("json", "TCGA-GBM", "TCGA-06-6701", null);
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getPatientStudy("json", "TCGA-GBM", null, null);
            logger.info("\n\n[getPatientStudy]: " + response.getBody());

            response = tciaInvoker.getSeries("json", "TCGA-GBM",
                    "TCGA-06-6701","1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", null);
            logger.info("\n\n[getSeries]: " + response.getBody());

            response = tciaInvoker.getBodyPartValues("json", null, null, "MR");
            logger.info("\n\n[getBodyPartValues]: " + response.getBody());

            response = tciaInvoker.getModalityValues("json", null, "BRAIN", "MR");
            logger.info("\n\n[getModalityValues]: " + response.getBody());

            response = tciaInvoker.getManufacturerValues("json", null, "BRAIN", "MR");
            logger.info("\n\n[getManufacturerValues]: " + response.getBody());
            createReplicaSets();

            logInInitiator.login(userId);

        } catch (Exception e) {
            logger.error("Failed invoking the request", e);
        }

        logger.info("Starting Tomcat ..");
        new TomcatEmbeddedRunner().startServer();

    }

    public static void createReplicaSets() {
        dataProSpecs = (DataProSpecs) DataProSpecs.getInfiniCore();
        String[] seriesInstanceUID_1 = {"1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679",
                "1.3.6.1.4.1.14519.5.2.1.4591.4001.207817564815692939776262246027",
                "1.3.6.1.4.1.14519.5.2.1.4591.4001.336683613914449960778928930818"};

        String[] seriesInstanceUID_2 = {"1.3.6.1.4.1.14519.5.2.1.4591.4001.257366771253217605513205827698",
                "1.3.6.1.4.1.14519.5.2.1.4591.4001.954200813327151024838841102184"};

        String[] studyInstanceUID_1 = {"1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895"};

        dataProSpecs.createReplicaSet(userId, null, null, studyInstanceUID_1, seriesInstanceUID_1);
        dataProSpecs.createReplicaSet(userId, null, null, null, seriesInstanceUID_2);
    }

    public static TciaInvoker getTciaInvoker() {
        return tciaInvoker;
    }
}
