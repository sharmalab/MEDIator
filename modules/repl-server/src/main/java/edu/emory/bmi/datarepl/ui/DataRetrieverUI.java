/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ui;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.core.InfDataAccessIntegration;
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Sample Main class to test the implementation
 */
public class DataRetrieverUI {
    private static Logger logger = LogManager.getLogger(DataRetrieverUI.class.getName());

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        InfDataAccessIntegration.getInfiniCore();

        LogInInitiator logInInitiator = new LogInInitiator();
        String userId = "USER__1001";
        logInInitiator.login(userId);

        TciaInvoker tciaInvoker = logInInitiator.getTciaInvoker();

        HttpResponse response;
        try {
            response = tciaInvoker.getSeries("json", "TCGA-GBM", "TCGA-06-6701", null, null);
            JSONArray jsonMainArr = new JSONArray(response.getBody().toString());

            ArrayList list = new ArrayList();
            for (int i = 0; i < jsonMainArr.length(); i++) {
                JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
                list.add(childJSONObject);
            }

            VelocityContext context = new VelocityContext();
            context.put("seriesList", list);

            Template template = velocityEngine.getTemplate("series.vm");
            StringWriter writer = new StringWriter();

            template.merge(context, writer);

            PrintWriter printWriter = new PrintWriter("series.html", "UTF-8");
            printWriter.println(writer.toString());
            printWriter.close();

            logger.info("\n\n[getSeries]: " + response.getBody());
        } catch (UnirestException e) {
            logger.error("Failed invoking the request", e);
        }
    }
}
