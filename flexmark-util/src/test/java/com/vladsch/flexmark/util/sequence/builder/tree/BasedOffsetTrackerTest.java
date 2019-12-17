package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasedOffsetTrackerTest {

    Pair<String, Integer> getInput(String input) {
        int pos = input.indexOf("⦙");
        if (pos >= 0) {
            return Pair.of(input.substring(0, pos) + input.substring(pos + 1), pos);
        }
        return Pair.of(input, 0);
    }

    String getResult(String actual, int index) {
        return actual.substring(0, index) + "⦙" + actual.substring(index);
    }

    String wrapText(String input, PositionAnchor anchor, int margin) {
        Pair<String, Integer> info = getInput(input);
        int offset = info.getSecond();
        BasedSequence sequence = BasedSequence.of(info.getFirst());
        MarkdownParagraph formatter = new MarkdownParagraph(sequence, CharWidthProvider.NULL);
        formatter.setFirstIndent(0);
        formatter.setWidth(margin);
        formatter.setFirstWidthOffset(0);
        formatter.setKeepSoftBreaks(false); // cannot keep line breaks when formatting as you type
        formatter.setKeepHardBreaks(true);

        SequenceBuilder builder1 = sequence.getBuilder();
        if (offset >= 0 && offset < sequence.length()) {
            sequence.subSequence(0, offset).addSegments(builder1.getSegmentBuilder());
            builder1.append("⦙");
            sequence.subSequence(offset).addSegments(builder1.getSegmentBuilder());
        } else {
            sequence.addSegments(builder1.getSegmentBuilder());
        }
        System.out.println(builder1.toStringWithRanges());

        BasedSequence actual = formatter.wrapText();

        SequenceBuilder builder = sequence.getBuilder();
        actual.addSegments(builder.getSegmentBuilder());
        System.out.println(builder.toStringWithRanges());

        BasedOffsetTracker tracker = BasedOffsetTracker.create(actual);
        int index = tracker.getOffsetIndex(offset, anchor);
        return getResult(actual.toString(), index);
    }

    @Test
    public void test_getOffsetIndex1Current() {
        String input = "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.";

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        assertEquals(expected, wrapText(input, PositionAnchor.CURRENT, 66));
    }

    @Test
    public void test_getOffsetIndex1Prev() {
        String input = "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.";

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        assertEquals(expected, wrapText(input, PositionAnchor.PREVIOUS, 66));
    }

    @Test
    public void test_getOffsetIndex1Next() {
        String input = "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.";

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        assertEquals(expected, wrapText(input, PositionAnchor.NEXT, 66));
    }

    @Test
    public void test_getOffsetIndex2Current() {
        String input = "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙ adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.";

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        assertEquals(expected, wrapText(input, PositionAnchor.CURRENT, 66));
    }

    @Test
    public void test_getOffsetIndex2Prev() {
        String input = "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙ adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.";

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        assertEquals(expected, wrapText(input, PositionAnchor.PREVIOUS, 66));
    }

    @Test
    public void test_getOffsetIndex2Next() {
        String input = "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙ adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.";

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        assertEquals(expected, wrapText(input, PositionAnchor.NEXT, 66));
    }

    @Test
    public void test_getOffsetIndexTypedText() {
        String input = "" +
                "text should wrap onto the next t⦙\n" +
                "line at right margin of 30" +
                "";

        String expected = "" +
                "text should wrap onto the next t⦙ line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, PositionAnchor.PREVIOUS, 66));
    }

    @Test
    public void test_getOffsetIndexTypedText2() {
        String input = "" +
                "text should wrap onto the next t⦙\n" +
                "line at right margin of 30" +
                "";

        String expected = "" +
                "text should wrap onto the next\n" +
                "t⦙ line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, PositionAnchor.PREVIOUS, 30));
    }

    @Test
    public void test_getOffsetIndexTypedText3() {
        String input = "" +
                "text should wrap onto the next\n" +
                "     ⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "text should wrap onto the next⦙\n" +
                "line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, PositionAnchor.PREVIOUS, 30));
    }

    @Test
    public void test_getOffsetIndexTypedText4() {
        String input = "" +
                "text should wrap onto the next\n" +
                "     ⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "text should wrap onto the next\n" +
                "⦙line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, PositionAnchor.NEXT, 30));
    }
}
