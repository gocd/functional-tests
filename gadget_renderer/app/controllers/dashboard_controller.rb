##########################GO-LICENSE-START################################
# Copyright 2015 ThoughtWorks, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
##########################GO-LICENSE-END##################################

class DashboardController < ApplicationController
  
  def index
  end
    
  def add_account_gadget
    render :update do |page|
      page.insert_html(:bottom, 'dashboard', <<-HTML)
      <div style="float: left; margin: 1em">
        <h3>Pipelines Status</h3>
          <iframe src="/gadgets/ifr?bpc=1&nocache=1&url=https://localhost:8254/go/gadgets/pipeline.xml&up_pipelineName=#{params[:up_pipelineName]}" width="278" height="232">
          </iframe>
      </div>
      HTML
    end 
  end
end