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

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.HashMap;

public class DomUtil {
	public static Document getDomFor(String xml) {
	    registerNamespace();
		SAXReader builder = new SAXReader();
        builder.setValidation(false);
        Document dom = null;
        try {
            dom = builder.read(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException("XML invalid. " + xml, e);
        }
        return dom;
	}

    private static void registerNamespace() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("atom", "http://www.w3.org/2005/Atom");
        DocumentFactory instance = DocumentFactory.getInstance();
        instance.setXPathNamespaceURIs(map);
    }
}
