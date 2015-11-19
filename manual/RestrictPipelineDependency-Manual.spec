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

RestrictPipelineDependency-Manual
=================================

Setup of contexts 
* Capture go state "RestrictPipelineDependency-Manual" - setup

RestrictPipelineDependency-Manual
---------------------------------

tags: 3289, pipeline, dependencies, manual

* verify that user gets an error message if config file has the following scenario


<materials>
  <pipeline pipelineName = "upstreamLibrary" stage="stage3"/>
  <pipeline pipelineName = "upstreamLibrary" stage="stage4"/>
</materials>





Teardown of contexts 
* Capture go state "RestrictPipelineDependency-Manual" - teardown


