package com.vladsch.flexmark.ext.youtube.embedded;

import com.vladsch.flexmark.ast.InlineLinkNode;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class YouTubeLink extends InlineLinkNode {
    public YouTubeLink() {
    }

    public YouTubeLink(final Link other) {
        super(other.getChars().baseSubSequence(other.getChars().getStartOffset() - 1, other.getChars().getEndOffset()),
                other.getChars().baseSubSequence(other.getChars().getStartOffset() - 1, other.getTextOpeningMarker().getEndOffset()),
                other.getText(),
                other.getTextClosingMarker(),
                other.getLinkOpeningMarker(),
                other.getUrl(),
                other.getTitleOpeningMarker(),
                other.getTitle(),
                other.getTitleClosingMarker(),
                other.getLinkClosingMarker()
        );
    }

    @Override
    public void setTextChars(final BasedSequence textChars) {
        int textCharsLength = textChars.length();
        this.textOpeningMarker = textChars.subSequence(0, 1);
        this.text = textChars.subSequence(1, textCharsLength - 1).trim();
        this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
    }
}
