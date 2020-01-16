package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TEST: complete tests for implementation
public class IRichSequenceBaseTest {
    @Test
    public void test_padStart() {
        assertEquals("     ", RichSequence.of("").padStart(5).toString());
        assertEquals("    a", RichSequence.of("a").padStart(5).toString());
        assertEquals("   ab", RichSequence.of("ab").padStart(5).toString());
        assertEquals("  abc", RichSequence.of("abc").padStart(5).toString());
        assertEquals(" abcd", RichSequence.of("abcd").padStart(5).toString());
        assertEquals("abcde", RichSequence.of("abcde").padStart(5).toString());
        assertEquals("abcdef", RichSequence.of("abcdef").padStart(5).toString());
    }

    @Test
    public void test_padEnd() {
        assertEquals("     ", RichSequence.of("").padEnd(5).toString());
        assertEquals("a    ", RichSequence.of("a").padEnd(5).toString());
        assertEquals("ab   ", RichSequence.of("ab").padEnd(5).toString());
        assertEquals("abc  ", RichSequence.of("abc").padEnd(5).toString());
        assertEquals("abcd ", RichSequence.of("abcd").padEnd(5).toString());
        assertEquals("abcde", RichSequence.of("abcde").padEnd(5).toString());
        assertEquals("abcdef", RichSequence.of("abcdef").padEnd(5).toString());
    }

    @Test
    public void test_padStartDash() {
        assertEquals("-----", RichSequence.of("").padStart(5, '-').toString());
        assertEquals("----a", RichSequence.of("a").padStart(5, '-').toString());
        assertEquals("---ab", RichSequence.of("ab").padStart(5, '-').toString());
        assertEquals("--abc", RichSequence.of("abc").padStart(5, '-').toString());
        assertEquals("-abcd", RichSequence.of("abcd").padStart(5, '-').toString());
        assertEquals("abcde", RichSequence.of("abcde").padStart(5, '-').toString());
        assertEquals("abcdef", RichSequence.of("abcdef").padStart(5, '-').toString());
    }

    @Test
    public void test_padEndDash() {
        assertEquals("-----", RichSequence.of("").padEnd(5, '-').toString());
        assertEquals("a----", RichSequence.of("a").padEnd(5, '-').toString());
        assertEquals("ab---", RichSequence.of("ab").padEnd(5, '-').toString());
        assertEquals("abc--", RichSequence.of("abc").padEnd(5, '-').toString());
        assertEquals("abcd-", RichSequence.of("abcd").padEnd(5, '-').toString());
        assertEquals("abcde", RichSequence.of("abcde").padEnd(5, '-').toString());
        assertEquals("abcdef", RichSequence.of("abcdef").padEnd(5, '-').toString());
    }
}
