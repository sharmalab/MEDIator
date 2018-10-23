/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.csv;

import edu.emory.bmi.mediator.integrator.ReplicaSetsIntegrator;
import edu.emory.bmi.mediator.integrator.RsIntegratorCore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parses the CSV Meta File(s) and loads the meta data into Infinispan.
 */
public class MetaDataLoader {
    private static Logger logger = LogManager.getLogger(MetaDataLoader.class.getName());
    private static BufferedReader br = null;
    private static String line = "";

    /**
     * Parsing the CSV Meta file
     */
    public static void parseCSV(String fileName, int meta, int parsingIndex, String splitBy) {
        int currentLine = 1;

        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                if (currentLine >= edu.emory.bmi.mediator.constants.DataSourcesConstants.DATA_START_LINE) {

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
                        RsIntegratorCore.updateMetaData(key, metaArray, meta);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Exception in parsing the line", ignored);
                        }
                        break;
                    }
                }
                currentLine++;
                if (currentLine >= edu.emory.bmi.mediator.constants.DataSourcesConstants.MAX_LINES) {
                    break;
                }
            }

        } catch (IOException e) {
            logger.error("Error in getting the CSV file", e);
        } finally {
            closeFile();
        }
        logger.info("Done parsing the CSV file..");
    }

    private static void closeFile() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                logger.error("Error in closing the file", e);
            }
        }
    }


    /**
     * Parsing the CSV Meta file
     */
    public static void readCSV() {
        try {

            br = new BufferedReader(new FileReader(edu.emory.bmi.mediator.constants.DataSourcesConstants.META_CSV_FILE));
            while ((line = br.readLine()) != null) {

                String[] entry = line.split(edu.emory.bmi.mediator.constants.DataSourcesConstants.CSV_SPLIT_BY);
                int length = entry.length;

                String[] metaArray = new String[length - 1];
                System.arraycopy(entry, 1, metaArray, 0, length - 1);

                ReplicaSetsIntegrator.getCsvMetaMap().put(entry[0], metaArray);

            }

        } catch (IOException e) {
            logger.error("Error in getting the CSV file", e);
        } finally {
            closeFile();
        }
        logger.info("Done parsing the CSV file..");
    }
}
