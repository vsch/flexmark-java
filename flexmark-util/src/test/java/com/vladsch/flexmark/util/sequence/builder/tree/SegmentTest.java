package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.Seg;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;

public class SegmentTest {

    static void loop(int start, int end, int span, int param, BiConsumer<Integer, Integer> consumer) {
        int iMaxStart = start + span;
        int iMinEnd = end - span;

        if (iMaxStart >= iMinEnd) {
            for (int i = start; i < end; i++) {
                consumer.accept(param, i);
            }
        } else {
            for (int i = start; i < iMaxStart; i++) {
                consumer.accept(param, i);
            }

            for (int i = iMinEnd; i < end; i++) {
                consumer.accept(param, i);
            }
        }
    }

    static void loopSizes(BiConsumer<Integer, Integer> consumer) {
        loop(0, 16, 8, 0, consumer);
        loop(16, 256, 8, 1, consumer);
        loop(256, 65536, 8, 2, consumer);
        loop(65536, 65536 * 256, 8, 3, consumer);
        loop(65536 * 256, Integer.MAX_VALUE, 8, 4, consumer);
    }

    static void loopSizesShort(BiConsumer<Integer, Integer> consumer) {
        loop(0, 16, 8, 0, consumer);
        loop(16, 256, 8, 1, consumer);
        loop(256, 65536, 8, 2, consumer);
        loop(65536, 65536 * 8, 8, 3, consumer);
    }

    static void loopEnd(int startOffset, BiConsumer<Integer, Integer> consumer) {
        loop(startOffset + 0, startOffset + 16, 8, 0, consumer);
        loop(startOffset + 16, startOffset + 256, 8, 1, consumer);
        loop(startOffset + 256, startOffset + 65536, 8, 2, consumer);
        loop(startOffset + 65536, startOffset + 65536 * 256, 8, 3, consumer);
        loop(startOffset + 65536 * 256, Seg.MAX_TEXT_OFFSET, 8, 4, consumer);
    }

    static void loopEndShort(int startOffset, BiConsumer<Integer, Integer> consumer) {
        loop(startOffset + 0, startOffset + 16, 8, 0, consumer);
        loop(startOffset + 16, startOffset + 256, 8, 1, consumer);
        loop(startOffset + 256, startOffset + 65536, 8, 2, consumer);
        loop(startOffset + 65536, startOffset + 65536 * 8, 8, 3, consumer);
    }

    @Test
    public void test_SegAnchorSize() {
        loopSizes((b, i) -> {
            Seg seg = Seg.segOf(i, i);
//            System.out.println(i);
            assertEquals("i: " + i, b + 1, Segment.getSegByteLength(seg, ""));
        });
    }

    @Test
    public void test_SegBaseSize() {
        loopSizes((bi, i) -> {
            loopEnd(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Seg seg = Seg.segOf(i, j);
                    assertEquals("i: " + i + " j: " + j, Math.max(1, bi) + Math.max(1, bj) + 1, Segment.getSegByteLength(seg, ""));
                }
            });
        });
    }

    static class DummyCharSequence implements CharSequence {
        final char myChar;
        final int myLength;

        public DummyCharSequence(char aChar, int length) {
            myChar = aChar;
            myLength = length;
        }

        @Override
        public int length() {
            return myLength;
        }

        @Override
        public char charAt(int index) {
            return myChar;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return new DummyCharSequence(myChar, end - start);
        }

        @NotNull
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(length());
            sb.append(this);
            return sb.toString();
        }
    }

    @Test
    public void test_SegTextSpaceSize() {
        loopSizes((bi, i) -> {
            loopEnd(i, (bj, j) -> {
                if (j > i) {
                    Seg seg = Seg.textOf(i, j, true, true);
                    assertEquals("i: " + i + " j: " + j, bj + 1, Segment.getSegByteLength(seg, new DummyCharSequence(' ', j)));
                }
            });
        });
    }

    @Test
    public void test_SegTextEolSize() {
        loopSizes((bi, i) -> {
            loopEnd(i, (bj, j) -> {
                if (j > i) {
                    Seg seg = Seg.textOf(i, j, true, true);
                    assertEquals("i: " + i + " j: " + j, bj + 1, Segment.getSegByteLength(seg, new DummyCharSequence('\n', j)));
                }
            });
        });
    }

    @Test
    public void test_SegTextRepeatedSize() {
        loopSizes((bi, i) -> {
            loopEnd(i, (bj, j) -> {
                if (j > i) {
                    Seg seg = Seg.textOf(i, j, true, true);
                    assertEquals("i: " + i + " j: " + j, bj + 2, Segment.getSegByteLength(seg, new DummyCharSequence('a', j)));
                }
            });
        });
    }

    @Test
    public void test_SegTextAsciiSize() {
        loopSizes((bi, i) -> {
            loopEnd(i, (bj, j) -> {
                if (j > i) {
                    Seg seg = Seg.textOf(i, j, true, false);
                    assertEquals("i: " + i + " j: " + j, bj + 1 + j - i, Segment.getSegByteLength(seg, new DummyCharSequence('a', j)));
                }
            });
        });
    }

    @Test
    public void test_SegTextSize() {
        loopSizes((bi, i) -> {
            loopEnd(i, (bj, j) -> {
                if (j > i) {
                    Seg seg = Seg.textOf(i, j, false, false);
                    assertEquals("i: " + i + " j: " + j, bj + 1 + (j - i) * 2, Segment.getSegByteLength(seg, new DummyCharSequence('a', j)));
                }
            });
        });
    }

    @Test
    public void test_addIntBytes_getInt() {
        byte[] bytes = new byte[10];

        loopSizes((bi, i) -> {
            for (int j = 0; j < 10 - 4; j++) {
                Arrays.fill(bytes, (byte) 0xff);
                int b = Math.max(1, bi);
                int offset = Segment.addIntBytes(bytes, j, i, b);
                assertEquals("i: " + i + " j: " + j, b + j, offset);

                int value = Segment.getInt(bytes, j, b);
                assertEquals("i: " + i + " j: " + j, value, (int) i);
            }
        });
    }

    @Test
    public void test_addChars_getChar() {
        byte[] bytes = new byte[10];

        for (char c = 0; ; c++) {
            CharSequence chars = new DummyCharSequence(c, 10);
            for (int j = 0; j < 10 - 4; j++) {
                Arrays.fill(bytes, (byte) 0xff);
                int offset = Segment.addChars(bytes, j, chars, j, j + 1);
                assertEquals("c: " + c + " j: " + j, j + 2, offset);

                char value = Segment.getChar(bytes, j);
                assertEquals("c: " + c + " j: " + j, value, c);
            }

            if (c == 0xffff) break;
        }
    }

    @Test
    public void test_addChar_getChar() {
        byte[] bytes = new byte[10];

        for (char c = 0; ; c++) {
            for (int j = 0; j < 10 - 4; j++) {
                Arrays.fill(bytes, (byte) 0xff);
                int offset = Segment.addChar(bytes, j, c);
                assertEquals("c: " + c + " j: " + j, j + 2, offset);

                char value = Segment.getChar(bytes, j);
                assertEquals("c: " + c + " j: " + j, value, c);
            }

            if (c == 0xffff) break;
        }
    }

    @Test
    public void test_addCharsAscii_getCharAscii() {
        byte[] bytes = new byte[10];

        for (char c = 0; c < 256; c++) {
            CharSequence chars = new DummyCharSequence(c, 10);
            for (int j = 0; j < 10 - 4; j++) {
                Arrays.fill(bytes, (byte) 0xff);
                int offset = Segment.addCharsAscii(bytes, j, chars, j, j + 1);
                assertEquals("c: " + c + " j: " + j, j + 1, offset);

                char value = Segment.getCharAscii(bytes, j);
                assertEquals("c: " + c + " j: " + j, value, c);
            }
        }
    }

    @Test
    public void test_addCharAscii_getCharAscii() {
        byte[] bytes = new byte[10];

        for (char c = 0; c < 256; c++) {
            for (int j = 0; j < 10 - 4; j++) {
                int offset = Segment.addCharAscii(bytes, j, c);
                assertEquals("c: " + c + " j: " + j, j + 1, offset);

                char value = Segment.getCharAscii(bytes, j);
                assertEquals("c: " + c + " j: " + j, value, c);
            }
        }
    }

    @Test
    public void test_SegBase() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            loopEndShort(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                    CharSequence dummy = new DummyCharSequence('a', j);
                    BasedSequence basedSequence = BasedSequence.of(dummy);
                    Seg seg = Seg.segOf(i, j);

                    int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                    int segByteLength = Segment.getSegByteLength(seg, dummy);
                    assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                    Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                    assertEquals(segByteLength, value.getByteLength());
                    assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
                }
            });
        });
    }

    @Test
    public void test_SegAnchor() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            for (int j = 0; j < 10; j++) {
//                    System.out.println("i: " + i + " j: " + j);
                Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                CharSequence dummy = new DummyCharSequence('a', j);
                BasedSequence basedSequence = BasedSequence.of(dummy);
                Seg seg = Seg.segOf(i, i);

                int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                int segByteLength = Segment.getSegByteLength(seg, dummy);
                assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                assertEquals(segByteLength, value.getByteLength());
                assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
            }
        });
    }

    @Test
    public void test_SegText() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            loopEndShort(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                    CharSequence dummy = new DummyCharSequence('a', j);
                    BasedSequence basedSequence = BasedSequence.of(dummy);
                    Seg seg = Seg.textOf(i, j, false, false);

                    int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                    int segByteLength = Segment.getSegByteLength(seg, dummy);
                    assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                    Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                    assertEquals(segByteLength, value.getByteLength());
                    assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
                }
            });
        });
    }

    @Test
    public void test_SegTextAscii() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            loopEndShort(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                    CharSequence dummy = new DummyCharSequence('a', j);
                    BasedSequence basedSequence = BasedSequence.of(dummy);
                    Seg seg = Seg.textOf(i, j, true, false);

                    int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                    int segByteLength = Segment.getSegByteLength(seg, dummy);
                    assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                    Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                    assertEquals(segByteLength, value.getByteLength());
                    assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
                }
            });
        });
    }

    @Test
    public void test_SegSpaces() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            loopEndShort(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                    CharSequence dummy = new DummyCharSequence(' ', j);
                    BasedSequence basedSequence = BasedSequence.of(dummy);
                    Seg seg = Seg.textOf(i, j, true, true);

                    int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                    int segByteLength = Segment.getSegByteLength(seg, dummy);
                    assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                    Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                    assertEquals(segByteLength, value.getByteLength());
                    assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
                }
            });
        });
    }

    @Test
    public void test_SegEol() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            loopEndShort(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                    CharSequence dummy = new DummyCharSequence('\n', j);
                    BasedSequence basedSequence = BasedSequence.of(dummy);
                    Seg seg = Seg.textOf(i, j, true, true);

                    int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                    int segByteLength = Segment.getSegByteLength(seg, dummy);
                    assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                    Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                    assertEquals(segByteLength, value.getByteLength());
                    assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
                }
            });
        });
    }

    @Test
    public void test_SegRepAscii() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            loopEndShort(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                    CharSequence dummy = new DummyCharSequence('a', j);
                    BasedSequence basedSequence = BasedSequence.of(dummy);
                    Seg seg = Seg.textOf(i, j, true, true);

                    int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                    int segByteLength = Segment.getSegByteLength(seg, dummy);
                    assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                    Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                    assertEquals(segByteLength, value.getByteLength());
                    assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
                }
            });
        });
    }

    @Test
    public void test_SegRepText() {
        byte[] bytes = new byte[65536 * 256 + 32];

        loopSizesShort((bi, i) -> {
            loopEndShort(i, (bj, j) -> {
                if (j > i) {
//                    System.out.println("i: " + i + " j: " + j);
                    Arrays.fill(bytes, Math.max(0, j - 1000), Math.min(bytes.length, j + 1000), (byte) 0xff);
                    CharSequence dummy = new DummyCharSequence('…', j);
                    BasedSequence basedSequence = BasedSequence.of(dummy);
                    Seg seg = Seg.textOf(i, j, false, true);

                    int offset = Segment.addSegBytes(bytes, j, seg, dummy);
                    int segByteLength = Segment.getSegByteLength(seg, dummy);
                    assertEquals("i: " + i + " j: " + j, j + segByteLength, offset);

                    Segment value = Segment.getSegment(bytes, j, 0, 0, basedSequence);
                    assertEquals(segByteLength, value.getByteLength());
                    assertEquals("i: " + i + " j: " + j, seg.toString(dummy), value.toString());
                }
            });
        });
    }
}
