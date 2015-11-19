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

class Bank < ActiveRecord::Base
  
  REDIRECT_URI = 'http://dreamcast.local:3000/oauth_callback'
  
  def authorize_url(state)
    url + "/oauth/authorize?response_type=code&client_id=#{client_id}&redirect_uri=#{REDIRECT_URI}&state=#{state}"
  end
  
  def token_url
    url + "/oauth/token"
  end
  
  def account_url(account_number)
    url + "/accounts/#{account_number}.xml"
  end
  
  def token_post_body(code)
    {:client_id => client_id, :client_secret => client_secret, :code => code, :grant_type => 'authorization-code', :redirect_uri => REDIRECT_URI}
  end
end
