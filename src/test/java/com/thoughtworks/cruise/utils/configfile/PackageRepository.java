/*************************GO-LICENSE-START*********************************
 * Copyright 2015 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.cruise.utils.configfile;

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.util.StringUtil;

public class PackageRepository {

	public static String getPackageRepositoryLocation()
	{
		return System.getProperty("java.io.tmpdir") + "/packagerepos/";
	}
	
	public static String getYumRepoFilesLocation()
	{
		return RuntimePath.absolutePathFor("test-repos/yumrepo/rpmfiles/");
	}
	
	public static String getPackageRepositoryURI(String repoId) {
    	repoId = repoId.replace("$", "");
		return getPackageRepositoryLocation()  + repoId + StringUtil.shortUUID();
	}
}