/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants related to data sources.
 */
public final class DataSourcesConstants {
    public static final String META_CSV_FILE = "conf/getAllDataAsCSV";
    public static final String S3_META_CSV_FILE = "conf/FILE_SAMPLE_MAP.txt";

    public static final int CSV_META_INDEX = 58;
    public static final int S3_META_INDEX = 1;

    public static final int DATA_START_LINE = 2;
    public static final String NA = "[Not Available]";
    public static final String CSV_SPLIT_BY = ",";
    public static final String S3_SPLIT_BY = "\t";


    public static final int MAX_LINES = 50000;

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

    public static final Map<Integer, String> META_MAP = new HashMap<Integer, String>() {{
        put(CSV_META_POSITION, "CSV");
        put(CA_META_POSITION, "CA");
        put(TCIA_META_POSITION, "TCIA");
        put(S3_META_POSITION, "S3");
    }};
}
