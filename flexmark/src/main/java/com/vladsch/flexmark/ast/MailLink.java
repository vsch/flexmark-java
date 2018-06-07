package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class MailLink extends DelimitedLinkNode {
    public MailLink() {
    }

    public MailLink(BasedSequence chars) {
        super(chars);
    }

    public MailLink(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }
}
