Agent UI Auto Refresh
=====================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-slow" - setup
* With "1" live agents in directory "AgentsUIWithAutoRefresh" - setup
* Capture go state "Agent uI auto refresh" - setup

Agent UI Auto Refresh
---------------------

tags: 2705, agents, UI, automate, 3268, 3304, 3422, 3313, 3450, 3434, 3483, stage1

We don't want the page to reopen everytime we go to a new workflow like we have with other "On xxx Page". So, we check if we are already on the agents page
and no open if we are. This way, we can ensure that auto refresh works fine.





* Turn on autoRefresh
* Sort column "Sandbox"
Removing this step as its not relevent for this scenario: Select agent "2" - On Agents Page
* Verify "ENABLED" agent count is "2"
* Enabling a "Disabled" agent should return "200"
* For pipeline "basic-pipeline-slow" - Using pipeline api
* Schedule should return code "202"
* Verify "ENABLED" agent count is "3"
* Wait for agent to show status "building"





Teardown of contexts
* Capture go state "Agent uI auto refresh" - teardown
* With "1" live agents in directory "AgentsUIWithAutoRefresh" - teardown
* Using pipeline "basic-pipeline-slow" - teardown
* Basic configuration - teardown


