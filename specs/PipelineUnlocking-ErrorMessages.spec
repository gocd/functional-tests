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

PipelineUnlocking-ErrorMessages
===============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-slow, basic-pipeline-fast, viewable-pipeline, admin-pipeline, environment-pipeline, environment-pipeline-locked" - setup
* Capture go state "PipelineUnlocking-ErrorMessages" - setup

PipelineUnlocking-ErrorMessages
-------------------------------

tags: 3378, 3411, feeds, restful api, shine, 3426, automate

* Logged in as "view"

* Verify unauthorized to unlock "admin-pipeline"
* Verify unauthorized to unlock "viewable-pipeline"
* Verify unlocking "basic-pipeline-fast" is not acceptable because "no lock exists within the pipeline configuration for"
* Verify unlocking "basic-pipeline-slow" is not acceptable because "lock exists within the pipeline configuration but no pipeline instance is currently in progress"
* Verify unlocking "environment-pipeline-locked" is not acceptable because "lock exists within the pipeline configuration but no pipeline instance is currently in progress"
* Verify unlocking "environment-pipeline" is not acceptable because "no lock exists within the pipeline configuration for"
* Verify unlocking "foo" fails as pipeline is not found





Teardown of contexts
* Capture go state "PipelineUnlocking-ErrorMessages" - teardown
* Using pipeline "basic-pipeline-slow, basic-pipeline-fast, viewable-pipeline, admin-pipeline, environment-pipeline, environment-pipeline-locked" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


