package com.vladsch.flexmark.experimental.util.collection;

import org.junit.Assert;
import org.junit.Test;

public class IntegerBitSetTest {
    @Test
    public void name() throws Exception {
        int i;

        i = -1 >>> -2;
    }

    @Test
    public void testBasic() throws Exception {

        int iMax = 128;
        int jMax = 128;
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                BitIntegerSet bits = new BitIntegerSet();
                bits.set(i, i + j);

                int count = bits.cardinality();
                Assert.assertEquals("i:" + i + " j:" + j, j, count);

                for (int k = 0; k < j; k++) {
                    Assert.assertEquals("i:" + i + " j:" + j + " k:" + k, j - k, bits.cardinality(i + k));
                    Assert.assertEquals("i:" + i + " j:" + j + " k:" + k, j - k, bits.cardinality(i + k, i + j));
                    Assert.assertEquals("i:" + i + " j:" + j + " k:" + k, j - k, bits.cardinality(i + k / 2, i + j - k + k / 2));
                }
            }
        }
    }
}
