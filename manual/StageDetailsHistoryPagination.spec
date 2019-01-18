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

StageDetailsHistoryPagination
=============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-ls" - setup
* Capture go state "StageDetailsHistoryPagination" - setup

StageDetailsHistoryPagination
-----------------------------

tags: 3609, stage-details, automate, ignore
Stage History page change is validated as part of the tests here - https://github.com/gocd/gocd/blob/master/server/webapp/WEB-INF/rails.new/spec/javascripts/stage_history_spec.js
So ignore this test at E2E level

* Trigger and cancel pipeline "pipeline-ls" "21" times
* Navigate to stage "defaultStage" of run "21" having counter "1"

* Turn off auto refresh
* Verify stage history has "21, 20, 19, 18, 17, 16, 15, 14, 13, 12"
* Verify shows history page "2"
* Click on history page "2"
* Verify stage history has "11, 10, 9, 8, 7, 6, 5, 4, 3, 2"
* Verify shows history page "3"
* Click on history page "3"
* Verify stage history has "1"




Teardown of contexts
____________________
* Capture go state "StageDetailsHistoryPagination" - teardown
* Using pipeline "pipeline-ls" - teardown
* Basic configuration - teardown


