#!/usr/bin/env python
 
import requests
import logging
logging.basicConfig(level=logging.INFO)

# Define the REST Port as defined in the CommonConstants class.
REST_PORT = "80"


# Retrieve the users.

response = requests.get("http://localhost:" + REST_PORT + "/")

logging.info('Retrieve the set of users')
logging.info(response.content)



# Create Replica Set.

response = requests.post("http://localhost:" + REST_PORT + "/replicasets", headers={ "Accept": "application/json" }, params={ "iUserID": 12, "iCollection": "TCGA-GBM", "iPatientID" : "TCGA-06-6701%2CTCGA-08-0831", "iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })

logging.info('Create a replica set')
logging.info(response.content)


# Retrieve Replica Sets.

response = requests.get("http://localhost:" + REST_PORT + "/replicasets/12")

logging.info('Retrieve replica sets of the user')
logging.info(response.content)


# Retrieve Replica Set.

response = requests.get("http://localhost:" + REST_PORT + "/replicaset/-4727115044472165798")

logging.info('Retrieve a replica set')
logging.info(response.content)


# Delete Replica Set.

response = requests.delete("http://localhost:" + REST_PORT + "/replicaset/12?replicaSetID=-5896416803618323002")

logging.info('Delete a replica set')
logging.info(response.content)


# Replace Replica Set.

logging.info('Replace a replica set')
response = requests.post("http://localhost:" + REST_PORT + "/replicaset/-4727115044472165798", headers={ "Accept": "application/json" }, params={"iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })
logging.info(response.content)


# Append Replica Set.

response = requests.put("http://localhost:" + REST_PORT + "/replicaset/-4727115044472165798", headers={ "Accept": "application/json" }, params={"iCollection" : "TCGA-GBM"})

logging.info('Append a replica set')
logging.info(response.content)


# Duplicate Replica Set.

response = requests.post("http://localhost:" + REST_PORT + "/replicaset", headers={ "Accept": "application/json" }, params={ "userID" : "1234567", "replicaSetID": "-4727115044472165798"})

logging.info('Duplicate a replica set')
logging.info(response.content)

# Retrieve the users.

response = requests.get("http://localhost:" + REST_PORT + "/")

logging.info('Retrieve the set of users')
logging.info(response.content)
