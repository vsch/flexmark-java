package com.vladsch.flexmark.util.format;

import static com.vladsch.flexmark.util.misc.CharPredicate.SPACE_TAB_NBSP;
import static com.vladsch.flexmark.util.misc.CharPredicate.SPACE_TAB_NBSP_EOL;
import static com.vladsch.flexmark.util.misc.CharPredicate.SPACE_TAB_NBSP_LINE_SEP;
import static com.vladsch.flexmark.util.misc.CharPredicate.WHITESPACE_NBSP;
import static com.vladsch.flexmark.util.misc.CharPredicate.WHITESPACE_NBSP_OR_NUL;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MarkdownParagraph {
  private static final char MARKDOWN_START_LINE_CHAR =
      SequenceUtils
          .LS; // https://www.fileformat.info/info/unicode/char/2028/index.htm LINE_SEPARATOR this
  // one is not preserved but will cause a line break if not already at beginning of
  // line
  private static final List<SpecialLeadInHandler> EMPTY_LEAD_IN_HANDLERS = Collections.emptyList();
  private static final List<TrackedOffset> EMPTY_OFFSET_LIST = Collections.emptyList();

  private final @NotNull BasedSequence baseSeq;
  private final @NotNull BasedSequence altSeq;
  private final @NotNull CharWidthProvider charWidthProvider;

  private BasedSequence firstIndent = BasedSequence.NULL;
  private BasedSequence indent = BasedSequence.NULL;
  private int firstWidthOffset = 0;
  private int width = 0;
  private boolean keepHardLineBreaks = true;
  private boolean keepSoftLineBreaks = false;
  private boolean unEscapeSpecialLeadInChars = true;
  private boolean escapeSpecialLeadInChars = true;
  private boolean restoreTrackedSpaces = false;
  @Nullable private DataHolder options = null;

  @NotNull private List<? extends SpecialLeadInHandler> leadInHandlers = EMPTY_LEAD_IN_HANDLERS;
  private List<TrackedOffset> trackedOffsets = EMPTY_OFFSET_LIST;
  private boolean trackedOffsetsSorted = true;

  public MarkdownParagraph(
      @NotNull BasedSequence chars, @NotNull CharWidthProvider charWidthProvider) {
    this(chars, chars, charWidthProvider);
  }

  public MarkdownParagraph(
      @NotNull BasedSequence chars,
      @NotNull BasedSequence altChars,
      @NotNull CharWidthProvider charWidthProvider) {
    baseSeq = chars;
    this.altSeq = altChars;
    this.charWidthProvider = charWidthProvider;
  }

  public BasedSequence wrapTextNotTracked() {
    if (getFirstWidth() <= 0) {
      return baseSeq;
    }

    LeftAlignedWrapping wrapping = new LeftAlignedWrapping(baseSeq);
    return wrapping.wrapText();
  }

  @NotNull
  private Range getContinuationStartSplice(int offset, boolean afterSpace, boolean afterDelete) {
    BasedSequence baseSequence = altSeq.getBaseSequence();
    if (afterSpace && afterDelete) {
      BasedOffsetTracker preFormatTracker = BasedOffsetTracker.create(altSeq);
      int startOfLine = baseSequence.startOfLine(offset);
      if (startOfLine > altSeq.getStartOffset()
          && !baseSequence.isCharAt(offset, SPACE_TAB_NBSP_LINE_SEP)) {
        int previousNonBlank = baseSequence.lastIndexOfAnyNot(SPACE_TAB_NBSP_EOL, offset - 1);
        if (previousNonBlank < startOfLine) {
          // delete range between last non-blank and offset index
          @NotNull OffsetInfo offsetInfo = preFormatTracker.getOffsetInfo(offset, true);
          int offsetIndex = offsetInfo.endIndex;
          int previousNonBlankIndex = altSeq.lastIndexOfAnyNot(SPACE_TAB_NBSP_EOL, offsetIndex - 1);
          return Range.of(previousNonBlankIndex + 1, offsetIndex);
        }
      }
    }
    return Range.NULL;
  }

  @NotNull
  private BasedSequence resolveTrackedOffsets(
      @NotNull BasedSequence unwrapped, @NotNull BasedSequence wrapped) {
    // Now we map the tracked offsets to indexes in the resulting text
    BasedOffsetTracker tracker = BasedOffsetTracker.create(wrapped);
    int iMax = trackedOffsets.size();
    for (int i = iMax; i-- > 0; ) {
      TrackedOffset trackedOffset = trackedOffsets.get(i);
      int offset = trackedOffset.getOffset();
      boolean baseIsWhiteSpaceAtOffset = unwrapped.isBaseCharAt(offset, WHITESPACE_NBSP);

      if (baseIsWhiteSpaceAtOffset && !(unwrapped.isBaseCharAt(offset - 1, WHITESPACE_NBSP))) {
        // we need to use previous non-blank and use that offset
        OffsetInfo info = tracker.getOffsetInfo(offset - 1, false);
        trackedOffset.setIndex(info.endIndex);
      } else if (!baseIsWhiteSpaceAtOffset && unwrapped.isBaseCharAt(offset + 1, WHITESPACE_NBSP)) {
        // we need to use this non-blank and use that offset
        OffsetInfo info = tracker.getOffsetInfo(offset, false);
        trackedOffset.setIndex(info.startIndex);
      } else {
        OffsetInfo info = tracker.getOffsetInfo(offset, true);
        trackedOffset.setIndex(info.endIndex);
      }
    }
    return wrapped;
  }

  public BasedSequence wrapText() {
    if (getFirstWidth() <= 0) {
      return baseSeq;
    }
    if (trackedOffsets.isEmpty()) {
      return wrapTextNotTracked();
    }

    // Adjust input text for wrapping by removing any continuation splice regions
    sortedTrackedOffsets();

    // delete any space ranges that need to be spliced
    BasedSequence baseSpliced = baseSeq;
    BasedSequence altSpliced = altSeq;
    Range lastRange = Range.NULL;

    {
      int iMax = trackedOffsets.size();
      for (int i = iMax; i-- > 0; ) {
        TrackedOffset trackedOffset = trackedOffsets.get(i);
        if (lastRange.isEmpty() || !lastRange.contains(trackedOffset.getOffset())) {
          lastRange =
              getContinuationStartSplice(
                  trackedOffset.getOffset(),
                  trackedOffset.isAfterSpaceEdit(),
                  trackedOffset.isAfterDelete());
          if (lastRange.isNotEmpty()) {
            trackedOffset.setSpliced(true);
            baseSpliced = baseSpliced.delete(lastRange.getStart(), lastRange.getEnd());
            altSpliced = altSpliced.delete(lastRange.getStart(), lastRange.getEnd());
          }
        }
      }
    }

    LeftAlignedWrapping textWrapper = new LeftAlignedWrapping(baseSpliced);
    BasedSequence wrapped = textWrapper.wrapText();

    if (restoreTrackedSpaces) {
      if (indent.isNotEmpty() || firstIndent.isNotEmpty())
        throw new IllegalStateException(
            "restoreTrackedSpaces is not supported with indentation applied by MarkdownParagraph");

      wrapped = resolveTrackedOffsetsEdit(baseSpliced, altSpliced, wrapped);
    } else {
      wrapped = resolveTrackedOffsets(baseSeq, wrapped);
    }

    return wrapped;
  }

  private BasedSequence resolveTrackedOffsetsEdit(
      BasedSequence baseSpliced, BasedSequence altSpliced, BasedSequence wrapped) {
    BasedSequence spliced = BasedSequence.of(baseSpliced.toString());
    LeftAlignedWrapping altTextWrapper = new LeftAlignedWrapping(spliced);
    BasedSequence altWrapped =
        spliced
            .getBuilder()
            .append(altTextWrapper.wrapText())
            .toSequence(altSpliced, CharPredicate.LINE_SEP, CharPredicate.SPACE_TAB_EOL);

    BasedOffsetTracker tracker = BasedOffsetTracker.create(altSeq);
    BasedOffsetTracker altTracker = BasedOffsetTracker.create(altWrapped);

    int iMax = trackedOffsets.size();
    BasedSequence baseSequence = altSeq.getBaseSequence();
    BasedSequence altUnwrapped = altSeq;

    // NOTE: Restore trailing spaces at end of line if it has tracked offset on it
    int restoredAppendSpaces = 0;

    // determine in reverse offset order
    for (int i = iMax; i-- > 0; ) {
      TrackedOffset trackedOffset = trackedOffsets.get(i);

      int offset = trackedOffset.getOffset();
      int countedSpacesBefore = baseSequence.countTrailing(SPACE_TAB_NBSP, offset);
      int countedSpacesAfter = baseSequence.countLeading(SPACE_TAB_NBSP, offset);
      int countedWhitespaceBefore = baseSequence.countTrailing(SPACE_TAB_NBSP_EOL, offset);
      int countedWhiteSpaceAfter = baseSequence.countLeading(SPACE_TAB_NBSP_EOL, offset);

      char baseCharAt = baseSequence.safeCharAt(offset);
      char prevBaseCharAt = baseSequence.safeCharAt(offset - countedSpacesBefore - 1);
      char nextBaseCharAt = baseSequence.safeCharAt(offset + countedSpacesAfter);

      int anchorOffset;
      int anchorDelta = 0;
      int anchorIndex;
      boolean isLineSep = false;
      if (!SPACE_TAB_NBSP.test(baseCharAt)) {
        anchorOffset = offset;
        anchorIndex = tracker.getOffsetInfo(anchorOffset, false).startIndex;

        if (altUnwrapped.safeCharAt(anchorIndex - 1) == SequenceUtils.LS) {
          // have line sep at anchor
          isLineSep = true;
        }
      } else {
        if (!SPACE_TAB_NBSP_EOL.test(prevBaseCharAt)) {
          anchorOffset = offset - countedWhitespaceBefore;
          anchorIndex = tracker.getOffsetInfo(anchorOffset - 1, false).endIndex;
        } else if (!SPACE_TAB_NBSP_EOL.test(nextBaseCharAt)) {
          anchorOffset = offset + countedWhiteSpaceAfter;
          anchorIndex = tracker.getOffsetInfo(anchorOffset, false).startIndex;
        } else {
          throw new IllegalStateException(
              String.format("Should not be here. altSeq: '%s'", altUnwrapped));
        }
      }

      // now see where it is in the wrapped sequence
      int wrappedIndex = altTracker.getOffsetInfo(anchorOffset, false).startIndex;

      // NOTE: at this point space == &nbsp; since altUnwrapped can have &nbsp; instead of spaces

      int addSpacesBeforeEol = 0;
      // take char start but if at whitespace, use previous for validation
      if (WHITESPACE_NBSP.test(altUnwrapped.safeCharAt(anchorIndex + anchorDelta))) {
        // NOTE: if after wrapping caret is still on whitespace or end of string, then we adjust
        // unwrapped and wrapped index backwards
        if (WHITESPACE_NBSP_OR_NUL.test(altWrapped.safeCharAt(wrappedIndex))) {
        } else {
          // NOTE: if the insert pos is on EOL (or whitespace) but after wrapping the leading
          // whitespace is removed
          //   then need to adjust only unwrapped and add space after tracked pos:
          //   altUnwrapped anchor: `en updated|\nabandoned`
          //   altWrapped anchor: `n\nupdated |abandoned `
          //   wrapped anchor: `n\nupdated |abandoned `
          addSpacesBeforeEol = 1;
        }
      } else if (altUnwrapped.safeCharAt(anchorIndex + anchorDelta) == SequenceUtils.LS) {
        // have line sep at anchor, if prev is not whitespace, use it for validation
        if (!WHITESPACE_NBSP.test(altUnwrapped.safeCharAt(anchorIndex + anchorDelta - 1))) {
        } else {
          // use next char, it should not be whitespace
          anchorDelta++;
          String.format(
              "Character(%s) after LS should not be whitespace.",
              SequenceUtils.toVisibleWhitespaceString(
                  Character.toString(altUnwrapped.safeCharAt(anchorIndex + anchorDelta))));
          isLineSep = true;
        }
      }

      // NOTE: at this point space == &nbsp; since altUnwrapped can have &nbsp; instead of spaces

      // Adjust index position and restore spaces if needed
      if (isLineSep) {
        wrappedIndex = Math.max(0, wrappedIndex - 1);
      }

      if (wrapped.isCharAt(wrappedIndex - 1, CharPredicate.ANY_EOL) && countedSpacesAfter > 0) {
        // at start of line with spaces to be inserted after, move to before prev EOL
        wrappedIndex -= wrapped.eolEndLength(wrappedIndex);
      }

      // treat NBSP as spaces
      int wrappedSpacesBefore = wrapped.countTrailing(SPACE_TAB_NBSP, wrappedIndex);
      int wrappedSpacesAfter = wrapped.countLeading(SPACE_TAB_NBSP, wrappedIndex);

      if (trackedOffset.isAfterSpaceEdit()) {
        if (trackedOffset.isAfterInsert()) {
          // need at least one space before
          countedSpacesBefore = Math.max(1, countedSpacesBefore);
        } else if (trackedOffset.isAfterDelete()) {
          countedSpacesBefore = 0;
        }
      }

      int addSpacesBefore =
          trackedOffset.isSpliced() ? 0 : Math.max(0, countedSpacesBefore - wrappedSpacesBefore);
      // add an implicit space if was followed by EOL
      int addSpacesAfter = Math.max(addSpacesBeforeEol, countedSpacesAfter - wrappedSpacesAfter);

      if (wrapped.isCharAt(wrappedIndex, CharPredicate.ANY_EOL_NUL)) {
        // at end of line add only before, nothing after
        addSpacesAfter = 0;
        if (trackedOffset.isAfterDelete()) addSpacesBefore = Math.min(1, addSpacesBefore);
      } else if (!wrapped.isCharAt(wrappedIndex - 1, CharPredicate.ANY_EOL_NUL)) {
        // not at start of line
        // spaces before caret, see if need to add max 1, and all spaces after
        addSpacesBefore = Math.min(1, addSpacesBefore);
      } else if (trackedOffset.isAfterDelete() && !trackedOffset.isAfterSpaceEdit()) {
        // at start of line, add max 1 space after
        // spaces before caret, see if need to add max 1
        addSpacesBefore = 0;
        addSpacesAfter = Math.min(1, addSpacesAfter);
      } else {
        // at start of line, not after delete or after space edit
        if (!trackedOffset.isAfterInsert() && !trackedOffset.isAfterDelete()) addSpacesAfter = 0;
        addSpacesBefore = 0;
      }

      if (addSpacesBefore + addSpacesAfter > 0) {
        int lastNonBlank = wrapped.lastIndexOfAnyNot(WHITESPACE_NBSP);
        if (wrappedIndex <= lastNonBlank) {
          // insert in middle
          wrapped =
              wrapped.insert(
                  wrappedIndex, RepeatedSequence.ofSpaces(addSpacesBefore + addSpacesAfter));

          // need to adjust all following indices by the amount inserted
          for (int j = i + 1; j < iMax; j++) {
            TrackedOffset trackedOffset1 = trackedOffsets.get(j);
            int indexJ = trackedOffset1.getIndex();
            trackedOffset1.setIndex(indexJ + addSpacesBefore + addSpacesAfter);
          }
        } else {
          restoredAppendSpaces = Math.max(restoredAppendSpaces, addSpacesBefore);
        }

        wrappedIndex += addSpacesBefore;
      }

      trackedOffset.setIndex(wrappedIndex);
    }

    // append any trailing spaces
    if (restoredAppendSpaces > 0) {
      wrapped = wrapped.appendSpaces(restoredAppendSpaces);
    }

    return wrapped;
  }

  public void addTrackedOffset(@NotNull TrackedOffset trackedOffset) {
    if (trackedOffsets == EMPTY_OFFSET_LIST) trackedOffsets = new ArrayList<>();
    trackedOffsets.removeIf(it -> it.getOffset() == trackedOffset.getOffset());
    trackedOffsets.add(trackedOffset);
    trackedOffsetsSorted = false;
  }

  public List<TrackedOffset> getTrackedOffsets() {
    return sortedTrackedOffsets();
  }

  private List<TrackedOffset> sortedTrackedOffsets() {
    if (!trackedOffsetsSorted) {
      trackedOffsets.sort(Comparator.comparing(TrackedOffset::getOffset));
      trackedOffsetsSorted = true;
    }
    return trackedOffsets;
  }

  @NotNull
  public List<? extends SpecialLeadInHandler> getLeadInHandlers() {
    return leadInHandlers;
  }

  public void setLeadInHandlers(@NotNull List<? extends SpecialLeadInHandler> leadInHandlers) {
    this.leadInHandlers = leadInHandlers;
  }

  @Nullable
  public DataHolder getOptions() {
    return options;
  }

  public void setOptions(@Nullable DataHolder options) {
    this.options = options;
  }

  public boolean isRestoreTrackedSpaces() {
    return restoreTrackedSpaces;
  }

  public void setRestoreTrackedSpaces(boolean restoreTrackedSpaces) {
    this.restoreTrackedSpaces = restoreTrackedSpaces;
  }

  @NotNull
  public BasedSequence getChars() {
    return baseSeq;
  }

  public CharSequence getFirstIndent() {
    return firstIndent;
  }

  public void setFirstIndent(CharSequence firstIndent) {
    this.firstIndent = BasedSequence.of(firstIndent);
  }

  public CharSequence getIndent() {
    return indent;
  }

  public void setIndent(CharSequence indent) {
    this.indent = BasedSequence.of(indent);
    if (this.firstIndent.isNull()) this.firstIndent = this.indent;
  }

  public int getFirstWidth() {
    return (width == 0) ? 0 : Math.max(0, width + firstWidthOffset);
  }

  public int getFirstWidthOffset() {
    return firstWidthOffset;
  }

  public void setFirstWidthOffset(int firstWidthOffset) {
    this.firstWidthOffset = firstWidthOffset;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = Math.max(0, width);
  }

  public boolean getKeepHardBreaks() {
    return keepHardLineBreaks;
  }

  public void setKeepHardBreaks(boolean keepHardBreaks) {
    this.keepHardLineBreaks = keepHardBreaks;
  }

  public boolean getKeepSoftBreaks() {
    return keepSoftLineBreaks;
  }

  public boolean isUnEscapeSpecialLeadIn() {
    return unEscapeSpecialLeadInChars;
  }

  public void setUnEscapeSpecialLeadIn(boolean unEscapeSpecialLeadInChars) {
    this.unEscapeSpecialLeadInChars = unEscapeSpecialLeadInChars;
  }

  public boolean isEscapeSpecialLeadIn() {
    return escapeSpecialLeadInChars;
  }

  public void setEscapeSpecialLeadIn(boolean escapeSpecialLeadInChars) {
    this.escapeSpecialLeadInChars = escapeSpecialLeadInChars;
  }

  public void setKeepSoftBreaks(boolean keepLineBreaks) {
    this.keepSoftLineBreaks = keepLineBreaks;
  }

  @NotNull
  public CharWidthProvider getCharWidthProvider() {
    return charWidthProvider;
  }

  private enum TextType {
    WORD,
    SPACE,
    BREAK,
    MARKDOWN_BREAK,
    MARKDOWN_START_LINE
  }

  private static class Token {
    private final @NotNull TextType type;
    private final @NotNull Range range;
    private final boolean isFirstWord;

    private Token(@NotNull TextType type, @NotNull Range range, boolean isFirstWord) {
      this.type = type;
      this.range = range;
      this.isFirstWord = isFirstWord;
    }

    @Override
    public String toString() {
      return "token: " + type + " " + range + (isFirstWord ? " isFirst" : "");
    }

    private BasedSequence subSequence(BasedSequence charSequence) {
      return range.basedSubSequence(charSequence);
    }

    @NotNull
    public static Token of(@NotNull TextType type, int start, int end) {
      return new Token(type, Range.of(start, end), false);
    }

    @NotNull
    public static Token of(@NotNull TextType type, @NotNull Range range, boolean isFirstWord) {
      return new Token(type, range, isFirstWord);
    }

    @NotNull
    public static Token of(@NotNull TextType type, int start, int end, boolean isFirstWord) {
      return new Token(type, Range.of(start, end), isFirstWord);
    }
  }

  private class LeftAlignedWrapping {
    private final @NotNull BasedSequence baseSeq;
    private final SequenceBuilder result;
    private final TextTokenizer tokenizer;
    private int col = 0;
    private final int spaceWidth = charWidthProvider.getSpaceWidth();
    private CharSequence lineIndent = getFirstIndent();
    private final CharSequence nextIndent = getIndent();
    private int lineWidth = spaceWidth * getFirstWidth();
    private final int nextWidth = width <= 0 ? Integer.MAX_VALUE : spaceWidth * width;
    private BasedSequence lastSpace = null;

    @NotNull
    List<? extends SpecialLeadInHandler> leadInHandlers = MarkdownParagraph.this.leadInHandlers;

    boolean unEscapeSpecialLeadInChars = MarkdownParagraph.this.unEscapeSpecialLeadInChars;
    boolean escapeSpecialLeadInChars = MarkdownParagraph.this.escapeSpecialLeadInChars;

    private LeftAlignedWrapping(@NotNull BasedSequence baseSeq) {
      this.baseSeq = baseSeq;
      result = SequenceBuilder.emptyBuilder(baseSeq);
      tokenizer = new TextTokenizer(baseSeq);
    }

    private void advance() {
      tokenizer.next();
    }

    private void addToken(Token token) {
      addChars(baseSeq.subSequence(token.range.getStart(), token.range.getEnd()));
    }

    private void addChars(CharSequence charSequence) {
      result.append(charSequence);
      col += charWidthProvider.getStringWidth(charSequence);
    }

    private void addSpaces(int count) {
      result.append(' ', count);
      col += charWidthProvider.getSpaceWidth() * count;
    }

    private BasedSequence addSpaces(BasedSequence sequence, int count) {
      if (count <= 0) {
        return sequence;
      }

      BasedSequence remainder = null;

      // NOTE: can do splitting add from sequence before and after padding spaces to have start/end
      // range if needed
      if (sequence != null) {
        addChars(sequence.subSequence(0, Math.min(sequence.length(), count)));

        if (sequence.length() > count) {
          remainder = sequence.subSequence(count);
        }

        count = Math.max(0, count - sequence.length());
      }

      // add more spaces if needed
      if (count > 0) {
        addSpaces(count);
      }

      return remainder;
    }

    private void afterLineBreak() {
      col = 0;
      lineIndent = nextIndent;
      lineWidth = nextWidth;
      lastSpace = null;
    }

    private void processLeadInEscape(
        List<? extends SpecialLeadInHandler> handlers, BasedSequence sequence) {
      if (sequence.isNotEmpty() && escapeSpecialLeadInChars) {
        for (SpecialLeadInHandler handler : handlers) {
          if (handler.escape(sequence, options, this::addChars)) {
            return;
          }
        }
      }
      addChars(sequence);
    }

    private void processLeadInUnEscape(
        List<? extends SpecialLeadInHandler> handlers, BasedSequence sequence) {
      if (sequence.isNotEmpty() && unEscapeSpecialLeadInChars) {
        for (SpecialLeadInHandler handler : handlers) {
          if (handler.unEscape(sequence, options, this::addChars)) {
            return;
          }
        }
      }
      addChars(sequence);
    }

    @NotNull
    BasedSequence wrapText() {
      while (true) {
        final Token token = tokenizer.getToken();
        if (token == null) {
          break;
        }

        switch (token.type) {
          case SPACE:
            {
              if (col != 0) lastSpace = baseSeq.subSequence(token.range);
              advance();
              break;
            }

          case WORD:
            {
              if (col == 0
                  || col + charWidthProvider.getStringWidth(token.subSequence(baseSeq)) + spaceWidth
                      <= lineWidth) {
                // fits, add it
                boolean firstNonBlank = col == 0;

                if (col > 0) {
                  lastSpace = addSpaces(lastSpace, 1);
                } else {
                  if (!SequenceUtils.isEmpty(lineIndent)) {
                    addChars(lineIndent);
                  }
                }

                if (firstNonBlank && !token.isFirstWord) {
                  processLeadInEscape(leadInHandlers, baseSeq.subSequence(token.range));
                } else if (!firstNonBlank && token.isFirstWord) {
                  processLeadInUnEscape(leadInHandlers, baseSeq.subSequence(token.range));
                } else {
                  addToken(token);
                }

                advance();
              } else {
                // need to insert a line break and repeat
                addChars(SequenceUtils.EOL);
                afterLineBreak();
              }
              break;
            }

          case MARKDOWN_START_LINE:
            {
              // start a new line if not already new
              if (col > 0) {
                addChars(SequenceUtils.EOL);
                afterLineBreak();
              }
              advance();
              break;
            }

          case MARKDOWN_BREAK:
            {
              // start a new line if not already new
              if (keepHardLineBreaks) {
                if (col > 0) {
                  addToken(token);
                  afterLineBreak();
                }
              } else {
                // treat as a space
                lastSpace = baseSeq.subSequence(token.range);
              }
              advance();
              break;
            }

          case BREAK:
            {
              if (col > 0 && keepSoftLineBreaks) {
                // only use the EOL
                addToken(token);
                afterLineBreak();
              }
              advance();
              break;
            }
        }
      }

      return result.toSequence();
    }
  }

  private static class TextTokenizer {
    private final CharSequence chars;
    private final int maxIndex;
    private int index = 0;
    private int lastPos = 0;
    private boolean isInWord = false;
    private boolean isFirstNonBlank = true;
    private int lastConsecutiveSpaces = 0;
    private @Nullable Token token = null;

    TextTokenizer(@NotNull CharSequence chars) {
      this.chars = chars;
      maxIndex = this.chars.length();
      reset();
    }

    private void reset() {
      index = 0;
      lastPos = 0;
      isInWord = false;
      token = null;
      lastConsecutiveSpaces = 0;
      isFirstNonBlank = true;
      next();
    }

    @Nullable
    private Token getToken() {
      return token;
    }

    private void next() {
      token = null;
      while (index < maxIndex) {
        char c = chars.charAt(index);
        if (isInWord) {
          if (c == ' ' || c == '\t' || c == '\n' || c == MARKDOWN_START_LINE_CHAR) {
            isInWord = false;
            boolean isFirstWord = isFirstNonBlank;
            isFirstNonBlank = false;

            if (lastPos < index) {
              // have a word
              token = Token.of(TextType.WORD, lastPos, index, isFirstWord);
              lastPos = index;
              break;
            }
          } else {
            index++;
          }
        } else {
          // in white space
          if (c != ' ' && c != '\t' && c != '\n' && c != MARKDOWN_START_LINE_CHAR) {
            if (lastPos < index) {
              token = Token.of(TextType.SPACE, lastPos, index);
              lastPos = index;
              isInWord = true;
              lastConsecutiveSpaces = 0;
              break;
            }

            isInWord = true;
            lastConsecutiveSpaces = 0;
          } else {
            if (c == '\n') {
              if (lastConsecutiveSpaces >= 2) {
                token = Token.of(TextType.MARKDOWN_BREAK, index - lastConsecutiveSpaces, index + 1);
              } else {
                token = Token.of(TextType.BREAK, index, index + 1);
              }

              lastPos = index + 1;
              lastConsecutiveSpaces = 0;
              isFirstNonBlank = true;
              index++;
              break;
            } else if (c == MARKDOWN_START_LINE_CHAR) {
              token = Token.of(TextType.MARKDOWN_START_LINE, index, index + 1);
              lastPos = index + 1;
              lastConsecutiveSpaces = 0;
              index++;
              break;
            } else {
              if (c == ' ') lastConsecutiveSpaces++;
              else lastConsecutiveSpaces = 0;
              index++;
            }
          }
        }
      }

      if (lastPos < index) {
        if (isInWord) {
          token = Token.of(TextType.WORD, lastPos, index, isFirstNonBlank);
          isFirstNonBlank = false;
        } else {
          token = Token.of(TextType.SPACE, lastPos, index);
        }
        lastPos = index;
      }
    }
  }
}
