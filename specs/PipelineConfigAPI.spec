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

Pipeline Config API
=====================

Setup of contexts
* Pipeline API configuration - setup
* Capture go state "PipelineConfigAPI" - setup



Pipeline Config API
----------------

tags: configAPI, pipeline_config

* Using Pipeline Config API
* Create pipeline "upstream" from "upstream_pipeline.json"
* Get pipeline "upstream"
* Create pipeline "complete_pipeline" from "complete_pipeline.json"
* Get pipeline "complete_pipeline"
* Update pipeline "complete_pipeline" with "complete_pipeline_updated.json"
* Verify pipeline "complete_pipeline" has "stages.name" has item "updated_stage"

* Delete pipeline "complete_pipeline" as "view" user should return access denied error
* Delete pipeline "complete_pipeline" as "admin" user should have "message" as "The pipeline 'complete_pipeline' was deleted successfully"

tear downs
___________

* Capture go state "PipelineConfigAPI" - teardown






