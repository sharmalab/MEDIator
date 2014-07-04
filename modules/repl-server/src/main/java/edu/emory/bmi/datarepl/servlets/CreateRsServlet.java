/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.servlets;

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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String lCollectionName = request.getParameter("iCollection");
        String lPatientID = request.getParameter("iPatientID");
        String lStudyInstanceUID = request.getParameter("iStudyInstanceUID");
        String lSeriesInstanceUID = request.getParameter("iSeriesInstanceUID");

        out.println("<HTML>    <BODY>\n");

            out.println("Collection Names: " + lCollectionName +
                    "Patient ID: " + lPatientID + " " +
                    "Study Instance UID: "  + lStudyInstanceUID + " " +
                    "Series Instance UID: " + lSeriesInstanceUID);

        out.println("</body></html>");
    }
}
