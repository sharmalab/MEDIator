package edu.emory.bmi.datarepl.servlets;

import edu.emory.bmi.datarepl.tcia.TciaReplicaSetInterface;
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
            long replicaSetID = Long.parseLong(request.getParameter("replicaSetID"));

            TciaReplicaSetInterface tciaReplicaSetInterface = (TciaReplicaSetInterface) TciaReplicaSetInterface.getInfiniCore();

            logger.info("Deleting the replica set of the user..");
            boolean deleted = tciaReplicaSetInterface.deleteReplicaSet(userId, replicaSetID);

            if (deleted) {
                Long[] replicaSets = tciaReplicaSetInterface.getUserReplicaSets(userId);

                String output = UIGenerator.returnReplicaSetOutput(replicaSets);

                logger.info("Listing the Replica Sets of the User");
                out.println(output);
            } else {
                out.println("<HTML>    <BODY>\n");
                out.println("Error in Deleting the replicaSet with ReplicaSet ID: " + replicaSetID);
                out.println("</body></html>");
            }
        } else {
            String output = "Empty values provided for the user ID or the replica Set ID";
            logger.error(output);
            out.println(output);
        }
    }
}

