package com.vladsch.flexmark.parser.core.delimiter;

public class UnderscoreDelimiterProcessor extends EmphasisDelimiterProcessor {
    public UnderscoreDelimiterProcessor(boolean strongWrapsEmphasis) {
        super('_', strongWrapsEmphasis);
    }

    @Override
    public boolean canBeOpener(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return leftFlanking && (!rightFlanking || beforeIsPunctuation);
    }

    @Override
    public boolean canBeCloser(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return rightFlanking && (!leftFlanking || afterIsPunctuation);
    }
}
