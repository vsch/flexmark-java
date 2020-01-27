package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ZzzzzzBlockPreProcessor implements BlockPreProcessor {
    final private ZzzzzzOptions options;

    public ZzzzzzBlockPreProcessor(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    @Override
    public void preProcess(ParserState state, Block block) {
    }

    public static class Factory implements BlockPreProcessorFactory {
        @NotNull
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            HashSet<Class<? extends Block>> set = new HashSet<>();
            set.add(Heading.class);
            return set;
        }

        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return true;
        }

        @NotNull
        @Override
        public BlockPreProcessor apply(@NotNull ParserState state) {
            return new ZzzzzzBlockPreProcessor(state.getProperties());
        }
    }
}
