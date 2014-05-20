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
import java.util.UUID;

/**
 * The core class of Infinispan data access integration
 */
public class InfDataAccessIntegration {
    private static InfDataAccessIntegration infDataAccessIntegration = null;
    protected static Cache<Long, String> defaultCache;
    private static Logger logger = LogManager.getLogger(InfDataAccessIntegration.class.getName());


    /**
     * Singleton. Prevents initialization from outside the class.
     *
     * @throws IOException, if getting the cache failed.
     */
    protected InfDataAccessIntegration() throws IOException {
        DefaultCacheManager manager = new DefaultCacheManager(InfConstants.INFINISPAN_CONFIG_FILE);
        defaultCache = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);
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
    public static Cache<Long, String> getDefaultCache() {
        return defaultCache;
    }

    /**
     * PUT /putReplicaSet
     *
     * @param replicaSet, the query that to be stored
     * @return replicaSetId: Long
     */
    public static long putReplicaSet(String replicaSet) {
        long replicaSetId = UUID.randomUUID().getLeastSignificantBits();
        defaultCache.put(replicaSetId, replicaSet);
        return replicaSetId;
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public static String getReplicaSet(long replicaSetId) {
        return defaultCache.get(replicaSetId);
    }

    /**
     * DELETE /deleteReplicaSet
     * @param replicaSetId the id of the replica to be evicted.
     * @return true, if evicted now. False, if not available.
     */
    public static boolean deleteReplicaSet(long replicaSetId) {
        if (defaultCache.get(replicaSetId) == null) {
            return false;
        } else {
            defaultCache.evict(replicaSetId);
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
        defaultCache.put(replicaSetId, newReplicaSet); //TODO: this could be adding some changes. Not merely replacing.
        return newReplicaSet;
    }


    /**
     * Makes a duplicate of an existing replica set.
     * @param replicaSetId the id of the replica set to be duplicated.
     * @return the id of the duplicate replica set.
     */
    public static long duplicateReplicaSet(long replicaSetId) {
        long duplicateReplicaSetId = UUID.randomUUID().getLeastSignificantBits();
        String replicaSet = getReplicaSet(replicaSetId);
        defaultCache.put(duplicateReplicaSetId, replicaSet);
        return duplicateReplicaSetId;
    }
}

