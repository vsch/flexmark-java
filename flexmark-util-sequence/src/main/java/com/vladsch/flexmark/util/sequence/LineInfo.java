package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.misc.BitField;
import com.vladsch.flexmark.util.misc.BitFieldSet;
import com.vladsch.flexmark.util.misc.Utils;
import org.jetbrains.annotations.NotNull;

/** Line information in LineAppendable */
public final class LineInfo {
  public enum Flags implements BitField {
    PREFORMATTED(2),
    BLANK_PREFIX,
    BLANK_TEXT,
    ;

    final int bits;

    Flags() {
      this(1);
    }

    Flags(int bits) {
      this.bits = bits;
    }

    @Override
    public int getBits() {
      return bits;
    }
  }

  public enum Preformatted {
    NONE,
    FIRST,
    BODY,
    LAST,
    ;

    final int mask;

    Preformatted() {
      this.mask = BitFieldSet.setBitField(0, Flags.PREFORMATTED, ordinal());
    }

    @NotNull
    static Preformatted get(int flags) {
      int preformatted = flags & F_PREFORMATTED;

      if (preformatted == FIRST.mask) {
        return FIRST;
      } else if (preformatted == BODY.mask) {
        return BODY;
      } else if (preformatted == LAST.mask) {
        return LAST;
      } else {
        return NONE;
      }
    }
  }

  public static final Flags BLANK_PREFIX = Flags.BLANK_PREFIX;
  public static final Flags BLANK_TEXT = Flags.BLANK_TEXT;
  public static final Flags PREFORMATTED = Flags.PREFORMATTED;

  public static final int F_PREFORMATTED = BitFieldSet.intMask(Flags.PREFORMATTED);
  public static final int F_BLANK_PREFIX = BitFieldSet.intMask(Flags.BLANK_PREFIX);
  public static final int F_BLANK_TEXT = BitFieldSet.intMask(Flags.BLANK_TEXT);

  public static final LineInfo NULL =
      new LineInfo(BasedSequence.NULL, -1, 0, 0, 0, 0, 0, 0, true, true, Preformatted.NONE);

  public final CharSequence lineSeq; // line content
  public final int index; // line index
  public final int prefixLength; // line's prefix length
  public final int textLength; // line's text length
  public final int length; // line's length (including EOL if any)
  public final int sumPrefixLength; // total length of previous lines' prefixes
  public final int sumTextLength; // total length of previous lines' text
  public final int sumLength; // total length of previous lines
  public final int flags;

  private LineInfo(
      @NotNull CharSequence lineSeq,
      int index,
      int prefixLength,
      int textLength,
      int length,
      int sumPrefixLength,
      int sumTextLength,
      int sumLength,
      boolean isBlankPrefix,
      boolean isBlankText,
      @NotNull Preformatted preformatted) {
    this.lineSeq = lineSeq;
    this.index = index;
    this.prefixLength = prefixLength;
    this.textLength = textLength;
    this.length = length;
    this.sumPrefixLength = sumPrefixLength + prefixLength;
    this.sumTextLength = sumTextLength + textLength;
    this.sumLength = sumLength + length;
    this.flags =
        (isBlankPrefix || prefixLength == 0 ? F_BLANK_PREFIX : 0)
            | (isBlankText || textLength == 0 ? F_BLANK_TEXT : 0)
            | (preformatted.ordinal());
  }

  /**
   * See if replacing this line info with another requires updating all following line info because
   * of aggregation change
   *
   * @param other line info
   * @return true if need to update
   */
  public boolean needAggregateUpdate(LineInfo other) {
    return this.sumPrefixLength != other.sumPrefixLength
        || this.sumTextLength != other.sumTextLength
        || this.sumLength != other.sumLength;
  }

  public boolean isNull() {
    return this == NULL;
  }

  public boolean isNotNull() {
    return this != NULL;
  }

  public boolean isBlankPrefix() {
    return BitFieldSet.any(flags, F_BLANK_PREFIX);
  }

  public boolean isBlankText() {
    return BitFieldSet.any(flags, F_BLANK_TEXT);
  }

  public boolean isPreformatted() {
    return BitFieldSet.any(flags, F_PREFORMATTED);
  }

  @NotNull
  public Preformatted getPreformatted() {
    return Preformatted.get(flags);
  }

  /**
   * NOTE: a line which consists of any prefix and blank text is considered a blank line
   *
   * @return true if the line is a blank line
   */
  public boolean isBlankTextAndPrefix() {
    return BitFieldSet.all(flags, F_BLANK_PREFIX | F_BLANK_TEXT);
  }

  public int getTextStart() {
    return prefixLength;
  }

  public int getTextEnd() {
    return prefixLength + textLength;
  }

  @NotNull
  public BasedSequence getLine() {
    return lineSeq instanceof BasedSequence ? (BasedSequence) lineSeq : BasedSequence.of(lineSeq);
  }

  @NotNull
  public BasedSequence getPrefix() {
    return getLine().subSequence(0, prefixLength);
  }

  @NotNull
  public BasedSequence getTextNoEOL() {
    return getLine().subSequence(prefixLength, prefixLength + textLength);
  }

  @NotNull
  public BasedSequence getText() {
    return getLine().subSequence(prefixLength, length);
  }

  @NotNull
  public BasedSequence getLineNoEOL() {
    return getLine().subSequence(0, prefixLength + textLength);
  }

  @NotNull
  public BasedSequence getEOL() {
    return getLine().subSequence(prefixLength + textLength, length);
  }

  @Override
  public String toString() {
    return "LineInfo{"
        + "i="
        + index
        + ", pl="
        + prefixLength
        + ", tl="
        + textLength
        + ", l="
        + length
        + ", sumPl="
        + sumPrefixLength
        + ", sumTl="
        + sumTextLength
        + ", sumL="
        + sumLength
        + (flags != 0
            ? ","
                + (isBlankPrefix() ? " bp" : "")
                + (isBlankText() ? " bt" : "")
                + (isPreformatted() ? " p" : "")
            : "")
        + ", '"
        + Utils.escapeJavaString(lineSeq)
        + "'"
        + '}';
  }

  @NotNull
  public static LineInfo create(
      @NotNull CharSequence line,
      int prefixLength,
      int textLength,
      int length,
      boolean isBlankPrefix,
      boolean isBlankText,
      @NotNull Preformatted preformatted) {
    return new LineInfo(
        line,
        0,
        prefixLength,
        textLength,
        length,
        0,
        0,
        0,
        isBlankPrefix,
        isBlankText,
        preformatted);
  }

  @NotNull
  public static LineInfo create(
      @NotNull CharSequence line,
      @NotNull LineInfo prevInfo,
      int prefixLength,
      int textLength,
      int length,
      boolean isBlankPrefix,
      boolean isBlankText,
      @NotNull Preformatted preformatted) {
    return new LineInfo(
        line,
        prevInfo.index + 1,
        prefixLength,
        textLength,
        length,
        prevInfo.sumPrefixLength,
        prevInfo.sumTextLength,
        prevInfo.sumLength,
        isBlankPrefix,
        isBlankText,
        preformatted);
  }

  @NotNull
  public static LineInfo create(@NotNull LineInfo prevInfo, @NotNull LineInfo info) {
    return new LineInfo(
        info.lineSeq,
        prevInfo.index + 1,
        info.prefixLength,
        info.textLength,
        info.length,
        prevInfo.sumPrefixLength,
        prevInfo.sumTextLength,
        prevInfo.sumLength,
        info.isBlankPrefix(),
        info.isBlankText(),
        info.getPreformatted());
  }
}
