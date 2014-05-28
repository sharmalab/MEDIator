/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.infinispan.InfDataAccessIntegration;
import edu.emory.bmi.datarepl.mashape.TciaInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiates the replica sets upon log in and search queries - Interfacing Layer
 */
public class ReplicaSetInitiator {
    private static Logger logger = LogManager.getLogger(ReplicaSetInitiator.class.getName());

    /**
     * When the user logs in, retrieve the stored replica sets
     * @param userId Id of the user.
     */
    public static void login(long userId) {
        Long[] replicaSetIDs = InfDataAccessIntegration.getUserReplicaSets(userId);

        //currently getting all the replicaSets. TODO: Get only those that are changed.
        for (Long aReplicaSetID: replicaSetIDs) {
            search(userId, InfDataAccessIntegration.getReplicaSet(aReplicaSetID));
        }
    }

    /**
     * For the initial search. Search and store the replicaSet.
     * @param userId the id of the user
     * @param query the user query.
     */
    public static void searchAndSave(long userId, String query) {
        try {
            TciaInvoker.retrieveMetadata(query);
            InfDataAccessIntegration.createReplicaSet(userId, query);
        } catch (UnirestException e) {
            logger.info("Query format was invalid", e);
        }
    }

    /**
     * For the initial search. Search and store the replicaSet.
     * @param userId the id of the user
     * @param query the user query.
     */
    public static void search(long userId, String query) {
        try {
            TciaInvoker.retrieveMetadata(query);
        } catch (UnirestException e) {
            logger.info("Query format was invalid", e);
        }
    }
}
