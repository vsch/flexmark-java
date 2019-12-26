package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.BitField;
import com.vladsch.flexmark.util.collection.BitFieldSet;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;

public interface TextContainer {
    enum Flags implements BitField {
        LINK_TEXT_TYPE(2),
        NODE_TEXT,  // not unescaped and not percent decoded
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

    int F_LINK_TEXT_TYPE = BitFieldSet.intMask(Flags.LINK_TEXT_TYPE);
    int F_LINK_TEXT = 0;
    int F_LINK_PAGE_REF = 1;
    int F_LINK_ANCHOR = 2;
    int F_LINK_URL = 3;

    int F_NODE_TEXT = BitFieldSet.intMask(Flags.NODE_TEXT);

    /**
     * Append node's text
     *
     * @param out   sequence build to which to append text
     * @param flags collection flags
     *
     * @return true if child nodes should be visited
     */
    boolean collectText(@NotNull SequenceBuilder out, int flags);
}
