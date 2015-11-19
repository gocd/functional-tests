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

NewCannotAutomateNowSceanrios
=============================

Setup of contexts 
* CompletedSvnPipeline - setup
* Capture go state "NewCannotAutomateNowSceanrios" - setup

NewCannotAutomateNowSceanrios
-----------------------------

tags: agent management, svn support, scheduling, agent auto-discover




 to setup Cruise server and agent

   1. Download installer of server and agent in terms of platform.
   2. Install server
   3. Install agent (Notes: one server and one agent can run on the same box.)
   4. Specify the ipaddress of server which the agent can access to on agent side.
   5. Check that server and agent are launched successfully
   6. I can see the license page in the browser.

 to add multiple agents

   1. Download agent installer
   2. Install agent on other laptop (Multiple agents #can not# run on same box.)
   3. Specify the ip address of server which the agent can access to.
   4. Find it in agent tab with gray bar
   5. Approve the agent
   6. See it with green bar.
   7. if agents is more than the license allowed
   8. See the warning message

 to uninstall agents

   1. uninstall it with the conventions of the platform
   2. Can not see the process running on background
   3. Make sure you can reinstall it on the same box.

 to uninstall the server

   1. uninstall it with the conventions of the platform
   2. Can not see the process running on background
   3. there are nothing relative to cruise except user’s artifacts, config file.
   4. Make sure you can reinstall it on the same box.

 to upgrade the server

   1. Make sure the old cruise server is running with some pipeline history,
   2. download high version server,
   3. Pause all the pipeline
   4. Waiting for all the builds done or cancel them all,
   5. install the high version with the conventions of the platform
   6. Make sure all of histories is same as before.
   7. Make sure that agents upgrade to the same version automatically.
   8. Make sure that the server and agents work as normal.
   9. Make sure that the config file does not change.
  10. Make sure that the label of pipeline will increase correctly.

  Run server on Solaris

   1. download installer of server for solaris platfrom
   2. install server by command “pkgadd -d [package name]”
   3. check that cruise server can be started successfully (by command “svcadm enable -s cruise/server”)
   4. check that cruise server can be stopped successfully (by command “svcadm disable -s cruise/server”)
   5. check that cruise server status can be checked (by command “svcs cruise/server”)
   6. check that web page http://serverIP:8153 can be opened successfully

 Run agent on Solaris

   1. download installer of agent for solaris platfrom
   2. install agent by command “pkgadd -d [package name]”
   3. check that cruise agent can regeister to a server by “svccfg -s svc:/application/cruise/agent:default setprop cruise-agent/server = < Server hostname or IP > && svcadm refresh svc:/application/cruise/agent && svcadm restart svc:/application/cruise/agent”
   4. check that cruise agent can be started successfully (by command “svcadm enable -s cruise/agent”)
   5. check that cruise agent can be stopped successfully (by command “svcadm disable -s cruise/agent”)
   6. check that cruise agent status can be checked (by command “svcs cruise/agent”)

to not poll repository for manual pipelines

   1. setup a pipeline and define manual gate for the first stage of this pipeline
   2. check in code to this repo
   3. after 5 minutes (the period is longer than that cruise detect modification period) checkin code again
   4. manually approve this first stage to trigger the pipeline
   5. make sure that only one build cause, including both checkins

to restructure artifact repository

   1. install cruise 1.2 first, define some artifacts for a job then trigger build
   2. after build finish then upgrade cruise to 1.3
   3. check that cruise can show artifacts of new job successfully in UI
   4. check that cruise can show artifacts of old job(which build in 1.2) successfully in UI
   5. check that in filesystem since 1.3.2, the new artifacts structure would be /var/lib/cruise-server/[repository folder name]/pipelines/[pilelinename]/[counter]/[Stagename]/[stagecounter]/[jobname]

to show warning message for low disk space of artifacts directory on server side

   1. check that when disk space of artifacts directory is less than 1 Gb, proper warning message will show, while pipeline still can be triggered
   2. check that admin (whose email address defined in smtp server part) can get waring email, user can get info like on which cruise server which disk space is lower than 1 Gb
   3. check that when disk space of artifacts directory is less than 100 Mb, proper warning message will show and pipeline can not be scheduled
   4. check that admin (whose email address defined in smtp server part) can get waring email, user can get info like on which cruise server which disk space is lower than 100 Mb

after applied free license allowed remote agents number will be 0

   1. apply new free license
   2. register multi local agents to this server
   3. check that all of these local agents can be assigned job and works well
   4. register some remote agents to this server
   5. check that those remote agents can not be assiged job, and proper error message shows to remind user
   6. if detect old license (old free, standard or enterprise), it will be shown as free edition, 5 users.

to install cruise server by cruise.jar

   1. start cruise server by cruise.jar
   2. check that cruise server is started successfully and works well.


to not block other pipeline’s scheduling once some pipeline shcheduling is blocked

   1. multi pipelines are setup (e.g 10 pipelines)
   2. many Devs check in to those repo almost at the same time
   3. make sure that all pipeline are scheduled on time
   4. unfortunately one pipeline scheduling take long time
   5. user still check in to that repo
   6. make sure that other pipeline still can be scheduled on time

to show warning message for low disk space of DB on server side.

   1. Cruise Server is installed and started
   2. build engineer confingure smtp and email info in Admin page.
   3. free disk space of db is less than 1024M (or customized one, to customized it just add -Ddb.warning.size.limit=1024M to server.sh then restart Cruise Server)
   4. make sure that in UI, proper warning message shows to remind user that db’s disk space is not enough.
   5. at the same time make sure that in admin’s mailbox, one mail (and only get one mail) with similar info is recevied to tell admin on which Cruise Server box db’s free disk space is lower than 1024M (or customized one)
   6. no action to release db’s disk space for Cruise Server, then free disk space of db is less than 100M (or customized one, to customized it just add -Ddb.full.size.limit=100M to server.sh then restart Cruise Server)
   7. make sure that in UI, proper error message shows to remind user that db’s disk space is almost full, and Cruise Server stops scheduling to protect db.
   8. at the same time make sure that in admin’s mailbox, one mail (and only get one mail) with similar info is recevied to tell admin on which Cruise Server box db’s free disk space is almost full – lower than 100M (or customized one), and Cruise Server will stop scheduling to protect db.
   9. build engineer release disk space of db to make it larger than 1024M (or customized one).
  10. make sure that warning/error message (on db’s disk space not enough) disappears from UI.







Teardown of contexts 
* Capture go state "NewCannotAutomateNowSceanrios" - teardown
* CompletedSvnPipeline - teardown


