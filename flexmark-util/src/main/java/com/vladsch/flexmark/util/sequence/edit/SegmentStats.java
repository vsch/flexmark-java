package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;

public class SegmentStats {
    protected int myTextLength = 0;             // length of all text in stats
    protected int myTextSegments = 0;           // total disjoint text segments
    protected int myTextSegmentLength = 0;      // length of last segment

    protected int myTextSpaceLength = 0;        // length of all space text
    protected int myTextSpaceSegments = 0;      // total disjoint spaces only segments
    protected int myTextSpaceSegmentLength = 0; // length of spaces in last segment

    protected int myTextFirst256Length = 0;     // length of all text chars < 256
    protected int myTextFirst256Segments = 0;   // total disjoint chars < 256 only segments
    protected int myTextFirst256SegmentLength = 0;// length of chars < 256 in last segment

    final protected boolean myTrackFirst256;    // options

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

    public boolean isTrackFirst256() {
        return myTrackFirst256;
    }

    public int getTextSegments() {
        return myTextSegments;
    }

    public boolean isEmpty() {
        return myTextLength == 0 &&
                myTextSegments == 0 &&
                myTextSegmentLength == 0 &&
                myTextSpaceLength == 0 &&
                myTextSpaceSegments == 0 &&
                myTextSpaceSegmentLength == 0 &&
                myTextFirst256Length == 0 &&
                myTextFirst256Segments == 0 &&
                myTextFirst256SegmentLength == 0
                ;
    }

    public boolean isValid() {
        return myTextLength >= myTextSegments
                && myTextLength >= myTextFirst256Length && myTextSegments >= myTextFirst256Segments && myTextFirst256Length >= myTextFirst256Segments
                && myTextFirst256Length >= myTextSpaceLength && myTextFirst256Segments >= myTextSpaceSegments && myTextSpaceLength >= myTextSpaceSegments
                ;
    }

    public void clear() {
        myTextLength = 0;
        myTextSegments = 0;
        myTextSegmentLength = 0;
        myTextSpaceLength = 0;
        myTextSpaceSegments = 0;
        myTextSpaceSegmentLength = 0;
        myTextFirst256Length = 0;
        myTextFirst256Segments = 0;
        myTextFirst256SegmentLength = 0;
    }

    public void add(SegmentStats other) {
        myTextLength += other.myTextLength;
        myTextSegments += other.myTextSegments;
        myTextSpaceLength += other.myTextSpaceLength;
        myTextSpaceSegments += other.myTextSpaceSegments;
        myTextFirst256Length += other.myTextFirst256Length;
        myTextFirst256Segments += other.myTextFirst256Segments;
    }

    public void remove(SegmentStats other) {
        assert myTextLength >= other.myTextLength;
        assert myTextSegments >= other.myTextSegments;
        assert myTextSpaceLength >= other.myTextSpaceLength;
        assert myTextSpaceSegments >= other.myTextSpaceSegments;
        assert myTextFirst256Length >= other.myTextFirst256Length;
        assert myTextFirst256Segments >= other.myTextFirst256Segments;

        myTextLength -= other.myTextLength;
        myTextSegments -= other.myTextSegments;
        myTextSpaceLength -= other.myTextSpaceLength;
        myTextSpaceSegments -= other.myTextSpaceSegments;
        myTextFirst256Length -= other.myTextFirst256Length;
        myTextFirst256Segments -= other.myTextFirst256Segments;
    }

    public void commitText() {
        if (myTextSegmentLength > 0) {
            myTextSegments++;

            if (myTextSpaceSegmentLength == myTextSegmentLength) myTextSpaceSegments++;
            if (myTextFirst256SegmentLength == myTextSegmentLength) myTextFirst256Segments++;

            myTextSegmentLength = 0;
            myTextSpaceSegmentLength = 0;
            myTextFirst256SegmentLength = 0;
        }
    }

    public void addText(CharSequence text) {
        // need to count spaces in it
        myTextLength += text.length();
        myTextSegmentLength += text.length();

        if (myTrackFirst256) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);
                if (c < 256) {
                    if (c == ' ') {
                        myTextSpaceSegmentLength++;
                        myTextSpaceLength++;
                    }

                    myTextFirst256SegmentLength++;
                    myTextFirst256Length++;
                }
            }
        }
    }

    public void addText(char c) {
        // need to count spaces in it
        myTextLength += 1;
        myTextSegmentLength += 1;

        if (myTrackFirst256) {
            if (c < 256) {
                if (c == ' ') {
                    myTextSpaceSegmentLength++;
                    myTextSpaceLength++;
                }

                myTextFirst256SegmentLength++;
                myTextFirst256Length++;
            }
        }
    }

    public void removeText(CharSequence text) {
        // need to count spaces in it
        myTextLength -= text.length();

        assert myTextSegmentLength >= text.length();
        myTextSegmentLength -= text.length();

        if (myTrackFirst256) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);
                if (c < 256) {
                    if (c == ' ') {
                        assert myTextSpaceLength > 0;
                        assert myTextSpaceSegmentLength > 0;

                        myTextSpaceLength--;
                        myTextSpaceSegmentLength--;
                    }

                    assert myTextFirst256Length > 0;
                    assert myTextFirst256SegmentLength > 0;

                    myTextFirst256Length--;
                    myTextFirst256SegmentLength--;
                }
            }
        }
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
