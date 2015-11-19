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

ApiAndFeedSecurity
==================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast" - setup
* With "1" live agents in directory "NOT-USED" - setup
* Capture go state "ApiAndFeedSecurity" - setup

ApiAndFeedSecurity
------------------

tags: 3351, 3365, feeds, shine

* looking at pipeline "basic-pipeline-fast"
* trigger pipeline
* verify stage "1" is "Passed" on pipeline with label "1"
* trigger pipeline

* using "HTTP"

* Verify stage link for current pipeline with counter "1" and stage "defaultStage" with stage counter "1" is approved by    "anonymous"

* using "HTTPS"

* Verify stage link for current pipeline with counter "1" and stage "defaultStage" with stage counter "1" is approved by    "anonymous"

* using "BROWSER"

* Verify stage link for current pipeline with counter "1" and stage "defaultStage" with stage counter "1" is approved by    "anonymous"






Teardown of contexts 
* Capture go state "ApiAndFeedSecurity" - teardown
* With "1" live agents in directory "NOT-USED" - teardown
* Using pipeline "basic-pipeline-fast" - teardown
* Basic configuration - teardown


