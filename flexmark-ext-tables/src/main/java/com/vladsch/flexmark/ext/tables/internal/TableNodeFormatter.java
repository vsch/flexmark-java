package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;

import java.util.*;

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
        isIntellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(options);
        intellijDummyIdentifier = isIntellijDummyIdentifier ? Parsing.INTELLIJ_DUMMY_IDENTIFIER : "";
        parserTrimCellWhiteSpace = TablesExtension.TRIM_CELL_WHITESPACE.getFrom(options);
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<? extends Node>>(Arrays.asList(
                new NodeFormattingHandler<TableBlock>(TableBlock.class, new CustomNodeFormatter<TableBlock>() {
                    @Override
                    public void render(TableBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<TableHead>(TableHead.class, new CustomNodeFormatter<TableHead>() {
                    @Override
                    public void render(TableHead node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<TableSeparator>(TableSeparator.class, new CustomNodeFormatter<TableSeparator>() {
                    @Override
                    public void render(TableSeparator node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<TableBody>(TableBody.class, new CustomNodeFormatter<TableBody>() {
                    @Override
                    public void render(TableBody node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<TableRow>(TableRow.class, new CustomNodeFormatter<TableRow>() {
                    @Override
                    public void render(TableRow node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<TableCell>(TableCell.class, new CustomNodeFormatter<TableCell>() {
                    @Override
                    public void render(TableCell node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<TableCaption>(TableCaption.class, new CustomNodeFormatter<TableCaption>() {
                    @Override
                    public void render(TableCaption node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Text>(Text.class, new CustomNodeFormatter<Text>() {
                    @Override
                    public void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    private void render(final TableBlock node, final NodeFormatterContext context, MarkdownWriter markdown) {
        myTable = new MarkdownTable(options);

        switch (context.getRenderPurpose()) {
            case TRANSLATION_SPANS:
            case TRANSLATED_SPANS:
            case TRANSLATED:
                markdown.blankLine();
                context.renderChildren(node);
                markdown.blankLine();
                break;

            case FORMAT:
            default:
                context.renderChildren(node);

                // allow table manipulation, mostly for testing
                if (options.tableManipulator != NULL) {
                    myTable.normalize();
                    options.tableManipulator.apply(myTable, node);
                }

                if (myTable.getMaxColumns() > 0) {
                    // output table
                    markdown.blankLine();
                    myTable.appendTable(markdown);
                    markdown.blankLine();
                    if (options.dumpIntellijOffsets) {
                        Map<Integer, Integer> offsets = myTable.getTrackedOffsets();
                        if (offsets.size() > 0) {
                            markdown.append("\nTracked Offsets").line();  // simulate flex example ast dump
                            String sep = "  ";
                            int i = 0;
                            for (Map.Entry<Integer, Integer> offset : offsets.entrySet()) {
                                i++;
                                markdown.append(sep).append(String.format(Locale.US, "%d:[%d,%d] was:[%d,%d]", i, offset.getValue(), offset.getValue() + 1, offset.getKey(), offset.getKey() + 1));
                                sep = " ";
                            }
                            markdown.append("\n");
                        }
                    }
                }
        }

        myTable = null;
    }

    private void render(final TableHead node, final NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(false);
        myTable.setHeader(true);
        context.renderChildren(node);
    }

    private void render(TableSeparator node, NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(true);
        context.renderChildren(node);
    }

    private void render(final TableBody node, final NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(false);
        myTable.setHeader(false);
        context.renderChildren(node);
    }

    private void render(final TableRow node, final NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
        if (context.getRenderPurpose() == FORMAT) {
            if (!myTable.isSeparator()) myTable.nextRow();
        } else {
            markdown.line();
        }
    }

    private void render(final TableCaption node, final NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getRenderPurpose() == FORMAT) {
            myTable.setCaptionWithMarkers(node.getOpeningMarker(), node.getText(), node.getClosingMarker());
        } else {
            // KLUDGE: to reuse the table formatting logic of MarkdownTable
            String dummyCaption = node.hasChildren() ? "dummy" : "";
            String formattedCaption = MarkdownTable.formattedCaption(BasedSequenceImpl.of(dummyCaption), options);

            if (formattedCaption != null) {
                markdown.line().append(node.getOpeningMarker());
                context.renderChildren(node);
                markdown.append(node.getClosingMarker()).line();
            }
        }
    }

    private void render(final TableCell node, final NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getRenderPurpose() == FORMAT) {
            BasedSequence text = node.getText();
            if (options.trimCellWhitespace) {
                if (text.isBlank() && !text.isEmpty()) {
                    text = text.subSequence(0, 1);
                } else {
                    text = text.trim();
                }
            }
            myTable.addCell(new com.vladsch.flexmark.util.format.TableCell(node.getOpeningMarker(), text, node.getClosingMarker(), 1, node.getSpan(), node.getAlignment() == null ? CellAlignment.NONE : node.getAlignment().cellAlignment()));
        } else {
            if (node.getPrevious() == null) {
                if (options.leadTrailPipes && node.getOpeningMarker().isEmpty()) markdown.append('|');
                else markdown.append(node.getOpeningMarker());
            } else {
                markdown.append(node.getOpeningMarker());
            }

            if (!myTable.isSeparator() && options.spaceAroundPipes && (!node.getText().startsWith(" ") || parserTrimCellWhiteSpace)) markdown.append(' ');

            final String[] childText = new String[] { "" };

            context.translatingSpan(new TranslatingSpanRender() {
                @Override
                public void render(final NodeFormatterContext context, final MarkdownWriter writer) {
                    context.renderChildren(node);
                    childText[0] = writer.getText();
                }
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
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new TableNodeFormatter(options);
        }
    }
}
