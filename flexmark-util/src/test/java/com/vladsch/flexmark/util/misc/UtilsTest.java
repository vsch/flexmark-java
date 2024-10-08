package com.vladsch.flexmark.util.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {
  @Test
  public void testCompareNullable() {
    assertEquals(0, Utils.compareNullable(null, false));
    assertEquals(0, Utils.compareNullable(true, true));
  }

  @Test
  public void testCount() {
    assertEquals(0, Utils.count(null, null, 0, 50));
    assertEquals(0, Utils.count(null, "a", 500, 0));
    assertEquals(0, Utils.count("teststring", null, 50, 50));
    assertEquals(1, Utils.count("teststring", "", 0, 0));
    assertEquals(0, Utils.count("teststring", "d", 0, 9));
    assertEquals(2, Utils.count("teststring", "s", 0, 50));
    assertEquals(2, Utils.count("teststring", "s", 0, 7));
    assertEquals(0, Utils.count("teststring", "S", 0, 7));
    assertEquals(0, Utils.count("teststring", " ", 0, -50));
    assertEquals(0, Utils.count("teststring", " ", 0, -30));
    assertEquals(0, Utils.count("abcdefghijklmnopqrstuvwxyz", "jk", 7, -2));
    assertEquals(1, Utils.count("abcdefghijklmnopqrs", "d", 1, 15));
    assertEquals(0, Utils.count("?", ' ', 1, 1));
    assertEquals(0, Utils.count("teststring", "s", -1, 50));

    assertEquals(0, Utils.count("123456789", "8", 0, 3));
  }

  @Test
  public void testJoin() {
    assertEquals("prefixsuffix", Utils.join(new String[] {}, "prefix", "suffix", "$", "#"));
    assertEquals(
        "prefixsuffix", Utils.join(new ArrayList<>(), "prefix", "suffix", "        ", "!!!!"));
    assertEquals(
        "itemPrefix1itemSuffixitemPrefix2itemSuffix",
        Utils.join(new String[] {"1", "2"}, "", "", "itemPrefix", "itemSuffix"));
    assertEquals("list#1-#2-end", Utils.join(new String[] {"1", "2"}, "list", "end", "#", "-"));
  }

  @Test
  public void testIsWhiteSpaceNoEOL() {
    assertFalse(Utils.isWhiteSpaceNoEOL("teststring"));
    assertFalse(Utils.isWhiteSpaceNoEOL("test string"));
    assertFalse(Utils.isWhiteSpaceNoEOL("\t test"));
    assertTrue(Utils.isWhiteSpaceNoEOL(""));
  }

  @Test
  public void testGetAbbreviatedText() {
    assertEquals("testString", Utils.getAbbreviatedText("testString", -2049));
    assertEquals("a", Utils.getAbbreviatedText("a", 402_667_521));
    assertEquals("abcd \u2026 j", Utils.getAbbreviatedText("abcdfeghij", 8));
    assertEquals("", Utils.getAbbreviatedText(null, -11));
  }

  @Test
  public void testIsBlank() {
    assertFalse(Utils.isBlank("      `a "));
    assertTrue(Utils.isBlank("      "));
    assertTrue(Utils.isBlank(null));
  }

  @Test
  public void testMaxLimit() {
    assertEquals(0.0f, Utils.maxLimit(0.0f), 0.0f);
    assertEquals(9.5f, Utils.maxLimit(10.5f, 9.5f), 0.0f);
    assertEquals(-9.5f, Utils.maxLimit(-5.5f, -9.5f), 0.0f);
    assertEquals(0.0f, Utils.maxLimit(0.0f, 10.5f), 0.0f);
  }

  @Test
  public void testOrEmpty() {
    assertEquals("   ", Utils.orEmpty("   "));
    assertEquals("", Utils.orEmpty(null));
  }

  @Test
  public void testPrefixWith() {
    assertEquals(" teststring", Utils.prefixWith("teststring", ' ', false));
    assertEquals("_teststring", Utils.prefixWith("teststring", "_", false));
    assertEquals("", Utils.prefixWith("", "", false));
    assertEquals("", Utils.prefixWith("", ' ', false));
    assertEquals("  ", Utils.prefixWith("  ", ' ', false));
    assertEquals("a teststring", Utils.prefixWith("teststring", "a ", false));
    assertEquals("a", Utils.prefixWith("a", "a", false));
    assertEquals("", Utils.prefixWith(null, null));
    assertEquals("A", Utils.prefixWith("A", 'a', true));
    assertEquals("aA", Utils.prefixWith("A", 'a', false));
  }

  @Test
  public void testRangeLimit() {
    assertEquals(-33, Utils.rangeLimit(-33, -34, -28));
    assertEquals(-134f, Utils.rangeLimit(-149f, -134f, -0.0f), 0.0f);
    assertEquals(-75, Utils.rangeLimit(50, 10, -75));
    assertEquals(0, Utils.rangeLimit(1, 0, 0));
    assertEquals(0.0f, Utils.rangeLimit(-149f, 0.0f, 0.0f), 0.0f);
    assertEquals(0, Utils.rangeLimit(0, 0, 0));
    assertEquals(0.0f, Utils.rangeLimit(0.0f, 0.0F, 0.0F), 0.0f);
  }

  @Test
  public void testRegexGroup() {
    assertEquals("(?:AA)", Utils.regexGroup("AA"));
    assertEquals("(?:)", Utils.regexGroup(null));
  }

  @Test
  public void testRegionMatches() {
    assertTrue(Utils.regionMatches("???", 10, "?", 5, 0, false));
    assertTrue(Utils.regionMatches("!!!", 10, "!!!!!", 5, 0, true));
    assertTrue(Utils.regionMatches("!!!", 0, "a!!!b", 1, 3, true));
  }

  @Test
  public void testRemoveAnySuffix() {
    assertEquals("!!", Utils.removeAnySuffix("!!"));
    assertEquals("testString", Utils.removeAnySuffix("testString", "?"));
    assertEquals("", Utils.removeAnySuffix(null, (String) null));
    assertEquals("testString", Utils.removeAnySuffix("testString!", "!"));
    assertEquals("testString", Utils.removeAnySuffix("testString!", "!"));
    assertEquals("testStrin!g", Utils.removeAnySuffix("testStrin!g", "!"));
    assertEquals("!testString", Utils.removeAnySuffix("!testString", "!"));
  }

  @Test
  public void testRemoveAnyPrefix() {
    assertEquals("", Utils.removeAnyPrefix(null, (String) null));
    assertEquals("testString", Utils.removeAnyPrefix("testString"));
    assertEquals("testString", Utils.removeAnyPrefix("testString", new String[] {null}));
    assertEquals("testString!", Utils.removeAnyPrefix("testString!", "!"));
    assertEquals("testStrin!g", Utils.removeAnyPrefix("testStrin!g", "!"));
    assertEquals("testString", Utils.removeAnyPrefix("!testString", "!"));
  }

  @Test
  public void testRemovePrefix() {
    assertEquals("", Utils.removePrefixIncluding("abcdefg", "abcdefg"));
    assertEquals("abcd_", Utils.removePrefixIncluding("abcd_", "abcde"));
    assertEquals("", Utils.removePrefixIncluding(null, null));
    assertEquals(" abcdefg", Utils.removePrefix(" abcdefg", '!'));
    assertEquals("abcdefg", Utils.removePrefix(" abcdefg", ' '));
    assertEquals("A", Utils.removePrefix("A", "prefix"));
    assertEquals("", Utils.removePrefix(null, "prefix"));
    assertEquals("", Utils.removePrefix(null, null));
  }

  @Test
  public void testRemoveSuffix() {
    assertEquals("      testString", Utils.removeSuffix("      testString", ' '));
    assertEquals("      ", Utils.removeSuffix("      !", '!'));
    assertEquals("abcdefg", Utils.removeSuffix("abcdefg", "!"));
    assertEquals("!", Utils.removeSuffix("!", ""));
    assertEquals("", Utils.removeSuffix(null, "a"));
    assertEquals("", Utils.removeSuffix(null, ""));
  }

  @Test
  public void testRepeat() {
    assertEquals("", "    ".repeat(0));
    assertEquals("aaa", "a".repeat(3));
  }

  @Test
  public void testStartsWith() {
    assertFalse(Utils.startsWith("??????????"));
    assertFalse(Utils.startsWith(""));
    assertFalse(Utils.startsWith("", "????"));
    assertFalse(Utils.startsWith("?", "???"));
    assertFalse(Utils.startsWith("????????", "??????????", "?????????"));
    assertFalse(Utils.startsWith(null, true));

    assertTrue(Utils.startsWith("aaa???", "aaa"));
    assertTrue(Utils.startsWith("testString", "testString"));
    assertTrue(Utils.startsWith("???", "??", "???"));
    assertTrue(Utils.startsWith("????????", "????????", "?", null));
    assertTrue(Utils.startsWith("?????", "???", null, null, null, null));
    assertTrue(Utils.startsWith("Hello", "H"));
  }

  @Test
  public void testStartsWithNullPointerException1() {
    Assert.assertThrows(
        NullPointerException.class, () -> Utils.startsWith("?", new String[] {null}));
  }

  @Test
  public void testStartsWithNullPointerException2() {
    Assert.assertThrows(
        NullPointerException.class, () -> Utils.startsWith("", new String[] {null}));
  }

  @Test
  public void testStartsWithNullPointerException3() {
    Assert.assertThrows(NullPointerException.class, () -> Utils.startsWith("", null, " ??????"));
  }

  @Test
  public void testStartsWithNullPointerException4() {
    Assert.assertThrows(
        NullPointerException.class, () -> Utils.startsWith("testString", null, null, null));
  }

  @Test
  public void testWrapWith() {
    assertEquals("", Utils.wrapWith("", " ", " "));
    assertEquals(" ! ", Utils.wrapWith("!", ' ', ' '));
    assertEquals(" ! ", Utils.wrapWith("!", " ", " "));
    assertEquals("prefixawrapped", Utils.wrapWith("a", "prefix", "wrapped"));
    assertEquals("34abc12", Utils.wrapWith("abc", "34", "12"));
    assertEquals("abc123", Utils.wrapWith("abc", "", "123"));
    assertEquals("123abc", Utils.wrapWith("abc", "123", ""));
    assertEquals(" a ", Utils.wrapWith("a", ' '));
    assertEquals("receiversuffix", Utils.wrapWith("receiver", null, "suffix"));
    assertEquals("", Utils.wrapWith(null, "", ""));
    assertEquals("prefixreceiversuffix", Utils.wrapWith("receiver", "prefix", "suffix"));
  }
}
