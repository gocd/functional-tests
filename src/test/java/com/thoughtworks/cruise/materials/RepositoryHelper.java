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

package com.thoughtworks.cruise.materials;

import org.apache.commons.io.FileUtils;

import java.io.File;

import static com.thoughtworks.cruise.util.FileUtil.deleteFolder;

/**
 * @understands repo helping
 */
public class RepositoryHelper {

	public static File createFileIn(File folder, String fileName) {
		return writeToFile(folder, fileName, "content added to " + fileName);
	}
	
	public static File modifyFileIn(File folder, String fileName) {
	    return writeToFile(folder, fileName, "modifying file " + fileName);
	}

	public static File modifyFileInFolderWithText(File folder, String fileName, String text) {
		return writeToFile(folder, fileName, text);
	}

	private static File writeToFile(File folder, String fileName, String message) {
		File file = new File(folder, fileName);
		try {
			FileUtils.writeStringToFile(file, message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return file;
	}

	public static void cleanup(File repositoryFolder) {
        if (repositoryFolder.exists()) {
            deleteFolder(repositoryFolder);
        }
	}

}
