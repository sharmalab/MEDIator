/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.webapp;

import edu.emory.bmi.mediator.core.TciaInitializer;
import edu.emory.bmi.tcia.client.exceptions.TCIAClientException;
import edu.emory.bmi.tcia.client.impl.TCIAClientImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;

/**
 * Retrieves the replicaSets with different granularity.
 */
public class ReplicaSetRetriever {
    private static Logger logger = LogManager.getLogger(ReplicaSetRetriever.class.getName());

    public ReplicaSetRetriever() {
        UIGenerator.init();
    }

    /**
     * Prints the replicaSets into an HTML page
     *
     * @param replicaSetID       replica Set ID
     * @param collectionNames    array of collection names
     * @param patientIDs         array of patient IDs
     * @param studyInstanceUIDs  array of study instance UIDs
     * @param seriesInstanceUIDs array of series instance UIDs
     */
    public static String retrieveReplicaSet(Long replicaSetID, String[] collectionNames, String[] patientIDs,
                                            String[] studyInstanceUIDs, String[] seriesInstanceUIDs) {
        TCIAClientImpl tciaClient = TciaInitializer.getTciaClient();
        VelocityContext context = new VelocityContext();
        context.put("collectionsList", collectionNames);
        context.put("patientsList", patientIDs);
        context.put("studiesList", studyInstanceUIDs);
        context.put("seriesList", seriesInstanceUIDs);
        if (seriesInstanceUIDs != null) {
            for (String aSeriesInstanceUID : seriesInstanceUIDs) {
                try {
                    tciaClient.getImage(aSeriesInstanceUID);
                } catch (TCIAClientException e) {
                   logger.error("TCIA Exception occurred while retrieving the images for the replicaSet", e);
                }
            }
        }
        context.put("title", "ReplicaSetID: " + replicaSetID.toString());
        return UIGenerator.returnPrintable(context, "replicaSet.vm");
    }
}
