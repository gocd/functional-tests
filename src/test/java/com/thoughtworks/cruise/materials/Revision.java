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

import java.util.ArrayList;
import java.util.List;

public class Revision {
    final private String revisionNumber;
    final private String comment;
    final private String author;
    private List<String> added;
    private List<String> modified;
    private List<String> deleted;

    public Revision(String revisionNumber, String comment) {
        this(revisionNumber, null, comment, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
    }

    public Revision(String revisionNumber, String author, String comment, List<String> modified, List<String> added, List<String> deleted) {
        this.revisionNumber = revisionNumber;
        this.author = author;
        this.comment = comment;
        this.added = added;
        this.modified = modified;
        this.deleted = deleted;
    }

    public boolean hasRevisionNumber(String number) {
        return revisionNumber.startsWith(number);
    }

    public boolean hasComment(String comment) {
        return this.comment.equals(comment);
    }

    public boolean was(String file, String action) {
        if (action.equals("added")) {
            return added.contains(file);
        }
        if (action.equals("modified")) {
            return modified.contains(file);
        }
        if (action.equals("deleted")) {
            return deleted.contains(file);
        }
        return false;
    }

    public String revisionNumber() {
        return revisionNumber;        
    }
    
    public String author(){
    	return author;
    }

    public void addDeletedFile(String text) {
        deleted.add(text);
    }

    public void addModifiedFile(String text) {
        modified.add(text);
    }

    public void addAddedFile(String text) {
        added.add(text);
    }
}
