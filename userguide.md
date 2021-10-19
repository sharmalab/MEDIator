# MEDIator User Guide
MEDIator, a data sharing and federation middleware platform for heterogeneous medical image archives. MEDIator allows sharing pointers to medical data efficiently, while letting the consumers manipulate the pointers without modifying the raw medical data. MEDIator has been implemented for multiple data sources, including Amazon S3, The Cancer Imaging Archive (TCIA), caMicroscope, and metadata from CSV files for cancer images.

By default, Mediator allows creating pointers to datasets from TCIA. These pointers are called "replicasets."


# Using MEDIator to create a replicaset
Why would someone use it? You use MEDIator to create pointers to data. This pointer points to various disjoint sets of data. In case of TCIA, this may span across various collections, patients, studies, and series. These pointers can be shared across various users, spanning multiple organizations, without actually duplicating and sharing the data.

What does it do? MEDIator saves the replicasets created by the users in a secure space, separate from other user spaces.

Creating a replicaset is a curl command that can be issued from a REST client.

For example, a POST request as below:
````
http://172.20.11.223:8040/replicasets", headers={ "Accept": "application/json" }, params={ "iUserID": 12, "iCollection": "TCGA-GBM", "iPatientID" : "TCGA-06-6701%2CTCGA-08-0831", "iStudyInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.151679082681232740021018262895", "iSeriesInstanceUID" : "1.3.6.1.4.1.14519.5.2.1.4591.4001.179004339156422100336233996679" })
````


To retrieve the replicasets of the user.

For example, a get request as below:
````
http://172.20.11.223:8040/replicasets/12"
````
