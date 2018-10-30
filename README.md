# Project Overview

Welcome to the wiki of the MEDIator - Data Sharing Framework!

Please visit the website to learn more - https://github.com/sharmalab/MEDIator


## Configuring Access and Authentication for TCIA-SDK

Create config.yaml in your execution directory with the correct access information and credentials 
(TCIA user name and password) to access the TCIA REST API with authentication. 

A sample configuration file can be found at modules/mediator-core/src/main/resources/config.test.yaml.

The currently implemented methods query the public data sets. Therefore, the user name and password are optional for 
now. You may therefore create your configuration file based on the simple configuration file that can be found at 
modules/mediator-core/src/main/resources/config.simple.test.yaml instead.

Place the configuration file at your execution directory.


## Building MEDIator Using Apache Maven 3.x.x
     mvn package

It is expected to have the TCIA API_KEY set in the environment variables to build with tests.

If you do not have a TCIA API_KEY yet, please build with skipping the tests.

    mvn package -DskipTests


Executing MEDIator
---------
[1] MEDIator REST APIs.

If you are hosting MEDIator for public access, you need to start it and expose its RESTful APIs. Execute the
MEDIatorEngine class.

     java -classpath lib/mediator-core-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.mediator.core.MEDIatorEngine

You may extend or leverage the exposed APIs. To begin with, you may consume the MEDIator RESTful APIs through a REST
client such as the Postman plugin of the Chrome browser.

To add more instances to the cluster, start the instances of Initiator class.

     java -classpath lib/mediator-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.mediator.core.Initiator

The implementation of the RESTful invocations can be found at TciaReplicaSetManager.


[2] MEDIator Web Application
   
     sh modules/mediator-core/target/bin/webapp

Go to http://localhost:<EMBEDDED_TOMCAT_PORT>/mediator/ using your browser to access the Web Application.

By default, http://localhost/mediator/


You may run the Initiator class in parallel, to create a MEDIator cluster in both cases.



## Further customizations
If you wish to reproduce the system in another deployment, please refer to documentation/HOW-TO.



## Citing MEDIator
If you have used MEDIator in your research, please cite the below papers:

* Kathiravelu, P., Sharma, A., Galhardas, H., Van Roy, P., & Veiga, L. On-Demand Big Data Integration: A Hybrid ETL 
Approach for Reproducible Scientific Research. In Distributed and Parallel Databases (DAPD). (JCR IF: 1.179, Q2). 
pp. 1 – 23. Sep. 2018. Springer. https://doi.org/10.1007/s10619-018-7248-y


* Kathiravelu, P. & Sharma, A. (2015). MEDIator: A Data Sharing Synchronization Platform for Heterogeneous Medical Image Archives.
In Workshop on Connected Health at Big Data Era (BigCHat'15), co-located with 21st ACM SIGKDD Conference on Knowledge Discovery and Data Mining (KDD 2015).
Aug. 2015. ACM. 6 pages. http://doi.org/10.13140/RG.2.1.3709.4248


* Kathiravelu, P. & Sharma, A. (2016). SPREAD - System for Sharing and Publishing Research Data. In Society for Imaging
Informatics in Medicine Annual Meeting (SIIM 2016). June 2016.
http://c.ymcdn.com/sites/siim.org/resource/resmgr/siim2016abstracts/Research_Kathiravelu.pdf

