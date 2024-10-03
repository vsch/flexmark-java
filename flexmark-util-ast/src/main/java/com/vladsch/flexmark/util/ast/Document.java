package com.vladsch.flexmark.util.ast;

import static com.vladsch.flexmark.util.sequence.BasedSequence.EMPTY_LIST;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.DataValueFactory;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.Collection;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Document extends Block implements MutableDataHolder {
  public static final Document NULL = new Document(null, BasedSequence.NULL);

  private final MutableDataSet dataSet;

  @Override
  public @NotNull BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public Document(DataHolder options, BasedSequence chars) {
    super(chars);
    dataSet = new MutableDataSet(options);
  }

  @Override
  public @NotNull MutableDataHolder clear() {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  public <T> MutableDataHolder set(@NotNull DataKey<T> key, @NotNull T value) {
    return dataSet.set(key, value);
  }

  @NotNull
  @Override
  public <T> MutableDataHolder set(@NotNull NullableDataKey<T> key, @Nullable T value) {
    return dataSet.set(key, value);
  }

  @NotNull
  @Override
  public MutableDataSet setFrom(@NotNull MutableDataSetter dataSetter) {
    return dataSet.setFrom(dataSetter);
  }

  @NotNull
  @Override
  public MutableDataSet setAll(@NotNull DataHolder other) {
    return dataSet.setAll(other);
  }

  public static MutableDataSet merge(DataHolder... dataHolders) {
    return MutableDataSet.merge(dataHolders);
  }

  @NotNull
  @Override
  public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
    return dataSet.setIn(dataHolder);
  }

  @NotNull
  @Override
  public MutableDataSet remove(@NotNull DataKeyBase<?> key) {
    return dataSet.remove(key);
  }

  @Override
  @Nullable
  public Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {
    return dataSet.getOrCompute(key, factory);
  }

  @Override
  @NotNull
  public MutableDataSet toMutable() {
    return dataSet.toMutable();
  }

  @Override
  @NotNull
  public DataSet toImmutable() {
    return dataSet.toImmutable();
  }

  @Override
  @NotNull
  public MutableDataSet toDataSet() {
    return dataSet.toDataSet();
  }

  @NotNull
  public static DataHolder aggregateActions(
      @NotNull DataHolder other, @NotNull DataHolder overrides) {
    return DataSet.aggregateActions(other, overrides);
  }

  @NotNull
  public DataHolder aggregate() {
    return dataSet.aggregate();
  }

  @NotNull
  public static DataHolder aggregate(@Nullable DataHolder other, @Nullable DataHolder overrides) {
    return DataSet.aggregate(other, overrides);
  }

  @Override
  @NotNull
  public Map<? extends DataKeyBase<?>, Object> getAll() {
    return dataSet.getAll();
  }

  @Override
  @NotNull
  public Collection<? extends DataKeyBase<?>> getKeys() {
    return dataSet.getKeys();
  }

  @Override
  public boolean contains(@NotNull DataKeyBase<?> key) {
    return dataSet.contains(key);
  }

  @Override
  public int getLineCount() {
    if (lineSegments == EMPTY_LIST) {
      char c = getChars().lastChar();
      return (c == '\n' || c == '\r' ? 0 : 1) + getLineNumber(getChars().length());
    }

    return lineSegments.size();
  }

  /**
   * Get line number at offset
   *
   * <p>Next line starts after the EOL sequence. offsets between \r and \n are considered part of
   * the same line as offset before \r.
   *
   * @param offset offset in document text
   * @return line number at offset
   */
  public int getLineNumber(int offset) {
    if (lineSegments == EMPTY_LIST) {
      BasedSequence preText =
          getChars().baseSubSequence(0, Utils.maxLimit(offset + 1, getChars().length()));

      if (preText.isEmpty()) return 0;
      int lineNumber = 0;
      int nextLineEnd = preText.endOfLineAnyEOL(0);
      int length = preText.length();
      while (nextLineEnd < length) {
        int eolLength = preText.eolStartLength(nextLineEnd);
        int lengthWithEOL = nextLineEnd + eolLength;
        if (offset >= lengthWithEOL) {
          lineNumber++; // do not treat offset between \r and \n as complete line
        }
        nextLineEnd = preText.endOfLineAnyEOL(lengthWithEOL);
      }

      return lineNumber;
    }

    int iMax = lineSegments.size();
    for (int i = 0; i < iMax; i++) {
      if (offset < lineSegments.get(i).getEndOffset()) {
        return i;
      }
    }

    return iMax;
  }
}
