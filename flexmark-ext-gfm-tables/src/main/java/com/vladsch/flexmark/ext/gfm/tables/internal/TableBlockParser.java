package com.vladsch.flexmark.ext.gfm.tables.internal;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.ext.gfm.tables.*;
import com.vladsch.flexmark.ext.gfm.tables.TableCell.Alignment;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class TableBlockParser extends AbstractBlockParser {
    private static final String COL = "\\s*:?-{1,}:?\\s*";
    static final Pattern TABLE_HEADER_SEPARATOR = Pattern.compile(
            // For single column, require at least one pipe, otherwise it's ambiguous with setext headers
            "\\|" + COL + "\\|?\\s*" + "|" +
                    COL + "\\|\\s*" + "|" +
                    "\\|?" + "(?:" + COL + "\\|)+" + COL + "\\|?\\s*");

    private final TableBlock block = new TableBlock();
    private BlockContent content = new BlockContent();

    boolean nextIsSeparatorLine = false;
    private BasedSequence separatorLine = BasedSequence.NULL;
    private int separatorLineNumber = 0;

    public static boolean bodyColumnsFilledToHead = true;
    public static boolean bodyColumnsTruncatedToHead = true;
    public static int maxHeaderRows = 1;

    TableBlockParser() {
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
    public void addLine(ParserState state, BasedSequence line) {
        if (nextIsSeparatorLine) {
            nextIsSeparatorLine = false;
            separatorLine = line;
            separatorLineNumber = content.getLineCount();
        }
        content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setContent(content);
        content = null;
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        Node section = new TableHead();
        block.appendChild(section);

        List<Alignment> alignments = parseAlignment(separatorLine);

        int rowNumber = 0;
        int separatorColumns = alignments.size();

        for (BasedSequence rowLine : block.getContentLines()) {
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
            TableRow tableRow = new TableRow(rowLine.trimEOL());

            int rowCells = countCells(cells);
            int maxColumns = rowCells;

            if (bodyColumnsTruncatedToHead && maxColumns > separatorColumns) maxColumns = separatorColumns;
            if (rowNumber >= separatorLineNumber) {
                if (!bodyColumnsFilledToHead && rowCells < maxColumns) maxColumns = rowCells;
                else if (bodyColumnsFilledToHead && maxColumns < separatorColumns) maxColumns = separatorColumns;
            }

            int segmentOffset = 0;
            BasedSequence openingMarker = BasedSequence.NULL;

            for (int i = 0; i < maxColumns; i++) {
                BasedSequence cell = i < rowCells ? cells.get(i + segmentOffset) : BasedSequence.NULL;
                if (!isCell(cell)) {
                    openingMarker = cell;
                    segmentOffset++;
                    cell = i < rowCells ? cells.get(i + segmentOffset) : BasedSequence.NULL;
                }

                Alignment alignment = i < alignments.size() ? alignments.get(i) : null;
                TableCell tableCell = new TableCell();
                tableCell.setHeader(rowNumber < separatorLineNumber);
                tableCell.setAlignment(alignment);
                tableCell.setOpeningMarker(openingMarker);
                openingMarker = BasedSequence.NULL;

                // if the next one is not a cell then it is our closing marker
                if (i + segmentOffset + 1 < cells.size()) {
                    BasedSequence closingMarker = cells.get(i + segmentOffset + 1);
                    if (!isCell(closingMarker)) {
                        segmentOffset++;
                        tableCell.setClosingMarker(closingMarker);
                    }
                }
                BasedSequence trimmed = cell.trim();
                tableCell.setText(trimmed);
                inlineParser.parse(trimmed, tableCell);
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

    private static int countCells(List<BasedSequence> segments) {
        int cells = 0;
        for (BasedSequence segment : segments) {
            if (isCell(segment)) cells++;
        }

        return cells;
    }

    private static boolean isCell(BasedSequence segment) {
        return segment.length() != 1 || segment.charAt(0) != '|';
    }

    private static List<Alignment> parseAlignment(BasedSequence separatorLine) {
        List<BasedSequence> parts = split(separatorLine);
        List<Alignment> alignments = new ArrayList<Alignment>();
        for (BasedSequence part : parts) {
            BasedSequence trimmed = part.trim();
            boolean left = trimmed.startsWith(":");
            boolean right = trimmed.endsWith(":");
            Alignment alignment = getAlignment(left, right);
            alignments.add(alignment);
        }
        return alignments;
    }

    static List<BasedSequence> split(BasedSequence input) {
        BasedSequence line = input.trim();
        int lineLength = line.length();
        List<BasedSequence> segments = new ArrayList<BasedSequence>();

        if (line.startsWith("|")) {
            //segments.add(line.subSequence(0, 1));
            line = line.subSequence(1, lineLength);
            lineLength--;
        }

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
                        segments.add(line.subSequence(lastPos, i));
                        //segments.add(line.subSequence(i, i + 1));
                        lastPos = i + 1;
                        cellChars = 0;
                        break;
                    default:
                        cellChars++;
                }
            }
        }

        if (cellChars > 0) {
            segments.add(line.subSequence(lastPos, lineLength));
        }
        return segments;
    }

    private static Alignment getAlignment(boolean left, boolean right) {
        if (left && right) {
            return Alignment.CENTER;
        } else if (left) {
            return Alignment.LEFT;
        } else if (right) {
            return Alignment.RIGHT;
        } else {
            return null;
        }
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        BlockFactory(DataHolder options) {
            super(options);
        }

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
                        TableBlockParser tableBlockParser = new TableBlockParser();
                        tableBlockParser.addLine(state, paragraph);
                        tableBlockParser.nextIsSeparatorLine = true;

                        return BlockStart.of(tableBlockParser)
                                .atIndex(state.getIndex())
                                .replaceActiveBlockParser();
                    }
                }
            }
            return BlockStart.none();
        }
    }
}
