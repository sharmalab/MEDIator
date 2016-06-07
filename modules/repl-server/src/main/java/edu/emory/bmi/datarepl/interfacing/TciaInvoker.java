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
    private boolean isMashapeMode;
    private final static String API_KEY_FIELD = "api_key";
    private String apiKey = TCIAConstants.API_KEY;

    public void setMashapeMode(boolean isMashapeMode) {
        this.isMashapeMode = isMashapeMode;
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
        temp = TciaUtil.addParam(temp, API_KEY_FIELD, apiKey);
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
        temp = TciaUtil.addParam(temp, API_KEY_FIELD, apiKey);
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
        temp = TciaUtil.addParam(temp, API_KEY_FIELD, apiKey);
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
        temp = TciaUtil.addParam(temp, API_KEY_FIELD, apiKey);
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
        String query = getPatientsOfTheCollectionString(iFormat, iCollection);

        return getHttpResponse(query);
    }

    /**
     * Get patient query
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @return patient query
     */
    public String getPatientsOfTheCollectionString(String iFormat, String iCollection) {
        String query = "getPatient";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, API_KEY_FIELD, apiKey);
        query += temp;
        return query;
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
        String query = getStudiesOfThePatientString(iFormat, iCollection, iPatientID, iStudyInstanceUID);

        return getHttpResponse(query);
    }

    /**
     * Get studies query
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @param iPatientID patient id, optional
     * @param iStudyInstanceUID study instance uid, optional
     * @return studies query
     */
    public String getStudiesOfThePatientString(
            String iFormat, String iCollection, String iPatientID, String iStudyInstanceUID) {
        String query = "getPatientStudy";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.PATIENT_ID, iPatientID);
        temp = TciaUtil.addParam(temp, TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);
        temp = TciaUtil.addParam(temp, API_KEY_FIELD, apiKey);
        query += temp;
        return query;
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
        String query = getSeriesOfTheStudyString(iFormat, iCollection, iPatientID, iStudyInstanceUID, iModality);

        return getHttpResponse(query);
    }

    /**
     * Get series query
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @param iPatientID patient id, optional
     * @param iStudyInstanceUID study instance uid, optional
     * @param iModality modality value. optional.
     * @return series query
     */
    public String getSeriesOfTheStudyString(
            String iFormat, String iCollection, String iPatientID, String iStudyInstanceUID, String iModality) {
        String query = "getSeries";
        String temp = "";
        temp = TciaUtil.addParam(temp, TCIAConstants.FORMAT, iFormat);
        temp = TciaUtil.addParam(temp, TCIAConstants.COLLECTION, iCollection);
        temp = TciaUtil.addParam(temp, TCIAConstants.PATIENT_ID, iPatientID);
        temp = TciaUtil.addParam(temp, TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);
        temp = TciaUtil.addParam(temp, TCIAConstants.MODALITY, iModality);
        temp = TciaUtil.addParam(temp, API_KEY_FIELD, apiKey);
        query += temp;
        return query;
    }

    /**
     * Gets the Http Response string for the given query, with mashap or direct rest api invocation
     * @param query the query to be invoked
     * @return response
     * @throws UnirestException, if invocation failed in mashape mode
     * @throws IOException IO issues
     */
    public String getHttpResponse(String query) throws UnirestException, IOException {
        if (isMashapeMode) {
            HttpResponse response =
                    (HttpResponse) Unirest.get(TCIAConstants.MASHAPE_BASE_URL + query).
                            header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                            header("api_key", TCIAConstants.API_KEY).
                            asJson();
            return response.toString();
        } else {
            try {
                InputStream is = getRawData(query);
                return TCIAClientImpl.convertStreamToString(is);
            } catch (TCIAClientException e) {
                logger.error("Exception in invoking the TCIA Client", e);
            }
            return null;
        }
    }

    /**
     * Returns the Raw Data for the query.
     * @param query the search query
     * @return InputStream
     * @throws IOException
     * @throws TCIAClientException
     */
    public InputStream getRawData(String query) throws IOException, TCIAClientException {
        URI uri = null;
        try {
            uri = new URI(TCIAConstants.BASE_URL + "/" + query);
        } catch (URISyntaxException e) {
            logger.error("URI syntax was wrong", e);
        }
        TCIAClientImpl client = new TCIAClientImpl(TCIAConstants.API_KEY, TCIAConstants.BASE_URL);

        return client.getRawData(uri);
        }

    /**
     * /GET Image
     *
     * @param seriesInstanceUID, instance ID of the series. Mandatory
     * @return image
     * @throws UnirestException
     */
    public String getImage(String seriesInstanceUID) throws UnirestException, IOException {
        String query = getImagesOfTheSeriesString(seriesInstanceUID);

        return getHttpResponse(query);
    }

    /**
     * Get image query
     * @param seriesInstanceUID, instance ID of the series. Mandatory
     * @return image query.
     */
    public String getImagesOfTheSeriesString(String seriesInstanceUID) {
        return "getImage?SeriesInstanceUID=" + seriesInstanceUID;
    }
}
