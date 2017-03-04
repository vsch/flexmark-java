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

import static com.vladsch.flexmark.parser.Parser.BLANK_LINES_IN_AST;

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
            final Node previous = block.getPreviousAnyNot(BlankLine.class);

            if (previous instanceof Paragraph) {
                final Paragraph paragraph = (Paragraph) previous;
                Node afterParagraph = previous.getNext();

                Node paragraphPrevious = paragraph.getPreviousAnyNot(BlankLine.class);
                final Node paragraphParent = paragraph.getParent();

                definitionItem.unlink();
                paragraph.unlink();
                state.blockRemovedWithChildren(paragraph);

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

                // if have blank lines after paragraph need to move them after the last term
                if (state.getProperties().get(BLANK_LINES_IN_AST) && afterParagraph instanceof BlankLine) {
                    while (afterParagraph instanceof BlankLine) {
                        Node next = afterParagraph.getNext();
                        afterParagraph.unlink();
                        definitionList.appendChild(afterParagraph);
                        afterParagraph = next;
                    }
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
                        if (paragraphParent.getFirstChild() != null) {
                            paragraphParent.getFirstChild().insertBefore(definitionList);
                        } else {
                            paragraphParent.appendChild(definitionList);
                        }
                    }

                    definitionList.setCharsFromContent();
                    state.blockAddedWithChildren(definitionList);
                }
            } else if (previous instanceof DefinitionList) {
                final DefinitionList previousList = (DefinitionList) previous;
                definitionItem.unlink();
                previousList.appendChild(definitionItem);
                previousList.setCharsFromContent();
            }
        }
    }

    public static class Factory implements BlockPreProcessorFactory {
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            HashSet<Class<? extends Block>> set = new HashSet<Class<? extends Block>>();
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
