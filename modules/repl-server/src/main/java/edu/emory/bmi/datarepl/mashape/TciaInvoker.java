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
import edu.emory.bmi.datarepl.infinispan.InfDataAccessIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Invokes TCIA through Mashape.
 */
public class TciaInvoker extends InterfaceManager {
    private static Logger logger = LogManager.getLogger(TciaInvoker.class.getName());
    private String userId;

    public TciaInvoker(String userId) {
        this.userId = userId;
    }

    /**
     * /GET the collection values
     *
     * @param iFormat format value. optional.
     * @return collection values
     * @throws UnirestException
     */
    public HttpResponse getCollectionValues(String iFormat) throws UnirestException {
        String query = "getCollectionValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        query += temp;

        InfDataAccessIntegration.createReplicaSet(userId, TCIAConstants.META_TAG + query);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET the values of the body parts
     *
     * @param iFormat           format value. optional.
     * @param iCollection       collection value. optional.
     * @param iBodyPartExamined examined body part value. optional.
     * @param iModality         modality value. optional.
     * @return bodyPartValues
     * @throws UnirestException
     */
    public HttpResponse getBodyPartValues(String iFormat, String iCollection, String iBodyPartExamined,
                                          String iModality) throws UnirestException {
        String query = "getBodyPartValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        InfDataAccessIntegration.createReplicaSet(userId, TCIAConstants.META_TAG + query);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET Manufacturer values
     *
     * @param iFormat           format value. optional.
     * @param iCollection       collection value. optional.
     * @param iBodyPartExamined examined body part value. optional.
     * @param iModality         modality value. optional.
     * @return manufacturer values
     * @throws UnirestException
     */
    public HttpResponse getManufacturerValues(String iFormat, String iCollection, String iBodyPartExamined,
                                              String iModality) throws UnirestException {
        String query = "getManufacturerValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        InfDataAccessIntegration.createReplicaSet(userId, TCIAConstants.META_TAG + query);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET Modality values
     *
     * @param iFormat           format value. optional.
     * @param iCollection       collection value. optional.
     * @param iBodyPartExamined examined body part value. optional.
     * @param iModality         modality value. optional.
     * @return modality values
     * @throws UnirestException
     */
    public HttpResponse getModalityValues(String iFormat, String iCollection, String iBodyPartExamined,
                                          String iModality) throws UnirestException {
        String query = "getModalityValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        InfDataAccessIntegration.createReplicaSet(userId, TCIAConstants.META_TAG + query);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET patient
     *
     * @param iFormat     format value. optional.
     * @param iCollection collection value. optional.
     * @return patient
     * @throws UnirestException
     */
    public HttpResponse getPatient(String iFormat, String iCollection) throws UnirestException {
        String query = "getPatient";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        query += temp;

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET patientStudy
     *
     * @param iFormat           format value. optional.
     * @param iCollection       collection value. optional.
     * @param iPatientID        ID of the patient. optional.
     * @param iStudyInstanceUID ID of the study instance. optional.
     * @return patientStudy
     * @throws UnirestException
     */
    public HttpResponse getPatientStudy(String iFormat, String iCollection, String iPatientID,
                                        String iStudyInstanceUID) throws UnirestException {
        String query = "getPatientStudy";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.PATIENT_ID, iPatientID);
        temp = TciaUtil.addParam(temp, TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);
        query += temp;

        InfDataAccessIntegration.createReplicaSet(userId, TCIAConstants.META_TAG + query);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }


    /**
     * /GET series
     *f
     * @param iFormat           format value. optional.
     * @param iCollection       collection value. optional.
     * @param iPatientID        ID of the patient. optional.
     * @param iStudyInstanceUID ID of the study instance. optional.
     * @param iModality         modality value. optional.
     * @return series
     * @throws UnirestException
     */
    public HttpResponse getSeries(String iFormat, String iCollection, String iPatientID,
                                  String iStudyInstanceUID, String iModality) throws UnirestException {
        String query = "getSeries";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.PATIENT_ID, iPatientID);
        temp = TciaUtil.addParam(temp, TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        InfDataAccessIntegration.createReplicaSet(userId, TCIAConstants.META_TAG + query);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET Image
     *
     * @param seriesInstanceUID, instance ID of the series. Mandatory
     * @return image as a zip
     * @throws UnirestException
     */
    public HttpResponse getImage(String seriesInstanceUID) throws UnirestException {
        String query = "getImage?SeriesInstanceUID=" + seriesInstanceUID;
        InfDataAccessIntegration.createReplicaSet(userId, TCIAConstants.IMAGE_TAG + query);

        HttpResponse<InputStream> request = Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asBinary();
        try {
            TciaUtil.saveTo(request.getBody(), seriesInstanceUID + ".zip", ".");
        } catch (IOException e) {
            logger.error("Error when downloading the image", e);
        }
        return request;
    }
}
