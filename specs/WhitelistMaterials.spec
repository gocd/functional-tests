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

WhitelistMaterials
================

Setup of contexts
* Basic configuration - setup
* Using pipeline "whitelist_pipeline" - setup
* With "1" live agents in directory "WhitelistMaterials" - setup
* Capture go state "WhitelistMaterials" - setup

WhitelistMaterials
----------------

tags: whitelist, regression

* Looking at pipeline "whitelist_pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"

* With material named "hg" in pipeline "whitelist_pipeline"
* Checkin file "new_artifact.txt" as user "whitelister" with message "Adding a blacklisted file"

* Verify pipeline is at label "1" and does not get triggered

* Invert file filter for pipeline "whitelist_pipeline"

* Verify stage "1" is "Passed" on pipeline with label "2"

* With material named "svn" in pipeline "whitelist_pipeline"
* Checkin file "new_artifact.txt" as user "whitelister" with message "Adding a blacklisted file"

* Verify stage "1" is "Passed" on pipeline with label "3"

* With material named "git" in pipeline "whitelist_pipeline"
* Checkin file "new_artifact.txt" as user "whitelister <whitelister@thoughtworks.com>" with message "Adding a blacklisted file"

* Verify stage "1" is "Passed" on pipeline with label "4"


Teardown of contexts
____________________
* Capture go state "WhitelistMaterials" - teardown
* Using pipeline "whitelist_pipeline" - teardown
* Basic configuration - teardown


