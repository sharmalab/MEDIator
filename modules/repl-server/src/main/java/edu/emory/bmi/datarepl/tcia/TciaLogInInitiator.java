/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.tcia;

import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import edu.emory.bmi.datarepl.ui.LogInInitiator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The log in class for TCIA
 */
public class TciaLogInInitiator extends LogInInitiator {
    private static Logger logger = LogManager.getLogger(TciaLogInInitiator.class.getName());
    private DataProSpecs dataProSpecs;

    /**
     * When the user logs in, retrieve the stored replica sets
     *
     * @param userId Id of the user.
     */
    public void login(String userId) {
        dataProSpecs = (DataProSpecs) DataProSpecs.getInfiniCore();

        logger.info("user, " + userId + " logs in..");

        tciaInvoker = new TciaInvoker(userId);

        Long[] replicaSetIDs = dataProSpecs.getUserReplicaSets(userId);
        String[] collectionNames;
        String[] patientID;
        String[] studyInstanceUID;
        String[] seriesInstanceUID;

        //currently getting all the replicaSets.
        if (replicaSetIDs != null) {
            logger.info("Retrieving the replica sets");
            for (Long aReplicaSetID : replicaSetIDs) {
                logger.info("ReplicaSetID: " + aReplicaSetID);
                // TODO: CREATE PAGE 3.
                Boolean[] metaMap = dataProSpecs.getMetaMap(aReplicaSetID);
                if (metaMap[0]) {
                    collectionNames = dataProSpecs.getCollectionsSet(aReplicaSetID);
                }
                if (metaMap[1]) {
                    patientID = dataProSpecs.getPatientsSet(aReplicaSetID);
                }
                if (metaMap[2]) {
                    studyInstanceUID = dataProSpecs.getStudiesSet(aReplicaSetID);
                }
                if (metaMap[3]) {
                    seriesInstanceUID = dataProSpecs.getSeriesSet(aReplicaSetID);
                }
                // TODO: FOREACH REPLICA SET: CREATE MULTIPLE PAGE 4.
            }
        }
    }

}
