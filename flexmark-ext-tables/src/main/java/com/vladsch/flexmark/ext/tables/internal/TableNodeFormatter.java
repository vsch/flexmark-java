package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.format.TrackedOffsetList;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.vladsch.flexmark.formatter.RenderPurpose.FORMAT;
import static com.vladsch.flexmark.util.format.TableManipulator.NULL;

public class TableNodeFormatter implements NodeFormatter {
    final private TableFormatOptions options;
    final private boolean parserTrimCellWhiteSpace;

    private MarkdownTable myTable;

    public TableNodeFormatter(DataHolder options) {
        this.options = new TableFormatOptions(options);
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

                TrackedOffsetList trackedOffsets = context.getTrackedOffsets();
                List<TrackedOffset> tableTrackedOffsets = trackedOffsets.getTrackedOffsets(node.getStartOffset(), node.getEndOffset());

                if (!trackedOffsets.isEmpty()) {
                    for (TrackedOffset trackedOffset : tableTrackedOffsets) {
                        assert (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset());
                        myTable.addTrackedOffset(trackedOffset);
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
                    myTable.setFormatTableIndentPrefix(prefix);
                    MarkdownWriter formattedTable = new MarkdownWriter(markdown.getOptions());
                    myTable.appendTable(formattedTable);

                    List<TrackedOffset> tableOffsets = myTable.getTrackedOffsets();
                    int startOffset = markdown.offsetWithPending();
                    if (!tableTrackedOffsets.isEmpty()) {
                        assert tableTrackedOffsets.size() == tableOffsets.size();

                        // get the indent used for new lines so that index can be adjusted by added indent
                        for (TrackedOffset trackedOffset : tableTrackedOffsets) {
                            assert (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset());

                            if (trackedOffset.isResolved()) {
                                trackedOffset.setIndex(trackedOffset.getIndex() + startOffset);
                            }
                        }
                    }

                    markdown.pushPrefix().setPrefix("", false)
                            .pushOptions()
                            .removeOptions(LineAppendable.F_WHITESPACE_REMOVAL)
                            .append(formattedTable)
                            .popOptions()
                            .popPrefix(false)
                    ;

                    markdown.tailBlankLine();

                    if (myTable.getMaxColumns() > 0 && !tableTrackedOffsets.isEmpty()) {
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
                childText[0] = writer.toString(-1, -1);
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
