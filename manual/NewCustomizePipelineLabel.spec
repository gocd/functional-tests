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

NewCustomizePipelineLabel
=========================

Setup of contexts 
* Capture go state "NewCustomizePipelineLabel" - setup

NewCustomizePipelineLabel
-------------------------

tags: admin


* configure label with a template "hello world" for pipeline "abc"
* trigger the pipeline
* verify the label starts with counter 1
* verify the pipeline label on various pages
* configure label with a template "hello world + ${material_name}" for pipeline "abc"
* trigger the pipeline
* verify the label starts with counter 1
* verify the pipeline label on various pages
* verify the old labels do not change

to include revision id in pipeline label

   1. user defines materialName for a material (svn,p4,hg,git,upstream pipeline)
   2. make sure that this configuration show in Pipeline Configuration tab correctly
   3. user involve materialName into a pipeline label template
   4. make sure that this configuration show in Pipeline Configuration tab correctly
   5. trigger the pipeline and wait for its finish
   6. make sure that in the generated pipeline instance, scm’s revision (svn,p4,hg,git) or upstream’s pipeline label is shown correctly in this pipeline label
   7. make sure that user can see material name for each material (svn,p4,hg,git,upstream pipeline) in build cause popup window or Materials tab

 to find artifacs on file system after pipeline couner is introduced

   1. setup a pipeline, and just include materialName in its label template
   2. trigger this pipeline and wait for its build finish
   3. get pipelie counter from CRUISEPIPELINECOUNTER property or CRUISEPIPELINECOUNTER environment variable. (svn,hg,git,p4 and dependent pipeline)
   4. make sure in file system of Cruise Server, artifacts dir structure is pipelineName/pipelineCounter/stageName/stageCounter/jobName

 to get/put/post artifacts by RESTful URL after pipeline counter is introduced

   1. user already install an old version Cruise Server (e.g. Cruise 1.3.0)
   2. on old server a pipeline is setup, and label template is defined for this pipeline. (e.g. labeltemplate=”pipeLabel.${COUNTER}”)
   3. and some pipeline instances are generated
   4. user upgrade Cruise Server to latest version (e.g. Cruise 1.3.2)
   5. trigger the pipeline then some new pipeline instances are generated
   6. make sure that user can get/put/post artifacts via pipeline counter by RESTful URL to those jobs with new pipeline instances
   7. make sure that user can get/put/post artifacts via pipeline label by RESTful URL to those jobs with new pipeline instances
   8. make sure that user can get/put/post artifacts via pipeline label by RESTful URL to those jobs with old pipeline instances (those pipeline instance which generated in old version Cruise)

 to get/post properties by RESTful URL after pipeline counter is introduced

   1. user already install an old version Cruise Server (e.g. Cruise 1.3.0)
   2. on old server a pipeline is setup, and label template is defined for this pipeline. (e.g. labeltemplate=”pipeLabel.${COUNTER}”)
   3. and some pipeline instances are generated
   4. user upgrade Cruise Server to latest version (e.g. Cruise 1.3.2)
   5. trigger the pipeline then some new pipeline instances are generated
   6. make sure that user can get/post properties via pipeline counter by RESTful URL to those jobs with new pipeline instances
   7. make sure that user can get/post properties via pipeline label by RESTful URL to those jobs with new pipeline instances, once two pipeline instances with the same pipeline label exist latest one will be returned.
   8. make sure that user can get/post properties via pipeline label by RESTful URL to those jobs with old pipeline instances (those pipeline instance which generated in old version Cruise)

 to re-run a stage by RESTful URL after pipeline counter is introduced

   1. user already install an old version Cruise Server (e.g. Cruise 1.3.0)
   2. on old server a pipeline is setup, and label template is defined for this pipeline. (e.g. labeltemplate=”pipeLabel.${COUNTER}”)
   3. and some pipeline instances are generated
   4. user upgrade Cruise Server to latest version (e.g. Cruise 1.3.2)
   5. trigger the pipeline then some new pipeline instances are generated
   6. make sure that user can re-run a stage via pipeline counter by RESTful URL to those stages with new pipeline instances
   7. make sure that user can re-run a stage via pipeline label by RESTful URL to those stages with new pipeline instances
   8. make sure that user can re-run a stage via pipeline label by RESTful URL to those stages with old pipeline instances (those pipeline instance which generated in old version Cruise)

 to navigate to stage detail page or job detail page after pipeline counter is introduced

   1. user already install an old version Cruise Server (e.g. Cruise 1.3.0)
   2. on old server a pipeline is setup, and label template is defined for this pipeline. (e.g. labeltemplate=”pipeLabel.${COUNTER}”)
   3. and some pipeline instances are generated
   4. user upgrade Cruise Server to latest version (e.g. Cruise 1.3.2)
   5. trigger the pipeline then some new pipeline instances are generated
   6. user go to pipeline activity page to check each pipeline instance’s status
   7. among those new pipeline instances user click one of its stage detail link to check a stage’s detail, then go to job detail page to check a job detail’s info
   8. make sure that correct stage/job detail page is opened
   9. among those old pipeline instances (those pipeline instance which generated in old version Cruise) user click one of its stage detail link to check a stage’s detail, then go to job detail page to check a job detail’s info
  10. make sure that correct stage/job detail page is opened

to operate/view cruise when pipeline instance with the same pipeline label exists

   1. setup a pipeline, define pipeline label template with materialName only
   2. define a fetch artifact task
   3. trigger the pipeline and wait for its finish, its building failed.
   4. force this pipeline and wait for its finish, then it passed
   5. several pipeline instance with the same pipeline label exist
   6. navigate among stage detail pages or job detail pages within these pipeline instances which with the same pipeline label
   7. make sure that user can be navigated to correct pages that he wants to
   8. rerun one of stage within these pipeline instances which with the same pipeline labels
   9. make sure that stage with the correct pipeline instance is re-runed
  10. make sure that each time correct artifacts are fetched







Teardown of contexts 
* Capture go state "NewCustomizePipelineLabel" - teardown


