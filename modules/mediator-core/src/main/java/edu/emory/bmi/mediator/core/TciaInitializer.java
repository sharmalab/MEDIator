/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.core;

import edu.emory.bmi.mediator.ds_mgmt.TciaDSManager;
import edu.emory.bmi.mediator.rs_mgmt.TciaReplicaSetHandler;
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
