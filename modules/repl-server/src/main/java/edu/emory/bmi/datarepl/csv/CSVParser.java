/*
 * Title:        Cloud2Sim
 * Description:  Distributed and Concurrent Cloud Simulation
 *                Toolkit for Modeling and Simulation
 *                of Clouds - Enhanced version of CloudSim.
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
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
import java.util.Map;

/**
 * Parses the CSV Meta File and stores the meta data into Infinispan.
 */
public class CSVParser {
    private static Logger logger = LogManager.getLogger(CSVParser.class.getName());
    private static final int index = 58;
    private static final int dataStartLine = 2;
    private static final String NA = "[Not Available]";
    private static final int maxLines = 500;

    public static void parseCSV() {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int currentLine = 1;

        try {
            br = new BufferedReader(new FileReader(CommonConstants.META_CSV_FILE));
            while ((line = br.readLine()) != null) {
                if (currentLine >= dataStartLine) {

                    String[] entry = line.split(cvsSplitBy);
                    int length = entry.length;

                    String[] metaArray = new String[length - 1];
                    int j = 0;
                    for (int i = 0; i < length - 1; i++) {
                        if (j != index) {
                            metaArray[i] = entry[j];
                            j++;
                        }
                    }
                    if (!entry[58].contains(NA)) {
                        CSVInfDai.getCsvMetaMap().put(entry[58], metaArray);
                    }
                }
                currentLine++;
                if (currentLine >= maxLines) {
                    break;
                }
            }

            //loop map
            for (Map.Entry<String, String[]> entry : CSVInfDai.getCsvMetaMap().entrySet()) {

                logger.info("ID:= " + entry.getKey() + " , length="
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
        logger.info("Done parsing the CSV file..");
    }
}
