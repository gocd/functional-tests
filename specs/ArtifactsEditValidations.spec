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

ArtifactsEditValidations
========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "ArtifactsEditValidations" - setup

ArtifactsEditValidations
------------------------

* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open artifacts


Artifacts Field Validations

* ArtifactsValidation 
     |source |destination |messageForSource                                                                                                                 |messageForDestination                                                                                                                  |saveStatus                   |expected|
     |-------|------------|---------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|--------|
     |#      |../../      |Error when processing params for '#' used in field 'src', # must be followed by a parameter pattern or escaped by another #      |Invalid destination path. Destination path should match the pattern ([^. ].+[^. ]):([^. ][^. ]):([^. ])                                |Save failed, see errors below|true    |
     |#source|#destination|Error when processing params for '#source' used in field 'src', # must be followed by a parameter pattern or escaped by another #|Error when processing params for '#destination' used in field 'dest', # must be followed by a parameter pattern or escaped by another #|Save failed, see errors below|true    |
     |##     |##          |                                                                                                                                 |                                                                                                                                       |Saved successfully.          |true    |

* Click on pipeline "edit-pipeline" for editing

* Open parameters page

* Enter parameter "1" name "source" and value "source_folder"
* Enter parameter "2" name "destination" and value "destination_folder"
* Click save
* Verify "Saved successfully." message is displayed - Already on parameters page
* Open general options page

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open artifacts


* ArtifactsValidation 
     |source   |message for source|destination   |message for destination|save status        |expected|
     |---------|------------------|--------------|-----------------------|-------------------|--------|
     |#{source}|                  |#{destination}|                       |Saved successfully.|true    |
     |${source}|                  |${destination}|                       |Saved successfully.|true    |





Teardown of contexts
* Capture go state "ArtifactsEditValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


