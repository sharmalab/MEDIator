/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sample Main class to test the implementation
 */
public class DataRetriever {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) throws UnirestException {
        try {
            HttpResponse<JsonNode> request = Unirest.get("https://tcia.p.mashape.com/getCollectionValues?format=%3Cformat%3E")
                    .header("X-Mashape-Authorization", "7AErcmPVXcqsOT13K1Ij0bLVYL8RVvZ6")
                    .header("api_key", CommonConstants.API_KEY)
                    .asJson();
            logger.info(request.getBody());
        } catch (UnirestException e) {
            logger.error("Failed invoking the request", e);
        }

    }
}
