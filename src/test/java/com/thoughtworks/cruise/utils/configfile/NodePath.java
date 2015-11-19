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

import java.util.ArrayList;

import static com.thoughtworks.cruise.util.ExceptionUtils.bombIf;

public class NodePath {
    private ArrayList<Entry<CruiseConfigTag>> queue;

    public NodePath(CruiseConfigTag... nodes) {
        queue = new ArrayList<Entry<CruiseConfigTag>>(nodes.length);
        for (CruiseConfigTag node : nodes) {
            queue.add(wrap(node));
        }
    }

    public CruiseConfigTag first() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.get(0).obj;
    }

    public CruiseConfigTag next(CruiseConfigTag node) {
        int i = queue.indexOf(wrap(node));
        bombIf(i < 0, "Illegal argument");
        if (i + 1 < queue.size()) {
            return queue.get(i + 1).obj;
        }
        return null;
    }
    public CruiseConfigTag childOf(Entry<CruiseConfigTag> wrap) {
        int i = queue.indexOf(wrap);
        bombIf(i < 0, "Illegal argument");
        if (i + 1 < queue.size()) {
            return queue.get(i + 1).obj;
        }
        return null;
    }

    private Entry<CruiseConfigTag> wrap(CruiseConfigTag node) {
        return new Entry<CruiseConfigTag>(node);
    }

    public Entry<CruiseConfigTag> get(int index) {
        return queue.get(index - 1);
    }

    private static class Entry<T> {
        T obj;

        private Entry(T obj) {
            this.obj = obj;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Entry entry = (Entry) o;
            return obj == entry.obj;
        }

        public int hashCode() {
            return obj.hashCode();
        }
    }

}
