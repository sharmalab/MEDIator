package edu.emory.bmi.datarepl.container;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import edu.emory.bmi.datarepl.servlet.InitServlet;

public class TomcatEmbeddedRunner {
	public void startServer() throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(9090);
		File base = new File(System.getProperty("java.io.tmpdir"));
		Context rootCtx = tomcat.addContext("/app", base.getAbsolutePath());
		Tomcat.addServlet(rootCtx, "initServlet", new InitServlet());
		rootCtx.addServletMapping("/init", "initServlet");
		tomcat.start();
		tomcat.getServer().await();
	}
}
