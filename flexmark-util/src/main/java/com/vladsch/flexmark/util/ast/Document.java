package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Collection;
import java.util.Map;

import static com.vladsch.flexmark.util.sequence.BasedSequence.EMPTY_LIST;

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
    public Map<DataKey, Object> getAll() { return dataSet.getAll(); }

    @Override
    public Collection<DataKey> keySet() { return dataSet.keySet(); }

    @Override
    public boolean contains(DataKey key) { return dataSet.contains(key); }

    @Override
    public <T> T get(DataKey<T> key) {
        return dataSet.get(key);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        return dataSet.setIn(dataHolder);
    }

    @Override
    public int getLineCount() {
        if (lineSegments == EMPTY_LIST) {
            final char c = getChars().lastChar();
            return (c == '\n' || c == '\r' ? 0 : 1) + getLineNumber(getChars().length());
        } else {
            return lineSegments.size();
        }
    }

    /**
     * Get line number at offset
     *
     * Next line starts after the EOL sequence.
     * offsets between \r and \n are considered part of the same line as offset before \r.
     *
     * @param offset  offset in document text
     * @return  line number at offset
     */
    public int getLineNumber(final int offset) {
        if (lineSegments == EMPTY_LIST) {
            BasedSequence preText = getChars().baseSubSequence(0, Utils.maxLimit(offset + 1, getChars().length()));

            if (preText.isEmpty()) return 0;
            int lineNumber = 0;
            int nextLineEnd = preText.endOfLineAnyEOL(0);
            final int length = preText.length();
            while (nextLineEnd < length) {
                int lengthWithEOL = nextLineEnd + preText.eolLength(nextLineEnd);
                if (offset >= lengthWithEOL) lineNumber++; // do not treat offset between \r and \n as complete line
                nextLineEnd = preText.endOfLineAnyEOL(lengthWithEOL);
            }

            return lineNumber;
        } else {
            final int iMax = lineSegments.size();
            for (int i = 0; i < iMax; i++) {
                if (offset < lineSegments.get(i).getEndOffset()) {
                    return i;
                }
            }
            return iMax;
        }
    }

    @Override
    public <T> T getOrCompute(DataKey<T> key, DataValueFactory<T> factory) { return dataSet.getOrCompute(key, factory); }

    @Override
    public <T> MutableDataHolder remove(final DataKey<T> key) { return dataSet.remove(key); }

    @Override
    public <T> MutableDataHolder set(DataKey<? extends T> key, T value) { return dataSet.set(key, value);}

    @Override
    public MutableDataHolder setFrom(MutableDataSetter dataSetter) { return dataSet.setFrom(dataSetter); }

    @Override
    public MutableDataHolder setAll(DataHolder other) {
        dataSet.setAll(other);
        return dataSet;
    }

    @Override
    public MutableDataHolder toMutable() { return dataSet.toMutable(); }

    @Override
    public DataHolder toImmutable() { return dataSet.toImmutable(); }

    @Override
    public MutableDataHolder clear() {
        throw new UnsupportedOperationException();
    }
}
