package edu.emory.bmi.datarepl.container;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import edu.emory.bmi.datarepl.servlet.InitServlet;
import org.apache.tomcat.util.http.mapper.Mapper;

/**
 * Defines the Embedded Tomcat Instance.
 */
public class TomcatEmbeddedRunner {
	public void startServer() throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(9090);
		File base = new File(System.getProperty("user.dir"));

        Context rootCtx = tomcat.addContext("/", base.getAbsolutePath());

		Tomcat.addServlet(rootCtx, "initServlet", new InitServlet());
        rootCtx.addServletMapping("/init", "initServlet");

        Wrapper defaultServlet = rootCtx.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);

        rootCtx.addChild(defaultServlet);
        rootCtx.addServletMapping("/", "default");
        rootCtx.addWelcomeFile("init.html");



        tomcat.start();
		tomcat.getServer().await();
	}
}
