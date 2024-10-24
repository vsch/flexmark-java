package com.vladsch.flexmark.util.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;

public class Utils {
  // TODO: rewrite these to use BasedSequence implementation
  public static boolean isWhiteSpaceNoEOL(String receiver) {
    int iMax = receiver.length();
    for (int i = 0; i < iMax; i++) {
      char c = receiver.charAt(i);
      if (c != ' ' && c != '\t') {
        return false;
      }
    }
    return true;
  }

  static String orEmpty(String receiver) {
    return receiver == null ? "" : receiver;
  }

  public static String suffixWith(String receiver, char suffix) {
    return suffixWith(receiver, suffix, false);
  }

  private static String suffixWith(String receiver, char suffix, boolean ignoreCase) {
    if (receiver != null
        && !receiver.isEmpty()
        && !endsWith(receiver, String.valueOf(suffix), ignoreCase)) {
      return receiver + suffix;
    }
    return orEmpty(receiver);
  }

  public static String suffixWith(String receiver, String suffix) {
    return suffixWith(receiver, suffix, false);
  }

  private static String suffixWith(String receiver, String suffix, boolean ignoreCase) {
    if (receiver != null
        && !receiver.isEmpty()
        && suffix != null
        && !suffix.isEmpty()
        && !endsWith(receiver, suffix, ignoreCase)) {
      return receiver + (suffix);
    }
    return orEmpty(receiver);
  }

  public static String prefixWith(String receiver, char prefix) {
    return prefixWith(receiver, prefix, false);
  }

  static String prefixWith(String receiver, char prefix, boolean ignoreCase) {
    if (receiver != null
        && !receiver.isEmpty()
        && !startsWith(receiver, String.valueOf(prefix), ignoreCase)) {
      return prefix + receiver;
    }
    return orEmpty(receiver);
  }

  private static boolean endsWith(String receiver, String... needles) {
    return endsWith(receiver, false, needles);
  }

  private static boolean endsWith(String receiver, boolean ignoreCase, String... needles) {
    if (receiver == null) {
      return false;
    }

    if (ignoreCase) {
      for (String needle : needles) {
        if (receiver.length() >= needle.length()
            && receiver.substring(receiver.length() - needle.length()).equalsIgnoreCase(needle)) {
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

  public static String urlDecode(String receiver, String charSet) {
    try {
      return URLDecoder.decode(receiver, charSet != null ? charSet : "UTF-8");
    } catch (UnsupportedEncodingException | IllegalArgumentException e) {
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

  static boolean regionMatches(
      CharSequence receiver,
      int thisOffset,
      String other,
      int otherOffset,
      int length,
      boolean ignoreCase) {
    if (ignoreCase) {
      for (int i = 0; i < length; i++) {
        if (Character.toLowerCase(receiver.charAt(i + thisOffset))
            != Character.toLowerCase(other.charAt(i + otherOffset))) {
          return false;
        }
      }
    } else {
      for (int i = 0; i < length; i++) {
        if (receiver.charAt(i + thisOffset) != other.charAt(i + otherOffset)) {
          return false;
        }
      }
    }
    return true;
  }

  private static boolean endsWith(CharSequence receiver, String suffix, boolean ignoreCase) {
    return receiver.length() >= suffix.length()
        && regionMatches(
            receiver, receiver.length() - suffix.length(), suffix, 0, suffix.length(), ignoreCase);
  }

  private static boolean startsWith(CharSequence receiver, String prefix, boolean ignoreCase) {
    return receiver.length() >= prefix.length()
        && regionMatches(receiver, 0, prefix, 0, prefix.length(), ignoreCase);
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

  public static String splice(
      Collection<String> receiver, String delimiter, boolean skipNullOrEmpty) {
    StringBuilder result = new StringBuilder(receiver.size() * (delimiter.length() + 10));
    String delim = "";
    for (String elem : receiver) {
      if (elem != null && !elem.isEmpty() || !skipNullOrEmpty) {
        if ((!skipNullOrEmpty
            || !elem.startsWith(delimiter) && !endsWith(result.toString(), delimiter)))
          result.append(delim);
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

  public static float rangeLimit(float receiver, float minBound, float maxBound) {
    return Math.min(Math.max(receiver, minBound), maxBound);
  }

  public static int compare(Number n1, Number n2) {
    if (n1 == null && n2 == null) {
      return 0;
    } else if (n1 == null) {
      return -1;
    } else if (n2 == null) {
      return 1;
    } else if (n1 instanceof Double
        || n2 instanceof Double
        || n1 instanceof Float
        || n2 instanceof Float) {
      return Double.compare(n1.doubleValue(), n2.doubleValue());
    } else {
      return Long.compare(n1.longValue(), n2.longValue());
    }
  }

  private static void streamAppend(StringBuilder sb, InputStream inputStream) {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      while (true) {
        String line = br.readLine();
        if (line == null) {
          break;
        }

        sb.append(line).append('\n');
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getResourceAsString(Class<?> clazz, String resourcePath) {
    InputStream stream = clazz.getResourceAsStream(resourcePath);
    StringBuilder sb = new StringBuilder();
    streamAppend(sb, stream);
    return sb.toString();
  }

  public static String escapeJavaString(CharSequence param) {
    if (param == null) {
      return "null";
    }
    StringBuilder out = new StringBuilder();
    escapeJavaString(out, param);
    return out.toString();
  }

  public static void escapeJavaString(StringBuilder out, CharSequence chars) {
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

  private Utils() {
    throw new IllegalStateException();
  }
}
