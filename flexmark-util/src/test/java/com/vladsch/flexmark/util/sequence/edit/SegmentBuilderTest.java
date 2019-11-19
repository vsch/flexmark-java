package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static com.vladsch.flexmark.util.Utils.escapeJavaString;
import static org.junit.Assert.assertEquals;

public class SegmentBuilderTest {
    @Test
    public void test_basicEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        segments.append(sequence.length(), sequence.length());

        assertEquals(0, segments.length());
        assertEquals("SegmentBuilder{end=10, parts=[10, 10) }", segments.toString());
    }

    @Test
    public void test_basicBuildEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        String expected = "";

        assertEquals("SegmentBuilder{end=0, parts= }", segments.toString());
        assertEquals(expected.length(), segments.length());

        assertEquals(expected, segments.toStringWithRanges(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRange1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 4);
        String expected = input.substring(0, 4);

        assertEquals("SegmentBuilder{end=4, parts=[0, 4) }", segments.toString());
        assertEquals(expected.length(), segments.length());

        assertEquals("⟦0123⟧", segments.toStringWithRanges(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeNonOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 4);
        segments.append(6, 7);
        String expected = input.substring(0, 4) + input.substring(6, 7);

        assertEquals("SegmentBuilder{end=7, parts=[0, 4), [6, 7) }", segments.toString());
        assertEquals(expected.length(), segments.length());

        assertEquals("⟦0123⟧⟦6⟧", segments.toStringWithRanges(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeTouching() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append(5, 7);
        String expected = input.substring(0, 7);

        assertEquals("SegmentBuilder{end=7, parts=[0, 7) }", segments.toString());
        assertEquals(expected.length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRanges(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append(3, 7);
        String expected = input.substring(0, 7);

        assertEquals("SegmentBuilder{end=7, parts=[0, 7) }", segments.toString());
        assertEquals(expected.length(), segments.length());

        assertEquals("⟦0123456⟧", segments.toStringWithRanges(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeOverlappingOverString() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        segments.append(0, 5);
        segments.append("abc");
        segments.append(3, 7);
        String expected = input.substring(0, 5) + "abc" + input.substring(5, 7);

        assertEquals("SegmentBuilder{end=7, parts=[0, 5), 'abc', [5, 7) }", segments.toString());
        assertEquals(expected.length(), segments.length());

        assertEquals("⟦01234⟧abc⟦56⟧", segments.toStringWithRanges(sequence));
        assertEquals(expected, segments.toString(sequence));
    }

    @Test
    public void test_appendRangeStrings() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("abc");
        segments.append("def");
        String expected = input.substring(0, 5) + "abcdef";

        assertEquals("SegmentBuilder{end=5, parts=[0, 5), 'abcdef' }", segments.toString());
        assertEquals(expected.length(), segments.length());

        assertEquals("⟦01234⟧abcdef", segments.toStringWithRanges(sequence));
        assertEquals(expected, segments.toString(sequence));
    }


    /*
       Optimization tests, optimizer for backward compatibility
     */

    @Test
    public void test_optimizerExtendPrev1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);

        segments.append(0, 3);
        segments.append("345");
        segments.append(6, 10);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), '345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendPrev2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);

        segments.append(0, 3);
        segments.append("34 ");
        segments.append(6, 10);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), '34 ', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=10, parts=[0, 5), ' ', [6, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendPrevNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);

        segments.append(0, 3);
        segments.append("34 5");
        segments.append(6, 10);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), '34 5', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=10, parts=[0, 5), ' ', [5, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendPrevNextCollapse() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.append(0, 3);
        segments.append("34 56");
        segments.append(7, 10);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), '34 56', [7, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=10, parts=[0, 5), ' ', [5, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);

        segments.append(0, 3);
        segments.append(" 3456");
        segments.append(7, 10);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), ' 3456', [7, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendNext1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);

        segments.append(0, 3);
        segments.append(" 345");
        segments.append(6, 10);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), ' 345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerIndent1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);

        segments.append(0, 3);
        segments.append(" 345");
        segments.append(6, 10);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), ' 345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    /*
     * Optimizer tests to ensure all optimizations are handled properly
     */

    @Test
    public void test_optimizersIndent1None() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("SegmentBuilder{end=12, parts='    ', [2, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts='  ', [0, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersSpacesNone() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("SegmentBuilder{end=12, parts=[0, 5), '    ', [7, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersSpacesLeft() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("SegmentBuilder{end=12, parts=[0, 5), '    ', [7, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 5), '  ', [5, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersSpacesRight() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("    ");
        segments.append(7, 12);
        assertEquals("SegmentBuilder{end=12, parts=[0, 5), '    ', [7, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersIndent1Left() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("SegmentBuilder{end=12, parts='    ', [2, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts='  ', [0, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersIndent1Right() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append("    ");
        segments.append(2, 12);
        assertEquals("SegmentBuilder{end=12, parts='    ', [2, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts='  ', [0, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL1None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 5), '\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL1Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 5), '\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL1Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n    ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 5), '\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL2None() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 5), '\n\n   ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL2Left() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 5), '\n\n   ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL2Right() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n\n   ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 5), '\n\n   ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL3None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 3), '34\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL3Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.PREVIOUS);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 3), '34\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL3Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 3);
        segments.append("34\n    ");
        segments.append(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 3), '34\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizers1() {
        String input = "01234 \n56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n  ");
        segments.append(7, 12);
        assertEquals(escapeJavaString("SegmentBuilder{end=12, parts=[0, 5), '\n  ', [7, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=12, parts=[0, 5), [6, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizers2() {
        String input = "01234 \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n");
        assertEquals(escapeJavaString("SegmentBuilder{end=5, parts=[0, 5), '\n' }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=7, parts=[0, 5), [6, 7) }", segments.toString());
    }

    @Test
    public void test_optimizers2a() {
        // this one causes text to be replaced with recovered EOL in the code
        String input = "01234  \n";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append(" \n");
        assertEquals(escapeJavaString("SegmentBuilder{end=5, parts=[0, 5), ' \n' }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=8, parts=[0, 6), [7, 8) }", segments.toString());
    }

    @Test
    public void test_optimizers3() {
        String input = "012340123401234";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("01234");
        segments.optimizeFor(sequence, optimizer);

        assertEquals(escapeJavaString("SegmentBuilder{end=10, parts=[0, 10) }"), segments.toString());

        assertEquals("SegmentBuilder{end=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_optimizers4() {
        String input = "0123  \n  5678";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.NEXT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(0, 5);
        segments.append("\n");
        segments.append(8, 13);
        assertEquals(escapeJavaString("SegmentBuilder{end=13, parts=[0, 5), '\n', [8, 13) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{end=13, parts=[0, 5), [6, 7), [8, 13) }", segments.toString());
    }

    @Test
    public void test_optimizersCompound1() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("    ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals(escapeJavaString("SegmentBuilder{end=29, parts='    ', [2, 8), '\n    ', [12, 18), '\n', [20, 20), '\n    ', [23, 29), '\n' }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
//        assertEquals(escapeJavaString("SegmentBuilder{end=30, parts='  ', [0, 8), '\n  ', [10, 18), [19, 21), '  ', [21, 30) }"), segments.toString());

        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", segments.toStringWithRanges(input));

        assertEquals("" +
                "    line 1\n" +
                "    line 2\n" +
                "\n" +
                "    line 3\n" +
                "", segments.toString(sequence));

        assertEquals(escapeJavaString("SegmentBuilder{end=30, parts='  ', [0, 8), [9, 10), '  ', [10, 18), [19, 21), '  ', [21, 30) }"), segments.toString());
    }

    @Test
    public void test_optimizersCompound2() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals(escapeJavaString("SegmentBuilder{end=29, parts='  ', [2, 8), '\n  ', [12, 18), '\n', [20, 20), '\n  ', [23, 29), '\n' }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals(escapeJavaString("SegmentBuilder{end=30, parts=[0, 8), [9, 18), [19, 30) }"), segments.toString());

        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", segments.toStringWithRanges(input));

        assertEquals("" +
                "  line 1\n" +
                "  line 2\n" +
                "\n" +
                "  line 3\n" +
                "", segments.toString(sequence));

        assertEquals(escapeJavaString("SegmentBuilder{end=30, parts=[0, 8), [9, 18), [19, 30) }"), segments.toString());
    }

    @Test
    public void test_optimizersCompound3() {
        String input = "" +
                "line 1\n" +
                "line 2 \n" +
                "\n" +
                "line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer<BasedSequence> optimizer = new CharRecoveryOptimizer<>(PositionAnchor.CURRENT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals(escapeJavaString("SegmentBuilder{end=22, parts=[0, 6), '\n', [7, 13), '\n', [15, 15), '\n', [16, 22), '\n' }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
//        assertEquals(escapeJavaString("SegmentBuilder{end=30, parts='  ', [0, 8), '\n  ', [10, 18), [19, 21), '  ', [21, 30) }"), segments.toString());

        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRanges(input));

        assertEquals("" +
                "line 1\n" +
                "line 2\n" +
                "\n" +
                "line 3\n" +
                "", segments.toString(sequence));

        assertEquals(escapeJavaString("SegmentBuilder{end=23, parts=[0, 13), [14, 23) }"), segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultMerge1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append(4, 8);
        assertEquals("SegmentBuilder{end=8, parts=[2, 8) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultMerge2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append(1, 8);
        assertEquals("SegmentBuilder{end=8, parts=[1, 8) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultMerge3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append(3, 5);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultMerge4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append(2, 4);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultMerge5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append(2, 5);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultMerge6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append(3, 4);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultChop1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(4, 8);
        assertEquals("SegmentBuilder{end=8, parts=[2, 5), '-', [5, 8) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultChop2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(1, 8);
        assertEquals("SegmentBuilder{end=8, parts=[2, 5), '-', [5, 8) }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultChop3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 5);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5), '-' }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultChop4() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 4);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5), '-' }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultChop5() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(2, 5);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5), '-' }", segments.toString());
    }

    @Test
    public void test_handleOverlapDefaultChop6() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.append(2, 5);
        segments.append("-");
        segments.append(3, 4);
        assertEquals("SegmentBuilder{end=5, parts=[2, 5), '-' }", segments.toString());
    }
}

