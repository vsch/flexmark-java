package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;

public interface DelimitedNode extends TextContainer {
    BasedSequence getOpeningMarker();

    void setOpeningMarker(BasedSequence openingMarker);

    BasedSequence getText();

    void setText(BasedSequence text);

    BasedSequence getClosingMarker();

    void setClosingMarker(BasedSequence closingMarker);

    @Override
    default boolean collectText(@NotNull SequenceBuilder out, int flags) {
        return true;
    }
}
