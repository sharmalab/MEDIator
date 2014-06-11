/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ui;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.exception.DataReplException;
import edu.emory.bmi.datarepl.core.InfDataAccessIntegration;
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
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
        InfDataAccessIntegration infDataAccessIntegration = InfDataAccessIntegration.getInfiniCore();
        tciaInvoker = new TciaInvoker(userId);

        Long[] replicaSetIDs = infDataAccessIntegration.getUserReplicaSets(userId);

        //currently getting all the replicaSets.
        if (replicaSetIDs != null) {
            for (Long aReplicaSetID : replicaSetIDs) {
                search(infDataAccessIntegration.getReplicaSet(aReplicaSetID));
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
