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

BasicParamSubstitutionWithTemplate
==================================

     |Param0                         |Param1          |Param2                                    |
     |-------------------------------|----------------|------------------------------------------|
     |template-params-pipeline-trunk |echo trunk-dir. |Tasks:Exec ( Command=echo, Args=trunk-dir |
     |template-params-pipeline-branch|echo branch-dir.|Tasks:Exec ( Command=echo, Args=branch-dir|
Setup of contexts
* Basic configuration - setup
* Using pipeline "template-params-pipeline-trunk, template-params-pipeline-branch" - setup
* With "1" live agents in directory "Params" - setup
* Capture go state "BasicParamSubstitutionWithTemplate" - setup

BasicParamSubstitutionWithTemplate
----------------------------------

tags: 4207, params, template


* Trigger pipelines <Param0> and wait for labels "1" to pass
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console contains <Param1>








Teardown of contexts
____________________
* Capture go state "BasicParamSubstitutionWithTemplate" - teardown
* With "1" live agents in directory "Params" - teardown
* Using pipeline "template-params-pipeline-trunk, template-params-pipeline-branch" - teardown
* Basic configuration - teardown


