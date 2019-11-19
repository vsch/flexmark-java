package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasedSegmentBuilderTest {

    @Test
    public void handleOverlapNone() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 5);
        builder.append("abc");
        builder.append(5, 10);
        String expected = input.substring(0, 5) + "abc" + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 5), 'abc', [5, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlap1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 6);
        builder.append("abc");
        builder.append(5, 10);
        String expected = input.substring(0, 6) + "abc" + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 6), 'abc5', [6, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlap2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 5);
        builder.append("abc");
        builder.append(4, 10);
        String expected = input.substring(0, 5) + "abc" + input.substring(4);

        assertEquals("SegmentBuilder{end=10, parts=[0, 5), 'abc4', [5, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapFull1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("abc");
        builder.append(5, 10);
        String expected = input.substring(0, 10) + "abc" + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), 'abc56789' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapFull2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("abc");
        builder.append(0, 5);
        String expected = input.substring(0, 10) + "abc" + input.substring(0, 5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), 'abc01234' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapFull3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("abc");
        builder.append(0, 10);
        String expected = input.substring(0, 10) + "abc" + input.substring(0, 10);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), 'abc0123456789' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapContained() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("abc");
        builder.append(1, 9);
        String expected = input.substring(0, 10) + "abc" + input.substring(1, 9);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), 'abc12345678' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapNoTextNone() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 5);
        builder.append(5, 10);
        String expected = input.substring(0, 5) + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapNoText1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 6);
        builder.append(5, 10);
        String expected = input.substring(0, 6) + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 6), '5', [6, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapNoText2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 5);
        builder.append(4, 10);
        String expected = input.substring(0, 5) + input.substring(4);

        assertEquals("SegmentBuilder{end=10, parts=[0, 5), '4', [5, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapNoTextFull1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append(5, 10);
        String expected = input.substring(0, 10) + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '56789' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapNoTextFull2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append(0, 5);
        String expected = input.substring(0, 10) + input.substring(0, 5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '01234' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapNoTextFull3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append(0, 10);
        String expected = input.substring(0, 10) + input.substring(0, 10);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '0123456789' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapNoTextContained() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append(1, 9);
        String expected = input.substring(0, 10) + input.substring(1, 9);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '12345678' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }
    @Test
    public void handleOverlapEmptyTextNone() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 5);
        builder.append("");
        builder.append(5, 10);
        String expected = input.substring(0, 5) + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapEmptyText1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 6);
        builder.append("");
        builder.append(5, 10);
        String expected = input.substring(0, 6) + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 6), '5', [6, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapEmptyText2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 5);
        builder.append("");
        builder.append(4, 10);
        String expected = input.substring(0, 5) + input.substring(4);

        assertEquals("SegmentBuilder{end=10, parts=[0, 5), '4', [5, 10) }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapEmptyTextFull1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("");
        builder.append(5, 10);
        String expected = input.substring(0, 10) + input.substring(5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '56789' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapEmptyTextFull2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("");
        builder.append(0, 5);
        String expected = input.substring(0, 10) + input.substring(0, 5);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '01234' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapEmptyTextFull3() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("");
        builder.append(0, 10);
        String expected = input.substring(0, 10) + input.substring(0, 10);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '0123456789' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }

    @Test
    public void handleOverlapEmptyTextContained() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSequenceBuilder builder = new BasedSequenceBuilder(sequence);

        builder.append(0, 10);
        builder.append("");
        builder.append(1, 9);
        String expected = input.substring(0, 10) + input.substring(1, 9);

        assertEquals("SegmentBuilder{end=10, parts=[0, 10), '12345678' }", builder.getSegmentBuilder().toString());

        assertEquals(expected, builder.toString());
    }
}
