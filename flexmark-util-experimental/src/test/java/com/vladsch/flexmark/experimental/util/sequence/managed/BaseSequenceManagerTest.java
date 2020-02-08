package com.vladsch.flexmark.experimental.util.sequence.managed;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class BaseSequenceManagerTest {
    static BaseSequenceManager INSTANCE = new BaseSequenceManager();

    @Test
    public void test_Reuse() {
        String input1 = "0123456789";
        String input2 = "0123456789";
        String input3 = "0123456789";
        String input4 = "012345678";
        String input5 = "1234567890";

        int[] callType = { 0 };
        BaseSequenceManager manager = INSTANCE;

        BasedSequence sequence1 = manager.getBaseSequence(input1, callType, seq -> BasedSequence.of(seq));
        BasedSequence sequence2 = manager.getBaseSequence(input2, callType, seq -> BasedSequence.of(seq));
        BasedSequence sequence3 = manager.getBaseSequence(input3, callType, seq -> BasedSequence.of(seq));
        BasedSequence sequence4 = manager.getBaseSequence(input4, callType, seq -> BasedSequence.of(seq));
        BasedSequence sequence5 = manager.getBaseSequence(input5, callType, seq -> BasedSequence.of(seq));

        assertSame(sequence1, sequence2);
        assertSame(sequence1, sequence3);
        assertNotEquals(sequence1, sequence4);
        assertNotEquals(sequence1, sequence5);

        assertTrue(sequence1.equals(input1));
        assertTrue(sequence2.equals(input2));
        assertTrue(sequence3.equals(input3));
        assertTrue(sequence4.equals(input4));
        assertTrue(sequence5.equals(input5));
    }

    @Test
    public void test_Release() {
        BaseSequenceManager manager = INSTANCE;
        int[] callType = { 0 };

        // NOTE: must use crate() directly
        BasedSequence sequence1 = manager.getBaseSequence("0123456789", callType, seq -> BasedSequence.of(seq));

        // NOTE: 0 if map lookup, 10 - set search, 20 - construct and add to map/set
        //   with units digit giving max testEquals call type from all tests done
//        System.out.println("CallType: " + callType[0]);
        assertTrue(callType[0] <= 21);

        BasedSequence sequence2 = manager.getBaseSequence("0123456789", callType, seq -> BasedSequence.of(seq));
        assertEquals(0, callType[0]);

        BasedSequence sequence3 = manager.getBaseSequence("0123456789", callType, seq -> BasedSequence.of(seq));
        assertEquals(0, callType[0]);

        BasedSequence sequence4 = manager.getBaseSequence("012345678", callType, seq -> BasedSequence.of(seq));
        assertTrue(callType[0] <= 21);

        BasedSequence sequence5 = manager.getBaseSequence("1234567890", callType, seq -> BasedSequence.of(seq));
        assertTrue(callType[0] <= 21);

        sequence5 = manager.getBaseSequence("1234567890", callType, seq -> BasedSequence.of(seq));
        assertEquals(0, callType[0]);

        assertSame(sequence1, sequence2);
        assertSame(sequence1, sequence3);
        assertNotEquals(sequence1, sequence4);
        assertNotEquals(sequence1, sequence5);

        assertTrue(sequence1.equals("0123456789"));
        assertTrue(sequence2.equals("0123456789"));
        assertTrue(sequence3.equals("0123456789"));
        assertTrue(sequence4.equals("012345678"));
        assertTrue(sequence5.equals("1234567890"));

        // should not release since it is held by sequence1, sequence2 and sequence3
        System.gc();
        sequence1 = manager.getBaseSequence("0123456789", callType, seq -> BasedSequence.of(seq));
        assertEquals(0, callType[0]);

        // should not release since it is held by sequence2 and sequence3
        sequence1 = null;
        System.gc();
        sequence1 = manager.getBaseSequence("0123456789", callType, seq -> BasedSequence.of(seq));
        assertEquals(0, callType[0]);

        // should not release since it is held by sequence2 and sequence3
        sequence1 = null;
        sequence2 = null;
        System.gc();
        sequence1 = manager.getBaseSequence("0123456789", callType, seq -> BasedSequence.of(seq));
        assertEquals(0, callType[0]);

        // should release since not held by anyone
        sequence1 = null;
        sequence2 = null;
        sequence3 = null;
        System.gc();

        sequence1 = manager.getBaseSequence("0123456789", callType, seq -> BasedSequence.of(seq));
        assertTrue(callType[0] <= 21);
    }
}
