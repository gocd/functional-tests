/*
 * Copyright 2016 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.models.html.fragments.pipeline;

import com.thoughtworks.go.framework.Fragment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public class MaterialsFragment extends Fragment {
    private HashMap<String, Class> materialTypeToFragment;

    public MaterialsFragment(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        this.materialTypeToFragment = new HashMap<String, Class>() {{
            put("hg", HgMaterialFragment.class);
            put("svn", SvnMaterialFragment.class);
            put("git", GitMaterialFragment.class);
        }};
    }

    public GitMaterialFragment getGitMaterialFragment() {
        return (GitMaterialFragment) materialFragments().get("git");
    }

    public SvnMaterialFragment getSvnMaterialFragment() {
        return (SvnMaterialFragment) materialFragments().get("svn");
    }

    public HgMaterialFragment getHgMaterialFragment() {
        return (HgMaterialFragment) materialFragments().get("hg");
    }

    private HashMap<String, MaterialFragment> materialFragments() {
        List<WebElement> materialFragments = children("dl.materials-definition");
        HashMap<String, MaterialFragment> fragments = new HashMap<>();

        for (int i = 1; i <= materialFragments.size(); ++i) {
            String materialType = child(String.format("dl.materials-definition:nth-child(%d)>dd.accordion-navigation>a", i)).getText();
            fragments.put(materialType, materialFor(this.materialTypeToFragment.get(materialType), String.format("dl.materials-definition:nth-child(%d)", i)));
        }
        return fragments;
    }

    private <T extends MaterialFragment> T materialFor(Class<T> clazz, String rootElement) {
        MaterialFragment fragment = null;

        try {
            fragment = clazz.getConstructor(WebDriver.class, WebElement.class).newInstance(driver, child(rootElement));
        } catch (Exception e) {
        }
        return (T) fragment;
    }
}