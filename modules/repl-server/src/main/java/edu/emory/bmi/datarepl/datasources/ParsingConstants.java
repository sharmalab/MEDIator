/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.datasources;

/**
 * Constants related to parsing CSV.
 */
public final class ParsingConstants {
    public static final int INDEX = 58;
    public static final int DATA_START_LINE = 2;
    public static final String NA = "[Not Available]";
    public static final String CSV_SPLIT_BY = ",";

    public static final int MAX_LINES = 50000;
    public static final Boolean[] EXISTS_EVERYWHERE = {true, true, true, true};
    public static final Boolean[] NOT_KNOWN_YET = {false, false, false, false};

    public static final String CA_MICROSCOPE_BASE_URL = "http://imaging.cci.emory.edu/camicroscope/camicroscope/";
    public static final String CA_MICROSCOPE_QUERY_URL = "osdCamicroscope.php?tissueId=";

    public static final int CSV_META_POSITION = 0;
    public static final int CA_META_POSITION = 1;
    public static final int TCIA_META_POSITION = 2;
    public static final int S3_META_POSITION = 3;

    public static final String S3_BASE_URL = "https://s3.amazonaws.com/dataReplServer/";
    public static final String S3_LEVEL1 = "level1";
    public static final String S3_LEVEL2 = "level2";
    public static final String S3_LEVEL3 = "level3";
    public static final String URL_SEPARATOR = "/";


}
