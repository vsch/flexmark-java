package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SegmentBuilderTest {
    @Test
    public void test_basicEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_basicRange() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_basicFull() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 10) }", segments.toString());
    }

    @Test
    public void test_basicBuildEmpty() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.emptyBuilder();
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        String expected = "";

        assertEquals("SegmentBuilder{endOffset=0, parts=[0, 0) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_basicBuildFull() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);
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

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);
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

        assertEquals("SegmentBuilder{endOffset=0, parts=[0, 0) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteTail() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        segments.deleteByIndices(6, 10);
        String expected = input.substring(0, 6);

        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 6) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }

    @Test
    public void test_deleteMiddle() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);
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

        assertEquals("SegmentBuilder{endOffset=0, parts='abc', [0, 0) }", segments.toString());

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

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);
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

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);
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

        SegmentBuilder segments = SegmentBuilder.fullBuilder(sequence);
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
    public void test_deleteMiddleString() {
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
        assertEquals("SegmentBuilder{endOffset=10, parts=[0, 0) }", segments.toString());

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
        assertEquals("SegmentBuilder{endOffset=10, parts=[3, 3) }", segments.toString());

        segments.buildSequence(sequence, builder);
        assertEquals(expected, builder.toSequence().toString());
        assertEquals(expected, builder.toString());
    }
}
