package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static com.vladsch.flexmark.util.misc.Utils.escapeJavaString;
import static com.vladsch.flexmark.util.sequence.builder.PlainSegmentBuilder.F_INCLUDE_ANCHORS;
import static com.vladsch.flexmark.util.sequence.builder.PlainSegmentBuilder.F_TRACK_FIRST256;
import static org.junit.Assert.assertEquals;

public class BasedSegmentBuilderTest {
    @Test
    public void test_basicBuildEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        String expected = "";

        assertEquals("BasedSegmentBuilder{NULL, s=0:0, u=0:0, t=0:0, l=0, sz=0, na=0 }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals(expected, segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_basicEmptyDefaults() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=0, sz=2, na=0: [0), [10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicEmptyNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, ISegmentBuilder.F_TRACK_FIRST256);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=0, sz=0, na=0 }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicEmptyAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, ISegmentBuilder.F_TRACK_FIRST256 | ISegmentBuilder.F_INCLUDE_ANCHORS);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=0, sz=2, na=0: [0), [10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicPrefixDefault() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append("  ");
        segments.append(0, 4);

        assertEquals("BasedSegmentBuilder{[0, 4), s=1:2, u=1:2, t=1:2, l=6, sz=2, na=2: a:2x' ', [0, 4) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_appendRange1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 4);
        String expected = input.substring(0, 4);

        assertEquals("BasedSegmentBuilder{[0, 4), s=0:0, u=0:0, t=0:0, l=4, sz=1, na=1: [0, 4) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeNonOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 4);
        segments.append(6, 7);
        String expected = input.substring(0, 4) + input.substring(6, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=5, sz=2, na=2: [0, 4), [6, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧⟦6⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append(3, 7);
        String expected = input.substring(0, 5) + input.substring(3, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=1:2, t=1:2, l=9, sz=3, na=3: [0, 5), a:'34', [5, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧34⟦56⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlappingOverString() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
        segments.append(0, 5);
        segments.append("abc");
        segments.append(3, 7);
        String expected = input.substring(0, 5) + "abc" + input.substring(3, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=1:5, t=1:5, l=12, sz=3, na=3: [0, 5), a:'abc34', [5, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abc34⟦56⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeStrings() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("abc");
        segments.append("def");
        String expected = input.substring(0, 5) + "abcdef";

        assertEquals("BasedSegmentBuilder{[0, 5), s=0:0, u=1:6, t=1:6, l=11, sz=3, na=2: [0, 5), a:'abcdef', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abcdef⟦⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeTouching() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append(5, 7);
        String expected = input.substring(0, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=7, sz=1, na=1: [0, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_handleOverlapDefaultChop1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(4, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:2, t=1:2, l=8, sz=3, na=3: [2, 5), a:'-4', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234-4567", segments.toString(sequence));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(1, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:5, t=1:5, l=11, sz=3, na=3: [2, 5), a:'-1234', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:3, t=1:3, l=6, sz=3, na=2: [2, 5), a:'-34', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:3, t=1:3, l=6, sz=3, na=2: [2, 5), a:'-23', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:4, t=1:4, l=7, sz=3, na=2: [2, 5), a:'-234', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:2, t=1:2, l=5, sz=3, na=2: [2, 5), a:'-3', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBefore() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 1);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:1, t=1:1, l=5, sz=3, na=2: [2, 6), a:'0', [6) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("23450", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBefore0() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 2);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:2, t=1:2, l=6, sz=3, na=2: [2, 6), a:'01', [6) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234501", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeIn() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 3);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:3, t=1:3, l=7, sz=3, na=2: [2, 6), a:'012', [6) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("2345012", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInLess1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 5);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:5, t=1:5, l=9, sz=3, na=2: [2, 6), a:'01234', [6) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234501234", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInLess0() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 6);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:6, t=1:6, l=10, sz=3, na=2: [2, 6), a:'012345', [6) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("2345012345", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInOver1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 7);
        assertEquals("BasedSegmentBuilder{[2, 7), s=0:0, u=1:6, t=1:6, l=11, sz=3, na=3: [2, 6), a:'012345', [6, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("23450123456", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInOver2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:6, t=1:6, l=12, sz=3, na=3: [2, 6), a:'012345', [6, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234501234567", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultIn0By1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(2, 3);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:1, t=1:1, l=5, sz=3, na=2: [2, 6), a:'2', [6) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("23452", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultIn0By2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(2, 4);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:2, t=1:2, l=6, sz=3, na=2: [2, 6), a:'23', [6) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234523", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapLoop() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        for (int s = 0; s < input.length(); s++) {
            for (int e = s; e < input.length(); e++) {
                BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
                segments.append(2, 6);

                segments.append(s, e);
                String expected = input.substring(2, 6) + input.substring(s, e);

                assertEquals("" + s + "," + e, expected, segments.toStringChars());
                assertEquals(expected.length(), segments.length());
            }
        }
    }

    @Test
    public void test_handleOverlapDefaultMerge1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(4, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:1, t=1:1, l=7, sz=3, na=3: [2, 5), a:'4', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(1, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:4, t=1:4, l=10, sz=3, na=3: [2, 5), a:'1234', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(3, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:2, t=1:2, l=5, sz=3, na=2: [2, 5), a:'34', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(2, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:2, t=1:2, l=5, sz=3, na=2: [2, 5), a:'23', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(2, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:3, t=1:3, l=6, sz=3, na=2: [2, 5), a:'234', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(3, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:1, t=1:1, l=4, sz=3, na=2: [2, 5), a:'3', [5) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    /*
       No Anchors
     */
    @Test
    public void test_appendRange1NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(0, 4);
        String expected = input.substring(0, 4);

        assertEquals("BasedSegmentBuilder{[0, 4), s=0:0, u=0:0, t=0:0, l=4, sz=1, na=1: [0, 4) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeNonOverlappingNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(0, 4);
        segments.append(6, 7);
        String expected = input.substring(0, 4) + input.substring(6, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=5, sz=2, na=2: [0, 4), [6, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧⟦6⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlappingNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append(3, 7);
        String expected = input.substring(0, 5) + input.substring(3, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=1:2, t=1:2, l=9, sz=3, na=3: [0, 5), a:'34', [5, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧34⟦56⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlappingOverStringNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);
        segments.append(0, 5);
        segments.append("abc");
        segments.append(3, 7);
        String expected = input.substring(0, 5) + "abc" + input.substring(3, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=1:5, t=1:5, l=12, sz=3, na=3: [0, 5), a:'abc34', [5, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abc34⟦56⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeStringsNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("abc");
        segments.append("def");
        String expected = input.substring(0, 5) + "abcdef";

        assertEquals("BasedSegmentBuilder{[0, 5), s=0:0, u=1:6, t=1:6, l=11, sz=2, na=2: [0, 5), a:'abcdef' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abcdef", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeTouchingNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append(5, 7);
        String expected = input.substring(0, 7);

        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=7, sz=1, na=1: [0, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_handleOverlapDefaultChop1NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(4, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:2, t=1:2, l=8, sz=3, na=3: [2, 5), a:'-4', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234-4567", segments.toString(sequence));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop2NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(1, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:5, t=1:5, l=11, sz=3, na=3: [2, 5), a:'-1234', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop3NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:3, t=1:3, l=6, sz=2, na=2: [2, 5), a:'-34' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop4NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:3, t=1:3, l=6, sz=2, na=2: [2, 5), a:'-23' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop5NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:4, t=1:4, l=7, sz=2, na=2: [2, 5), a:'-234' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop6NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:2, t=1:2, l=5, sz=2, na=2: [2, 5), a:'-3' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 1);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:1, t=1:1, l=5, sz=2, na=2: [2, 6), a:'0' }", escapeJavaString(segments.toStringPrep()));
        assertEquals("23450", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBefore0NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 2);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:2, t=1:2, l=6, sz=2, na=2: [2, 6), a:'01' }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234501", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 3);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:3, t=1:3, l=7, sz=2, na=2: [2, 6), a:'012' }", escapeJavaString(segments.toStringPrep()));
        assertEquals("2345012", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInLess1NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 5);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:5, t=1:5, l=9, sz=2, na=2: [2, 6), a:'01234' }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234501234", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInLess0NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 6);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:6, t=1:6, l=10, sz=2, na=2: [2, 6), a:'012345' }", escapeJavaString(segments.toStringPrep()));
        assertEquals("2345012345", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInOver1NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 7);
        assertEquals("BasedSegmentBuilder{[2, 7), s=0:0, u=1:6, t=1:6, l=11, sz=3, na=3: [2, 6), a:'012345', [6, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("23450123456", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultFromBeforeInOver2NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(0, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:6, t=1:6, l=12, sz=3, na=3: [2, 6), a:'012345', [6, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234501234567", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultIn0By1NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(2, 3);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:1, t=1:1, l=5, sz=2, na=2: [2, 6), a:'2' }", escapeJavaString(segments.toStringPrep()));
        assertEquals("23452", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultIn0By2NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 6);
        segments.append(2, 4);
        assertEquals("BasedSegmentBuilder{[2, 6), s=0:0, u=1:2, t=1:2, l=6, sz=2, na=2: [2, 6), a:'23' }", escapeJavaString(segments.toStringPrep()));
        assertEquals("234523", segments.toStringChars());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapLoopNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        for (int s = 0; s < input.length(); s++) {
            for (int e = s; e < input.length(); e++) {
                BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);
                segments.append(2, 6);

                segments.append(s, e);
                String expected = input.substring(2, 6) + input.substring(s, e);

                assertEquals("" + s + "," + e, expected, segments.toStringChars());
                assertEquals(expected.length(), segments.length());
            }
        }
    }

    @Test
    public void test_handleOverlapDefaultMerge1NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(4, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:1, t=1:1, l=7, sz=3, na=3: [2, 5), a:'4', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge2NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(1, 8);
        assertEquals("BasedSegmentBuilder{[2, 8), s=0:0, u=1:4, t=1:4, l=10, sz=3, na=3: [2, 5), a:'1234', [5, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge3NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(3, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:2, t=1:2, l=5, sz=2, na=2: [2, 5), a:'34' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge4NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(2, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:2, t=1:2, l=5, sz=2, na=2: [2, 5), a:'23' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge5NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(2, 5);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:3, t=1:3, l=6, sz=2, na=2: [2, 5), a:'234' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge6NoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(3, 4);
        assertEquals("BasedSegmentBuilder{[2, 5), s=0:0, u=1:1, t=1:1, l=4, sz=2, na=2: [2, 5), a:'3' }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }


    /*
       Optimization tests, optimizer for backward compatibility
     */

    @Test
    public void test_optimizerExtendPrev1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("345");
        segments.append(6, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=10, sz=1, na=1: [0, 10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendPrev2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("34 ");
        segments.append(6, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=1:1, u=1:1, t=1:1, l=10, sz=3, na=3: [0, 5), a:' ', [6, 10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendPrevNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("34 5");
        segments.append(6, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 5), a:' ', [5, 10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendPrevNextCollapse() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("34 56");
        segments.append(7, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 5), a:' ', [5, 10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append(" 3456");
        segments.append(7, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 3), a:' ', [3, 10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendNext1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append(" 345");
        segments.append(6, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 3), a:' ', [3, 10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerIndent1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append(" 345");
        segments.append(6, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 3), a:' ', [3, 10) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    /*
     * Optimizer tests to ensure all optimizations are handled properly
     */

    @Test
    public void test_optimizersIndent1None() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=2, na=2: a:2x' ', [0, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersSpacesNone() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersSpacesLeft() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 5), a:2x' ', [5, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersSpacesRight() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersIndent1Left() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=2, na=2: a:2x' ', [0, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersIndent1Right() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=2, na=2: a:2x' ', [0, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL1None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL1Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL1Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL2None() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL2Left() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL2Right() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3LeftNonAscii() {
        String input = "01234\n……56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("34\n…………");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=0:0, u=0:0, t=1:2, l=14, sz=3, na=3: [0, 6), 2x'…', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers1() {
        String input = "01234 \n56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n  ");
        segments.append(7, 12);
        assertEquals("BasedSegmentBuilder{[0, 12), s=1:2, u=1:2, t=1:2, l=13, sz=4, na=4: [0, 5), [6, 7), a:2x' ', [7, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers2() {
        String input = "01234 \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n");
        assertEquals("BasedSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=6, sz=2, na=2: [0, 5), [6, 7) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers2a() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "01234  \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append(" \n");
        assertEquals("BasedSegmentBuilder{[0, 8), s=0:0, u=0:0, t=0:0, l=7, sz=2, na=2: [0, 6), [7, 8) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers3() {
        String input = "012340123401234";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("01234");
        assertEquals(escapeJavaString("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=10, sz=1, na=1: [0, 10) }"), escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers4() {
        String input = "0123  \n  5678";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("\n");
        segments.append(8, 13);
        assertEquals("BasedSegmentBuilder{[0, 13), s=0:0, u=0:0, t=0:0, l=11, sz=3, na=3: [0, 5), [6, 7), [8, 13) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersCompoundNoAnchors1() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("    ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("BasedSegmentBuilder{[0, 30), s=3:6, u=3:6, t=3:6, l=34, sz=8, na=8: a:2x' ', [0, 8), [9, 10), a:2x' ', [10, 18), [19, 21), a:2x' ', [21, 30) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        assertEquals("" +
                "    line 1\n" +
                "    line 2\n" +
                "\n" +
                "    line 3\n" +
                "", segments.toString(sequence));
    }

    @Test
    public void test_optimizersCompoundNoAnchors2() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("BasedSegmentBuilder{[0, 30), s=0:0, u=0:0, t=0:0, l=28, sz=3, na=3: [0, 8), [9, 18), [19, 30) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        assertEquals("" +
                "  line 1\n" +
                "  line 2\n" +
                "\n" +
                "  line 3\n" +
                "", segments.toString(sequence));
    }

    @Test
    public void test_optimizersCompoundNoAnchors3() {
        String input = "" +
                "line 1\n" +
                "line 2 \n" +
                "\n" +
                "line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("BasedSegmentBuilder{[0, 23), s=0:0, u=0:0, t=0:0, l=22, sz=2, na=2: [0, 13), [14, 23) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "\n" +
                "line 3\n" +
                "", segments.toString(sequence));
    }

    @Test
    public void test_optimizersCompoundAnchors1() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("    ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }

        assertEquals("BasedSegmentBuilder{[0, 30), s=3:6, u=3:6, t=3:6, l=34, sz=8, na=8: a:2x' ', [0, 8), [9, 10), a:2x' ', [10, 18), [19, 21), a:2x' ', [21, 30) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        assertEquals("" +
                "    line 1\n" +
                "    line 2\n" +
                "\n" +
                "    line 3\n" +
                "", segments.toString(sequence));
    }

    @Test
    public void test_optimizersCompoundAnchors2() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("BasedSegmentBuilder{[0, 30), s=0:0, u=0:0, t=0:0, l=28, sz=3, na=3: [0, 8), [9, 18), [19, 30) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        assertEquals("" +
                "  line 1\n" +
                "  line 2\n" +
                "\n" +
                "  line 3\n" +
                "", segments.toString(sequence));
    }

    @Test
    public void test_optimizersCompound3Anchors() {
        String input = "" +
                "line 1\n" +
                "line 2 \n" +
                "\n" +
                "line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("BasedSegmentBuilder{[0, 23), s=0:0, u=0:0, t=0:0, l=22, sz=2, na=2: [0, 13), [14, 23) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "\n" +
                "line 3\n" +
                "", segments.toString(sequence));
    }

    // ************************************************************************
    // CAUTION: BasedSegmentBuilder Unique Test, Not in Segment Builder Tests
    //   Do NOT blow away if synchronizing the two test files
    // ************************************************************************

    @Test
    public void test_extractRangesDefault() {
        String input = "0123456789";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        // NOTE: test from BasedSequenceImpl which is fragile and depends on segment builder working 100%
        // BasedSequence replaced = sequence.extractRanges(Range.of(0, 0), Range.of(0, 1), Range.of(3, 6), Range.of(8, 12));
        segments.append(Range.of(0, 0));
        segments.append(Range.of(0, 1));
        segments.append(Range.of(3, 6));
        segments.append(Range.of(8, 10));
        assertEquals(escapeJavaString("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=6, sz=3, na=3: [0, 1), [3, 6), [8, 10) }"), escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("034589", segments.toString(sequence));
    }

    @Test
    public void test_extractRangesAnchors() {
        String input = "0123456789";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, ISegmentBuilder.F_TRACK_FIRST256 | ISegmentBuilder.F_INCLUDE_ANCHORS);

        // NOTE: test from BasedSequenceImpl which is fragile and depends on segment builder working 100%
        // BasedSequence replaced = sequence.extractRanges(Range.of(0, 0), Range.of(0, 1), Range.of(3, 6), Range.of(8, 12));
        segments.append(Range.of(0, 0));
        segments.append(Range.of(0, 1));
        segments.append(Range.of(3, 6));
        segments.append(Range.of(8, 10));
        assertEquals(escapeJavaString("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=6, sz=3, na=3: [0, 1), [3, 6), [8, 10) }"), escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("034589", segments.toString(sequence));
    }

    @Test
    public void test_extractRangesNoAnchors() {
        String input = "0123456789";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, ISegmentBuilder.F_TRACK_FIRST256);

        // NOTE: test from BasedSequenceImpl which is fragile and depends on segment builder working 100%
        // BasedSequence replaced = sequence.extractRanges(Range.of(0, 0), Range.of(0, 1), Range.of(3, 6), Range.of(8, 12));
        segments.append(Range.of(0, 0));
        segments.append(Range.of(0, 1));
        segments.append(Range.of(3, 6));
        segments.append(Range.of(8, 10));
        assertEquals(escapeJavaString("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=6, sz=3, na=3: [0, 1), [3, 6), [8, 10) }"), escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("034589", segments.toString(sequence));
    }

    @Test
    public void test_replacePrefixDefault() {
        String input = "0123456789";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        // NOTE: test from BasedSequenceImpl which is fragile and depends on segment builder working 100%
        // BasedSequence replaced = sequence.replace(0, 1, "^");
        // assertEquals("^123456789", replaced.toString());

        segments.append(Range.of(0, 0));
        segments.append("^");
        segments.append(Range.of(1, 10));
        assertEquals(escapeJavaString("BasedSegmentBuilder{[0, 10), s=0:0, u=1:1, t=1:1, l=10, sz=3, na=2: [0), a:'^', [1, 10) }"), escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("^123456789", segments.toString(sequence));
    }

    @Test
    public void test_replacePrefixAnchors() {
        String input = "0123456789";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, ISegmentBuilder.F_TRACK_FIRST256 | ISegmentBuilder.F_INCLUDE_ANCHORS);

        // NOTE: test from BasedSequenceImpl which is fragile and depends on segment builder working 100%
        // BasedSequence replaced = sequence.replace(0, 1, "^");
        // assertEquals("^123456789", replaced.toString());

        segments.append(Range.of(0, 0));
        segments.append("^");
        segments.append(Range.of(1, 10));
        assertEquals(escapeJavaString("BasedSegmentBuilder{[0, 10), s=0:0, u=1:1, t=1:1, l=10, sz=3, na=2: [0), a:'^', [1, 10) }"), escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("^123456789", segments.toString(sequence));
    }

    @Test
    public void test_replacePrefixNoAnchors() {
        String input = "0123456789";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, ISegmentBuilder.F_TRACK_FIRST256);

        // NOTE: test from BasedSequenceImpl which is fragile and depends on segment builder working 100%
        // BasedSequence replaced = sequence.replace(0, 1, "^");
        // assertEquals("^123456789", replaced.toString());

        segments.append(Range.of(0, 0));
        segments.append("^");
        segments.append(Range.of(1, 10));
        assertEquals(escapeJavaString("BasedSegmentBuilder{[0, 10), s=0:0, u=1:1, t=1:1, l=10, sz=2, na=2: a:'^', [1, 10) }"), escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("^123456789", segments.toString(sequence));
    }
}

