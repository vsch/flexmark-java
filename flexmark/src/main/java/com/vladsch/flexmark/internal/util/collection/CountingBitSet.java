package com.vladsch.flexmark.internal.util.collection;

import java.util.BitSet;

public class CountingBitSet extends BitSet {
    private static final byte[] bitCounts = new byte[] {
            0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4,
            1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
            1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
            2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
            2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
            1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
            2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
            2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
            3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
            3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
            4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8
    };

    /*
    private static final byte[] bitsCounts = new byte[256];
    static {
        int iMax = 256;
        for (int mask = 1; mask < 256; mask <<= 1) {
            for (int i = mask; i < iMax; i++) {
                if ((i & mask) != 0) bitsCounts[i]++;
            }
        }

        System.out.print("    private static final byte[] bitCounts = new byte[] {");
        for (int i = 0; i < iMax; i++) {
            if ((i & 15) == 0) System.out.print("\n            ");
            byte count = bitsCounts[i];
            System.out.print(count);
            if (i != 255) System.out.print((i & 15) == 15 ? "," : ", ");
        }
        System.out.println("\n    };\n");
    }
    //*/

    public CountingBitSet() {

    }

    public CountingBitSet(int i) {
        super(i);
    }

    public int count() {
        return count(0, length());
    }

    public int count(int start) {
        return count(start, length());
    }

    public int count(int start, int end) {
        int count = 0;
        if (start >= 0 && end > 0 && start < end) {
            int firstBit = nextSetBit(0);
            int lastBit = previousSetBit(length()) + 1;
            if (start < firstBit) start = firstBit;
            if (end > lastBit) end = lastBit;

            if (start <= end && length() > 0) {
                int firstByte = nextSetBit(0) >> 3;
                int lastByte = previousSetBit(length()) >> 3;
                int startByte = (start >> 3);
                int endByte = (end >> 3);
                int startMask = (0xff << (start & 7)) & 0xff; // 0-FF, 1-FE, 2-FC, 3-FE, 4-F0.... 
                int endMask = (~(0xff << (end & 7))) & 0xff; // 0-0, 1-01, 2-03, 3-07

                if (endMask == 0) {
                    endByte--;
                    endMask = 0xff;
                }
                
                byte[] bytes = this.toByteArray();
                for (int i = startByte; i <= endByte; i++) {
                    if (i >= bytes.length) {
                        int tmp = 0;
                    }
                    int aByte = bytes[i] & 0xff;

                    if (i == startByte) aByte &= startMask;
                    if (i == endByte) aByte &= endMask;

                    count += bitCounts[aByte];
                }
            }
        }

        return count;
    }
}
