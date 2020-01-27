package com.vladsch.flexmark.ext.media.tags.internal;

import com.vladsch.flexmark.ast.InlineLinkNode;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class AbstractMediaLink extends InlineLinkNode {

    final private static String INVALID_SEQUENCE = "%s Link's CharSequence MUST start with an '%s'!";

    final private String PREFIX;
    final private String TYPE;

    public AbstractMediaLink(String prefix, String type) {
        this.PREFIX = prefix;
        this.TYPE = type;
    }

    public AbstractMediaLink(String prefix, String type, Link other) {
        super(other.baseSubSequence(other.getStartOffset() - prefix.length(), other.getEndOffset()),
                other.baseSubSequence(other.getStartOffset() - prefix.length(), other.getTextOpeningMarker().getEndOffset()),
                other.getText(),
                other.getTextClosingMarker(),
                other.getLinkOpeningMarker(),
                other.getUrl(),
                other.getTitleOpeningMarker(),
                other.getTitle(),
                other.getTitleClosingMarker(),
                other.getLinkClosingMarker()
        );

        this.PREFIX = prefix;
        this.TYPE = type;

        verifyBasedSequence(other.getChars(), other.getStartOffset() - prefix.length());
    }

    final public String getPrefix() {
        return PREFIX;
    }

    @Override
    public void setTextChars(BasedSequence textChars) {
        verifyBasedSequence(textChars, 0);

        int textCharsLength = textChars.length();
        textOpeningMarker = textChars.subSequence(0, PREFIX.length() + 1); // grab n characters, n - 1 for the PREFIX and 1 for the opener
        text = textChars.subSequence(PREFIX.length() + 2, textCharsLength - 1).trim();
        textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
    }

    protected final void verifyBasedSequence(BasedSequence chars, int startOffset) {
        if (!chars.baseSubSequence(startOffset, startOffset + PREFIX.length()).matches(PREFIX)) {
            throw new IllegalArgumentException(String.format(INVALID_SEQUENCE, TYPE, PREFIX));
        }
    }
}
