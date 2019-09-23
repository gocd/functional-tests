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
import com.thoughtworks.cruise.util.CruiseConstants;
import com.thoughtworks.cruise.util.FileUtil;
import com.thoughtworks.cruise.util.TestFileUtil;
import com.thoughtworks.cruise.util.command.ConsoleResult;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.thoughtworks.cruise.util.command.CommandLine.createCommandLine;

public class HgRepository extends AbstractRepository {
    private String bundle;
    protected File repositoryFolder;
    private List<String> commits = new ArrayList<String>();
    private String branch;

    public HgRepository(Element material, String bundlePath) {
        super(material);
        this.bundle = bundlePath;
        this.branch = getBranch();
        cloneTestRepo();
    }

    public void cleanup() {
        RepositoryHelper.cleanup(repositoryFolder);
    }

    public void createNewFile(String fileName, String userName) {
        hgadd(RepositoryHelper.createFileIn(repositoryFolder, fileName));
    }

    public void addFile(File file) {
        FileUtil.copy(file, new File(repositoryFolder, file.getName()));
    }

    public void addFile(File file, String destFolder) {
        File dest = new File(repositoryFolder, destFolder);
        dest.mkdirs();
        FileUtil.copy(file, new File(dest, file.getName()));
    }

    public void checkin(String comment) {
        checkin("twist", comment);
    }

    public void checkin(String username, String comment) {
        createCommandLine("hg").withArgs("ci", "-A", "-u", username, "-m", comment).withWorkingDir(repositoryFolder).runOrBomb();
        commits.add(head());
    }

    public String getUrl() {
        String url = FileUtil.toFileURI(repositoryFolder);
        if (StringUtils.isEmpty(branch)) return url;
        return StringUtils.chomp(url, "/") + "##" + branch;
    }

    private String getBranch() {
        String materialUrlAttribute = getMaterialAttribute("url");
        String branch = materialUrlAttribute.contains("##") ? materialUrlAttribute.split("##")[1] : "";
        return branch;
    }

    private void cloneTestRepo() {
        repositoryFolder = TestFileUtil.prepareFolderInsideTempFolder("testHgRepo");
        File tmpFolder = new File(System.getProperty("java.io.tmpdir"), "cruise");
        String url = new File(bundle).getAbsolutePath();
        if (!StringUtils.isEmpty(branch))
            url = url + "#" + branch;
        createCommandLine("hg").withWorkingDir(tmpFolder).withArgs("clone", url, repositoryFolder.getPath()).runOrBomb();
    }

    private void hgadd(File file) {
        createCommandLine("hg").withArgs("add", file.getAbsolutePath()).withWorkingDir(repositoryFolder).runOrBomb();
    }

    @Override
    public String getRevisionFromComment(String checkinComment) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Revision latestRevision() {
        String branchname = branch;
        if (StringUtils.isEmpty(branch))
            branchname = "default";
        ConsoleResult result = createCommandLine("hg").withArgs("log", "-b", branchname, "-l1", "--template={node}\\n{author}\\n{desc}\\n----\\n{file_mods}\\n----\\n{file_adds}\\n----\\n{file_dels}\\n----").withWorkingDir(repositoryFolder).runOrBomb();
        return makeRevision(result.outputAsString());
    }

    @Override
    public String head() {
        ConsoleResult result = createCommandLine("hg").withArgs("tip", "--template", "{node}").withWorkingDir(repositoryFolder).runOrBomb();
        return result.outputAsString();
    }

    @Override
    public List<String> recentCommits() {
        return commits;
    }

    public static void main(String[] args) {
        new HgRepository(null, null).makeRevision("123456\nthi is a comment\n----\nmodified/file1 ");
    }

    private Revision makeRevision(String output) {
        String[] parts = output.split("----");
        String[] checkinInfo = parts[0].split("\n");
        return new Revision(checkinInfo[0], checkinInfo[1], checkinInfo[2], makeList(parts[1]), makeList(parts[2]), makeList(parts[3]));
    }

    private List<String> makeList(String string) {
        return Arrays.asList(string.trim().split("\n"));
    }

    @Override
    public void modifyFile(String fileName, String userName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteFile(String fileName, String userName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String latestRevisionString() {
        return latestRevision().revisionNumber();
    }

    @Override
    public void initatePostCommitHook(TalkToCruise talkToCruise) {
        StringRequestEntity requestEntity;
        requestEntity = null;
        try {
            requestEntity = new StringRequestEntity(
                    "{\"repository_url\": \""+ getUrl() + "\"}",
                    "application/json",
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = Urls.hgPostCommitHook();
        CruiseResponse response = talkToCruise.post(url, requestEntity, "CONFIRM", CruiseConstants.apiV1);
        Assert.assertThat(response.getStatus(), Is.is(HttpStatus.SC_ACCEPTED));
    }
}
