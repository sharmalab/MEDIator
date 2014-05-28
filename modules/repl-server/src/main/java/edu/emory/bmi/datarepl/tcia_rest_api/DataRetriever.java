/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.tcia_rest_api;

import edu.emory.bmi.datarepl.core.TCIAConstants;
import edu.emory.bmi.datarepl.infinispan.InfDataAccessIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) throws TCIAClientException {
        InfDataAccessIntegration.getInfiniCore();

        ITCIAClient client = new TCIAClientImpl(TCIAConstants.API_KEY, TCIAConstants.BASE_URL);


        //Here, put some replicaSet, resulted from createReplicaSet(). For now, just some random string.
        long replicaSetId = InfDataAccessIntegration.putReplicaSet(1111L, "SSS245");

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

        //Here, push some real replicaSet changes. For now, just some random string.
        String newReplicaSet = InfDataAccessIntegration.pushChangesToReplicaSet(replicaSetId, "NEW00");

        logger.info("New ReplicaSet: " + newReplicaSet);

        newReplicaSet = InfDataAccessIntegration.pushChangesToReplicaSet(replicaSetId, "NEW11");

        logger.info("Newer ReplicaSet: " + newReplicaSet);

        long duplicateId = InfDataAccessIntegration.duplicateReplicaSet(replicaSetId, "User11");
        logger.info("Duplicate Id: " + duplicateId + " Duplicate replica set: " +
                InfDataAccessIntegration.getReplicaSet(duplicateId));


        String respXML = client.getCollectionValues(OutputFormat.xml);
        String respJSON = client.getCollectionValues(OutputFormat.json);
        String respCSV = client.getCollectionValues(OutputFormat.csv);

        System.out.println(respXML);
        System.out.println(respJSON);
        System.out.println(respCSV);
    }
}
