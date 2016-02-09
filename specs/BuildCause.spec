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

BuildCause
==========

Setup of contexts
* Basic configuration - setup
* Using pipeline "up, down" - setup
* With "1" live agents in directory "BuildCause" - setup
* Capture go state "BuildCause" - setup

BuildCause
----------

tags: 3915, pipeline, automate, failing, stage1

* Trigger pipelines "up" and wait for labels "1" to pass
* Verify pipeline "up" is triggered by "anonymous"
* Navigate to materials for "up" "1" "defaultStage" "1"

* Looking at material of type "Mercurial" named "hg-material"
* Verify modification "0" has latest revision
* Verify material has changed - Already On Build Cause Section

* On Pipeline Dashboard Page
* Trigger pipelines "down" and wait for labels "1" to pass
* Navigate to materials for "down" "1" "defaultStage" "1"

* Looking at material of type "Pipeline" named "${runtime_name:up}"
* Verify modification "0" has revision "${runtime_name:up}/1/defaultStage/1" - Already On Build Cause Section
* Verify material has changed - Already On Build Cause Section

* On Pipeline Dashboard Page
* Looking at pipeline "down"
* Open changes section for counter "1"

* Looking at material of type "Pipeline" named "${runtime_name:up}" for pipeline "down" with counter "1"
* Verify modification "0" has revision "${runtime_name:up}/1/defaultStage/1"
* Verify material has changed

* With material named "hg-material" in pipeline "up"
* Checkin file "foo.txt" as user "user" with message "Comment user"
* Checkin file "bar.txt" as user "loser" with message "Comment loser"

* On Pipeline Dashboard Page
* Trigger pipelines "up" and wait for labels "2" to pass
* Navigate to materials for "up" "2" "defaultStage" "1"

* Looking at material of type "Mercurial" named "hg-material"
* Verify modification "0" is checked in by "loser" with comment "Comment loser"
* Verify modification "1" is checked in by "user" with comment "Comment user"
* Verify material has changed - Already On Build Cause Section

* On Pipeline Dashboard Page
* Trigger pipelines "up" and wait for labels "3" to pass
* Navigate to materials for "up" "3" "defaultStage" "1"

* Looking at material of type "Mercurial" named "hg-material"
* Verify modification "0" is checked in by "loser" with comment "Comment loser"
* Verify material has not changed - Already On Build Cause Section

* On Pipeline Dashboard Page
* Trigger pipelines "down" and wait for labels "2" to pass
* Navigate to materials for "down" "2" "defaultStage" "1"

* Looking at material of type "Pipeline" named "${runtime_name:up}"
* Verify modification "0" has revision "${runtime_name:up}/3/defaultStage/1" - Already On Build Cause Section
* Verify material has changed - Already On Build Cause Section

* On Pipeline Dashboard Page
* Trigger pipelines "down" and wait for labels "3" to pass
* Navigate to materials for "down" "3" "defaultStage" "1"

* Looking at material of type "Pipeline" named "${runtime_name:up}"
* Verify modification "0" has revision "${runtime_name:up}/3/defaultStage/1" - Already On Build Cause Section
* Verify material has not changed - Already On Build Cause Section





Teardown of contexts
____________________
* Capture go state "BuildCause" - teardown
* With "1" live agents in directory "BuildCause" - teardown
* Using pipeline "up, down" - teardown
* Basic configuration - teardown


