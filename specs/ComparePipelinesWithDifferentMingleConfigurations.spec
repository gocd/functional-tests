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

ComparePipelinesWithDifferentMingleConfigurations
=================================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-with-mingle-config,down-pipeline-with-mingle-config" - setup
* With "1" live agents in directory "compare_pipelines" - setup
* Capture go state "ComparePipelinesWithDifferentMingleConfigurations" - setup

ComparePipelinesWithDifferentMingleConfigurations
-------------------------------------------------

tags: 4643, automate, compare_pipeline, dependency_walk, 4687

* With material "hg-for-down-pipeline-with-mingle-config" for pipeline "down-pipeline-with-mingle-config"
* Checkin file "hg-new-file" as user "go" with message "Added hg-new-file"
* Remember current version as "hg-new"
* With material "git-for-pipeline-with-mingle-config" for pipeline "pipeline-with-mingle-config"
* Checkin file "git-new-file" as user "go <go@thoughtworks.com>" with message "Added git-new-file"
* Remember current version as "git-new"

* Trigger pipelines "pipeline-with-mingle-config" and wait for labels "1" to pass
* Trigger pipelines "down-pipeline-with-mingle-config" and wait for labels "1" to pass

* Looking at pipeline "down-pipeline-with-mingle-config"
* Compare pipeline instance "1" with "1"

* Verify that there are "3" materials
* Verify displays revision "${runtime_name:pipeline-with-mingle-config}/1/defaultStage/1" having label "1" under pipeline named "${runtime_name:pipeline-with-mingle-config}"
* Verify displays revision "hg-new" having comment "Added hg-new-file" under "Mercurial" named "hg-for-down-pipeline-with-mingle-config" for pipeline "down-pipeline-with-mingle-config"
* Verify displays revision "git-new" having comment "Added git-new-file" under "Git" named "git-for-pipeline-with-mingle-config" for pipeline "pipeline-with-mingle-config"

* Changing "mingle" attribute "baseUrl" to "https://mingle.example.com" for pipeline "down-pipeline-with-mingle-config"

* On Pipeline Dashboard Page
* Looking at pipeline "down-pipeline-with-mingle-config"
* Compare pipeline instance "1" with "1"

* Verify that there are "3" materials
* Verify displays revision "${runtime_name:pipeline-with-mingle-config}/1/defaultStage/1" having label "1" under pipeline named "${runtime_name:pipeline-with-mingle-config}"
* Verify displays revision "hg-new" having comment "Added hg-new-file" under "Mercurial" named "hg-for-down-pipeline-with-mingle-config" for pipeline "down-pipeline-with-mingle-config"
* Verify displays revision "git-new" having comment "Added git-new-file" under "Git" named "git-for-pipeline-with-mingle-config" for pipeline "pipeline-with-mingle-config"


Teardown of contexts
____________________
* Capture go state "ComparePipelinesWithDifferentMingleConfigurations" - teardown
* With "1" live agents in directory "compare_pipelines" - teardown
* Using pipeline "pipeline-with-mingle-config,down-pipeline-with-mingle-config" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


