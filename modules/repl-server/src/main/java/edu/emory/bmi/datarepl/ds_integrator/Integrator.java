/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_integrator;

/**
 * The Integrator interface to integrate with data sources.
 */
public interface Integrator {
    /**
     * Updates existence in the meta map
     *
     * @param key,            key of the object
     * @param metaArrayIndex, index in the meta map
     * @param existence,      existence, true or false.
     */
    public static void updateExistenceInDataSource(String key, int metaArrayIndex, boolean existence) {
    }

    /**
     * Downloads the raw data from the data source
     *
     * @param key, id of the object.
     */
    public static void getRawData(String key) {
        //implement the JNLP/Java Web Start code for downloading the respective file for the replicaSetIDS as a zip.
    }

    /**
     * Does the data source exists.
     *
     * @param key,            patientId
     * @param metaArrayIndex, index in the meta array
     * @return if exists.
     */
    public static boolean doesExistInDataSource(String key, int metaArrayIndex) {
        return false;
    }

    /**
     * /GET Gets the value stored against the key from the map.
     *
     * @param key, id of the object.
     * @return the object
     */
    public static String getMetaData(String key) {
        return null;
    }

    /**
     * /PUT Store the value against the key in the map.
     *
     * @param key,    id of the object.
     * @param object, value of the object
     * @return the object
     */
    public static String putMetaData(String key, String object) {
        return null;
    }

    /**
     * /PUSH Update the value against the key in the map.
     *
     * @param key,    id of the object.
     * @param object, value of the object
     * @return the object
     */
    public static String updateMetaData(String key, String object) {
        return null;
    }

    /**
     * /DELETE Delete the value stored in the map.
     *
     * @param key,    id of the object.
     */
    public static void deleteMetaData(String key) {
    }

}
