/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.mediator.core;

import edu.emory.bmi.mediator.constants.CommonConstants;
import edu.emory.bmi.mediator.integrator.ReplicaSetsIntegrator;
import edu.emory.bmi.mediator.rs_mgmt.TciaReplicaSetManager;
import edu.emory.bmi.mediator.webapp.DataRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static spark.Spark.port;

/**
 * Invokes the REST APIs of MEDIator.
 */
public class MEDIatorEngine {
    private static Logger logger = LogManager.getLogger(DataRetriever.class.getName());

    public static void main(String[] args) {

        port(CommonConstants.REST_PORT);

        TciaInitializer tciaInitializer = new TciaInitializer();
        tciaInitializer.init();

        logger.info("Initializing MEDIator REST APIs");
        TciaReplicaSetManager.initialize();
        ReplicaSetsIntegrator.initialize();
    }
}
