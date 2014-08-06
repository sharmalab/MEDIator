/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.datasources;

import edu.emory.bmi.datarepl.constants.CommonConstants;
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
    public static void parseCSV(String fileName, int meta) {

        BufferedReader br = null;
        String line = "";
        int currentLine = 1;

        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                if (currentLine >= ParsingConstants.DATA_START_LINE) {

                    String[] entry = line.split(ParsingConstants.CSV_SPLIT_BY);
                    int length = entry.length;

                    String[] metaArray = new String[length - 1];
                    int j = 0;
                    for (int i = 0; i < length - 1; i++) {
                        if (j != ParsingConstants.INDEX) {
                            metaArray[i] = entry[j];
                            j++;
                        }
                    }
                    String key = entry[ParsingConstants.INDEX];
                    updateMetaData(key, metaArray, meta);
                }
                currentLine++;
                if (currentLine >= ParsingConstants.MAX_LINES) {
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
        if (!key.contains(ParsingConstants.NA)) {
            CSVInfDai.getCsvMetaMap().put(key, metaArray);
            DataSourcesIntegrator.updateExistenceInDataSource(key, meta, true);
        }
    }

    /**
     * Update meta data with CSV entry
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaDataWithCSV(String key, String[] metaArray) {
        updateMetaData(key, metaArray, ParsingConstants.CSV_META_POSITION);
    }

    /**
     * Update meta data with CSV entry
     *
     * @param key,       patient ID
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaDataWithS3Entry(String key, String[] metaArray) {
        updateMetaData(key, metaArray, ParsingConstants.S3_META_POSITION);
    }
}