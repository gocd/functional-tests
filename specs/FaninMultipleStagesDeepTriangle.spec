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

FaninMultipleStagesDeepTriangle
===============================

Setup of contexts
* Fanin configuration - setup
* Using pipeline "DeepC1, DeepC2, DeepC3, DeepC4-auto, DeepC5, DeepC6" - setup
* With "3" live agents in directory "StageDetails" - setup
* Capture go state "FaninMultipleStagesDeepTriangle" - setup

FaninMultipleStagesDeepTriangle
-------------------------------

tags: diamond dependency, fanin, multiple stages, deep triangle, #6435

* Trigger pipelines "DeepC1, DeepC5, DeepC6" and wait for labels "1" to pass
* Navigate to pipeline dependencies for "DeepC1" "1" "first_stage" "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC1"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Navigate to pipeline dependencies for "DeepC5" "1" "first_stage" "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC5"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Navigate to pipeline dependencies for "DeepC6" "1" "first_stage" "1"


* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC6"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Trigger pipelines "DeepC2" and wait for labels "1" to pass
* Navigate to pipeline dependencies for "DeepC2" "1" "first_stage" "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC2"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Trigger pipelines "DeepC3" and wait for labels "1" to pass
* Navigate to pipeline dependencies for "DeepC3" "1" "first_stage" "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC4-auto"
* Verify stage "1" is "Passed" on pipeline with label "1"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC4-auto"
* Verify stage "2" is "Passed" on pipeline with label "1"


* With material "git-one" for pipeline "DeepC1"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-one-g2"

* On Pipeline Dashboard Page
* Trigger pipelines "DeepC1" and wait for labels "2" to pass

On Pipeline Dashboard Page
looking at pipeline "DeepC4-auto"
verify pipeline is at label "1" and does not get triggered

* Navigate to pipeline dependencies for "DeepC1" "2" "first_stage" "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC1"
* Verify stage "2" is "Passed" on pipeline with label "2"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC4-auto"
* Verify pipeline is at label "1" and does not get triggered

* On Pipeline Dashboard Page
* Trigger pipelines "DeepC2" and wait for labels "2" to pass
* Trigger pipelines "DeepC3" and wait for labels "2" to pass
* Looking at pipeline "DeepC4-auto"
* Verify stage "2" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* Looking at material of type "Pipeline" named "${runtime_name:DeepC3}" for pipeline "DeepC4-auto" with counter "2"
* Verify modification "0" has revision "${runtime_name:DeepC3}/2/first_stage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:DeepC1}" for pipeline "DeepC4-auto" with counter "2"
* Verify modification "0" has revision "${runtime_name:DeepC1}/2/first_stage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:DeepC5}" for pipeline "DeepC4-auto" with counter "2"
* Verify modification "0" has revision "${runtime_name:DeepC5}/1/first_stage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:DeepC6}" for pipeline "DeepC4-auto" with counter "2"
* Verify modification "0" has revision "${runtime_name:DeepC6}/1/second_stage/1"
* Verify material has not changed

* With material "git-two" for pipeline "DeepC5"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-two-g2"

* On Pipeline Dashboard Page
* Trigger pipelines "DeepC5" and wait for labels "2" to pass


On Pipeline Dashboard Page
looking at pipeline "DeepC4-auto"
verify pipeline is at label "2" and does not get triggered

* Navigate to pipeline dependencies for "DeepC5" "2" "first_stage" "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC5"
* Verify stage "2" is "Passed" on pipeline with label "2"
* Trigger pipelines "DeepC6" and wait for labels "2" to pass
* Looking at pipeline "DeepC6"
* Verify stage "1" is "Passed" on pipeline with label "2"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC4-auto"
* Verify pipeline is at label "2" and does not get triggered

* Navigate to pipeline dependencies for "DeepC6" "2" "first_stage" "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "DeepC6"
* Verify stage "2" is "Passed" on pipeline with label "2"
* Looking at pipeline "DeepC4-auto"
* Verify stage "2" is "Passed" on pipeline with label "3"
* Open changes section for counter "3"

* Looking at material of type "Pipeline" named "${runtime_name:DeepC3}" for pipeline "DeepC4-auto" with counter "3"
* Verify modification "0" has revision "${runtime_name:DeepC3}/2/first_stage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:DeepC1}" for pipeline "DeepC4-auto" with counter "3"
* Verify modification "0" has revision "${runtime_name:DeepC1}/2/first_stage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:DeepC5}" for pipeline "DeepC4-auto" with counter "3"
* Verify modification "0" has revision "${runtime_name:DeepC5}/2/first_stage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:DeepC6}" for pipeline "DeepC4-auto" with counter "3"
* Verify modification "0" has revision "${runtime_name:DeepC6}/2/second_stage/1"
* Verify material has changed





Teardown of contexts
* Capture go state "FaninMultipleStagesDeepTriangle" - teardown
* With "3" live agents in directory "StageDetails" - teardown
* Using pipeline "DeepC1, deepC2, deepC3, deepC4-auto, deepC5, deepC6" - teardown
* Fanin configuration - teardown


