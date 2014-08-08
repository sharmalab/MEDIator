/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.constants;

/**
 * Constants specific to Infinispan In-Memory Data Grid Integration.
 */
public final class InfConstants {
    public static final String INFINISPAN_CONFIG_FILE = "conf/infinispan.xml";

    public static String TRANSACTIONAL_CACHE = "transactional";
    public static String TRANSACTIONAL_CACHE_RS = "transactional_rs";
    public static String TRANSACTIONAL_CACHE_META = "transactional_meta";
    public static String TRANSACTIONAL_CACHE_S3 = "transactional_s3";
    public static String TRANSACTIONAL_CACHE_CA = "transactional_ca";
    public static String TRANSACTIONAL_CACHE_COLLECTIONS = "transactional_collections";
    public static String TRANSACTIONAL_CACHE_PATIENTS = "transactional_patients";
    public static String TRANSACTIONAL_CACHE_STUDIES = "transactional_studies";
    public static String TRANSACTIONAL_CACHE_SERIES = "transactional_series";

}
