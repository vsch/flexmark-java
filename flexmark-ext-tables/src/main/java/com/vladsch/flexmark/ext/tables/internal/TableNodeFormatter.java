package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.tables.TableBlock;
import com.vladsch.flexmark.ext.tables.TableBody;
import com.vladsch.flexmark.ext.tables.TableCaption;
import com.vladsch.flexmark.ext.tables.TableCell;
import com.vladsch.flexmark.ext.tables.TableHead;
import com.vladsch.flexmark.ext.tables.TableRow;
import com.vladsch.flexmark.ext.tables.TableSeparator;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.formatter.NodeFormatter;
import com.vladsch.flexmark.formatter.NodeFormatterContext;
import com.vladsch.flexmark.formatter.NodeFormatterFactory;
import com.vladsch.flexmark.formatter.NodeFormattingHandler;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.html.LineAppendable;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.vladsch.flexmark.formatter.RenderPurpose.FORMAT;
import static com.vladsch.flexmark.util.format.TableManipulator.NULL;

public class TableNodeFormatter implements NodeFormatter {
    private final TableFormatOptions options;
    private final boolean isIntellijDummyIdentifier;
    private final String intellijDummyIdentifier;
    private final boolean parserTrimCellWhiteSpace;

    private MarkdownTable myTable;

    public TableNodeFormatter(DataHolder options) {
        this.options = new TableFormatOptions(options);
        isIntellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.get(options);
        intellijDummyIdentifier = isIntellijDummyIdentifier ? TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER : "";
        parserTrimCellWhiteSpace = TablesExtension.TRIM_CELL_WHITESPACE.get(options);
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(TableBlock.class, TableNodeFormatter.this::render),
                new NodeFormattingHandler<>(TableHead.class, TableNodeFormatter.this::render),
                new NodeFormattingHandler<>(TableSeparator.class, TableNodeFormatter.this::render),
                new NodeFormattingHandler<>(TableBody.class, TableNodeFormatter.this::render),
                new NodeFormattingHandler<>(TableRow.class, TableNodeFormatter.this::render),
                new NodeFormattingHandler<>(TableCell.class, TableNodeFormatter.this::render),
                new NodeFormattingHandler<>(TableCaption.class, TableNodeFormatter.this::render),
                new NodeFormattingHandler<>(Text.class, TableNodeFormatter.this::render)
        ));
    }

    private void render(TableBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        myTable = new MarkdownTable(node.getChars(), options);

        switch (context.getRenderPurpose()) {
            case TRANSLATION_SPANS:
            case TRANSLATED_SPANS:
            case TRANSLATED:
                markdown.blankLine();
                context.renderChildren(node);
                markdown.tailBlankLine();
                break;

            case FORMAT:
            default:
                context.renderChildren(node);

                List<TrackedOffset> formatTrackedOffsets = Formatter.TRACKED_OFFSETS.get(context.getDocument());
                int tableOffsetCount = 0;

                if (!formatTrackedOffsets.isEmpty()) {
                    for (TrackedOffset trackedOffset : formatTrackedOffsets) {
                        if (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset()) {
                            tableOffsetCount++;
                            myTable.addTrackedOffset(trackedOffset);
                        }
                    }
                }

                // allow table manipulation, mostly for testing
                if (options.tableManipulator != NULL) {
                    myTable.normalize();
                    options.tableManipulator.apply(myTable, node);
                }

                if (myTable.getMaxColumns() > 0) {
                    // output table
                    markdown.blankLine();

                    CharSequence prefix = markdown.getPrefix();
                    int startOffset = markdown.offsetWithPending();
                    markdown.pushPrefix().setPrefix("", false);

                    myTable.setFormatTableIndentPrefix(prefix);
                    MarkdownWriter formattedTable = new MarkdownWriter(markdown.getOptions());
                    myTable.appendTable(formattedTable);

                    markdown.pushOptions()
                            .removeOptions(LineAppendable.F_COLLAPSE_WHITESPACE | LineAppendable.F_TRIM_TRAILING_WHITESPACE | LineAppendable.F_TRIM_LEADING_WHITESPACE)
                            .append(formattedTable)
                            .popOptions()
                            .popPrefix(false);
                    markdown.tailBlankLine();

                    List<TrackedOffset> tableOffsets = myTable.getTrackedOffsets();

                    if (tableOffsetCount > 0) {
                        assert tableOffsetCount == tableOffsets.size();

                        // get the indent used for new lines so that index can be adjusted by added indent
                        for (TrackedOffset trackedOffset:tableOffsets) {
                            if (trackedOffset.isResolved()) {
                                trackedOffset.setIndex(trackedOffset.getIndex() + startOffset);
                            }
                        }

                        if (options.dumpIntellijOffsets) {
                            markdown.append("\nTracked Offsets").line();  // simulate flex example ast dump
                            String sep = "  ";
                            int i = 0;
                            for (TrackedOffset trackedOffset : tableOffsets) {
                                i++;
                                markdown.append(sep).append(String.format(Locale.US, "%d:[%d,%d] was:[%d,%d]", i, trackedOffset.getIndex(), trackedOffset.getIndex() + 1, trackedOffset.getOffset(), trackedOffset.getOffset() + 1));
                                sep = " ";
                            }
                            markdown.append("\n");
                        }
                    }
                }
        }

        myTable = null;
    }

    private void render(TableHead node, NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(false);
        myTable.setHeader(true);
        context.renderChildren(node);
    }

    private void render(TableSeparator node, NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(true);
        context.renderChildren(node);
    }

    private void render(TableBody node, NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(false);
        myTable.setHeader(false);
        context.renderChildren(node);
    }

    private void render(TableRow node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
        if (context.getRenderPurpose() == FORMAT) {
            if (!myTable.isSeparator()) myTable.nextRow();
        } else {
            markdown.line();
        }
    }

    private void render(TableCaption node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getRenderPurpose() == FORMAT) {
            myTable.setCaptionWithMarkers(node, node.getOpeningMarker(), node.getText(), node.getClosingMarker());
        } else {
            // HACK: to reuse the table formatting logic of MarkdownTable
            String dummyCaption = node.hasChildren() ? "dummy" : "";
            String formattedCaption = MarkdownTable.formattedCaption(BasedSequence.of(dummyCaption).subSequence(0, ((CharSequence) dummyCaption).length()), options);

            if (formattedCaption != null) {
                markdown.line().append(node.getOpeningMarker());
                context.renderChildren(node);
                markdown.append(node.getClosingMarker()).line();
            }
        }
    }

    private void render(TableCell node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getRenderPurpose() == FORMAT) {
            BasedSequence text = node.getText();
            if (options.trimCellWhitespace) {
                if (text.isBlank() && !text.isEmpty()) {
                    text = text.subSequence(0, 1);
                } else {
                    text = text.trim();
                }
            }
            myTable.addCell(new com.vladsch.flexmark.util.format.TableCell(node, node.getOpeningMarker(), text, node.getClosingMarker(), 1, node.getSpan(), node.getAlignment() == null ? CellAlignment.NONE : node.getAlignment().cellAlignment()));
        } else {
            if (node.getPrevious() == null) {
                if (options.leadTrailPipes && node.getOpeningMarker().isEmpty()) markdown.append('|');
                else markdown.append(node.getOpeningMarker());
            } else {
                markdown.append(node.getOpeningMarker());
            }

            if (!myTable.isSeparator() && options.spaceAroundPipes && (!node.getText().startsWith(" ") || parserTrimCellWhiteSpace)) markdown.append(' ');

            String[] childText = new String[] { "" };

            context.translatingSpan((context1, writer) -> {
                context1.renderChildren(node);
                childText[0] = writer.toString(-1);
            });

            if (!myTable.isSeparator() && options.spaceAroundPipes && (!childText[0].endsWith(" ") || parserTrimCellWhiteSpace)) markdown.append(' ');
            if (node.getNext() == null) {
                if (options.leadTrailPipes && node.getClosingMarker().isEmpty()) markdown.append('|');
                else markdown.append(node.getClosingMarker());
            } else {
                markdown.append(node.getClosingMarker());
            }
        }
    }

    private void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
        //if (TABLE_HEADER_SEPARATOR.matcher(node.getChars()).matches()) {
        if (myTable != null && myTable.isSeparator()) {
            Node parent = node.getAncestorOfType(Paragraph.class);
            if (parent instanceof Paragraph && ((Paragraph) parent).hasTableSeparator()) {
                markdown.pushPrefix().addPrefix(" ").append(node.getChars()).popPrefix();
            } else {
                markdown.append(node.getChars());
            }
        } else {
            markdown.append(node.getChars());
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new TableNodeFormatter(options);
        }
    }
}
