package com.vladsch.flexmark.util.misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Utils {

    public static <T> T ifNull(T receiver, T altValue) {
        return (receiver == null) ? altValue : receiver;
    }

    public static <T> T ifNullOr(T receiver, boolean condition, T altValue) {
        return (receiver == null || condition) ? altValue : receiver;
    }

    public static <T> T ifNullOrNot(T receiver, boolean condition, T altValue) {
        return receiver == null || !condition ? altValue : receiver;
    }

    public static <T> T ifNullOr(T receiver, Function<T, Boolean> condition, T altValue) {
        return (receiver == null || condition.apply(receiver)) ? altValue : receiver;
    }

    public static <T> T ifNullOrNot(T receiver, Function<T, Boolean> condition, T altValue) {
        return (receiver == null || !condition.apply(receiver)) ? altValue : receiver;
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

    public static String ifEmpty(String receiver, Supplier<String> arg) {
        if (receiver != null && !receiver.isEmpty()) return receiver;
        return arg.get();
    }

    public static String ifEmpty(
            String receiver,
            Supplier<String> ifEmptyArg,
            Supplier<String> ifNotEmptyArg
    ) {
        return (receiver == null || receiver.isEmpty()) ? ifEmptyArg.get() : ifNotEmptyArg.get();
    }

    public static boolean isBlank(String receiver) {
        return receiver == null || receiver.trim().isEmpty();
    }

    // TODO: rewrite these to use BasedSequence implementation
    public static boolean isWhiteSpaceNoEOL(String receiver) {
        int iMax = receiver.length();
        for (int i = 0; i < iMax; i++) {
            char c = receiver.charAt(i);
            if (c != ' ' && c != '\t') return false;
        }
        return true;
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
        return wrapWith(receiver, prefixSuffix, prefixSuffix);
    }

    public static String wrapWith(String receiver, String prefix, String suffix) {
        return (receiver == null || receiver.isEmpty()) ? "" : prefixWith(suffixWith(receiver, suffix), prefix);
    }

    public static String suffixWith(String receiver, char suffix) {
        return suffixWith(receiver, suffix, false);
    }

    public static String suffixWithEol(String receiver) {
        return suffixWith(receiver, '\n', false);
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
        if (receiver != null && !receiver.isEmpty() && suffix != null && !suffix.isEmpty() && !endsWith(receiver, suffix, ignoreCase)) {
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
        if (receiver != null && !receiver.isEmpty() && prefix != null && !prefix.isEmpty() && !startsWith(receiver, prefix, ignoreCase))
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

        if (ignoreCase) {
            for (String needle : needles) {
                if (receiver.length() >= needle.length() && receiver.substring(receiver.length() - needle.length()).equalsIgnoreCase(needle)) {
                    return true;
                }
            }
        } else {
            for (String needle : needles) {
                if (receiver.endsWith(needle)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean startsWith(String receiver, String... needles) {
        return startsWith(receiver, false, needles);
    }

    public static boolean startsWith(String receiver, boolean ignoreCase, String... needles) {
        if (receiver == null) return false;

        if (ignoreCase) {
            for (String needle : needles) {
                if (receiver.length() >= needle.length() && receiver.substring(0, needle.length()).equalsIgnoreCase(needle)) {
                    return true;
                }
            }
        } else {
            for (String needle : needles) {
                if (receiver.startsWith(needle)) {
                    return true;
                }
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
            if (pos < 0 || pos > lastIndex) break;
            count++;
            pos++;
        }
        return count;
    }

    public static String urlDecode(String receiver, String charSet) {
        try {
            return URLDecoder.decode(receiver, charSet != null ? charSet : "UTF-8");
        } catch (UnsupportedEncodingException | IllegalArgumentException e) {
            //e.printStackTrace()
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

    public static String removePrefix(String receiver, char prefix) {
        if (receiver != null) {
            if (receiver.startsWith(String.valueOf(prefix))) {
                return receiver.substring(1);
            }
            return receiver;
        }
        return "";
    }

    public static String removePrefix(String receiver, String prefix) {
        if (receiver != null) {
            if (receiver.startsWith(String.valueOf(prefix))) {
                return receiver.substring(prefix.length());
            }
            return receiver;
        }
        return "";
    }

    public static String removeAnyPrefix(String receiver, String... prefixes) {
        if (receiver != null) {
            for (String prefix : prefixes) {
                if (receiver.startsWith(String.valueOf(prefix))) {
                    return receiver.substring(prefix.length());
                }
            }
            return receiver;
        }
        return "";
    }

    public static String removePrefixIncluding(String receiver, String delimiter) {
        if (receiver != null) {
            int pos = receiver.indexOf(delimiter);
            if (pos != -1) {
                return receiver.substring(pos + delimiter.length());
            }
            return receiver;
        }
        return "";
    }

    public static String removeSuffix(String receiver, char suffix) {
        if (receiver != null) {
            if (receiver.endsWith(String.valueOf(suffix))) {
                return receiver.substring(0, receiver.length() - 1);
            }
            return receiver;
        }
        return "";
    }

    public static String removeSuffix(String receiver, String suffix) {
        if (receiver != null) {
            if (receiver.endsWith(String.valueOf(suffix))) {
                return receiver.substring(0, receiver.length() - suffix.length());
            }
            return receiver;
        }
        return "";
    }

    public static String removeAnySuffix(String receiver, String... suffixes) {
        if (receiver != null) {
            for (String suffix : suffixes) {
                if (receiver.endsWith(String.valueOf(suffix))) {
                    return receiver.substring(0, receiver.length() - suffix.length());
                }
            }
            return receiver;
        }
        return "";
    }

    public static <T> List<? extends T> stringSorted(Collection<? extends T> receiver, Function<T, String> stringer) {
        ArrayList<? extends T> result = new ArrayList<T>(receiver);
        result.sort(Comparator.comparing(stringer));
        return result;
    }

    public static String regexGroup(String receiver) {
        return "(?:" + orEmpty(receiver) + ")";
    }

    public static boolean regionMatches(
            CharSequence receiver,
            int thisOffset,
            String other,
            int otherOffset,
            int length,
            boolean ignoreCase
    ) {
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

    /**
     * Longest Common Prefix for a set of strings
     *
     * @param s array of strings or null
     * @return longest common prefix
     */
    public static String getLongestCommonPrefix(String... s) {
        if (s == null || s.length == 0) return "";
        if (s.length == 1) return s[0];

        String s0 = s[0];
        int iMax = s0.length();
        int jMax = s.length;

        for (int j = 1; j < jMax; j++) {
            iMax = Math.min(s[j].length(), iMax);
        }

        for (int i = 0; i < iMax; i++) {
            char c = s0.charAt(i);
            for (int j = 1; j < jMax; j++) {
                if (s[j].charAt(i) != c) return s0.substring(0, i);
            }
        }
        return s0.substring(0, iMax);
    }

    public static String getAbbreviatedText(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength || maxLength < 6) return text;

        int prefix = maxLength / 2;
        int suffix = maxLength - 3 - prefix;
        return text.substring(0, prefix) + " … " + text.substring(text.length() - suffix);
    }

    public static String splice(Collection<String> receiver, String delimiter, boolean skipNullOrEmpty) {
        StringBuilder result = new StringBuilder(receiver.size() * (delimiter.length() + 10));
        String delim = "";
        for (String elem : receiver) {
            if (elem != null && !elem.isEmpty() || !skipNullOrEmpty) {
                if ((!skipNullOrEmpty || !elem.startsWith(delimiter) && !endsWith(result.toString(), delimiter)))
                    result.append(delim);
                delim = delimiter;
                result.append(orEmpty(elem));
            }
        }
        return result.toString();
    }

    public static String join(String[] items, String prefix, String suffix, String itemPrefix, String itemSuffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (String item : items) {
            sb.append(itemPrefix).append(item).append(itemSuffix);
        }
        sb.append(suffix);
        return sb.toString();
    }

    public static String join(
            Collection<String> items,
            String prefix,
            String suffix,
            String itemPrefix,
            String itemSuffix
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (String item : items) {
            sb.append(itemPrefix).append(item).append(itemSuffix);
        }
        sb.append(suffix);
        return sb.toString();
    }

    public static String repeat(String text, int repeatCount) {
        if (repeatCount > 0) {
            StringBuilder sb = new StringBuilder(text.length() * repeatCount);
            while (repeatCount-- > 0) {
                sb.append(text);
            }
            return sb.toString();
        }
        return "";
    }

    /*
       Limits and other numeric helpers
     */

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
            if (min > other) min = other;
        }
        return min;
    }

    public static int minLimit(int receiver, int... minBound) {
        return max(receiver, minBound);
    }

    public static int maxLimit(int receiver, int... maxBound) {
        return min(receiver, maxBound);
    }

    public static int rangeLimit(int receiver, int minBound, int maxBound) {
        return Math.min(Math.max(receiver, minBound), maxBound);
    }

    public static float max(float receiver, float... others) {
        float max = receiver;
        for (float other : others) {
            if (max < other) max = other;
        }
        return max;
    }

    public static float min(float receiver, float... others) {
        float min = receiver;
        for (float other : others) {
            if (min > other) min = other;
        }
        return min;
    }

    public static float minLimit(float receiver, float... minBound) {
        return max(receiver, minBound);
    }

    public static float maxLimit(float receiver, float... maxBound) {
        return min(receiver, maxBound);
    }

    public static float rangeLimit(float receiver, float minBound, float maxBound) {
        return Math.min(Math.max(receiver, minBound), maxBound);
    }

    public static int compare(@Nullable Number n1, @Nullable Number n2) {
        if (n1 == null && n2 == null) return 0;
        else if (n1 == null) return -1;
        else if (n2 == null) return 1;
        else if (n1 instanceof Double || n2 instanceof Double || n1 instanceof Float || n2 instanceof Float) return Double.compare(n1.doubleValue(), n2.doubleValue());
        else return Long.compare(n1.longValue(), n2.longValue());
    }

    static public <T extends Comparable<T>> int compareNullable(T i1, T i2) {
        if (i1 == null || i2 == null) return 0;
        else return i1.compareTo(i2);
    }

    public static <K, V> V putIfMissing(Map<K, V> receiver, K key, Supplier<V> value) {
        V elem = receiver.get(key);

        if (elem == null) {
            elem = value.get();
            receiver.put(key, elem);
        }
        return elem;
    }

    public static <K, V> Map<K, V> withDefaults(Map<K, V> receiver, Map<K, V> defaults) {
        HashMap<K, V> map = new HashMap<>(receiver);
        for (Map.Entry<K, V> entry : defaults.entrySet()) {
            putIfMissing(map, entry.getKey(), entry::getValue);
        }
        return map;
    }

    public static <K, V> void removeIf(Map<K, V> receiver, Function<Map.Entry<K, V>, Boolean> removeFilter) {
        ArrayList<K> keys = new ArrayList<>();
        for (Map.Entry<K, V> entry : receiver.entrySet()) {
            if (removeFilter.apply(entry)) {
                keys.add(entry.getKey());
            }
        }

        for (K key : keys) {
            receiver.remove(key);
        }
    }

    public static <K, V> void removeIf(Map<K, V> receiver, BiFunction<K, V, Boolean> removeFilter) {
        removeIf(receiver, entry -> removeFilter.apply(entry.getKey(), entry.getValue()));
    }

    public static void streamAppend(StringBuilder sb, InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getResourceAsString(Class<?> clazz, String resourcePath) {
        InputStream stream = clazz.getResourceAsStream(resourcePath);
        StringBuilder sb = new StringBuilder();
        streamAppend(sb, stream);
        return sb.toString();
    }

    @NotNull
    public static String escapeJavaString(@Nullable CharSequence param) {
        if (param == null) return "null";
        StringBuilder out = new StringBuilder();
        escapeJavaString(out, param);
        return out.toString();
    }

    @NotNull
    public static String quoteJavaString(@Nullable CharSequence param) {
        if (param == null) return "null";
        StringBuilder out = new StringBuilder();
        out.append("\"");
        escapeJavaString(out, param);
        out.append("\"");
        return out.toString();
    }

    public static void escapeJavaString(@NotNull StringBuilder out, @NotNull CharSequence chars) {
        int iMax = chars.length();
        for (int i = 0; i < iMax; i++) {
            char c = chars.charAt(i);
            switch (c) {
                case '"':
                    out.append("\\\"");
                    break;
                case '\n':
                    out.append("\\n");
                    break;
                case '\r':
                    out.append("\\r");
                    break;
                case '\t':
                    out.append("\\t");
                    break;
                case '\b':
                    out.append("\\b");
                    break;
                case '\f':
                    out.append("\\f");
                    break;
                case '\0':
                    out.append("\\0");
                    break;
                default:
                    if (c < ' ') {
                        out.append('%').append(String.format("%02x", (int) c));
                    } else {
                        out.append(c);
                    }
                    break;
            }
        }
    }

    public static <T> T getOrNull(@NotNull List<T> list, int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public static <T, S extends T> S getOrNull(@NotNull List<T> list, int index, Class<S> elementClass) {
        if (index >= 0 && index < list.size()) {
            T value = list.get(index);
            //noinspection unchecked
            return elementClass.isInstance(value) ? (S) value : null;
        }
        return null;
    }

    public static <T> T setOrAdd(@NotNull List<T> list, int index, T value) {
        if (index == list.size()) {
            list.add(value);
            return null;
        } else {
            return list.set(index, value);
        }
    }
}
