/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.exception;

/**
 * MEDIator general Exception
 */
public class MediatorException extends Exception {

    private static final long serialVersionUID = -2548423882689490255L;
    private String replicaSet;

    public MediatorException(String replicaSet) {
        super();
        this.replicaSet = replicaSet;
    }

    public MediatorException(String message, Throwable cause, String replicaSet) {
        super(message, cause);
        this.replicaSet = replicaSet;
    }

    public MediatorException(String message, String replicaSet) {
        super(message);
        this.replicaSet = replicaSet;
    }

    public MediatorException(Throwable cause, String replicaSet) {
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