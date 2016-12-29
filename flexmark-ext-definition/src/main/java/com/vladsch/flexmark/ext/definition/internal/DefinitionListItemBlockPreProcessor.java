package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.ext.definition.DefinitionTerm;
import com.vladsch.flexmark.internal.ParagraphParser;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefinitionListItemBlockPreProcessor implements BlockPreProcessor {
    private final DefinitionOptions options;

    public DefinitionListItemBlockPreProcessor(DataHolder options) {
        this.options = new DefinitionOptions(options);
    }

    @Override
    public void preProcess(ParserState state, Block block) {
        if (block instanceof DefinitionItem) {
            // we chop up the previous paragraph into definition terms and add the definition item to the last one
            // we add all these to the previous DefinitionList or add a new one if there isn't one
            final DefinitionItem definitionItem = (DefinitionItem) block;
            final Node previous = block.getPrevious();

            definitionItem.unlink();

            if (previous instanceof Paragraph) {
                final Paragraph paragraph = (Paragraph) previous;
                Node paragraphPrevious = paragraph.getPrevious();
                final Node paragraphParent = paragraph.getParent();

                paragraph.unlink();
                state.blockRemovedWithChildren(paragraph);

                if (paragraphPrevious == null) {
                    paragraphPrevious = paragraphParent.getLastChild();
                }

                final boolean hadPreviousList = paragraphPrevious instanceof DefinitionList;
                final DefinitionList definitionList = new DefinitionList();
                definitionList.setTight(true);

                final List<BasedSequence> lines = paragraph.getContentLines();
                DefinitionTerm definitionTerm = null;

                int lineIndex = 0;
                for (BasedSequence line : lines) {
                    definitionTerm = new DefinitionTerm();

                    ParagraphParser parser = new ParagraphParser();
                    BlockContent content = new BlockContent();
                    content.add(line, paragraph.getLineIndent(lineIndex++));
                    parser.getBlock().setContent(content);
                    parser.getBlock().setCharsFromContent();

                    definitionTerm.appendChild(parser.getBlock());
                    definitionTerm.setCharsFromContent();

                    state.blockParserAdded(parser);

                    definitionList.appendChild(definitionTerm);
                    state.blockAdded(definitionTerm);
                }

                definitionList.appendChild(definitionItem);

                if (hadPreviousList) {
                    final DefinitionList previousList = (DefinitionList) paragraphPrevious;
                    previousList.takeChildren(definitionList);
                    for (Node node : definitionList.getChildren()) {
                        node.unlink();
                        previousList.appendChild(node);
                        state.blockAddedWithChildren((Block) node);
                    }

                    previousList.setCharsFromContent();
                } else {
                    // insert new one, after paragraphPrevious
                    if (paragraphPrevious != null) {
                        paragraphPrevious.insertAfter(definitionList);
                    } else {
                        paragraphParent.appendChild(definitionList);
                    }

                    definitionList.setCharsFromContent();
                    state.blockAddedWithChildren(definitionList);
                }
            }
        }
    }

    public static class Factory implements BlockPreProcessorFactory {
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            HashSet<Class<? extends Block>> set = new HashSet<>();
            set.add(DefinitionItem.class);
            return set;
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
            return new DefinitionListItemBlockPreProcessor(state.getProperties());
        }
    }
}
