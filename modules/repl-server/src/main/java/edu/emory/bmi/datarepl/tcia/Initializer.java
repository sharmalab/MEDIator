/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.tcia;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.exception.DataReplException;
import edu.emory.bmi.datarepl.integrator.ReplicaSetsIntegrator;
import edu.emory.bmi.datarepl.ds_mgmt.TciaDSManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initiates the replica sets upon log in and search queries - Interfacing Layer
 */
public abstract class Initializer {
    private static Logger logger = LogManager.getLogger(Initializer.class.getName());
    protected TciaDSManager tciaDSManager;

    /**
     * When the user logs in, retrieve the stored replica sets
     *
     * @param userId Id of the user.
     */
    public void login(String userId) {
        ReplicaSetsIntegrator replicaSetsIntegrator = ReplicaSetsIntegrator.getInfiniCore();
        tciaDSManager = new TciaDSManager();
        tciaDSManager.setMashapeMode(false);

        Long[] replicaSetIDs = replicaSetsIntegrator.getUserReplicaSets(userId);

        //currently getting all the replicaSets.
        if (replicaSetIDs != null) {
            for (Long aReplicaSetID : replicaSetIDs) {
                search(replicaSetsIntegrator.getReplicaSet(aReplicaSetID));
            }
        }
    }

    /**
     * For the initial search. Search and store the replicaSet.
     *
     * @param replicaSet the user query.
     */
    public void search(String replicaSet) {
        try {
            tciaDSManager.retrieve(replicaSet);
        } catch (UnirestException e) {
            logger.error("Query format was invalid", e);
        } catch (DataReplException e) {
            logger.error("ReplicationSet format was invalid", e);
        }
    }

    public TciaDSManager getTciaDSManager() {
        return tciaDSManager;
    }
}
