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

class OauthCallbackController < ApplicationController
  def index
    bank = Bank.find(params[:state])
    current_user.authorization_codes.find_all_by_bank_id(bank.id).map(&:delete)
    current_user.authorization_codes.create!(:bank => bank, :code => params[:code])
    render :text => "OK"
  end
end
