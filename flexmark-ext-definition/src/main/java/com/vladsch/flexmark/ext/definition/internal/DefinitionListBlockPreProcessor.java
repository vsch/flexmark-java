package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ast.BlankLine;
import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

import static com.vladsch.flexmark.parser.Parser.BLANK_LINES_IN_AST;

public class DefinitionListBlockPreProcessor implements BlockPreProcessor {
    private final DefinitionOptions options;

    public DefinitionListBlockPreProcessor(DataHolder options) {
        this.options = new DefinitionOptions(options);
    }

    @Override
    public void preProcess(ParserState state, Block block) {
        Boolean blankLinesInAST = state.getProperties().get(BLANK_LINES_IN_AST);
        
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
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            HashSet<Class<? extends Block>> set = new HashSet<Class<? extends Block>>();
            set.add(DefinitionList.class);
            return set;
        }

        @Override
        public Set<Class<? extends BlockPreProcessorFactory>> getAfterDependents() {
            HashSet<Class<? extends BlockPreProcessorFactory>> set = new HashSet<Class<? extends BlockPreProcessorFactory>>();
            set.add(DefinitionListItemBlockPreProcessor.Factory.class);
            return set;
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
            return new DefinitionListBlockPreProcessor(state.getProperties());
        }
    }
}
