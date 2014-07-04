/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.servlets;

import edu.emory.bmi.datarepl.tcia.DataProSpecs;
import edu.emory.bmi.datarepl.tcia.TciaLogInInitiator;
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
 * Servlet for retrieving replica sets.
 */
public class RetrieveRsServlet extends HttpServlet{
    private static Logger logger = LogManager.getLogger(RetrieveRsServlet.class.getName());
    private static DataProSpecs dataProSpecs;
    private static String userId;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Long replicaSetID = Long.parseLong(request.getParameter("replicaSetID"));

        dataProSpecs = (DataProSpecs) DataProSpecs.getInfiniCore();
        userId = DataRetriever.getUserId();
        logger.info("Retrieving the replica set for the user..");

        String output = TciaLogInInitiator.retrieveReplicaSet(replicaSetID);

        logger.info("\n\n[retriveReplicaSet]: " + output);
        out.println(output);
    }
}
