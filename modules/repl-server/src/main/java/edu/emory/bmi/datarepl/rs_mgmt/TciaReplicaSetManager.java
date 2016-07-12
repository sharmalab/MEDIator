/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.datarepl.rs_mgmt;

import edu.emory.bmi.datarepl.constants.CommonConstants;
import edu.emory.bmi.datarepl.constants.DataSourcesConstants;
import edu.emory.bmi.datarepl.integrator.RsIntegratorCore;
import edu.emory.bmi.datarepl.core.TciaInitializer;
import edu.emory.bmi.datarepl.webapp.DataRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import static spark.Spark.*;

/**
 * Offers a REST API for TCIA replica sets management.
 */
public class TciaReplicaSetManager {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) {

        port(CommonConstants.REST_PORT);

        TciaInitializer tciaInitializer = new TciaInitializer();
        tciaInitializer.init();

        TciaReplicaSetHandler tciaReplicaSetHandler = TciaInitializer.getTciaReplicaSetHandler();


        /**
         * Create Replica Set:
         /POST
         http://localhost:9090/replicasets?iUserID=12&iCollection=TCGA-GBM&iPatientID=TCGA-06-6701%2CTCGA-08-0831&iStudyInstanceUID=1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895&iSeriesInstanceUID=1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679

         Response:
         (replicaSetID).
         -4764762120292626164
         */
        post("/replicasets", (request, response) -> {
            String userId = request.queryParams("iUserID");
            String[] collection = (request.queryParams("iCollection") != null) ? request.queryParams("iCollection").split(",") : new String[0];
            String[] patientId = (request.queryParams("iPatientID") != null) ? request.queryParams("iPatientID").split(",") : new String[0];
            String[] studyInstanceUID = (request.queryParams("iStudyInstanceUID") != null) ? request.queryParams("iStudyInstanceUID").split(",") : new String[0];
            String[] seriesInstanceUID = (request.queryParams("iSeriesInstanceUID") != null) ? request.queryParams("iSeriesInstanceUID").split(",") : new String[0];
            long replicaSetID = tciaReplicaSetHandler.createNewReplicaSet(userId, collection, patientId, studyInstanceUID, seriesInstanceUID);
            RsIntegratorCore.updateExistenceInDataSource(replicaSetID, DataSourcesConstants.TCIA_META_POSITION, true);
            response.status(201); // 201 Created
            return replicaSetID;
        });


        /**
         Retrieve Replica Sets of the user:
         /GET
         http://localhost:9090/replicasets/12

         Response:
         [-7466653342708752832, -7059417815353339196, -6908825180316283930, -6365519002970140943]

         or

         Replicasets not found for the user: 123
         */
        get("/replicasets/:id", (request, response) -> {
            Long[] replicaSets = tciaReplicaSetHandler.getUserReplicaSets(request.params(":id"));

            String out = Arrays.toString(replicaSets);

            if (replicaSets != null) {
                return out;
            } else {
                response.status(404); // 404 Not found
                return "Replicasets not found for the user: " + request.params(":id");
            }
        });


        /**
         *
         Retrieve Replica Set:
         /GET
         http://localhost:9090/replicaset/-9176938584709039161

         Response:
         Collection Names: [TCGA-GBM]. Patient IDs: [TCGA-06-6701, TCGA-08-0831]. StudyInstanceUIDs: [1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895]. SeriesInstanceUIDs: [1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679]

         or

         <html>
         <body>
         <h2>500 Internal Error</h2>
         </body>
         </html>


         */
        get("/replicaset/:id", (request, response) -> {
            long replicaSetID = Long.parseLong(request.params(":id"));
            if (RsIntegratorCore.doesExistInDataSource(replicaSetID, DataSourcesConstants.TCIA_META_POSITION)) {
                String replicaSet = tciaReplicaSetHandler.getReplicaSet(replicaSetID);
                if (replicaSet != null) {
                    return replicaSet;
                } else {
                    response.status(404); // 404 Not found
                    return "Replicaset not found: " + request.params(":id");
                }
            } else {
                response.status(404); // 404 Not found
                return "Replicaset does not exist: " + request.params(":id");
            }
        });


        /**
         *
         Delete Replica Set:
         /DELETE
         http://localhost:9090/replicaset/12?replicaSetID=-9176938584709039161

         Response:
         true

         or

         false
         */
        delete("/replicaset/:id", (request, response) -> {
            String userId = request.params(":id");
            long replicaSetID = Long.parseLong(request.queryParams("replicaSetID"));

            boolean deleted = tciaReplicaSetHandler.deleteReplicaSet(userId, replicaSetID);

            if (deleted) { // Update existence only if the action was successful.
                RsIntegratorCore.updateExistenceInDataSource(replicaSetID, DataSourcesConstants.TCIA_META_POSITION, false);
            }
            return deleted;
        });


        /**
         *
         Replace Replica Set:
         /POST
         http://localhost:9090/replicaset/-5841894688098285105?iStudyInstanceUID=1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895&iSeriesInstanceUID=1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679

         Response:
         true

         or

         false
         */
        post("/replicaset/:id", (request, response) -> {
            long replicaSetID = Long.parseLong(request.params(":id"));

            String[] collection = (request.queryParams("iCollection") != null) ? request.queryParams("iCollection").split(",") : new String[0];
            String[] patientId = (request.queryParams("iPatientID") != null) ? request.queryParams("iPatientID").split(",") : new String[0];
            String[] studyInstanceUID = (request.queryParams("iStudyInstanceUID") != null) ? request.queryParams("iStudyInstanceUID").split(",") : new String[0];
            String[] seriesInstanceUID = (request.queryParams("iSeriesInstanceUID") != null) ? request.queryParams("iSeriesInstanceUID").split(",") : new String[0];

            Boolean out = tciaReplicaSetHandler.replaceReplicaSet(replicaSetID, collection, patientId, studyInstanceUID, seriesInstanceUID);
            if (out) { // Update existence only if the action was successful.
                RsIntegratorCore.updateExistenceInDataSource(replicaSetID, DataSourcesConstants.TCIA_META_POSITION, true);
            }
            response.status(201); // 201 Created
            return out;
        });


        /**
         *
         Append Replica Set:
         /PUT
         http://localhost:9090/replicaset/-5841894688098285105?iCollection=TCGA-GBM

         Response:
         true

         or

         false
         */
        put("/replicaset/:id", (request, response) -> {
            long replicaSetID = Long.parseLong(request.params(":id"));

            String[] collection = (request.queryParams("iCollection") != null) ? request.queryParams("iCollection").split(",") : new String[0];
            String[] patientId = (request.queryParams("iPatientID") != null) ? request.queryParams("iPatientID").split(",") : new String[0];
            String[] studyInstanceUID = (request.queryParams("iStudyInstanceUID") != null) ? request.queryParams("iStudyInstanceUID").split(",") : new String[0];
            String[] seriesInstanceUID = (request.queryParams("iSeriesInstanceUID") != null) ? request.queryParams("iSeriesInstanceUID").split(",") : new String[0];

            Boolean out = tciaReplicaSetHandler.addToReplicaSet(replicaSetID, collection, patientId, studyInstanceUID, seriesInstanceUID);

            if (out) { // Update existence only if the action was successful.
                RsIntegratorCore.updateExistenceInDataSource(replicaSetID, DataSourcesConstants.TCIA_META_POSITION, true);
            }
            response.status(201); // 201 Created
            return out;
        });


        /**
         Duplicate Replica Set:
         /POST
         http://localhost:9090/replicaset?userID=1234567&replicaSetID=-7196077834010820228

         Response:
         -5054196249282594410
         (replicaset ID)
         */
        post("/replicaset", (request, response) -> {
            String userId = request.queryParams("userID");
            long replicaSetID = Long.parseLong(request.queryParams("replicaSetID"));

            long newReplicaSetID = tciaReplicaSetHandler.duplicateReplicaSet(replicaSetID, userId);
            RsIntegratorCore.updateExistenceInDataSource(newReplicaSetID, DataSourcesConstants.TCIA_META_POSITION, true);

            response.status(201); // 201 Created
            return newReplicaSetID;
        });
    }
}
