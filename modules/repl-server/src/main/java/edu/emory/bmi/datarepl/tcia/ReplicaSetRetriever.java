/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.tcia;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import edu.emory.bmi.datarepl.ui.DataRetriever;
import edu.emory.bmi.datarepl.ui.UIGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;

import java.io.IOException;

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
        TciaInvoker tciaInvoker = DataRetriever.getTciaInvoker();
        VelocityContext context = new VelocityContext();
        context.put("collectionsList", collectionNames);
        context.put("patientsList", patientIDs);
        context.put("studiesList", studyInstanceUIDs);
        context.put("seriesList", seriesInstanceUIDs);
        if (seriesInstanceUIDs != null) {
            for (String aSeriesInstanceUID : seriesInstanceUIDs) {
                try {
                    tciaInvoker.getImage(aSeriesInstanceUID);
                } catch (UnirestException e) {
                    logger.error("Exception while retrieving the images for the replicaSet", e);
                } catch (IOException e) {
                    logger.error("IO Exception occurred while retrieving the images for the replicaSet", e);
                }
            }
        }
        context.put("title", "ReplicaSetID: " + replicaSetID.toString());
        return UIGenerator.returnPrintable(context, "replicaSet.vm");
    }
}
