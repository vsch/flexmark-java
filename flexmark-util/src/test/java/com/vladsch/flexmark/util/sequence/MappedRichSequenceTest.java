package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.mappers.ChangeCase;
import com.vladsch.flexmark.util.sequence.mappers.NullEncoder;
import com.vladsch.flexmark.util.sequence.mappers.SpaceMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MappedRichSequenceTest {
    @Test
    public void test_nullEncoding() {
        String input = "\u0000\n123456789\u0000\nabcdefghij\n\u0000";
        String encodedInput = "\uFFFD\n123456789\uFFFD\nabcdefghij\n\uFFFD";

        IRichSequence sequence = RichSequence.of(input, 0, ((CharSequence) input).length());
        IRichSequence mapEncoded = sequence.toMapped(NullEncoder.encodeNull);
        IRichSequence mapDecoded = sequence.toMapped(NullEncoder.decodeNull);
        IRichSequence encoded = RichSequence.of(encodedInput, 0, ((CharSequence) encodedInput).length());
        IRichSequence encodedDecoded = encoded.toMapped(NullEncoder.decodeNull);

        assertEquals(encodedInput, sequence.toString()); // sequences encoded by default
        assertEquals(encodedInput, mapEncoded.toString());
        assertEquals(input, mapDecoded.toString());
        assertEquals(encodedInput, encoded.toString());
        assertEquals(input, encodedDecoded.toString());
    }

    @Test
    public void test_spaceMapping() {
        String input = "\u0020\n123456789\u0020\nabcdefghij\n\u0020";
        String encodedInput = "\u00A0\n123456789\u00A0\nabcdefghij\n\u00A0";

        IRichSequence sequence = RichSequence.of(input, 0, ((CharSequence) input).length());
        IRichSequence mapEncoded = sequence.toMapped(SpaceMapper.toNonBreakSpace);
        IRichSequence mapDecoded = sequence.toMapped(SpaceMapper.fromNonBreakSpace);
        IRichSequence encoded = RichSequence.of(encodedInput, 0, ((CharSequence) encodedInput).length());
        IRichSequence encodedDecoded = encoded.toMapped(SpaceMapper.fromNonBreakSpace);

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

        IRichSequence sequence = RichSequence.of(input, 0, ((CharSequence) input).length());
        IRichSequence mapEncoded = sequence.toMapped(ChangeCase.toLowerCase);

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    @Test
    public void test_toUpperCase() {
        String input = "This Is Mixed\n";
        String encodedInput = "THIS IS MIXED\n";

        IRichSequence sequence = RichSequence.of(input, 0, ((CharSequence) input).length());
        IRichSequence mapEncoded = sequence.toMapped(ChangeCase.toUpperCase);

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
    }

    @Test
    public void test_chainMapper() {
        String input = "This Is Mixed\n";
        String encodedInput = "THIS\u00A0IS\u00A0MIXED\n";

        IRichSequence sequence = RichSequence.of(input, 0, ((CharSequence) input).length());
        MappedRichSequence mapEncoded = (MappedRichSequence) sequence.toMapped(ChangeCase.toUpperCase.andThen(SpaceMapper.toNonBreakSpace));

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
        assertSame(mapEncoded.getBaseSequence(), sequence);
    }

    @Test
    public void test_chainToMapped() {
        String input = "This Is Mixed\n";
        String encodedInput = "THIS\u00A0IS\u00A0MIXED\n";

        IRichSequence sequence = RichSequence.of(input, 0, ((CharSequence) input).length());
        MappedRichSequence mapEncoded = (MappedRichSequence) sequence.toMapped(ChangeCase.toUpperCase).toMapped(SpaceMapper.toNonBreakSpace);

        assertEquals(input, sequence.toString());
        assertEquals(encodedInput, mapEncoded.toString());
        assertSame(mapEncoded.getBaseSequence(), sequence);
    }
}
