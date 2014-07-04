/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.interfacing;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.emory.bmi.datarepl.constants.TCIAConstants;
import edu.emory.bmi.datarepl.tcia_rest_api.TCIAClientException;
import edu.emory.bmi.datarepl.tcia_rest_api.TCIAClientImpl;
import org.apache.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Invokes TCIA through Mashape.
 */
public class TciaInvoker extends InterfaceManager {
    private static Logger logger = LogManager.getLogger(TciaInvoker.class.getName());
    private String userId;
    private boolean isMashapeMode;

    private TCIAClientImpl client;

    public void setMashapeMode(boolean isMashapeMode) {
        this.isMashapeMode = isMashapeMode;
    }

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
    public String getCollectionValues(String iFormat) throws UnirestException, IOException {
        String query = "getCollectionValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        query += temp;

        return getHttpResponse(query);
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
    public String getBodyPartValues(String iFormat, String iCollection, String iBodyPartExamined,
                                    String iModality) throws UnirestException, IOException {
        String query = "getBodyPartValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        return getHttpResponse(query);
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
    public String getManufacturerValues(String iFormat, String iCollection, String iBodyPartExamined,
                                        String iModality) throws UnirestException, IOException {
        String query = "getManufacturerValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        return getHttpResponse(query);
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
    public String getModalityValues(String iFormat, String iCollection, String iBodyPartExamined,
                                    String iModality) throws UnirestException, IOException {
        String query = "getModalityValues";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        return getHttpResponse(query);
    }

    /**
     * /GET patient
     *
     * @param iFormat     format value. optional.
     * @param iCollection collection value. optional.
     * @return patient
     * @throws UnirestException
     */
    public String getPatient(String iFormat, String iCollection) throws UnirestException, IOException {
        String query = "getPatient";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        query += temp;

        return getHttpResponse(query);
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
    public String getPatientStudy(String iFormat, String iCollection, String iPatientID,
                                  String iStudyInstanceUID) throws UnirestException, IOException {
        String query = "getPatientStudy";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.PATIENT_ID, iPatientID);
        temp = TciaUtil.addParam(temp, TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);
        query += temp;

        return getHttpResponse(query);
    }


    /**
     * /GET series
     *
     * @param iFormat           format value. optional.
     * @param iCollection       collection value. optional.
     * @param iPatientID        ID of the patient. optional.
     * @param iStudyInstanceUID ID of the study instance. optional.
     * @param iModality         modality value. optional.
     * @return series
     * @throws UnirestException
     */
    public String getSeries(String iFormat, String iCollection, String iPatientID,
                            String iStudyInstanceUID, String iModality) throws UnirestException, IOException {
        String query = "getSeries";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.PATIENT_ID, iPatientID);
        temp = TciaUtil.addParam(temp, TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        query += temp;

        return getHttpResponse(query);
    }

    public String getHttpResponse(String query) throws UnirestException, IOException {
        if (isMashapeMode) {
            HttpResponse response =
                    (HttpResponse) Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                            header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                            header("api_key", TCIAConstants.API_KEY).
                            asJson();
            return response.toString();
        } else {
            URI uri = null;
            try {
                uri = new URI(TCIAConstants.BASE_URL + "/" + query);
            } catch (URISyntaxException e) {
                logger.error("URI syntax was wrong", e);
            }
            client = new TCIAClientImpl(TCIAConstants.API_KEY, TCIAConstants.API_KEY);

            try {
                InputStream is = client.getRawData(uri);
                return TCIAClientImpl.convertStreamToString(is);

            } catch (TCIAClientException e) {
                logger.error("Exception in invoking the TCIA Client", e);
            }
            return null;
        }
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

        HttpResponse request = (HttpResponse) Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asBinary();
//        try {
//            TciaUtil.saveTo(request.getBody(), seriesInstanceUID + ".zip", ".");
//        } catch (IOException e) {
//            logger.error("Error when downloading the image", e);
//        }
        return request;
    }
}
