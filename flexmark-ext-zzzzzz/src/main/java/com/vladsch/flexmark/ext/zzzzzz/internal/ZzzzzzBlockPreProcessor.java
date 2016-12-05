package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Collections;
import java.util.Set;

public class ZzzzzzBlockPreProcessor implements BlockPreProcessor {
    private final ZzzzzzOptions options;

    public ZzzzzzBlockPreProcessor(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    @Override
    public void preProcess(ParserState state, Block block) {
    }

    public static class Factory implements BlockPreProcessorFactory {
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            return Collections.singleton(Heading.class);
        }

        @Override
        public Set<Class<? extends BlockPreProcessorFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends BlockPreProcessorFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return true;
        }

        @Override
        public BlockPreProcessor create(ParserState state) {
            return new ZzzzzzBlockPreProcessor(state.getProperties());
        }
    }
}
