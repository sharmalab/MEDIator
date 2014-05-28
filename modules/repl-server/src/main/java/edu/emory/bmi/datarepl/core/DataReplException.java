/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

/**
 * DataReplication Tool general Exception
 */
public class DataReplException  extends Exception {

    private static final long serialVersionUID = -2548423882689490255L;
    private String replicaSet;

    public DataReplException(String replicaSet) {
        super();
        this.replicaSet = replicaSet;
    }

    public DataReplException(String message, Throwable cause, String replicaSet) {
        super(message, cause);
        this.replicaSet = replicaSet;
    }

    public DataReplException(String message, String replicaSet) {
        super(message);
        this.replicaSet = replicaSet;
    }

    public DataReplException(Throwable cause, String replicaSet) {
        super(cause);
        this.replicaSet = replicaSet;
    }

    public String toString()
    {
        return String.format("Replica Set =[%s]\t%s", this.replicaSet , super.toString());
    }

    @Override
    public String getMessage() {
        return String.format("Replica Set =[%s]\t%s", this.replicaSet , super.getMessage());
    }
}