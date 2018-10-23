#!/usr/bin/env python
 
import unirest

# Define the REST Port as defined in the CommonConstants class.
REST_PORT = "80"


# Retrieve the users.

response = unirest.get("http://localhost:" + REST_PORT + "/")

print 'Retrieve the set of users'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)



# Create Replica Set.

response = unirest.post("http://localhost:" + REST_PORT + "/replicasets", headers={ "Accept": "application/json" }, params={ "iUserID": 12, "iCollection": "TCGA-GBM", "iPatientID" : "TCGA-06-6701%2CTCGA-08-0831", "iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })

print 'Create a replica set'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Retrieve Replica Sets.

response = unirest.get("http://localhost:" + REST_PORT + "/replicasets/12")

print 'Retrieve replica sets of the user'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Retrieve Replica Set.

response = unirest.get("http://localhost:" + REST_PORT + "/replicaset/-4727115044472165798")

print 'Retrieve a replica set'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Delete Replica Set.

response = unirest.delete("http://localhost:" + REST_PORT + "/replicaset/12?replicaSetID=-5896416803618323002")

print 'Delete a replica set'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Replace Replica Set.

print 'Replace a replica set'
response = unirest.post("http://localhost:" + REST_PORT + "/replicaset/-4727115044472165798", headers={ "Accept": "application/json" }, params={"iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })

print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Append Replica Set.

response = unirest.put("http://localhost:" + REST_PORT + "/replicaset/-4727115044472165798", headers={ "Accept": "application/json" }, params={"iCollection" : "TCGA-GBM"})

print 'Append a replica set'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)


# Duplicate Replica Set.

response = unirest.post("http://localhost:" + REST_PORT + "/replicaset", headers={ "Accept": "application/json" }, params={ "userID" : "1234567", "replicaSetID": "-4727115044472165798"})

print 'Duplicate a replica set'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)

# Retrieve the users.

response = unirest.get("http://localhost:" + REST_PORT + "/")

print 'Retrieve the set of users'
print '%s\n%s\n%s' % (response.code, response.headers, response.body)