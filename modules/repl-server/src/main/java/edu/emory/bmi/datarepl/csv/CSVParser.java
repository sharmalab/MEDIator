/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.csv;

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
    public static void parseCSV() {

        BufferedReader br = null;
        String line = "";
        int currentLine = 1;

        try {
            br = new BufferedReader(new FileReader(CommonConstants.META_CSV_FILE));
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
                    updateMetaData(entry, metaArray);
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
     * @param entry, entry to be updated as a string array of properties
     * @param metaArray, meta array to be stored
     */
    public static void updateMetaData(String[] entry, String[] metaArray) {
        if (!entry[58].contains(ParsingConstants.NA)) {
            CSVInfDai.getCsvMetaMap().put(entry[ParsingConstants.INDEX], metaArray);
            if (CSVInfDai.getMetaMap().get(entry[ParsingConstants.INDEX]) != null) {
                Boolean[] temp = CSVInfDai.getMetaMap().get(entry[ParsingConstants.INDEX]);
                if (temp[3]) {
                    CSVInfDai.getMetaMap().put(entry[ParsingConstants.INDEX], ParsingConstants.EXISTING_EVERYWHERE);
                    logger.info("Existing entry is updated in the map.." + entry[ParsingConstants.INDEX]);
                } else {
                    CSVInfDai.getMetaMap().put(entry[ParsingConstants.INDEX], ParsingConstants.DOES_NOT_EXIST_IN_S3);
                    logger.info("Entry without S3, is updated in the map.." + entry[ParsingConstants.INDEX]);
                }
            } else {
                CSVInfDai.getMetaMap().putIfAbsent(entry[ParsingConstants.INDEX], ParsingConstants.NEW_ENTRY);
                logger.info("Adding new entry to the meta map.." + entry[ParsingConstants.INDEX]);
            }
        }
    }
}
