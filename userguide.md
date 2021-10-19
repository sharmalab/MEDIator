# MEDIator User Guide
MEDIator, a data sharing and federation middleware platform for heterogeneous medical image archives. MEDIator allows sharing pointers to medical data efficiently, while letting the consumers manipulate the pointers without modifying the raw medical data. MEDIator has been implemented for multiple data sources, including Amazon S3, The Cancer Imaging Archive (TCIA), caMicroscope, and metadata from CSV files for cancer images.

By default, Mediator allows creating pointers to datasets from TCIA. These pointers are called "replicasets."


# Using MEDIator to create a replicaset
Why would someone use it? You use MEDIator to create pointers to data. This pointer points to various disjoint sets of data. In case of TCIA, this may span across various collections, patients, studies, and series. These pointers can be shared across various users, spanning multiple organizations, without actually duplicating and sharing the data.
