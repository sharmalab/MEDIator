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
        String[] seriesInstanceUID_1 = {"1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679",
        "1.3.6.1.4.1.14519.5.2.1.4591.4001.207817564815692939776262246027",
        "1.3.6.1.4.1.14519.5.2.1.4591.4001.336683613914449960778928930818"};

        String[] seriesInstanceUID_2 = {"1.3.6.1.4.1.14519.5.2.1.4591.4001.257366771253217605513205827698",
                "1.3.6.1.4.1.14519.5.2.1.4591.4001.954200813327151024838841102184"};

        dataProSpecs.createReplicaSet(userId, null, null, null, seriesInstanceUID_1);
        dataProSpecs.createReplicaSet(userId, null, null, null, seriesInstanceUID_2);

        tciaInvoker = new TciaInvoker(userId);

        Long[] replicaSetIDs = dataProSpecs.getUserReplicaSets(userId);
        String[] collectionNames;
        String[] patientID;
        String[] studyInstanceUID;
        String[] seriesInstanceUID;

        //currently getting all the replicaSets.
        if (replicaSetIDs != null) {
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
