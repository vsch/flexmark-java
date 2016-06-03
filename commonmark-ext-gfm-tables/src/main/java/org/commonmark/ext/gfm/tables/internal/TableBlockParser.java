package org.commonmark.ext.gfm.tables.internal;

import org.commonmark.ext.gfm.tables.*;
import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;
import org.commonmark.node.Block;
import org.commonmark.node.Node;
import org.commonmark.parser.InlineParser;
import org.commonmark.parser.block.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TableBlockParser extends AbstractBlockParser {

    private static String COL = "\\s*:?-{3,}:?\\s*";
    private static Pattern TABLE_HEADER_SEPARATOR = Pattern.compile(
            // For single column, require at least one pipe, otherwise it's ambiguous with setext headers
            "\\|" + COL + "\\|?\\s*" + "|" +
            COL + "\\|\\s*" + "|" +
            "\\|?" + "(?:" + COL + "\\|)+" + COL + "\\|?\\s*");

    private final TableBlock block = new TableBlock();
    private final List<BasedSequence> rowLines = new ArrayList<>();

    private boolean nextIsSeparatorLine = true;
    private BasedSequence separatorLine = SubSequence.EMPTY;

    private TableBlockParser(BasedSequence headerLine) {
        rowLines.add(headerLine);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (state.getLine().toString().contains("|")) {
            return BlockContinue.atIndex(state.getIndex());
        } else {
            return BlockContinue.none();
        }
    }

    @Override
    public void addLine(BasedSequence line, int eolLength) {
        if (nextIsSeparatorLine) {
            nextIsSeparatorLine = false;
            separatorLine = line;
        } else {
            rowLines.add(line);
        }
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        Node section = new TableHead();
        block.appendChild(section);

        List<TableCell.Alignment> alignments = parseAlignment(separatorLine);

        int headerColumns = -1;
        boolean header = true;
        for (BasedSequence rowLine : rowLines) {
            List<BasedSequence> cells = split(rowLine);
            TableRow tableRow = new TableRow();

            if (headerColumns == -1) {
                headerColumns = cells.size();
            }

            // Body can not have more columns than head
            for (int i = 0; i < headerColumns; i++) {
                BasedSequence cell = i < cells.size() ? cells.get(i) : SubSequence.EMPTY;
                TableCell.Alignment alignment = i < alignments.size() ? alignments.get(i) : null;
                TableCell tableCell = new TableCell();
                tableCell.setHeader(header);
                tableCell.setAlignment(alignment);
                inlineParser.parse(cell.trim(), tableCell);
                tableRow.appendChild(tableCell);
            }

            section.appendChild(tableRow);

            if (header) {
                // Format allows only one row in head
                header = false;
                section = new TableBody();
                block.appendChild(section);
            }
        }
    }

    private static List<TableCell.Alignment> parseAlignment(BasedSequence separatorLine) {
        List<BasedSequence> parts = split(separatorLine);
        List<TableCell.Alignment> alignments = new ArrayList<>();
        for (BasedSequence part : parts) {
            BasedSequence trimmed = part.trim();
            boolean left = trimmed.startsWith(":");
            boolean right = trimmed.endsWith(":");
            TableCell.Alignment alignment = getAlignment(left, right);
            alignments.add(alignment);
        }
        return alignments;
    }

    private static List<BasedSequence> split(BasedSequence input) {
        BasedSequence line = input.trim();
        int lineLength = line.length();
        if (line.startsWith("|")) {
            line = line.subSequence(1, lineLength);
        }
        List<BasedSequence> cells = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        int lastPos = 0;
        for (int i = 0; i < lineLength; i++) {
            char c = line.charAt(i);
            if (escape) {
                escape = false;
                sb.append(c);
            } else {
                switch (c) {
                    case '\\':
                        escape = true;
                        // Removing the escaping '\' is handled by the inline parser later, so add it to cell
                        sb.append(c);
                        break;
                    case '|':
                        cells.add(input.subSequence(lastPos, i));
                        lastPos = i+1;
                        sb.setLength(0);
                        break;
                    default:
                        sb.append(c);
                }
            }
        }
        if (sb.length() > 0) {
            cells.add(input.subSequence(lastPos, lineLength));
        }
        return cells;
    }

    private static TableCell.Alignment getAlignment(boolean left, boolean right) {
        if (left && right) {
            return TableCell.Alignment.CENTER;
        } else if (left) {
            return TableCell.Alignment.LEFT;
        } else if (right) {
            return TableCell.Alignment.RIGHT;
        } else {
            return null;
        }
    }

    public static class Factory extends AbstractBlockParserFactory {

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            BasedSequence paragraph = matchedBlockParser.getParagraphContent();
            if (paragraph != null && paragraph.toString().contains("|") && !paragraph.toString().contains("\n")) {
                BasedSequence separatorLine = line.subSequence(state.getIndex(), line.length());
                if (TABLE_HEADER_SEPARATOR.matcher(separatorLine).matches()) {
                    List<BasedSequence> headParts = split(paragraph);
                    List<BasedSequence> separatorParts = split(separatorLine);
                    if (separatorParts.size() >= headParts.size()) {
                        return BlockStart.of(new TableBlockParser(paragraph))
                                .atIndex(state.getIndex())
                                .replaceActiveBlockParser();
                    }
                }
            }
            return BlockStart.none();
        }
    }

}
