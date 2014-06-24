/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.interfacing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.exception.DataReplException;
import edu.emory.bmi.datarepl.constants.TCIAConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * The abstract core class of the interface layer.
 */
public abstract class InterfaceManager implements InterfaceAPI{
    private static Logger logger = LogManager.getLogger(InterfaceManager.class.getName());


    /**
     * /GET Retrieve the meta data
     *
     * @param replicaSet query information
     * @return HttpResponse
     * @throws com.mashape.unirest.http.exceptions.UnirestException
     */
    public HttpResponse retrieve(String replicaSet) throws UnirestException, DataReplException {
        String meta[];
        if (logger.isDebugEnabled()) {
            logger.debug("Retrieving the information associated to the replicaSet, " + replicaSet);
        }
        if (replicaSet.startsWith(TCIAConstants.IMAGE_TAG)) {
            meta = replicaSet.split(TCIAConstants.IMAGE_TAG);
            return retrieveImage(meta[1]);
        } else if (replicaSet.startsWith(TCIAConstants.META_TAG)){
            meta = replicaSet.split(TCIAConstants.META_TAG);
            return retrieveMetadata(meta[1]);
        } else {
            throw new DataReplException(replicaSet);
        }
    }

    /**
     * /GET Retrieve the meta data
     *
     * @param query query information
     * @return HttpResponse
     * @throws com.mashape.unirest.http.exceptions.UnirestException
     */
    public HttpResponse retrieveMetadata(String query) throws UnirestException {
        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET Retrieve the images
     *
     * @param query query information
     * @return HttpResponse
     * @throws UnirestException
     */
    public HttpResponse retrieveImage(String query) throws UnirestException {
        HttpResponse<InputStream> request = Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asBinary();
        if (logger.isDebugEnabled()) {
            logger.debug("Downloading the image again: " + query);
        }
        logger.info(request.getHeaders());
        String [] imageData = query.split("SeriesInstanceUID=");
        try {
            TciaUtil.saveTo(request.getBody(), imageData[1] + ".zip", ".");
        } catch (IOException e) {
            logger.error("Error when downloading the image", e);
        }
        return request;

    }
}
