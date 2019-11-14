package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CharMatchingSegmentOptimizerTest {
    @Test
    public void test_EmptyNone() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);
        SegmentParams paramsNull0 = SegmentParams.of(null, "", null);
        SegmentParams paramsNull1 = SegmentParams.of(null, "", Range.NULL);
        SegmentParams paramsNull2 = SegmentParams.of(Range.NULL, "", null);
        SegmentParams paramsNull3 = SegmentParams.of(Range.NULL, "", Range.NULL);

        assertSame(paramsNull0, optimizer.apply(sequence, paramsNull0));
        assertSame(paramsNull1, optimizer.apply(sequence, paramsNull1));
        assertSame(paramsNull2, optimizer.apply(sequence, paramsNull2));
        assertSame(paramsNull3, optimizer.apply(sequence, paramsNull3));
    }

    @Test
    public void test_EmptyLeft() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);
        SegmentParams paramsNull0 = SegmentParams.of(null, "", null);
        SegmentParams paramsNull1 = SegmentParams.of(null, "", Range.NULL);
        SegmentParams paramsNull2 = SegmentParams.of(Range.NULL, "", null);
        SegmentParams paramsNull3 = SegmentParams.of(Range.NULL, "", Range.NULL);

        assertSame(paramsNull0, optimizer.apply(sequence, paramsNull0));
        assertSame(paramsNull1, optimizer.apply(sequence, paramsNull1));
        assertSame(paramsNull2, optimizer.apply(sequence, paramsNull2));
        assertSame(paramsNull3, optimizer.apply(sequence, paramsNull3));
    }

    @Test
    public void test_EmptyRight() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);
        SegmentParams paramsNull0 = SegmentParams.of(null, "", null);
        SegmentParams paramsNull1 = SegmentParams.of(null, "", Range.NULL);
        SegmentParams paramsNull2 = SegmentParams.of(Range.NULL, "", null);
        SegmentParams paramsNull3 = SegmentParams.of(Range.NULL, "", Range.NULL);

        assertSame(paramsNull0, optimizer.apply(sequence, paramsNull0));
        assertSame(paramsNull1, optimizer.apply(sequence, paramsNull1));
        assertSame(paramsNull2, optimizer.apply(sequence, paramsNull2));
        assertSame(paramsNull3, optimizer.apply(sequence, paramsNull3));
    }

    @Test
    public void test_optimizeExtendPrev1None() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "345", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 10), "", null);
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrev2None() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 ", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(6, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrevNextNone() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 5", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(5, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrevNextCollapseNone() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 56", Range.of(7, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(5, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendNextNone() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), " 3456", Range.of(7, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 3), " ", Range.of(3, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendNext1None() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), " 345", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 3), " ", Range.of(3, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeIndent1None() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(null, "    ", Range.of(2, 12));
        SegmentParams expected = SegmentParams.of(null, "  ", Range.of(0, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeSpacesNone() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "    ", Range.of(7, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 6), "  ", Range.of(6, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeSpacesLeft() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "    ", Range.of(7, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), "  ", Range.of(5, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeSpacesRight() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "    ", Range.of(7, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 7), "  ", Range.of(7, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrev1Left() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "345", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 10), "", null);
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrev2Left() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 ", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(6, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrevNextLeft() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 5", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(5, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrevNextCollapseLeft() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 56", Range.of(7, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(5, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendNextLeft() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), " 3456", Range.of(7, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 3), " ", Range.of(3, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendNext1Left() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), " 345", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 3), " ", Range.of(3, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeIndent1Left() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(null, "    ", Range.of(2, 12));
        SegmentParams expected = SegmentParams.of(null, "  ", Range.of(0, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrev1Right() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "345", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 10), "", null);
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrev2Right() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 ", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(6, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrevNextRight() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 5", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(5, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendPrevNextCollapseRight() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), "34 56", Range.of(7, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 5), " ", Range.of(5, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendNextRight() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), " 3456", Range.of(7, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 3), " ", Range.of(3, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeExtendNext1Right() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 3), " 345", Range.of(6, 10));
        SegmentParams expected = SegmentParams.of(Range.of(0, 3), " ", Range.of(3, 10));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeIndent1Right() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(null, "    ", Range.of(2, 12));
        SegmentParams expected = SegmentParams.of(null, "  ", Range.of(0, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeEOL1None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "\n    ", Range.of(8, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 6), "  ", Range.of(6, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeEOL1Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "\n    ", Range.of(8, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 6), "  ", Range.of(6, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeEOL1Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "\n    ", Range.of(8, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 6), "  ", Range.of(6, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeEOL2None() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "\n\n   ", Range.of(8, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 7), "  ", Range.of(7, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeEOL2Left() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "\n\n   ", Range.of(8, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 7), "  ", Range.of(7, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }

    @Test
    public void test_optimizeEOL2Right() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);

        SegmentParams params = SegmentParams.of(Range.of(0, 5), "\n\n   ", Range.of(8, 12));
        SegmentParams expected = SegmentParams.of(Range.of(0, 7), "  ", Range.of(7, 12));
        assertEquals(expected, optimizer.apply(sequence, params));
    }
}
