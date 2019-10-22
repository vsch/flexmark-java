package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

import static com.vladsch.flexmark.util.sequence.BasedSequence.EMPTY_LIST;

public class Document extends Block implements MutableDataHolder {
    private final MutableDataSet dataSet;

    @NotNull
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
    public <T> MutableDataSet set(DataKeyBase<T> key, T value) {return dataSet.set(key, value);}

    @Override
    public MutableDataSet setFrom(MutableDataSetter dataSetter) {return dataSet.setFrom(dataSetter);}

    @Override
    public MutableDataSet setAll(DataHolder other) {return dataSet.setAll(other);}

    public static MutableDataSet merge(DataHolder... dataHolders) {return MutableDataSet.merge(dataHolders);}

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {return dataSet.setIn(dataHolder);}

    @Override
    public <T> MutableDataSet remove(DataKeyBase<T> key) {return dataSet.remove(key);}

    @Override
    @Nullable
    public Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {return dataSet.getOrCompute(key, factory);}

    @Override
    @NotNull
    public MutableDataSet toMutable() {return dataSet.toMutable();}

    @Override
    @NotNull
    public DataSet toImmutable() {return dataSet.toImmutable();}

    @Override
    @NotNull
    public MutableDataSet toDataSet() {return dataSet.toDataSet();}

    @NotNull
    public static DataHolder aggregateActions(@NotNull DataHolder other, @NotNull DataHolder overrides) {return DataSet.aggregateActions(other, overrides);}

    @NotNull
    public DataHolder aggregate() {return dataSet.aggregate();}

    @NotNull
    public static DataHolder aggregate(@Nullable DataHolder other, @Nullable DataHolder overrides) {return DataSet.aggregate(other, overrides);}

    @Override
    @NotNull
    public Map<? extends DataKeyBase<?>, Object> getAll() {return dataSet.getAll();}

    @Override
    @NotNull
    public Collection<? extends DataKeyBase<?>> getKeys() {return dataSet.getKeys();}

    @Override
    public boolean contains(@NotNull DataKeyBase<?> key) {return dataSet.contains(key);}

    @Override
    public int getLineCount() {
        if (lineSegments == EMPTY_LIST) {
            char c = getChars().lastChar();
            return (c == '\n' || c == '\r' ? 0 : 1) + getLineNumber(getChars().length());
        } else {
            return lineSegments.size();
        }
    }

    /**
     * Get line number at offset
     * <p>
     * Next line starts after the EOL sequence.
     * offsets between \r and \n are considered part of the same line as offset before \r.
     *
     * @param offset offset in document text
     * @return line number at offset
     */
    public int getLineNumber(int offset) {
        if (lineSegments == EMPTY_LIST) {
            BasedSequence preText = getChars().baseSubSequence(0, Utils.maxLimit(offset + 1, getChars().length()));

            if (preText.isEmpty()) return 0;
            int lineNumber = 0;
            int nextLineEnd = preText.endOfLineAnyEOL(0);
            int length = preText.length();
            while (nextLineEnd < length) {
                int lengthWithEOL = nextLineEnd + preText.eolLength(nextLineEnd);
                if (offset >= lengthWithEOL) lineNumber++; // do not treat offset between \r and \n as complete line
                nextLineEnd = preText.endOfLineAnyEOL(lengthWithEOL);
            }

            return lineNumber;
        } else {
            int iMax = lineSegments.size();
            for (int i = 0; i < iMax; i++) {
                if (offset < lineSegments.get(i).getEndOffset()) {
                    return i;
                }
            }
            return iMax;
        }
    }
}
