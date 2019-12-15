package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
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

    String wrapText(String input, PositionAnchor anchor) {
        Pair<String, Integer> info = getInput(input);
        int offset = info.getSecond();
        BasedSequence sequence = BasedSequence.of(info.getFirst());
        MarkdownParagraph formatter = new MarkdownParagraph(sequence, CharWidthProvider.NULL);
        formatter.setFirstIndent(0);
        formatter.setWidth(66);
        formatter.setFirstWidthOffset(0);
        formatter.setKeepLineBreaks(false); // cannot keep line breaks when formatting as you type
        formatter.setKeepHardBreaks(true);

        BasedSequence actual = formatter.computeLeftAlignedSequence();
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

        assertEquals(expected, wrapText(input, PositionAnchor.CURRENT));
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

        assertEquals(expected, wrapText(input, PositionAnchor.PREVIOUS));
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

        assertEquals(expected, wrapText(input, PositionAnchor.NEXT));
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

        assertEquals(expected, wrapText(input, PositionAnchor.CURRENT));
    }

    @Test
    public void test_getOffsetIndex2Prev() {
        String input = "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙ adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.";

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when⦙ adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        assertEquals(expected, wrapText(input, PositionAnchor.PREVIOUS));
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

        assertEquals(expected, wrapText(input, PositionAnchor.NEXT));
    }
}
