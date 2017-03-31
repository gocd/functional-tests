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

NewStageCancellation
====================

Setup of contexts 
* Pipeline "test" with cancel tasks - setup
* Capture go state "NewStageCancellation" - setup

NewStageCancellation
--------------------

tags: 2511, diagnostics messages, ant support, cancel, 2511, in-progress, personal notification, svn support, scheduling


Possibly use - http://quintanasoft.com/dumbster

Old twist tests: CancellingStageRunsOnCancelTask, CancellingAStageSendsAnEmailWithUserInformation

* configure cruise with email notification and stage "test" with on cancel task
* open pipeline dashboard
* trigger build
* verify job is assigned to an agent
* cancel stage on pipeline "test"
* verify job is cancelled
* Wait for console to contain "Cancel task:"
* verify email contains "The stage was cancelled by "






Teardown of contexts 
* Capture go state "NewStageCancellation" - teardown
* Pipeline "test" with cancel tasks - teardown


