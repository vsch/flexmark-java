package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.ext.definition.DefinitionTerm;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.parser.core.ParagraphParser;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.vladsch.flexmark.parser.Parser.BLANK_LINES_IN_AST;

public class DefinitionListItemBlockPreProcessor implements BlockPreProcessor {
    final private DefinitionOptions options;
    Boolean blankLinesInAst;

    public DefinitionListItemBlockPreProcessor(DataHolder options) {
        this.options = new DefinitionOptions(options);
        blankLinesInAst = BLANK_LINES_IN_AST.get(options);
    }

    @Override
    public void preProcess(ParserState state, Block block) {
        if (block instanceof DefinitionItem) {
            // we chop up the previous paragraph into definition terms and add the definition item to the last one
            // we add all these to the previous DefinitionList or add a new one if there isn't one
            final DefinitionItem definitionItem = (DefinitionItem) block;
            final Node previous = block.getPreviousAnyNot(BlankLine.class);

            Node trailingBlankLines = new DefinitionList();

            Node blankLine = definitionItem.getNext();
            if (blankLine instanceof BlankLine) {
                blankLine.extractChainTo(trailingBlankLines);
            }

            if (previous instanceof Paragraph) {
                final Paragraph paragraph = (Paragraph) previous;
                Node afterParagraph = previous.getNext();

                Node paragraphPrevNonBlank = paragraph.getPreviousAnyNot(BlankLine.class);
                Node paragraphPrevious = paragraph.getPrevious();
                final Node paragraphParent = paragraph.getParent();

                definitionItem.unlink();
                paragraph.unlink();
                state.blockRemovedWithChildren(paragraph);

                final boolean hadPreviousList;
                if (options.doubleBlankLineBreaksList) {
                    // intervening characters between previous paragraph and definition terms
                    final BasedSequence interSpace = paragraphPrevNonBlank == null ? BasedSequence.NULL :
                            BasedSequence.of(paragraphPrevNonBlank.baseSubSequence(paragraphPrevNonBlank.getEndOffset(), paragraph.getStartOffset()).normalizeEOL());
                    hadPreviousList = paragraphPrevNonBlank instanceof DefinitionList && interSpace.countLeading(CharPredicate.EOL) < 2;
                } else {
                    hadPreviousList = paragraphPrevNonBlank instanceof DefinitionList;
                }

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
                if (blankLinesInAst && afterParagraph instanceof BlankLine) {
                    while (afterParagraph instanceof BlankLine) {
                        Node next = afterParagraph.getNext();
                        afterParagraph.unlink();
                        definitionList.appendChild(afterParagraph);
                        afterParagraph = next;
                    }
                }

                definitionList.appendChild(definitionItem);
                definitionList.takeChildren(trailingBlankLines);

                if (hadPreviousList) {
                    final DefinitionList previousList = (DefinitionList) paragraphPrevNonBlank;
                    previousList.takeChildren(definitionList);
                    for (Node node : definitionList.getChildren()) {
                        node.unlink();
                        previousList.appendChild(node);
                        state.blockAddedWithChildren((Block) node);
                    }

                    previousList.setCharsFromContent();
                } else {
                    // insert new one, after paragraphPrevious
                    if (paragraphPrevNonBlank != null) {
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
                previousList.takeChildren(trailingBlankLines);
                previousList.setCharsFromContent();
            }
        }
    }

    public static class Factory implements BlockPreProcessorFactory {
        @NotNull
        @Override
        public Set<Class<? extends Block>> getBlockTypes() {
            HashSet<Class<? extends Block>> set = new HashSet<>();
            set.add(DefinitionItem.class);
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
            return new DefinitionListItemBlockPreProcessor(state.getProperties());
        }
    }
}
