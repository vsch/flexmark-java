package org.commonmark.internal;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.node.Block;
import org.commonmark.node.Document;
import org.commonmark.parser.block.AbstractBlockParser;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.ParserState;

public class DocumentBlockParser extends AbstractBlockParser {

    private Document document;

    public DocumentBlockParser() {
    }

    public void setDocument(BasedSequence charSequence) {
        document = new Document(charSequence);
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(Block block) {
        return true;
    }

    @Override
    public Document getBlock() {
        return document;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        return BlockContinue.atIndex(state.getIndex());
    }

    @Override
    public void addLine(BasedSequence line) {
    }

}
