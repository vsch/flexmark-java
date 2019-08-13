package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Del node
 */
public class GitLabDel extends GitLabInline {
    public GitLabDel() {
    }

    public GitLabDel(BasedSequence chars) {
        super(chars);
    }

    public GitLabDel(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }
}
