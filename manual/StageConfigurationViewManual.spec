// --GO-LICENSE-START--
// Copyright 2015 ThoughtWorks, Inc.
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//    http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --GO-LICENSE-END--

StageConfigurationViewManual
============================

StageConfigurationViewManual
----------------------------

tags: 4489

Scenario: User opens the existing stage that has completed running

* All stages in a particular pipelines has completed running Pipeline has four stages and they had been executed with 3 different configuration files as below
* Build – Config.xml version 19
* Unit Test – Config.xml vers’ion 19
* Smoke Test config.xml version 20
* Dist config.xml version 21

* The user view stage details page and clicks on respective configuration history tab

* The users should be shown appropriate config.xml versions as given below
* Build – Config.xml version 19
* Unit Test – Config.xml version 19
* Smoke Test config.xml version 20
* Dist config.xml version 21


Scenario: Migration related scenario

Given: User has upgraded the go application to the latest build

When: User selects a previous run for the existing pipeline

Then: He should be shown the message “Historical configuration is not available for this stage run.”

Scenario: Build Work, Job Reference related scenarios

* User has triggered a stage which has many jobs.
* Couple of them has completed running,
* Few of them are waiting for the resources to be assigned, few of them are still running.
* User changes the configuration of the associated jobs that are running/not running as a result of which associated configuration reference changes.
* All the associated jobs have completed running

* Build work associated with all these jobs should have been created with previous version of config.xml that was existing when the stage was triggered
* Configuration reference associated with all these jobs that ran within the statge should be same previous version from the stage got triggered.


Scenario: Security Related Scenario

* There is an user named John who is a not an admin but a pipeline group admin for the following pipeline group "DakaCo_Project"
* The pipeline group "DakaCo_Project" has the following pipeline
* 1. Unit Testing
* 2. Acceptance Testing
* 3. Build
* 4. Smoke Testing
* There is a user named Smith who is a System Admin for Go Application
* There is a user named  Bravo who has only view permission for "DakaCo_Project"
* There is a user named Kent who has only operate permission for "Dakaco_Project"

* John logs into the system and open the stage details page Unit Testing

* Old Config Tab should be shown with message "Historical configuration is available only for Go Administrators."
* Revision lines corresponding to different cruise instances should be shown

* Smith logs into the system and open the stage details page of unit testing test

* Old Config Tab should be shown
* Revision lines corresponding to different cruise_config instances sould  be shown

* Bravo logs into the system and open the stage details page of unit testing test

* Old Config Tab should not  shown with message "Historical configuration is available only for Go Administrators."
* Revision lines corresponding to different cruise instances sould  be shown

* Kent logs into the system unit testing pipeline should not be shown




