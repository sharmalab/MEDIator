/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.core;

import edu.emory.bmi.datarepl.constants.InfConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * The core class of Infinispan data access integration - Publisher/Consumer API.
 */
public class InfDataAccessIntegration implements PubConsAPI {
    private static InfDataAccessIntegration infDataAccessIntegration = null;

    protected static Cache<Long, String> replicaSetsMap;
    protected static Cache<String, Long[]> userReplicasMap;

    protected static Cache<String, Boolean[]> metaMap; /*csv, ca, tcia, s3*/

    protected static Cache<String, String[]> csvMetaMap;
    protected static Cache<String, String> caMetaMap;
    protected static Cache<String, String> s3MetaMap;
    protected static Cache<Long, Boolean[]> tciaMetaMap;


    protected DefaultCacheManager manager;

    private static Logger logger = LogManager.getLogger(InfDataAccessIntegration.class.getName());


    /**
     * Singleton. Prevents initialization from outside the class.
     *
     * @throws IOException, if getting the cache failed.
     */
    protected InfDataAccessIntegration() throws IOException {
        manager = new DefaultCacheManager(InfConstants.INFINISPAN_CONFIG_FILE);
        userReplicasMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);
        replicaSetsMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_RS);

        metaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);

        csvMetaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_META);
        s3MetaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_S3);
        tciaMetaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_META);
        caMetaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_CA);

        logger.info("Initialized the Infinispan Cache for the Data Replication Tool..");
    }

    /**
     * Initializes Infinispan
     */
    public static InfDataAccessIntegration getInfiniCore() {
        if (infDataAccessIntegration == null) {
            try {
                infDataAccessIntegration = new InfDataAccessIntegration();
            } catch (IOException e) {
               logger.error("Exception when trying to initialize Infinispan.", e);
            }
        }
        return infDataAccessIntegration;
    }

    /**
     * Gets the replica sets map
     *
     * @return the replica sets map
     */
    public Cache<Long, String> getReplicaSetsMap() {
        return replicaSetsMap;
    }

    /**
     * Gets the user replicas map
     *
     * @return the user replicas map
     */
    public Cache<String, Long[]> getUserReplicasMap() {
        return userReplicasMap;
    }

    /**
     * Creates a replicaSet
     * @param userId the user that the replicaSet is associated with
     * @param replicaSet the replica set
     * @return replicaSetId
     */
    public long createReplicaSet(String userId, String replicaSet) {
        long replicaSetId = UUID.randomUUID().getLeastSignificantBits();
        putReplicaSet(replicaSetId, replicaSet);
        addToUserReplicasMap(userId, replicaSetId);
        return replicaSetId;
    }

    /**
     * Creates a replicaSet
     * @param userId the user that the replicaSet is associated with
     * @param replicaSet the array of replica sets
     */
    public void createMultipleReplicaSets(String userId, String[] replicaSet) {
        for (String aReplicaSet : replicaSet) {
            long replicaSetId = UUID.randomUUID().getLeastSignificantBits();
            putReplicaSet(replicaSetId, aReplicaSet);
            addToUserReplicasMap(userId, replicaSetId);
        }
    }

    /**
     * /PUSH add replicaSetId to the userReplicasMap
     * @param userId Id of the user
     * @param replicaSetId new replicaSetIds to be added
     */
    public void addToUserReplicasMap(String userId, long replicaSetId) {
        Long replicaSetIDs[];
        if (userReplicasMap.get(userId) != null) {
            replicaSetIDs = Arrays.copyOf(userReplicasMap.get(userId), userReplicasMap.get(userId).length + 1);
            replicaSetIDs[replicaSetIDs.length - 1] = replicaSetId;
        }
        else {
            replicaSetIDs = new Long[1];
            replicaSetIDs[0] = replicaSetId;
        }
        userReplicasMap.put(userId, replicaSetIDs);
    }

    /**
     * PUT /putMetaMap
     *
     * @param replicaSetId, the id of the replica set.
     * @param replicaSet, the query that to be stored
     * @return replicaSetId: Long
     */
    public long putReplicaSet(long replicaSetId, String replicaSet) {
        replicaSetsMap.put(replicaSetId, replicaSet);
        return replicaSetId;
    }

    /**
     * GET /getUserReplicaSets
     *
     * @param userId, String
     * @return userReplicas: Long[]
     */
    public Long[] getUserReplicaSets(String userId) {
        return userReplicasMap.get(userId);
    }

    /**
     * GET /getUses
     *
     * @return users: String[]
     */
    public String[] getUsers() {
        Object[] temp = userReplicasMap.keySet().toArray();
        String[] users = new String[temp.length];
        for (int i = 0; i < temp.length; i++) {
            users[i] = temp[i].toString();
        }
        return users;
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public String getReplicaSet(long replicaSetId) {
        return replicaSetsMap.get(replicaSetId);
    }

    /**
     * DELETE /deleteReplicaSet
     * @param replicaSetId the id of the replica to be evicted.
     * @return true, if evicted now. False, if not available.
     */
    public boolean deleteReplicaSet(String userId, long replicaSetId) {
        if (replicaSetsMap.get(replicaSetId) == null) {
            return false;
        } else {
            replicaSetsMap.remove(replicaSetId);
            Long[] replicas = userReplicasMap.get(userId);
            Long[] newReplicas = new Long[0];
            int j = 0;
            for (Long replica : replicas) {
                if (replica != replicaSetId) {
                    newReplicas[j] = replica;
                    j++;
                }
            }
            userReplicasMap.put(userId, newReplicas);
            return true;
        }
    }

    /**
     * PUSH /updateReplicaSet
     * @param replicaSetId, the id of the replica to be modified.
     * @param newReplicaSet, the new replicaSet.
     * @return the updated replica set.
     */
    public String updateReplicaSet(long replicaSetId, String newReplicaSet) {
        replicaSetsMap.put(replicaSetId, newReplicaSet);
        return newReplicaSet;
    }


    /**
     * Makes a duplicate of an existing replica set.
     * @param replicaSetId the id of the replica set to be duplicated.
     * @param userId the user who is duplicating the replica.
     * @return the id of the duplicate replica set.
     */
    public long duplicateReplicaSet(long replicaSetId, String userId) {
        long duplicateReplicaSetId = UUID.randomUUID().getLeastSignificantBits();
        String replicaSet = getReplicaSet(replicaSetId);
        replicaSetsMap.put(duplicateReplicaSetId, replicaSet);
        addToUserReplicasMap(userId, duplicateReplicaSetId);
        return duplicateReplicaSetId;
    }

    public void getRawData(String key) {
        //implement the JNLP/Java Web Start code for downloading the respective file for the replicaSetIDS as a zip.
    }


    public static Cache<String, String[]> getCsvMetaMap() {
        return csvMetaMap;
    }

    public static Cache<String, Boolean[]> getMetaMap() {
        return metaMap;
    }

    public static Cache<String, String> getS3MetaMap() {
        return s3MetaMap;
    }

    public static Cache<String, String> getCaMetaMap() {
        return caMetaMap;
    }


    /**
     * GET /getTciaMetaMap
     * Gets the meta map entry of the given replicaSetID.
     *
     * @param replicaSetId, long
     * @return replicaSet:Boolean[]
     */
    public Boolean[] getTciaMetaMap(long replicaSetId) {
        return tciaMetaMap.get(replicaSetId);
    }

}

