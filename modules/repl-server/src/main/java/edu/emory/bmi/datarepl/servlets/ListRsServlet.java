package edu.emory.bmi.datarepl.servlets;

import edu.emory.bmi.datarepl.tcia.TciaReplicaSetAPI;
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
 * Servlet to list the replica sets of the specified user
 */
public class ListRsServlet extends HttpServlet{
    private static Logger logger = LogManager.getLogger(ListRsServlet.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String dUserId = request.getParameter("dUserID");

        TciaReplicaSetAPI tciaReplicaSetAPI = (TciaReplicaSetAPI) TciaReplicaSetAPI.getInfiniCore();

        logger.info("Listing the replica sets of the user..");

        Long[] replicaSets = tciaReplicaSetAPI.getUserReplicaSets(dUserId);

        String output = UIGenerator.returnReplicaSetOutput(replicaSets);

        logger.info("Listing the Replica Sets of the Receiving User");
        out.println(output);
    }

}
