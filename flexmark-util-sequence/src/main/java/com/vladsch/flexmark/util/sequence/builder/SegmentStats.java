package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.misc.DelimitedBuilder;

class SegmentStats {
  private static final int NULL_REPEATED_CHAR = -1;
  private static final int NOT_REPEATED_CHAR = -2;

  private int textLength = 0; // length of all text in stats
  private int textSegments = 0; // total disjoint text segments
  private int textSegmentLength = 0; // length at start of last segment

  private int textSpaceLength = 0; // length of all space text
  private int textSpaceSegments = 0; // total disjoint spaces only segments
  private int textSpaceSegmentLength = 0; // length at start of spaces in last segment

  private int textFirst256Length = 0; // length of all text chars < 256
  private int textFirst256Segments = 0; // total disjoint chars < 256 only segments
  private int textFirst256SegmentLength = 0; // length at start of chars < 256 in last segment

  private int repeatedChar =
      -1; // repeated char if all same, -1 if no char, -2 if different chars, must be checked before
  // commit which clears this

  private final boolean trackFirst256; // options

  SegmentStats(boolean trackFirst256) {
    this.trackFirst256 = trackFirst256;
  }

  int getTextLength() {
    return textLength;
  }

  SegmentStats committedCopy() {
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

  void clear() {
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

  void remove(SegmentStats other) {
    textLength -= other.textLength;
    textSegments -= other.textSegments;

    // reset segment starts
    textSegmentLength = textLength;

    if (trackFirst256 && other.trackFirst256) {
      textSpaceLength -= other.textSpaceLength;
      textSpaceSegments -= other.textSpaceSegments;
      textFirst256Length -= other.textFirst256Length;
      textFirst256Segments -= other.textFirst256Segments;

      // reset segment starts
      textSpaceSegmentLength = textSpaceLength;
      textFirst256SegmentLength = textFirst256Length;
    }
  }

  boolean isTextFirst256() {
    int segmentLength = textLength - textSegmentLength;
    return textFirst256Length - textFirst256SegmentLength == segmentLength;
  }

  boolean isRepeatedText() {
    return repeatedChar >= 0;
  }

  void commitText() {
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

  void addText(CharSequence text) {
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

  void addText(char c) {
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

  void addText(char c, int repeat) {
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

  @Override
  public String toString() {
    DelimitedBuilder sb = new DelimitedBuilder(", ");
    sb.append("s=")
        .append(textSpaceSegments)
        .append(":")
        .append(textSpaceLength)
        .mark()
        .append("u=")
        .append(textFirst256Segments)
        .append(":")
        .append(textFirst256Length)
        .mark()
        .append("t=")
        .append(textSegments)
        .append(":")
        .append(textLength);

    return sb.toString();
  }
}
