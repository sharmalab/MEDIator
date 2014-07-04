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
        logger.info("Creating the replica set for the user..");
        dataProSpecs.createReplicaSet(userId,lCollectionName,lPatientID,lStudyInstanceUID,lSeriesInstanceUID);

        Long[] replicaSets = dataProSpecs.getUserReplicaSets(userId);

        out.println("<HTML>    <BODY>\n");

        out.println("Replica Sets of the User: ");
        for (Long aReplicaSet : replicaSets) {
            out.println(aReplicaSet);
        }
        out.println("</body></html>");
    }
}
