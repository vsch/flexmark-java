package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

import static com.vladsch.flexmark.parser.Parser.BLANK_LINES_IN_AST;

public class DefinitionListBlockPreProcessor implements BlockPreProcessor {
    final private DefinitionOptions options;

    public DefinitionListBlockPreProcessor(DataHolder options) {
        this.options = new DefinitionOptions(options);
    }

    @Override
    public void preProcess(ParserState state, Block block) {
        Boolean blankLinesInAST = BLANK_LINES_IN_AST.get(state.getProperties());

        if (block instanceof DefinitionList) {
            // need to propagate loose/tight
            final DefinitionList definitionList = (DefinitionList) block;
            boolean isTight = definitionList.isTight();
            if (options.autoLoose && isTight) {
                for (Node child : definitionList.getChildren()) {
                    if (child instanceof DefinitionItem) {
                        if (((DefinitionItem) child).isLoose()) {
                            isTight = false;
                            if (!blankLinesInAST) break;
                        }

                        if (blankLinesInAST) {
                            // transfer its trailing blank lines to uppermost level
                            child.moveTrailingBlankLines();
                        }
                    }
                }

                definitionList.setTight(isTight);
            }

            if (blankLinesInAST) {
                definitionList.moveTrailingBlankLines();
            }
        }
    }

    public static class Factory implements BlockPreProcessorFactory {
        @NotNull
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            HashSet<Class<? extends Block>> set = new HashSet<>();
            set.add(DefinitionList.class);
            return set;
        }

        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            HashSet<Class<?>> set = new HashSet<>();
            set.add(DefinitionListItemBlockPreProcessor.Factory.class);
            return set;
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
            return new DefinitionListBlockPreProcessor(state.getProperties());
        }
    }
}
