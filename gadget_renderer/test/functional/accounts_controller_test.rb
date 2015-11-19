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

require 'test_helper'

class AccountsControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:accounts)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create account" do
    assert_difference('Account.count') do
      post :create, :account => { }
    end

    assert_redirected_to account_path(assigns(:account))
  end

  test "should show account" do
    get :show, :id => accounts(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => accounts(:one).to_param
    assert_response :success
  end

  test "should update account" do
    put :update, :id => accounts(:one).to_param, :account => { }
    assert_redirected_to account_path(assigns(:account))
  end

  test "should destroy account" do
    assert_difference('Account.count', -1) do
      delete :destroy, :id => accounts(:one).to_param
    end

    assert_redirected_to user_accounts_path
  end
end
