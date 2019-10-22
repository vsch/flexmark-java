package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.BlockParser;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.BlankLineContainer;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import static com.vladsch.flexmark.parser.Parser.TRACK_DOCUMENT_LINES;

public class DocumentBlockParser extends AbstractBlockParser implements BlankLineContainer {
    private Document document;

    public DocumentBlockParser() {
    }

    public void initializeDocument(DataHolder options, BasedSequence charSequence) {
        document = new Document(options, charSequence);
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, Block block) {
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
    public void addLine(ParserState state, BasedSequence line) {
    }

    @Override
    public void closeBlock(ParserState state) {
        if (TRACK_DOCUMENT_LINES.get(state.getProperties())) {
            document.setContent(state.getLineSegments());
        }
    }
}
