/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import edu.emory.bmi.datarepl.ds_mgmt.TciaDSManager;
import edu.emory.bmi.datarepl.rs_mgmt.TciaReplicaSetHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The core Initializer class for TCIA
 */
public class TciaInitializer {
    private static Logger logger = LogManager.getLogger(TciaInitializer.class.getName());
    private static TciaReplicaSetHandler tciaReplicaSetHandler;
    private static TciaDSManager tciaDSManager;


    /**
     * Initiate replication and synchronization tool at the start time, without the user id.
     */
    public void init() {
        tciaReplicaSetHandler = (TciaReplicaSetHandler) TciaReplicaSetHandler.getInfiniCore();
        tciaDSManager = new TciaDSManager();
        logger.info("MEDIator has been initialized..");
    }

    public static TciaReplicaSetHandler getTciaReplicaSetHandler() {
        return tciaReplicaSetHandler;
    }

    public static TciaDSManager getTciaDSManager() {
        return tciaDSManager;
    }
}
