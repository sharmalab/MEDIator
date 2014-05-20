/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import edu.emory.bmi.datarepl.infinispan.InfDataAccessIntegration;
import edu.emory.bmi.datarepl.tcia.ITCIAClient;
import edu.emory.bmi.datarepl.tcia.OutputFormat;
import edu.emory.bmi.datarepl.tcia.TCIAClientException;
import edu.emory.bmi.datarepl.tcia.TCIAClientImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) throws TCIAClientException {
        InfDataAccessIntegration.getInfiniCore();


        long replicaSetId = InfDataAccessIntegration.putReplicaSet("SSS245");

        logger.info("Replica Set Id: " + replicaSetId + " Replica Set: " +
                InfDataAccessIntegration.getReplicaSet(replicaSetId));

        logger.info("Replica Set Id: " + replicaSetId + " Replica Set: " +
                InfDataAccessIntegration.getReplicaSet(replicaSetId));

        boolean success = InfDataAccessIntegration.deleteReplicaSet(replicaSetId);
        logger.info(success);

        success = InfDataAccessIntegration.deleteReplicaSet(replicaSetId);
        logger.info(success);

        logger.info("Replica Set Id: " + replicaSetId + " Replica Set: " +
                InfDataAccessIntegration.getReplicaSet(replicaSetId));

        String newReplicaSet = InfDataAccessIntegration.pushChangesToReplicaSet(replicaSetId, "NEW00");

        logger.info("New ReplicaSet: " + newReplicaSet);

        newReplicaSet = InfDataAccessIntegration.pushChangesToReplicaSet(replicaSetId, "NEW11");

        logger.info("Newer ReplicaSet: " + newReplicaSet);

        long duplicateId = InfDataAccessIntegration.duplicateReplicaSet(replicaSetId);
        logger.info("Duplicate Id: " + duplicateId + " Duplicate replica set: " +
                InfDataAccessIntegration.getReplicaSet(duplicateId));

        ITCIAClient client = new TCIAClientImpl(CommonConstants.API_KEY, CommonConstants.BASE_URL);

//        String respXML = client.getCollectionValues(OutputFormat.xml);
//        String respJSON = client.getCollectionValues(OutputFormat.json);
//        String respCSV = client.getCollectionValues(OutputFormat.csv);
//
//        System.out.println(respXML);
//        System.out.println(respJSON);
//        System.out.println(respCSV);
    }
}
