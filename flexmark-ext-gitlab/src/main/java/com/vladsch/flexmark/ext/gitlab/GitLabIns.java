package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Ins node
 */
public class GitLabIns extends GitLabInline {
    public GitLabIns() {
    }

    public GitLabIns(final BasedSequence chars) {
        super(chars);
    }

    public GitLabIns(final BasedSequence openingMarker, final BasedSequence text, final BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }
}
