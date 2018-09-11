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

BisectingPipelines
==================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-svn-pipeline" - setup
* With "1" live agents in directory "BisectPipelines" - setup
* Capture go state "BisectingPipelines" - setup

BisectingPipelines
------------------

tags: shine, bisect, automate

* Trigger pipelines "basic-svn-pipeline" and wait for labels "1" to pass

* With material named "svn" in pipeline "basic-svn-pipeline"
* Checkin file "foo4.txt" as user "cceuser4" with message "Comment a"
* Remember current version as "rev-4"
* Checkin file "foo5.txt" as user "cceuser5" with message "Comment b"
* Remember current version as "rev-5"
* Checkin file "foo6.txt" as user "cceuser6" with message "Comment c"
* Remember current version as "rev-6"
* Checkin file "foo7.txt" as user "cceuser7" with message "Comment d"
* Remember current version as "rev-7"
* Checkin file "foo8.txt" as user "cceuser8" with message "Comment e"
* Remember current version as "rev-8"

* Trigger pipelines "basic-svn-pipeline" and wait for labels "2" to pass
* Open changes section for counter "2"


* Looking at material of type "svn" named "svn" for pipeline "basic-svn-pipeline" with counter "2"
* Verify modification "0" has revision "rev-8"
* Verify material has changed
* Verify modification "1" has revision "rev-7"
* Verify material has changed
* Verify modification "2" has revision "rev-6"
* Verify material has changed
* Verify modification "3" has revision "rev-5"
* Verify material has changed
* Verify modification "4" has revision "rev-4"
* Verify material has changed

* Looking at pipeline "basic-svn-pipeline"
* Open trigger with options

* Using material "svn"
* Set revision to trigger with as "rev-6"
* Trigger

* Looking at pipeline "basic-svn-pipeline"
* Wait for labels "3" to pass
* Verify pipeline "basic-svn-pipeline" is triggered by "anonymous"
* Open changes section for counter "3"


* Looking at material of type "svn" named "svn" for pipeline "basic-svn-pipeline" with counter "3"
* Verify modification "0" has revision "rev-6"
* Verify material has not changed


* Navigate to the pipeline history page for pipeline "basic-svn-pipeline"

* Looking at pipeline with label "2"
* Verify build cause message contains "Comment a"
* Verify build cause message contains "Comment b"
* Verify build cause message contains "Comment c"
* Verify build cause message contains "Comment d"
* Verify build cause message contains "Comment e"
* Verify pipeline is triggered by "anonymous"
* Pause pipeline on activity page

* On Pipeline Dashboard Page
* Verify pipeline is paused by "anonymous"





Teardown of contexts
____________________
* Capture go state "BisectingPipelines" - teardown
* With "1" live agents in directory "BisectPipelines" - teardown
* Using pipeline "basic-svn-pipeline" - teardown
* Basic configuration - teardown


