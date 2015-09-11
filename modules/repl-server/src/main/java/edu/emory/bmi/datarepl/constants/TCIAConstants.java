/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.constants;

/**
 * Common TCIA and DataReplServer Constants
 */
public final class TCIAConstants {

    // Old testing url
    // https://services-test.cancerimagingarchive.net/services/TCIA/TCIA/query"; // Base URL of the service (Test)

//    Old url
//    public static String BASE_URL = "https://services.cancerimagingarchive.net/services/TCIA/TCIA/query";

//    New Url
    public static String BASE_URL = "https://services.cancerimagingarchive.net/services/v3/TCIA/query";

    public static String API_KEY = "7bc5fd7f-0ac8-40b3-a8a7-d06a0cb157c2";

    public static String MASHAPE_AUTHORIZATION = "7AErcmPVXcqsOT13K1Ij0bLVYL8RVvZ6";

    public static String FORMAT = "format";

    public static String COLLECTION = "Collection";

    public static String BODY_PART_EXAMINED = "BodyPartExamined";

    public static String MODALITY = "Modality";

    public static String PATIENT_ID = "PatientID";

    public static String STUDY_INSTANCE_UID = "StudyInstanceUID";

    public static String MASHAPE_BASE_URL = "https://tcia.p.mashape.com/";

    public static String IMAGE_TAG = "IMG__";

    public static String META_TAG = "META__";
}
