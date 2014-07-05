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
import edu.emory.bmi.datarepl.ui.UIGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The log in class for TCIA
 */
public class TciaLogInInitiator extends LogInInitiator {
    private static Logger logger = LogManager.getLogger(TciaLogInInitiator.class.getName());
    private static DataProSpecs dataProSpecs;

    /**
     * When the user logs in, retrieve the stored replica sets
     *
     * @param userId Id of the user.
     */
    public void login(String userId) {
        dataProSpecs = (DataProSpecs) DataProSpecs.getInfiniCore();

        tciaInvoker = new TciaInvoker();

        Long[] replicaSetIDs = dataProSpecs.getUserReplicaSets(userId);

        //currently getting all the replicaSets.
        if (replicaSetIDs != null) {
            logger.info("Retrieving the replica sets");
            UIGenerator.printReplicaSetList(replicaSetIDs);

            for (Long aReplicaSetID : replicaSetIDs) {
                retrieveReplicaSet(aReplicaSetID);
            }
        }
    }

    /**
     * Initiate replication and synchronization tool at the start time, without the user id.
     */
    public void init() {
        dataProSpecs = (DataProSpecs) DataProSpecs.getInfiniCore();
        tciaInvoker = new TciaInvoker();
    }

    /**
     * Retrieve the replicaSet of the given replicaSetID
     * @param aReplicaSetID replicaSet ID
     * @return replicaSet as a printable output
     */
    public static String retrieveReplicaSet(Long aReplicaSetID) {
        logger.info("ReplicaSetID: " + aReplicaSetID);

        String[] collectionNames = {};
        String[] patientIDs = {};
        String[] studyInstanceUIDs = {};
        String[] seriesInstanceUIDs = {};

        Boolean[] metaMap = dataProSpecs.getMetaMap(aReplicaSetID);
        if (metaMap[0]) {
            collectionNames = dataProSpecs.getCollectionsSet(aReplicaSetID);
        }
        if (metaMap[1]) {
            patientIDs = dataProSpecs.getPatientsSet(aReplicaSetID);
        }
        if (metaMap[2]) {
            studyInstanceUIDs = dataProSpecs.getStudiesSet(aReplicaSetID);
        }
        if (metaMap[3]) {
            seriesInstanceUIDs = dataProSpecs.getSeriesSet(aReplicaSetID);
        }
        return ReplicaSetRetriever.retrieveReplicaSet(aReplicaSetID, collectionNames, patientIDs, studyInstanceUIDs,
                seriesInstanceUIDs);
    }

}
