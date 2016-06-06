package com.vladsch.flexmark.ext.gfm.tables.internal;

import com.vladsch.flexmark.ext.gfm.tables.*;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

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
    private int separatorLineNumber = 0;

    public static boolean bodyColumnsFilledToHead = true;
    public static boolean bodyColumnsTruncatedToHead = true;
    public static int maxHeaderRows = 1;

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
            separatorLineNumber = rowLines.size();
            rowLines.add(line);
        } else {
            rowLines.add(line);
        }
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setCharsFromContent();
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        Node section = new TableHead();
        block.appendChild(section);

        List<TableCell.Alignment> alignments = parseAlignment(separatorLine);

        int rowNumber = 0;
        int headerColumns = -1;
        for (BasedSequence rowLine : rowLines) {
            if (rowNumber == separatorLineNumber) {
                section.setCharsFromContent();
                section = new TableSeparator();
                block.appendChild(section);
            } else if (rowNumber == separatorLineNumber + 1) {
                section.setCharsFromContent();
                section = new TableBody();
                block.appendChild(section);
            }

            List<BasedSequence> cells = split(rowLine);
            TableRow tableRow = new TableRow();

            int rowCells = cells.size();
            int maxColumns = rowCells;

            if (rowNumber < separatorLineNumber || headerColumns == -1) {
                if (headerColumns == -1) {
                    headerColumns = rowCells;
                } else if (headerColumns < rowCells) {
                    headerColumns = rowCells;
                }
            }

            if (bodyColumnsTruncatedToHead && maxColumns > headerColumns) maxColumns = headerColumns;
            if (rowNumber >= separatorLineNumber) {
                if (!bodyColumnsFilledToHead && rowCells < maxColumns) maxColumns = rowCells;
                else if (bodyColumnsFilledToHead && maxColumns < headerColumns) maxColumns = headerColumns;
            }

            for (int i = 0; i < maxColumns; i++) {
                BasedSequence cell = i < rowCells ? cells.get(i) : SubSequence.EMPTY;
                TableCell.Alignment alignment = i < alignments.size() ? alignments.get(i) : null;
                TableCell tableCell = new TableCell();
                tableCell.setHeader(rowNumber < separatorLineNumber);
                tableCell.setAlignment(alignment);
                inlineParser.parse(cell.trim(), tableCell);
                tableCell.setCharsFromContent();
                tableRow.appendChild(tableCell);
            }

            tableRow.setCharsFromContent();
            section.appendChild(tableRow);

            rowNumber++;
        }

        if (section instanceof TableSeparator) {
            block.appendChild(new TableBody());
        }

        section.setCharsFromContent();
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
            lineLength--;
        }

        List<BasedSequence> cells = new ArrayList<>();
        boolean escape = false;
        int lastPos = 0;
        int cellChars = 0;
        for (int i = 0; i < lineLength; i++) {
            char c = line.charAt(i);
            if (escape) {
                escape = false;
                cellChars++;
            } else {
                switch (c) {
                    case '\\':
                        escape = true;
                        // Removing the escaping '\' is handled by the inline parser later, so add it to cell
                        cellChars++;
                        break;
                    case '|':
                        cells.add(line.subSequence(lastPos, i));
                        lastPos = i + 1;
                        cellChars = 0;
                        break;
                    default:
                        cellChars++;
                }
            }
        }

        if (cellChars > 0) {
            cells.add(line.subSequence(lastPos, lineLength));
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
            List<BasedSequence> paragraphLines = matchedBlockParser.getParagraphLines();
            if (paragraphLines != null && paragraphLines.size() >= 1 && paragraphLines.size() <= maxHeaderRows && paragraphLines.get(0).toString().contains("|")) {
                BasedSequence separatorLine = line.subSequence(state.getIndex(), line.length());
                if (TABLE_HEADER_SEPARATOR.matcher(separatorLine).matches()) {
                    BasedSequence paragraph = paragraphLines.get(0);
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
