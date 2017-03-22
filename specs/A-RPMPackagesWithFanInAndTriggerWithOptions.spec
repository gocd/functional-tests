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

RPMPackagesWithFanInAndTriggerWithOptions
=========================================

Setup of contexts
* Package configuration - setup
* Using pipeline "F1, F2, F3" - setup
* Setup file system based yum repos "repo1:go-server-13.1.0-122.noarch.rpm;repo3:go-agent-13.1.0-112.noarch.rpm" - setup
* With "2" live agents in directory "RPMPackagesWithFanInAndTriggerWithOptions" - setup
* Capture go state "RPMPackagesWithFanInAndTriggerWithOptions" - setup

RPMPackagesWithFanInAndTriggerWithOptions
-----------------------------------------

tags: diamond dependency, fanin, #7501, plugins-tests

* Looking at pipeline "F3"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Open changes section for counter "1"

* verify material of type "Package" name "repo-3:CP-2" for pipeline "F3" with counter "1" and modification "0"  has changed revision "go-agent-13.1.0-112.noarch"
* verify material of type "Pipeline" name "${runtime_name:F1}" for pipeline "F3" with counter "1" and modification "0"  has changed revision "${runtime_name:F1}/1/defaultStage/1"
* verify material of type "Pipeline" name "${runtime_name:F2}" for pipeline "F3" with counter "1" and modification "0"  has changed revision "${runtime_name:F2}/1/defaultStage/1"

* Publish artifacts "go-agent-13.1.0-113.noarch.rpm" to "repo3"

* On Pipeline Dashboard Page
* Looking at pipeline "F3"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* verify material of type "Package" name "repo-3:CP-2" for pipeline "F3" with counter "2" and modification "0"  has changed revision "go-agent-13.1.0-113.noarch"
* verify material of type "Pipeline" with name "${runtime_name:F1}" for pipeline "F3" with counter "2" and modification "0" has unchanged revision "${runtime_name:F1}/1/defaultStage/1"
* verify material of type "Pipeline" name "${runtime_name:F2}" for pipeline "F3" with counter "2" and modification "0"  has changed revision "${runtime_name:F2}/2/defaultStage/1"

* On Pipeline Dashboard Page
* Looking at pipeline "F2"
* Pause pipeline with reason "Pausing Pipeline F2"

* Publish artifacts "go-server-13.1.0-123.noarch.rpm" to "repo1"

* On Pipeline Dashboard Page
* Looking at pipeline "F1"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* verify material of type "Package" name "repo-1:P-1" for pipeline "F1" with counter "2" and modification "0"  has changed revision "go-server-13.1.0-123.noarch"

On Pipeline Dashboard Page
looking at pipeline "F3"
verify pipeline is at label "2" and does not get triggered

* On Pipeline Dashboard Page
* Looking at pipeline "F2"
* Unpause pipeline
* Looking at pipeline "F2"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Looking at pipeline "F3"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Open changes section for counter "3"

* verify material of type "Package" with name "repo-3:CP-2" for pipeline "F3" with counter "3" and modification "0" has unchanged revision "go-agent-13.1.0-113.noarch"
* verify material of type "Pipeline" name "${runtime_name:F1}" for pipeline "F3" with counter "3" and modification "0"  has changed revision "${runtime_name:F1}/2/defaultStage/1"
* verify material of type "Pipeline" name "${runtime_name:F2}" for pipeline "F3" with counter "3" and modification "0"  has changed revision "${runtime_name:F2}/3/defaultStage/1"

* Publish artifacts "go-server-13.2.0-124.noarch.rpm" to "repo1"

* On Pipeline Dashboard Page
* Looking at pipeline "F1"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Looking at pipeline "F2"
* Verify stage "1" is "Passed" on pipeline with label "4"
* Looking at pipeline "F3"
* Verify stage "1" is "Passed" on pipeline with label "4"
* Open changes section for counter "4"

* verify material of type "Package" with name "repo-3:CP-2" for pipeline "F3" with counter "4" and modification "0" has unchanged revision "go-agent-13.1.0-113.noarch"
* verify material of type "Pipeline" name "${runtime_name:F1}" for pipeline "F3" with counter "4" and modification "0"  has changed revision "${runtime_name:F1}/3/defaultStage/1"
* verify material of type "Pipeline" name "${runtime_name:F2}" for pipeline "F3" with counter "4" and modification "0"  has changed revision "${runtime_name:F2}/4/defaultStage/1"

* On Pipeline Dashboard Page
* Looking at pipeline "F1"
* Open trigger with options

* Select revision "go-server-13.1.0-123.noarch" for material "repo-1:P-1"
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "F1"
* Verify stage "1" is "Passed" on pipeline with label "4"
* Open changes section for counter "4"

* verify material of type "Package" with name "repo-1:P-1" for pipeline "F1" with counter "4" and modification "0" has unchanged revision "go-server-13.1.0-123.noarch"

* On Pipeline Dashboard Page
* Looking at pipeline "F3"
* Verify stage "1" is "Passed" on pipeline with label "5"
* Open changes section for counter "5"

* verify material of type "Package" with name "repo-3:CP-2" for pipeline "F3" with counter "5" and modification "0" has unchanged revision "go-agent-13.1.0-113.noarch"
* verify material of type "Pipeline" name "${runtime_name:F1}" for pipeline "F3" with counter "5" and modification "0"  has changed revision "${runtime_name:F1}/4/defaultStage/1"
* verify material of type "Pipeline" with name "${runtime_name:F2}" for pipeline "F3" with counter "5" and modification "0" has unchanged revision "${runtime_name:F2}/3/defaultStage/1"

* On Pipeline Dashboard Page
* Looking at pipeline "F2"
* Open trigger with options

* Select revision "go-agent-13.1.0-113.noarch" for material "repo-3:CP-2"
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "F2"
* Verify stage "1" is "Passed" on pipeline with label "5"
* Open changes section for counter "5"

* verify material of type "Package" with name "repo-3:CP-2" for pipeline "F2" with counter "5" and modification "0" has unchanged revision "go-agent-13.1.0-113.noarch"
* verify material of type "Package" with name "repo-1:P-1" for pipeline "F2" with counter "5" and modification "0" has unchanged revision "go-server-13.2.0-124.noarch"

On Pipeline Dashboard Page
looking at pipeline "F3"
verify pipeline is at label "5" and does not get triggered

* On Pipeline Dashboard Page
* Looking at pipeline "F2"
* Open trigger with options

* Select revision "go-server-13.1.0-122.noarch" for material "repo-1:P-1"
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "F2"
* Verify stage "1" is "Passed" on pipeline with label "6"
* Open changes section for counter "6"

* verify material of type "Package" with name "repo-3:CP-2" for pipeline "F2" with counter "6" and modification "0" has unchanged revision "go-agent-13.1.0-113.noarch"
* verify material of type "Package" with name "repo-1:P-1" for pipeline "F2" with counter "6" and modification "0" has unchanged revision "go-server-13.1.0-122.noarch"

* On Pipeline Dashboard Page
* Looking at pipeline "F3"
* Verify stage "1" is "Passed" on pipeline with label "6"
* Open changes section for counter "6"

* verify material of type "Package" with name "repo-3:CP-2" for pipeline "F3" with counter "6" and modification "0" has unchanged revision "go-agent-13.1.0-113.noarch"
* verify material of type "Pipeline" name "${runtime_name:F2}" for pipeline "F3" with counter "6" and modification "0"  has changed revision "${runtime_name:F2}/6/defaultStage/1"
* verify material of type "Pipeline" with name "${runtime_name:F1}" for pipeline "F3" with counter "6" and modification "0" has unchanged revision "${runtime_name:F1}/1/defaultStage/1"





Teardown of contexts
____________________
* Capture go state "RPMPackagesWithFanInAndTriggerWithOptions" - teardown
* With "2" live agents in directory "RPMPackagesWithFanInAndTriggerWithOptions" - teardown
* Setup file system based yum repos "repo1:go-server-13.1.0-122.noarch.rpm;repo3:go-agent-13.1.0-112.noarch.rpm" - teardown
* Using pipeline "F1, f2, f3" - teardown
* Package configuration - teardown


