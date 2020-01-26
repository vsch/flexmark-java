package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class BasedOffsetTrackerTest {

    Pair<String, Integer> getInput(String input) {
        int pos = input.indexOf("⦙");
        if (pos >= 0) {
            return Pair.of(input.substring(0, pos) + input.substring(pos + 1), pos);
        }
        return Pair.of(input, 0);
    }

    String getResult(String actual, OffsetInfo result) {
        if (result.isEndOffset == (result.startIndex == result.endIndex)) {
            int index = result.startIndex;
            return actual.substring(0, index) + "⦙" + actual.substring(index);
        } else {
            return actual.substring(0, result.startIndex) + "⟦" + actual.substring(result.startIndex, result.endIndex) + "⟧" + actual.substring(result.endIndex);
        }
    }

    BasedSequence wrapText(String input, int margin) {
        Pair<String, Integer> info = getInput(input);
        BasedSequence sequence = BasedSequence.of(info.getFirst());

        MarkdownParagraph formatter = new MarkdownParagraph(sequence, CharWidthProvider.NULL);
        formatter.setFirstIndent("");
        formatter.setWidth(margin);
        formatter.setFirstWidthOffset(0);
        formatter.setKeepSoftBreaks(false); // cannot keep line breaks when formatting as you type
        formatter.setKeepHardBreaks(true);

        int offset = info.getSecond();
        SequenceBuilder builder1 = sequence.getBuilder();
        if (offset >= 0 && offset < sequence.length()) {
            sequence.subSequence(0, offset).addSegments(builder1.getSegmentBuilder());
            builder1.append("⦙");
            sequence.subSequence(offset).addSegments(builder1.getSegmentBuilder());
        } else {
            sequence.addSegments(builder1.getSegmentBuilder());
        }
        System.out.println(builder1.toStringWithRanges());

        return formatter.wrapTextNotTracked();
    }

    String wrapText(String input, boolean isEndOffset, int margin) {
        BasedSequence actual = wrapText(input, margin);
        Pair<String, Integer> info1 = getInput(input);
        BasedSequence sequence1 = BasedSequence.of(info1.getFirst());
        return resolveOffset(sequence1, actual, info1.getSecond(), isEndOffset);
    }

    String wrapText(String input, boolean isEndOffset, int margin, @NotNull Function<BasedSequence, BasedSequence> postProcessor) {
        BasedSequence actual = postProcessor.apply(wrapText(input, margin));
        Pair<String, Integer> info1 = getInput(input);
        BasedSequence sequence1 = BasedSequence.of(info1.getFirst());
        return resolveOffset(sequence1, actual, info1.getSecond(), isEndOffset);
    }

    String resolveOffset(BasedSequence sequence, BasedSequence actual, int offset, boolean isEndOffset) {
        SequenceBuilder builder = sequence.getBuilder();
        actual.addSegments(builder.getSegmentBuilder());
        System.out.println(builder.toStringWithRanges());

        BasedOffsetTracker tracker = BasedOffsetTracker.create(actual);
        OffsetInfo result = tracker.getOffsetInfo(offset, isEndOffset);
        return result.toString() + "\n-----------------------------------------------------\n" +
                builder.toStringWithRanges().replace("\\n", "\n") + "\n-----------------------------------------------------\n" +
                getResult(actual.toString(), result);
    }

    @Test
    public void test_getOffsetIndex1() {
        String input = "" +
                "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied." +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[83, 84), i=[83, 84) }\n" +
                "-----------------------------------------------------\n" +
                "⟦Add: configuration for repeated prefixes in items, which would `be⟧\n" +
                "⟦#2` copied when adding/splitting an item. In other words they\n" +
                "⟧⟦would be treated equivalent to task item marker prefix. That way\n" +
                "⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧\n" +
                "⟦would be automatically copied.⟧\n" +
                "-----------------------------------------------------\n" +
                "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied." +
                "";

        assertEquals(expected, wrapText(input, false, 66));
    }

    @Test
    public void test_getOffsetIndex1End() {
        String input = "" +
                "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied." +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[83), i=[83, 83) }\n" +
                "-----------------------------------------------------\n" +
                "⟦Add: configuration for repeated prefixes in items, which would `be⟧\n" +
                "⟦#2` copied when adding/splitting an item. In other words they\n" +
                "⟧⟦would be treated equivalent to task item marker prefix. That way\n" +
                "⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧\n" +
                "⟦would be automatically copied.⟧\n" +
                "-----------------------------------------------------\n" +
                "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied." +
                "";

        assertEquals(expected, wrapText(input, true, 66));
    }

    @Test
    public void test_getOffsetIndex2() {
        String input = "" +
                "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙ adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied." +
                "";

        String expected = "" +
                "OffsetInfo{ p=2, o=[83), i=[83, 83) }\n" +
                "-----------------------------------------------------\n" +
                "⟦Add: configuration for repeated prefixes in items, which would `be⟧\n" +
                "⟦#2` copied when ⟧⟦adding/splitting an item. In other words they\n" +
                "⟧⟦would be treated equivalent to task item marker prefix. That way\n" +
                "⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧\n" +
                "⟦would be automatically copied.⟧\n" +
                "-----------------------------------------------------\n" +
                "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied." +
                "";

        assertEquals(expected, wrapText(input, false, 66));
    }

    @Test
    public void test_getOffsetIndex2End() {
        String input = "" +
                "Add: configuration for repeated prefixes in items, which would `be #2` copied when ⦙ adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied." +
                "";

        String expected = "" +
                "OffsetInfo{ p=2, o=[83), i=[83, 83) }\n" +
                "-----------------------------------------------------\n" +
                "⟦Add: configuration for repeated prefixes in items, which would `be⟧\n" +
                "⟦#2` copied when ⟧⟦adding/splitting an item. In other words they\n" +
                "⟧⟦would be treated equivalent to task item marker prefix. That way\n" +
                "⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧\n" +
                "⟦would be automatically copied.⟧\n" +
                "-----------------------------------------------------\n" +
                "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when ⦙adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied." +
                "";

        assertEquals(expected, wrapText(input, true, 66));
    }

    @Test
    public void test_getOffsetIndexTypedText() {
        String input = "" +
                "text should wrap onto the next t⦙\n" +
                "line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[32), i=[32, 33) }\n" +
                "-----------------------------------------------------\n" +
                "⟦text should wrap onto the next t⟧ ⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "text should wrap onto the next t⟦ ⟧line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, false, 66));
    }

    @Test
    public void test_getOffsetIndexTypedTextEnd() {
        String input = "" +
                "text should wrap onto the next t⦙\n" +
                "line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[32), i=[32, 33) }\n" +
                "-----------------------------------------------------\n" +
                "⟦text should wrap onto the next t⟧ ⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "text should wrap onto the next t⟦ ⟧line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, true, 66));
    }

    @Test
    public void test_getOffsetIndexTypedText2() {
        String input = "" +
                "text should wrap onto the next t⦙\n" +
                "line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=2, o=[32), i=[32, 33) }\n" +
                "-----------------------------------------------------\n" +
                "⟦text should wrap onto the next⟧\n" +
                "⟦t⟧ ⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "text should wrap onto the next\n" +
                "t⟦ ⟧line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, false, 30));
    }

    @Test
    public void test_getOffsetIndexTypedText2End() {
        String input = "" +
                "text should wrap onto the next t⦙\n" +
                "line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=2, o=[32), i=[32, 33) }\n" +
                "-----------------------------------------------------\n" +
                "⟦text should wrap onto the next⟧\n" +
                "⟦t⟧ ⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "text should wrap onto the next\n" +
                "t⟦ ⟧line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, true, 30));
    }

    @Test
    public void test_getOffsetIndexTypedText3() {
        String input = "" +
                "text should wrap onto the next\n" +
                "     ⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[36), i=[31, 31) }\n" +
                "-----------------------------------------------------\n" +
                "⟦text should wrap onto the next\n" +
                "⟧⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "text should wrap onto the next\n" +
                "⦙line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, false, 30));
    }

    @Test
    public void test_getOffsetIndexTypedText3End() {
        String input = "" +
                "text should wrap onto the next\n" +
                "     ⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[36), i=[31, 31) }\n" +
                "-----------------------------------------------------\n" +
                "⟦text should wrap onto the next\n" +
                "⟧⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "text should wrap onto the next\n" +
                "⦙line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, true, 30));
    }

    @Test
    public void test_getOffsetIndexTypedText4() {
        String input = "" +
                "text should wrap onto the next\n" +
                "     ⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[36, 37), i=[38, 39) }\n" +
                "-----------------------------------------------------\n" +
                "⟦⟧    ⟦text should wrap onto the next\n" +
                "  ⟧⟦  line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "    text should wrap onto the next\n" +
                "   ⦙ line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, false, 30, wrapped -> {
            SequenceBuilder indented = wrapped.getBuilder();
            for (BasedSequence line : wrapped.splitListEOL(true)) {
                indented.append("    ");
                indented.append(line);
            }
            return indented.toSequence();
        }));
    }

    @Test
    public void test_getOffsetIndexTypedText4End() {
        String input = "" +
                "text should wrap onto the next\n" +
                "     ⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[36), i=[38, 38) }\n" +
                "-----------------------------------------------------\n" +
                "⟦⟧    ⟦text should wrap onto the next\n" +
                "  ⟧⟦  line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "    text should wrap onto the next\n" +
                "   ⦙ line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, true, 30, wrapped -> {
            SequenceBuilder indented = wrapped.getBuilder();
            for (BasedSequence line : wrapped.splitListEOL(true)) {
                indented.append("    ");
                indented.append(line);
            }
            return indented.toSequence();
        }));
    }

    @Test
    public void test_getOffsetIndexTypedText5() {
        String input = "" +
                "text should wrap onto the next⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[30), i=[34, 35) }\n" +
                "-----------------------------------------------------\n" +
                "⟦⟧    ⟦text should wrap onto the next⟧\n" +
                "⟦⟧    ⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "    text should wrap onto the next⟦\n" +
                "⟧    line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, false, 30, wrapped -> {
            SequenceBuilder indented = wrapped.getBuilder();
            for (BasedSequence line : wrapped.splitListEOL(true)) {
                indented.append("    ");
                indented.append(line);
            }
            return indented.toSequence();
        }));
    }

    @Test
    public void test_getOffsetIndexTypedText5End() {
        String input = "" +
                "text should wrap onto the next⦙ line at right margin of 30" +
                "";

        String expected = "" +
                "OffsetInfo{ p=1, o=[30), i=[34, 35) }\n" +
                "-----------------------------------------------------\n" +
                "⟦⟧    ⟦text should wrap onto the next⟧\n" +
                "⟦⟧    ⟦line at right margin of 30⟧\n" +
                "-----------------------------------------------------\n" +
                "    text should wrap onto the next⟦\n" +
                "⟧    line at right margin of 30" +
                "";

        assertEquals(expected, wrapText(input, true, 30, wrapped -> {
            SequenceBuilder indented = wrapped.getBuilder();
            for (BasedSequence line : wrapped.splitListEOL(true)) {
                indented.append("    ");
                indented.append(line);
            }
            return indented.toSequence();
        }));
    }
}
