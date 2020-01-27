package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.misc.DelimitedBuilder;

public class SegmentStats {
    final public static int NULL_REPEATED_CHAR = -1;
    final public static int NOT_REPEATED_CHAR = -2;

    protected int textLength = 0;                 // length of all text in stats
    protected int textSegments = 0;               // total disjoint text segments
    protected int textSegmentLength = 0;          // length at start of last segment

    protected int textSpaceLength = 0;            // length of all space text
    protected int textSpaceSegments = 0;          // total disjoint spaces only segments
    protected int textSpaceSegmentLength = 0;     // length at start of spaces in last segment

    protected int textFirst256Length = 0;         // length of all text chars < 256
    protected int textFirst256Segments = 0;       // total disjoint chars < 256 only segments
    protected int textFirst256SegmentLength = 0;  // length at start of chars < 256 in last segment

    protected int repeatedChar = -1;              // repeated char if all same, -1 if no char, -2 if different chars, must be checked before commit which clears this

    final protected boolean trackFirst256;        // options

    public SegmentStats(boolean trackFirst256) {
        this.trackFirst256 = trackFirst256;
    }

    public int getTextLength() {
        return textLength;
    }

    public int getTextSpaceLength() {
        return textSpaceLength;
    }

    public int getTextFirst256Length() {
        return textFirst256Length;
    }

    public boolean isTrackTextFirst256() {
        return trackFirst256;
    }

    public int getTextSegments() {
        return textSegments;
    }

    public int getTextSpaceSegments() {
        return textSpaceSegments;
    }

    public int getTextFirst256Segments() {
        return textFirst256Segments;
    }

    public boolean isEmpty() {
        return textLength == 0 && textSegments == 0 && textSegmentLength == 0
                && (!trackFirst256
                || textSpaceLength == 0
                && textSpaceSegments == 0
                && textSpaceSegmentLength == 0
                && textFirst256Length == 0
                && textFirst256Segments == 0
                && textFirst256SegmentLength == 0);
    }

    public boolean isValid() {
        return textLength >= textSegments
                && (
                !trackFirst256
                        || textLength >= textFirst256Length && textSegments >= textFirst256Segments && textFirst256Length >= textFirst256Segments
                        && textFirst256Length >= textSpaceLength && textFirst256Segments >= textSpaceSegments && textSpaceLength >= textSpaceSegments);
    }

    public SegmentStats committedCopy() {
        SegmentStats other = new SegmentStats(trackFirst256);
        other.textLength = this.textLength;
        other.textSegments = this.textSegments;
        other.textSegmentLength = this.textSegmentLength;

        if (trackFirst256) {
            other.textSpaceLength = this.textSpaceLength;
            other.textSpaceSegments = this.textSpaceSegments;
            other.textSpaceSegmentLength = this.textSpaceSegmentLength;
            other.textFirst256Length = this.textFirst256Length;
            other.textFirst256Segments = this.textFirst256Segments;
            other.textFirst256SegmentLength = this.textFirst256SegmentLength;
        }

        other.commitText();
        return other;
    }

    public void clear() {
        textLength = 0;
        textSegments = 0;
        textSegmentLength = 0;
        repeatedChar = NULL_REPEATED_CHAR;

        if (trackFirst256) {
            textSpaceLength = 0;
            textSpaceSegments = 0;
            textSpaceSegmentLength = 0;
            textFirst256Length = 0;
            textFirst256Segments = 0;
            textFirst256SegmentLength = 0;
        }
    }

    public void add(SegmentStats other) {
        textLength += other.textLength;
        textSegments += other.textSegments;

        if (trackFirst256 && other.trackFirst256) {
            textSpaceLength += other.textSpaceLength;
            textSpaceSegments += other.textSpaceSegments;
            textFirst256Length += other.textFirst256Length;
            textFirst256Segments += other.textFirst256Segments;
        }
    }

    public void remove(SegmentStats other) {
        assert textLength >= other.textLength;
        assert textSegments >= other.textSegments;
        textLength -= other.textLength;
        textSegments -= other.textSegments;

        // reset segment starts
        textSegmentLength = textLength;

        if (trackFirst256 && other.trackFirst256) {
            assert textSpaceLength >= other.textSpaceLength;
            assert textSpaceSegments >= other.textSpaceSegments;
            assert textFirst256Length >= other.textFirst256Length;
            assert textFirst256Segments >= other.textFirst256Segments;

            textSpaceLength -= other.textSpaceLength;
            textSpaceSegments -= other.textSpaceSegments;
            textFirst256Length -= other.textFirst256Length;
            textFirst256Segments -= other.textFirst256Segments;

            // reset segment starts
            textSpaceSegmentLength = textSpaceLength;
            textFirst256SegmentLength = textFirst256Length;
        }
    }

    public boolean isTextFirst256() {
        int segmentLength = textLength - textSegmentLength;
        return textFirst256Length - textFirst256SegmentLength == segmentLength;
    }

    public boolean isTextRepeatedSpace() {
        int segmentLength = textLength - textSegmentLength;
        return textSpaceLength - textSpaceSegmentLength == segmentLength;
    }

    public boolean isRepeatedText() {
        return repeatedChar >= 0;
    }

    public void commitText() {
        if (textLength > textSegmentLength) {
            textSegments++;
            repeatedChar = NULL_REPEATED_CHAR;

            if (trackFirst256) {
                int segmentLength = textLength - textSegmentLength;

                if (textSpaceLength - textSpaceSegmentLength == segmentLength) textSpaceSegments++;
                if (textFirst256Length - textFirst256SegmentLength == segmentLength) textFirst256Segments++;
            }

            textSegmentLength = textLength;
            if (trackFirst256) {
                textSpaceSegmentLength = textSpaceLength;
                textFirst256SegmentLength = textFirst256Length;
            }
        }
    }

    public void addText(CharSequence text) {
        // need to count spaces in it
        textLength += text.length();

        if (trackFirst256) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);

                if (repeatedChar == NULL_REPEATED_CHAR) {
                    repeatedChar = c;
                } else if (repeatedChar != c) {
                    repeatedChar = NOT_REPEATED_CHAR;
                }

                if (c < 256) {
                    if (c == ' ') textSpaceLength++;
                    textFirst256Length++;
                }
            }
        }
    }

    public void addText(char c) {
        // need to count spaces in it
        textLength += 1;

        if (trackFirst256) {
            if (repeatedChar == NULL_REPEATED_CHAR) {
                repeatedChar = c;
            } else if (repeatedChar != c) {
                repeatedChar = NOT_REPEATED_CHAR;
            }

            if (c < 256) {
                if (c == ' ') textSpaceLength++;
                textFirst256Length++;
            }
        }
    }

    public void addText(char c, int repeat) {
        assert repeat > 0;

        // need to count spaces in it
        textLength += repeat;

        if (trackFirst256) {
            if (repeatedChar == NULL_REPEATED_CHAR) {
                repeatedChar = c;
            } else if (repeatedChar != c) {
                repeatedChar = NOT_REPEATED_CHAR;
            }

            if (c < 256) {
                if (c == ' ') textSpaceLength += repeat;
                textFirst256Length += repeat;
            }
        }
    }

    public void removeText(CharSequence text) {
        textLength -= text.length();

        if (trackFirst256) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);
                if (repeatedChar == NULL_REPEATED_CHAR) {
                    repeatedChar = c;
                } else if (repeatedChar != c) {
                    repeatedChar = NOT_REPEATED_CHAR;
                }

                if (c < 256) {
                    if (c == ' ') {
                        assert textSpaceLength > 0;
                        textSpaceLength--;
                    }

                    assert textFirst256Length > 0;
                    textFirst256Length--;
                }
            }
        }

        // if whole segment was removed, reset repeated char
        if (textLength == textSegmentLength) repeatedChar = NULL_REPEATED_CHAR;
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("s=").append(textSpaceSegments).append(":").append(textSpaceLength).mark()
                .append("u=").append(textFirst256Segments).append(":").append(textFirst256Length).mark()
                .append("t=").append(textSegments).append(":").append(textLength)
        ;

        return sb.toString();
    }
}
