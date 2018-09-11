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

CruiseErrorMessages
===================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-with-all-materials" - setup
* With "1" live agents in directory "CruiseErrorMessages" - setup
* Capture go state "CruiseErrorMessages" - setup

CruiseErrorMessages
-------------------

tags: 1996, svn support, diagnostics messages, timer, 2272, automate, failing, linux

* Looking at pipeline "basic-pipeline-with-all-materials"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

* Save config by name "LastValid"
* Make pipeline "basic-pipeline-with-all-materials" use all non existant material
* Using timer with invalid spec "* ? ? ? * ? 2012"

* Looking at pipeline "basic-pipeline-with-all-materials"
* Trigger pipeline
* On Admin page
* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* On Pipeline Dashboard Page
* Navigate to pipeline dependencies for "basic-pipeline-with-all-materials" "1" "defaultStage" "1"

* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "1"

* Turn on autoRefresh - On Any Page
* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "description" contains "Failed to run git clone command"
* Verify error "description" contains "Failed to run hg clone command"
* Verify error "description" contains "Error performing command: --- Command --- svn log"
* Verify error "description" contains "Error performing command: --- Command --- p4 login"
* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "Invalid cron syntax"

* Using timer with spec "* * * * * ? 2012"

* Verify there are at least "4" errors
* Open error and warning messages popup

* Verify error "message" does not contain "Invalid Configuration"
* Verify error "description" does not contain "Invalid cron syntax"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-with-all-materials"

* Make cruise config file invalid

* On Admin page
* Verify there are at least "5" errors
* Open error and warning messages popup

* Verify error "message" contains "Invalid Configuration"
* Verify error "description" contains "XML document structures must start and end within the same entity"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-with-all-materials"

* Restore config by name "LastValid"

* On Admin page
* Verify there are no error messages

* Verify there are no error messages

* Verify there are no error messages

* Verify there are no error messages

* Verify there are no error messages

* On Pipeline Dashboard Page
* Navigate to pipeline dependencies for "basic-pipeline-with-all-materials" "1" "defaultStage" "1"

* On Admin page
* Verify there are no error messages

* On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "1"

* Turn on autoRefresh - On Any Page

* Verify there are no error messages

* Verify there are no error messages

* Turn off autoRefresh - On Any Page


Teardown of contexts
____________________
* Capture go state "CruiseErrorMessages" - teardown
* With "1" live agents in directory "CruiseErrorMessages" - teardown
* Using pipeline "basic-pipeline-with-all-materials" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


