package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        assertEquals(0, BasedSequenceImpl.of("       ").countLeading(""));
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
        assertEquals(0, BasedSequenceImpl.of("       ").countTrailing(""));
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
        ArrayList<String> sl = new ArrayList<String>(list.length);
        for (BasedSequence basedSequence : list) sl.add(basedSequence.toString());

        assertArrayEquals(new String[] { "1", "2", "3", "4", "5" }, sl.toArray(new String[0]));
    }

    @Test
    public void testPrefixOf() {
        BasedSequence of = BasedSequenceImpl.of("123");
        assertEquals(of.subSequence(0, 0), of.baseSubSequence(0, 0).prefixOf(of.subSequence(0, 0)));
        assertEquals(of.subSequence(0, 0), of.baseSubSequence(0, 0).prefixOf(of.subSequence(0, 1)));

        assertEquals(of.subSequence(0, 0), of.baseSubSequence(0, 1).prefixOf(of.subSequence(0, 1)));
        assertEquals(of.subSequence(0, 1), of.baseSubSequence(0, 1).prefixOf(of.subSequence(1, 1)));

        assertEquals(of.subSequence(0, 1), of.baseSubSequence(0, 3).prefixOf(of.subSequence(1, 1)));

        assertEquals(of.subSequence(0, 1), of.baseSubSequence(0, 2).prefixOf(of.subSequence(1, 2)));
        assertEquals(of.subSequence(0, 1), of.baseSubSequence(0, 1).prefixOf(of.subSequence(1, 1)));
    }

    @Test
    public void testSuffixOf() {
        BasedSequence of = BasedSequenceImpl.of("123");
        assertEquals(of.subSequence(3, 3), of.baseSubSequence(3, 3).suffixOf(of.subSequence(3, 3)));
        assertEquals(of.subSequence(3, 3), of.baseSubSequence(3, 3).suffixOf(of.subSequence(2, 3)));

        assertEquals(of.subSequence(3, 3), of.baseSubSequence(2, 3).suffixOf(of.subSequence(2, 3)));
        assertEquals(of.subSequence(2, 3), of.baseSubSequence(2, 3).suffixOf(of.subSequence(2, 2)));

        assertEquals(of.subSequence(1, 3), of.baseSubSequence(0, 3).suffixOf(of.subSequence(1, 1)));

        assertEquals(of.subSequence(2, 2), of.baseSubSequence(0, 2).suffixOf(of.subSequence(1, 2)));
        assertEquals(of.subSequence(1, 1), of.baseSubSequence(0, 1).suffixOf(of.subSequence(1, 1)));
    }

    @Test
    public void testReplace() {
        BasedSequence text = BasedSequenceImpl.of("[foo]");
        assertEquals(BasedSequenceImpl.of("[bar]"), text.replace("foo", "bar"));
        assertEquals(BasedSequenceImpl.of("[foo]"), text.replace("food", "bars"));
        assertEquals(BasedSequenceImpl.of("(foo]"), text.replace("[", "("));
        assertEquals(BasedSequenceImpl.of("[foo)"), text.replace("]", ")"));
    }

    @Test
    public void test_getLineColumnAtIndex() {
        String[] lines = new String[] {
                "1: line 1\n",
                "2: line 2\n",
                "3: line 3\r",
                "4: line 4\r\n",
                "5: line 5\r",
                "6: line 6"
        };

        int iMax = lines.length;

        int[] lineStarts = new int[iMax + 1];
        int[] lineEnds = new int[iMax];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iMax; i++) {
            lineStarts[i] = len;
            String line = lines[i].replaceAll("\r|\n", "");
            lineEnds[i] = len + line.length();
            len += lines[i].length();
            sb.append(lines[i]);
        }

        lineStarts[iMax] = len;

        int jMax = len;
        List<Pair<Integer, Integer>> info = new ArrayList<>(jMax);

        for (int j = 0; j < jMax; j++) {
            for (int i = 0; i < iMax; i++) {
                if (j >= lineStarts[i] && j < lineStarts[i + 1]) {
                    int col = j - lineStarts[i];
                    int line = i;
                    if (j > lineEnds[i]) {
                        col = 0;
                        line++;
                    }
                    info.add(new Pair<>(line, col));
                    //System.out.println(String.format("%d: [%d, %d]", j, line, col));
                    break;
                }
            }
        }

        assertEquals(jMax, info.size());

        BasedSequence text = BasedSequenceImpl.of(sb.toString());

        for (int j = 0; j < jMax; j++) {
            final Pair<Integer, Integer> atIndex = text.getLineColumnAtIndex(j);
            if (!info.get(j).equals(atIndex)) {
                text.getLineColumnAtIndex(j);
            }
            assertEquals("Failed at " + j, info.get(j), atIndex);
        }
    }

    @Test
    public void test_trimEndTo() {
        assertEquals("", BasedSequenceImpl.of("").trimEnd(0, ".\t").toString());
        assertEquals("", BasedSequenceImpl.of(".").trimEnd(0, ".\t").toString());
        assertEquals("", BasedSequenceImpl.of("..").trimEnd(0, ".\t").toString());
        assertEquals(".", BasedSequenceImpl.of("..").trimEnd(1, ".\t").toString());
        assertEquals(".", BasedSequenceImpl.of(".").trimEnd(1, ".\t").toString());
        assertEquals(".", BasedSequenceImpl.of(".").trimEnd(2, ".\t").toString());
        assertEquals("abc", BasedSequenceImpl.of("abc").trimEnd(0, ".\t").toString());
        assertEquals(".abc", BasedSequenceImpl.of(".abc").trimEnd(0, ".\t").toString());
        assertEquals("..abc", BasedSequenceImpl.of("..abc").trimEnd(0, ".\t").toString());
        assertEquals("..abc", BasedSequenceImpl.of("..abc.").trimEnd(0, ".\t").toString());
        assertEquals("..abc", BasedSequenceImpl.of("..abc..").trimEnd(0, ".\t").toString());
        assertEquals("..abc", BasedSequenceImpl.of("..abc..").trimEnd(4, ".\t").toString());
        assertEquals("..abc", BasedSequenceImpl.of("..abc..").trimEnd(5, ".\t").toString());
        assertEquals("..abc.", BasedSequenceImpl.of("..abc....").trimEnd(6, ".\t").toString());
        assertEquals("..abc..", BasedSequenceImpl.of("..abc....").trimEnd(7, ".\t").toString());
        assertEquals("..abc...", BasedSequenceImpl.of("..abc....").trimEnd(8, ".\t").toString());
    }

    @Test
    public void test_trimStartTo() {
        assertEquals("", BasedSequenceImpl.of("").trimStart(0, ".\t").toString());
        assertEquals("", BasedSequenceImpl.of(".").trimStart(0, ".\t").toString());
        assertEquals("", BasedSequenceImpl.of("..").trimStart(0, ".\t").toString());
        assertEquals(".", BasedSequenceImpl.of("..").trimStart(1, ".\t").toString());
        assertEquals(".", BasedSequenceImpl.of(".").trimStart(1, ".\t").toString());
        assertEquals(".", BasedSequenceImpl.of(".").trimStart(2, ".\t").toString());
        assertEquals("..", BasedSequenceImpl.of("...").trimStart(2, ".\t").toString());
        assertEquals("..", BasedSequenceImpl.of("....").trimStart(2, ".\t").toString());
        assertEquals("abc", BasedSequenceImpl.of("abc").trimStart(0, ".\t").toString());
        assertEquals("abc.", BasedSequenceImpl.of("abc.").trimStart(0, ".\t").toString());
        assertEquals("abc..", BasedSequenceImpl.of(".abc..").trimStart(0, ".\t").toString());
        assertEquals("abc..", BasedSequenceImpl.of("..abc..").trimStart(0, ".\t").toString());
        assertEquals("abc..", BasedSequenceImpl.of(".abc..").trimStart(0, ".\t").toString());
        assertEquals("abc..", BasedSequenceImpl.of(".abc..").trimStart(4, ".\t").toString());
        assertEquals("abc..", BasedSequenceImpl.of(".abc..").trimStart(5, ".\t").toString());
        assertEquals(".abc..", BasedSequenceImpl.of("..abc..").trimStart(6, ".\t").toString());
        assertEquals("..abc..", BasedSequenceImpl.of("...abc..").trimStart(7, ".\t").toString());
        assertEquals("...abc..", BasedSequenceImpl.of("...abc..").trimStart(8, ".\t").toString());
    }

    @Test
    public void test_indexOfAll() throws Exception {
        final String s1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        BasedSequence s = BasedSequenceImpl.of(s1);

        int[] indices = s.indexOfAll("a");

        assertEquals(33, indices.length);

        for (int i = 0; i < indices.length; i++) {
            assertEquals(i, indices[i]);
        }
    }

    @Test
    public void test_indexOfAll2() throws Exception {
        final String s1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        BasedSequence s = BasedSequenceImpl.of(s1);

        int[] indices = s.indexOfAll("a");

        assertEquals(66, indices.length);

        for (int i = 0; i < indices.length; i++) {
            assertEquals(i, indices[i]);
        }
    }

    @Test
    public void test_extendByOneOfAny() {
        BasedSequence b = BasedSequenceImpl.of("this;.,\n");
        BasedSequence s = b.subSequence(0, 4);

        assertEquals("this", s.extendByOneOfAny("").toString());
        assertEquals("this", s.extendByOneOfAny("-*").toString());
        assertEquals("this;", s.extendByOneOfAny(";").toString());
        assertEquals("this;", s.extendByOneOfAny(".;").toString());
        assertEquals("this;", s.extendByOneOfAny(",.;").toString());
        assertEquals("this;", s.extendByOneOfAny("\n,.;").toString());
    }

    @Test
    public void test_extendByAny() {
        BasedSequence b = BasedSequenceImpl.of("this;.,\n");
        BasedSequence s = b.subSequence(0, 4);

        assertEquals("this", s.extendByAny("").toString());
        assertEquals("this", s.extendByAny("-*").toString());
        assertEquals("this;", s.extendByAny(";").toString());
        assertEquals("this;.", s.extendByAny(".;").toString());
        assertEquals("this;.,", s.extendByAny(",.;").toString());
        assertEquals("this;.,\n", s.extendByAny("\n,.;").toString());
    }

    @Test
    public void test_extendByAny2() {
        BasedSequence b = BasedSequenceImpl.of("this;.,\n");
        BasedSequence s = b.subSequence(0, 4);

        assertEquals("this", s.extendByAny("", 2).toString());
        assertEquals("this", s.extendByAny("-*", 2).toString());
        assertEquals("this;", s.extendByAny(";", 2).toString());
        assertEquals("this;.", s.extendByAny(".;", 2).toString());
        assertEquals("this;.", s.extendByAny(",.;", 2).toString());
        assertEquals("this;.", s.extendByAny("\n,.;", 2).toString());
    }

    @Test
    public void test_extendToAny() {
        BasedSequence b = BasedSequenceImpl.of("this;.,\n");
        BasedSequence s = b.subSequence(0, 4);

        assertEquals("this", s.extendToAny("").toString());
        assertEquals("this", s.extendToAny("-*").toString());
        assertEquals("this;", s.extendToAny(";").toString());
        assertEquals("this;", s.extendToAny(".;").toString());
        assertEquals("this;.", s.extendToAny(".").toString());
        assertEquals("this;.", s.extendToAny(".,").toString());
        assertEquals("this;.", s.extendToAny(",.").toString());
        assertEquals("this;.", s.extendToAny(",.").toString());
        assertEquals("this;.,", s.extendToAny(",").toString());
        assertEquals("this;", s.extendToAny("\n,.;").toString());
        assertEquals("this;.", s.extendToAny("\n,.").toString());
        assertEquals("this;.,", s.extendToAny("\n,").toString());
    }

    @Test
    public void test_prefixWithIndent() {
        assertEquals("test\n", BasedSequenceImpl.of("\ntest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals(" test\n", BasedSequenceImpl.of("\n test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("  test\n", BasedSequenceImpl.of("\n  test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("   test\n", BasedSequenceImpl.of("\n   test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("    test\n", BasedSequenceImpl.of("\n    test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("     test\n", BasedSequenceImpl.of("\n     test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("      test\n", BasedSequenceImpl.of("\n      test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
    }
    @Test
    public void test_prefixWithIndent0() {
        assertEquals("test\n", BasedSequenceImpl.of("\ntest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n  test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n   test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n    test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n     test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n      test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
    }
    @Test
    public void test_prefixWithIndent4() {
        assertEquals("test\n", BasedSequenceImpl.of("\ntest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals(" test\n", BasedSequenceImpl.of("\n test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals("  test\n", BasedSequenceImpl.of("\n  test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals("   test\n", BasedSequenceImpl.of("\n   test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals("    test\n", BasedSequenceImpl.of("\n    test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals("    test\n", BasedSequenceImpl.of("\n     test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals("    test\n", BasedSequenceImpl.of("\n      test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
    }
    @Test
    public void test_prefixWithIndentTabs() {
        assertEquals("\ttest\n", BasedSequenceImpl.of("\n\ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals(" \ttest\n", BasedSequenceImpl.of("\n \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("  \ttest\n", BasedSequenceImpl.of("\n  \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("   \ttest\n", BasedSequenceImpl.of("\n   \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("    \ttest\n", BasedSequenceImpl.of("\n    \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("     \ttest\n", BasedSequenceImpl.of("\n     \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("      \ttest\n", BasedSequenceImpl.of("\n      \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("\ttest\n", BasedSequenceImpl.of("\n\ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("\t test\n", BasedSequenceImpl.of("\n\t test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("\t  test\n", BasedSequenceImpl.of("\n\t  test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("\t   test\n", BasedSequenceImpl.of("\n\t   test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("\t    test\n", BasedSequenceImpl.of("\n\t    test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("\t     test\n", BasedSequenceImpl.of("\n\t     test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
        assertEquals("\t      test\n", BasedSequenceImpl.of("\n\t      test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent().toString());
    }

    @Test
    public void test_prefixWithIndentTabs0() {
        assertEquals("test\n", BasedSequenceImpl.of("\n\ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n  \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n   \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n    \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n     \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n      \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n\ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n\t test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n\t  test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n\t   test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n\t    test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n\t     test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n\t      test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(0).toString());
    }
    @Test
    public void test_prefixWithIndentTabs1to8() {
        assertEquals("test\n", BasedSequenceImpl.of("\n\ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(1).toString());
        assertEquals("test\n", BasedSequenceImpl.of("\n \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(2).toString());
        assertEquals(" \ttest\n", BasedSequenceImpl.of("\n  \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(3).toString());
        assertEquals("   \ttest\n", BasedSequenceImpl.of("\n   \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals(" \ttest\n", BasedSequenceImpl.of("\n    \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(5).toString());
        assertEquals("   \ttest\n", BasedSequenceImpl.of("\n     \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(6).toString());
        assertEquals("     \ttest\n", BasedSequenceImpl.of("\n      \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(7).toString());
        assertEquals("       \ttest\n", BasedSequenceImpl.of("\n       \ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(8).toString());

        assertEquals("test\n", BasedSequenceImpl.of("\n\ttest\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(1).toString());
        assertEquals(" test\n", BasedSequenceImpl.of("\n\t test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(2).toString());
        assertEquals("  test\n", BasedSequenceImpl.of("\n\t  test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(3).toString());
        assertEquals("   test\n", BasedSequenceImpl.of("\n\t   test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(4).toString());
        assertEquals("    test\n", BasedSequenceImpl.of("\n\t    test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(5).toString());
        assertEquals("     test\n", BasedSequenceImpl.of("\n\t     test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(6).toString());
        assertEquals("      test\n", BasedSequenceImpl.of("\n\t      test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(7).toString());
        assertEquals("       test\n", BasedSequenceImpl.of("\n\t       test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(8).toString());
        assertEquals("\t    test\n", BasedSequenceImpl.of("\n\t    test\n").trimStart(BasedSequence.WHITESPACE_CHARS).prefixWithIndent(8).toString());
    }
}
