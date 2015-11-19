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

import com.thoughtworks.cruise.materials.Revision;
import org.dom4j.Document;
import org.dom4j.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SvnLogXmlParser {
    private static final String SVN_DATE_FORMAT_OUT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public List<Revision> parse(String svnLogOutput, String path) {
        try {
            return parseDOMTree(XmlUtil.parse(svnLogOutput), path);
        } catch (Exception e) {
            throw ExceptionUtils.bomb("Unable to parse svn log output: " + svnLogOutput, e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Revision> parseDOMTree(Document document, String path) throws ParseException {
        List<Revision> modifications = new ArrayList<Revision>();

        Element rootElement = document.getRootElement();
        List logEntries = rootElement.selectNodes("//logentry");
        for (Iterator iterator = logEntries.iterator(); iterator.hasNext();) {
            Element logEntry = (Element) iterator.next();

            modifications.add(parseLogEntry(logEntry, path));
        }

        return modifications;
    }

    @SuppressWarnings("unchecked")
    private Revision parseLogEntry(Element logEntry, String path) throws ParseException {
//        TODO: we may need to add this information to the Revision in a potentially dark future of this probabilistic multiverse.
//        Date modifiedTime = convertDate(logEntry.elementText("date"));
//        String author = logEntry.elementText("author");
        String comment = logEntry.elementText("msg");
        String revisionNumber = logEntry.attributeValue("revision");
        Revision revision = new Revision(revisionNumber, comment);
        
        Element logEntryPaths = (Element) logEntry.selectSingleNode("/paths");
        if (logEntryPaths != null) {
            for (Element pathElement : (List<Element>)logEntryPaths.selectNodes("/path")) {
                if (underPath(path, pathElement.getText())) {
                    addModificationToRevision(revision, pathElement);
                }
            }
        }
        return revision;
    }

    private void addModificationToRevision(Revision revision, Element pathElement) {
        String action = pathElement.attributeValue("action");
        if (action.equals("A")) {
            revision.addAddedFile(pathElement.getText());
        }
        if (action.equals("M")) {
            revision.addModifiedFile(pathElement.getText());
        }
        if (action.equals("D")) {
            revision.addDeletedFile(pathElement.getText());
        }
    }

    private boolean underPath(String path, String text) {
        return text.startsWith(path);
    }

    /**
     * Converts the specified SVN date string into a Date.
     *
     * @param date with format "yyyy-MM-dd'T'HH:mm:ss.SSS" + "...Z"
     * @return converted date
     * @throws java.text.ParseException if specified date doesn't match the expected format
     */
    static Date convertDate(String date) throws ParseException {
        final int zIndex = date.indexOf('Z');
        if (zIndex - 3 < 0) {
            throw new ParseException(date
                    + " doesn't match the expected subversion date format", date.length());
        }
        String withoutMicroSeconds = date.substring(0, zIndex - 3);

        return getOutDateFormatter().parse(withoutMicroSeconds);
    }

    public static DateFormat getOutDateFormatter() {
        DateFormat f = new SimpleDateFormat(SVN_DATE_FORMAT_OUT);
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        return f;
    }
}
