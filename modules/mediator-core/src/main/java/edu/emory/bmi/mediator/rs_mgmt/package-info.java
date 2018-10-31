/*
 * Core Data publication and replicaset management classes.
 *
 * Replicaset Management Package: manages the replica sets pointing to each of the data sources.
 *
 * ReplicaSetHandlers are implemented for each of the data sources that are federated by MEDIator.
 * The ReplicaSetHandlers offer an internal implementation of the replica sets management.
 *
 * ReplicaSetManagers are the core REST APIs of MEDIator. They leverage the ReplicaSetHandlers to manage the replicasets
 * of each of the data sources. There is a one-to-one mapping between the ReplicaSetManager api implementations and the
 * data sources.
 */

package edu.emory.bmi.mediator.rs_mgmt;
