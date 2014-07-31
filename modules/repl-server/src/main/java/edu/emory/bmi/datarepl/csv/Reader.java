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
 * Reads the CSV Meta File
 */
public class Reader {
    private static Logger logger = LogManager.getLogger(Reader.class.getName());

    /**
     * Parsing the CSV Meta file
     */
    public static void readCSV() {

        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(CommonConstants.META_CSV_FILE));
            while ((line = br.readLine()) != null) {

                String[] entry = line.split(ParsingConstants.CSV_SPLIT_BY);
                int length = entry.length;

                String[] metaArray = new String[length - 1];
                System.arraycopy(entry, 1, metaArray, 0, length - 1);

                CSVInfDai.getCsvMetaMap().put(entry[0], metaArray);

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
}