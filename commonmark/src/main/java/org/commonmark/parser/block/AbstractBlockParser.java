package org.commonmark.parser.block;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.node.Block;
import org.commonmark.parser.InlineParser;

public abstract class AbstractBlockParser implements BlockParser {
    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public boolean canContain(Block block) {
        return false;
    }

    @Override
    public void addLine(BasedSequence line, BasedSequence eol) {
    }

    @Override
    public void closeBlock() {
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
    }

}
