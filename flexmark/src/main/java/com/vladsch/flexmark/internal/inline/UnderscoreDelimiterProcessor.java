package com.vladsch.flexmark.internal.inline;

public class UnderscoreDelimiterProcessor extends EmphasisDelimiterProcessor {
    public UnderscoreDelimiterProcessor() {
        super('_');
    }

    @Override
    public boolean canBeOpener(boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return leftFlanking && (!rightFlanking || beforeIsPunctuation);
    }

    @Override
    public boolean canBeCloser(boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return rightFlanking && (!leftFlanking || afterIsPunctuation);
    }
}
