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

import com.thoughtworks.cruise.tfs.Workspaces;
import com.thoughtworks.cruise.util.command.ConsoleResult;
import com.thoughtworks.xstream.XStream;

/**
 * @understands: how to parse xml output from tfs command
 */
public class XmlFormatTfsOutputParser {

    public Workspaces parseWorkspaces(ConsoleResult consoleResult) {
        return (Workspaces) deserialize(consoleResult);
    }


    private Object deserialize(ConsoleResult consoleResult) {
        XStream xStream = xstream();
        return xStream.fromXML(unwindowfy(consoleResult.outputAsString()));
    }

    private String unwindowfy(String windowfiedXml) {
        int startOfXml = windowfiedXml.indexOf("<?xml");
        return windowfiedXml.substring(startOfXml);
    }   

    private static XStream xstream() {
        XStream xStream = new XStream();
        xStream.alias("workspaces", Workspaces.class);
        xStream.autodetectAnnotations(true);
        return xStream;
    }

}
