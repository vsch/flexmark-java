package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings({ "EqualsBetweenInconvertibleTypes", "SimplifiableJUnitAssertion" })
public class BaseSequenceEntryTest {
    @Test
    public void test_basic() {
        String input1 = "0123456789";
        String input2 = "0123456789";
        String input3 = "0123456789";
        String input4 = "012345678";
        String input5 = "1234567890";

        // NOTE: must use crate() directly so as not to get cached bases from BasedSequence.of() use through BasedSequenceImpl
        BasedSequence sequence1 = BasedSequenceImpl.create(input1, 0, input1.length());
        BasedSequence sequence2 = BasedSequenceImpl.create(input2, 0, input2.length());
        BasedSequence sequence3 = BasedSequenceImpl.create(input3, 0, input3.length());
        BasedSequence sequence4 = BasedSequenceImpl.create(input4, 0, input4.length());
        BasedSequence sequence5 = BasedSequenceImpl.create(input5, 0, input5.length());

        BaseSequenceEntry entry = new BaseSequenceEntry();

        // getEqualsCall(): 0 - quick class and/or length, 1 - hash, 2 - quick lookup, 3 - content
        assertTrue(entry.testEquals(sequence1, input1));
        assertEquals(0, entry.getEqualsCall());

        assertTrue(entry.testEquals(sequence1, input2));
        assertEquals(0, entry.getEqualsCall());
        assertTrue(entry.testEquals(sequence1, input3));
        assertEquals(0, entry.getEqualsCall());
        assertFalse(entry.testEquals(sequence1, input4));
        assertEquals(0, entry.getEqualsCall());
        assertFalse(entry.testEquals(sequence1, input5));
        assertEquals(1, entry.getEqualsCall());
        assertTrue(entry.testEquals(sequence1, sequence1));
        assertEquals(0, entry.getEqualsCall());

        // first time around expensive content comparison, second time hash map lookup i
        assertTrue(entry.testEquals(sequence1, sequence2));
        assertEquals(3, entry.getEqualsCall());
        assertTrue(entry.testEquals(sequence1, sequence2));
        assertEquals(2, entry.getEqualsCall());

        sequence2 = null;

        // should release weak hash map ref to sequence2
        System.gc();

        sequence2 = BasedSequenceImpl.create(input2, 0, input2.length());

        // first time around expensive content comparison, second time hash map lookup i
        assertTrue(entry.testEquals(sequence1, sequence2));
        assertEquals(3, entry.getEqualsCall());
        assertTrue(entry.testEquals(sequence1, sequence2));
        assertEquals(2, entry.getEqualsCall());

        assertTrue(entry.testEquals(sequence1, sequence3));
        assertEquals(2, entry.getEqualsCall());

        assertFalse(entry.testEquals(sequence1, sequence4));
        assertEquals(0, entry.getEqualsCall());

        assertFalse(entry.testEquals(sequence1, sequence5));
        assertEquals(1, entry.getEqualsCall());
    }
}
