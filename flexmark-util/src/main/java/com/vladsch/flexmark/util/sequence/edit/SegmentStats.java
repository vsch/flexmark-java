package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;

public class SegmentStats {
    public static final int NULL_REPEATED_CHAR = -1;
    public static final int NOT_REPEATED_CHAR = -2;

    protected int myTextLength = 0;                 // length of all text in stats
    protected int myTextSegments = 0;               // total disjoint text segments
    protected int myTextSegmentLength = 0;          // length at start of last segment

    protected int myTextSpaceLength = 0;            // length of all space text
    protected int myTextSpaceSegments = 0;          // total disjoint spaces only segments
    protected int myTextSpaceSegmentLength = 0;     // length at start of spaces in last segment

    protected int myTextFirst256Length = 0;         // length of all text chars < 256
    protected int myTextFirst256Segments = 0;       // total disjoint chars < 256 only segments
    protected int myTextFirst256SegmentLength = 0;  // length at start of chars < 256 in last segment

    protected int myRepeatedChar = -1;              // repeated char if all same, -1 if no char, -2 if different chars, must be checked before commit which clears this

    final protected boolean myTrackFirst256;        // options

    public SegmentStats(boolean trackFirst256) {
        myTrackFirst256 = trackFirst256;
    }

    public int getTextLength() {
        return myTextLength;
    }

    public int getTextSpaceLength() {
        return myTextSpaceLength;
    }

    public int getTextFirst256Length() {
        return myTextFirst256Length;
    }

    public boolean isTrackTextFirst256() {
        return myTrackFirst256;
    }

    public int getTextSegments() {
        return myTextSegments;
    }

    public int getTextSpaceSegments() {
        return myTextSpaceSegments;
    }

    public int getTextFirst256Segments() {
        return myTextFirst256Segments;
    }

    public boolean isEmpty() {
        return myTextLength == 0 && myTextSegments == 0 && myTextSegmentLength == 0
                && (!myTrackFirst256
                || myTextSpaceLength == 0
                && myTextSpaceSegments == 0
                && myTextSpaceSegmentLength == 0
                && myTextFirst256Length == 0
                && myTextFirst256Segments == 0
                && myTextFirst256SegmentLength == 0);
    }

    public boolean isValid() {
        return myTextLength >= myTextSegments
                && (
                !myTrackFirst256
                        || myTextLength >= myTextFirst256Length && myTextSegments >= myTextFirst256Segments && myTextFirst256Length >= myTextFirst256Segments
                        && myTextFirst256Length >= myTextSpaceLength && myTextFirst256Segments >= myTextSpaceSegments && myTextSpaceLength >= myTextSpaceSegments);
    }

    public SegmentStats committedCopy() {
        SegmentStats other = new SegmentStats(myTrackFirst256);
        other.myTextLength = this.myTextLength;
        other.myTextSegments = this.myTextSegments;
        other.myTextSegmentLength = this.myTextSegmentLength;

        if (myTrackFirst256) {
            other.myTextSpaceLength = this.myTextSpaceLength;
            other.myTextSpaceSegments = this.myTextSpaceSegments;
            other.myTextSpaceSegmentLength = this.myTextSpaceSegmentLength;
            other.myTextFirst256Length = this.myTextFirst256Length;
            other.myTextFirst256Segments = this.myTextFirst256Segments;
            other.myTextFirst256SegmentLength = this.myTextFirst256SegmentLength;
        }

        other.commitText();
        return other;
    }

    public void clear() {
//        System.out.println("clear()");

        myTextLength = 0;
        myTextSegments = 0;
        myTextSegmentLength = 0;
        myRepeatedChar = NULL_REPEATED_CHAR;

        if (myTrackFirst256) {
            myTextSpaceLength = 0;
            myTextSpaceSegments = 0;
            myTextSpaceSegmentLength = 0;
            myTextFirst256Length = 0;
            myTextFirst256Segments = 0;
            myTextFirst256SegmentLength = 0;
        }
    }

    public void add(SegmentStats other) {
//        System.out.println("add(" + other + ")");

        myTextLength += other.myTextLength;
        myTextSegments += other.myTextSegments;

        if (myTrackFirst256 && other.myTrackFirst256) {
            myTextSpaceLength += other.myTextSpaceLength;
            myTextSpaceSegments += other.myTextSpaceSegments;
            myTextFirst256Length += other.myTextFirst256Length;
            myTextFirst256Segments += other.myTextFirst256Segments;
        }
    }

    public void remove(SegmentStats other) {
//        System.out.println("remove(" + other + ")");

        assert myTextLength >= other.myTextLength;
        assert myTextSegments >= other.myTextSegments;
        myTextLength -= other.myTextLength;
        myTextSegments -= other.myTextSegments;

        // reset segment starts
        myTextSegmentLength = myTextLength;

        if (myTrackFirst256 && other.myTrackFirst256) {
            assert myTextSpaceLength >= other.myTextSpaceLength;
            assert myTextSpaceSegments >= other.myTextSpaceSegments;
            assert myTextFirst256Length >= other.myTextFirst256Length;
            assert myTextFirst256Segments >= other.myTextFirst256Segments;

            myTextSpaceLength -= other.myTextSpaceLength;
            myTextSpaceSegments -= other.myTextSpaceSegments;
            myTextFirst256Length -= other.myTextFirst256Length;
            myTextFirst256Segments -= other.myTextFirst256Segments;

            // reset segment starts
            myTextSpaceSegmentLength = myTextSpaceLength;
            myTextFirst256SegmentLength = myTextFirst256Length;
        }
    }

    public boolean isTextFirst256() {
        int segmentLength = myTextLength - myTextSegmentLength;
        return myTextFirst256Length - myTextFirst256SegmentLength == segmentLength;
    }

    public boolean isTextRepeatedSpace() {
        int segmentLength = myTextLength - myTextSegmentLength;
        return myTextSpaceLength - myTextSpaceSegmentLength == segmentLength;
    }

    public boolean isRepeatedText() {
        int segmentLength = myTextLength - myTextSegmentLength;
        return myRepeatedChar >= 0;
    }

    public void commitText() {
//        System.out.println("commitText()");
        if (myTextLength > myTextSegmentLength) {
            myTextSegments++;
            myRepeatedChar = NULL_REPEATED_CHAR;

            if (myTrackFirst256) {
                int segmentLength = myTextLength - myTextSegmentLength;

                if (myTextSpaceLength - myTextSpaceSegmentLength == segmentLength) myTextSpaceSegments++;
                if (myTextFirst256Length - myTextFirst256SegmentLength == segmentLength) myTextFirst256Segments++;
            }

            myTextSegmentLength = myTextLength;
            if (myTrackFirst256) {
                myTextSpaceSegmentLength = myTextSpaceLength;
                myTextFirst256SegmentLength = myTextFirst256Length;
            }
        }
    }

    public void addText(CharSequence text) {
//        System.out.println("addText(" + Utils.quoteJavaString(SequenceUtils.toVisibleWhitespaceString(text)) + ")");
//        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        for (StackTraceElement element : stackTrace) {
//            System.out.append("\n").append(element.toString());
//        }

        // need to count spaces in it
        myTextLength += text.length();

        if (myTrackFirst256) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);

                if (myRepeatedChar == NULL_REPEATED_CHAR) {
                    myRepeatedChar = c;
                } else if (myRepeatedChar != c) {
                    myRepeatedChar = NOT_REPEATED_CHAR;
                }

                if (c < 256) {
                    if (c == ' ') myTextSpaceLength++;
                    myTextFirst256Length++;
                }
            }
        }
    }

    public void addText(char c) {
//        System.out.println("addText('" + SequenceUtils.toVisibleWhitespaceString(Character.toString(c)) + "')");

        // need to count spaces in it
        myTextLength += 1;

        if (myTrackFirst256) {
            if (myRepeatedChar == NULL_REPEATED_CHAR) {
                myRepeatedChar = c;
            } else if (myRepeatedChar != c) {
                myRepeatedChar = NOT_REPEATED_CHAR;
            }

            if (c < 256) {
                if (c == ' ') myTextSpaceLength++;
                myTextFirst256Length++;
            }
        }
    }

    public void addText(char c, int repeat) {
        assert repeat > 0;
//        System.out.println("addText('" + SequenceUtils.toVisibleWhitespaceString(Character.toString(c)) + "')");

        // need to count spaces in it
        myTextLength += repeat;

        if (myTrackFirst256) {
            if (myRepeatedChar == NULL_REPEATED_CHAR) {
                myRepeatedChar = c;
            } else if (myRepeatedChar != c) {
                myRepeatedChar = NOT_REPEATED_CHAR;
            }

            if (c < 256) {
                if (c == ' ') myTextSpaceLength += repeat;
                myTextFirst256Length += repeat;
            }
        }
    }

    public void removeText(CharSequence text) {
        // need to count spaces in it
//        System.out.println("removeText(" + SequenceUtils.toVisibleWhitespaceString(text) + ")");

        myTextLength -= text.length();

        if (myTrackFirst256) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);
                if (myRepeatedChar == NULL_REPEATED_CHAR) {
                    myRepeatedChar = c;
                } else if (myRepeatedChar != c) {
                    myRepeatedChar = NOT_REPEATED_CHAR;
                }

                if (c < 256) {
                    if (c == ' ') {
                        assert myTextSpaceLength > 0;
                        myTextSpaceLength--;
                    }

                    assert myTextFirst256Length > 0;
                    myTextFirst256Length--;
                }
            }
        }

        // if whole segment was removed, reset repeated char
        if (myTextLength == myTextSegmentLength) myRepeatedChar = NULL_REPEATED_CHAR;
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("s=").append(myTextSpaceSegments).append(":").append(myTextSpaceLength).mark()
                .append("u=").append(myTextFirst256Segments).append(":").append(myTextFirst256Length).mark()
                .append("t=").append(myTextSegments).append(":").append(myTextLength)
//                .append("t=").append(myTextLength)
        ;

        return sb.toString();
    }
}
