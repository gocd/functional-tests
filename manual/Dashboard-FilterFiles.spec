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

Dashboard-FilterFiles
=====================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-with-filter" - setup
* With "1" live agents in directory "Dashboard-FilterFiles" - setup
* Capture go state "Dashboard-FilterFiles" - setup

Dashboard-FilterFiles
---------------------

tags: 2482, pipeline-activity, manual-approval, rerun, 1646, force-build, automate, filter, linux, perforce, 6118, stage1

This test should work once bug #3656 is fixed.

* Click on pipeline "basic-pipeline-with-filter" for editing

* Open material listing page

* Edit material "p4"

* Check connectivity should be successful

* Making pipeline "basic-pipeline-with-filter" auto update

* Looking at pipeline "basic-pipeline-with-filter"
* Verify stage "1" is "Passed" on pipeline with label "1"

* With material named "hg" in pipeline "basic-pipeline-with-filter"
* Checkin file "foo1.ignore" as user "twist" with message "Added foo1.ignore"
* With material named "svn" in pipeline "basic-pipeline-with-filter"
* Checkin file "foo2.ignore" as user "twist" with message "Added foo2.ignore"

* Verify pipeline is at label "1" and does not get triggered

* With material named "hg" in pipeline "basic-pipeline-with-filter"
* Checkin file "foo.trigger" as user "twist" with message "Added foo.trigger"

* Verify stage "1" is "Building" on pipeline with label "2"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* Looking at material of type "Mercurial" named "hg" for pipeline "basic-pipeline-with-filter" with counter "2"
* Verify modification "0" has comment containing "Added foo.trigger"
* Verify modification "1" has comment containing "Added foo1.ignore"
* Looking at material of type "Subversion" named "svn" for pipeline "basic-pipeline-with-filter" with counter "2"
* Verify modification "0" has comment containing "Added foo2.ignore"

* With material named "git" in pipeline "basic-pipeline-with-filter"
* Checkin file "foo3.ignore" as user "Twist <twist@cruise.thoughtworks.com>" with message "Added foo3.ignore"
* With material named "p4" in pipeline "basic-pipeline-with-filter"
* Checkin file "foo4.ignore" as user "cceuser" with message "Added foo4.ignore"

* Verify pipeline is at label "2" and does not get triggered
* Trigger pipelines "basic-pipeline-with-filter" and wait for labels "3" to pass
* Open changes section for counter "3"

* Looking at material of type "Git" named "git" for pipeline "basic-pipeline-with-filter" with counter "3"
* Verify modification "0" has comment containing "Added foo3.ignore"
* Looking at material of type "Perforce" named "p4" for pipeline "basic-pipeline-with-filter" with counter "3"
* Verify modification "0" has comment containing "Added foo4.ignore"




Teardown of contexts
____________________
* Capture go state "Dashboard-FilterFiles" - teardown
* With "1" live agents in directory "Dashboard-FilterFiles" - teardown
* Using pipeline "basic-pipeline-with-filter" - teardown
* Basic configuration - teardown


