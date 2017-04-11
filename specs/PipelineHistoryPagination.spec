// --GO-LICENSE-START--
// Copyright 2017 ThoughtWorks, Inc.
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

PipelineHistoryPagination
=============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-ls" - setup
* Capture go state "PipelineHistoryPagination" - setup

PipelineHistoryPagination
-----------------------------

tags: pipeline-history

* Trigger and cancel pipeline "pipeline-ls" "23" times
* Navigate to pipeline history of pipeline "pipeline-ls"

* Verify shows pipeline history page "1"
* Verify pipeline history has "23, 22, 21, 20, 19, 18, 17, 16, 15, 14"
* Search for "20" on pipeline history page
* Verify pipeline history has "20"
* Search for "" on pipeline history page
* Verify pipeline history has "23, 22, 21, 20, 19, 18, 17, 16, 15, 14"

* Click on next pipeline history page
* Verify shows pipeline history page "2"
* Verify pipeline history has "13, 12, 11, 10, 9, 8, 7, 6, 5, 4"

* Click on pipeline history page "3"
* Verify shows pipeline history page "3"
* Verify pipeline history has "3, 2, 1"

* Click on previous pipeline history page
* Verify shows pipeline history page "2"
* Verify pipeline history has "13, 12, 11, 10, 9, 8, 7, 6, 5, 4"

Teardown of contexts
____________________
* Capture go state "PipelineHistoryPagination" - teardown
* Using pipeline "pipeline-ls" - teardown
* Basic configuration - teardown


