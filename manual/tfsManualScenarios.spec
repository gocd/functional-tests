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

tfsManualScenarios
==================

tfsManualScenarios
------------------

tags: TFS

Scenario 1: General configuration of tfs using UI

Given: 

	There is a server with tfs client configured
	There is an agent with tfs client configured
	User clicked on New pipeline wizard and clicked on next button after entering valid values for pipeline name


	User selects Team Foundation Server as material

	User should see the below fields which had been marked as mandatory
	url
	username
	password
	workspace
	workspace owner
	project path

	Poll for new changes check box should be clicked by default

	The user enters valid values for all the fields and clicks check connection

	Connection ok text should be displayed

	User enters or leaves fields blank so that they become invalid and clicks on next button

	Appropriate inline error messages should be shown for all the fields


	Valid data is entered and finish button is clicked on

	Respective tfs configuration should be saved.
	Passwords should be encrypted.
	On unpausing the pipeline, it should execute without issues
	On new material checking polling should pickup the changes as required.

Scenario 2: Configuring two tfs material for a pipeline

	User has multiple tfs materials to be configured
	
	user saves a valid configuration with multiple tfs materials

	It should be saved and unpausing the pipeline should work without issues


Scenario 3: User a configuring a tfs material with workspace name that has been previously been used

	User has used the workspace name for a different pipeline

When:	
	User tries configure the same workspace for one more pipeline and save it

	User should not be allowed to save

Scenario 4: user is configuring a tfs material with material name and material name is being used as pipeline label

	User has created a tfs material with material name and the material name is being used as a part of label

 	The pipeline is triggered automatically because of checkin

	After the execution of the pipeline, it should appropriate material number

Scenario 5: User has configured black listing for tfs

	user has created a pipeline with tfs material for which black listing is configured

 	File that has been black listed is being modified

	Pipeline should not be triggered 

Scenario 6: User modifies the url and also the workspace associated

	User has created a pipeline and pipeline has completed one round of execution

	Url modifies the url to point to a different collection and also changes the workspace name

	The pipeline should run as expected

Scenario 7: Clean working directory has been introduced to one of the tfs materials


	User has created a pipeline with tfs material that has clean working directory turned on

	Pipeline is triggered manually or because of checkin

	Working directory should be clean up and materials should be checked into the newly created directory with the same name
	pipeline execution should go good

Scenario 7: Using performs comparing operations

* User has a pipeline with tfs material configured
* Pipeline has been triggered many times due to checkins

* The user clicks on compare link for the pipeline

* He should be shown list of checkins



