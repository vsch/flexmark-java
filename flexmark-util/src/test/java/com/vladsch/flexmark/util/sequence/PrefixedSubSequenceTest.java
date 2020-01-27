package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

final public class PrefixedSubSequenceTest {
    final private BasedSequence sequence = BasedSequence.of("abcdefghi").subSequence(3, 6);
    final private BasedSequence substring = PrefixedSubSequence.prefixOf("0123", sequence);

    @Test
    public void testLength() {
        assertEquals(7, substring.length());
    }

    @Test
    public void testCharAt() {
        assertEquals('0', substring.charAt(0));
        assertEquals('1', substring.charAt(1));
        assertEquals('2', substring.charAt(2));
        assertEquals('3', substring.charAt(3));
        assertEquals('d', substring.charAt(4));
        assertEquals('e', substring.charAt(5));
        assertEquals('f', substring.charAt(6));
    }

    @Test
    public void testSubSequence() {
        assertEquals("0", substring.subSequence(0, 1).toString());
        assertEquals("1", substring.subSequence(1, 2).toString());
        assertEquals("2", substring.subSequence(2, 3).toString());
        assertEquals("3", substring.subSequence(3, 4).toString());
        assertEquals("d", substring.subSequence(4, 5).toString());
        assertEquals("e", substring.subSequence(5, 6).toString());
        assertEquals("f", substring.subSequence(6, 7).toString());
        assertEquals("0123", substring.subSequence(0, 4).toString());
        assertEquals("0123def", substring.subSequence(0, 7).toString());
        assertEquals("3de", substring.subSequence(3, 6).toString());
        assertEquals("def", substring.subSequence(4, 7).toString());
    }
}
