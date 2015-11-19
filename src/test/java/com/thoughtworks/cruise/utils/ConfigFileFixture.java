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

package com.thoughtworks.cruise.utils;

import com.thoughtworks.cruise.util.CruiseConstants;
import com.thoughtworks.cruise.util.TestFileUtil;
import com.thoughtworks.cruise.utils.configfile.*;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import static com.thoughtworks.cruise.client.Stage.DEFAULT_STAGE;
import static com.thoughtworks.cruise.util.ExceptionUtils.bomb;
import static com.thoughtworks.cruise.utils.Pop3MailClient.*;
import static com.thoughtworks.cruise.utils.configfile.CruiseConfigUtil.InsertLocation.END;
import static com.thoughtworks.cruise.utils.configfile.CruiseConfigUtil.InsertLocation.START;
import static com.thoughtworks.cruise.utils.configfile.MaterialTags.*;
import static com.thoughtworks.cruise.utils.configfile.PipelineTags.*;

public class ConfigFileFixture {
    private CruiseConfigFileEditor contents = new CruiseConfigFileEditor();
    private CruiseConfigUtil cruiseConfigUtil = new CruiseConfigUtil(contents);
    public static final String PASSWORD = "badger";
    private CruiseConfigTag security;
    private static final String HEADER = "<cruise xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:noNamespaceSchemaLocation=\"cruise-config.xsd\" schemaVersion=\""
            + CruiseConstants.CONFIG_SCHEMA_VERSION + "\">\n";

    /*
    * {"allow_anonymous"=>false,
    * "product"=>"cruise",
    * "licensee"=>"Cruise UAT ThoughtWorks Beijing",
    * "type"=>"standard",
    * "max_light_users"=>nil,
    * "cruise_max_users"=>10,
    * "cruise_max_agents"=>50,
    * "cruise_edition"=>"Enterprise",
    * "expiration_date"=>2018-04-28,
    * "customer_number"=>"111eval",
    * "max_active_users"=>nil}
    */

    private static final String MISSING_AGENTS ="<agents>\n" +
    "<agent hostname=\"missing-agent\" ipaddress=\"10.232.3.1\" uuid=\"missing\">\n" +
    "<resources>\n" +
    "<resource>missing</resource>\n" +
    "</resources>\n" +
    "</agent>\n" +
    "<agent hostname=\"denied-agent\" ipaddress=\"10.232.3.2\" uuid=\"denied\" isDisabled=\"true\">\n" +
    "<resources>\n" +
    "<resource>denied</resource>\n" +
    "</resources>\n" +
    "</agent>\n" +
    "</agents>\n";
    
    
    public static final String EMPTY_CRUISE = HEADER + "<server></server>\n" + MISSING_AGENTS + "</cruise>";
    public static final String WITHOUT_LICENSE = HEADER + "<server artifactsdir=\"logs\"/>\n</cruise>";

    //{"allow_anonymous"=>false, 
    //"product"=>"cruise", 
    //"licensee"=>"Cruise team internal", 
    //"type"=>"standard", "max_light_users"=>nil,  
    //"cruise_max_users"=>20,  
    //"cruise_max_agents"=>100,  
    //"cruise_edition"=>"Enterprise",  
    //"expiration_date"=>2018-04-28,  
    //"customer_number"=>"111eval",  
    //"max_active_users"=>nil}
    private static final String ENTERPRISE_LICENSE =
            "E0TCr1FPWPOfh+yfApnYqUkqgNDbByvoWnaql4QvzgOj4Y8kfKCpM9GzOEQz"
                    + "WAR/8e0RmcziTKT2/IqKD8zUd1n4hFkUeHn9x9Lne1krwgBRLdqH44gkZa7s"
                    + "O87bQY/U+jpUF8J53z9blLtrpbvJTlnXq+CPatFV5YWKb6RMhZDN1JBjIg1h"
                    + "kFtCSEXGs+M2m51TC/dFKO3xAB3TpuSSltLbzfpjArdbnawglXKgEy5x72I5"
                    + "jnZuqHroZU9tL0uvp+t8l9mpsWQnFy1Fo4qL+wA7UETCRF+ugDJIPZBPx5eb"
                    + "wEnzXtpMspeFVWJfYXqzmV3uJg7XyUJmQajT/mdXDw==";
    private static final String ENTERPRISE_LICENSE_USER = "Cruise team internal";

    //{"licensee"=>"internal_test_professional",
    // "product"=>"cruise",
    // "cruise_max_users"=>5,
    // "cruise_max_agents"=>2,
    // "cruise_edition"=>"Professional",
    // "expiration_date"=>2010-06-30,
    // "customer_number"=>"1"}
    public static final String PROFESSIONAL_LICENSE = "dVz/uhkGaIlPguH1QWuxHflj42e5ODvDKVJ8WG1rRvQ1gtxlmONtIq7dUmJW\n"
            + "DbE0v1JFHnOOoLamSjKpYJdLbtBfeaDupxhB2Xah/S9jUDHNEasvVtMgscO0\n"
            + "4FvyCdH2WwUpZPSEu2RcHUvXUxNZWBSTggu0doudkp5idQTFh65LHEQLVJf/\n"
            + "c+mS7/EH0jI6IrLPYnvHxfuOScY1jBQyvZdU1F5bQpk2zYKUO9+JY4VU7rCI\n"
            + "BGx3tayO34Y+34xg4t3Ju52wfzxifz7UL/ayi+8qSiTlTM5k0lGsppcY2j3k\n"
            + "hIH75yrJf5nuEUd/MJZCQNqzhOV3J1VofwtBsQZ9QA==";
    private static final String PROFESSIONAL_LICENSE_USER = "internal_test_professional";

    // {"allow_anonymous"=>false,
    //"product"=>"cruise",   
    //"licensee"=>"Ricky",   
    //"type"=>"standard",   
    //"max_light_users"=>nil,   
    //"cruise_max_users"=>5,   
    //"cruise_max_agents"=>2,   
    //"cruise_edition"=>"Free",   
    //"expiration_date"=>2018-04-28,   
    //"customer_number"=>"111eval",   
    //"max_active_users"=>nil}
    public static final String FREE_LICENSE =
            "Wc54kj15kDDqvjutrIYuvaViVv9IGHYq4GtG5eadypNcPzoL45D2hA+D/VZj"
                    + "wqAYzO+LFtMx8xYmRA6KHBQF4gDWgz7fzNy7FNuLljWbWy5rw2liRdTIwYgL"
                    + "EWeAarXwe0N0z1BGoq3n7Em2PqV1jvVpEhndtj2fsZVwtJDQsmoPJlKONfGO"
                    + "aTtxBjQE0NF3D+wdbY6QEX3su775izK7WbH9Np/waonUNFYB43Kr5gr5Uv27"
                    + "nptvPeF5yZo3vhxAqRlD0rMXlWLfPDd8BBq53j4UKrgtiQkJ8USROGrrYe/F"
                    + "adqOTqvVZUPcpDj9hRcNqqcWW9dTPFXXSk3wgG8Qcw==";
    public static final String FREE_LICENSE_USER = "Ricky";

//    public static final String OLD_FREE_LICENSE =
//            "Wc54kj15kDDqvjutrIYuvaViVv9IGHYq4GtG5eadypNcPzoL45D2hA+D/VZj"
//                    + "wqAYzO+LFtMx8xYmRA6KHBQF4gDWgz7fzNy7FNuLljWbWy5rw2liRdTIwYgL"
//                    + "EWeAarXwe0N0z1BGoq3n7Em2PqV1jvVpEhndtj2fsZVwtJDQsmoPJlKONfGO"
//                    + "aTtxBjQE0NF3D+wdbY6QEX3su775izK7WbH9Np/waonUNFYB43Kr5gr5Uv27"
//                    + "nptvPeF5yZo3vhxAqRlD0rMXlWLfPDd8BBq53j4UKrgtiQkJ8USROGrrYe/F"
//                    + "adqOTqvVZUPcpDj9hRcNqqcWW9dTPFXXSk3wgG8Qcw==";
//    public static final String OLD_FREE_LICENSE_USER = "Ricky";
    
    public static final String OLD_FREE_LICENSE = "M83LmtvD27f1Bt6FUlEvTeuOCyjD3TseNmLmTzs653wGY0T00cmTo6HGBc3m" 
        + " ICsrLggtIclM/xUi08LtQKicIT9i4tDv3rObeXOaoEXk+nwY6eAN3pT+ORNE" 
        + "HUPG9NHLQ9iwooQAIo3enTMaarJoPExuFws07jReyGl0lCkK5NjaHpjvcZgZ"
        + "O7YMRS6D2z11AVB7JodtvKbjyZWbTPbbXMRdrlgHOkYumQUWa3D94k5oBvQK"
        + "7RYts714dNmWlsE1ANKGwK7+nUuCi70/CrRjvt8r3cNyoON89nJ2nx7mGS0F"
        + "yQOdSVX/kBc3XHWAYsL2D45D69JIIP54KgMysc0g5A==";    
    public static final String OLD_FREE_LICENSE_USER = "Cruise Test License with 36 Users";

    public static final String OLD_ENTERPRISE_LICENSE =
            "bkYQ8KNFON8sXu4z1izymW9CGa3lSkB1/8vSkTp5LNDHkD7unBEGBTx6psTe\n"
                    + "wlD21s6XOB3U4oOs0Z3o6nxbhq0QfCuSqpqC7f0ukjpILgPoTCoAFyWTv64K\n"
                    + "VT+iDEKlS2ayG3AXRgzcq/G8qu4y7ETj7WSvHuTM8ATr9IKICduVdVtXlx/q\n"
                    + "BhaNzNC3rtgDb5hRi1rFSZenkD5W7JUkCyJGilSbF1pDaSXbvmzeSPb7tHzd\n"
                    + "jMC2PJIhfjycI4BVp1VmmLop8admUNkat7w4HkqMbzk315NyOi/oyMwcMEIm\n"
                    + "MCujuw71EtWNEmhjgBCu3oMjfrmWfQ9os3XmEJiWDw==";
    public static final String OLD_ENTERPRISE_LICENSE_USER = "Cruise team internal";
    public static final String OLD_STANDARD_LICENSE =
            "JsHwZ0X3I114DbVO7UCrb+HlSaXJCxPgyL07372RydzA0lfdMCLO3IUA7Hlm\n"
                    + "9SCGI9IS62rmosU7geWDwfVYF4Kas408JCy0dkBGDYrTW+LjOuMizboEqZj0\n"
                    + "L/MdPRgz3tEHDGcG9UZwu9+JD7jZzohG8pmWnYCo6c+47h6SzLPxURdw2VK9\n"
                    + "ELSw8GM+CqomMGhopf28iehYOxH3Gip4P7+jm1lvkLztPmw5/5G0qx24QY+o\n"
                    + "f4X6ng2rC/tB3sS9s3YTKKKEqoYO95krea0tX3qtaP7E2ODZEV28QKz79rhE\n"
                    + "wF0VXkXgoaQ4z114ZPZIWyYEiwxapLwvB2r8Ks7u3Q==";
    public static final String OLD_STANDARD_LICENSE_USER = "Cruise team internal";        		

    public void useCleanConfigXml() {
        cruiseConfigUtil.resetCruiseConfig();
    }

    public void useNoLicenseConfigXml() {
        cruiseConfigUtil.withoutLicense();
    }

    public void changeP4MaterialUrlTo(String url) {
        cruiseConfigUtil.deleteFrom(materialsTag(), anyP4Tag());
        cruiseConfigUtil.insertAtEndOf(materialsTag(), p4Tag(url));
    }

    public void changeSVNMaterialUrlTo(String url) {
        cruiseConfigUtil.deleteFrom(materialsTag(), MaterialTags.anySvnTag());
        cruiseConfigUtil.insertAtEndOf(materialsTag(), MaterialTags.svnTag(url));
    }

    public void corrupt() {
        cruiseConfigUtil.corrupt();
    }

    private File writePasswordFile(Properties props) throws IOException {
        File tempPasswordFile = TestFileUtil.createTempFile("password.properties");
        tempPasswordFile.deleteOnExit();
        PrintStream printStream = new PrintStream(new FileOutputStream(tempPasswordFile));
        try {
            props.list(printStream);
        } finally {
            printStream.close();
        }
        return tempPasswordFile;
    }

    public void addStage(final String pipelineName, final String stageName) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.addStage(pipelineName, stageName);
            }
        });
    }

    public void addApprovalRole(String stageName, String approvalType, String roleName) {
        cruiseConfigUtil.ensureExistsIn(stageTag(stageName), approvalTag(approvalType), START);
        cruiseConfigUtil.ensureExistsIn(approvalTag(approvalType), authTag(), START);
        cruiseConfigUtil.insertAtEndOf(authTag(), PipelineTags.roleTag(roleName));
    }

    public void configAsManualApproval(String pipelineName, String stageName) {
        configApprovalType(pipelineName, stageName, "manual");
    }

    public void configAsAutoApproval(String pipelineName, String stageName) {
        configApprovalType(pipelineName, stageName, "success");
    }

    public void configApprovalType(String pipelineName, String stageName, String type) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.setApprovalTypeForStage(pipelineName, stageName, type);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void write(String newContent) {
        cruiseConfigUtil.write(newContent);
    }

    public static String onePipelineWithTwoStages(String pipelineName, String url) {
        return HEADER + "<server></server>\n"
                + "<pipelines>\n"
                + pipelineWithTwoStages(pipelineName, url)
                + "</pipelines>\n"
                + "</cruise>";
    }

    public static String twoPipelinesWithDependency(String firstPipelineName, String secondPipelineName, String url) {
        return twoPipelinesWithDependency(firstPipelineName, secondPipelineName, url, "success");
    }

    public static String twoPipelinesWithDependency(String firstPipelineName, String secondPipelineName, String url,
                                                    String approvalTypeForFirstPipeline) {
        return HEADER + "<server>"
                + "          <license user=\"" + ENTERPRISE_LICENSE_USER + "\">"
                + ENTERPRISE_LICENSE
                + "          </license>"
                + "</server>\n"
                + "<pipelines>\n"
                + "<pipeline name='" + firstPipelineName + "'>\n"
                + "    <materials>\n"
                + "      <svn url ='" + url + "'/>"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <approval type='" + approvalTypeForFirstPipeline + "'/>"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n"
                + "<pipeline name='" + secondPipelineName + "'>\n"
                + "    <materials>\n"
                + "      <svn url ='" + url + "'/>"
                + "      <pipeline pipelineName='" + firstPipelineName + "' stageName='" + DEFAULT_STAGE + "'/>"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n"
                + "</pipelines>\n"
                + "</cruise>";
    }

    public static String pipelineWithOnCancelTask(String pipelineName, String url) {
        return HEADER + "<server>"
                + "          <license user=\"" + ENTERPRISE_LICENSE_USER + "\">"
                + ENTERPRISE_LICENSE
                + "          </license>"
                + "</server>\n"
                + "<pipelines>\n"
                + "<pipeline name='" + pipelineName + "'>\n"
                + "    <materials>\n"
                + "      <svn url ='" + url + "'/>"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <approval type='manual' />"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml' target='longsleep'>\n"
                + "             <oncancel>\n"
                + "                 <ant buildfile='build.xml'/>\n"
                + "             </oncancel>\n"
                + "          </ant>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n"
                + "</pipelines>\n"
                + "</cruise>";
    }

    public static String twoPipelinesSecondPipelineOnlyWithDependencyMaterial(String firstPipelineName,
                                                                              String secondPipelineName, String url) {
        return HEADER + "<server></server>\n"
                + "<pipelines>\n"
                + pipeline(firstPipelineName, url)
                + "<pipeline name='" + secondPipelineName + "'>\n"
                + "    <materials>\n"
                + "      <pipeline pipelineName='" + firstPipelineName + "' stageName='" + DEFAULT_STAGE + "'/>"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n"
                + "</pipelines>\n"
                + "</cruise>";
    }

    public static String onePipelineWithSlowSecondStage(String pipelineName, String url) {
        return HEADER + "<server></server>\n"
                + "<pipelines>\n"
                + "<pipeline name='" + pipelineName + "'>\n"
                + "    <materials>\n"
                + "      <svn url ='" + url + "'/>"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "  <stage name='secondStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml' target='longsleep'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n"
                + "</pipelines>\n"
                + "</cruise>";
    }

    public static String pipelineWithTwoStages(String pipelineName, String url) {
        return "<pipeline name='" + pipelineName + "'>\n"
                + "    <materials>\n"
                + "      <svn url ='" + url + "'/>"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "  <stage name='secondStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n";
    }

    private static String pipelineWithTwoMaterials(String pipelineName, String theFirstUrl, String theFirstDest,
                                                   String theSecondUrl,
                                                   String theSecondDest) {
        return HEADER + "<server>"
                + "<license user=\"" + ENTERPRISE_LICENSE_USER + "\">"
                + ENTERPRISE_LICENSE
                + "</license>"
                + "</server>\n"
                + "<pipelines>\n"
                + "<pipeline name='" + pipelineName + "'>\n"
                + "    <materials>\n"
                + "      <svn url ='" + theFirstUrl + "' dest='" + theFirstDest + "'/>\n"
                + "      <svn url ='" + theSecondUrl + "' dest='" + theSecondDest + "'/>\n"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='end2end/build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n"
                + "</pipelines>\n"
                + "</cruise>";
    }


    private static String pipeline(String pipelineName, String url) {
        return "<pipeline name='" + pipelineName + "'>\n"
                + "    <materials>\n"
                + "      <svn url ='" + url + "'/>"
                + "    </materials>\n"
                + "  <stage name='defaultStage'>\n"
                + "    <jobs>\n"
                + "      <job name='defaultJob'>\n"
                + "        <tasks>\n"
                + "          <ant buildfile='build.xml'/>\n"
                + "        </tasks>\n"
                + "      </job>\n"
                + "    </jobs>\n"
                + "  </stage>\n"
                + "</pipeline>\n";
    }

    public void setResourceOnTheJob(String job, String resource) {
        cruiseConfigUtil.insertAtStartOf(jobTag(job), PipelineTags.resourceTag(resource));
    }

    public void setSleepTargetForFirstStage() {
        cruiseConfigUtil.deleteFrom(PipelineTags.tasksTag(), PipelineTags.antTagWithBuildFile("build.xml"));
        cruiseConfigUtil.insertAtEndOf(PipelineTags.tasksTag(), PipelineTags.antTag("longsleep"));
    }

    public void setBuildFileForFirstStage(String filePath) {
        cruiseConfigUtil.deleteFrom(PipelineTags.tasksTag(), PipelineTags.antTagWithBuildFile("build.xml"));

        cruiseConfigUtil.insertAtEndOf(PipelineTags.tasksTag(), PipelineTags.antTagWithBuildFile(filePath));
    }

    public void turnOnSvnCheckExternals(String url) {
        cruiseConfigUtil.deleteFrom(materialsTag(), MaterialTags.anySvnTag());
        cruiseConfigUtil.insertAtEndOf(materialsTag(), MaterialTags.svnTag(url, true));
    }

    public int numbersOfPipelin() {
        return this.cruiseConfigUtil.numbersOfPipelin();
    }

    public void addJobProperty(String jobName, String propertyName, String src,
                               String xpath) {
        CruiseConfigTag tag = propertiesTag();
        cruiseConfigUtil.ensureExistsIn(jobTag(jobName), tag, END);
        cruiseConfigUtil.insertAtEndOf(tag, propertyTag(propertyName, src, xpath));
    }

    public void addTrackingToolTo(String pipelineName, String link, String regex) {
        cruiseConfigUtil.insertAtStartOf(pipelineTag(pipelineName), trackingTool(link, regex));
    }

    public void createPipeline(final String pipelineName, final String materialType, final String url) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.createPipeline(pipelineName, materialType, url, materialType);
            }
        });
    }

    public void createPipeline(final String pipelineName, final String materialType, final String materialName,
                               final String url) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.createPipeline(pipelineName, materialType, materialName, url, materialName);
            }
        });
    }


    public void createManualPipelineWithSvnMaterial(String pipelineName, String url, String materialDest)
            throws Exception {
        CruiseConfigDom dom = cruiseConfigDom();
        dom.createPipeline(pipelineName, "svn", url, materialDest);
        dom.setTaskForFirstStage(pipelineName, (SimpleCruiseConfigTag) PipelineTags.antTag(""));
        updateCruiseConfig(dom);
    }

    public void createPipelineWithTwoSvnMaterials(String pipelineName, String theFirstUrl, String theFirstDest,
                                                  String theSecondUrl, String theSecondDest)
            throws Exception {
        CruiseConfigDom dom = cruiseConfigDom();
        dom.createPipeline(pipelineName, "svn", theFirstUrl, theFirstDest);
        dom.addMaterial(pipelineName, "svn", theSecondUrl, theSecondDest);
        updateCruiseConfig(dom);
    }

    public void addNewSvnMaterial(String pipelineName, String oldRepoUrl, String newRepoUrl, String theFirstDest,
                                  String theSecondDest) {
        CruiseConfigTag materialsTag = materialsTag();
        cruiseConfigUtil.deleteFrom(materialsTag, MaterialTags.svnTag(oldRepoUrl));
        cruiseConfigUtil.insertAtEndOf(materialsTag, MaterialTags.svnTag(oldRepoUrl, theFirstDest));
        cruiseConfigUtil.insertAtEndOf(materialsTag, MaterialTags.svnTag(newRepoUrl, theSecondDest));
    }


    public void changeSvnDestTo(final String pipelineName, final String url, final String newDest) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.removeMaterialByURL(pipelineName, "svn", url);
                dom.addMaterial(pipelineName, "svn", url, newDest);
            }
        });
    }

    public void removeSvnMaterial(String url, String dest) {
        CruiseConfigTag materialsTag = materialsTag();
        cruiseConfigUtil.deleteFrom(materialsTag, MaterialTags.svnTag(url, dest));
    }

    public void addFilterToSvnMaterial(String filter) {
        cruiseConfigUtil.insertAtEndOf(MaterialTags.anySvnTag(), MaterialTags.filterTag(filter));
    }

    public void addFilterToHgMaterial(String filter) {
        cruiseConfigUtil.insertAtEndOf(MaterialTags.anyHgTag(), MaterialTags.filterTag(filter));
    }

    public void addFilterToGitMaterial(String filter) {
        cruiseConfigUtil.insertAtEndOf(MaterialTags.anyGitTag(), MaterialTags.filterTag(filter));
    }

    public void fetchArtifact(String pipelineName,
                              String stageName, String job, String pipelineFetchFrom, String stageFetchFrom,
                              String artifact,
                              String dest) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.appendFetchArtifacts(pipelineName, stageName, job, pipelineFetchFrom, stageFetchFrom, artifact, dest);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void fetchDirectory(String pipelineName, String firstStage,
                               String secondStage, String job, String artifact, String dest) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.appendFetchDirectory(pipelineName, firstStage, secondStage, job, artifact, dest);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void createTwoPipelineGroups(String pipelineName1, String pipelineName2, String svnRepositoryUrl) {
        createGroupWithPipeline("firstGroup", pipelineName1, svnRepositoryUrl);
        createGroupWithPipeline("secondGroup", pipelineName2, svnRepositoryUrl);
    }

    public void createGroupWithPipeline(String groupName, String pipelineName1, String svnRepositoryUrl) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.addPipelineGroupWithSvnMaterialAndOneStage(groupName, pipelineName1, svnRepositoryUrl);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void setUpDefaultSMTP() {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.setUpSMTP(SMTP_HOSTNAME,
                    SMTP_PORT,
                    SMTP_USERNAME, SMTP_PASSWORD, SMTP_TLS, SMTP_FROM, SMTP_ADMIN);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void useEnterpriseLicense() {
        useLicense(ENTERPRISE_LICENSE, ENTERPRISE_LICENSE_USER);
    }

    public void useProfessionalLicense() {
        useLicense(PROFESSIONAL_LICENSE, PROFESSIONAL_LICENSE_USER);
    }

    public void useFreeLicense() {
        useLicense(FREE_LICENSE, FREE_LICENSE_USER);
    }

    private void useLicense(String license, String licenseUser) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.updateLicense(license, licenseUser);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    private void updateCruiseConfig(CruiseConfigDom dom) {
        contents.write(dom.asXml());
    }

    private CruiseConfigDom cruiseConfigDom() throws DocumentException, SAXException, URISyntaxException {
        return new CruiseConfigDom(contents.read());
    }

    public void removePipelineGroup(String groupName) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.removePipelineGroup(groupName);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }

    }

    public List<String> pipelineGroups() {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            return dom.pipelineGroups();
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public List<String> pipelinesFromGroup(String pipelineGroupName) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            return dom.pipelinesFromGroup(pipelineGroupName);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public List<String> stagesFromPipeline(String pipelineName) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            return dom.stagesFromPipeline(pipelineName);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void addUserViewAuthorizationForGroup(String user, String groupName) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addUserViewAuthorizationForGroup(user, groupName);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void addRoleViewAuthorizationForGroup(String role, String groupName) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addRoleViewAuthorizationForGroup(role, groupName);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void createOnePipelineGroup(String pipelineName, String url) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.addPipelineGroupWithSvnMaterial("firstGroup", pipelineName, url);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void removeViewPermissionConfigurationFrom(String groupName) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.removeViewPermissionDefinition("firstGroup");
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void updateCruiseConfig(CruiseConfigUpdater updater) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            updater.update(dom);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void addPipelineToPipelineGroup(String pipeline, String pipelineGroup, String url) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.addPipelineGroupWithSvnMaterial(pipelineGroup, pipeline, url);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void removePipelineFromPipelineGroup(String pipeline) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.removePipeline(pipeline);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void moveExistingPipelineToPipelineGroup(String pipeline, String pipelineGroup) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.moveExistingPipelineToPipelineGroup(pipeline, pipelineGroup);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void addUserOperateAuthorizationForGroup(String user, String groupName) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addUserOperateAuthorizationForGroup(user, groupName);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void removeOperatePermissionConfigurationFrom(String groupName) {
        CruiseConfigDom dom;
        try {
            dom = cruiseConfigDom();
            dom.removeOperatePermissionDefinition(groupName);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            bomb(e);
        }
    }

    public void addJobToStageOfPipeline(String job, String stage, String pipeline) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addJobToStageOfPipeline(job, stage, pipeline);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void addArtifactForFirstJobInFirstPipeline(String src, String dest) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addArtifactForFirstJobInFirstPipeline(src, dest);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void addApprovalUserForStage(String pipelineName, String stageName, String username) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addApprovalUserForStage(pipelineName, stageName, username);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void addAdmins(String... users) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addAdmins(users);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void removeUserFromRole(String role, String user) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.removeUserFromRole(role, user);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void addRoles(String[] roles) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addRoles(roles);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void createRoleWithUser(String roleName, String... users) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addUsersToRole(roleName, users);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void addRolesToAdmin(String[] strings) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.addRolesToAdmin(strings);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void changeHgUrl(String newURL) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            dom.changeHgUrl(newURL);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void ignore(final String pipelineName, final String scm, final String pattern) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.ignore(pipelineName, scm, pattern);
            }
        });
    }

    public void manualTrigger(final String pipeline) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.changeToManualTrigger(pipeline);
            }
        });
    }

    public void removeMaterialByURL(final String pipeline, final String type, final String url) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.removeMaterialByURL(pipeline, type, url);
            }
        });
    }

    private void configure(Action action) {
        try {
            CruiseConfigDom dom = cruiseConfigDom();
            action.doAction(dom);
            updateCruiseConfig(dom);
        } catch (Exception e) {
            throw bomb(e);
        }
    }

    public void setLabelTemplate(final String pipelineName, final String labelTemplate) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.setLabelTemplate(pipelineName, labelTemplate);
            }
        });
    }

    private interface Action {
        void doAction(CruiseConfigDom dom);
    }

    public void setDependencyMaterialNameForPipeline(final String pipelineName, final String materialName) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.setDependencyMaterialNameForPipeline(pipelineName, materialName);
            }
        });
    }

    public void configPipelineDependsOn(final String downstreamPipeline, final String upstreamPipeline, final String upstreamStage) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.addDependencyMaterial(downstreamPipeline, upstreamPipeline, upstreamStage);
            }
        });
    }

    public void addTimerFor(final String pipelineName, final String timerSpec) {
        configure(new Action() {
            public void doAction(CruiseConfigDom dom) {
                dom.addTimerFor(pipelineName, timerSpec);
            }
        });
    }

    public void addAgentToEnvironment(final String environmentName, final String agentUuid) {
        configure(new Action() {
            @Override
            public void doAction(CruiseConfigDom dom) {
                dom.addAgentUnderEnvironment(environmentName, agentUuid);
            }
        });
    }

    public void removeAgentFromEnvironment(final String envronmentName, final String uuid) {
        configure(new Action() {
            @Override
            public void doAction(CruiseConfigDom dom) {
                dom.removeAgentFromEnvironment(envronmentName, uuid);
            }
        }
    );
    }

}