/*
 * Title:        Cloud2Sim
 * Description:  Distributed and Concurrent Cloud Simulation
 *                Toolkit for Modeling and Simulation
 *                of Clouds - Enhanced version of CloudSim.
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
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
import java.util.HashMap;
import java.util.Map;

/**
 * Reads the CSV Meta File
 */
public class Reader {
    private static Logger logger = LogManager.getLogger(Reader.class.getName());

    public static void readCSV() {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(CommonConstants.META_CSV_FILE));
            while ((line = br.readLine()) != null) {

                String[] entry = line.split(cvsSplitBy);
                int length = entry.length;

                String[] metaArray = new String[length-1];
                for (int i = 0; i < length-1; i++) {
                    metaArray[i] = entry[i+1];
                }

                CSVInfDai.getCsvMetaMap().put(entry[0], metaArray);

            }

            //loop map
            for (Map.Entry<String, String[]> entry : CSVInfDai.getCsvMetaMap().entrySet()) {

                System.out.println("ID:= " + entry.getKey() + " , length="
                        + entry.getValue().length + "]");

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
    }
}