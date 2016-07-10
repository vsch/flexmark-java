package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.Visitor;

/**
 * A TOC node
 */
public class TocBlock extends CustomBlock {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence levelMarker= SubSequence.NULL;
    protected BasedSequence level = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, levelMarker, "levelMarker");
        segmentSpan(out, level, "level");
        segmentSpan(out, closingMarker, "close");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, levelMarker, level, closingMarker };
    }

    public TocBlock(BasedSequence chars) {
        this(chars, null);
    }

    public TocBlock(BasedSequence chars, BasedSequence levelChars) {
        super(chars);
        openingMarker = chars.subSequence(0, 4);
        if (levelChars != null) {
            levelMarker = levelChars.midSequence(0, -1);
            level = levelChars.endSequence(1);
        }
        closingMarker = chars.endSequence(1);
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public BasedSequence getLevelMarker() {
        return levelMarker;
    }

    public void setLevelMarker(BasedSequence levelMarker) {
        this.levelMarker = levelMarker;
    }

    public BasedSequence getLevelChars() {
        return level;
    }

    public int getLevel() {
        return levelMarker.isNotNull() ? Integer.valueOf(level.toString()) : 3;
    }

    public void setLevelChars(BasedSequence level) {
        this.level = level;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
