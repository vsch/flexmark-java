package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import static com.vladsch.flexmark.util.misc.CharPredicate.SPACE;
import static org.junit.Assert.assertEquals;

public class SequenceUtilsTest {

    @Test
    public void test_lastIndexOfAnyNot() {
        assertEquals(15, SequenceUtils.lastIndexOfAnyNot("0123456789  2345", SPACE, 0, 17));
        assertEquals(15, SequenceUtils.lastIndexOfAnyNot("0123456789  2345", SPACE, 0, 16));
        assertEquals(15, SequenceUtils.lastIndexOfAnyNot("0123456789  2344", SPACE, 0, 15));
        assertEquals(14, SequenceUtils.lastIndexOfAnyNot("0123456789  2343", SPACE, 0, 14));
        assertEquals(13, SequenceUtils.lastIndexOfAnyNot("0123456789  2342", SPACE, 0, 13));
        assertEquals(12, SequenceUtils.lastIndexOfAnyNot("0123456789  2342", SPACE, 0, 12));
        assertEquals(9, SequenceUtils.lastIndexOfAnyNot("0123456789  2342", SPACE, 0, 11));
    }

    @Test
    public void test_countTrailing() {
        assertEquals(0, SequenceUtils.countTrailing("0123456789  2345", SPACE, 0, 17));
        assertEquals(0, SequenceUtils.countTrailing("0123456789  2345", SPACE, 0, 16));
        assertEquals(0, SequenceUtils.countTrailing("0123456789  2344", SPACE, 0, 15));
        assertEquals(0, SequenceUtils.countTrailing("0123456789  2343", SPACE, 0, 14));
        assertEquals(0, SequenceUtils.countTrailing("0123456789  2342", SPACE, 0, 13));
        assertEquals(2, SequenceUtils.countTrailing("0123456789  2342", SPACE, 0, 12));
        assertEquals(1, SequenceUtils.countTrailing("0123456789  2342", SPACE, 0, 11));
        assertEquals(0, SequenceUtils.countTrailing("0123456789  2342", SPACE, 0, 10));
    }
}
