/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.servlets;

import edu.emory.bmi.datarepl.tcia.DataProSpecs;
import edu.emory.bmi.datarepl.ui.DataRetriever;
import edu.emory.bmi.datarepl.ui.UIGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for replicaSet creation
 */
public class CreateRsServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(CreateRsServlet.class.getName());
    private static DataProSpecs dataProSpecs;
    private static String userId;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String[] lCollectionName = request.getParameter("iCollection").split(",");
        String[] lPatientID = request.getParameter("iPatientID").split(",");
        String[] lStudyInstanceUID = request.getParameter("iStudyInstanceUID").split(",");
        String[] lSeriesInstanceUID = request.getParameter("iSeriesInstanceUID").split(",");


        dataProSpecs = (DataProSpecs) DataProSpecs.getInfiniCore();
        userId = DataRetriever.getUserId();
        if (request.getParameter("iRsID")==null || request.getParameter("iRsID").trim().length()==0) {
            logger.info("Creating the replica set for the user..");
            dataProSpecs.createReplicaSet(userId,lCollectionName,lPatientID,lStudyInstanceUID,lSeriesInstanceUID);
        } else {
            logger.info("Updating the replica set for the user..");
            Long rsID = Long.parseLong(request.getParameter("iRsID").trim());
            dataProSpecs.updateReplicaSet(rsID,lCollectionName,lPatientID,lStudyInstanceUID,lSeriesInstanceUID);
        }

        Long[] replicaSets = dataProSpecs.getUserReplicaSets(userId);

        String output = UIGenerator.returnReplicaSetOutput(replicaSets);

        logger.info("Listing the Replica Sets of the User");
        out.println(output);
    }
}
