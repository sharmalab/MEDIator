/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.container;

import java.io.File;

import edu.emory.bmi.datarepl.constants.CommonConstants;
import edu.emory.bmi.datarepl.servlets.*;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

/**
 * Defines the Embedded Tomcat Instance and registers the servlets and mappings.
 */
public class TomcatEmbeddedRunner {
	public void startServer() throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(CommonConstants.EMBEDDED_TOMCAT_PORT);
		File base = new File(System.getProperty("user.dir"));

        Context rootCtx = tomcat.addContext("/mediator", base.getAbsolutePath());

        /*Create Servlet Mappings*/
		Tomcat.addServlet(rootCtx, "initServlet", new InitServlet());
        rootCtx.addServletMapping("/init", "initServlet");

		Tomcat.addServlet(rootCtx, "createRsServlet", new CreateRsServlet());
        rootCtx.addServletMapping("/createRs", "createRsServlet");

		Tomcat.addServlet(rootCtx, "appendRsServlet", new AppendRsServlet());
        rootCtx.addServletMapping("/appendRs", "appendRsServlet");

		Tomcat.addServlet(rootCtx, "retrieveRsServlet", new RetrieveRsServlet());
        rootCtx.addServletMapping("/retrieveRs", "retrieveRsServlet");

        Tomcat.addServlet(rootCtx, "deleteRsServlet", new DeleteRsServlet());
        rootCtx.addServletMapping("/deleteRs", "deleteRsServlet");

        Tomcat.addServlet(rootCtx, "duplicateRsServlet", new DuplicateRsServlet());
        rootCtx.addServletMapping("/duplicateRs", "duplicateRsServlet");

        Tomcat.addServlet(rootCtx, "listRsServlet", new ListRsServlet());
        rootCtx.addServletMapping("/listRs", "listRsServlet");

        Tomcat.addServlet(rootCtx, "listUsersServlet", new ListUsersServlet());
        rootCtx.addServletMapping("/listUsers", "listUsersServlet");

        Wrapper defaultServlet = rootCtx.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);

        /*Add welcome page*/
        rootCtx.addChild(defaultServlet);
        rootCtx.addServletMapping("/", "default");
        rootCtx.addWelcomeFile("init.html");

        tomcat.start();
		tomcat.getServer().await();
	}
}
