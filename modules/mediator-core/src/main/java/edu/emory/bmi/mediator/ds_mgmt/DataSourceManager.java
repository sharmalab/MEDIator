/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.ds_mgmt;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.mediator.exception.MediatorException;
import edu.emory.bmi.mediator.constants.TCIAConstants;
import edu.emory.bmi.mediator.ds_mgmt.tcia.TCIAClientImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * The abstract core class of the interface layer.
 */
public abstract class DataSourceManager implements IDataSourceManager {
    private static Logger logger = LogManager.getLogger(DataSourceManager.class.getName());


    /**
     * /GET Retrieve the meta data
     *
     * @param replicaSet query information
     * @return HttpResponse
     * @throws com.mashape.unirest.http.exceptions.UnirestException
     */
    public HttpResponse retrieve(String replicaSet) throws UnirestException, MediatorException {
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
            throw new MediatorException(replicaSet);
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
                header("X-Mashape-Authorization", TCIAClientImpl.getMashapeAuthorization()).
                header("api_key", TCIAClientImpl.getApiKey()).
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
                header("X-Mashape-Authorization", TCIAClientImpl.getMashapeAuthorization()).
                header("api_key", TCIAClientImpl.getApiKey()).
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
