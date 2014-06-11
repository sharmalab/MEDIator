package edu.emory.bmi.datarepl.core;

/**
 * API for Publish/Consume action
 */
public interface PubConsAPI {

    public long createReplicaSet(String userId, String replicaSet);

    public long putReplicaSet(long replicaSetId, String replicaSet);

    public String getReplicaSet(long replicaSetId);

    public String pushChangesToReplicaSet(long replicaSetId, String newReplicaSet);

    public boolean deleteReplicaSet(long replicaSetId);

    public long duplicateReplicaSet(long replicaSetId, String userId);
}
