package edu.emory.bmi.datarepl.servlet;

import com.mashape.unirest.http.HttpResponse;
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
    private static TciaInvoker tciaInvoker;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tciaInvoker = DataRetriever.getTciaInvoker();

        HttpResponse response1;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String patientID = request.getParameter("iPatientID");
        String collectionName = request.getParameter("iCollection");
        String modality = request.getParameter("iModality");
        String bodyPartExamined = request.getParameter("iBodyPartExamined");

        try {
            response1 = tciaInvoker.getSeries("json", "TCGA-GBM", "TCGA-06-6701", null, null);
            String output = UIGenerator.returnSeriesOutput(response1);
            logger.info("\n\n[getSeries]: " + response1.getBody());
            out.println(output);
        } catch (Exception e) {
            logger.error("Exception in getting the response from the TCIA invoker", e);
            out.println("<body>");

            out.println("Patient ID: " +
                     patientID + "  " +
                    "Collection Name: " + collectionName + " " +
                    "Modality: "  + modality + " " +
                    "Body Part Examined: " + bodyPartExamined);
            out.println("</body></html>");
        }
    }
}
