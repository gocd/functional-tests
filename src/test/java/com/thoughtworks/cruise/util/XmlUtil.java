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

package com.thoughtworks.cruise.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.StringReader;
import java.net.URISyntaxException;

public class XmlUtil {
    public static Document parse(String xml) throws DocumentException, SAXException, URISyntaxException {
        SAXReader builder = new SAXReader();
        Document dom = null;
        try {
            dom = builder.read(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException("XML invalid. " + xml, e);
        }
        return dom;
    }
}
