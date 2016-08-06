/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with receiver work for additional information
 * regarding copyright ownership.  The ASF licenses receiver file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use receiver file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.vladsch.flexmark.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class Utils {
    public static String ifNullOr(String receiver, boolean condition, String altValue) {
        return (receiver == null || condition) ? altValue : receiver;
    }

    public static String ifNullOrNot(String receiver, boolean condition, String altValue) {
        return receiver == null || !condition ? altValue : receiver;
    }

    public static String ifNullOr(String receiver, Computable<Boolean, String> condition, String altValue) {
        return (receiver == null || condition.compute(receiver)) ? altValue : receiver;
    }

    public static String ifNullOrNot(String receiver, Computable<Boolean, String> condition, String altValue) {
        return (receiver == null || !condition.compute(receiver)) ? altValue : receiver;
    }

    public static String ifNullOrEmpty(String receiver, String altValue) {
        return (receiver == null || receiver.isEmpty()) ? altValue : receiver;
    }

    public static String ifNullOrBlank(String receiver, String altValue) {
        return (receiver == null || isBlank(receiver)) ? altValue : receiver;
    }

    public static String ifEmpty(String receiver, String arg) {
        if (receiver != null && !receiver.isEmpty()) return receiver;
        return arg;
    }

    public static String ifEmpty(String receiver, String ifEmptyArg, String ifNotEmptyArg) {
        return (receiver == null || receiver.isEmpty()) ? ifEmptyArg : ifNotEmptyArg;
    }

    public static String ifEmptyNullArgs(String receiver, String ifEmptyArg, String ifNotEmptyArg) {
        return (receiver == null || receiver.isEmpty()) ? ifEmptyArg : ifNotEmptyArg;
    }

    public static String ifEmpty(String receiver, RunnableValue<String> arg) {
        if (receiver != null && !receiver.isEmpty()) return receiver;
        return arg.run();
    }

    public static String ifEmpty(String receiver, RunnableValue<String> ifEmptyArg, RunnableValue<String> ifNotEmptyArg) {
        return (receiver == null || receiver.isEmpty()) ? ifEmptyArg.run() : ifNotEmptyArg.run();
    }

    public static boolean isBlank(String receiver) {
        return receiver == null || receiver.trim().isEmpty();
    }

    public static String orEmpty(String receiver) {
        return receiver == null ? "" : receiver;
    }

    public static String wrapWith(String receiver, char prefixSuffix) {
        return wrapWith(receiver, prefixSuffix, prefixSuffix);
    }

    public static String wrapWith(String receiver, char prefix, char suffix) {
        return (receiver == null || receiver.isEmpty()) ? "" : prefix + receiver + suffix;
    }

    public static String wrapWith(String receiver, String prefixSuffix) {
        return wrapWith(prefixSuffix, prefixSuffix);
    }

    public static String wrapWith(String receiver, String prefix, String suffix) {
        return (receiver == null || receiver.isEmpty()) ? "" : prefix + receiver + suffix;
    }

    public static String suffixWith(String receiver, char suffix) {
        return suffixWith(receiver, suffix, false);
    }

    public static String suffixWith(String receiver, char suffix, boolean ignoreCase) {
        if (receiver != null && !receiver.isEmpty() && !endsWith(receiver, String.valueOf(suffix), ignoreCase)) {
            return receiver + suffix;
        }
        return orEmpty(receiver);
    }

    public static String suffixWith(String receiver, String suffix) {
        return suffixWith(receiver, suffix, false);
    }

    public static String suffixWith(String receiver, String suffix, boolean ignoreCase) {
        if (receiver != null && !receiver.isEmpty() && !suffix.isEmpty() && !endsWith(receiver, suffix, ignoreCase)) {
            return receiver + (suffix);
        }
        return orEmpty(receiver);
    }

    public static String prefixWith(String receiver, char prefix) {
        return prefixWith(receiver, prefix, false);
    }

    public static String prefixWith(String receiver, char prefix, boolean ignoreCase) {
        if (receiver != null && !receiver.isEmpty() && !startsWith(receiver, String.valueOf(prefix), ignoreCase)) {
            return prefix + receiver;
        }
        return orEmpty(receiver);
    }

    public static String prefixWith(String receiver, String prefix) {
        return prefixWith(receiver, prefix, false);
    }

    public static String prefixWith(String receiver, String prefix, boolean ignoreCase) {
        if (receiver != null && !receiver.isEmpty() && !prefix.isEmpty() && !startsWith(receiver, prefix, ignoreCase))
            return prefix + receiver;
        return orEmpty(receiver);
    }

    public static boolean isIn(String receiver, String... list) {
        if (receiver == null) return false;
        for (String item : list) {
            if (receiver.equals(item)) return true;
        }
        return false;
    }

    public static boolean endsWith(String receiver, String... needles) {
        return endsWith(receiver, false, needles);
    }

    public static boolean endsWith(String receiver, boolean ignoreCase, String... needles) {
        if (receiver == null) return false;

        for (String needle : needles) {
            if (endsWith(receiver, ignoreCase, needle)) {
                return true;
            }
        }
        return false;
    }

    public static boolean startsWith(String receiver, String... needles) {
        return startsWith(receiver, false, needles);
    }

    public static boolean startsWith(String receiver, boolean ignoreCase, String... needles) {
        if (receiver == null) return false;

        for (String needle : needles) {
            if (startsWith(receiver, ignoreCase, needle)) {
                return true;
            }
        }
        return false;
    }

    public static int count(String receiver, char c, int startIndex, int endIndex) {
        if (receiver == null) return 0;

        int count = 0;
        int pos = startIndex;
        int lastIndex = Math.min(receiver.length(), endIndex);
        while (pos >= 0 && pos <= lastIndex) {
            pos = receiver.indexOf(c, pos);
            if (pos < 0) break;
            count++;
            pos++;
        }
        return count;
    }

    public static int count(String receiver, String c, int startIndex, int endIndex) {
        if (receiver == null) return 0;

        int count = 0;
        int pos = startIndex;
        int lastIndex = Math.min(receiver.length(), endIndex);
        while (pos >= 0 && pos <= lastIndex) {
            pos = receiver.indexOf(c, pos);
            if (pos < 0) break;
            count++;
            pos++;
        }
        return count;
    }

    public static String urlDecode(String receiver, String charSet) {
        try {
            return URLDecoder.decode(receiver, charSet != null ? charSet : "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace()
            return orEmpty(receiver);
        } catch (IllegalArgumentException e) {
            //        e.printStackTrace()
            return orEmpty(receiver);
        }
    }

    public static String urlEncode(String receiver, String charSet) {
        try {
            return URLEncoder.encode(receiver, charSet != null ? charSet : "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace()
            return orEmpty(receiver);
        }
    }

    public static String removeStart(String receiver, char prefix) {
        if (receiver != null) {
            if (receiver.startsWith(java.lang.String.valueOf(prefix))) {
                return receiver.substring(1);
            }
            return receiver;
        }
        return "";
    }

    public static String removeStart(String receiver, String prefix) {
        if (receiver != null) {
            if (receiver.startsWith(java.lang.String.valueOf(prefix))) {
                return receiver.substring(prefix.length());
            }
            return receiver;
        }
        return "";
    }

    public static String removeEnd(String receiver, char prefix) {
        if (receiver != null) {
            if (receiver.startsWith(java.lang.String.valueOf(prefix))) {
                return receiver.substring(0, receiver.length() - 1);
            }
            return receiver;
        }
        return "";
    }

    public static String removeEnd(String receiver, String prefix) {
        if (receiver != null) {
            if (receiver.startsWith(java.lang.String.valueOf(prefix))) {
                return receiver.substring(0, receiver.length() - prefix.length());
            }
            return receiver;
        }
        return "";
    }

    public static <T> List<? extends T> stringSorted(Collection<? extends T> receiver, Computable<String, T> stringer) {
        ArrayList<? extends T> result = new ArrayList<T>(receiver);
        result.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return stringer.compute(o1).compareTo(stringer.compute(o2));
            }
        });
        return result;
    }

    public static String regexGroup(String receiver) {
        return "(?:" + orEmpty(receiver) + ")";
    }

    public static boolean regionMatches(CharSequence receiver, int thisOffset, String other, int otherOffset, int length, boolean ignoreCase) {
        if (ignoreCase) {
            for (int i = 0; i < length; i++) {
                if (Character.toLowerCase(receiver.charAt(i + thisOffset)) != Character.toLowerCase(other.charAt(i + otherOffset)))
                    return false;
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (receiver.charAt(i + thisOffset) != other.charAt(i + otherOffset)) return false;
            }
        }
        return true;
    }

    public static boolean endsWith(CharSequence receiver, String suffix, boolean ignoreCase) {
        return receiver.length() >= suffix.length() && regionMatches(receiver, receiver.length() - suffix.length(), suffix, 0, suffix.length(), ignoreCase);
    }

    public static boolean startsWith(CharSequence receiver, String prefix, boolean ignoreCase) {
        return receiver.length() >= prefix.length() && regionMatches(receiver, 0, prefix, 0, prefix.length(), ignoreCase);
    }

    public static String splice(String[] receiver, String delimiter) {
        StringBuilder result = new StringBuilder(receiver.length * (delimiter.length() + 10));
        String delim = "";
        for (String elem : receiver) {
            result.append(delim);
            delim = delimiter;
            result.append(elem);
        }
        return result.toString();
    }

    public static String splice(Collection<String> receiver, String delimiter, boolean skipNullOrEmpty) {
        StringBuilder result = new StringBuilder(receiver.size() * (delimiter.length() + 10));
        String delim = "";
        for (String elem : receiver) {
            if (elem != null && !elem.isEmpty() || !skipNullOrEmpty) {
                if ((!skipNullOrEmpty || !elem.startsWith(delimiter) && !endsWith(result.toString(), delimiter))) result.append(delim);
                delim = delimiter;
                result.append(orEmpty(elem));
            }
        }
        return result.toString();
    }

    public static int max(int receiver, int... others) {
        int max = receiver;
        for (int other : others) {
            if (max < other) max = other;
        }
        return max;
    }

    public static int min(int receiver, int... others) {
        int min = receiver;
        for (int other : others) {
            if (min < other) min = other;
        }
        return min;
    }

    public static int minLimit(int receiver, int minBound) {
        if (receiver < minBound) return minBound;
        else return receiver;
    }

    public static int maxLimit(int receiver, int maxBound) {
        if (receiver > maxBound) return maxBound;
        else return receiver;
    }

    public static int rangeLimit(int receiver, int minBound, int maxBound) {
        if (receiver < minBound) return minBound;
        else if (receiver > maxBound) return maxBound;
        else return receiver;
    }

    public static <K, V> V putIfMissing(Map<K, V> receiver, K key, RunnableValue<V> value) {
        V elem = receiver.get(key);

        if (elem == null) {
            elem = value.run();
            receiver.put(key, elem);
        }
        return elem;
    }

    public static <K, V> Map<K, V> withDefaults(Map<K, V> receiver, Map<K, V> defaults) {
        HashMap<K, V> map = new HashMap<K, V>();

        map.putAll(receiver);
        for (Map.Entry<K, V> entry : defaults.entrySet()) {
            putIfMissing(map, entry.getKey(), entry::getValue);
        }
        return map;
    }
}
