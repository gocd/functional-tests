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

package com.thoughtworks.twist.gauge.migration;

import com.thoughtworks.gauge.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;

public class GaugeSpringInitializer {

    private static AnnotationConfigApplicationContext elementContext;
    private static final String APPLICATION_CONTEXT_SUITE_XML = "applicationContext-suite.xml";
    private static final String APPLICATION_CONTEXT_SCENARIO_XML = "applicationContext-scenario.xml";
    private static ClassPathXmlApplicationContext suiteContext;
    private static ClassPathXmlApplicationContext scenarioContext;

    public GaugeSpringInitializer() {
        elementContext = new AnnotationConfigApplicationContext();
    }

    @BeforeSuite
    public void before() {
        ClassInstanceManager.setClassInitializer(new ClassInitializer() {
            @Override
            public Object initialize(Class<?> aClass) throws Exception {
                registerJavaBean(getBeanName(aClass.getSimpleName()), aClass, elementContext, false, true);
                return bean(getBeanName(aClass.getSimpleName()));
            }
        });
        suiteContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_SUITE_XML);
        suiteContext.registerShutdownHook();
        suiteContext.start();
    }

    @AfterSuite
    public void afterSuite() {
        if (suiteContext != null) {
            suiteContext.close();
            suiteContext = null;
        }
    }


    @BeforeScenario
    public void beforeScenario() {
        scenarioContext = new ClassPathXmlApplicationContext(new String[]{APPLICATION_CONTEXT_SCENARIO_XML}, suiteContext);
        scenarioContext.setParent(suiteContext);
        scenarioContext.refresh();
        scenarioContext.registerShutdownHook();
        scenarioContext.start();
        elementContext = new AnnotationConfigApplicationContext();
        elementContext.setParent(scenarioContext);
        RootBeanDefinition beanDefinition = new RootBeanDefinition(ScriptFactoryPostProcessor.class);
        elementContext.registerBeanDefinition(".scriptFactoryPostProcessor", beanDefinition);
        elementContext.refresh();
        elementContext.start();
    }

    @AfterScenario
    public void afterScenario() {
        if (scenarioContext != null) {
            scenarioContext.close();
            scenarioContext = null;
        }
    }

    private void registerJavaBean(String beanId, Class<?> aClass, GenericApplicationContext context, boolean lazy, boolean singleton) {
        String beanName = getBeanName(beanId);
        boolean containsDefinition = context.containsBeanDefinition(beanName);
        if (containsDefinition && singleton) {
            return;
        }
        RootBeanDefinition beanDefinition = new RootBeanDefinition(aClass, AbstractBeanDefinition.AUTOWIRE_AUTODETECT);
        beanDefinition.setLazyInit(lazy);
        beanDefinition.setSingleton(singleton);
        context.registerBeanDefinition(beanName, beanDefinition);
    }

    private static String getBeanName(String name) {
        if (name.length() <= 1) {
            return name.toLowerCase();
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    @SuppressWarnings("unchecked")
    public synchronized Object bean(String name) {
        String beanName = getBeanName(name);
        if (elementContext.containsBean(beanName)) {
            return elementContext.getBean(beanName);
        }

        return null;
    }
}
