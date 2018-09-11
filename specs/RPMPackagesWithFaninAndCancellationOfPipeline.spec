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

RPMPackagesWithFaninAndCancellationOfPipeline
=============================================

Setup of contexts
* Package configuration - setup
* Using pipeline "fanin_a, fanin_b, fanin_c, fanin_d" - setup
* Setup file system based yum repos "repo3:go-agent-13.1.0-112.noarch.rpm" - setup
* With "2" live agents in directory "RPMPackagesWithFaninAdvanced" - setup
* Capture go state "RPMPackagesWithFaninAdvanced" - setup

RPMPackagesWithFaninAndCancellationOfPipeline
---------------------------------------------

tags: diamond dependency, fanin, #7538, plugins-tests

* Looking at pipeline "fanin_a"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "stage_1" of run "1" having counter "1"

* Trigger stage "stage_2"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_a"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Looking at pipeline "fanin_b"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "fanin_c"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "fanin_d"
* Verify stage "1" is "Passed" on pipeline with label "1"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_d"
* Open changes section for counter "1"

* verify material of type "Pipeline" name "${runtime_name:fanin_b}" for pipeline "fanin_d" with counter "1" and modification "0"  has changed revision "${runtime_name:fanin_b}/1/defaultStage/1"
* verify material of type "Pipeline" name "${runtime_name:fanin_c}" for pipeline "fanin_d" with counter "1" and modification "0"  has changed revision "${runtime_name:fanin_c}/1/defaultStage/1"

* Publish artifacts "go-agent-13.1.0-113.noarch.rpm" to "repo3"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_a"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "fanin_b"
* Verify stage "1" is "Passed" on pipeline with label "2"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_b"
* Open changes section for counter "2"

* verify material of type "Pipeline" name "${runtime_name:fanin_a}" for pipeline "fanin_b" with counter "2" and modification "0"  has changed revision "${runtime_name:fanin_a}/2/stage_1/1"

On pipeline Dashboard Page
looking at pipeline "faninc"
verify pipeline is at label "1" and does not get triggered
looking at pipeline "fanind"
verify pipeline is at label "1" and does not get triggered

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_a"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to stage "stage_1" of run "2" having counter "1"

* Trigger stage "stage_2"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_a"
* Verify stage "2" is "Passed" on pipeline with label "2"
* Looking at pipeline "fanin_c"
* Verify stage "1" is "Passed" on pipeline with label "2"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_c"
* Open changes section for counter "2"

* verify material of type "Pipeline" name "${runtime_name:fanin_a}" for pipeline "fanin_c" with counter "2" and modification "0"  has changed revision "${runtime_name:fanin_a}/2/stage_2/1"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_d"
* Verify stage "1" is "Passed" on pipeline with label "2"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_d"
* Open changes section for counter "2"

* verify material of type "Pipeline" name "${runtime_name:fanin_b}" for pipeline "fanin_d" with counter "2" and modification "0"  has changed revision "${runtime_name:fanin_b}/2/defaultStage/1"
* verify material of type "Pipeline" name "${runtime_name:fanin_c}" for pipeline "fanin_d" with counter "2" and modification "0"  has changed revision "${runtime_name:fanin_c}/2/defaultStage/1"

* For template "fanin_b_template"
* For stage "defaultStage"
* For job "defaultJob"
* Add custom command "sleep:300"

* Publish artifacts "go-agent-13.1.0-5411.noarch.rpm" to "repo3"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_a"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Looking at pipeline "fanin_b"
* Verify stage "1" is "Building" on pipeline with label "3"
* Navigate to stage "defaultStage" of run "3"

* Cancel "defaultStage" - Already On Stage Detail Page
* On Pipeline Dashboard Page

* Looking at pipeline "fanin_a"
* Navigate to stage "stage_1" of run "3" having counter "1"

* Trigger stage "stage_2"

* On Pipeline Dashboard Page
* Looking at pipeline "fanin_c"
* Verify stage "1" is "Passed" on pipeline with label "3"

On Pipeline Dashboard Page
looking at pipeline "fanind"
verify pipeline is at label "2" and does not get triggered

* Looking at pipeline "fanin_b"
* Navigate to stage "defaultStage" of run "3"

* For template "fanin_b_template"
* For stage "defaultStage"
* For job "defaultJob"
* Remove custom command "sleep:300"

* Rerun stage "defaultStage"
* On Pipeline Dashboard Page

* Looking at pipeline "fanin_b"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Looking at pipeline "fanin_d"
* Verify stage "1" is "Passed" on pipeline with label "3"

* Looking at pipeline "fanin_d"
* Open changes section for counter "3"

* verify material of type "Pipeline" name "${runtime_name:fanin_b}" for pipeline "fanin_d" with counter "3" and modification "0"  has changed revision "${runtime_name:fanin_b}/3/defaultStage/2"
* verify material of type "Pipeline" name "${runtime_name:fanin_c}" for pipeline "fanin_d" with counter "3" and modification "0"  has changed revision "${runtime_name:fanin_c}/3/defaultStage/1"






Teardown of contexts
____________________
* Capture go state "RPMPackagesWithFaninAdvanced" - teardown
* With "2" live agents in directory "RPMPackagesWithFaninAdvanced" - teardown
* Setup file system based yum repos "repo3:go-agent-13.1.0-112.noarch.rpm" - teardown
* Using pipeline "fanin_a, fanin_b, fanin_c, fanin_d" - teardown
* Package configuration - teardown


