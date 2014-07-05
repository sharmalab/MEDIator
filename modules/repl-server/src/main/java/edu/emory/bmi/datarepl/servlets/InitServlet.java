/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.servlets;

import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import edu.emory.bmi.datarepl.ui.DataRetriever;
import edu.emory.bmi.datarepl.ui.UIGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Initiating servlet class.
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger(InitServlet.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TciaInvoker tciaInvoker = DataRetriever.getTciaInvoker();

        String response1;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String collectionName = request.getParameter("iCollection");
        String patientID = request.getParameter("iPatientID");
        String studyInstanceUID = request.getParameter("iStudyInstanceUID");
        String modality = request.getParameter("iModality");

        out.println("<HTML>    <BODY>\n");
        try {
            response1 = tciaInvoker.getSeries("json", collectionName, patientID, studyInstanceUID, modality);
            String output = UIGenerator.returnSeriesOutput(response1);
            logger.info("\n\n[getSeries]: " + response1);
            out.println(output);
        } catch (Exception e) {
            logger.warn("Exception in getting the response from the TCIA invoker", e);
            out.println("<body>");

            out.println("Collection Name: " + collectionName + " " +
                    "Patient ID: " + patientID + "  " +
                    "Study Instance UID: " + studyInstanceUID + " " +
                    "Modality: " + modality);
        }
        out.println("<CENTER>\n" +
                "       <button type=\"button\"  onclick=\"location.href='createReplicaSet.html' \">Create Replica Set</button> \n" +
                "</CENTER>");
        out.println("</body></html>");
    }
}
