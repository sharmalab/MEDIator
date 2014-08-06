/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.datasources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parses the CSV Meta File and stores the meta data into Infinispan.
 */
public class CSVParser {
    private static Logger logger = LogManager.getLogger(CSVParser.class.getName());

    /**
     * Parsing the CSV Meta file
     */
    public static void parseCSV(String fileName, int meta, int parsingIndex, String splitBy) {

        BufferedReader br = null;
        String line = "";
        int currentLine = 1;

        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                if (currentLine >= DataSourcesConstants.DATA_START_LINE) {

                    String[] entry = line.split(splitBy);
                    int length = entry.length;

                    String[] metaArray = new String[length - 1];
                    int j = 0;
                    for (int i = 0; i < length - 1; i++) {
                        if (j != parsingIndex) {
                            metaArray[i] = entry[j];
                            j++;
                        }
                    }
                    try {
                        String key = entry[parsingIndex];
                        updateMetaData(key, metaArray, meta);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Exception in parsing the line", ignored);
                        }
                        break;
                    }
                }
                currentLine++;
                if (currentLine >= DataSourcesConstants.MAX_LINES) {
                    break;
                }
            }

        } catch (IOException e) {
            logger.error("Error in getting the CSV file", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("Error in closing the file", e);
                }
            }
        }
        logger.info("Done parsing the CSV file..");
    }

    /**
     * Update meta data
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     * @param meta,      location in the meta array
     */
    public static void updateMetaData(String key, String[] metaArray, int meta) {
        if (meta == DataSourcesConstants.CSV_META_POSITION) {
            updateMetaDataWithCSV(key, metaArray);
        } else if (meta == DataSourcesConstants.S3_META_POSITION) {
            updateMetaDataWithS3Entry(key, metaArray);
        }
    }

    /**
     * Update meta data with CSV entry
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaDataWithCSV(String key, String[] metaArray) {
        if (!key.contains(DataSourcesConstants.NA)) {
            CSVInfDai.getCsvMetaMap().put(key, metaArray);
            DataSourcesIntegrator.updateExistenceInDataSource(key, DataSourcesConstants.CSV_META_POSITION, true);
        }
    }

    /**
     * Update meta data with CSV entry
     *
     * @param longKey,   contains patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaDataWithS3Entry(String longKey, String[] metaArray) {
        String patientID = longKey.substring(0, 12);
        CSVInfDai.getS3MetaMap().put(patientID, metaArray[0]);
        DataSourcesIntegrator.updateExistenceInDataSource(patientID, DataSourcesConstants.S3_META_POSITION, true);
    }
}
