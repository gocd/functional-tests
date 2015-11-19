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

import java.util.LinkedHashMap;

public class MaterialTags {

    public static CruiseConfigTag materialsTag() {
        return new SimpleCruiseConfigTag("materials");
    }

    public static CruiseConfigTag anySvnTag() {
        return new CruiseConfigTag() {
            protected String startTag() {
                return "<svn ";
            }

            protected String endTag() {
                return "</svn>";
            }

            protected String asString() {
                throw new UnsupportedOperationException("Unable to add anySvnTag.");
            }
        };
    }

    public static CruiseConfigTag anyHgTag() {
        return new CruiseConfigTag() {
            protected String startTag() {
                return "<hg ";
            }

            protected String endTag() {
                return "</hg>";
            }

            protected String asString() {
                throw new UnsupportedOperationException("Unable to add anyHgTag.");
            }
        };
    }

    public static CruiseConfigTag anyGitTag() {
        return new CruiseConfigTag() {
            protected String startTag() {
                return "<git ";
            }

            protected String endTag() {
                return "</git>";
            }

            protected String asString() {
                throw new UnsupportedOperationException("Unable to add anyGitTag.");
            }
        };
    }
    
    public static CruiseConfigTag filterTag(final String filter) {
        return new CruiseConfigTag() {
            protected String startTag() {
                return "<filter><ignore pattern='" + filter + "'/>";
            }

            protected String endTag() {
                return "</filter>";
            }

            protected String asString() {
                return startTag() + endTag();
            }
        };
    }

    public static CruiseConfigTag anyP4Tag() {
        return new CruiseConfigTag() {
            protected String startTag() {
                return "<p4 ";
            }

            protected String endTag() {
                return "</p4>";
            }


            protected String asString() {
                throw new UnsupportedOperationException("Unable to add anyP4Tag.");
            }
        };
    }

    public static CruiseConfigTag hgTag(final String url) {
        return new SimpleCruiseConfigTag("hg", "url", url);
    }

    public static CruiseConfigTag svnTag(final String url) {
        return new SimpleCruiseConfigTag("svn", "url", url);
    }

    public static CruiseConfigTag svnTag(final String url, final boolean checkExternal) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        attributes.put("url", url);
        attributes.put("checkexternals", String.valueOf(checkExternal));
        return new SimpleCruiseConfigTag("svn", attributes);
    }

    public static CruiseConfigTag gitTag(final String url) {
        return new SimpleCruiseConfigTag("git", "url", url);
    }

    public static CruiseConfigTag p4Tag(String url) {
        String view = "//depot/... //localhost/...";
        SimpleCruiseConfigTag viewTag = new SimpleCruiseConfigTag("view", "<![CDATA[\n" + view + "]]>");
        return new SimpleCruiseConfigTag("p4", "\n" + viewTag.asString(), "port", url);
    }

    public static CruiseConfigTag svnTag(String repoUrl, String dest) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        attributes.put("url", repoUrl);
        attributes.put("dest", dest);
        return new SimpleCruiseConfigTag("svn", attributes);
    }
}
