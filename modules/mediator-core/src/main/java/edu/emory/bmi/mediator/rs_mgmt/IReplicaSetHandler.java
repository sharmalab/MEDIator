/*
 * Title:        MEDIator Data Sharing Framework
 * Description:  Data Sharing for Reproducible Scientific Research.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.mediator.rs_mgmt;

/**
 * API for Publish/Consume action
 */
public interface IReplicaSetHandler {

    public long createReplicaSet(String userId, String replicaSet);

    public long putReplicaSet(long replicaSetId, String replicaSet);

    public String getReplicaSet(long replicaSetId);

    public String updateReplicaSet(long replicaSetId, String newReplicaSet);

    public boolean deleteReplicaSet(String userId, long replicaSetId);

    public long duplicateReplicaSet(long replicaSetId, String userId);
}
