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

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {

    public static void main(String[] args) throws TCIAClientException {

        InfDataAccessIntegration.getInfiniCore();


        long replicaSetId = InfDataAccessIntegration.putReplicaSet("SSS245");

        System.out.println("Replica Set Id: " + replicaSetId + " Replica Set: " +
                InfDataAccessIntegration.getReplicaSet(replicaSetId));

        System.out.println("Replica Set Id: " + replicaSetId + " Replica Set: " +
                InfDataAccessIntegration.getReplicaSet(replicaSetId));

        boolean success = InfDataAccessIntegration.deleteReplicaSet(replicaSetId);
        System.out.println(success);

        success = InfDataAccessIntegration.deleteReplicaSet(replicaSetId);
        System.out.println(success);

        System.out.println("Replica Set Id: " + replicaSetId + " Replica Set: " +
                InfDataAccessIntegration.getReplicaSet(replicaSetId));

        String newReplicaSet = InfDataAccessIntegration.pushChangesToReplicaSet(replicaSetId, "NEW00");

        System.out.println("New ReplicaSet: " + newReplicaSet);

        newReplicaSet = InfDataAccessIntegration.pushChangesToReplicaSet(replicaSetId, "NEW11");

        System.out.println("Newer ReplicaSet: " + newReplicaSet);

        long duplicateId = InfDataAccessIntegration.duplicateReplicaSet(replicaSetId);
        System.out.println("Duplicate Id: " + duplicateId + " Duplicate replica set: " +
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
