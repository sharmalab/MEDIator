/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.webapp.servlets;

import edu.emory.bmi.mediator.ds_mgmt.TciaDSManager;
import edu.emory.bmi.mediator.core.TciaInitializer;
import edu.emory.bmi.mediator.webapp.UIGenerator;
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
        TciaDSManager tciaDSManager = TciaInitializer.getTciaDSManager();

        String response1;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String collectionName = request.getParameter("iCollection");
        String patientID = request.getParameter("iPatientID");
        String studyInstanceUID = request.getParameter("iStudyInstanceUID");
        String modality = request.getParameter("iModality");

        out.println("<HTML>    <BODY>\n");
        try {
            response1 = tciaDSManager.getSeries("json", collectionName, patientID, studyInstanceUID, modality);
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
