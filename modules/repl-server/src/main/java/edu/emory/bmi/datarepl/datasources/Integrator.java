/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.datasources;

/**
 * The Integrator interface to integrate with data sources.
 */
public interface Integrator {
    public static void updateExistenceInDataSource(String key, int metaArrayIndex, boolean existence) {
    }
}
