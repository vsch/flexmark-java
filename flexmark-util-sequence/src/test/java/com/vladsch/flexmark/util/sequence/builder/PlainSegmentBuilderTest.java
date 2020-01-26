package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static com.vladsch.flexmark.util.misc.Utils.escapeJavaString;
import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_INCLUDE_ANCHORS;
import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_TRACK_FIRST256;
import static org.junit.Assert.assertEquals;

public class PlainSegmentBuilderTest {
    @Test
    public void test_basicBuildEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        String expected = "";

        assertEquals("PlainSegmentBuilder{NULL, s=0:0, u=0:0, t=0:0, l=0, sz=0, na=0 }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals(expected, segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_basicEmptyDefaults() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("PlainSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=0, sz=2, na=0: [0), [10) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicEmptyNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_TRACK_FIRST256);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("PlainSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=0, sz=0, na=0 }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicEmptyAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_TRACK_FIRST256 | PlainSegmentBuilder.F_INCLUDE_ANCHORS);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("PlainSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=0, sz=2, na=0: [0), [10) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicPrefix() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append("  ");
        segments.append(0, 4);

        assertEquals("PlainSegmentBuilder{[0, 4), s=1:2, u=1:2, t=1:2, l=6, sz=2, na=2: a:2x' ', [0, 4) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicPrefix1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(' ');
        segments.append(' ');
        segments.append(0, 4);

        assertEquals("PlainSegmentBuilder{[0, 4), s=1:2, u=1:2, t=1:2, l=6, sz=2, na=2: a:2x' ', [0, 4) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicPrefix2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(' ', 2);
        segments.append(0, 4);

        assertEquals("PlainSegmentBuilder{[0, 4), s=1:2, u=1:2, t=1:2, l=6, sz=2, na=2: a:2x' ', [0, 4) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicAnchorBeforeEnd() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_TRACK_FIRST256 | PlainSegmentBuilder.F_INCLUDE_ANCHORS);

        segments.append("  ");
        segments.append(0, 4);
        segments.appendAnchor(3);

        assertEquals("PlainSegmentBuilder{[0, 4), s=1:2, u=1:2, t=1:2, l=6, sz=2, na=2: a:2x' ', [0, 4) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicAnchorAtEnd() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_TRACK_FIRST256 | PlainSegmentBuilder.F_INCLUDE_ANCHORS);

        segments.append("  ");
        segments.append(0, 4);
        segments.appendAnchor(4);

        assertEquals("PlainSegmentBuilder{[0, 4), s=1:2, u=1:2, t=1:2, l=6, sz=2, na=2: a:2x' ', [0, 4) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicAnchorAfterEnd() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_TRACK_FIRST256 | PlainSegmentBuilder.F_INCLUDE_ANCHORS);

        segments.append("  ");
        segments.append(0, 4);
        segments.appendAnchor(5);

        assertEquals("PlainSegmentBuilder{[0, 5), s=1:2, u=1:2, t=1:2, l=6, sz=3, na=2: a:2x' ', [0, 4), [5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧⟦⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_appendRange1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 4);
        String expected = input.substring(0, 4);

        assertEquals("PlainSegmentBuilder{[0, 4), s=0:0, u=0:0, t=0:0, l=4, sz=1, na=1: [0, 4) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeNonOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 4);
        segments.append(6, 7);
        String expected = input.substring(0, 4) + input.substring(6, 7);

        assertEquals("PlainSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=5, sz=2, na=2: [0, 4), [6, 7) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧⟦6⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append(3, 7);
        String expected = input.substring(0, 7);

        assertEquals("PlainSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=7, sz=1, na=1: [0, 7) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlappingOverString() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
        segments.append(0, 5);
        segments.append("abc");
        segments.append(3, 7);
        String expected = input.substring(0, 5) + "abc" + input.substring(5, 7);

        assertEquals("PlainSegmentBuilder{[0, 7), s=0:0, u=1:3, t=1:3, l=10, sz=3, na=3: [0, 5), a:'abc', [5, 7) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abc⟦56⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeStrings() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append("abc");
        segments.append("def");
        String expected = input.substring(0, 5) + "abcdef";

        assertEquals("PlainSegmentBuilder{[0, 5), s=0:0, u=1:6, t=1:6, l=11, sz=3, na=2: [0, 5), a:'abcdef', [5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abcdef⟦⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeTouching() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 5);
        segments.append(5, 7);
        String expected = input.substring(0, 7);

        assertEquals("PlainSegmentBuilder{[0, 7), s=0:0, u=0:0, t=0:0, l=7, sz=1, na=1: [0, 7) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_handleOverlapDefaultChop1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(4, 8);
        assertEquals("PlainSegmentBuilder{[2, 8), s=0:0, u=1:1, t=1:1, l=7, sz=3, na=3: [2, 5), a:'-', [5, 8) }", segments.toStringPrep());
        assertEquals("234-567", segments.toString(sequence));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(1, 8);
        assertEquals("PlainSegmentBuilder{[2, 8), s=0:0, u=1:1, t=1:1, l=7, sz=3, na=3: [2, 5), a:'-', [5, 8) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 5);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=1:1, t=1:1, l=4, sz=3, na=2: [2, 5), a:'-', [5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 4);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=1:1, t=1:1, l=4, sz=3, na=2: [2, 5), a:'-', [5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 5);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=1:1, t=1:1, l=4, sz=3, na=2: [2, 5), a:'-', [5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 4);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=1:1, t=1:1, l=4, sz=3, na=2: [2, 5), a:'-', [5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(4, 8);
        assertEquals("PlainSegmentBuilder{[2, 8), s=0:0, u=0:0, t=0:0, l=6, sz=1, na=1: [2, 8) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(1, 8);
        assertEquals("PlainSegmentBuilder{[2, 8), s=0:0, u=0:0, t=0:0, l=6, sz=1, na=1: [2, 8) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(3, 5);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=0:0, t=0:0, l=3, sz=1, na=1: [2, 5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(2, 4);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=0:0, t=0:0, l=3, sz=1, na=1: [2, 5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(2, 5);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=0:0, t=0:0, l=3, sz=1, na=1: [2, 5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(2, 5);
        segments.append(3, 4);
        assertEquals("PlainSegmentBuilder{[2, 5), s=0:0, u=0:0, t=0:0, l=3, sz=1, na=1: [2, 5) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    /*
       Optimization tests, optimizer for backward compatibility
     */

    static class OptimizedSegmentBuilder2 extends PlainSegmentBuilder {
        final CharSequence myBase;
        final CharRecoveryOptimizer myOptimizer;

        public OptimizedSegmentBuilder2(CharSequence base, CharRecoveryOptimizer optimizer) {
            super(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
            myBase = base;
            myOptimizer = optimizer;
        }

        public OptimizedSegmentBuilder2(CharSequence base, CharRecoveryOptimizer optimizer, int options) {
            super(options);
            myBase = base;
            myOptimizer = optimizer;
        }

        @Override
        protected Object[] optimizeText(@NotNull Object[] parts) {
            return myOptimizer.apply(myBase, parts);
        }

        @NotNull
        public static OptimizedSegmentBuilder2 emptyBuilder(CharSequence base, CharRecoveryOptimizer optimizer) {
            return new OptimizedSegmentBuilder2(base, optimizer);
        }

        @NotNull
        public static OptimizedSegmentBuilder2 emptyBuilder(CharSequence base, CharRecoveryOptimizer optimizer, int options) {
            return new OptimizedSegmentBuilder2(base, optimizer, options);
        }
    }

    @Test
    public void test_optimizerExtendPrev1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("345");
        segments.append(6, 10);
        assertEquals("OptimizedSegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=10, sz=1, na=1: [0, 10) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendPrev2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("34 ");
        segments.append(6, 10);
        assertEquals("OptimizedSegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1:1, l=10, sz=3, na=3: [0, 5), a:' ', [6, 10) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendPrevNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("34 5");
        segments.append(6, 10);
        assertEquals("OptimizedSegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 5), a:' ', [5, 10) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendPrevNextCollapse() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("34 56");
        segments.append(7, 10);
        assertEquals("OptimizedSegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 5), a:' ', [5, 10) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append(" 3456");
        segments.append(7, 10);
        assertEquals("OptimizedSegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 3), a:' ', [3, 10) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizerExtendNext1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append(" 345");
        segments.append(6, 10);
        assertEquals("OptimizedSegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 3), a:' ', [3, 10) }", segments.toStringPrep());
    }

    @Test
    public void test_optimizerIndent1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append(" 345");
        segments.append(6, 10);
        assertEquals("OptimizedSegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1:1, l=11, sz=3, na=3: [0, 3), a:' ', [3, 10) }", segments.toStringPrep());
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
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=2, na=2: a:2x' ', [0, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersSpacesNone() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersSpacesLeft() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 5), a:2x' ', [5, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersSpacesRight() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersIndent1Left() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=2, na=2: a:2x' ', [0, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersIndent1Right() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=2, na=2: a:2x' ', [0, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL1None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL1Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL1Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL2None() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL2Left() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL2Right() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 7), a:2x' ', [7, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3LeftNonAscii() {
        String input = "01234\n……56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("34\n…………");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=0:0, u=0:0, t=1:2, l=14, sz=3, na=3: [0, 6), 2x'…', [6, 12) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizersEOL3Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=14, sz=3, na=3: [0, 6), a:2x' ', [6, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers1() {
        String input = "01234 \n56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n  ");
        segments.append(7, 12);
        assertEquals("OptimizedSegmentBuilder2{[0, 12), s=1:2, u=1:2, t=1:2, l=13, sz=4, na=4: [0, 5), [6, 7), a:2x' ', [7, 12) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers2() {
        String input = "01234 \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n");
        assertEquals("OptimizedSegmentBuilder2{[0, 7), s=0:0, u=0:0, t=0:0, l=6, sz=2, na=2: [0, 5), [6, 7) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers2a() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "01234  \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append(" \n");
        assertEquals("OptimizedSegmentBuilder2{[0, 8), s=0:0, u=0:0, t=0:0, l=7, sz=2, na=2: [0, 6), [7, 8) }", segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers3() {
        String input = "012340123401234";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("01234");
        assertEquals(escapeJavaString("OptimizedSegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=10, sz=1, na=1: [0, 10) }"), segments.toStringPrep());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_optimizers4() {
        String input = "0123  \n  5678";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer);

        segments.append(0, 5);
        segments.append("\n");
        segments.append(8, 13);
        assertEquals("OptimizedSegmentBuilder2{[0, 13), s=0:0, u=0:0, t=0:0, l=11, sz=3, na=3: [0, 5), [6, 7), [8, 13) }", segments.toStringPrep());
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
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("    ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("OptimizedSegmentBuilder2{[0, 30), s=3:6, u=3:6, t=3:6, l=34, sz=8, na=8: a:2x' ', [0, 8), [9, 10), a:2x' ', [10, 18), [19, 21), a:2x' ', [21, 30) }", escapeJavaString(segments.toStringPrep()));
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
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("OptimizedSegmentBuilder2{[0, 30), s=0:0, u=0:0, t=0:0, l=28, sz=3, na=3: [0, 8), [9, 18), [19, 30) }", escapeJavaString(segments.toStringPrep()));
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
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("OptimizedSegmentBuilder2{[0, 23), s=0:0, u=0:0, t=0:0, l=22, sz=2, na=2: [0, 13), [14, 23) }", escapeJavaString(segments.toStringPrep()));
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
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256 | OptimizedSegmentBuilder2.F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("    ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("OptimizedSegmentBuilder2{[0, 30), s=3:6, u=3:6, t=3:6, l=34, sz=8, na=8: a:2x' ', [0, 8), [9, 10), a:2x' ', [10, 18), [19, 21), a:2x' ', [21, 30) }", escapeJavaString(segments.toStringPrep()));
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
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256 | OptimizedSegmentBuilder2.F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("OptimizedSegmentBuilder2{[0, 30), s=0:0, u=0:0, t=0:0, l=28, sz=3, na=3: [0, 8), [9, 18), [19, 30) }", escapeJavaString(segments.toStringPrep()));
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
        OptimizedSegmentBuilder2 segments = OptimizedSegmentBuilder2.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256 | OptimizedSegmentBuilder2.F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("OptimizedSegmentBuilder2{[0, 23), s=0:0, u=0:0, t=0:0, l=22, sz=2, na=2: [0, 13), [14, 23) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "\n" +
                "line 3\n" +
                "", segments.toString(sequence));
    }
}

