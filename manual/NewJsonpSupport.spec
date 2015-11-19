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

NewJsonpSupport
===============

Setup of contexts 
* Completed svn pipeline - setup
* Capture go state "NewJsonpSupport" - setup

NewJsonpSupport
---------------

tags: svn support, customizable reports, scheduling


to support JSONP, then user can write a Go macro for Mingle

   1. setup a Go Server and switch on its security
   2. make sure that user can get pipeline history data through jsonp. 
       e.g.http://admin:badger@<hostname>:8153/go/pipelineHistory.json?pipelineName=<pipelineName>
   3. make sure that only users with view permission can get those pipeline history data via jsonp






Teardown of contexts 
* Capture go state "NewJsonpSupport" - teardown
* Completed svn pipeline - teardown


