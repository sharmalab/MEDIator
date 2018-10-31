package edu.emory.bmi.mediator.webapp.servlets;

import edu.emory.bmi.mediator.rs_mgmt.TciaReplicaSetHandler;
import edu.emory.bmi.mediator.webapp.UIGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet to delete the specified replica set
 */
public class DeleteRsServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(DeleteRsServlet.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userId = request.getParameter("userID");
        if (request.getParameter("replicaSetID") != null && !request.getParameter("replicaSetID").equals("") &&
                !userId.trim().equals("")) {
            try {
                long replicaSetID = Long.parseLong(request.getParameter("replicaSetID"));

                TciaReplicaSetHandler tciaReplicaSetHandler = (TciaReplicaSetHandler) TciaReplicaSetHandler.getInfiniCore();

                logger.info("Deleting the replica set of the user..");
                boolean deleted = tciaReplicaSetHandler.deleteReplicaSet(userId, replicaSetID);

                if (deleted) {
                    Long[] replicaSets = tciaReplicaSetHandler.getUserReplicaSets(userId);

                    String output = UIGenerator.returnReplicaSetOutput(replicaSets);

                    logger.info("Listing the Replica Sets of the User");
                    out.println(output);
                } else {
                    out.println("<HTML>    <BODY>\n");
                    out.println("Error in Deleting the replicaSet with ReplicaSet ID: " + replicaSetID);
                    out.println("</body></html>");
                }
            } catch (NumberFormatException e) {
                String output = "Illegal values provided for the replica Set ID. It should be a long integer.";
                logger.error(output);
                out.println(output);
            }
        } else {
            String output = "Empty values provided for the user ID or the replica Set ID";
            logger.error(output);
            out.println(output);
        }
    }
}

