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

PreventArtifactUploadManual
===========================

PreventArtifactUploadManual
---------------------------

tags: #4131

Use VHD (Virtual Hard Drive) to create partitions in windows. Computer > Manage > Disk Manager


Scenario One : Single Pipeline upload

* There is a clean empty directory with 20 GB hard disk space name G colon
* Go Server has been installed in the G colon
* Go Agent has been installed in the G colon
* There is a pipeline named huge_artifact_uploader with a stage named uploader stage  and a job named uploader job
* Uploader job uploads an artifact of size 2GB
* There is a pipeline named small_artifact_uploader with a stage named small_uploader_stage and  a job name small_uploader_job
* small_artifact_uploader uploads an artifact of size 500 MB
* Server has been configured not to delete any artifacts

* only huge_artifact_uploader pipeline has been run continuously

* Upload of artifacts should be stopped when free disk space reaches 4 GB space ie twice the size of artifact getting uploaded
	
* only small_artifact_uploader pipeline has been run continuously

* Upload of artifacts should be stopped when free disk space reaches 1 GB

Scenario Two : Two pipelines tries to upload sequentially one after the other

* Same as Scenario one

* Both the pipelines are run in parallel

* Verify if the upload stops once when the disp space is less than twice the size of artifacts getting uploaded for pipeline having the smallest uploading artifact
* Pipeline that is trying to upload the artifact should fail populating appropriate message

Scenario Three Server alone exists in the drive

* There is a clean empty directory with 20 GB hard disk space name G colon
* Go Server has been installed in the G colon
* Artifacts folder exists in the G colon with in Go Server
* Go Agent has been installed in a different directory
* There is a pipeline named huge_artifact_uploader with a stage named uploader stage  and a job named uploader job
* Uploader job uploads an artifact of size 2GB
* There is a pipeline named small_artifact_uploader with a stage named small_uploader_stage and  a job name small_uploader_job
* small_artifact_uploader uploads an artifact of size 500 MB
* Server has been configured not to delete any artifacts

* Both the pipelines are triggered one after the other

* Upload  should stop once when the disk space is less than twice the size of artifact getting uploaded for currently executed pipeline and the pipeline fails

Scenario four Directory has been dedicated to artifacts directory alone

* There is a clean empty directory with 20 GB hard disk space name G colon
* Go Server has been installed in the C colon
* Artifacts folder exists in the G colon
* Go Agent has been installed in a different directory
* There is a pipeline named huge_artifact_uploader with a stage named uploader stage  and a job named uploader job
* Uploader job uploads an artifact of size 2GB
* There is a pipeline named small_artifact_uploader with a stage named small_uploader_stage and  a job name small_uploader_job
* small_artifact_uploader uploads an artifact of size 500 MB
* Server has been configured not to delete any artifacts

* Both pipelines are run alternatively

* Upload  should stop once when the disk space is less than twice the size of artifact getting uploaded for currently executed  pipeline and the pipeline fails

Scenario five Pipelines are uploading artifacts with auto delete turned on

* There is a clean empty directory with 20 GB hard disk space name G colon
* Go Server has been installed in the C colon
* Artifacts folder exists in the G colon
* Go Agent has been installed in a different directory
* There is a pipeline named huge_artifact_uploader with a stage named uploader stage  and a job named uploader job
* Uploader job uploads an artifact of size 2GB
* There is a pipeline named small_artifact_uploader with a stage named small_uploader_stage and  a job name small_uploader_job
* small_artifact_uploader uploads an artifact of size 500 MB
* Auto delete artifacts has been turned on
* Choose less than 4 GB as trigger point auto deletion
* Choose free untill disk space as 15 GB

* The pipelines are triggered alternatively

* Uploading should never stop
* Previously uploaded files should be deleted to recover the available disk space



