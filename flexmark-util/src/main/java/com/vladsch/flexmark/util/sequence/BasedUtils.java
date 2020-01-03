package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import org.jetbrains.annotations.NotNull;

public interface BasedUtils {
    /**
     * Generate segments for given sequence
     *
     * @param segments segment builder
     * @param chars    based sequence for which to generate segments
     */
    static void generateSegments(IBasedSegmentBuilder<?> segments, @NotNull BasedSequence chars) {
        // find contiguous ranges of base chars and replaced chars, slower but only when optimizers are available
        int baseStart = -1;
        int baseEnd = -1;
        boolean hadSequence = false;

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
        }

        if (!hadSequence) {
            assert chars.length() == 0;
            segments.appendAnchor(chars.getStartOffset());
        }
    }
    static BasedSequence asBased(CharSequence sequence) {
        return BasedSequence.of(sequence);
    }
}
