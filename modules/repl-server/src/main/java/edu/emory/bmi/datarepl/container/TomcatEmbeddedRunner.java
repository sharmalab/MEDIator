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
import edu.emory.bmi.datarepl.servlets.CreateRsServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import edu.emory.bmi.datarepl.servlets.InitServlet;

/**
 * Defines the Embedded Tomcat Instance and registers the servlets and mappings.
 */
public class TomcatEmbeddedRunner {
	public void startServer() throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(CommonConstants.EMBEDDED_TOMCAT_PORT);
		File base = new File(System.getProperty("user.dir"));

        Context rootCtx = tomcat.addContext("/", base.getAbsolutePath());

        /*Create Servlet Mappings*/
		Tomcat.addServlet(rootCtx, "initServlet", new InitServlet());
        rootCtx.addServletMapping("/init", "initServlet");

		Tomcat.addServlet(rootCtx, "createRsServlet", new CreateRsServlet());
        rootCtx.addServletMapping("/createRs", "createRsServlet");

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
