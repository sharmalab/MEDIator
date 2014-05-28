/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.mashape;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.core.TCIAConstants;

/**
 * The abstract core class of the interface layer.
 */
public abstract class InterfaceManager {

    /**
     * /GET Retrieve the meta data
     *
     * @param replicaSet query information
     * @return HttpResponse
     * @throws com.mashape.unirest.http.exceptions.UnirestException
     */
    public HttpResponse retrieveMetadata(String replicaSet) throws UnirestException {
        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + replicaSet).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET Retrieve the images
     *
     * @param replicaSet query information
     * @return HttpResponse
     * @throws UnirestException
     */
    public HttpResponse retrieveImage(String replicaSet) throws UnirestException {
        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + replicaSet).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asBinary();
    }
}