package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.Utils.escapeJavaString;

/**
 * SegmentedSequence Segment stored in byte[] in serialized format
 */
public abstract class Segment {
    // @formatter:off
    final static int TYPE_MASK              = 0b0000_0000_1110_0000;

    final static int TYPE_NO_SIZE_BYTES     = 0b0000_0000_0001_0000;
    final static int TYPE_START_BYTES       = 0b0000_0000_0000_0011;
    final static int TYPE_LENGTH_BYTES      = 0b0000_0000_0000_1100;

    final static int TYPE_ANCHOR            = 0b0000_0000_0000_0000;
    final static int TYPE_BASE              = 0b0000_0000_0010_0000;
    final static int TYPE_TEXT              = 0b0000_0000_0100_0000;
    final static int TYPE_REPEATED_TEXT     = 0b0000_0000_0110_0000;
    final static int TYPE_TEXT_ASCII        = 0b0000_0000_1000_0000;
    final static int TYPE_REPEATED_ASCII    = 0b0000_0000_1010_0000;
    final static int TYPE_REPEATED_SPACE    = 0b0000_0000_1100_0000;
    final static int TYPE_REPEATED_EOL      = 0b0000_0000_1110_0000;

    final static int TYPE_HAS_OFFSET        = 0b0000_0001_0000_0000;
    final static int TYPE_HAS_LENGTH        = 0b0000_0010_0000_0000;
    final static int TYPE_HAS_BOTH          = 0b0000_0011_0000_0000;
    final static int TYPE_HAS_CHAR          = 0b0000_0100_0000_0000;
    final static int TYPE_HAS_CHARS         = 0b0000_1000_0000_0000;
    final static int TYPE_HAS_BYTE          = 0b0001_0000_0000_0000;
    final static int TYPE_HAS_BYTES         = 0b0010_0000_0000_0000;

// @formatter:on

    public enum SegType {
        ANCHOR(TYPE_ANCHOR | TYPE_HAS_OFFSET),
        BASE(TYPE_BASE | TYPE_HAS_BOTH),
        TEXT(TYPE_TEXT | TYPE_HAS_LENGTH | TYPE_HAS_CHARS),
        REPEATED_TEXT(TYPE_REPEATED_TEXT | TYPE_HAS_LENGTH | TYPE_HAS_CHAR),
        TEXT_ASCII(TYPE_TEXT_ASCII | TYPE_HAS_LENGTH | TYPE_HAS_BYTES),
        REPEATED_ASCII(TYPE_REPEATED_ASCII | TYPE_HAS_LENGTH | TYPE_HAS_BYTE),
        REPEATED_SPACE(TYPE_REPEATED_SPACE | TYPE_HAS_LENGTH),
        REPEATED_EOL(TYPE_REPEATED_EOL | TYPE_HAS_LENGTH),
        ;

        final public int flags;

        SegType(int flags) {
            this.flags = flags;
        }

        public boolean hasAll(int flags) {
            return (this.flags & flags) == flags;
        }

        public boolean hasLength() {
            return hasAll(TYPE_HAS_LENGTH);
        }

        public boolean hasOffset() {
            return hasAll(TYPE_HAS_OFFSET);
        }

        public boolean hasBoth() {
            return hasAll(TYPE_HAS_BOTH);
        }

        public boolean hasChar() {
            return hasAll(TYPE_HAS_CHAR);
        }

        public boolean hasChars() {
            return hasAll(TYPE_HAS_CHARS);
        }

        public boolean hasByte() {
            return hasAll(TYPE_HAS_BYTE);
        }

        public boolean hasBytes() {
            return hasAll(TYPE_HAS_BYTES);
        }

        public static SegType fromTypeMask(int segTypeMask) {
            switch (segTypeMask & TYPE_MASK) {
// @formatter:off
                case TYPE_ANCHOR: return SegType.ANCHOR;
                case TYPE_BASE: return SegType.BASE;
                case TYPE_TEXT: return SegType.TEXT;
                case TYPE_TEXT_ASCII: return SegType.TEXT_ASCII;
                case TYPE_REPEATED_TEXT: return SegType.REPEATED_TEXT;
                case TYPE_REPEATED_ASCII: return SegType.REPEATED_ASCII;
                case TYPE_REPEATED_SPACE: return SegType.REPEATED_SPACE;
                case TYPE_REPEATED_EOL: return SegType.REPEATED_EOL;
// @formatter:on

                default:
                    throw new IllegalStateException(String.format("Invalid text type %02x", segTypeMask));
            }
        }
    }

    public boolean hasAll(int flags, int mask) {
        return (flags & mask) == mask;
    }

    protected final int myPos;  // position of segment in aggr length table
    protected final byte[] myBytes;
    protected final int myByteOffset;
    protected final int myStartIndex;

    public Segment(int pos, byte[] bytes, int byteOffset, int startIndex) {
        myPos = pos;
        myBytes = bytes;
        myByteOffset = byteOffset;
        myStartIndex = startIndex;
    }

    public int getPos() {
        return myPos;
    }

    public byte[] getBytes() {
        return myBytes;
    }

    final public int getByteOffset() {
        return myByteOffset;
    }

    final public int getStartIndex() {
        return myStartIndex;
    }

    final public int getEndIndex() {
        return myStartIndex + length();
    }

    public boolean notInSegment(int index) {
        return index < myStartIndex || index >= myStartIndex + length();
    }


    final public SegType getType() {
        return SegType.fromTypeMask(myBytes[myByteOffset]);
    }

    final public int getByteLength() {
        return getSegByteLength(getType(), getStartOffset(), length());
    }

    public abstract int length();
    public abstract boolean isBase();
    public abstract boolean isAnchor();
    public abstract boolean isText();
    public abstract boolean isFirst256Start();
    public abstract boolean isRepeatedTextEnd();
    public abstract int getStartOffset();
    public abstract int getEndOffset();
    public abstract CharSequence getCharSequence();
    /**
     * get char at index
     *
     * @param index index in segmented sequence coordinates. index offset must be subtracted to convert to segment coordinates
     * @return character at given index in segmented sequence
     */
    public abstract char charAt(int index);

    @Override
    public String toString() {
        if (isBase()) {
            if (isAnchor()) {
                return "[" + getStartOffset() + ")";
            } else {
                return "[" + getStartOffset() + ", " + getEndOffset() + ")";
            }
        } else {
            CharSequence charSequence = getCharSequence();
            if (isRepeatedTextEnd() && length() > 1) {
                if (isFirst256Start()) {
                    return "a:" + (length() + "x'" + escapeJavaString(charSequence.subSequence(0, 1)) + "'");
                } else {
                    return "" + (length() + "x'" + escapeJavaString(charSequence.subSequence(0, 1)) + "'");
                }
            } else {
                int length = charSequence.length();
                String chars = length <= 20 ? charSequence.toString() : charSequence.subSequence(0, 10).toString() + "…" + charSequence.subSequence(length - 10, length).toString();
                if (isFirst256Start()) {
                    return "a:'" + escapeJavaString(chars) + "'";
                } else {
                    return "'" + escapeJavaString(chars) + "'";
                }
            }
        }
    }

    static class Base extends Segment {
        protected final int myStartOffset;
        protected final int myEndOffset;
        protected final @NotNull BasedSequence myBasedSequence;

        public Base(int pos, byte[] bytes, int byteOffset, int indexOffset, @NotNull BasedSequence basedSequence) {
            super(pos, bytes, byteOffset, indexOffset);

            myBasedSequence = basedSequence;

            int type = bytes[byteOffset++] & 0x00ff;
            if ((type & TYPE_MASK) == TYPE_ANCHOR) {
                if (hasAll(type, TYPE_NO_SIZE_BYTES)) {
                    myEndOffset = myStartOffset = type & 0x000f;
                } else {
                    int intBytes = type & TYPE_START_BYTES;
                    myEndOffset = myStartOffset = getInt(bytes, byteOffset, intBytes + 1);
                }
            } else {
                assert !hasAll(type, TYPE_NO_SIZE_BYTES);

                int intBytes = type & TYPE_START_BYTES;
                myStartOffset = getInt(bytes, byteOffset, intBytes + 1);
                byteOffset += intBytes + 1;

                int lengthBytes = (type & TYPE_LENGTH_BYTES) >> 2;
                myEndOffset = myStartOffset + getInt(bytes, byteOffset, lengthBytes + 1);
            }
        }

        @Override
        public int length() {
            return myEndOffset - myStartOffset;
        }

        @Override
        public boolean isBase() {
            return true;
        }

        @Override
        public boolean isAnchor() {
            return myStartOffset == myEndOffset;
        }

        @Override
        public boolean isText() {
            return false;
        }

        @Override
        public boolean isFirst256Start() {
            return false;
        }

        @Override
        public boolean isRepeatedTextEnd() {
            return false;
        }

        @Override
        public int getStartOffset() {
            return myStartOffset;
        }

        @Override
        public int getEndOffset() {
            return myEndOffset;
        }

        @Override
        public char charAt(int index) {
            if (index < myStartIndex || index - myStartIndex >= length()) {
                throw new IndexOutOfBoundsException("index " + index + " out of bounds [" + myStartIndex + ", " + myStartIndex +  length() + ")");
            }
            return myBasedSequence.charAt(myStartOffset + index - myStartIndex);
        }

        @Override
        public CharSequence getCharSequence() {
            return myBasedSequence.subSequence(myStartOffset, myEndOffset);
        }
    }

    static abstract class TextCharSequenceBase implements CharSequence {
        protected final byte[] myBytes;
        protected final int myByteOffset;  // byte offset of first byte of chars for original base sequence
        protected final int myStartOffset;
        protected final int myLength;

        public TextCharSequenceBase(byte[] bytes, int byteOffset, int startOffset, int length) {
            myBytes = bytes;
            myByteOffset = byteOffset;
            myStartOffset = startOffset;
            myLength = length;
        }

        @Override
        public int length() {
            return myLength;
        }

        @Override
        public abstract char charAt(int index);

        abstract CharSequence create(int startOffset, int length);

        @Override
        public CharSequence subSequence(int startIndex, int endIndex) {
            if (startIndex < 0 || startIndex > endIndex || endIndex > myLength) {
                throw new IndexOutOfBoundsException("Invalid index range [" + startIndex + ", " + endIndex + "] out of bounds [0, " + length() + ")");
            }
            return create(myStartOffset + startIndex, endIndex - startIndex);
        }

        @NotNull
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < myLength; i++) {
                sb.append(charAt(i));
            }
            return sb.toString();
        }
    }

    static class TextCharSequence extends TextCharSequenceBase {
        public TextCharSequence(byte[] bytes, int byteOffset, int startOffset, int length) {
            super(bytes, byteOffset, startOffset, length);
        }

        @Override
        public char charAt(int index) {
            if (index < 0 || index >= myLength) {
                throw new IndexOutOfBoundsException("index " + index + " out of bounds [0, " + myLength + ")");
            }
            return getChar(myBytes, myByteOffset + (myStartOffset + index) * 2);
        }

        @Override
        CharSequence create(int startOffset, int length) {
            return new TextCharSequence(myBytes, myByteOffset, startOffset, length);
        }
    }

    static class TextAsciiCharSequence extends TextCharSequenceBase {
        public TextAsciiCharSequence(byte[] bytes, int byteOffset, int startOffset, int length) {
            super(bytes, byteOffset, startOffset, length);
        }

        @Override
        public char charAt(int index) {
            if (index < 0 || index >= myLength) {
                throw new IndexOutOfBoundsException("index " + index + " out of bounds [0, " + myLength + ")");
            }

            return (char) (0x00ff & myBytes[myByteOffset + myStartOffset + index]);
        }

        @Override
        CharSequence create(int startOffset, int length) {
            return new TextAsciiCharSequence(myBytes, myByteOffset, startOffset, length);
        }
    }

    static class TextRepeatedSequence implements CharSequence {
        protected final char myChar;
        protected final int myLength;

        public TextRepeatedSequence(char c, int length) {
            myChar = c;
            myLength = length;
        }

        @Override
        public int length() {
            return myLength;
        }

        @Override
        public char charAt(int index) {
            if (index < 0 || index >= myLength) {
                throw new IndexOutOfBoundsException("index " + index + " out of bounds [0, " + myLength + ")");
            }
            return myChar;
        }

        @Override
        public CharSequence subSequence(int startIndex, int endIndex) {
            if (startIndex < 0 || startIndex > endIndex || endIndex > myLength) {
                throw new IndexOutOfBoundsException("Invalid index range [" + startIndex + ", " + endIndex + "] out of bounds [0, " + length() + ")");
            }
            return new TextRepeatedSequence(myChar, endIndex - startIndex);
        }

        @NotNull
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < myLength; i++) {
                sb.append(myChar);
            }
            return sb.toString();
        }
    }

    static class Text extends Segment {
        protected final @NotNull CharSequence myCharSequence;

        public Text(int pos, byte[] bytes, int byteOffset, int indexOffset) {
            super(pos, bytes, byteOffset, indexOffset);
            int type = bytes[byteOffset++] & 0x00ff;
            int segTypeMask = type & TYPE_MASK;

            int length;

            if (hasAll(type, TYPE_NO_SIZE_BYTES)) {
                length = type & 0x000f;
            } else {
                int lengthBytes = (type & TYPE_LENGTH_BYTES) >> 2;
                length = getInt(bytes, byteOffset, lengthBytes + 1);
                byteOffset += lengthBytes + 1;
            }

            switch (segTypeMask) {
                case TYPE_TEXT:
                    myCharSequence = new TextCharSequence(bytes, byteOffset, 0, length);
                    break;

                case TYPE_TEXT_ASCII:
                    myCharSequence = new TextAsciiCharSequence(bytes, byteOffset, 0, length);
                    break;

                case TYPE_REPEATED_TEXT:
                    myCharSequence = new TextRepeatedSequence(getChar(bytes, byteOffset), length);
                    break;

                case TYPE_REPEATED_ASCII:
                    myCharSequence = new TextRepeatedSequence((char) (0x00ff & bytes[byteOffset]), length);
                    break;

                case TYPE_REPEATED_SPACE:
                    myCharSequence = new TextRepeatedSequence(' ', length);
                    break;

                case TYPE_REPEATED_EOL:
                    myCharSequence = new TextRepeatedSequence('\n', length);
                    break;

                default:
                    throw new IllegalStateException("Invalid text type " + segTypeMask);
            }
        }

        @Override
        public int length() {
            return myCharSequence.length();
        }

        @Override
        public char charAt(int index) {
            if (index < myStartIndex || index - myStartIndex >= myCharSequence.length()) {
                throw new IndexOutOfBoundsException("index " + index + " out of bounds [" + myStartIndex + ", " + myStartIndex + myCharSequence.length() + ")");
            }
            return myCharSequence.charAt(index - myStartIndex);
        }

        @Override
        public boolean isBase() {
            return false;
        }

        @Override
        public boolean isAnchor() {
            return false;
        }

        @Override
        public boolean isText() {
            return true;
        }

        int textType() {
            return myBytes[myByteOffset] & TYPE_MASK;
        }

        @Override
        public boolean isFirst256Start() {
            int textType = textType();
            return textType == TYPE_TEXT_ASCII || textType == TYPE_REPEATED_ASCII || textType == TYPE_REPEATED_SPACE || textType == TYPE_REPEATED_EOL;
        }

        @Override
        public boolean isRepeatedTextEnd() {
            int textType = textType();
            return textType == TYPE_REPEATED_TEXT || textType == TYPE_REPEATED_ASCII || textType == TYPE_REPEATED_SPACE || textType == TYPE_REPEATED_EOL;
        }

        @Override
        public int getStartOffset() {
            return -1;
        }

        @Override
        public int getEndOffset() {
            return -1;
        }

        @NotNull
        @Override
        public CharSequence getCharSequence() {
            return myCharSequence;
        }
    }

    public static Segment getSegment(byte[] bytes, int byteOffset, int pos, int indexOffset, @NotNull BasedSequence basedSequence) {
        int type = bytes[byteOffset] & TYPE_MASK;

        switch (type) {
            case TYPE_ANCHOR:
            case TYPE_BASE:
                return new Base(pos, bytes, byteOffset, indexOffset, basedSequence);

            case TYPE_TEXT:
            case TYPE_REPEATED_TEXT:
            case TYPE_TEXT_ASCII:
            case TYPE_REPEATED_ASCII:
            case TYPE_REPEATED_SPACE:
            case TYPE_REPEATED_EOL:
                return new Text(pos, bytes, byteOffset, indexOffset);

            default:
                throw new IllegalStateException("Invalid text type " + type);
        }
    }

    public static SegType getSegType(@NotNull Seg seg, @NotNull CharSequence textChars) {
        if (seg.isBase()) {
            return seg.isAnchor() ? SegType.ANCHOR : SegType.BASE;
        } else if (seg.isText()) {
            boolean first256Start = seg.isFirst256Start();
            boolean repeatedTextEnd = seg.isRepeatedTextEnd();

            if (first256Start) {
                // ascii text
                if (repeatedTextEnd) {
                    // repeated chars
                    char c = textChars.charAt(seg.getTextStart());
                    if (c == ' ') return SegType.REPEATED_SPACE;
                    else if (c == '\n') return SegType.REPEATED_EOL;
                    else return SegType.REPEATED_ASCII;
                } else {
                    return SegType.TEXT_ASCII;
                }
            } else {
                return repeatedTextEnd ? SegType.REPEATED_TEXT : SegType.TEXT;
            }
        } else {
            throw new IllegalStateException("Unknown seg type " + seg);
        }
    }

    public static int getOffsetBytes(int offset) {
        return offset < 16 ? 0 : offset < 256 ? 1 : offset < 65536 ? 2 : offset < 65536 * 256 ? 3 : 4;
    }

    public static int getLengthBytes(int length) {
        return length < 16 ? 0 : length < 256 ? 1 : length < 65536 ? 2 : length < 65536 * 256 ? 3 : 4;
    }

    public static int getIntBytes(int length) {
        return length < 256 ? 1 : length < 65536 ? 2 : length < 65536 * 256 ? 3 : 4;
    }

    public static int getSegByteLength(@NotNull Segment.SegType segType, int segStart, int segLength) {
        int length = 1;

        if (segType.hasBoth()) {
            length += getIntBytes(segStart) + getIntBytes(segLength);
        } else if (segType.hasOffset()) {
            length += getOffsetBytes(segStart);
        } else if (segType.hasLength()) {
            length += getLengthBytes(segLength);
        }

        if (segType.hasChar()) length += 2;
        else if (segType.hasChars()) length += 2 * segLength;
        else if (segType.hasByte()) length += 1;
        else if (segType.hasBytes()) length += segLength;

        return length;
    }

    public static int getSegByteLength(@NotNull Seg seg, @NotNull CharSequence textChars) {
        SegType segType = getSegType(seg, textChars);
        return getSegByteLength(segType, seg.getSegStart(), seg.length());
    }

    public static int addIntBytes(byte[] bytes, int offset, int value, int count) {
        switch (count) {
            case 4:
                bytes[offset++] = (byte) ((value & 0xff000000) >> 24);
            case 3:
                bytes[offset++] = (byte) ((value & 0x00ff0000) >> 16);
            case 2:
                bytes[offset++] = (byte) ((value & 0x0000ff00) >> 8);
            case 1:
                bytes[offset++] = (byte) (value & 0x000000ff);
        }
        return offset;
    }

    public static int getInt(byte[] bytes, int offset, int count) {
        int value = 0;

        switch (count) {
            case 4:
                value |= (0x00ff & bytes[offset++]) << 24;
            case 3:
                value |= (0x00ff & bytes[offset++]) << 16;
            case 2:
                value |= (0x00ff & bytes[offset++]) << 8;
            case 1:
                value |= (0x00ff & bytes[offset]);
        }
        return value;
    }

    public static int addChar(byte[] bytes, int offset, char c) {
        bytes[offset++] = (byte) ((c & 0x0000ff00) >> 8);
        bytes[offset++] = (byte) (c & 0x000000ff);
        return offset;
    }

    public static char getChar(byte[] bytes, int offset) {
        char c = (char) ((0x00ff & bytes[offset++]) << 8);
        c |= 0x00ff & bytes[offset];
        return c;
    }

    public static int addChars(byte[] bytes, int offset, @NotNull CharSequence chars, int start, int end) {
        for (int i = start; i < end; i++) {
            char c = chars.charAt(i);
            bytes[offset++] = (byte) ((c & 0x0000ff00) >> 8);
            bytes[offset++] = (byte) (c & 0x000000ff);
        }
        return offset;
    }

    public static int addCharAscii(byte[] bytes, int offset, char c) {
        assert c < 256;
        bytes[offset++] = (byte) (c & 0x000000ff);
        return offset;
    }

    public static int addCharsAscii(byte[] bytes, int offset, @NotNull CharSequence chars, int start, int end) {
        for (int i = start; i < end; i++) {
            char c = chars.charAt(i);
            assert c < 256;
            bytes[offset++] = (byte) (c & 0x000000ff);
        }
        return offset;
    }

    public static char getCharAscii(byte[] bytes, int offset) {
        return (char) (0x00ff & bytes[offset]);
    }

    public static int addSegBytes(byte[] bytes, int offset, @NotNull Seg seg, @NotNull CharSequence textChars) {
        SegType segType = getSegType(seg, textChars);
        int segLength = seg.length();

        if (segType.hasOffset()) {
            int segStart = seg.getStart();

            if (segType.hasLength()) {
                int offsetBytes = getIntBytes(segStart);
                int intBytes = getIntBytes(segLength);

                bytes[offset++] = (byte) (segType.flags | (offsetBytes - 1) | ((intBytes - 1) << 2));
                offset = addIntBytes(bytes, offset, segStart, offsetBytes);
                offset = addIntBytes(bytes, offset, segLength, intBytes);
            } else {
                int offsetBytes = getOffsetBytes(segStart);
                if (offsetBytes == 0) {
                    assert segStart < 16;
                    bytes[offset++] = (byte) (segType.flags | TYPE_NO_SIZE_BYTES | segStart);
                } else {
                    bytes[offset++] = (byte) (segType.flags | (offsetBytes - 1));
                    offset = addIntBytes(bytes, offset, segStart, offsetBytes);
                }
            }
        } else if (segType.hasLength()) {
            int lengthBytes = getLengthBytes(segLength);
            if (lengthBytes == 0) {
                assert segLength < 16;
                bytes[offset++] = (byte) (segType.flags | TYPE_NO_SIZE_BYTES | segLength);
            } else {
                bytes[offset++] = (byte) (segType.flags | ((lengthBytes - 1) << 2));
                offset = addIntBytes(bytes, offset, segLength, lengthBytes);
            }
        }

        if (segType.hasChar()) offset = addChar(bytes, offset, textChars.charAt(seg.getTextStart()));
        else if (segType.hasChars()) offset = addChars(bytes, offset, textChars, seg.getTextStart(), seg.getTextEnd());
        else if (segType.hasByte()) offset = addCharAscii(bytes, offset, textChars.charAt(seg.getTextStart()));
        else if (segType.hasBytes()) offset = addCharsAscii(bytes, offset, textChars, seg.getTextStart(), seg.getTextEnd());

        return offset;
    }
}
