package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.mappers.ChangeCase;
import com.vladsch.flexmark.util.mappers.NullEncoder;
import com.vladsch.flexmark.util.mappers.SpaceMapper;
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

        assertEquals(input, sequence.toString());
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
}
