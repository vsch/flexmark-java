package com.vladsch.flexmark.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

public class UtilsTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCompareNullable() {
        Assert.assertEquals(0, Utils.compareNullable(null, false));
        Assert.assertEquals(0, Utils.compareNullable(true, true));
        Assert.assertTrue(Utils.contained(-339_763_186, new Object[]{-339_763_186}));
        Assert.assertTrue(Utils.contained(0, new int[]{0}));
        Assert.assertFalse(Utils.contained(-2_147_483_647, new Object[]{}));
        Assert.assertFalse(Utils.contained(-1_547_722_752, new Object[]{-339_763_186}));
        Assert.assertFalse(Utils.contained(0, new int[]{}));
        Assert.assertFalse(Utils.contained(0, new int[]{1}));
    }

    @Test
    public void testCount() {
        Assert.assertEquals(0, Utils.count(null, null, 0, 50));
        Assert.assertEquals(0, Utils.count(null, "a", 500, 0));
        Assert.assertEquals(0, Utils.count("teststring", null, 50, 50));
        Assert.assertEquals(1, Utils.count("teststring", "", 0, 0));
        Assert.assertEquals(0, Utils.count("teststring", "d", 0, 9));
        Assert.assertEquals(2, Utils.count("teststring", "s", 0, 50));
        Assert.assertEquals(2, Utils.count("teststring", "s", 0, 7));
        Assert.assertEquals(0, Utils.count("teststring", "S", 0, 7));
        Assert.assertEquals(0, Utils.count("teststring", " ", 0, -50));
        Assert.assertEquals(0, Utils.count("teststring", " ", 0, -30));
        Assert.assertEquals(0, Utils.count("abcdefghijklmnopqrstuvwxyz", "jk", 7, -2));
        Assert.assertEquals(1, Utils.count("abcdefghijklmnopqrs", "d", 1, 15));
        Assert.assertEquals(0, Utils.count("?", ' ', 1, 1));
        Assert.assertEquals(0, Utils.count("teststring", "s", -1, 50));

        /*
        This assertion describes a current behaviour of count that appears incorrect.
        We would expect this combination of inputs to return 0.
        */
        Assert.assertEquals(1, Utils.count("123456789", "8", 0, 3));
    }

    @Test
    public void testJoin() {
        Assert.assertEquals("prefixsuffix", Utils.join(new String[] {}, "prefix", "suffix", "$", "#"));
        Assert.assertEquals("prefixsuffix", Utils.join(new ArrayList<String>(), "prefix", "suffix", "        ", "!!!!"));
        Assert.assertEquals("itemPrefix1itemSuffixitemPrefix2itemSuffix", Utils.join(new String[] {"1", "2"}, "", "", "itemPrefix", "itemSuffix"));
        Assert.assertEquals("list#1-#2-end", Utils.join(new String[]{"1", "2"}, "list", "end", "#", "-"));
    }

    @Test
    public void testIsWhiteSpaceNoEOL() {
        Assert.assertFalse(Utils.isWhiteSpaceNoEOL("teststring"));
        Assert.assertFalse(Utils.isWhiteSpaceNoEOL("test string"));
        Assert.assertFalse(Utils.isWhiteSpaceNoEOL("\t test"));
        Assert.assertTrue(Utils.isWhiteSpaceNoEOL(""));
    }

    @Test
    public void testGetAbbreviatedText() {
        Assert.assertEquals("testString", Utils.getAbbreviatedText("testString", -2049));
        Assert.assertEquals("a", Utils.getAbbreviatedText("a", 402_667_521));
        Assert.assertEquals("abcd \u2026 j", Utils.getAbbreviatedText("abcdfeghij", 8));
        Assert.assertEquals("", Utils.getAbbreviatedText(null, -11));
    }

    @Test
    public void testIsBlank() {
        Assert.assertEquals(false, Utils.isBlank("      `a "));
        Assert.assertEquals(true, Utils.isBlank("      "));
        Assert.assertEquals(true, Utils.isBlank(null));
    }

    @Test
    public void testMaxLimit() {
        Assert.assertEquals(0.0f, Utils.maxLimit(0.0f), 0.0f);
        Assert.assertEquals(9.5f, Utils.maxLimit(10.5f, 9.5f), 0.0f);
        Assert.assertEquals(-9.5f, Utils.maxLimit(-5.5f, -9.5f), 0.0f);
        Assert.assertEquals(0.0f, Utils.maxLimit(0.0f, 10.5f), 0.0f);
    }

    @Test
    public void testOrEmpty() {
        Assert.assertEquals("   ", Utils.orEmpty("   "));
        Assert.assertEquals("", Utils.orEmpty(null));
    }

    @Test
    public void testParseIntOrNull() {
        Assert.assertEquals(new Integer(3), Utils.parseIntOrNull("3"));
        Assert.assertEquals(new Integer(8), Utils.parseIntOrNull("+8"));
        Assert.assertEquals(new Integer(8), Utils.parseIntOrNull("8"));
        Assert.assertEquals(new Integer(7), Utils.parseIntOrNull("7"));
        Assert.assertEquals(new Integer(0), Utils.parseIntOrNull("0"));
        Assert.assertEquals(null, Utils.parseIntOrNull(""));
    }

    @Test
    public void testParseUnsignedIntOrNull() {
        Assert.assertNull(Utils.parseUnsignedIntOrNull("-2"));
        Assert.assertNull(Utils.parseUnsignedIntOrNull("999999999999999999999"));
        Assert.assertEquals(new Integer(23_333_333), Utils.parseUnsignedIntOrNull("23333333"));
        Assert.assertEquals(new Integer(3), Utils.parseUnsignedIntOrNull("3"));
        Assert.assertEquals(new Integer(63), Utils.parseUnsignedIntOrNull("63"));
        Assert.assertEquals(new Integer(0), Utils.parseUnsignedIntOrNull("0"));

        /*
        This assertion describes a current behaviour of ParseUnsignedIntOrNull that appears incorrect.
        We would expect -0 to be equivalent to 0, a valid unsigned int.
        We could therefore expect this input to return Integer(0)
        */
        Assert.assertNull(Utils.parseUnsignedIntOrNull("-0"));
    }


    @Test
    public void testPrefixWith() {
        Assert.assertEquals(" teststring", Utils.prefixWith("teststring", ' ', false));
        Assert.assertEquals("_teststring", Utils.prefixWith("teststring", "_", false));
        Assert.assertEquals("", Utils.prefixWith("", "", false));
        Assert.assertEquals("", Utils.prefixWith("", ' ', false));
        Assert.assertEquals("  ", Utils.prefixWith("  ", ' ', false));
        Assert.assertEquals("a teststring", Utils.prefixWith("teststring", "a ", false));
        Assert.assertEquals("a", Utils.prefixWith("a", "a", false));
        Assert.assertEquals("", Utils.prefixWith(null, null));
        Assert.assertEquals("A", Utils.prefixWith("A", 'a', true));
        Assert.assertEquals("aA", Utils.prefixWith("A", 'a', false));
    }

    @Test
    public void testRangeLimit() {
        Assert.assertEquals(-28, Utils.rangeLimit(-33, -28, -34));
        Assert.assertEquals(-134f, Utils.rangeLimit(-149f, -134f, -0.0f), 0.0f);
        Assert.assertEquals(-75, Utils.rangeLimit(50, 10, -75));
        Assert.assertEquals(0, Utils.rangeLimit(1, 0, 0));
        Assert.assertEquals(0.0f, Utils.rangeLimit(-149f, 0.0f, 0.0f), 0.0f);
        Assert.assertEquals(0, Utils.rangeLimit(0, 0, 0));
        Assert.assertEquals(0.0f, Utils.rangeLimit(0.0f, 0.0F, 0.0F), 0.0f);
    }

    @Test
    public void testRegexGroup() {
        Assert.assertEquals("(?:AA)", Utils.regexGroup("AA"));
        Assert.assertEquals("(?:)", Utils.regexGroup(null));
    }


    @Test
    public void testRegionMatches() {
        Assert.assertTrue(Utils.regionMatches("???", 10, "?", 5, 0, false));
        Assert.assertTrue(Utils.regionMatches("!!!", 10, "!!!!!", 5, 0, true));
        Assert.assertTrue(Utils.regionMatches("!!!", 0, "a!!!b", 1, 3, true));
    }

    @Test
    public void testRemoveAnySuffix() {
        Assert.assertEquals("!!", Utils.removeAnySuffix("!!"));
        Assert.assertEquals("testString", Utils.removeAnySuffix("testString", "?"));
        Assert.assertEquals("", Utils.removeAnySuffix(null, null));
        Assert.assertEquals("testString", Utils.removeAnySuffix("testString!", "!"));
        Assert.assertEquals("testString", Utils.removeAnySuffix("testString!", "!"));
        Assert.assertEquals("testStrin!g", Utils.removeAnySuffix("testStrin!g", "!"));
        Assert.assertEquals("!testString", Utils.removeAnySuffix("!testString", "!"));
    }

    @Test
    public void testRemoveAnyPrefix() {
        Assert.assertEquals("", Utils.removeAnyPrefix(null, null));
        Assert.assertEquals("testString", Utils.removeAnyPrefix("testString"));
        Assert.assertEquals("testString", Utils.removeAnyPrefix("testString", new String[] {null}));
        Assert.assertEquals("testString!", Utils.removeAnyPrefix("testString!", "!"));
        Assert.assertEquals("testStrin!g", Utils.removeAnyPrefix("testStrin!g", "!"));
        Assert.assertEquals("testString", Utils.removeAnyPrefix("!testString", "!"));
    }


    @Test
    public void testRemovePrefix() {
        Assert.assertEquals("", Utils.removePrefixIncluding("abcdefg", "abcdefg"));
        Assert.assertEquals("abcd_", Utils.removePrefixIncluding("abcd_", "abcde"));
        Assert.assertEquals("", Utils.removePrefixIncluding(null, null));
        Assert.assertEquals(" abcdefg", Utils.removePrefix(" abcdefg", '!'));
        Assert.assertEquals("abcdefg", Utils.removePrefix(" abcdefg", ' '));
        Assert.assertEquals("A", Utils.removePrefix("A", "prefix"));
        Assert.assertEquals("", Utils.removePrefix(null, "prefix"));
        Assert.assertEquals("", Utils.removePrefix(null, null));
    }

    @Test
    public void testRemoveSuffix() {
        Assert.assertEquals("      testString", Utils.removeSuffix("      testString", ' '));
        Assert.assertEquals("      ", Utils.removeSuffix("      !", '!'));
        Assert.assertEquals("abcdefg", Utils.removeSuffix("abcdefg", "!"));
        Assert.assertEquals("!", Utils.removeSuffix("!", ""));
        Assert.assertEquals("", Utils.removeSuffix(null, "a"));
        Assert.assertEquals("", Utils.removeSuffix(null, ""));
    }

    @Test
    public void testRepeat() {
        Assert.assertEquals("", Utils.repeat("    ", 0));
        Assert.assertEquals("aaa", Utils.repeat("a", 3));
        Assert.assertEquals("", Utils.repeat("a", -5));

        thrown.expect(NullPointerException.class);
        Utils.repeat(null, 268_435_456);
    }


    @Test
    public void testStartsWith() {
        Assert.assertFalse(Utils.startsWith("??????????"));
        Assert.assertFalse(Utils.startsWith(""));
        Assert.assertFalse(Utils.startsWith("", "????"));
        Assert.assertFalse(Utils.startsWith("?", "???"));
        Assert.assertFalse(Utils.startsWith("????????", "??????????", "?????????"));
        Assert.assertFalse(Utils.startsWith(null, true, new String[] {}));


        Assert.assertTrue(Utils.startsWith("aaa???", "aaa"));
        Assert.assertTrue(Utils.startsWith("testString", "testString"));
        Assert.assertTrue(Utils.startsWith("???", "??", "???"));
        Assert.assertTrue(Utils.startsWith("????????", "????????", "?", null));
        Assert.assertTrue(Utils.startsWith("?????", "???", null, null, null, null));
        Assert.assertTrue(Utils.startsWith("Hello", "H"));
    }

    @Test
    public void testStartsWithNullPointerException1(){
        thrown.expect(NullPointerException.class);
        Utils.startsWith("?", new String[] {null});
    }

    @Test
    public void testStartsWithNullPointerException2(){
        thrown.expect(NullPointerException.class);
        Utils.startsWith("", new String[] {null});
    }

    @Test
    public void testStartsWithNullPointerException3(){
        thrown.expect(NullPointerException.class);
        Utils.startsWith("", null, " ??????");
    }

    @Test
    public void testStartsWithNullPointerException4(){
        thrown.expect(NullPointerException.class);
        Utils.startsWith("testString", null, null, null);
    }

    @Test
    public void testWrapWith() {
        Assert.assertEquals("", Utils.wrapWith("", " ", " "));
        Assert.assertEquals(" ! ", Utils.wrapWith("!", ' ', ' '));
        Assert.assertNotEquals(" ! ", Utils.wrapWith("!", " ", " "));
        Assert.assertEquals("awrappedprefix", Utils.wrapWith("a", "prefix", "wrapped"));
        Assert.assertEquals("abc1234", Utils.wrapWith("abc", "34", "12"));
        Assert.assertEquals("", Utils.wrapWith("abc", "", "123"));
        Assert.assertEquals("abc123", Utils.wrapWith("abc", "123", ""));
        Assert.assertEquals(" a ", Utils.wrapWith("a", ' '));
        Assert.assertEquals("", Utils.wrapWith("receiver", null, "suffix"));
        Assert.assertEquals("", Utils.wrapWith(null, "", ""));

        /*
        This assertion describes a current behaviour of WrapWith that appears incorrect.
        We would expect an output of prefixreceiversuffix given these inputs.
        */
        Assert.assertEquals("receiversuffixprefix", Utils.wrapWith("receiver", "prefix", "suffix"));
    }
}
