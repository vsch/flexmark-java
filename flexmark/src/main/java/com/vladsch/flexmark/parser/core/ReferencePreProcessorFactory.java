package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.parser.internal.InlineParserImpl;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessor;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;

import java.util.Set;

public class ReferencePreProcessorFactory implements ParagraphPreProcessorFactory {
    @Override
    public boolean affectsGlobalScope() {
        return true;
    }

    @Override
    public Set<Class<? extends ParagraphPreProcessorFactory>> getAfterDependents() {
        return null;
    }

    @Override
    public Set<Class<? extends ParagraphPreProcessorFactory>> getBeforeDependents() {
        return null;
    }

    @Override
    public ParagraphPreProcessor create(ParserState state) {
        return ((InlineParserImpl) state.getInlineParser());
    }
}
