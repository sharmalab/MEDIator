/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.csv;

/**
 * Constants related to parsing CSV.
 */
public final class ParsingConstants {
    public static final int INDEX = 58;
    public static final int DATA_START_LINE = 2;
    public static final String NA = "[Not Available]";
    public static final String CSV_SPLIT_BY = ",";

    public static final int MAX_LINES = 500;
    public static final Boolean[] NEW_ENTRY = {true, true, true, false};
    public static final Boolean[] EXISTING_EVERYWHERE = {true, true, true, true};
    public static final Boolean[] DOES_NOT_EXIST_IN_S3 = {true, true, true, false};

    public static final String CA_MICROSCOPE_BASE_URL = "http://imaging.cci.emory.edu/camicroscope/camicroscope/";
    public static final String CA_MICROSCOPE_QUERY_URL = "osdCamicroscope.php?tissueId=";

}
