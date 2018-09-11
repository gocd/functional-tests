Copyright 2018 ThoughtWorks, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

PipelineDashboardAndPipelineActivitySecurityAtStageLevel
========================================================

Setup of contexts
* Permissions configuration - setup
* Login as "admin" - setup
* Using pipeline "P5, P6,P7" - setup
* With "3" live agents in directory "PipelineDashboardSecurity" - setup
* Capture go state "PipelineDashboardAndPipelineActivitySecurityAtStageLevel" - setup

PipelineDashboardAndPipelineActivitySecurityAtStageLevel
--------------------------------------------------------

tags: 6786, Permissions, long_running

* Trigger pipelines "P5" and wait for labels "1" to pass
* Trigger pipelines "P6" and wait for labels "1" to pass
* Trigger pipelines "P7" and wait for labels "1" to pass


* Logout - On Any Page

* Login as "raghu"

Pipeline Visibility
* PipelineVisibility 
     |pipeline name|is visible?|can operate using ui?|can operate?|can pause using ui?|can pause using api?|
     |-------------|-----------|---------------------|------------|-------------------|--------------------|
     |P5           |true       |true                 |true        |true               |true                |
     |P6           |true       |true                 |true        |true               |true                |
     |P7           |true       |false                |false       |true               |true                |

* Looking at pipeline "P5"
* Wait for labels "2" to pass
* Looking at pipeline "P6"
* Wait for labels "2" to pass

* verify user has operate permissions on "defaultStage" for pipeline "P5" on Pipeline Activity Page

* On Pipeline Dashboard Page
* verify user has operate permissions on "firstStage" for pipeline "P6" on Pipeline Activity Page
* Verify "secondStage" can be approved
* Approve stage "secondStage"

* On Pipeline Dashboard Page
* verify user does not have stage "firstStage" operate permissions for pipleine "P7" on pipeline activity page
* Verify "secondStage" can be approved
* Approve stage "secondStage"

* Logout - On Any Page

* Login as "pavan"


Behavior of P7 is similar for user 'pavan' and 'raghu', hence not verified
Pipeline Visibility
* PipelineVisibility 
     |pipeline name|is visible?|can operate using ui?|can operate?|can pause using ui?|can pause using api?|
     |-------------|-----------|---------------------|------------|-------------------|--------------------|
     |P5           |true       |false                |false       |true               |true                |
     |P6           |true       |true                 |true        |true               |true                |

* Looking at pipeline "P6"
* Wait for labels "3" to pass

* On Pipeline Dashboard Page
* Looking at pipeline "P5"
* Navigate to the pipeline history page for pipeline "P5"
* Looking at pipeline with label "1"
* Verify stage "defaultStage" of pipeline cannot be rerun
* Pause pipeline on activity page

* On Pipeline Dashboard Page
* Verify pipeline is paused by "pavan"
* Unpause pipeline
* verify user has operate permissions on "firstStage" for pipeline "P6" on Pipeline Activity Page

* On Pipeline Dashboard Page
* Looking at pipeline "P6"
* Navigate to the pipeline history page for pipeline "P6"
* Looking at pipeline with label "1"
* Verify stage "secondStage" of pipeline cannot be rerun

* On Pipeline Dashboard Page
* Looking at pipeline "P6"
* Navigate to the pipeline history page for pipeline "P6"

* Looking at pipeline with label "2"
* Verify "secondStage" cannot be approved

* Logout - On Any Page

* Login as "group1View"

Pipeline Visibility
* PipelineVisibility 
     |pipeline name|is visible?|can operate using ui?|can operate?|can pause using ui?|can pause using api?|
     |-------------|-----------|---------------------|------------|-------------------|--------------------|
     |P5           |true       |true                 |true        |true               |true                |
     |P6           |true       |true                 |true        |true               |true                |
     |P7           |true       |true                 |true        |true               |true                |

* Looking at pipeline "P5"
* Wait for labels "3" to pass
* Looking at pipeline "P6"
* Wait for labels "4" to pass
* Looking at pipeline "P7"
* Wait for labels "2" to pass

* On Pipeline Dashboard Page
* verify user has operate permissions on "defaultStage" for pipeline "P5" on Pipeline Activity Page
* On Pipeline Dashboard Page
* verify user does not have stage "secondStage" operate permissions for pipleine "P6" on pipeline activity page

* On Pipeline Dashboard Page
* Looking at pipeline "P6"
* Navigate to the pipeline history page for pipeline "P6"

* Looking at pipeline with label "2"
* Verify "secondStage" cannot be approved
* On Pipeline Dashboard Page
* verify user has operate permissions on "firstStage" for pipeline "P7" on Pipeline Activity Page
* Verify stage "secondStage" of pipeline can be rerun
* Rerun stage "secondStage" - Already On Pipeline History Page
* Verify "secondStage" stage can be cancelled






Teardown of contexts
____________________
* Capture go state "PipelineDashboardAndPipelineActivitySecurityAtStageLevel" - teardown
* With "2" live agents in directory "PipelineDashboardSecurity" - teardown
* Using pipeline "P5, p6,P7" - teardown
* Login as "admin" - teardown
* Permissions configuration - teardown


