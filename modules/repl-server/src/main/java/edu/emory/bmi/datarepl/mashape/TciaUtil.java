/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.mashape;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility classes for the TCIA integration
 */
public class TciaUtil {
    private static Logger logger = LogManager.getLogger(TciaUtil.class.getName());

    /**
     * Saves an input stream as a file in the given directory.
     *
     * @param in        InputStream
     * @param name      fileName
     * @param directory directory name
     * @throws IOException
     */
    public static void saveTo(InputStream in, String name, String directory) throws IOException {
        FileOutputStream fos = new FileOutputStream(directory + "/" + name);
        byte[] buffer = new byte[4096];
        int read;
        while ((read = in.read(buffer)) > 0) {
            fos.write(buffer, 0, read);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Downloaded the file: " + name);
        }
        fos.close();
        in.close();
    }

    /**
     * Wrap the variables for TCIA API
     * @param variableName, the name of the variable to be wrapped
     * @param inputValue, the input value from the user.
     * @return the wrapped value.
     */
    public static String wrapVariable(String variableName, String inputValue) {
        String wrappedValue;
        if (inputValue.length() > 0) {
            wrappedValue = inputValue;
        } else {
            wrappedValue = "%3C" + variableName + "%3E";
        }
        return wrappedValue;
    }
}

