package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestUtilsTest {

    @Test
    public void test_insertCaretMarkup() {
        assertEquals("No markup", TestUtils.insertCaretMarkup(BasedSequence.of("No markup"), new int[] { }).toSequence().toString());
        assertEquals("With ⦙markup", TestUtils.insertCaretMarkup(BasedSequence.of("With markup"), new int[] { 5 }).toSequence().toString());
        assertEquals("With ⦙markups⦙", TestUtils.insertCaretMarkup(BasedSequence.of("With markups"), new int[] { 5, 12 }).toSequence().toString());
        assertEquals("With ⦙mark⦙ups⦙", TestUtils.insertCaretMarkup(BasedSequence.of("With markups"), new int[] { 5, 12, 9 }).toSequence().toString());
    }

    static String pairToString(Pair<? extends CharSequence, int[]> pair) {
        return "(" + SequenceUtils.toVisibleWhitespaceString(pair.getFirst().toString()) + ", " + Arrays.toString(pair.getSecond()) + ")";
    }

    @Test
    public void test_extractMarkup() {
        assertEquals(pairToString(Pair.of("No markup", new int[] { })), pairToString(TestUtils.extractMarkup(BasedSequence.of("No markup"))));
        assertEquals(pairToString(Pair.of("With markup", new int[] { 5 })), pairToString(TestUtils.extractMarkup(BasedSequence.of("With ⦙markup"))));
        assertEquals(pairToString(Pair.of("With markups", new int[] { 5, 12 })), pairToString(TestUtils.extractMarkup(BasedSequence.of("With ⦙markups⦙"))));
        assertEquals(pairToString(Pair.of("With markups", new int[] { 5, 9, 12 })), pairToString(TestUtils.extractMarkup(BasedSequence.of("With ⦙mark⦙ups⦙"))));
        assertEquals(pairToString(Pair.of("With markups\nMore markups", new int[] { 5, 9, 12, 18, 22, 25 })), pairToString(TestUtils.extractMarkup(BasedSequence.of("With ⦙mark⦙ups⦙\nMore ⦙mark⦙ups⦙"))));
        assertEquals(pairToString(Pair.of("With markups", new int[] { 5, 9, 12 })), pairToString(TestUtils.extractMarkup(BasedSequence.of("⟦⟧With ⦙mark⦙ups⦙"))));
        assertEquals(pairToString(Pair.of("With markups", new int[] { 7, 11, 14 })), pairToString(TestUtils.extractMarkup(BasedSequence.of("⟦  ⟧With ⦙mark⦙ups⦙"))));
        assertEquals(pairToString(Pair.of("With markups\nMore markups", new int[] { 7, 11, 14, 22, 26, 29 })), pairToString(TestUtils.extractMarkup(BasedSequence.of("⟦  ⟧With ⦙mark⦙ups⦙\n⟦  ⟧More ⦙mark⦙ups⦙"))));
    }
}
