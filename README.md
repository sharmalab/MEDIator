## Building MEDIator Using Apache Maven 3.x.x

     mvn package


## Configuring Access and Authentication for TCIA-SDK

Create config.yaml in your execution directory with the correct access information and credentials 
(TCIA user name and password) to access the TCIA REST API with authentication. 

Sample configuration files can be found at the conf folder (config.test.yaml and config.simple.test.yaml).


Executing MEDIator
---------
Execute the MEDIatorEngine class to start MEDIator.

     java -classpath lib/mediator-core-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.mediator.core.MEDIatorEngine

You may extend or leverage the exposed APIs. To begin with, you may consume the MEDIator RESTful APIs through a REST
client such as the Postman plugin of the Chrome browser.


The implementation of the RESTful invocations can be found at TciaReplicaSetManager.


Executing MEDIator Web Application
---------
Alternatively, you may run MEDIator as a web application.   
   

     sh modules/mediator-core/target/bin/webapp

   
   

Go to http://localhost:<EMBEDDED_TOMCAT_PORT>/mediator/ using your browser to access the Web Application.

By default, http://localhost/mediator/


You may run the Initiator class in parallel, to create a MEDIator cluster in both cases.


## Documentation
Further documentation can be found inside the documentation folder. If you wish to reproduce the system in another 
deployment, please refer to HOW-TO.rst.


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

