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

EnvironmentsScreenAutoRefresh
=============================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "multiple-materials-pipeline-run-till-file-exists" - setup
* With "1" live agents in directory "UIAutoRefreshForEnvironments" - setup
* Capture go state "EnvironmentsScreenAutoRefresh" - setup

EnvironmentsScreenAutoRefresh
-----------------------------

tags: environment, ajax, 3505, automate, deployment lightbox

Verify that the Environments screen automatically refreshes using Ajax. By default a page opens without autorefresh. We need to explicitly turn it on when we want it during open.

* assigning "1" agents  to environment "uat"
* adding pipeline "multiple-materials-pipeline-run-till-file-exists" to "uat" environment
* enable auto update for pipeline "multiple-materials-pipeline-run-till-file-exists"

* turn on autoRefresh
* looking at pipeline "multiple-materials-pipeline-run-till-file-exists" of "uat" environment
* deploy latest
* wait for the pipeline to have label "1"
* stop "1" jobs waiting for file to exist
* wait for status "Passed: defaultStage" to show up for the pipeline
* wait for the pipeline to have label "1"
* expand materials
* verify latest of "hg-material" material is currently deployed
* remember version of "hg-material" currently deployed

* with material named "hg-material" in pipeline "multiple-materials-pipeline-run-till-file-exists"
* checkin file "foo.txt" as user "twist" with message "Added foo.txt"

* turn on autoRefresh
* looking at pipeline "multiple-materials-pipeline-run-till-file-exists" of "uat" environment
* expand materials
* verify materials remain expanded
* collapse materials
* verify materials remain collapsed
* verify material has new revisions
* expand materials
* verify material "hg-material" has new revisions
* change materials

* using material "hg-material"
* verify currently deployed revision is shown
* select latest revision for deploy
* deploy

* turn on autoRefresh
* looking at pipeline "multiple-materials-pipeline-run-till-file-exists" of "uat" environment
* wait for the pipeline to have label "2"
* stop "1" jobs waiting for file to exist
* wait for status "Passed: defaultStage" to show up for the pipeline
* verify latest of "hg-material" material is currently deployed
* looking at pipeline "multiple-materials-pipeline-run-till-file-exists" of "uat" environment
* change materials

* using material "svn-material"
* verify search box is displayed with "1" matches
* using material "hg-material"
* verify search box is displayed with "2" matches
* select revision "2" in search box
* verify material summary contains selected revision
* verify material summary is marked as modified
* clear search box
* verify search box is displayed with "2" matches
* select revision "1" in search box
* verify material summary contains selected revision
* verify material summary is not marked as modified
* search for "foo" and expect "1" matches
* search for "nothing matches" and expect "0" matches
* search for latest revision number and expect "1" matches
* search for "twist" and expect "1" matches
* search for "Added foo.txt" and expect "1" matches





Teardown of contexts 
* Capture go state "EnvironmentsScreenAutoRefresh" - teardown
* With "1" live agents in directory "UIAutoRefreshForEnvironments" - teardown
* Using pipeline "multiple-materials-pipeline-run-till-file-exists" - teardown
* Basic configuration - teardown


