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
 * Servlet to display the list of users.
 */
public class ListUsersServlet extends HttpServlet{
    private static Logger logger = LogManager.getLogger(ListUsersServlet.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        TciaReplicaSetHandler tciaReplicaSetHandler = (TciaReplicaSetHandler) TciaReplicaSetHandler.getInfiniCore();

        logger.info("Listing the users..");

        String[] users = tciaReplicaSetHandler.getUsers();

        String output = UIGenerator.returnUsersOutput(users);

        logger.info("Listing the Replica Sets of the Receiving User");
        out.println(output);
    }

}
