/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.servlets;

import edu.emory.bmi.datarepl.tcia.TciaReplicaSetInterface;
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String output;
        if (!request.getParameter("replicaSetID").equals("") && request.getParameter("replicaSetID")!=null) {
            Long replicaSetID = Long.parseLong(request.getParameter("replicaSetID"));
            logger.info("Retrieving the replica set for the user..");
            TciaReplicaSetInterface tciaReplicaSetInterface = (TciaReplicaSetInterface) TciaReplicaSetInterface.getInfiniCore();
            output = tciaReplicaSetInterface.getReplicaSet(replicaSetID);
        }
        else {
            output = "Empty values provided for the replica set ID";
        }
        out.println(output);
    }
}
