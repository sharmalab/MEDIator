/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.csv;

import edu.emory.bmi.datarepl.constants.InfConstants;
import edu.emory.bmi.datarepl.core.InfDataAccessIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.Cache;

import java.io.IOException;

/**
 * Data Access Integration from CSV.
 */
public class CSVInfDai extends InfDataAccessIntegration{
    private static CSVInfDai infDataAccessIntegration = null;

    private static Logger logger = LogManager.getLogger(CSVInfDai.class.getName());

    protected static Cache<String, Boolean[]> metaMap; /*csv, ca, tcia, s3*/
    protected static Cache<String, String[]> csvMetaMap;

    /**
     * Singleton. Prevents initialization from outside the class.
     *
     * @throws java.io.IOException, if getting the cache failed.
     */
    protected CSVInfDai() throws IOException {
        super();
        metaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);
        csvMetaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_META);
        logger.info("Initialized the Infinispan Cache for the TCIA Data Replication Tool..");
    }

    /**
     * Initializes Infinispan
     */
    public static InfDataAccessIntegration getInfiniCore() {
        if (infDataAccessIntegration == null) {
            try {
                infDataAccessIntegration = new CSVInfDai();
            } catch (IOException e) {
                logger.error("Exception when trying to initialize Infinispan.", e);
            }
        }
        return infDataAccessIntegration;
    }

    public static Cache<String, String[]> getCsvMetaMap() {
        return csvMetaMap;
    }

    public static Cache<String, Boolean[]> getMetaMap() {
        return metaMap;
    }
}
