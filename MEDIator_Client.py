#!/usr/bin/env python
 
import unirest

# Create Replica Set.

response = unirest.post("http://localhost:9090/replicasets", headers={ "Accept": "application/json" }, params={ "iUserID": 12, "iCollection": "TCGA-GBM", "iPatientID" : "TCGA-06-6701%2CTCGA-08-0831", "iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })

print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Retrieve Replica Sets.

response = unirest.get("http://localhost:9090/replicasets/12")

print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Retrieve Replica Set.

response = unirest.get("http://localhost:9090/replicaset/-4727115044472165798")

print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Delete Replica Set.

response = unirest.delete("http://localhost:9090/replicaset/12?replicaSetID=-5896416803618323002")

print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Replace Replica Set.

response = unirest.post("http://localhost:9090/replicaset/-4727115044472165798", headers={ "Accept": "application/json" }, params={"iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })

print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Append Replica Set.

response = unirest.put("http://localhost:9090/replicaset/-4727115044472165798", headers={ "Accept": "application/json" }, params={"iCollection" : "TCGA-GBM"})

print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Duplicate Replica Set.

response = unirest.post("http://localhost:9090/replicaset", headers={ "Accept": "application/json" }, params={ "userID" : "1234567", "replicaSetID": "-4727115044472165798"})

print '%s\n%s\n%s' % (response.code, response.headers, response.body)