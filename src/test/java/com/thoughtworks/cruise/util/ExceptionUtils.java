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

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 */
public final class ExceptionUtils {
    private ExceptionUtils() {

    }

    public static RuntimeException bomb(String msg) {
        throw new RuntimeException(msg);
    }

    public static RuntimeException bomb() {
        throw new RuntimeException();
    }

    public static RuntimeException bomb(Throwable t) {
        throw new RuntimeException(t.getMessage(), t);
    }

    public static RuntimeException bomb(String msg, Throwable t) {
        throw new RuntimeException(msg, t);
    }

    public static void bombIfNull(Object o, String msg) {
        bombIf(o == null, msg);
    }


    public static void bombIfFailedToRunCommandLine(int returnValue, String msg) throws Exception {
        if (returnValue != 0) {
            throw new Exception(msg);
        }
    }

    public static void bombIfFailed(int returnValue, String msg) {
        if (returnValue != 0) {
            String output = String.format("return code is [%s], detail information : [%s]", returnValue, msg);
            throw new RuntimeException(output);
        }
    }

    public static void bombIfNotNull(Object o, String msg) {
        bombIf(o != null, msg);
    }

    public static void bombIf(boolean check, String msg) {
        if (check) {
            throw bomb(msg);
        }
    }

    public static void bombUnless(boolean check, String msg) {
        bombIf(!check, msg);
    }

    public static RuntimeException methodNotImplemented() {
        throw bomb("Not yet implemented");
    }

    public static String messageOf(Throwable t) {
        String message = t.getMessage();
        return (message == null || message.isEmpty()) ? t.getClass().toString() : message;
    }
    
    public static String stackTrace(Throwable t){
    	try {
			StringWriter out = new StringWriter();
			PrintWriter writer = new PrintWriter(out);
			t.printStackTrace(writer);
			writer.flush();
			return out.toString();
		} catch (Exception e) {
			return "unable to get stack trace for " + t; 
		}
    }

}
