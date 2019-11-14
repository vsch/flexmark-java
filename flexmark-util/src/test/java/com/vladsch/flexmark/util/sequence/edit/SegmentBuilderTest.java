package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
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

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_basicRange() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_basicFull() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_basicBuildEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        String expected = "";

        assertEquals("SegmentBuilder{endOffset=0, parts= }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_basicBuildFull() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        String expected = input;

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_basicBuildRange() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.rangeBuilder(4, 7);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        String expected = input.substring(4, 7);

        assertEquals("SegmentBuilder{endOffset=7, parts=[4, 7) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteHead() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.deleteByIndices(0, 4);
        String expected = input.substring(4);

        assertEquals("SegmentBuilder{endOffset=10, parts=[4, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.deleteByIndices(0, 4);
        String expected = "";

        assertEquals("SegmentBuilder{endOffset=0, parts= }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteTail() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.deleteByIndices(6, 10);
        String expected = input.substring(0, 6);

        assertEquals("SegmentBuilder{endOffset=6, parts=[0, 6) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddleRange() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.deleteByIndices(3, 7);
        String expected = input.substring(0, 3) + input.substring(7);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), [7, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_insertEmptyHead1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.insertString(0, "abc");
        String expected = "abc";

        assertEquals("SegmentBuilder{endOffset=0, parts='abc' }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_insertEmptyHead2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.rangeBuilder(10, 10);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.insertString(10, "abc");
        String expected = "abc";

        assertEquals("SegmentBuilder{endOffset=10, parts=[10, 10), 'abc' }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_insertEmptyTail1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.rangeBuilder(10, 10);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.insertString(0, "abc");
        String expected = "abc";

        assertEquals("SegmentBuilder{endOffset=10, parts='abc', [10, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_insertEmptyTail2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.rangeBuilder(10, 10);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.insertString(10, "abc");
        String expected = "abc";

        assertEquals("SegmentBuilder{endOffset=10, parts=[10, 10), 'abc' }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_insertHead() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.insertString(0, "abc");
        String expected = "abc" + input;

        assertEquals("SegmentBuilder{endOffset=10, parts='abc', [0, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_insertTail() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.insertString(10, "xyz");
        String expected = input + "xyz";

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10), 'xyz' }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_insertMiddle() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.sequenceBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.insertString(5, "pqr");
        String expected = input.substring(0, 5) + "pqr" + input.substring(5);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'pqr', [5, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_appendRange1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 4);
        String expected = input.substring(0, 4);

        assertEquals("SegmentBuilder{endOffset=4, parts=[0, 4) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_appendRangeNonOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 4);
        segments.appendRange(6, 7);
        String expected = input.substring(0, 4) + input.substring(6, 7);

        assertEquals("SegmentBuilder{endOffset=7, parts=[0, 4), [6, 7) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_appendRangeTouching() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendRange(5, 7);
        String expected = input.substring(0, 7);

        assertEquals("SegmentBuilder{endOffset=7, parts=[0, 7) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_appendRangeOverlapping() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendRange(3, 7);
        String expected = input.substring(0, 7);

        assertEquals("SegmentBuilder{endOffset=7, parts=[0, 7) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_appendRangeOverlappingOverString() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(3, 7);
        String expected = input.substring(0, 5) + "abc" + input.substring(5, 7);

        assertEquals("SegmentBuilder{endOffset=7, parts=[0, 5), 'abc', [5, 7) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_appendRangeStrings() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendString("def");
        String expected = input.substring(0, 5) + "abcdef";

        assertEquals("SegmentBuilder{endOffset=5, parts=[0, 5), 'abcdef' }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddleStringFull() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [6, 10) }", segments.toString());

        segments.deleteByLength(5, 3);

        String expected = input.substring(0, 5) + input.substring(6);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), [6, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddleStringHead() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [6, 10) }", segments.toString());

        segments.deleteByLength(5, 1);

        String expected = input.substring(0, 5) + "bc" + input.substring(6);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'bc', [6, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddleStringTail() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [6, 10) }", segments.toString());

        segments.deleteByLength(7, 1);

        String expected = input.substring(0, 5) + "ab" + input.substring(6);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'ab', [6, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddleStringMiddle() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [6, 10) }", segments.toString());

        segments.deleteByLength(6, 1);

        String expected = input.substring(0, 5) + "ac" + input.substring(6);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'ac', [6, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddleMergeRanges() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(5, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [5, 10) }", segments.toString());

        segments.deleteByLength(5, 3);

        //noinspection UnnecessaryLocalVariable
        String expected = input;

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddleMergeStrings() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(5, 8);
        segments.appendString("xyz");
        segments.appendRange(8, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [5, 8), 'xyz', [8, 10) }", segments.toString());

        segments.deleteByLength(8, 3);

        String expected = input.substring(0, 5) + "abcxyz" + input.substring(8);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abcxyz', [8, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteTailMiddleHead() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [6, 10) }", segments.toString());

        String partial = "01234abc6789";
        String expectedPartial = partial.substring(0, 3) + partial.substring(10);
        String expected = input.substring(0, 3) + input.substring(8);
        assertEquals(expectedPartial, expected);

        segments.deleteRange(Range.of(3, 10));
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), [8, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteStartMiddlePartial() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [6, 10) }", segments.toString());

        String partial = "01234abc6789";
        String expectedPartial = partial.substring(0, 3) + partial.substring(7);
        String expected = input.substring(0, 3) + "c" + input.substring(6);
        assertEquals(expectedPartial, expected);

        segments.deleteByIndices(3, 7);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), 'c', [6, 10) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteAll() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), 'abc', [6, 10) }", segments.toString());

        String expected = "";
        segments.deleteByIndices(0, 15);
        assertEquals("SegmentBuilder{endOffset=0, parts= }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteAll2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.rangeBuilder(Range.of(3, 3));
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(3, 5);
        segments.appendString("abc");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[3, 5), 'abc', [6, 10) }", segments.toString());

        String expected = "";
        segments.deleteByIndices(0, 15);
        assertEquals("SegmentBuilder{endOffset=0, parts= }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }


    /*
           Optimization tests, old code
     */

    @Test
    public void test_optimizeExtendPrev1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 3);
        segments.appendString("345");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_optimizeExtendPrev2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 3);
        segments.appendString("34 ");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '34 ', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), ' ', [6, 10) }", segments.toString());
    }

    @Test
    public void test_optimizeExtendPrevNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 3);
        segments.appendString("34 5");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '34 5', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), ' ', [5, 10) }", segments.toString());
    }

    @Test
    public void test_optimizeExtendPrevNextCollapse() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 3);
        segments.appendString("34 56");
        segments.appendRange(7, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '34 56', [7, 10) }", segments.toString());

        segments.optimizeFor(sequence);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), ' ', [5, 10) }", segments.toString());
    }

    @Test
    public void test_optimizeExtendNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 3);
        segments.appendString(" 3456");
        segments.appendRange(7, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' 3456', [7, 10) }", segments.toString());

        segments.optimizeFor(sequence);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    @Test
    public void test_optimizeExtendNext1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 3);
        segments.appendString(" 345");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' 345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    @Test
    public void test_optimizeIndent1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 3);
        segments.appendString(" 345");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' 345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }


    /*
       Optimization tests, optimizer for backward compatibility
     */

    @Test
    public void test_optimizerExtendPrev1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        segments.appendRange(0, 3);
        segments.appendString("345");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendPrev2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        segments.appendRange(0, 3);
        segments.appendString("34 ");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '34 ', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), ' ', [6, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendPrevNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        segments.appendRange(0, 3);
        segments.appendString("34 5");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '34 5', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), ' ', [5, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendPrevNextCollapse() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.appendRange(0, 3);
        segments.appendString("34 56");
        segments.appendRange(7, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), '34 56', [7, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 5), ' ', [5, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendNext() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        segments.appendRange(0, 3);
        segments.appendString(" 3456");
        segments.appendRange(7, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' 3456', [7, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerExtendNext1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        segments.appendRange(0, 3);
        segments.appendString(" 345");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' 345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    @Test
    public void test_optimizerIndent1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);

        segments.appendRange(0, 3);
        segments.appendString(" 345");
        segments.appendRange(6, 10);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' 345', [6, 10) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 3), ' ', [3, 10) }", segments.toString());
    }

    /*
     * Optimizer tests to ensure all optimizations are handled properly
     */

    @Test
    public void test_optimizersIndent1None() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendString("    ");
        segments.appendRange(2, 12);
        assertEquals("SegmentBuilder{endOffset=12, parts='    ', [2, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts='  ', [0, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersSpacesNone() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("    ");
        segments.appendRange(7, 12);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 5), '    ', [7, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersSpacesLeft() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("    ");
        segments.appendRange(7, 12);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 5), '    ', [7, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 5), '  ', [5, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersSpacesRight() {
        String input = "01234  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("    ");
        segments.appendRange(7, 12);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 5), '    ', [7, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersIndent1Left() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendString("    ");
        segments.appendRange(2, 12);
        assertEquals("SegmentBuilder{endOffset=12, parts='    ', [2, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts='  ', [0, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersIndent1Right() {
        String input = "  0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendString("    ");
        segments.appendRange(2, 12);
        assertEquals("SegmentBuilder{endOffset=12, parts='    ', [2, 12) }", segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts='  ', [0, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL1None() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("\n    ");
        segments.appendRange(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=12, parts=[0, 5), '\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL1Left() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("\n    ");
        segments.appendRange(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=12, parts=[0, 5), '\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL1Right() {
        String input = "01234\n  56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("\n    ");
        segments.appendRange(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=12, parts=[0, 5), '\n    ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 6), '  ', [6, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL2None() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("\n\n   ");
        segments.appendRange(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=12, parts=[0, 5), '\n\n   ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL2Left() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.LEFT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("\n\n   ");
        segments.appendRange(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=12, parts=[0, 5), '\n\n   ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersEOL2Right() {
        String input = "01234\n\n 56789";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.RIGHT);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();

        segments.appendRange(0, 5);
        segments.appendString("\n\n   ");
        segments.appendRange(8, 12);
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=12, parts=[0, 5), '\n\n   ', [8, 12) }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals("SegmentBuilder{endOffset=12, parts=[0, 7), '  ', [7, 12) }", segments.toString());
    }

    @Test
    public void test_optimizersCompound() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharMatchingSegmentOptimizer<BasedSequence> optimizer = new CharMatchingSegmentOptimizer<>(TrackerDirection.NONE);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.appendString("    ");
            segments.appendRange(trim.getSourceRange());
            segments.appendString("\n");
        }
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=29, parts='    ', [2, 8), '\n    ', [12, 18), '\n', [20, 20), '\n    ', [23, 29), '\n' }"), segments.toString());

        segments.optimizeFor(sequence, optimizer);
        assertEquals(escapeJavaString("SegmentBuilder{endOffset=29, parts='  ', [0, 8), '\n  ', [10, 18), [19, 21), '  ', [21, 30) }"), segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals("" +
                "    line 1\n" +
                "    line 2\n" +
                "\n" +
                "    line 3\n" +
                "", builder.toString());
    }
}
