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

NewPipelineHistoryPage
======================

Setup of contexts 
* Completed svn pipeline - setup
* Capture go state "NewPipelineHistoryPage" - setup

NewPipelineHistoryPage
----------------------

tags: 1256, svn support, dashboard

Can't be automated.

* open pipeline history page
* another user changed configuration for this pipeline
* user should see new pipeline configuration without refresh page
* new pipeline is scheduled
* user should see new stage without refresh page

can be automated

* open Pipeline dashboard
* user navigate to pipeline history
* user can see Id and build cause for certain pipeline
* user navigate to stage history to find detail information

could be automated

* open pipeline history page for pipeline in pipeline group "defaultGroup"
* verify shows pipeline history page for pipeline in pipeline group "defaultGroup"
* verify access pipeline history page in group "secondGroup" got unauthorized error




Teardown of contexts 
* Capture go state "NewPipelineHistoryPage" - teardown
* Completed svn pipeline - teardown


