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

# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#   
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Major.create(:name => 'Daley', :city => cities.first)

bank = Bank.create!(:name => "ACME Bank", :url => 'https://localhost:5000', :client_id => 'dd07b68e5400d428350cc585af6df38069056de96daf953091b4a65295fdf727', :client_secret => '9e07285bbcf48f892e4570f546f9fade0adf8c750fe313ac9b40d084f09f2fa9')
bank = Bank.create!(:name => "Programmers Credit Union", :url => 'https://localhost:6000', :client_id => 'zzz', :client_secret => 'zzz')
bank = Bank.create!(:name => "Money Club", :url => 'https://localhost:7000', :client_id => 'yyy', :client_secret => 'yyy')
