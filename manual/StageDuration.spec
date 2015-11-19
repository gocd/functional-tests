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

StageDuration
=============

StageDuration
-------------

tags: 4460, Stage Duration Graph

* Verify if stage duration page is shown with in Stage Details Page
* Verify if bar chart is being shown on click on Stage Duration tab
* Verify if (Y-coordinate) represents time duration and X axis corresponds to pipeline instance listing
* Verify if the latest stage is being shown in right hand side and oldest stage is being shown if left side
* Verify if only passed and failed stages are being shown.
* Verify on hovering on a particular bar shows the label of the pipeline instance the stage corresponds to
* Verify 50 stage instances belonging to 50 pipeline runs are shown as a part of trend line analysis
* Verify if on clicking on a different stage from the stage listing bar changes the trends in the stage duration chart displayed and also stage duration corresponds to stage in context.
* Verify on clicking a stage instance from stage history would take to that corresponding stage instances' stage duration graph
* Verify hovering over a bar representing a stage instance shows label, stage duration and when was it started at.
* Verify if clicking on a stage respresented by a bar in the graph takes you to the respective job details page

* Verify if cancelled stage is not being shown
* Verify if Stages created as a result of job rerun is not being shown
* Verify if stages that are currently getting executed is not being shown in the graph
* Verify if bar that is being displayed corresponds the stage in context


Scenario : User opens a particular pipeline that is having multiple stages and has been executed more than 300 times

* There is a pipeline that is having five stages namely development, dist, smoke-firefox, smoke-ie, dist-all
* The pipeline has at least 1000 runs
* 20 of the stages are created due to job reruns
* 40 of the stages are cancelled on execution
* 50 of the stages has duration much larger than average ( 25 of them are on sequence (50-75 runs) , 25 of them are scattered across different runs)
* One of the pipeline instance is currently executed as a consequence of this the pipeline is being shown as green

* User clicks on stage duration tab for a particular pipeline instance

* Job rerun, cancelled stage, stage that is getting currently executed should not be shown

* User opens 75th pipeline instance

* The pipeline runs should not be shown as outliers

* User opens 50th pipeline instance

* The pipeline run should be shown as outliers



