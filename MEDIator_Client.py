#!/usr/bin/env python
 
import unirest

# Create Replica Set.

response = unirest.post("http://localhost:9090/replicasets", headers={ "Accept": "application/json" }, params={ "iUserID": 12, "iCollection": "TCGA-GBM", "iPatientID" : "TCGA-06-6701%2CTCGA-08-0831", "iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })

response.code # The HTTP status code
response.headers # The HTTP headers
response.body # The parsed response
response.raw_body # The unparsed response


print '%s\n%s\n%s' % (response.code, response.headers, response.body)
