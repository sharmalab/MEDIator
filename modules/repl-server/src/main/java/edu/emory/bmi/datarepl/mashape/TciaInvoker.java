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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Invokes TCIA through Mashape.
 */
public class TciaInvoker extends InterfaceManager {
    private static Logger logger = LogManager.getLogger(TciaInvoker.class.getName());
    private Long userId;

    public TciaInvoker(Long userId) {
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
        String format = TciaUtil.wrapVariable(TCIAConstants.FORMAT, iFormat);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getCollectionValues?format="+format).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET the values of the body parts
     *
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @param iBodyPartExamined examined body part value. optional.
     * @param iModality modality value. optional.
     * @return bodyPartValues
     * @throws UnirestException
     */
    public HttpResponse getBodyPartValues(String iFormat, String iCollection, String iBodyPartExamined,
                                                 String iModality) throws UnirestException {
        String format = TciaUtil.wrapVariable(TCIAConstants.FORMAT, iFormat);
        String collection = TciaUtil.wrapVariable(TCIAConstants.COLLECTION, iCollection);
        String bodyPartExamined = TciaUtil.wrapVariable(TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        String modality = TciaUtil.wrapVariable(TCIAConstants.MODALITY, iModality);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getBodyPartValues?format=" + format +
                "&Collection=" + collection +
                "&BodyPartExamined=" + bodyPartExamined +
                "&Modality=" + modality).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET Manufacturer values
     *
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @param iBodyPartExamined examined body part value. optional.
     * @param iModality modality value. optional.
     * @return manufacturer values
     * @throws UnirestException
     */
    public HttpResponse getManufacturerValues(String iFormat, String iCollection, String iBodyPartExamined,
                                                     String iModality) throws UnirestException {
        String format = TciaUtil.wrapVariable(TCIAConstants.FORMAT, iFormat);
        String collection = TciaUtil.wrapVariable(TCIAConstants.COLLECTION, iCollection);
        String bodyPartExamined = TciaUtil.wrapVariable(TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        String modality = TciaUtil.wrapVariable(TCIAConstants.MODALITY, iModality);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getManufacturerValues?format=" + format +
                "&Collection=" + collection +
                "BodyPartExamined=" + bodyPartExamined +
                "&Modality=" + modality).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET Modality values
     *
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @param iBodyPartExamined examined body part value. optional.
     * @param iModality modality value. optional.
     * @return modality values
     * @throws UnirestException
     */
    public HttpResponse getModalityValues(String iFormat, String iCollection, String iBodyPartExamined,
                                                 String iModality) throws UnirestException {
        String format = TciaUtil.wrapVariable(TCIAConstants.FORMAT, iFormat);
        String collection = TciaUtil.wrapVariable(TCIAConstants.COLLECTION, iCollection);
        String bodyPartExamined = TciaUtil.wrapVariable(TCIAConstants.BODY_PART_EXAMINED, iBodyPartExamined);
        String modality = TciaUtil.wrapVariable(TCIAConstants.MODALITY, iModality);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getModalityValues?Collection=" + collection +
                "&BodyPartExamined=" + bodyPartExamined +
                "&Modality=" + modality +
                "format=" + format).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET patient
     *
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @return patient
     * @throws UnirestException
     */
    public HttpResponse getPatient(String iFormat, String iCollection) throws UnirestException {
        String format = TciaUtil.wrapVariable(TCIAConstants.FORMAT, iFormat);
        String collection = TciaUtil.wrapVariable(TCIAConstants.COLLECTION, iCollection);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getPatient?format=" + format +
                "&Collection=" + collection).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET patientStudy
     *
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @param iPatientID ID of the patient. optional.
     * @param iStudyInstanceUID ID of the study instance. optional.
     * @return patientStudy
     * @throws UnirestException
     */
    public HttpResponse getPatientStudy(String iFormat, String iCollection, String iPatientID,
                                               String iStudyInstanceUID) throws UnirestException {
        String format = TciaUtil.wrapVariable(TCIAConstants.FORMAT, iFormat);
        String collection = TciaUtil.wrapVariable(TCIAConstants.COLLECTION, iCollection);
        String patientID = TciaUtil.wrapVariable(TCIAConstants.PATIENT_ID, iPatientID);
        String studyInstanceUID = TciaUtil.wrapVariable(TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getPatientStudy?format=" + format +
                "&Collection=" + collection +
                "&PatientID=" + patientID +
                "&StudyInstanceUID=" + studyInstanceUID).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asJson();
    }

    /**
     * /GET series
     *
     * @param iFormat format value. optional.
     * @param iCollection collection value. optional.
     * @param iPatientID ID of the patient. optional.
     * @param iStudyInstanceUID ID of the study instance. optional.
     * @param iModality modality value. optional.
     * @return series
     * @throws UnirestException
     */
    public HttpResponse getSeries(String iFormat, String iCollection, String iPatientID,
                                         String iStudyInstanceUID, String iModality) throws UnirestException {
        String format = TciaUtil.wrapVariable(TCIAConstants.FORMAT, iFormat);
        String collection = TciaUtil.wrapVariable(TCIAConstants.COLLECTION, iCollection);
        String patientID = TciaUtil.wrapVariable(TCIAConstants.PATIENT_ID, iPatientID);
        String studyInstanceUID = TciaUtil.wrapVariable(TCIAConstants.STUDY_INSTANCE_UID, iStudyInstanceUID);
        String modality = TciaUtil.wrapVariable(TCIAConstants.MODALITY, iModality);

        return Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getSeries?format=" + format +
                "&Collection=" + collection +
                "&PatientID=" + patientID +
                "&StudyInstanceUID=" + studyInstanceUID +
                "&Modality=" + modality).
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
        HttpResponse<InputStream> request = Unirest.get(TCIAConstants.MASHAPE_BASE_URL +
                "getImage?SeriesInstanceUID=" +
                seriesInstanceUID).
                header("X-Mashape-Authorization", TCIAConstants.MASHAPE_AUTHORIZATION).
                header("api_key", TCIAConstants.API_KEY).
                asBinary();
        logger.info(request.getHeaders());
        try {
            TciaUtil.saveTo(request.getBody(), seriesInstanceUID + ".zip", ".");
        } catch (IOException e) {
            logger.error("Error when downloading the image", e);
        }
        return request;
    }

}
