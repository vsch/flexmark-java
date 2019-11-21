package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.edit.BasedSegmentBuilder;
import org.jetbrains.annotations.NotNull;

public interface BasedUtils {
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

    /**
     * Generate segments for given sequence
     *
     * @param segments       segment builder
     * @param chars          based sequence for which to generate segments
     * @return true if had out of base chars.
     */
    static boolean generateSegments(BasedSegmentBuilder segments, @NotNull BasedSequence chars) {
        // find contiguous ranges of base chars and replaced chars, slower but only when optimizers are available
        int baseStart = -1;
        int baseEnd = -1;
        boolean hadSequence = false;
        boolean hadOutOfBase = false;

        StringBuilder stringBuilder = null;

        int iMax = chars.length();
        for (int i = 0; i < iMax; i++) {
            int offset = chars.getIndexOffset(i);

            if (offset >= 0) {
                if (baseStart == -1) {
                    if (stringBuilder != null) {
                        if (!hadSequence) {
                            segments.appendAnchor(chars.getStartOffset());
                            hadSequence = true;
                        }
                        segments.append(stringBuilder.toString());
                        stringBuilder = null;
                        hadOutOfBase = true;
                    }

                    baseStart = offset;
                } else {
                    if (offset > baseEnd + 1) {
                        // not contiguous base, append accumulated so far and start a new range
                        segments.append(baseStart, baseEnd + 1);
                        baseStart = offset;
                    }
                }

                baseEnd = offset;
            } else {
                if (baseStart != -1) {
                    segments.append(baseStart, baseEnd + 1);
                    baseEnd = baseStart = -1;
                    hadSequence = true;
                }

                if (stringBuilder == null) stringBuilder = new StringBuilder();
                stringBuilder.append(chars.charAt(i));
            }
        }

        if (baseStart != -1) {
            segments.append(baseStart, baseEnd + 1);
            hadSequence = true;
        }

        if (stringBuilder != null) {
            if (!hadSequence) {
                segments.appendAnchor(chars.getStartOffset());
                hadSequence = true;
            }
            segments.append(stringBuilder.toString());
            segments.appendAnchor(chars.getEndOffset());
            hadOutOfBase = true;
        }

        if (!hadSequence) {
            assert chars.length() == 0;
            segments.appendAnchor(chars.getStartOffset());
        }
        return hadOutOfBase;
    }
}
