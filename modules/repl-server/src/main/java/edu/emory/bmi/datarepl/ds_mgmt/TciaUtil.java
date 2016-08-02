/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.ds_mgmt;

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
        logger.info("\n\n[getImage]: Downloaded the file: " + name);
        fos.close();
        in.close();
    }

    /**
     * Wrap the variables for TCIA API
     *
     * @param variableName, the name of the variable to be wrapped
     * @param inputValue,   the input value from the user.
     * @return the wrapped value.
     */
    public static String wrapVariable(String variableName, String inputValue) {
        String wrappedValue;
        if (inputValue == null || inputValue.length() <= 0) {
            wrappedValue = "%3C" + variableName + "%3E";
        } else {
            wrappedValue = inputValue;
        }
        return wrappedValue;
    }

    /**
     * Add parameters to form the query
     *
     * @param output       output query
     * @param variableName the parameter name to be added
     * @param value        value of the parameter to be added
     * @return output
     */
    public static String addParam(String output, String variableName, String value) {
        String join = output.length() == 0 ? "?" : "&";
        if (value != null) {
            output += join + variableName + "=" + value;
        }
        return output;
    }
}

