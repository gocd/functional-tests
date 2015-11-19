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

package com.thoughtworks.cruise.editpipelinewizard;

import com.thoughtworks.cruise.Regex;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @understands performing actions on go-admin form-field
 */
public class FormField {
    private static class FieldInstance {
        protected final ElementStub element;

        FieldInstance(ElementStub element) {
            this.element = element;
        }

        public final void assertValue(String value) {
            assertThat(getValue(), is(value));
        }

        protected String getValue() {
            return element.getValue();
        }

        public void assertVisible() {
            assertThat(element.toString(), element.isVisible(), is(true));
        }

        public void setValue(final String value) {
            element.setValue(value);
        }

        public void verifyError(Browser browser, String errorMessage) {
            assertThat(findParentWithKlass(element, "form_item_block").isVisible(), is(true));

            ElementStub formItemFragment = findParentWithKlass(element, "form_item_block");
            assertThat(browser.div("form_error").in(formItemFragment).getText(), is(errorMessage));
        }

        private ElementStub findParentWithKlass(ElementStub element, String cssKlass) {
            ElementStub parent = element.parentNode();
            String klass = parent.fetch("className");
            return klass != null && klass.contains(cssKlass) ? parent : findParentWithKlass(parent, cssKlass);
        }
    }

    private static enum FieldType {
        text_field {
            @Override
            public FieldInstance getField(Browser browser, String name) {
                return new FieldInstance(browser.textbox(fieldNameMatcher(name)));
            }},
        url_field {
                @Override
                public FieldInstance getField(Browser browser, String name) {
                    return new FieldInstance(browser.urlbox(fieldNameMatcher(name)));
                }},
        password_field {
            @Override
            public FieldInstance getField(Browser browser, String name) {
               return new FieldInstance(browser.password(fieldNameMatcher(name)));
            }},
        text_area {
            @Override
            public FieldInstance getField(Browser browser, String name) {
                return new FieldInstance(browser.textarea(fieldNameMatcher(name)));
            }},
        check_box {
            @Override
            public FieldInstance getField(Browser browser, String name) {
                return new FieldInstance(browser.checkbox(fieldNameMatcher(name))) {
                    @Override
                    protected String getValue() {
                        return String.valueOf(element.checked());
                    }

                    @Override
                    public void setValue(String value) {
                        if (value.equals("true")) {
                            element.check();
                        } else {
                            element.uncheck();
                        }
                    }
                };
            }};

        public static String fieldNameMatcher(String name) {
            return Regex.matches(name.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]"));
        }

        public abstract FieldInstance getField(Browser browser, String name);
    }

    private static final String NULL = "NULL";
    static Pattern REPRESENTATION_FORM = Pattern.compile("^\\s*(.+?)\\s*\\((.+?)\\)\\s*:\\s*(.+?)\\s*$");

    private String name;
    private FieldType type;
    private String contextData;

    public FormField(String representation) {
        Matcher matcher = REPRESENTATION_FORM.matcher(representation);
        if (matcher.matches()) {
            this.name = matcher.group(1);
            this.type = FieldType.valueOf(matcher.group(2));
            String value = matcher.group(3);
            this.contextData = value.equals(NULL) ? "" : value;
        } else {
            throw new IllegalArgumentException(String.format("bad input: given representation [%s] does not match /%s/", representation, REPRESENTATION_FORM));
        }
    }

    private FieldInstance find(Browser browser) {
        FieldInstance field = type.getField(browser, name);
        field.assertVisible();
        return field;
    }

    public void assertExistance(Browser browser) {
        find(browser).assertValue(contextData);
    }

    public void setValue(Browser browser) {
        find(browser).setValue(contextData);
    }

    public void verifyError(Browser browser) {
        find(browser).verifyError(browser, contextData);
    }
}
