#!/bin/bash

java -Djava.net.preferIPv4Stack=true -classpath lib/mediator-core-1.0-SNAPSHOT.jar:lib/*:conf/ edu.emory.bmi.mediator.core.MEDIatorEngine
