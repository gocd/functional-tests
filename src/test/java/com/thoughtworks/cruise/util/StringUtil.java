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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static class AlphaAsciiComparator implements Comparator<String> {
        public int compare(String one, String other) {
            int alphaComparison = one.toLowerCase().compareTo(other.toLowerCase());
            return alphaComparison == 0 ? one.compareTo(other) : alphaComparison;
        }
    }

    private StringUtil() {
    }

    public static String quote(String s) {
        return "\"" + s + "\"";
    }

    public static String quoteJavascriptString(String s) {
        s = s.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"");
        return quote(s);
    }

    public static String stripSpacesAndNewLines(String content) {
        return content.replaceAll("\\s+", "");
    }

//    public static String base64Encode(byte[] bytes) {
//        Base64 base64 = new Base64();
//        return stripLineSeparator(base64.encodeToString(bytes));
//    }

//    public static String md5Digest(byte[] bytes) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            return base64Encode(md.digest(bytes));
//        } catch (NoSuchAlgorithmException nsae) {
//            throw bomb(nsae);
//        }
//    }

//    public static String sha1Digest(byte[] bytes) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA1");
//            return base64Encode(md.digest(bytes));
//        } catch (NoSuchAlgorithmException nsae) {
//            throw bomb(nsae);
//        }
//    }

//    public static String sha1Digest(File file) {
//        InputStream input = null;
//        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA1");
//            input = new BufferedInputStream(new FileInputStream(file));
//            int n;
//            while (-1 != (n = input.read(buffer))) {
//                digest.update(buffer, 0, n);
//            }
//            return base64Encode(digest.digest());
//        } catch (Exception nsae) {
//            throw bomb(nsae);
//        } finally {
//            IOUtils.closeQuietly(input);
//        }
//    }

    public static String stripLineSeparator(String s) {
        return s.replaceAll("\\n", "").replaceAll("\\r", "");
    }

    public static String matchPattern(String regEx, String s) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String removeTrailingSlash(String s) {
        if (s.endsWith("/")) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    public static String wrapConfigVariable(String var) {
        return String.format("${%s}", var);
    }

    public static List<String> splitLines(String str) {
        return str.isEmpty() ? new ArrayList<String>() : Arrays.asList(str.split("\n"));
    }

    public static String passwordToString(String password) {
        if (password == null) {
            return "not-set";
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            buffer.append('*');
        }
        return buffer.toString();
    }

    public static String shortUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

}
