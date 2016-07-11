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
import edu.emory.bmi.datarepl.interfacing.TciaInvoker;
import edu.emory.bmi.datarepl.tcia_rest_api.TCIAClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infinispan.Cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Extending DataAccessIntegration for Tcia.
 */
public class TciaReplicaSetAPI extends InfDataAccessIntegration {

    private static TciaReplicaSetAPI infDataAccessIntegration = null;

    private static Logger logger = LogManager.getLogger(TciaReplicaSetAPI.class.getName());
    private static TciaInvoker tciaInvoker = new TciaInvoker();

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
    protected TciaReplicaSetAPI() throws IOException {
        super();
        tciaMetaMap = manager.getCache(InfConstants.TRANSACTIONAL_CACHE_META);
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
                infDataAccessIntegration = new TciaReplicaSetAPI();
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
     * @return the created replica set.
     */
    public Boolean[] createReplicaSet(String userId, String[] collection, String[] patientID,
                                 String[] studyInstanceUID, String[] seriesInstanceUID) {
        long replicaSetId = UUID.randomUUID().getLeastSignificantBits();
        addToUserReplicasMap(userId, replicaSetId);

        return updateReplicaSet(replicaSetId, collection,patientID,studyInstanceUID,seriesInstanceUID);
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
     * @return the created replica set.
     */
    public long createNewReplicaSet(String userId, String[] collection, String[] patientID,
                                 String[] studyInstanceUID, String[] seriesInstanceUID) {
        long replicaSetId = UUID.randomUUID().getLeastSignificantBits();
        addToUserReplicasMap(userId, replicaSetId);

        updateReplicaSet(replicaSetId, collection,patientID,studyInstanceUID,seriesInstanceUID);
        return replicaSetId;
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
        return metaMap;
    }

    /**
     * PUT /appendReplicaSet
     *
     * @param replicaSetId,     the id of the replica to be modified.
     * @param collection        collection names
     * @param patientID         String[]
     * @param studyInstanceUID  String[]
     * @param seriesInstanceUID String[]
     * @return the updated/appended replica set.
     */
    public Boolean[] appendReplicaSet(long replicaSetId, String[] collection, String[] patientID,
                                      String[] studyInstanceUID, String[] seriesInstanceUID) {

        Boolean[] metaMap = getMetaMap(replicaSetId);

        metaMap[0] = metaMap[0] || (collection != null);
        metaMap[1] = metaMap[0] || (patientID != null);
        metaMap[2] = metaMap[0] || (studyInstanceUID != null);
        metaMap[3] = metaMap[0] || (seriesInstanceUID != null);

        putMetaMap(replicaSetId, metaMap);


        List<String> collectionNames = new ArrayList<>();
        List<String> patientIDs = new ArrayList<>();
        List<String> studyInstanceUIDs = new ArrayList<>();
        List<String> seriesInstanceUIDs = new ArrayList<>();

        if (metaMap[0]) {
            collectionNames.addAll(Arrays.asList(getCollectionsSet(replicaSetId)));
            if (collection!=null) {
                collectionNames.addAll(Arrays.asList(collection));
            }
        }
        if (metaMap[1]) {
            patientIDs.addAll(Arrays.asList(getPatientsSet(replicaSetId)));
            if (patientID!=null) {
                patientIDs.addAll(Arrays.asList(patientID));
            }
        }
        if (metaMap[2]) {
            studyInstanceUIDs.addAll(Arrays.asList(getStudiesSet(replicaSetId)));
            if (studyInstanceUID!=null) {
                studyInstanceUIDs.addAll(Arrays.asList(studyInstanceUID));
            }
        }
        if (metaMap[3]) {
            seriesInstanceUIDs.addAll(Arrays.asList(getSeriesSet(replicaSetId)));
            if (seriesInstanceUID!=null) {
                seriesInstanceUIDs.addAll(Arrays.asList(seriesInstanceUID));
            }
        }

        if (collection != null) {
            putCollectionSet(replicaSetId, collectionNames.stream().toArray(String[]::new));
        }
        if (patientID != null) {
            putPatientSet(replicaSetId, patientIDs.stream().toArray(String[]::new));
        }
        if (studyInstanceUID != null) {
            putStudiesSet(replicaSetId, studyInstanceUIDs.stream().toArray(String[]::new));
        }
        if (seriesInstanceUID != null) {
            putSeriesSet(replicaSetId, seriesInstanceUIDs.stream().toArray(String[]::new));
        }
        return metaMap;
    }

    /**
     * GET /get the replicaSet of the given replicaSetID
     *
     * @param aReplicaSetID replicaSet ID
     * @return replicaSet as a printable output
     */
    public String getReplicaSet(long aReplicaSetID) {
        logger.info("Getting the ReplicaSet with ID: " + aReplicaSetID);

        String[] collectionNames = {};
        String[] patientIDs = {};
        String[] studyInstanceUIDs = {};
        String[] seriesInstanceUIDs = {};

        Boolean[] metaMap = getMetaMap(aReplicaSetID);
        if (metaMap[0]) {
            collectionNames = getCollectionsSet(aReplicaSetID);
        }
        if (metaMap[1]) {
            patientIDs = getPatientsSet(aReplicaSetID);
        }
        if (metaMap[2]) {
            studyInstanceUIDs = getStudiesSet(aReplicaSetID);
        }
        if (metaMap[3]) {
            seriesInstanceUIDs = getSeriesSet(aReplicaSetID);
        }
        String out = "Collection Names: " + Arrays.toString(collectionNames) + ". Patient IDs: " + Arrays.toString(patientIDs) +
                ". StudyInstanceUIDs: " + Arrays.toString(studyInstanceUIDs) + ". SeriesInstanceUIDs: " + Arrays.toString(seriesInstanceUIDs);
        return out;
    }

    /**
     * GET /get the replicaSet of the given replicaSetID
     *
     * @param aReplicaSetID replicaSet ID
     * @return replicaSet as a printable output
     */
    public String getReplicaSet(Long aReplicaSetID) {
        logger.info("Getting the ReplicaSet with ID: " + aReplicaSetID);

        String[] collectionNames = {};
        String[] patientIDs = {};
        String[] studyInstanceUIDs = {};
        String[] seriesInstanceUIDs = {};

        Boolean[] metaMap = getMetaMap(aReplicaSetID);
        if (metaMap[0]) {
            collectionNames = getCollectionsSet(aReplicaSetID);
        }
        if (metaMap[1]) {
            patientIDs = getPatientsSet(aReplicaSetID);
        }
        if (metaMap[2]) {
            studyInstanceUIDs = getStudiesSet(aReplicaSetID);
        }
        if (metaMap[3]) {
            seriesInstanceUIDs = getSeriesSet(aReplicaSetID);
        }
        logger.info("SeriesInstanceUIDs: " + Arrays.toString(seriesInstanceUIDs));
        String out = ReplicaSetRetriever.retrieveReplicaSet(aReplicaSetID, collectionNames, patientIDs, studyInstanceUIDs,
                seriesInstanceUIDs);
        logger.info("out: " + out);
        return out;
    }

    /**
     * Gets the Raw Data for the replicaSetID
     *
     * @param aReplicaSetID, the replicaSetID to be queried for the raw data.
     * @return raw data as a list of InputStream
     */
    public List<InputStream> getRawData(Long aReplicaSetID) {
        logger.info("Getting the raw data for the ReplicaSet with ID: " + aReplicaSetID);
        List<InputStream> inputStreams = new ArrayList<>();
        String[] collectionNames;
        String[] patientIDs;
        String[] studyInstanceUIDs;
        String[] seriesInstanceUIDs;

        Boolean[] metaMap = getMetaMap(aReplicaSetID);
        if (metaMap[0]) {
            collectionNames = getCollectionsSet(aReplicaSetID);
            for (String aCollection : collectionNames) {
                if (getRawPatientsOfTheCollection("json", aCollection) != null) {
                    inputStreams.add(getRawPatientsOfTheCollection("json", aCollection));
                }
            }
        }

        if (metaMap[1]) {
            patientIDs = getPatientsSet(aReplicaSetID);
            for (String patientID : patientIDs) {
                if (getRawStudiesOfThePatient("json", null, patientID, null) != null) {
                    inputStreams.add(getRawStudiesOfThePatient("json", null, patientID, null));
                }
            }
        }

        if (metaMap[2]) {
            studyInstanceUIDs = getStudiesSet(aReplicaSetID);
            for (String studyInstanceUID : studyInstanceUIDs) {
                if (getRawSeriesOfTheStudies("json", null, null, studyInstanceUID, null) != null) {
                    inputStreams.add(getRawSeriesOfTheStudies("json", null, null, studyInstanceUID, null));
                }
            }
        }

        if (metaMap[3]) {
            seriesInstanceUIDs = getSeriesSet(aReplicaSetID);
            for (String seriesInstanceUID : seriesInstanceUIDs) {
                if (getRawImagesOfTheSeries(seriesInstanceUID) != null) {
                    inputStreams.add(getRawImagesOfTheSeries(seriesInstanceUID));
                }
            }
        }
        return inputStreams;
    }

    /**
     * Get Patients of the Collection
     *
     * @param iFormat     format
     * @param iCollection collection name
     * @return raw data as Input Stream
     */
    public static InputStream getRawPatientsOfTheCollection(String iFormat, String iCollection) {
        InputStream inputStream = null;

        String query = tciaInvoker.getPatientsOfTheCollectionString(iFormat, iCollection);
        try {
            inputStream = tciaInvoker.getRawData(query);
        } catch (IOException e) {
            logger.error("IOException in retrieving the patients", e);
        } catch (TCIAClientException e) {
            logger.error("TCIAClientException in retrieving the patients", e);
        }
        return inputStream;
    }

    /**
     * Get Studies of the Patient
     *
     * @param iFormat           format
     * @param iCollection       collection name
     * @param iPatientID        patient id
     * @param iStudyInstanceUID study instance uid
     * @return raw data as Input Stream
     */
    public static InputStream getRawStudiesOfThePatient(String iFormat, String iCollection,
                                                        String iPatientID, String iStudyInstanceUID) {
        InputStream inputStream = null;

        String query = tciaInvoker.getStudiesOfThePatientString(iFormat, iCollection, iPatientID, iStudyInstanceUID);
        try {
            inputStream = tciaInvoker.getRawData(query);
        } catch (IOException e) {
            logger.error("IOException in retrieving the studies", e);
        } catch (TCIAClientException e) {
            logger.error("TCIAClientException in retrieving the studies", e);
        }
        return inputStream;
    }

    /**
     * Get Series of the Study
     *
     * @param iFormat           format
     * @param iCollection       collection name
     * @param iPatientID        patient id
     * @param iStudyInstanceUID study instance uid
     * @param iModality         modality
     * @return raw data as Input Stream
     */
    public static InputStream getRawSeriesOfTheStudies(String iFormat, String iCollection, String iPatientID,
                                                       String iStudyInstanceUID, String iModality) {
        InputStream inputStream = null;

        String query = tciaInvoker.getSeriesOfTheStudyString(iFormat, iCollection, iPatientID,
                iStudyInstanceUID, iModality);
        try {
            inputStream = tciaInvoker.getRawData(query);
        } catch (IOException e) {
            logger.error("IOException in retrieving the series", e);
        } catch (TCIAClientException e) {
            logger.error("TCIAClientException in retrieving the series", e);
        }
        return inputStream;
    }

    /**
     * Get Images of the Series
     *
     * @param seriesInstanceUID series instance UID
     * @return raw data as Input Stream
     */
    public static InputStream getRawImagesOfTheSeries(String seriesInstanceUID) {
        InputStream inputStream = null;

        String query = tciaInvoker.getImagesOfTheSeriesString(seriesInstanceUID);
        try {
            inputStream = tciaInvoker.getRawData(query);
        } catch (IOException e) {
            logger.error("IOException in retrieving the images");
        } catch (TCIAClientException e) {
            logger.error("TCIAClientException in retrieving the images");
        }
        return inputStream;
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
     * Makes a duplicate of an existing replica set.
     *
     * @param replicaSetId the id of the replica set to be duplicated.
     * @param userId       the user who is duplicating the replica.
     * @return the id of the duplicate replica set.
     */
    @Override
    public long duplicateReplicaSet(long replicaSetId, String userId) {
        long duplicateReplicaSetId = 0;
        Boolean[] replicaSet = getMetaMap(replicaSetId);
        if (replicaSet!=null) {
            duplicateReplicaSetId = UUID.randomUUID().getLeastSignificantBits();
            tciaMetaMap.put(duplicateReplicaSetId, replicaSet);
            addToUserReplicasMap(userId, duplicateReplicaSetId);
            collectionsMap.put(duplicateReplicaSetId, getCollectionsSet(replicaSetId));
            patientsMap.put(duplicateReplicaSetId, getPatientsSet(replicaSetId));
            studiesMap.put(duplicateReplicaSetId, getStudiesSet(replicaSetId));
            seriesMap.put(duplicateReplicaSetId, getSeriesSet(replicaSetId));
        } else {
            logger.error("Non-existent user or replica set entered. Please check the user ID and the replica set ID");
        }
        return duplicateReplicaSetId;
    }

    /**
     * PUT /putMetaMap
     * Puts the MetaData array for the given replicaSetID into the MetaMap.
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
     * PUT /putCollectionSet
     * Puts the array of collection names for the given replicaSetID into the CollectionsMap.
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     String array of collection names
     * @return replicaSetId: Long
     */
    public long putCollectionSet(long replicaSetId, String[] metadata) {
        collectionsMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * PUT /putPatientSet
     * Puts the array of patientIDs into the patientsMap.
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     String array of patient IDs.
     * @return replicaSetId: Long
     */
    public long putPatientSet(long replicaSetId, String[] metadata) {
        patientsMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * PUT /putStudiesSet
     * Puts the array of StudiesIDs into the studiesMap.
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     String array of studyInstanceUIDs.
     * @return replicaSetId: Long
     */
    public long putStudiesSet(long replicaSetId, String[] metadata) {
        studiesMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * PUT /putSeriesSet
     * Puts the array of SeriesIDs into the seriesMap.
     *
     * @param replicaSetId, the id of the replica set.
     * @param metadata,     String array of seriesInstanceUIDs
     * @return replicaSetId: Long
     */
    public long putSeriesSet(long replicaSetId, String[] metadata) {
        seriesMap.put(replicaSetId, metadata);
        return replicaSetId;
    }

    /**
     * GET /getMetaMap
     * Gets the meta map entry of the given replicaSetID.
     *
     * @param replicaSetId, long
     * @return replicaSet:Boolean[]
     */
    public Boolean[] getMetaMap(long replicaSetId) {
        return tciaMetaMap.get(replicaSetId);
    }

    /**
     * GET /getCollectionsSet
     * Gets the array of collection names for the given replicaSetID.
     *
     * @param replicaSetId, long
     * @return replicaSet:String[] - Array of Collection Names.
     */
    public String[] getCollectionsSet(long replicaSetId) {
        return collectionsMap.get(replicaSetId);
    }

    /**
     * GET /getPatientsSet
     * Gets the array of patientIDs for the given replicaSetID.
     *
     * @param replicaSetId, long
     * @return replicaSet:String[] - Array of Patient IDs.
     */
    public String[] getPatientsSet(long replicaSetId) {
        return patientsMap.get(replicaSetId);
    }

    /**
     * GET /getStudiesSet
     * Gets the array of studyInstanceIDs for the given replicaSetID.
     *
     * @param replicaSetId, long
     * @return replicaSet:String[] - Array of StudyInstance IDs.
     */
    public String[] getStudiesSet(long replicaSetId) {
        return studiesMap.get(replicaSetId);
    }

    /**
     * GET /getSeriesSet
     * Gets the array of seriesIDs for the given replicaSetID.
     *
     * @param replicaSetId, long
     * @return replicaSet:String[] - Array of SeriesInstance IDs.
     */
    public String[] getSeriesSet(long replicaSetId) {
        return seriesMap.get(replicaSetId);
    }
}
