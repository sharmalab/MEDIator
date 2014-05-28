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
public class LogInInitiator {
    private static Logger logger = LogManager.getLogger(LogInInitiator.class.getName());
    private TciaInvoker tciaInvoker;
    /**
     * When the user logs in, retrieve the stored replica sets
     * @param userId Id of the user.
     */
    public void login(String userId) {
        tciaInvoker = new TciaInvoker(userId);

        Long[] replicaSetIDs = InfDataAccessIntegration.getUserReplicaSets(userId);

        //currently getting all the replicaSets.
        if (replicaSetIDs != null) {
            for (Long aReplicaSetID : replicaSetIDs) {
                search(InfDataAccessIntegration.getReplicaSet(aReplicaSetID));
            }
        }
    }

     /**
     * For the initial search. Search and store the replicaSet.
     * @param replicaSet the user query.
     */
    public void search(String replicaSet) {
        try {
            tciaInvoker.retrieve(replicaSet);
        } catch (UnirestException e) {
            logger.error("Query format was invalid", e);
        } catch (DataReplException e) {
            logger.error("ReplicationSet format was invalid", e);
        }
    }

    public TciaInvoker getTciaInvoker() {
        return tciaInvoker;
    }
}
