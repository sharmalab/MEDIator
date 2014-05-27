/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.mashape;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.core.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Invokes TCIA through Mashape.
 */
public class TciaInvoker {
    private static Logger logger = LogManager.getLogger(TciaInvoker.class.getName());

    public static HttpResponse getCollectionValues() throws UnirestException {
            HttpResponse<JsonNode> request = Unirest.get("https://tcia.p.mashape.com/getCollectionValues?format=%3Cformat%3E")
                    .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                    .header("api_key", CommonConstants.API_KEY)
                    .asJson();
            return request;
    }

}
