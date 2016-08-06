package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class BasedSequenceImplTest {
    // TODO: need complete tests here

    @Test
    public void testSplitBasic() throws Exception {
        BasedSequence sequence = new SubSequence(" 1,2 , 3 ,4,5,   ");
        List<BasedSequence> list = sequence.split(',', 0, BasedSequence.SPLIT_TRIM_PARTS | BasedSequence.SPLIT_SKIP_EMPTY);
        ArrayList<String> sl = new ArrayList<>(list.size());
        for (BasedSequence basedSequence : list) sl.add(basedSequence.toString());

        assertArrayEquals(new String[] { "1", "2", "3", "4", "5" }, sl.toArray(new String[0]));
    }
}
