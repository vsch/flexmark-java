package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;

public class Reference extends Node {
    public BasedSequence openingMarker = SubSequence.EMPTY;
    public BasedSequence reference = SubSequence.EMPTY;
    public BasedSequence closingMarker = SubSequence.EMPTY;
    public BasedSequence url = SubSequence.EMPTY;
    public BasedSequence titleOpenMarker = SubSequence.EMPTY;
    public BasedSequence title = SubSequence.EMPTY;
    public BasedSequence titleCloseMarker = SubSequence.EMPTY;

    public Reference(BasedSequence label, BasedSequence url, BasedSequence title) {
        super(new SubSequence(label.getBase().subSequence(label.getStartOffset(), (title != null ? title.getEndOffset() : (url != null ? url.getEndOffset() : label.getEndOffset())))));

        this.openingMarker = label.subSequence(0, 1);
        this.reference = label.subSequence(1, label.length() - 2).trim();
        this.closingMarker = label.subSequence(label.length() - 2, label.length());

        if (url != null) this.url = url;

        if (title != null) {
            this.titleOpenMarker = title.subSequence(0, 1);
            this.title = title.subSequence(1, title.length() - 1);
            this.titleCloseMarker = title.subSequence(title.length() - 1, title.length());
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected String toStringAttributes() {
        return "reference=" + reference + ", url=" + url;
    }
}
