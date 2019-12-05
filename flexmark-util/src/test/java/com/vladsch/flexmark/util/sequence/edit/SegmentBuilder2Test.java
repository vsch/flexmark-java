package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static com.vladsch.flexmark.util.Utils.escapeJavaString;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.toVisibleWhitespaceString;
import static org.junit.Assert.assertEquals;

public class SegmentBuilder2Test {
    @Test
    public void test_basicBuildEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        String expected = "";

        assertEquals("SegmentBuilder2{NULL, s=0:0, u=0:0, t=0:0, l=0 }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals(expected, segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_basicEmptyDefaults() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("SegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=0, [0), [10) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicEmptyNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("SegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=0 }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicEmptyAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256 | SegmentBuilder2.F_INCLUDE_ANCHORS);
        segments.append(0, 0);
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("SegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=0, [0), [10) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_basicPrefix() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append("  ");
        segments.append(0, 4);

        assertEquals("SegmentBuilder2{[0, 4), s=1:2, u=1:2, t=1:2, l=6, '  ', [0, 4) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicAnchorBeforeEnd() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256 | SegmentBuilder2.F_INCLUDE_ANCHORS);

        segments.append("  ");
        segments.append(0, 4);
        segments.appendAnchor(3);

        assertEquals("SegmentBuilder2{[0, 4), s=1:2, u=1:2, t=1:2, l=6, '  ', [0, 4) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicAnchorAtEnd() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256 | SegmentBuilder2.F_INCLUDE_ANCHORS);

        segments.append("  ");
        segments.append(0, 4);
        segments.appendAnchor(4);

        assertEquals("SegmentBuilder2{[0, 4), s=1:2, u=1:2, t=1:2, l=6, '  ', [0, 4) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_basicAnchorAfterEnd() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256 | SegmentBuilder2.F_INCLUDE_ANCHORS);

        segments.append("  ");
        segments.append(0, 4);
        segments.appendAnchor(5);

        assertEquals("SegmentBuilder2{[0, 5), s=1:2, u=1:2, t=1:2, l=6, '  ', [0, 4), [5) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("  ⟦0123⟧⟦⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals("  0123", segments.toString(sequence));
    }

    @Test
    public void test_appendRange1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(0, 4);
        String expected = input.substring(0, 4);

        assertEquals("SegmentBuilder2{[0, 4), s=0:0, u=0:0, t=0:0, l=4, [0, 4) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeNonOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(0, 4);
        segments.append(6, 7);
        String expected = input.substring(0, 4) + input.substring(6, 7);

        assertEquals("SegmentBuilder2{[0, 7), s=0:0, u=0:0, t=0:0, l=5, [0, 4), [6, 7) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123⟧⟦6⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(0, 5);
        segments.append(3, 7);
        String expected = input.substring(0, 7);

        assertEquals("SegmentBuilder2{[0, 7), s=0:0, u=0:0, t=0:0, l=7, [0, 7) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlappingOverString() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
        segments.append(0, 5);
        segments.append("abc");
        segments.append(3, 7);
        String expected = input.substring(0, 5) + "abc" + input.substring(5, 7);

        assertEquals("SegmentBuilder2{[0, 7), s=0:0, u=1:3, t=1:3, l=10, [0, 5), 'abc', [5, 7) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abc⟦56⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeStrings() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(0, 5);
        segments.append("abc");
        segments.append("def");
        String expected = input.substring(0, 5) + "abcdef";

        assertEquals("SegmentBuilder2{[0, 5), s=0:0, u=1:6, t=1:6, l=11, [0, 5), 'abcdef' }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦01234⟧abcdef", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeTouching() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(0, 5);
        segments.append(5, 7);
        String expected = input.substring(0, 7);

        assertEquals("SegmentBuilder2{[0, 7), s=0:0, u=0:0, t=0:0, l=7, [0, 7) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRangesVisibleWhitespace(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_handleOverlapDefaultChop1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(4, 8);
        assertEquals("SegmentBuilder2{[2, 8), s=0:0, u=1:1, t=1:1, l=7, [2, 5), '-', [5, 8) }", segments.toString());
        assertEquals("234-567", segments.toString(sequence));
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(1, 8);
        assertEquals("SegmentBuilder2{[2, 8), s=0:0, u=1:1, t=1:1, l=7, [2, 5), '-', [5, 8) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 5);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=1:1, t=1:1, l=4, [2, 5), '-' }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 4);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=1:1, t=1:1, l=4, [2, 5), '-' }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 5);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=1:1, t=1:1, l=4, [2, 5), '-' }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultChop6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 4);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=1:1, t=1:1, l=4, [2, 5), '-' }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append(4, 8);
        assertEquals("SegmentBuilder2{[2, 8), s=0:0, u=0:0, t=0:0, l=6, [2, 8) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append(1, 8);
        assertEquals("SegmentBuilder2{[2, 8), s=0:0, u=0:0, t=0:0, l=6, [2, 8) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append(3, 5);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=0:0, t=0:0, l=3, [2, 5) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append(2, 4);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=0:0, t=0:0, l=3, [2, 5) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append(2, 5);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=0:0, t=0:0, l=3, [2, 5) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

    @Test
    public void test_handleOverlapDefaultMerge6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();

        segments.append(2, 5);
        segments.append(3, 4);
        assertEquals("SegmentBuilder2{[2, 5), s=0:0, u=0:0, t=0:0, l=3, [2, 5) }", segments.toString());
        assertEquals(segments.toString(sequence).length(), segments.length());
    }

//    /*
//       Optimization tests, optimizer for backward compatibility
//     */
//
//    @Test
//    public void test_optimizerExtendPrev1() {
//        String input = "0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//
//        segments.append(0, 3);
//        segments.append("345");
//        segments.append(6, 10);
//        assertEquals("SegmentBuilder2{[0, 10), s=0:0, u=3:3, t=3, l=10, [0, 3), [3, 6, '345'), [6, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=10, [0, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizerExtendPrev2() {
//        String input = "0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//
//        segments.append(0, 3);
//        segments.append("34 ");
//        segments.append(6, 10);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=3:3, t=3, l=10, [0, 3), [3, 6, '34 '), [6, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1, l=10, [0, 5), [5, 6, ' '), [6, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizerExtendPrevNext() {
//        String input = "0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//
//        segments.append(0, 3);
//        segments.append("34 5");
//        segments.append(6, 10);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=4:4, t=4, l=11, [0, 3), [3, 6, '34 5'), [6, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1, l=11, [0, 5), [5, ' '), [5, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizerExtendPrevNextCollapse() {
//        String input = "0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//        BasedSequenceBuilder builder = BasedSequenceBuilder.emptyBuilder(sequence);
//
//        segments.append(0, 3);
//        segments.append("34 56");
//        segments.append(7, 10);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=5:5, t=5, l=11, [0, 3), [3, 7, '34 56'), [7, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1, l=11, [0, 5), [5, ' '), [5, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizerExtendNext() {
//        String input = "0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//
//        segments.append(0, 3);
//        segments.append(" 3456");
//        segments.append(7, 10);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=5:5, t=5, l=11, [0, 3), [3, 7, ' 3456'), [7, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1, l=11, [0, 3), [3, ' '), [3, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizerExtendNext1() {
//        String input = "0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//
//        segments.append(0, 3);
//        segments.append(" 345");
//        segments.append(6, 10);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=4:4, t=4, l=11, [0, 3), [3, 6, ' 345'), [6, 10) }", segments.toString());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1, l=11, [0, 3), [3, ' '), [3, 10) }", segments.toString());
//    }
//
//    @Test
//    public void test_optimizerIndent1() {
//        String input = "0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//
//        segments.append(0, 3);
//        segments.append(" 345");
//        segments.append(6, 10);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=4:4, t=4, l=11, [0, 3), [3, 6, ' 345'), [6, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 10), s=1:1, u=1:1, t=1, l=11, [0, 3), [3, ' '), [3, 10) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    /*
//     * Optimizer tests to ensure all optimizations are handled properly
//     */
//
//    @Test
//    public void test_optimizersIndent1None() {
//        String input = "  0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append("    ");
//        segments.append(2, 12);
//        assertEquals("SegmentBuilder2{[2, 12), s=4:4, u=1:4, t=4, l=14, [2, '    '), [2, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, '  '), [0, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersSpacesNone() {
//        String input = "01234  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("    ");
//        segments.append(7, 12);
//        assertEquals("SegmentBuilder2{[0, 12), s=4:4, u=1:4, t=4, l=14, [0, 5), [5, 7, '    '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 6), [6, '  '), [6, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersSpacesLeft() {
//        String input = "01234  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("    ");
//        segments.append(7, 12);
//        assertEquals("SegmentBuilder2{[0, 12), s=4:4, u=1:4, t=4, l=14, [0, 5), [5, 7, '    '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 5), [5, '  '), [5, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersSpacesRight() {
//        String input = "01234  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("    ");
//        segments.append(7, 12);
//        assertEquals("SegmentBuilder2{[0, 12), s=4:4, u=1:4, t=4, l=14, [0, 5), [5, 7, '    '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 7), [7, '  '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersIndent1Left() {
//        String input = "  0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append("    ");
//        segments.append(2, 12);
//        assertEquals("SegmentBuilder2{[2, 12), s=4:4, u=1:4, t=4, l=14, [2, '    '), [2, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, '  '), [0, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersIndent1Right() {
//        String input = "  0123456789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append("    ");
//        segments.append(2, 12);
//        assertEquals("SegmentBuilder2{[2, 12), s=4:4, u=1:4, t=4, l=14, [2, '    '), [2, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, '  '), [0, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL1None() {
//        String input = "01234\n  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n    ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=4:4, u=2:5, t=5, l=14, [0, 5), [5, 8, '\n    '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 6), [6, '  '), [6, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL1Left() {
//        String input = "01234\n  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n    ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=4:4, u=2:5, t=5, l=14, [0, 5), [5, 8, '\n    '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 6), [6, '  '), [6, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL1Right() {
//        String input = "01234\n  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n    ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=4:4, u=2:5, t=5, l=14, [0, 5), [5, 8, '\n    '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 6), [6, '  '), [6, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL2None() {
//        String input = "01234\n\n 56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n\n   ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=3:3, u=2:5, t=5, l=14, [0, 5), [5, 8, '\n\n   '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 7), [7, '  '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL2Left() {
//        String input = "01234\n\n 56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n\n   ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=3:3, u=2:5, t=5, l=14, [0, 5), [5, 8, '\n\n   '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 7), [7, '  '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL2Right() {
//        String input = "01234\n\n 56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n\n   ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=3:3, u=2:5, t=5, l=14, [0, 5), [5, 8, '\n\n   '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 7), [7, '  '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL3None() {
//        String input = "01234\n  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 3);
//        segments.append("34\n    ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=4:4, u=4:7, t=7, l=14, [0, 3), [3, 8, '34\n    '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 6), [6, '  '), [6, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL3Left() {
//        String input = "01234\n  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 3);
//        segments.append("34\n    ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=4:4, u=4:7, t=7, l=14, [0, 3), [3, 8, '34\n    '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 6), [6, '  '), [6, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersEOL3Right() {
//        String input = "01234\n  56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 3);
//        segments.append("34\n    ");
//        segments.append(8, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=4:4, u=4:7, t=7, l=14, [0, 3), [3, 8, '34\n    '), [8, 12) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=14, [0, 6), [6, '  '), [6, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizers1() {
//        String input = "01234 \n56789";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n  ");
//        segments.append(7, 12);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 12), s=2:2, u=2:3, t=3, l=13, [0, 5), [5, 7, '\n  '), [7, 12) }"), segments.toString());
//        assertEquals(escapeJavaString("01234\n  56789"), toVisibleWhitespaceString(segments.toString(sequence)));
//        String string = segments.toString(sequence);
//        int length = string.length();
//        assertEquals(string.length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 12), s=2:2, u=1:2, t=2, l=13, [0, 5), [6, 7), [7, '  '), [7, 12) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizers2() {
//        String input = "01234 \n";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n");
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 5), s=0:0, u=1:1, t=1, l=6, [0, 5), [5, '\n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 7), s=0:0, u=0:0, t=0:0, l=6, [0, 5), [6, 7) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizers2a() {
//        // this one causes text to be replaced with recovered EOL in the code
//        String input = "01234  \n";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append(" \n");
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 5), s=1:1, u=2:2, t=2, l=7, [0, 5), [5, ' \n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 8), s=0:0, u=0:0, t=0:0, l=7, [0, 6), [7, 8) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizers3() {
//        String input = "012340123401234";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("01234");
//        assertEquals("SegmentBuilder2{[0, 5), s=0:0, u=5:5, t=5, l=10, [0, 5), [5, '01234') }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 10), s=0:0, u=0:0, t=0:0, l=10, [0, 10) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizers4() {
//        String input = "0123  \n  5678";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder();
//
//        segments.append(0, 5);
//        segments.append("\n");
//        segments.append(8, 13);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 13), s=0:0, u=1:1, t=1, l=11, [0, 5), [5, 8, '\n'), [8, 13) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("SegmentBuilder2{[0, 13), s=0:0, u=0:0, t=0:0, l=11, [0, 5), [6, 7), [8, 13) }", segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//    }
//
//    @Test
//    public void test_optimizersCompoundNoAnchors1() {
//        String input = "" +
//                "  line 1 \n" +
//                "  line 2 \n" +
//                "\n" +
//                "  line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("    ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals(escapeJavaString("SegmentBuilder2{[2, 29), s=12:12, u=2:16, t=16, l=34, [2, '    '), [2, 8), [8, 12, '\n    '), [12, 18), [18, 23, '\n\n    '), [23, 29), [29, '\n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
////        assertEquals(escapeJavaString("SegmentBuilder2{end=30, parts='  ', [0, 8), '\n  ', [10, 18), [19, 21), '  ', [21, 30) }"), segments.toString());
//
//        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n⟧\\n  ⟦  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "    line 1\n" +
//                "    line 2\n" +
//                "\n" +
//                "    line 3\n" +
//                "", segments.toString(sequence));
//    }
//
//    @Test
//    public void test_optimizersCompoundNoAnchors2() {
//        String input = "" +
//                "  line 1 \n" +
//                "  line 2 \n" +
//                "\n" +
//                "  line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals(escapeJavaString("SegmentBuilder2{[2, 29), s=6:6, u=2:10, t=10, l=28, [2, '  '), [2, 8), [8, 12, '\n  '), [12, 18), [18, 23, '\n\n  '), [23, 29), [29, '\n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 30), s=0:0, u=0:0, t=0:0, l=28, [0, 8), [9, 18), [19, 30) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "  line 1\n" +
//                "  line 2\n" +
//                "\n" +
//                "  line 3\n" +
//                "", segments.toString(sequence));
//    }
//
//    @Test
//    public void test_optimizersCompoundNoAnchors3() {
//        String input = "" +
//                "line 1\n" +
//                "line 2 \n" +
//                "\n" +
//                "line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
////            if (!trim.isEmpty()) segments.append("  ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 22), s=0:0, u=1:4, t=4, l=22, [0, 6), [6, 7, '\n'), [7, 13), [13, 16, '\n\n'), [16, 22), [22, '\n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 23), s=0:0, u=0:0, t=0:0, l=22, [0, 13), [14, 23) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "line 1\n" +
//                "line 2\n" +
//                "\n" +
//                "line 3\n" +
//                "", segments.toString(sequence));
//    }
//
//    @Test
//    public void test_optimizersCompoundAnchors1() {
//        String input = "" +
//                "  line 1 \n" +
//                "  line 2 \n" +
//                "\n" +
//                "  line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256 | SegmentBuilder2.F_INCLUDE_ANCHORS);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("    ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals(escapeJavaString("SegmentBuilder2{[2, 29), s=12:12, u=2:16, t=16, l=34, [2, '    '), [2, 8), [8, 12, '\n    '), [12, 18), [18, 20, '\n'), [20), [20, 23, '\n    '), [23, 29), [29, '\n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "    line 1\n" +
//                "    line 2\n" +
//                "\n" +
//                "    line 3\n" +
//                "", segments.toString(sequence));
//    }
//
//    @Test
//    public void test_optimizersCompoundAnchors2() {
//        String input = "" +
//                "  line 1 \n" +
//                "  line 2 \n" +
//                "\n" +
//                "  line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256 | SegmentBuilder2.F_INCLUDE_ANCHORS);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals(escapeJavaString("SegmentBuilder2{[2, 29), s=6:6, u=2:10, t=10, l=28, [2, '  '), [2, 8), [8, 12, '\n  '), [12, 18), [18, 20, '\n'), [20), [20, 23, '\n  '), [23, 29), [29, '\n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 30), s=0:0, u=0:0, t=0:0, l=28, [0, 8), [9, 18), [19, 30) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "  line 1\n" +
//                "  line 2\n" +
//                "\n" +
//                "  line 3\n" +
//                "", segments.toString(sequence));
//    }
//
//    @Test
//    public void test_optimizersCompound3Anchors() {
//        String input = "" +
//                "line 1\n" +
//                "line 2 \n" +
//                "\n" +
//                "line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
//        SegmentBuilder2 segments = SegmentBuilder2.emptyBuilder(SegmentBuilder2.F_TRACK_FIRST256 | SegmentBuilder2.F_INCLUDE_ANCHORS);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
////            if (!trim.isEmpty()) segments.append("  ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 22), s=0:0, u=1:4, t=4, l=22, [0, 6), [6, 7, '\n'), [7, 13), [13, 15, '\n'), [15), [15, 16, '\n'), [16, 22), [22, '\n') }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        segments.optimizeFor(sequence, optimizer);
//        assertEquals(escapeJavaString("SegmentBuilder2{[0, 23), s=0:0, u=0:0, t=0:0, l=22, [0, 13), [14, 23) }"), segments.toString());
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "line 1\n" +
//                "line 2\n" +
//                "\n" +
//                "line 3\n" +
//                "", segments.toString(sequence));
//    }
}

