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

public class Document extends Block implements MutableDataHolder {
  private final MutableDataSet dataSet;

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public Document(DataHolder options, BasedSequence chars) {
    super(chars);
    dataSet = new MutableDataSet(options);
  }

  @Override
  public MutableDataHolder clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> MutableDataHolder set(DataKey<T> key, T value) {
    return dataSet.set(key, value);
  }

  @Override
  public <T> MutableDataHolder set(NullableDataKey<T> key, T value) {
    return dataSet.set(key, value);
  }

  @Override
  public MutableDataSet setFrom(MutableDataSetter dataSetter) {
    return dataSet.setFrom(dataSetter);
  }

  @Override
  public MutableDataSet setAll(DataHolder other) {
    return dataSet.setAll(other);
  }

  @Override
  public MutableDataHolder setIn(MutableDataHolder dataHolder) {
    return dataSet.setIn(dataHolder);
  }

  @Override
  public MutableDataSet remove(DataKeyBase<?> key) {
    return dataSet.remove(key);
  }

  @Override
  public Object getOrCompute(DataKeyBase<?> key, DataValueFactory<?> factory) {
    return dataSet.getOrCompute(key, factory);
  }

  @Override
  public MutableDataSet toMutable() {
    return dataSet.toMutable();
  }

  @Override
  public DataSet toImmutable() {
    return dataSet.toImmutable();
  }

  @Override
  public MutableDataSet toDataSet() {
    return dataSet.toDataSet();
  }

  @Override
  public Map<? extends DataKeyBase<?>, Object> getAll() {
    return dataSet.getAll();
  }

  @Override
  public Collection<? extends DataKeyBase<?>> getKeys() {
    return dataSet.getKeys();
  }

  @Override
  public boolean contains(DataKeyBase<?> key) {
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
  int getLineNumber(int offset) {
    if (lineSegments == EMPTY_LIST) {
      BasedSequence preText =
          getChars().baseSubSequence(0, Utils.maxLimit(offset + 1, getChars().length()));

      if (preText.isEmpty()) {
        return 0;
      }
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
