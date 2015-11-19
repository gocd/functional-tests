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

ScheduleParticularVersionOfMaterials-Unit
=========================================

Setup of contexts 
* Capture go state "ScheduleParticularVersionOfMaterials-Unit" - setup

ScheduleParticularVersionOfMaterials-Unit
-----------------------------------------

tags: 3086, materials, restful api, unit, New Item, 3293, 3453

* Trigger a pipeline with some old specific revision
* Wait for the pipeline to finish
* Trigger a fresh instance of this pipeline in a normal way without specifing any specific revision
* verify that materials page which shows what changes since last pipeline instance doesnt compare to pipeline triggered with specific revision




We need more scenarios for using all version control systems




Teardown of contexts 
* Capture go state "ScheduleParticularVersionOfMaterials-Unit" - teardown


