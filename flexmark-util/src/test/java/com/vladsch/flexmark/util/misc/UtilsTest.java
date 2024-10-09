package com.vladsch.flexmark.util.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilsTest {
  @Test
  public void testIsWhiteSpaceNoEOL() {
    assertFalse(Utils.isWhiteSpaceNoEOL("teststring"));
    assertFalse(Utils.isWhiteSpaceNoEOL("test string"));
    assertFalse(Utils.isWhiteSpaceNoEOL("\t test"));
    assertTrue(Utils.isWhiteSpaceNoEOL(""));
  }

  @Test
  public void testOrEmpty() {
    assertEquals("   ", Utils.orEmpty("   "));
    assertEquals("", Utils.orEmpty(null));
  }

  @Test
  public void testPrefixWith() {
    assertEquals(" teststring", Utils.prefixWith("teststring", ' ', false));
    assertEquals("", Utils.prefixWith("", ' ', false));
    assertEquals("  ", Utils.prefixWith("  ", ' ', false));
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
  public void testRegionMatches() {
    assertTrue(Utils.regionMatches("???", 10, "?", 5, 0, false));
    assertTrue(Utils.regionMatches("!!!", 10, "!!!!!", 5, 0, true));
    assertTrue(Utils.regionMatches("!!!", 0, "a!!!b", 1, 3, true));
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
}
