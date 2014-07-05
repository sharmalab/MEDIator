/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.tcia;

import edu.emory.bmi.datarepl.constants.InfConstants;
import edu.emory.bmi.datarepl.core.InfDataAccessIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.Cache;

import java.io.IOException;
import java.util.UUID;

/**
 * Extending DataAccessIntegration for Tcia.
 */
public class DataProSpecs extends InfDataAccessIntegration {

    private static DataProSpecs infDataAccessIntegration = null;

    private static Logger logger = LogManager.getLogger(DataProSpecs.class.getName());

    protected static Cache<Long, Boolean[]> tciaMetaMap;
    protected static Cache<Long, String[]> collectionsMap;
    protected static Cache<Long, String[]> patientsMap;
    protected static Cache<Long, String[]> studiesMap;
    protected static Cache<Long, String[]> seriesMap;

    /**
     * Singleton. Prevents initialization from outside the class.
     *
     * @throws java.io.IOException, if getting the cache failed.
     */
    protected DataProSpecs() throws IOException {
        super();
        tciaMetaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE);
        collectionsMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_COLLECTIONS);
        patientsMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_PATIENTS);
        studiesMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_STUDIES);
        seriesMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_SERIES);
        logger.info("Initialized the Infinispan Cache for the TCIA Data Replication Tool..");

    }

    /**
     * Initializes Infinispan
     */
    public static InfDataAccessIntegration getInfiniCore() {
        if (infDataAccessIntegration == null) {
            try {
                infDataAccessIntegration = new DataProSpecs();
            } catch (IOException e) {
                logger.error("Exception when trying to initialize Infinispan.", e);
            }
        }
        return infDataAccessIntegration;
    }

    /**
     * Creates a replicaSet for the user in the format: {collection: boolean, patient: boolean,
     * study: boolean, series: boolean}
     *
     * @param userId            the user
     * @param collection        collection names
     * @param patientID         String[]
     * @param studyInstanceUID  String[]
     * @param seriesInstanceUID String[]
     */
    public void createReplicaSet(String userId, String[] collection, String[] patientID,
                                 String[] studyInstanceUID, String[] seriesInstanceUID) {
        long replicaSetId = UUID.randomUUID().getLeastSignificantBits();

        Boolean[] metaMap = new Boolean[4];
        metaMap[0] = collection != null;
        metaMap[1] = patientID != null;
        metaMap[2] = studyInstanceUID != null;
        metaMap[3] = seriesInstanceUID != null;

        putMetaMap(replicaSetId, metaMap);
        if (collection != null) {
            putCollectionSet(replicaSetId, collection);
        }
        if (patientID != null) {
            putPatientSet(replicaSetId, patientID);
        }
        if (studyInstanceUID != null) {
            putStudiesSet(replicaSetId, studyInstanceUID);
        }
        if (seriesInstanceUID != null) {
            putSeriesSet(replicaSetId, seriesInstanceUID);
        }
        addToUserReplicasMap(userId, replicaSetId);
    }

    /**
     * PUT /putMetaMap
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     boolean array of meta data
     * @return replicaSetId: Long
     */
    public long putMetaMap(long replicaSetId, Boolean[] metadata) {
        tciaMetaMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * PUT /putMetaMap
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     boolean array of meta data
     * @return replicaSetId: Long
     */
    public long putCollectionSet(long replicaSetId, String[] metadata) {
        collectionsMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * PUT /putMetaMap
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     boolean array of meta data
     * @return replicaSetId: Long
     */
    public long putPatientSet(long replicaSetId, String[] metadata) {
        patientsMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * PUT /putMetaMap
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     boolean array of meta data
     * @return replicaSetId: Long
     */
    public long putStudiesSet(long replicaSetId, String[] metadata) {
        studiesMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * PUT /putMetaMap
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     boolean array of meta data
     * @return replicaSetId: Long
     */
    public long putSeriesSet(long replicaSetId, String[] metadata) {
        seriesMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public Boolean[] getMetaMap(long replicaSetId) {
        return tciaMetaMap.get(replicaSetId);
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public String[] getCollectionsSet(long replicaSetId) {
        return collectionsMap.get(replicaSetId);
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public String[] getPatientsSet(long replicaSetId) {
        return patientsMap.get(replicaSetId);
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public String[] getStudiesSet(long replicaSetId) {
        return studiesMap.get(replicaSetId);
    }

    /**
     * GET /getReplicaSet
     *
     * @param replicaSetId, long
     * @return replicaSet:String
     */
    public String[] getSeriesSet(long replicaSetId) {
        return seriesMap.get(replicaSetId);
    }

    /**
     * DELETE /deleteReplicaSet
     *
     * @param replicaSetId the id of the replica to be evicted.
     * @return true, if evicted now. False, if not available.
     */
    @Override
    public boolean deleteReplicaSet(String userId, long replicaSetId) {
        if (tciaMetaMap.get(replicaSetId) == null) {
            return false;
        } else {
            Long[] replicas = userReplicasMap.get(userId);
            Long[] newReplicas = new Long[replicas.length - 1];
            int j = 0;
            for (Long replica : replicas) {
                if (replica != replicaSetId) {
                    newReplicas[j] = replica;
                    j++;
                }
            }
            userReplicasMap.put(userId, newReplicas);

            tciaMetaMap.remove(replicaSetId);
            collectionsMap.remove(replicaSetId);
            patientsMap.remove(replicaSetId);
            studiesMap.remove(replicaSetId);
            seriesMap.remove(replicaSetId);
            return true;
        }
    }

    /**
     * PUSH /updateReplicaSet
     *
     * @param replicaSetId,     the id of the replica to be modified.
     * @param collection        collection names
     * @param patientID         String[]
     * @param studyInstanceUID  String[]
     * @param seriesInstanceUID String[]
     * @return the updated replica set.
     */
    public Boolean[] updateReplicaSet(long replicaSetId, String[] collection, String[] patientID,
                                      String[] studyInstanceUID, String[] seriesInstanceUID) {
        Boolean[] metaMap = new Boolean[4];
        metaMap[0] = collection != null;
        metaMap[1] = patientID != null;
        metaMap[2] = studyInstanceUID != null;
        metaMap[3] = seriesInstanceUID != null;

        putMetaMap(replicaSetId, metaMap);
        putCollectionSet(replicaSetId, collection);
        putPatientSet(replicaSetId, patientID);
        putStudiesSet(replicaSetId, studyInstanceUID);
        putSeriesSet(replicaSetId, seriesInstanceUID);
        return metaMap;
    }


    /**
     * Makes a duplicate of an existing replica set.
     *
     * @param replicaSetId the id of the replica set to be duplicated.
     * @param userId       the user who is duplicating the replica.
     * @return the id of the duplicate replica set.
     */
    @Override
    public long duplicateReplicaSet(long replicaSetId, String userId) {
        long duplicateReplicaSetId = UUID.randomUUID().getLeastSignificantBits();
        Boolean[] replicaSet = getMetaMap(replicaSetId);
        tciaMetaMap.put(duplicateReplicaSetId, replicaSet);
        addToUserReplicasMap(userId, duplicateReplicaSetId);
        collectionsMap.put(duplicateReplicaSetId, getCollectionsSet(replicaSetId));
        patientsMap.put(duplicateReplicaSetId, getPatientsSet(replicaSetId));
        studiesMap.put(duplicateReplicaSetId, getStudiesSet(replicaSetId));
        seriesMap.put(duplicateReplicaSetId, getSeriesSet(replicaSetId));
        return duplicateReplicaSetId;
    }
}
