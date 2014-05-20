# Project Overview

Welcome to the wiki of the Data Replication System project!

Please visit the website to learn more - https://bitbucket.org/BMI/datareplicationsystem


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

Build using Apache Maven,
$ mvn clean install

To execute,
$ java -classpath lib/commons-codec-1.6.jar:lib/commons-logging-1.1.3.jar:lib/documentation-1.0-SNAPSHOT.jar:lib/httpclient-4.3.3.jar:lib/httpcore-4.3.2.jar:lib/infinispan-commons-6.0.2.Final.jar:lib/infinispan-core-6.0.2.Final.jar:lib/jboss-logging-3.1.2.GA.jar:lib/jboss-marshalling-1.4.4.Final.jar:lib/jboss-marshalling-river-1.4.4.Final.jar:lib/jboss-transaction-api_1.1_spec-1.0.1.Final.jar:lib/jgroups-3.4.1.Final.jar:lib/repl-server-1.0-SNAPSHOT.jar edu.emory.bmi.datarepl.core.DataRetriever