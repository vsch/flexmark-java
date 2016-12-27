package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BasedSequenceImplTest {
    // TODO: need complete tests here

    @Test
    public void test_indexOf() throws Exception {
        final String s1 = "01234567890123456789";
        BasedSequence s = BasedSequenceImpl.of(s1);
        int iMax = s.length();

        assertEquals(s1.indexOf(' '), s.indexOf(' '));

        for (int i = 0; i < iMax; i++) {
            final char c = (char) ('0' + (i % 10));
            assertEquals("indexOf('" + c + "')", s1.indexOf(c), s.indexOf(c));
            for (int j = i; j < iMax; j++) {
                assertEquals("indexOf('" + c + "', " + j + ")", s1.indexOf(c, j), s.indexOf(c, j));
                for (int k = iMax; k-- > j; ) {
                    assertEquals("indexOf('" + c + "', " + j + ", " + k + ")", s1.substring(0, k).indexOf(c, j), s.indexOf(c, j, k));
                }
            }
        }
    }

    @Test
    public void test_lastIndexOf() throws Exception {
        final String s1 = "01234567890123456789";
        BasedSequence s = BasedSequenceImpl.of(s1);
        int iMax = s.length();

        assertEquals(s1.lastIndexOf(' '), s.lastIndexOf(' '));

        for (int i = 0; i < iMax; i++) {
            final char c = (char) ('0' + (i % 10));
            assertEquals("lastIndexOf('" + c + "')", s1.lastIndexOf(c), s.lastIndexOf(c));
            for (int j = i; j < iMax; j++) {
                assertEquals("lastIndexOf('" + c + "', " + j + ")", s1.lastIndexOf(c, j), s.lastIndexOf(c, j));
                for (int k = iMax; k-- > j; ) {
                    final int lastIndexOf = s1.lastIndexOf(c, k);
                    assertEquals("lastIndexOf('" + c + "', " + j + ", " + k + ")", lastIndexOf < j ? -1 : lastIndexOf, s.lastIndexOf(c, j, k));
                }
            }
        }
    }

    @Test
    public void test_countLeading() throws Exception {
        assertEquals("       ".length(), BasedSequenceImpl.of("       ").countLeading(" "));
        assertEquals("    ".length(), BasedSequenceImpl.of("    abcdef   ").countLeading(" "));
        assertEquals("    ".length(), BasedSequenceImpl.of("    abcdef ").countLeading(" "));
        assertEquals("    ".length(), BasedSequenceImpl.of("    abcdef").countLeading(" "));
        assertEquals(" ".length(), BasedSequenceImpl.of(" abcdef   ").countLeading(" "));
        assertEquals(" ".length(), BasedSequenceImpl.of(" abcdef ").countLeading(" "));
        assertEquals(" ".length(), BasedSequenceImpl.of(" abcdef").countLeading(" "));
        assertEquals("".length(), BasedSequenceImpl.of("abcdef   ").countLeading(" "));
        assertEquals("".length(), BasedSequenceImpl.of("abcdef ").countLeading(" "));
        assertEquals("".length(), BasedSequenceImpl.of("abcdef").countLeading(" "));
    }

    @Test
    public void test_countTrailing() throws Exception {
        assertEquals("       ".length(), BasedSequenceImpl.of("       ").countTrailing(" "));
        assertEquals("   ".length(), BasedSequenceImpl.of("    abcdef   ").countTrailing(" "));
        assertEquals(" ".length(), BasedSequenceImpl.of("    abcdef ").countTrailing(" "));
        assertEquals("".length(), BasedSequenceImpl.of("    abcdef").countTrailing(" "));
        assertEquals("   ".length(), BasedSequenceImpl.of(" abcdef   ").countTrailing(" "));
        assertEquals(" ".length(), BasedSequenceImpl.of(" abcdef ").countTrailing(" "));
        assertEquals("".length(), BasedSequenceImpl.of(" abcdef").countTrailing(" "));
        assertEquals("   ".length(), BasedSequenceImpl.of("abcdef   ").countTrailing(" "));
        assertEquals(" ".length(), BasedSequenceImpl.of("abcdef ").countTrailing(" "));
        assertEquals("".length(), BasedSequenceImpl.of("abcdef").countTrailing(" "));
    }

    @Test
    public void test_trim() throws Exception {
        assertEquals("", BasedSequenceImpl.of("       ").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("    abcdef   ").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("    abcdef ").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("    abcdef").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of(" abcdef   ").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of(" abcdef ").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of(" abcdef").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("abcdef   ").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("abcdef ").trim().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("abcdef").trim().toString());
    }

    @Test
    public void test_trimStart() throws Exception {
        assertEquals("", BasedSequenceImpl.of("       ").trimStart().toString());
        assertEquals("abcdef   ", BasedSequenceImpl.of("    abcdef   ").trimStart().toString());
        assertEquals("abcdef ", BasedSequenceImpl.of("    abcdef ").trimStart().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("    abcdef").trimStart().toString());
        assertEquals("abcdef   ", BasedSequenceImpl.of(" abcdef   ").trimStart().toString());
        assertEquals("abcdef ", BasedSequenceImpl.of(" abcdef ").trimStart().toString());
        assertEquals("abcdef", BasedSequenceImpl.of(" abcdef").trimStart().toString());
        assertEquals("abcdef   ", BasedSequenceImpl.of("abcdef   ").trimStart().toString());
        assertEquals("abcdef ", BasedSequenceImpl.of("abcdef ").trimStart().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("abcdef").trimStart().toString());
    }

    @Test
    public void test_trimEnd() throws Exception {
        assertEquals("", BasedSequenceImpl.of("       ").trimEnd().toString());
        assertEquals("    abcdef", BasedSequenceImpl.of("    abcdef   ").trimEnd().toString());
        assertEquals("    abcdef", BasedSequenceImpl.of("    abcdef ").trimEnd().toString());
        assertEquals("    abcdef", BasedSequenceImpl.of("    abcdef").trimEnd().toString());
        assertEquals(" abcdef", BasedSequenceImpl.of(" abcdef   ").trimEnd().toString());
        assertEquals(" abcdef", BasedSequenceImpl.of(" abcdef ").trimEnd().toString());
        assertEquals(" abcdef", BasedSequenceImpl.of(" abcdef").trimEnd().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("abcdef   ").trimEnd().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("abcdef ").trimEnd().toString());
        assertEquals("abcdef", BasedSequenceImpl.of("abcdef").trimEnd().toString());
    }

    @Test
    public void test_startOfDelimitedBy() throws Exception {
        assertEquals(0, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 0));
        assertEquals(0, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 1));
        assertEquals(2, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 2));
        assertEquals(2, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 3));
        assertEquals(2, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 4));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 5));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 6));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 7));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 8));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 9));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 10));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 11));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 12));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 13));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedBy(",", 14));
    }

    @Test
    public void test_startOfDelimitedByAny() throws Exception {
        assertEquals(0, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 0));
        assertEquals(0, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 1));
        assertEquals(2, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 2));
        assertEquals(2, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 3));
        assertEquals(2, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 4));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 5));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 6));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 7));
        assertEquals(5, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 8));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 9));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 10));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 11));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 12));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 13));
        assertEquals(9, BasedSequenceImpl.of("0,23,567,9012").startOfDelimitedByAny(",", 14));
    }

    @Test
    public void test_endOfDelimitedBy() throws Exception {
        assertEquals(1, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 0));
        assertEquals(1, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 1));
        assertEquals(4, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 2));
        assertEquals(4, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 3));
        assertEquals(4, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 4));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 5));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 6));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 7));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 8));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 9));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 10));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 11));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 12));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 13));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedBy(",", 14));
    }

    @Test
    public void test_endOfDelimitedByAny() throws Exception {
        assertEquals(1, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 0));
        assertEquals(1, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 1));
        assertEquals(4, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 2));
        assertEquals(4, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 3));
        assertEquals(4, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 4));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 5));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 6));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 7));
        assertEquals(8, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 8));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 9));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 10));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 11));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 12));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 13));
        assertEquals(13, BasedSequenceImpl.of("0,23,567,9012").endOfDelimitedByAny(",", 14));
    }

    @Test
    public void testSplitBasic() throws Exception {
        BasedSequence sequence = SubSequence.of(" 1,2 , 3 ,4,5,   ");
        BasedSequence[] list = sequence.split(',', 0, BasedSequence.SPLIT_TRIM_PARTS | BasedSequence.SPLIT_SKIP_EMPTY);
        ArrayList<String> sl = new ArrayList<>(list.length);
        for (BasedSequence basedSequence : list) sl.add(basedSequence.toString());

        assertArrayEquals(new String[] { "1", "2", "3", "4", "5" }, sl.toArray(new String[0]));
    }
}
