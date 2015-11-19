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

import com.thoughtworks.cruise.utils.ConfigFileFixture;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thoughtworks.cruise.util.ExceptionUtils.*;
import static com.thoughtworks.cruise.utils.Assertions.assertWillHappen;
import static com.thoughtworks.cruise.utils.configfile.CruiseConfigUtil.InsertLocation.START;

public class CruiseConfigUtil {

    private final ConfigFileContents contents;

    public CruiseConfigUtil(ConfigFileContents contents) {
        this.contents = contents;
    }

    public void resetCruiseConfig() {
        contents.reset();
    }

    public void withoutLicense() {
        contents.write(ConfigFileFixture.WITHOUT_LICENSE);
    }

    public int numbersOfPipelin() {
        String output = contents.read();
        return StringUtils.countMatches(output, "</pipeline>");
    }

    public void insertAtStartOf(CruiseConfigTag wrapperTag, CruiseConfigTag tagToInsert) {
        String currentContents = contents.read();

        String toFind = wrapperTag.startTag();
        bombUnless(currentContents.contains(toFind), "Unable to find '" + toFind + " in\n" + currentContents);

        String toInsert = tagToInsert.asString();
        String newContents = currentContents.replace(toFind, toFind + toInsert);

        contents.write(newContents);
    }

    public void insertAtEndOf(CruiseConfigTag wrapperTag, CruiseConfigTag tagToInsert) {
        String currentContents = contents.read();

        String toFind = wrapperTag.endTag();
        String toInsert = tagToInsert.asString();
        bombUnless(currentContents.contains(toFind),
                "Unable to find '" + toFind + "' to insert '" + toInsert + "' in\n" + currentContents);
        String newContent = currentContents.replace(toFind, toInsert + toFind + "\n");

        contents.write(newContent);
    }

    public void insertAtStartOf(NodePath path, CruiseConfigTag tagToInsert) {
        insertAt(path, tagToInsert, InsertLocation.START);
    }

    public void insertAtEndOf(NodePath path, CruiseConfigTag tagToInsert) {
        insertAt(path, tagToInsert, InsertLocation.END);
    }

    public void insertAt(NodePath path, CruiseConfigTag tagToInsert, InsertLocation insertLocation) {
        String content = this.contents.read();
        NodeInfo nodeInfo = locate(content, path);
        String pattern = tagToInsert.startTag() + ".*" + tagToInsert.endTag();
        if (containsPattern(nodeInfo.getContent(), pattern)) {
            return;
        }

        int insertionPoint = insertionPoint(nodeInfo, insertLocation);
        String newContent = new StringBuffer().append(content.substring(0, insertionPoint)).append(
                tagToInsert.asString()).append(content.substring(insertionPoint)).toString();

        this.contents.write(newContent);
    }

    private int insertionPoint(NodeInfo info, InsertLocation insertLocation) {
        switch (insertLocation) {
            case START:
                return info.getStartingInsertionPoint();
            case END:
                return info.getEndingInsertionPoint();
            default:
                throw bomb("Illegal argument");
        }
    }

    NodeInfo locate(String content, NodePath path) {
        int startInsertionPoint = 0;
        int absoluteStartIndex = 0;
        int absoluteEndIndex = 0;
        CruiseConfigTag currentNode = path.first();

        while (currentNode != null) {
            String startTag = currentNode.startTag();
            int startIndex = content.indexOf(startTag);
            absoluteStartIndex += startIndex;
            startInsertionPoint = absoluteStartIndex + startTag.length();
            bombIf(startIndex < 0, "Unable to find '" + startTag + " in\n" + content);

            String endTag = currentNode.endTag();
            int endIndex = content.indexOf(endTag, startIndex);
            bombIf(endIndex < 0, "Unable to find '" + endTag + " in\n" + content);

            content = content.substring(startIndex, endIndex + endTag.length());
            absoluteEndIndex = absoluteStartIndex + content.length() - endTag.length();
            currentNode = path.next(currentNode);
        }

        return new NodeInfo(content, startInsertionPoint, absoluteEndIndex);
    }

    public void ensureExistsIn(CruiseConfigTag wrapper, CruiseConfigTag shouldExist, InsertLocation insertLocation) {
        if (!containsMatch(wrapper.startTag() + ".*" + shouldExist.startTag())) {
            if (insertLocation.equals(START)) {
                insertAtStartOf(wrapper, shouldExist);
            } else {
                insertAtEndOf(wrapper, shouldExist);
            }
        }
    }

    public void ensureExistsIn(NodePath wrapperQueue, CruiseConfigTag shouldExist, InsertLocation insertLocation) {
        CruiseConfigTag wrapper = wrapperQueue.childOf(wrapperQueue.get(1));
        System.err.println(wrapper);
        if (!containsMatch(wrapper.startTag() + ".*" + shouldExist.startTag())) {
            if (insertLocation.equals(START)) {
                insertAtStartOf(wrapper, shouldExist);
            } else {
                insertAtEndOf(wrapper, shouldExist);
            }
        }
    }

    public void deleteFrom(CruiseConfigTag containerTag, CruiseConfigTag toDelete) {
        String currentContents = contents.read();

        Matcher matcher = matcher("(" + containerTag.startTag() + ".*?" + containerTag.endTag() + ")", currentContents);
        if (matcher.find()) {
            String subcontent = matcher.group(1);
            String newSubcontent = matcher(toDelete.startTag() + ".*?" + toDelete.endTag(), subcontent).replaceAll("");
            String newContents = currentContents.replace(subcontent, newSubcontent);

            contents.write(newContents);
        }
    }

    public void corrupt() {
        contents.write("some corrupt #@#@$%$!!");
    }

    boolean containsMatch(String regex) {
        String currentContents = contents.read();
        return containsPattern(currentContents, ".*" + regex + ".*");
    }

    private static Matcher matcher(String pattern, String contents) {
        return Pattern.compile(pattern, Pattern.DOTALL).matcher(contents);
    }

    @Deprecated
    static boolean contentsContainsMatch(String contents, String regex) {
        return containsPattern(contents, ".*" + regex + ".*");
    }

    private static boolean containsPattern(String content, String pattern) {
        return matcher(pattern, content).matches();
    }

    public enum InsertLocation {
        START, END
    }

    public void write(String newContent) {
        contents.write(newContent);
    }

    public void waitForTag(CruiseConfigTag tag) {
        assertWillHappen(this, shouldContainTag(tag));
    }

    private org.hamcrest.Matcher shouldContainTag(final CruiseConfigTag tag) {
        return new TypeSafeMatcher<CruiseConfigUtil>() {

            public void describeTo(Description arg0) {
                arg0.appendText("Should contain tag " + tag);
            }

            @Override
            public boolean matchesSafely(CruiseConfigUtil item) {
                return item.containsMatch(tag.startTag() + ".*" + tag.endTag());
            }
        };

    }

    public String currentContents() {
        return contents.currentContents();
    }

}
