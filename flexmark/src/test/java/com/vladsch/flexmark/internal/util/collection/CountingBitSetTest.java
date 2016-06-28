package com.vladsch.flexmark.internal.util.collection;

import org.junit.Assert;
import org.junit.Test;

public class CountingBitSetTest {

    @Test
    public void testBasic() throws Exception {

        int iMax = 128;
        int jMax = 32;
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                CountingBitSet bits = new CountingBitSet();
                bits.set(i, i + j);

                if (i == 8 && j == 1) {
                    int tmp = 0;
                }
                Assert.assertEquals("i:" + i + " j:" + j, j, bits.count());

                for (int k = 0; k < j; k++) {
                    Assert.assertEquals("i:" + i + " j:" + j + " k:" + k, j - k, bits.count(i + k));
                    Assert.assertEquals("i:" + i + " j:" + j + " k:" + k, j - k, bits.count(i + k, i + j));
                    Assert.assertEquals("i:" + i + " j:" + j + " k:" + k, j - k, bits.count(i + k / 2, i + j - k + k / 2));
                }
            }
        }
    }
}
