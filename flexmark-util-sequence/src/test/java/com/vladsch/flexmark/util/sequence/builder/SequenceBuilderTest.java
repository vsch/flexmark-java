package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_INCLUDE_ANCHORS;
import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_TRACK_FIRST256;
import static org.junit.Assert.assertEquals;

public class SequenceBuilderTest {
    @Test
    public void test_appendRangeDefault() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(Range.of(0, 0));
        builder.append(Range.of(0, 4));
        builder.append(Range.of(10, 10));
        String expected = input.substring(0, 4);

        assertEquals("⟦0123⟧⟦⟧", builder.toStringWithRanges());
        assertEquals("0123", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendRangeAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        builder.append(Range.of(0, 0));
        builder.append(Range.of(0, 4));
        builder.append(Range.of(10, 10));
        String expected = input.substring(0, 4);

        assertEquals("⟦0123⟧⟦⟧", builder.toStringWithRanges());
        assertEquals("0123", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendRangeNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        builder.append(Range.of(0, 0));
        builder.append(Range.of(0, 4));
        builder.append(Range.of(10, 10));
        String expected = input.substring(0, 4);

        assertEquals("⟦0123⟧", builder.toStringWithRanges());
        assertEquals("0123", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendSubSequenceDefault() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(sequence.subSequence(0, 0));
        builder.append(sequence.subSequence(0, 4));
        builder.append(sequence.subSequence(10, 10));
        String expected = input.substring(0, 4);

        assertEquals("⟦0123⟧⟦⟧", builder.toStringWithRanges());
        assertEquals("0123", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendSubSequenceAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        builder.append(sequence.subSequence(0, 0));
        builder.append(sequence.subSequence(0, 4));
        builder.append(sequence.subSequence(10, 10));
        String expected = input.substring(0, 4);

        assertEquals("⟦0123⟧⟦⟧", builder.toStringWithRanges());
        assertEquals("0123", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendSubSequenceNoAnchors() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_TRACK_FIRST256);

        builder.append(sequence.subSequence(0, 0));
        builder.append(sequence.subSequence(0, 4));
        builder.append(sequence.subSequence(10, 10));
        String expected = input.substring(0, 4);

        assertEquals("⟦0123⟧", builder.toStringWithRanges());
        assertEquals("0123", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendRange1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(Range.of(0, 4));
        String expected = input.substring(0, 4);

        assertEquals("⟦0123⟧", builder.toStringWithRanges());
        assertEquals("0123", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendRangeNonOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(0, 4);
        builder.append(6, 7);

        assertEquals("⟦0123⟧⟦6⟧", builder.toStringWithRanges());
        assertEquals("01236", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendRangeTouching() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(Range.of(0, 5));
        builder.append(Range.of(5, 7));
        assertEquals("⟦0123456⟧", builder.toStringWithRanges());
        assertEquals("0123456", builder.toSequence().toString());
    }

    @Test
    public void test_appendRangeOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(0, 5);
        builder.append(3, 7);
        assertEquals("⟦01234⟧34⟦56⟧", builder.toStringWithRanges());
        assertEquals("012343456", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendRangeOverlappingOverString() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(0, 5);
        builder.append("abc");
        builder.append(3, 7);
        assertEquals("⟦01234⟧abc34⟦56⟧", builder.toStringWithRanges());
        assertEquals("01234abc3456", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_appendRangeStrings() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(0, 5);
        builder.append("abc");
        builder.append("def");
        assertEquals("⟦01234⟧abcdef⟦⟧", builder.toStringWithRanges());
        assertEquals("01234abcdef", builder.toSequence().toVisibleWhitespaceString());
    }

    /*
       Optimization tests, optimizer for backward compatibility
     */

    @Test
    public void test_optimizerExtendPrev1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append("345");
        builder.append(6, 10);
        assertEquals("⟦0123456789⟧", builder.toStringWithRanges());
        assertEquals("0123456789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizerExtendPrev2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append("34 ");
        builder.append(6, 10);
        assertEquals("⟦01234⟧ ⟦6789⟧", builder.toStringWithRanges());
        assertEquals("01234 6789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizerExtendPrevNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append("34 5");
        builder.append(6, 10);
        assertEquals("⟦01234⟧ ⟦56789⟧", builder.toStringWithRanges());
        assertEquals("01234 56789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizerExtendPrevNextCollapse() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append("34 56");
        builder.append(7, 10);
        assertEquals("⟦01234⟧ ⟦56789⟧", builder.toStringWithRanges());
        assertEquals("01234 56789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizerExtendNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append(" 3456");
        builder.append(7, 10);
        assertEquals("⟦012⟧ ⟦3456789⟧", builder.toStringWithRanges());
        assertEquals("012 3456789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizerExtendNext1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append(" 345");
        builder.append(6, 10);
        assertEquals("⟦012⟧ ⟦3456789⟧", builder.toStringWithRanges());
        assertEquals("012 3456789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizerIndent1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append(" 345");
        builder.append(6, 10);
        assertEquals("⟦012⟧ ⟦3456789⟧", builder.toStringWithRanges());
        assertEquals("012 3456789", builder.toSequence().toVisibleWhitespaceString());
    }

    /*
     * Optimizer tests to ensure all optimizations are handled properly
     */

    @Test
    public void test_optimizersIndent1None() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append("    ");
        builder.append(2, 12);
        assertEquals("  ⟦  0123456789⟧", builder.toStringWithRanges());
        assertEquals("    0123456789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersSpacesNone() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("    ");
        builder.append(7, 12);
        assertEquals("⟦01234 ⟧  ⟦ 56789⟧", builder.toStringWithRanges());
        assertEquals("01234    56789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersSpacesLeft() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("    ");
        builder.append(7, 12);
        assertEquals("⟦01234⟧  ⟦  56789⟧", builder.toStringWithRanges());
        assertEquals("01234    56789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersSpacesRight() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("    ");
        builder.append(7, 12);
        assertEquals("⟦01234  ⟧  ⟦56789⟧", builder.toStringWithRanges());
        assertEquals("01234    56789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersIndent1Left() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append("    ");
        builder.append(2, 12);
        assertEquals("  ⟦  0123456789⟧", builder.toStringWithRanges());
        assertEquals("    0123456789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersIndent1Right() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append("    ");
        builder.append(2, 12);
        assertEquals("  ⟦  0123456789⟧", builder.toStringWithRanges());
        assertEquals("    0123456789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL1None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n    ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n⟧  ⟦  5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n    5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL1Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n    ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n⟧  ⟦  5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n    5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL1Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n    ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n⟧  ⟦  5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n    5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL2None() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n\n   ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n\\n⟧  ⟦ 5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n\\n   5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL2Left() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n\n   ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n\\n⟧  ⟦ 5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n\\n   5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL2Right() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n\n   ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n\\n⟧  ⟦ 5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n\\n   5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL3None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append("34\n    ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n⟧  ⟦  5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n    5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL3Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.PREVIOUS);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append("34\n    ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n⟧  ⟦  5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n    5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersEOL3Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 3);
        builder.append("34\n    ");
        builder.append(8, 12);
        assertEquals("⟦01234\\n⟧  ⟦  5678⟧", builder.toStringWithRanges());
        assertEquals("01234\\n    5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizers1() {
        String input = "01234 \n56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n  ");
        builder.append(7, 12);
        assertEquals("⟦01234⟧⟦\\n⟧  ⟦56789⟧", builder.toStringWithRanges());
        assertEquals("01234\\n  56789", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizers2() {
        String input = "01234 \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n");
        assertEquals("⟦01234⟧⟦\\n⟧", builder.toStringWithRanges());
        assertEquals("01234\\n", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizers2a() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "01234  \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append(" \n");
        assertEquals("⟦01234 ⟧⟦\\n⟧", builder.toStringWithRanges());
        assertEquals("01234 \\n", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizers3() {
        String input = "012340123401234";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("01234");
        assertEquals("⟦0123401234⟧", builder.toStringWithRanges());
        assertEquals("0123401234", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizers4() {
        String input = "0123  \n  5678";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 5);
        builder.append("\n");
        builder.append(8, 13);
        assertEquals("⟦0123 ⟧⟦\\n⟧⟦ 5678⟧", builder.toStringWithRanges());
        assertEquals("0123 \\n 5678", builder.toSequence().toVisibleWhitespaceString());
    }

    @Test
    public void test_optimizersCompoundDefault1() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) builder.append("    ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }

        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "    line 1\n" +
                "    line 2\n" +
                "\n" +
                "    line 3\n" +
                "", builder.toString());
    }

    @Test
    public void test_optimizersCompoundDefault2() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) builder.append("  ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }
        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "  line 1\n" +
                "  line 2\n" +
                "\n" +
                "  line 3\n" +
                "", builder.toString());
    }

    @Test
    public void test_optimizersCompoundDefault3() {
        String input = "" +
                "line 1\n" +
                "line 2 \n" +
                "\n" +
                "line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }
        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "\n" +
                "line 3\n" +
                "", builder.toString());
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
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_TRACK_FIRST256, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) builder.append("    ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }

        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "    line 1\n" +
                "    line 2\n" +
                "\n" +
                "    line 3\n" +
                "", builder.toString());
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
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_TRACK_FIRST256, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) builder.append("  ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }
        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "  line 1\n" +
                "  line 2\n" +
                "\n" +
                "  line 3\n" +
                "", builder.toString());
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
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_TRACK_FIRST256, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }
        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "\n" +
                "line 3\n" +
                "", builder.toString());
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
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) builder.append("    ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }

        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "    line 1\n" +
                "    line 2\n" +
                "\n" +
                "    line 3\n" +
                "", builder.toString());
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
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) builder.append("  ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }
        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "  line 1\n" +
                "  line 2\n" +
                "\n" +
                "  line 3\n" +
                "", builder.toString());
    }

    @Test
    public void test_optimizersCompoundAnchors3() {
        String input = "" +
                "line 1\n" +
                "line 2 \n" +
                "\n" +
                "line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256, optimizer);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            builder.append(trim.getSourceRange());
            builder.append("\n");
        }
        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", builder.toStringWithRanges());
        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "\n" +
                "line 3\n" +
                "", builder.toString());
    }

    @Test
    public void test_addSuffix2() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "0123456789";
        BasedSequence base = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        BasedSequence sequence = base.subSequence(1, 9);

        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(sequence.subSequence(0, 8));
        builder.append(">");
        assertEquals("⟦12345678⟧>⟦⟧", builder.toStringWithRanges());
        assertEquals("12345678>", builder.toSequence().toVisibleWhitespaceString());

        builder.append(">");
        assertEquals("⟦12345678⟧>⟦⟧>⟦⟧", builder.toStringWithRanges());
        assertEquals("12345678>>", builder.toSequence().toVisibleWhitespaceString());

        BasedSequence replaced1 = base.suffixWith(">");
        assertEquals("0123456789>", replaced1.toString());

        BasedSequence replaced2 = replaced1.suffixWith(">");
        assertEquals("0123456789>>", replaced2.toString());

        BasedSequence replaced3 = base.suffixWith(">").suffixWith(">");
        assertEquals("0123456789>>", replaced3.toString());

        SequenceBuilder builder2 = SequenceBuilder.emptyBuilder(replaced1, optimizer);
        builder2.append(replaced1);
        assertEquals("⟦0123456789⟧>⟦⟧", builder2.toStringWithRanges());
        builder2.append(">");
        assertEquals("⟦0123456789⟧>⟦⟧>⟦⟧", builder2.toStringWithRanges());
        assertEquals("0123456789>>", builder2.toSequence().toVisibleWhitespaceString());
        assertEquals(Range.of(0, 10), builder2.toSequence().getSourceRange());
    }

    @Test
    public void test_replaced() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);

        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 0);
        builder.append("^");
        builder.append(1, 10);
        assertEquals("⟦⟧^⟦123456789⟧", builder.toStringWithRanges());
        assertEquals("^123456789", builder.toSequence().toVisibleWhitespaceString());
        BasedSequence sequence1 = builder.toSequence();
        assertEquals(Range.of(0, 10), sequence1.getSourceRange());

        BasedSequence replaced = sequence.replace(0, 1, "^");

        assertEquals("^123456789", replaced.toString());
        assertEquals(Range.of(0, 10), replaced.getSourceRange());
    }

    @Test
    public void test_replaced2() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "01234567890123456789";
        BasedSequence sequence = BasedSequence.of(BasedOptionsSequence.of(input, BasedSequence.F_FULL_SEGMENTED_SEQUENCES));
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 0);
        builder.append("abcd");
        builder.append(3, 10);
        builder.append("abcd");
        builder.append(13, 20);
        assertEquals("⟦⟧abcd⟦3456789⟧abcd⟦3456789⟧", builder.toStringWithRanges());
        assertEquals("abcd3456789abcd3456789", builder.toSequence().toVisibleWhitespaceString());
        BasedSequence sequence1 = builder.toSequence();
        assertEquals(Range.of(0, 20), sequence1.getSourceRange());

        BasedSequence replaced = sequence.replace("012", "abcd");
        assertEquals("abcd3456789abcd3456789", replaced.toString());
        assertEquals(Range.of(3, 20), replaced.getSourceRange());

        SequenceBuilder builder2 = SequenceBuilder.emptyBuilder(replaced, optimizer);
        builder2.append("  ");
        builder2.append(replaced.subSequence(0, 11));
        builder2.append("\n  ");
        builder2.append(replaced.subSequence(11, 22));
        builder2.append("\n");
        assertEquals("  ⟦⟧abcd⟦3456789⟧\\n  ⟦⟧abcd⟦3456789⟧\\n⟦⟧", builder2.toStringWithRanges());
        assertEquals("  abcd3456789\\n  abcd3456789\\n", builder2.toSequence().toVisibleWhitespaceString());
        BasedSequence sequence2 = builder2.toSequence();
        assertEquals(Range.of(3, 20), sequence2.getSourceRange());
    }

    @Test
    public void test_replaced3() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = " 0123456789\n 0123456789";
        BasedSequence sequence = BasedSequence.of(BasedOptionsSequence.of(input, BasedSequence.F_FULL_SEGMENTED_SEQUENCES));
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        builder.append(0, 1);
        builder.append("abcd");
        builder.append(4, 11);
        builder.append("abcd");
        builder.append(16, 23);
        assertEquals("⟦ ⟧abcd⟦3456789⟧abcd⟦3456789⟧", builder.toStringWithRanges());
        assertEquals(" abcd3456789abcd3456789", builder.toSequence().toVisibleWhitespaceString());
        BasedSequence sequence1 = builder.toSequence();
        assertEquals(Range.of(0, 23), sequence1.getSourceRange());

        BasedSequence replaced = sequence.replace("012", "abcd");
        assertEquals(" abcd3456789\n abcd3456789", replaced.toString());
        assertEquals(Range.of(0, 23), replaced.getSourceRange());

        SequenceBuilder builder2 = SequenceBuilder.emptyBuilder(replaced, optimizer);
        builder2.append("  ");
        builder2.append(replaced.subSequence(1, 12));
        builder2.append("\n  ");
        builder2.append(replaced.subSequence(14, 25));
        builder2.append("\n");
        assertEquals("  ⟦⟧abcd⟦3456789\\n⟧  ⟦⟧abcd⟦3456789⟧\\n⟦⟧", builder2.toStringWithRanges());
        assertEquals("  abcd3456789\\n  abcd3456789\\n", builder2.toSequence().toVisibleWhitespaceString());
        BasedSequence sequence2 = builder2.toSequence();
        assertEquals(Range.of(4, 23), sequence2.getSourceRange());
    }

    @Test
    public void test_replaced4() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = " 0123456789\n 0123456789";
        BasedSequence sequence = BasedSequence.of(BasedOptionsSequence.of(input, BasedSequence.F_FULL_SEGMENTED_SEQUENCES));
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        builder.append(0, 1);
        builder.append("abcd");
        builder.append(4, 11);
        builder.append("abcd");
        builder.append(16, 23);
        assertEquals("⟦ ⟧abcd⟦3456789⟧abcd⟦3456789⟧", builder.toStringWithRanges());
        assertEquals(" abcd3456789abcd3456789", builder.toSequence().toVisibleWhitespaceString());
        BasedSequence sequence1 = builder.toSequence();
        assertEquals(Range.of(0, 23), sequence1.getSourceRange());

        BasedSequence replaced = sequence.replace("012", "abcd");
        assertEquals(" abcd3456789\n abcd3456789", replaced.toString());
        assertEquals(Range.of(0, 23), replaced.getSourceRange());

        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.NEXT);
        SequenceBuilder builder2 = SequenceBuilder.emptyBuilder(replaced, optimizer);
        builder2.append("  ");
        assertEquals("  ", builder2.toStringWithRanges());
        builder2.append(replaced.subSequence(1, 12));
        assertEquals("  ⟦⟧abcd⟦3456789⟧", builder2.toStringWithRanges());
        builder2.append("\n  ");
        assertEquals("  ⟦⟧abcd⟦3456789\\n⟧  ", builder2.toStringWithRanges());

        SequenceBuilder builder3 = SequenceBuilder.emptyBuilder(replaced, optimizer);
        builder3.append(replaced.subSequence(14, 25));
        assertEquals("⟦⟧abcd⟦3456789⟧", builder3.toStringWithRanges());

        builder2.append(replaced.subSequence(14, 25));
        assertEquals("  ⟦⟧abcd⟦3456789\\n ⟧ ⟦⟧abcd⟦3456789⟧", builder2.toStringWithRanges());

        builder2.append("\n");
        assertEquals("  ⟦⟧abcd⟦3456789\\n ⟧ ⟦⟧abcd⟦3456789⟧\\n⟦⟧", builder2.toStringWithRanges());

        assertEquals("  abcd3456789\\n  abcd3456789\\n", builder2.toSequence().toVisibleWhitespaceString());
        BasedSequence sequence2 = builder2.toSequence();
        assertEquals(Range.of(4, 23), sequence2.getSourceRange());
    }

    @Test
    public void test_appendPrefixed2() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(BasedOptionsSequence.of(input, BasedSequence.F_FULL_SEGMENTED_SEQUENCES));
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence, optimizer);

        BasedSequence text = PrefixedSubSequence.repeatOf(" ", 5, sequence).append(PrefixedSubSequence.repeatOf(" ", 5, sequence.getEmptySuffix()));
        builder.append(text);

        assertEquals("⟦⟧     ⟦0123456789⟧     ⟦⟧", builder.toStringWithRanges());
        assertEquals("     0123456789     ", builder.toSequence().toVisibleWhitespaceString());
    }
}
