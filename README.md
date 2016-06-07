# Project Overview

Welcome to the wiki of the MEDIator - Data Replication System project!

Please visit the website to learn more - https://bitbucket.org/BMI/datareplicationsystem


## Setting the Environment Variables.
Set the API_KEY, MASHAPE_AUTHORIZATION, and BASE_UR as environment variables.

For example, in Linux.

    export API_KEY=your-api-key
    
    export MASHAPE_AUTHORIZATION=your-mashape-authorization
    
    export BASE_URL=services.cancerimagingarchive.net/services/v4/TCIA/query


## Building and Executing Using Apache Maven 3.x.x
Building
--------
     mvn package

It is expected to have the API_KEY set in the TCIAConstants to build with tests.

If you have not received your API_KEY yet, please build with skipping the tests.

    mvn package -DskipTests


Executing
---------
     sh modules/repl-server/target/bin/webapp

Go to http://localhost:9090/mediator/ using your browser to access the Data Replication and Synchronization Tool.

Logging
-------
Make sure to include log4j2-test.xml into your class path to be able to configure and view the logs. Default log level is [WARN].


## Dependencies
This project depends on the below major projects.

* Infinispan
* Apache Tomcat (Embedded)
* Apache HTTP Client
* Apache Velocity
* Apache Log4j2
* Mashape Unirest


## Resources

[1] TCIA REST API
-----------------
[1.1]  TCIA Programmatic Interface (REST API) Usage Guide - 
https://wiki.cancerimagingarchive.net/display/Public/TCIA+Programmatic+Interface+%28REST+API%29+Usage+Guide

[1.2] TCIA on Mashape - https://www.mashape.com/tcia/the-cancer-imaging-archive

[1.3] TCIA REST API Client - https://github.com/nadirsaghar/TCIA-REST-API-Client


[2] Infinispan - MongoDB Integration
------------------------------------
[2.1] Using MongoDB as cache store - http://blog.infinispan.org/2013/06/using-mongodb-as-cache-store.html

[2.2] Infinispan MongoDB Cache Store - https://github.com/infinispan/infinispan-cachestore-mongodb


## Connect to An EC2 Instance
If you prefer to use an EC2 Instance to upload clinical files to S3, you may follow the below steps.

Otherwise, you may directly upload the clinical files which were downloaded to your local computer.

* Connect to the instance

$ ssh -i pradeeban.pem ubuntu@ec2-54-237-35-248.compute-1.amazonaws.com

* Make a directory and change to the directory.

$ mkdir gsoc2014

$ cd gsoc2014

* Download and extract the meta data from the online repository (link sent to email. Given below is an example).

$ nohup wget https://tcga-data.nci.nih.gov/tcgafiles/ftp_auth/distro_ftpusers/anonymous/userCreatedArchives/652ccf44-cfda-4e99-81ac-d8f4c0eca6be.tar > nohup.out &

$ tar -zxvf 652ccf44-cfda-4e99-81ac-d8f4c0eca6be.tar


## Further customizations
If you wish to reproduce the system in another deployment, please refer to documentation/HOW-TO.