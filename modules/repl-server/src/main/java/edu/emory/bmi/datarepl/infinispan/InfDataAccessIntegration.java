/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.infinispan;

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

    /**
     * Singleton. Prevents initialization from outside the class.
     *
     * @throws IOException, if getting the cache failed.
     */
    protected InfDataAccessIntegration() throws IOException {
        DefaultCacheManager manager = new DefaultCacheManager(InfConstants.INFINISPAN_CONFIG_FILE);
        defaultCache = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);
    }

    /**
     * Initializes Infinispan
     */
    public static void getInfiniCore() {
        if (infDataAccessIntegration == null) {
            try {
                infDataAccessIntegration = new InfDataAccessIntegration();
            } catch (IOException e) {
                System.out.println("Exception when trying to initialize Infinispan." + e);
            }
        }
    }

    /**
     * Gets the default cache
     * @return the cache
     */
    public static Cache<Long, String> getDefaultCache() {
        return defaultCache;
    }

    /**
     * PUT /putReplicaSet
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
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public static String getReplicaSet(long replicaSetId) {
        return defaultCache.get(replicaSetId);
    }
}

