package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public interface BlockQuoteLike {
    BasedSequence getOpeningMarker();
    Node getFirstChild();
    BasedSequence getChars();
    Document getDocument();
}
