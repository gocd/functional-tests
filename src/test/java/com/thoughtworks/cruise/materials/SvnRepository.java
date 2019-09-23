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
import com.thoughtworks.cruise.util.*;
import com.thoughtworks.cruise.util.command.ConsoleResult;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;

import static com.thoughtworks.cruise.util.command.CommandLine.createCommandLine;

public class SvnRepository extends AbstractRepository {
	private static final String DEFAULT_USER = "twist-test";
	private String bundle;
    private File repositoryFolder;
    private File workingCopy;
    private String repoUUID;
    
    static class SvnInfo {
        private static final String ENCODING = "UTF-8";

        private String path = "";
        private String encodedUrl = "";
        
        public void parse(String xml) throws DocumentException, SAXException, URISyntaxException {
            Document document = XmlUtil.parse(xml);
            parseDOMTree(document);
    	}

        public String getPath() {
            return path;
        }

        public String getUrl() {
            return encodedUrl;
        }

        private void parseDOMTree(Document document) {
            Element infoElement = document.getRootElement();
            Element entryElement = (Element) infoElement.selectSingleNode("//entry");
            String encodedUrl = entryElement.selectSingleNode("url").getText().trim();

            Element repositoryElement = (Element) entryElement.selectSingleNode("//repository");
            String root = repositoryElement.selectSingleNode("//root").getText().trim();
            String encodedPath = StringUtils.replace(encodedUrl, root, "");

            try {
                this.path = URLDecoder.decode(encodedPath, ENCODING);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            this.encodedUrl = encodedUrl;
        }
    }

	public SvnRepository(Element material, String bundlePath) {
		super(material);
		this.bundle = bundlePath;
		prepare();
	}

	public void cleanup() {
		RepositoryHelper.cleanup(repositoryFolder);
	}

	public void createNewFile(String fileName, String userName) {
		svnAdd(RepositoryHelper.createFileIn(workingCopy, fileName));
	}

	public void checkin(String comment) {
		checkin(DEFAULT_USER, comment);
	}

	public void checkin(String username, String comment) {
		createCommandLine("svn").withArgs("ci", "--non-interactive", "--username", username, "-m", comment).withWorkingDir(workingCopy).runOrBomb();
	}

	public String getUrl() {
		return repositoryUri();
	}
	
	public String getUUIDOfRepo() {
	     return repoUUID;
	}
	
    private void prepare() {
        repositoryFolder = TestFileUtil.prepareFolderInsideTempFolder("testsvnRepo");
        workingCopy = TestFileUtil.prepareFolderInsideTempFolder("svnRepoWorkingCopy");
        File bundleFile = new File(bundle);
        if (!bundleFile.exists()) {
        	throw new RuntimeException("Bundle file [" + bundle + "] does not exist. Path was [" + bundleFile.getAbsolutePath() + "]");
        }
        try {
        	FileUtils.copyDirectory(bundleFile, repositoryFolder);
        	checkout(workingCopy);
        	ConsoleResult result = createCommandLine("svnlook").withArgs("uuid", repositoryFolder.toString()).runOrBomb();
            repoUUID = result.outputAsString();       	
		} catch (Exception e) {
			ExceptionUtils.bomb(e);			
		}
    }

    private void checkout(File workingCopy) throws Exception {
        createCommandLine("svn").withArgs("checkout",  repositoryUri(), workingCopy.getAbsolutePath()).withWorkingDir(repositoryFolder).runOrBomb();
    }

	private String repositoryUri() {
		return FileUtil.toFileURI(repositoryFolder);
	}

    private void svnAdd(File file) {
        createCommandLine("svn").withArgs("add", file.getAbsolutePath()).withWorkingDir(workingCopy).runOrBomb();
    }

	@Override
	public String getRevisionFromComment(String checkinComment) {
		throw new UnsupportedOperationException("TODO");
	}

	public void addExternal(SvnRepository externalRepo) {
		createCommandLine("svn").withWorkingDir(workingCopy).withArgs("propset", "svn:externals", "external " + externalRepo.getUrl() , ".").runOrBomb();
        checkin("added external");		
	}
	
	@Override
	public Revision latestRevision() {
	    createCommandLine("svn").withWorkingDir(workingCopy).withArgs("up").runOrBomb();
		ConsoleResult result = createCommandLine("svn").withWorkingDir(workingCopy).withArgs("log", "--non-interactive", "--xml", "-v", "--limit", "1").runOrBomb();
        String output = result.outputAsString();
        try {
            return parseSvnLog(output).get(0);
        } catch (Exception e) {
            System.err.println("Error parsing svn log output");
            throw ExceptionUtils.bomb(e);
        } 
	}
	
    private List<Revision> parseSvnLog(String output) {
        return new SvnLogXmlParser().parse(output, remoteInfo().getPath());
    }

	
	private SvnInfo remoteInfo() {
        SvnInfo svnInfo = new SvnInfo();
        try {
			svnInfo.parse(createCommandLine("svn").withArgs("info", "--xml", "--non-interactive").withArg(repositoryUri()).runOrBomb().outputAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return svnInfo;
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
        String url = Urls.svnPostCommitHook();
        CruiseResponse response = talkToCruise.post(url, requestEntity, "CONFIRM", CruiseConstants.apiV1);
        Assert.assertThat(response.getStatus(), Is.is(HttpStatus.SC_ACCEPTED));
    }
}
