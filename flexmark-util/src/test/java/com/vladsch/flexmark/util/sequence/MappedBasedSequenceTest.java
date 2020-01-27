package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.mappers.ChangeCase;
import com.vladsch.flexmark.util.sequence.mappers.NullEncoder;
import com.vladsch.flexmark.util.sequence.mappers.SpaceMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MappedBasedSequenceTest {
    @Test
    public void test_nullEncoding() {
        String input = "\u0000\n123456789\u0000\nabcdefghij\n\u0000";
        String encodedInput = "\uFFFD\n123456789\uFFFD\nabcdefghij\n\uFFFD";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(NullEncoder.encodeNull);
        BasedSequence mapDecoded = sequence.toMapped(NullEncoder.decodeNull);
        BasedSequence encoded = BasedSequence.of(encodedInput);
        BasedSequence encodedDecoded = encoded.toMapped(NullEncoder.decodeNull);

        // sequences automatically encode nulls
        assertEquals(encodedInput, sequence.toString());

        assertEquals(encodedInput, mapEncoded.toString());
        assertEquals(input, mapDecoded.toString());
        assertEquals(encodedInput, encoded.toString());
        assertEquals(input, encodedDecoded.toString());
    }

    @Test
    public void test_spaceMapping() {
        String input = "\u0020\n123456789\u0020\nabcdefghij\n\u0020";
        String encodedInput = "\u00A0\n123456789\u00A0\nabcdefghij\n\u00A0";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(SpaceMapper.toNonBreakSpace);
        BasedSequence mapDecoded = sequence.toMapped(SpaceMapper.fromNonBreakSpace);
        BasedSequence encoded = BasedSequence.of(encodedInput);
        BasedSequence encodedDecoded = encoded.toMapped(SpaceMapper.fromNonBreakSpace);

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
        assertEquals(input, mapDecoded.toString());
        assertEquals(encodedInput, encoded.toString());
        assertEquals(input, encodedDecoded.toString());
    }

    @Test
    public void test_toLowerCase() {
        String input = "This Is Mixed\n";
        String encodedInput = "this is mixed\n";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(ChangeCase.toLowerCase);

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    @Test
    public void test_unmodifiedBaseSequence() {
        String input = "This Is Mixed\n";
        String encodedInput = "this is mixed\n";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(ChangeCase.toLowerCase);

        assertEquals(input, sequence.toString());
        assertEquals(input, mapEncoded.baseSubSequence(0).toString());
    }

    @Test
    public void test_toUpperCase() {
        String input = "This Is Mixed\n";
        String encodedInput = "THIS IS MIXED\n";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(ChangeCase.toUpperCase);

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    @Test
    public void test_chain() {
        String input = "This Is Mixed\n";
        String encodedInput = "THIS\u00A0IS\u00A0MIXED\n";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(ChangeCase.toUpperCase.andThen(SpaceMapper.toNonBreakSpace));

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    @Test
    public void test_chain2() {
        String input = "This Is Mixed\n";
        String encodedInput = "THIS\u00A0IS\u00A0MIXED\n";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(ChangeCase.toUpperCase).toMapped(SpaceMapper.toNonBreakSpace);

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    @Test
    public void test_segmented() {
        String input = "This Is Mixed\n";
        String encodedInput = "<THIS IS MIXED\n";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(ChangeCase.toUpperCase).prefixWith("<");

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    @Test
    public void test_segmented2() {
        String input = "This Is Mixed\n";
        String encodedInput = "THIS IS MIXED\n>";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence mapEncoded = sequence.toMapped(ChangeCase.toUpperCase).suffixWith(">");

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    // preserve segmented sequence segments
    @Test
    public void test_segmented3() {
        String input = "Fix: mixed task and non-task items, toggle prefix adds it to all instead of removing only task\n    item prefix or adding to only list items. Test is done.";
        String expected = "Fix: mixed task and non-task items, toggle prefix adds it to all instead of removing only\n" +
                "task item prefix or adding to only list items. Test is done.";

        BasedSequence sequence = BasedSequence.of(input);
        BasedSequence wrapped = sequence.subSequence(0, 89).append(SequenceUtils.EOL).append(sequence.subSequence(90, 94)).append(SequenceUtils.SPACE).append(sequence.subSequence(99, 154));
        BasedSequence mapEncoded = wrapped.toMapped(SpaceMapper.fromNonBreakSpace);
        BasedSequence eolAdded = mapEncoded.appendEOL();

        assertEquals(expected, mapEncoded.toString());
        assertEquals(expected + "\n", eolAdded.toString());
    }

    // preserve segmented sequence segments
    @Test
    public void test_segmented4() {
        String input = "Fix: mixed task and non-task items, toggle prefix adds it to all instead of removing only task\n    item prefix or adding to only list items. Test is done.";
        String expected = "Fix: mixed task and non-task items, toggle prefix adds it to all instead of removing only\n" +
                "task item prefix or adding to only list items. Test is done.";

        BasedSequence sequence = BasedSequence.of(input);
        MarkdownParagraph formatter = new MarkdownParagraph(sequence, CharWidthProvider.NULL);
        formatter.setFirstIndent("");
        formatter.setWidth(90);
        formatter.setFirstWidthOffset(0);
        formatter.setKeepSoftBreaks(false); // cannot keep line breaks when formatting as you type
        formatter.setKeepHardBreaks(true);
        BasedSequence wrapped = formatter.wrapTextNotTracked();
        assertEquals(expected, wrapped.toString());

        BasedSegmentBuilder builder = BasedSegmentBuilder.emptyBuilder(sequence);
        wrapped.addSegments(builder);
        assertEquals("⟦Fix: mixed task and non-task items, toggle prefix adds it to all instead of removing only⟧\\n⟦task⟧⟦ ⟧⟦item prefix or adding to only list items. Test is done.⟧", builder.toStringWithRangesVisibleWhitespace());

        BasedSequence mapEncoded = wrapped.toMapped(SpaceMapper.fromNonBreakSpace);
        BasedSequence eolAdded = mapEncoded.appendEOL();
        BasedSegmentBuilder builder2 = BasedSegmentBuilder.emptyBuilder(sequence);
        mapEncoded.addSegments(builder2);
        assertEquals("⟦Fix: mixed task and non-task items, toggle prefix adds it to all instead of removing only⟧\\n⟦task⟧⟦ ⟧⟦item prefix or adding to only list items. Test is done.⟧", builder2.toStringWithRangesVisibleWhitespace());

        assertEquals(expected, mapEncoded.toString());
        assertEquals(expected + "\n", eolAdded.toString());
    }

    // preserve segmented sequence segments
    @Test
    public void test_segmentedNbsp() {
        String input = "[simLink](simLink.md)";
        String expected = "[simLink](simLink.md)";

        BasedSequence sequence = BasedSequence.of(input).toMapped(SpaceMapper.toNonBreakSpace);
        MarkdownParagraph formatter = new MarkdownParagraph(sequence, CharWidthProvider.NULL);
        formatter.setFirstIndent("");
        formatter.setWidth(90);
        formatter.setFirstWidthOffset(0);
        formatter.setKeepSoftBreaks(false); // cannot keep line breaks when formatting as you type
        formatter.setKeepHardBreaks(true);
        BasedSequence wrapped = formatter.wrapTextNotTracked();
        assertEquals(expected, wrapped.toString());

        BasedSegmentBuilder builder = BasedSegmentBuilder.emptyBuilder(sequence);
        wrapped.addSegments(builder);
        assertEquals("⟦[simLink](simLink.md)⟧", builder.toStringWithRangesVisibleWhitespace());
    }

    @Test
    public void test_segmentedNbsp2() {
        String input = "[simLink spaced](simLink.md)";
        String expected = "[simLink spaced](simLink.md)";

        BasedSequence sequence = BasedSequence.of(input).toMapped(SpaceMapper.toNonBreakSpace);
        MarkdownParagraph formatter = new MarkdownParagraph(sequence, CharWidthProvider.NULL);
        formatter.setFirstIndent("");
        formatter.setWidth(90);
        formatter.setFirstWidthOffset(0);
        formatter.setKeepSoftBreaks(false); // cannot keep line breaks when formatting as you type
        formatter.setKeepHardBreaks(true);
        BasedSequence wrapped = formatter.wrapTextNotTracked();
        assertEquals(expected.replace(" ", "\u00A0"), wrapped.toString());

        BasedSegmentBuilder builder = BasedSegmentBuilder.emptyBuilder(sequence);
        wrapped.toMapped(SpaceMapper.fromNonBreakSpace).addSegments(builder);
        assertEquals("⟦[simLink spaced](simLink.md)⟧", builder.toStringWithRangesVisibleWhitespace());
    }
}
