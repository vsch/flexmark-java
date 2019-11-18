package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.edit.BasedSegmentBuilder;
import org.jetbrains.annotations.NotNull;

public interface BasedUtils {

    /**
     * Converts a based sequence, possibly with replaced characters to segment parts
     * uses getIndexOffset() which is to be retired.
     *
     * @param segments segment builder
     * @param base     base sequence
     * @param chars    base sequence to convert
     * @return true if had out of base chars
     */
    static boolean generateSegments(BasedSegmentBuilder segments, @NotNull BasedSequence base, @NotNull BasedSequence chars) {
        return generateSegments(segments, base, chars.getStartOffset(), chars.getEndOffset(), chars.length(), chars::getIndexOffset, (startIndex, endIndex) -> chars.subSequence(startIndex, endIndex).toString());
    }

    interface OffsetFunction {
        /**
         * Return -ve if this index is out of base
         *
         * @param index index into sequence
         * @return -ve or offset into base
         */
        int getIndexOffset(int index);
    }

    interface StringFunction {
        /**
         * Return -ve if this index is out of base
         *
         * @param startIndex index into sequence where -ve offset was first reported
         * @param endIndex   index into sequence where -ve offset stopped being reported
         * @return string for the subsequence
         */
        String getString(int startIndex, int endIndex);
    }

    static boolean generateSegments(BasedSegmentBuilder segments, @NotNull BasedSequence base, int startOffset, int endOffset, int sequenceLength, @NotNull OffsetFunction offsetFunction, @NotNull StringFunction stringFunction) {
        // find contiguous ranges of base chars and replaced chars, slower but only when optimizers are available
        int baseStart = -1;
        int baseEnd = -1;
        int stringStart = -1;
        boolean hadSequence = false;
        boolean hadOutOfBase = false;

        int iMax = sequenceLength;
        for (int i = 0; i < iMax; i++) {
            int offset = offsetFunction.getIndexOffset(i);

            if (offset != -1) {
                if (baseStart == -1) {
                    if (stringStart != -1) {
                        if (!hadSequence) {
                            segments.append(startOffset, startOffset);
                            hadSequence = true;
                        }
                        segments.append(stringFunction.getString(stringStart, i));
                        hadOutOfBase = true;
                        stringStart = -1;
                    }

                    baseStart = offset;
                } else {
                    if (offset > baseEnd + 1) {
                        // not contiguous base, append accumulated so far and start a new range
                        segments.append(baseStart, baseEnd + 1);
                        baseStart = i;
                    }
                }

                baseEnd = offset;
            } else {
                if (baseStart != -1) {
                    segments.append(baseStart, baseEnd + 1);
                    baseEnd = baseStart = -1;
                    hadSequence = true;
                }

                if (stringStart == -1) stringStart = i;
            }
        }

        if (baseStart != -1) {
            segments.append(baseStart, baseEnd + 1);
            hadSequence = true;
        }

        if (stringStart != -1) {
            if (!hadSequence) {
                segments.append(startOffset, startOffset);
                hadSequence = true;
            }
            segments.append(stringFunction.getString(stringStart, iMax));
            segments.append(endOffset, endOffset);
            hadOutOfBase = true;
        }

        if (!hadSequence) {
            assert sequenceLength == 0;
            segments.append(startOffset, startOffset);
        }
        return hadOutOfBase;
    }
}
