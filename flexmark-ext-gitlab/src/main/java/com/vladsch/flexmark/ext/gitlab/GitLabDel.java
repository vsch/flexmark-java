package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Del node
 */
public class GitLabDel extends GitLabInline {
    public GitLabDel() {
    }

    public GitLabDel(final BasedSequence chars) {
        super(chars);
    }

    public GitLabDel(final BasedSequence openingMarker, final BasedSequence text, final BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }
}
