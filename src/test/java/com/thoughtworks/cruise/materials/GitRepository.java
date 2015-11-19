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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.util.TestFileUtil;
import com.thoughtworks.cruise.util.command.ConsoleResult;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Element;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.io.File;

import static com.thoughtworks.cruise.util.command.CommandLine.createCommandLine;

public class GitRepository extends AbstractRepository {
	private static final String DEFAULT_USER = "twist <twist>";
	private String bundle;
    private File repositoryFolder;

	public GitRepository(Element material, String bundlePath) {
		this(material, bundlePath, TestFileUtil.prepareFolderInsideTempFolder("testGitRepo"));
	}

	public GitRepository(Element material, String bundlePath, File repoFolder) {
		super(material);
		this.repositoryFolder = repoFolder;
		this.bundle = bundlePath;		
		cloneRepo();
	}

	public void cleanup() {
		RepositoryHelper.cleanup(repositoryFolder);
	}

	public void createNewFile(String fileName, String userName) {
		gitadd(RepositoryHelper.createFileIn(repositoryFolder, fileName));
	}

	public void checkin(String comment) {
        checkin(DEFAULT_USER, comment);
	}

	public void checkin(String username, String comment) {
		git("commit", "--author", "'" + username + "'", "-m", comment);
	}

	public String getUrl() {
		return repositoryFolder.getAbsolutePath();
	}

    private void cloneRepo() {
        File tmpFolder = new File(System.getProperty("java.io.tmpdir"), "cruise");
        gitIn(tmpFolder, "clone", new File(bundle).getAbsolutePath(), repositoryFolder.getPath());
    }

    private void gitadd(File file) {
        git("add", file.getAbsolutePath());
    }

	public void initSubmodule(GitRepository referencedRepo) {
		git("submodule", "add", referencedRepo.getUrl(),new File(referencedRepo.getUrl()).getName());
		checkin("inited_submodule");
	}
	
	@Override
	public String getRevisionFromComment(String checkinComment) {        
		String grep = "--grep=" + checkinComment;
		return git("log", grep, "--pretty=oneline").outputAsString().split(" ")[0];
	}

	public void updateAndCommitSubmoduleReference(GitRepository referencedRepo) {
		File submoduleFolder = new File(repositoryFolder, new File(referencedRepo.getUrl()).getName());
		git("submodule", "update", "--init");
		gitIn(submoduleFolder, "checkout", "master");
		gitIn(submoduleFolder, "pull");
		git("add", submoduleFolder.getName());
		checkin("Updated submodule " + submoduleFolder.getName());
	}
	
	private ConsoleResult git(String... args) {
		return gitIn(repositoryFolder, args);
	}

	private ConsoleResult gitIn(File location, String... args) {
		return createCommandLine("git").withWorkingDir(location).withArgs(args).runOrBomb();
	}

	@Override
	public Revision latestRevision() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void modifyFile(String fileName, String userName) {
		gitadd(RepositoryHelper.modifyFileIn(repositoryFolder, fileName));
	}
	
	@Override
	public void deleteFile(String fileName, String userName) {
		git("rm", new File(repositoryFolder, fileName).getAbsolutePath());
	}

	@Override
	public String latestRevisionString() {
		return git("rev-list", "HEAD", "--max-count=1").outputAsString();
	}

	  @Override
	    public void initatePostCommitHook(TalkToCruise talkToCruise) {
	        CruiseResponse response = talkToCruise.post(Urls.gitPostCommitHook(), new NameValuePair("repository_url", getUrl()));
	        Assert.assertThat(response.getStatus(), Is.is(HttpStatus.SC_ACCEPTED));
	    }
	
}