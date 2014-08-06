# Project Overview

Welcome to the wiki of the Data Replication System project!

Please visit the website to learn more - https://bitbucket.org/BMI/datareplicationsystem


## Building and Executing Using Apache Maven 3.x.x
Building
--------
$ mvn package

Executing
---------
$ sh modules/repl-server/target/bin/webapp

Go to http://localhost:9090/ using your browser to access the Data Replication and Synchronization Tool.

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


## Connect to the EC2 Instance

ssh -i pradeeban.pem ubuntu@ec2-54-237-35-248.compute-1.amazonaws.com
mkdir gsoc2014
cd gsoc2014

nohup wget https://tcga-data.nci.nih.gov/tcgafiles/ftp_auth/distro_ftpusers/anonymous/userCreatedArchives/652ccf44-cfda-4e99-81ac-d8f4c0eca6be.tar > nohup.out &
tar -zxvf 652ccf44-cfda-4e99-81ac-d8f4c0eca6be.tar
