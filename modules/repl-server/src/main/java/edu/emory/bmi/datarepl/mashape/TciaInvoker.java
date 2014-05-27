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
import edu.emory.bmi.datarepl.core.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Invokes TCIA through Mashape.
 */
public class TciaInvoker {
    private static Logger logger = LogManager.getLogger(TciaInvoker.class.getName());

    /**
     * /GET the collection values
     * @return collection values
     * @throws UnirestException
     */
    public static HttpResponse getCollectionValues() throws UnirestException {
        return Unirest.get("https://tcia.p.mashape.com/getCollectionValues?format=%3Cformat%3E")
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET the values of the body parts
     * @return bodyPartValues
     * @throws UnirestException
     */
    public static HttpResponse getBodyPartValues() throws UnirestException {
        return Unirest.get("https://tcia.p.mashape.com/getBodyPartValues?format=%3Cformat%3E&Collection=%3CCollection%3E&BodyPartExamined=%3CBodyPartExamined%3E&Modality=%3CModality%3E")
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET Manufacturer values
     * @return manufacturer values
     * @throws UnirestException
     */
    public static HttpResponse getManufacturerValues() throws UnirestException {
        return Unirest.get("https://tcia.p.mashape.com/getManufacturerValues?format=%3Cformat%3E&Collection=%3CCollection%3E&BodyPartExamined=%3CBodyPartExamined%3E&Modality=%3CModality%3E")
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET Modality values
     * @return modality values
     * @throws UnirestException
     */
    public static HttpResponse getModalityValues() throws UnirestException {
        return Unirest.get("https://tcia.p.mashape.com/getModalityValues?Collection=%3CCollection%3E&BodyPartExamined=%3CBodyPartExamined%3E&Modality=%3CModality%3E&format=%3Cformat%3E")
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET patient
     * @return patient
     * @throws UnirestException
     */
    public static HttpResponse getPatient() throws UnirestException {
        return  Unirest.get("https://tcia.p.mashape.com/getPatient?format=%3Cformat%3E&Collection=%3CCollection%3E")
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET patientStudy
     * @return patientStudy
     * @throws UnirestException
     */
    public static HttpResponse getPatientStudy() throws UnirestException {
        return Unirest.get("https://tcia.p.mashape.com/getPatientStudy?format=%3Cformat%3E&Collection=%3CCollection%3E&PatientID=%3CPatientID%3E&StudyInstanceUID=%3CStudyInstanceUID%3E")
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET series
     * @return series
     * @throws UnirestException
     */
    public static HttpResponse getSeries() throws UnirestException {
        return Unirest.get("https://tcia.p.mashape.com/getSeries?format=%3Cformat%3E&Collection=%3CCollection%3E&PatientID=%3CPatientID%3E&StudyInstanceUID=%3CStudyInstanceUID%3E&Modality=%3CModality%3E")
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asJson();
    }

    /**
     * /GET Image
     * @param SeriesInstanceUID, instance ID of the series
     * @return image as a zip
     * @throws UnirestException
     */
    public static HttpResponse getImage(String SeriesInstanceUID) throws UnirestException {
//        Unirest.get("https://tcia.p.mashape.com/getImage?SeriesInstanceUID=%3CSeriesInstanceUID%3E")
        return Unirest.get("https://tcia.p.mashape.com/getImage?SeriesInstanceUID="+SeriesInstanceUID)
                .header("X-Mashape-Authorization", CommonConstants.MASHAPE_AUTHORIZATION)
                .header("api_key", CommonConstants.API_KEY)
                .asBinary();
    }
}
