/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.tcia;

import edu.emory.bmi.datarepl.ds_mgmt.TciaDSManager;
import edu.emory.bmi.datarepl.rs_mgmt.TciaReplicaSetHandler;
import edu.emory.bmi.datarepl.webapp.UIGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The log in class for TCIA
 */
public class TciaInitializer extends Initializer {
    private static Logger logger = LogManager.getLogger(TciaInitializer.class.getName());
    private static TciaReplicaSetHandler tciaReplicaSetHandler;

    /**
     * When the user logs in, retrieve the stored replica sets
     *
     * @param userId Id of the user.
     */
    public void login(String userId) {
        tciaReplicaSetHandler = (TciaReplicaSetHandler) TciaReplicaSetHandler.getInfiniCore();

        tciaDSManager = new TciaDSManager();

        Long[] replicaSetIDs = tciaReplicaSetHandler.getUserReplicaSets(userId);

        //currently getting all the replicaSets.
        if (replicaSetIDs != null) {
            logger.info("Retrieving the replica sets");
            UIGenerator.printReplicaSetList(replicaSetIDs);

            for (Long aReplicaSetID : replicaSetIDs) {
                tciaReplicaSetHandler.getReplicaSet(aReplicaSetID);
            }
        }
    }

    /**
     * Initiate replication and synchronization tool at the start time, without the user id.
     */
    public void init() {
        tciaReplicaSetHandler = (TciaReplicaSetHandler) TciaReplicaSetHandler.getInfiniCore();
        tciaDSManager = new TciaDSManager();
    }

    public static TciaReplicaSetHandler getTciaReplicaSetHandler() {
        return tciaReplicaSetHandler;
    }
}
