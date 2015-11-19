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

package com.thoughtworks.twist.migration.brt;

import org.apache.commons.lang.WordUtils;
import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BRTMigrator {

	public void assertValue(String expected, String columnName, Object obj)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException, ClassNotFoundException {
		Class cls = Class.forName(obj.getClass().getName());
		String methodName = camelCase(columnName.substring(0,
				columnName.length() - 1));
		Method meth = cls.getMethod(methodName);
		Object returnValue = meth.invoke(obj);
		Assert.assertTrue(expected.equals(returnValue));
	}

	public void invokeSetter(String rowValue, String columnName, Object obj)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException, ClassNotFoundException {
		Class cls = Class.forName(obj.getClass().getName());
		Class paramType[] = new Class[] { String.class };

		String methodName = "set" + WordUtils.capitalize(camelCase(columnName));
		Method meth = cls.getMethod(methodName, paramType);
		Object args[] = new Object[] { rowValue };
		meth.invoke(obj, args);
	}

	public void BRTExecutor(com.thoughtworks.gauge.Table table, Object obj)
			throws ClassNotFoundException, InvocationTargetException,
			IllegalAccessException, NoSuchMethodException {
		List<String> columnNames = table.getColumnNames();
		List<List<String>> allRows = table.getRows();

		for (List<String> row : allRows) {
			invokeHook(obj, "setUp");
			for (int i = 0; i < row.size(); i++) {
				String col = columnNames.get(i);
				if (col.endsWith("?")) {
					assertValue(row.get(i), col, obj);
				} else {
					invokeSetter(row.get(i), col, obj);
				}
			}
			invokeHook(obj, "tearDown");
		}
	}

	private void invokeHook(Object obj, String name)
			throws ClassNotFoundException, InvocationTargetException,
			IllegalAccessException {
		Class cls = Class.forName(obj.getClass().getName());
		Method meth = null;
		try {
			meth = cls.getMethod(name);
		} catch (NoSuchMethodException e) {
			return;
		}
		Object args[] = new Object[0];
		meth.invoke(obj, args);
	}

	private String camelCase(String text) {
		String[] splits = text.split(" ");
		StringBuilder sb = new StringBuilder();
		char[] chars = splits[0].toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		sb.append(String.valueOf(chars));
		for (int i = 1; i < splits.length; i++)
			sb.append(WordUtils.capitalize(splits[i]));
		return sb.toString();
	}

}
