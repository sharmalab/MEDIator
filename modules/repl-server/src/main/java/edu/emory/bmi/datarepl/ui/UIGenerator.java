/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ui;

import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

/**
 * Generates HTML pages and outputs using velocity templates.
 */
public class UIGenerator {

    private static VelocityEngine velocityEngine = new VelocityEngine();
    private static VelocityContext context;
    private static Logger logger = LogManager.getLogger(UIGenerator.class.getName());
    private static boolean initiated = false;

    /**
     * Initialize the velocity engine
     */
    public UIGenerator() {
        init();
    }

    public static void init() {
        if (!initiated) {
            velocityEngine.init();
            initiated = true;
        }
    }
    /**
     * Prints the replicaSets into an HTML page
     * @param replicaSetIDs an array of replicaSet IDs.
     */
    public static void printReplicaSetList(Long[] replicaSetIDs) {
        context = new VelocityContext();
        context.put("rsList", replicaSetIDs);
        printToFile("replicaSets.vm", "replicaSets.html");
    }


    private static void getSeriesContext(String response) {
        context = new VelocityContext();
        JSONArray jsonMainArr = new JSONArray(response);

        ArrayList list = new ArrayList();
        for (int i = 0; i < jsonMainArr.length(); i++) {
            JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
            list.add(childJSONObject);
        }
        context.put("seriesList", list);
    }

    private static void getStudiesContext(HttpResponse response) {
        context = new VelocityContext();
        JSONArray jsonMainArr = new JSONArray(response.toString());

        ArrayList list = new ArrayList();
        for (int i = 0; i < jsonMainArr.length(); i++) {
            JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
            list.add(childJSONObject);
        }
        context.put("studiesList", list);
    }

    private static void getPatientsContext(HttpResponse response) {
        context = new VelocityContext();
        JSONArray jsonMainArr = new JSONArray(response.toString());

        ArrayList list = new ArrayList();
        for (int i = 0; i < jsonMainArr.length(); i++) {
            JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
            list.add(childJSONObject);
        }
        context.put("patientsList", list);
    }

    /**
     * Prints the series into the html page.
     * @param response, the response
     */
    public static String returnSeriesOutput(String response) {
        getSeriesContext(response);
        return returnPrintable("series.vm");
    }

    /**
     * Prints the series into the html page.
     * @param response, the response
     */
    public static void printSeries(String response) {
        getSeriesContext(response);
        printToFile("series.vm", "series.html");
    }

    /**
     * Prints the studies into the html page.
     * @param response, the response
     */
    public static void printStudies(HttpResponse response) {
        getStudiesContext(response);
        printToFile("studies.vm", "studies.html");
    }

    /**
     * Prints the patient into the html page.
     * @param response, the response
     */
    public static void printPatients(HttpResponse response) {
        getPatientsContext(response);
        printToFile("patients.vm", "patients.html");
    }

    /**
     * Returns a printable HTML Output
     * @return printable HTML output String
     * @param context VelocityContext
     * @param templateName template file
     */
    public static String returnPrintable(VelocityContext context, String templateName) {
        Template template = velocityEngine.getTemplate(templateName);
        StringWriter writer = new StringWriter();

        template.merge(context, writer);

        return writer.toString();
    }

    private static String returnPrintable(String templateName) {
        return returnPrintable(context, templateName);
    }

    /**
     * Prints the output into an HTML file
     * @param context VelocityContext
     * @param templateName template file
     * @param outputFile output HTML file
     */
    public static void printToFile(VelocityContext context, String templateName, String outputFile) {
        Template template = velocityEngine.getTemplate(templateName);
        StringWriter writer = new StringWriter();

        template.merge(context, writer);

        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(outputFile, "UTF-8");
            printWriter.println(writer.toString());
            printWriter.close();
        } catch (FileNotFoundException e) {
            logger.error("File, " + outputFile + ", could not be written", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported encoding", e);
        }
    }

    private static void printToFile(String templateName, String outputFile) {
        printToFile(context, templateName, outputFile);
    }
}
