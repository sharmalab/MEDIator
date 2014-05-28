/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.infinispan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * The core class of Infinispan data access integration
 */
public class InfDataAccessIntegration {
    private static InfDataAccessIntegration infDataAccessIntegration = null;
    protected static Cache<Long, String> replicaSetsMap;
    protected static Cache<Long, Long[]> userReplicasMap;

    private static Logger logger = LogManager.getLogger(InfDataAccessIntegration.class.getName());


    /**
     * Singleton. Prevents initialization from outside the class.
     *
     * @throws IOException, if getting the cache failed.
     */
    protected InfDataAccessIntegration() throws IOException {
        DefaultCacheManager manager = new DefaultCacheManager(InfConstants.INFINISPAN_CONFIG_FILE);
        replicaSetsMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);
        userReplicasMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);
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
     * Gets the default cache
     *
     * @return the cache
     */
    public static Cache<Long, String> getReplicaSetsMap() {
        return replicaSetsMap;
    }

    public static Cache<Long, Long[]> getUserReplicasMap() {
        return userReplicasMap;
    }

    /**
     * Creates a replicaSet
     * @param userId the user that the replicaSet is associated with
     * @param replicaSet the replica set
     * @return replicaSetId
     */
    public static long createReplicaSet(long userId, String replicaSet) {
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
    public static void createMultipleReplicaSets(long userId, String[] replicaSet) {
        for (String aReplicaSet : replicaSet) {
            long replicaSetId = UUID.randomUUID().getLeastSignificantBits();
            putReplicaSet(replicaSetId, aReplicaSet);
            addToUserReplicasMap(userId, replicaSetId);
        }
    }

    /**
     * /PUSH add replicaSetId to the userReplicasMap
     * @param userId Id of the user
     * @param repicaSetId new replicaSetIds to be added
     */
    public static void addToUserReplicasMap(long userId, long repicaSetId) {
        Long replicaSetIDs[];
        if (userReplicasMap.get(userId) != null) {
            replicaSetIDs = Arrays.copyOf(userReplicasMap.get(userId), userReplicasMap.get(userId).length + 1);
            replicaSetIDs[replicaSetIDs.length - 1] = repicaSetId;
        }
        else {
            replicaSetIDs = new Long[1];
            replicaSetIDs[0] = repicaSetId;
        }
        userReplicasMap.put(userId, replicaSetIDs);
    }

    /**
     * PUT /putReplicaSet
     *
     * @param replicaSetId, the id of the replica set.
     * @param replicaSet, the query that to be stored
     * @return replicaSetId: Long
     */
    public static long putReplicaSet(long replicaSetId, String replicaSet) {
        replicaSetsMap.put(replicaSetId, replicaSet);
        return replicaSetId;
    }

    /**
     * GET /getUserReplicasMap
     *
     * @param replicaSetId, long
     * @return userReplicas: Long[]
     */
    public static Long[] getUserReplicasMap(long replicaSetId) {
        return userReplicasMap.get(replicaSetId);
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public static String getReplicaSet(long replicaSetId) {
        return replicaSetsMap.get(replicaSetId);
    }

    /**
     * DELETE /deleteReplicaSet
     * @param replicaSetId the id of the replica to be evicted.
     * @return true, if evicted now. False, if not available.
     */
    public static boolean deleteReplicaSet(long replicaSetId) {
        if (replicaSetsMap.get(replicaSetId) == null) {
            return false;
        } else {
            replicaSetsMap.evict(replicaSetId);
            return true;
        }
    }

    /**
     * PUSH /pushChangesToReplicaSet
     * @param replicaSetId, the id of the replica to be modified.
     * @param newReplicaSet, the new replicaSet.
     * @return the updated replica set.
     */
    public static String pushChangesToReplicaSet(long replicaSetId, String newReplicaSet) {
        replicaSetsMap.put(replicaSetId, newReplicaSet); //TODO: this could be adding some changes. Not merely replacing.
        return newReplicaSet;
    }


    /**
     * Makes a duplicate of an existing replica set.
     * @param replicaSetId the id of the replica set to be duplicated.
     * @param userId the user who is duplicating the replica.
     * @return the id of the duplicate replica set.
     */
    public static long duplicateReplicaSet(long replicaSetId, long userId) {
        long duplicateReplicaSetId = UUID.randomUUID().getLeastSignificantBits();
        String replicaSet = getReplicaSet(replicaSetId);
        replicaSetsMap.put(duplicateReplicaSetId, replicaSet);
        addToUserReplicasMap(duplicateReplicaSetId, userId);
        return duplicateReplicaSetId;
    }
}

