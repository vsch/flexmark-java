package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.util.ClassifyingBlockTracker;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.*;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.collection.ItemFactoryMap;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.dependency.DependencyResolver;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static com.vladsch.flexmark.parser.Parser.BLANK_LINES_IN_AST;
import static com.vladsch.flexmark.parser.Parser.TRACK_DOCUMENT_LINES;

public class DocumentParser implements ParserState {

    final public static InlineParserFactory INLINE_PARSER_FACTORY = CommonmarkInlineParser::new;

    final private static HashMap<CustomBlockParserFactory, DataKey<Boolean>> CORE_FACTORIES_DATA_KEYS = new HashMap<>();
    static {
        CORE_FACTORIES_DATA_KEYS.put(new BlockQuoteParser.Factory(), Parser.BLOCK_QUOTE_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new HeadingParser.Factory(), Parser.HEADING_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new FencedCodeBlockParser.Factory(), Parser.FENCED_CODE_BLOCK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new HtmlBlockParser.Factory(), Parser.HTML_BLOCK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new ThematicBreakParser.Factory(), Parser.THEMATIC_BREAK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new ListBlockParser.Factory(), Parser.LIST_BLOCK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new IndentedCodeBlockParser.Factory(), Parser.INDENTED_CODE_BLOCK_PARSER);
    }

    //private static List<CustomBlockParserFactory> CORE_FACTORIES = new ArrayList<>();
    //static {
    //    CORE_FACTORIES.add(new BlockQuoteParser.Factory());
    //    CORE_FACTORIES.add(new HeadingParser.Factory());
    //    CORE_FACTORIES.add(new FencedCodeBlockParser.Factory());
    //    CORE_FACTORIES.add(new HtmlBlockParser.Factory());
    //    CORE_FACTORIES.add(new ThematicBreakParser.Factory());
    //    CORE_FACTORIES.add(new ListBlockParser.Factory());
    //    CORE_FACTORIES.add(new IndentedCodeBlockParser.Factory());
    //}

    final private static HashMap<DataKey<Boolean>, ParagraphPreProcessorFactory> CORE_PARAGRAPH_PRE_PROCESSORS = new HashMap<>();
    static {
        CORE_PARAGRAPH_PRE_PROCESSORS.put(Parser.REFERENCE_PARAGRAPH_PRE_PROCESSOR, new ReferencePreProcessorFactory());
    }

//    final private static HashMap<DataKey<Boolean>, BlockPreProcessorFactory> CORE_BLOCK_PRE_PROCESSORS = new HashMap<>();
//    static {
//        //CORE_BLOCK_PRE_PROCESSORS.put(Parser.REFERENCE_PARAGRAPH_PRE_PROCESSOR, new ReferencePreProcessorFactory());
//    }

    private BasedSequence line;
    private BasedSequence lineWithEOL;

    /**
     * current line number in the input
     */
    private int lineNumber = 0;

    /**
     * current start of line offset in the input
     */
    private int lineStart = 0;

    /**
     * current lines EOL sequence
     */
    private int lineEOLIndex = 0;

    /**
     * current end of line offset in the input including EOL
     */
    private int lineEndIndex = 0;

    /**
     * current index (offset) in input line (0-based)
     */
    private int index = 0;

    /**
     * current column of input line (tab causes column to go to next 4-space tab stop) (0-based)
     */
    private int column = 0;

    /**
     * if the current column is within a tab character (partially consumed tab)
     */
    private boolean columnIsInTab;

    private int nextNonSpace = 0;
    private int nextNonSpaceColumn = 0;
    private int indent = 0;
    private boolean blank;
    private boolean isBlankLine;

    final private List<BlockParserFactory> blockParserFactories;
    final private List<List<ParagraphPreProcessorFactory>> paragraphPreProcessorDependencies;
    final private List<List<BlockPreProcessorFactory>> blockPreProcessorDependencies;
    final private InlineParser inlineParser;
    final private DocumentBlockParser documentBlockParser;
    final private boolean blankLinesInAst;
    final private boolean trackDocumentLines;
    final private List<BasedSequence> lineSegments = new ArrayList<>();
    final private List<BlockParser> activeBlockParsers = new ArrayList<>();
    final private ClassifyingBlockTracker blockTracker = new ClassifyingBlockTracker();

    @Override
    public List<BasedSequence> getLineSegments() {
        return lineSegments;
    }

    public void blockParserAdded(BlockParser blockParser) {
        blockTracker.blockParserAdded(blockParser);
    }

    public void blockParserRemoved(BlockParser blockParser) {
        blockTracker.blockParserRemoved(blockParser);
    }

    public void blockAdded(@NotNull Block node) {
        blockTracker.blockAdded(node);
    }

    public void blockAddedWithChildren(@NotNull Block node) {
        blockTracker.blockAddedWithChildren(node);
    }

    public void blockAddedWithDescendants(@NotNull Block node) {
        blockTracker.blockAddedWithDescendants(node);
    }

    public void blockRemoved(@NotNull Block node) {
        blockTracker.blockRemoved(node);
    }

    public void blockRemovedWithChildren(@NotNull Block node) {
        blockTracker.blockRemovedWithChildren(node);
    }

    public void blockRemovedWithDescendants(@NotNull Block node) {
        blockTracker.blockRemovedWithDescendants(node);
    }

    final private Map<Node, Boolean> lastLineBlank = new HashMap<>();
    final private DataHolder options;
    private ParserPhase currentPhase;

    @Override
    public ParserPhase getParserPhase() {
        return currentPhase;
    }

    final private Parsing myParsing;

    public DocumentParser(
            DataHolder options,
            List<CustomBlockParserFactory> customBlockParserFactories,
            List<List<ParagraphPreProcessorFactory>> paragraphPreProcessorDependencies,
            List<List<BlockPreProcessorFactory>> blockPreProcessorDependencies,
            InlineParser inlineParser
    ) {
        this.options = options;
        this.myParsing = inlineParser.getParsing();

        ArrayList<BlockParserFactory> blockParserFactories = new ArrayList<>(customBlockParserFactories.size());
        for (CustomBlockParserFactory factory : customBlockParserFactories) {
            blockParserFactories.add(factory.apply(options));
        }

        this.blockParserFactories = blockParserFactories;
        this.paragraphPreProcessorDependencies = paragraphPreProcessorDependencies;
        this.blockPreProcessorDependencies = blockPreProcessorDependencies;
        this.inlineParser = inlineParser;

        this.documentBlockParser = new DocumentBlockParser();
        activateBlockParser(this.documentBlockParser);
        this.currentPhase = ParserPhase.STARTING;
        this.blankLinesInAst = BLANK_LINES_IN_AST.get(options);
        this.trackDocumentLines = TRACK_DOCUMENT_LINES.get(options);
    }

    @Override
    public Parsing getParsing() {
        return myParsing;
    }

    @Override
    public MutableDataHolder getProperties() {
        return documentBlockParser.getBlock();
    }

    public static List<CustomBlockParserFactory> calculateBlockParserFactories(DataHolder options, List<CustomBlockParserFactory> customBlockParserFactories) {
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        List<CustomBlockParserFactory> list = new ArrayList<>(customBlockParserFactories);

        // need to keep core parsers in the right order, this is done through their dependencies
        for (Map.Entry<CustomBlockParserFactory, DataKey<Boolean>> entry : CORE_FACTORIES_DATA_KEYS.entrySet()) {
            if (entry.getValue().get(options)) {
                list.add(entry.getKey());
            }
        }

        return DependencyResolver.resolveFlatDependencies(list, null, null);
    }

    public static List<List<ParagraphPreProcessorFactory>> calculateParagraphPreProcessors(
            DataHolder options,
            List<ParagraphPreProcessorFactory> blockPreProcessors,
            InlineParserFactory inlineParserFactory
    ) {
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        List<ParagraphPreProcessorFactory> list = new ArrayList<>(blockPreProcessors);

        if (inlineParserFactory == INLINE_PARSER_FACTORY) {
            //list.addAll(CORE_PARAGRAPH_PRE_PROCESSORS.keySet().stream().filter(options::get).map(key -> CORE_PARAGRAPH_PRE_PROCESSORS.get(key)).collect(Collectors.toList()));
            for (DataKey<Boolean> preProcessorDataKey : CORE_PARAGRAPH_PRE_PROCESSORS.keySet()) {
                if (preProcessorDataKey.get(options)) {
                    ParagraphPreProcessorFactory preProcessorFactory = CORE_PARAGRAPH_PRE_PROCESSORS.get(preProcessorDataKey);
                    list.add(preProcessorFactory);
                }
            }
        }

        return DependencyResolver.resolveDependencies(list, null, null);
    }

    public static List<List<BlockPreProcessorFactory>> calculateBlockPreProcessors(
            DataHolder options,
            List<BlockPreProcessorFactory> blockPreProcessors
    ) {
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
//        List<BlockPreProcessorFactory> list = new ArrayList<>(blockPreProcessors);
//
//        // add core block preprocessors
//        for (DataKey<Boolean> preProcessorDataKey : CORE_BLOCK_PRE_PROCESSORS.keySet()) {
//            if (preProcessorDataKey.get(options)) {
//                BlockPreProcessorFactory preProcessorFactory = CORE_BLOCK_PRE_PROCESSORS.get(preProcessorDataKey);
//                list.add(preProcessorFactory);
//            }
//        }
//
//        return DependencyResolver.resolveDependencies(list, null, null);
        return DependencyResolver.resolveDependencies(blockPreProcessors, null, null);
    }

    @Override
    public InlineParser getInlineParser() {
        return inlineParser;
    }

    /**
     * The main parsing function. Returns a parsed document AST.
     *
     * @param source source sequence to parse
     * @return Document node of the resulting AST
     */
    public Document parse(CharSequence source) {
        BasedSequence input = BasedSequence.of(source);
        int lineStart = 0;
        int lineBreak;
        int lineEOL;
        int lineEnd;
        lineNumber = 0;

        documentBlockParser.initializeDocument(options, input);
        inlineParser.initializeDocument(documentBlockParser.getBlock());

        currentPhase = ParserPhase.PARSE_BLOCKS;

        while ((lineBreak = Parsing.findLineBreak(input, lineStart)) != -1) {
            BasedSequence line = input.subSequence(lineStart, lineBreak);
            lineEOL = lineBreak;
            if (lineBreak + 1 < input.length() && input.charAt(lineBreak) == '\r' && input.charAt(lineBreak + 1) == '\n') {
                lineEnd = lineBreak + 2;
            } else {
                lineEnd = lineBreak + 1;
            }

            this.lineWithEOL = input.subSequence(lineStart, lineEnd);
            this.lineStart = lineStart;
            this.lineEOLIndex = lineEOL;
            this.lineEndIndex = lineEnd;
            incorporateLine(line);
            lineNumber++;
            lineStart = lineEnd;
        }

        if (input.length() > 0 && (lineStart == 0 || lineStart < input.length())) {
            this.lineWithEOL = input.subSequence(lineStart, input.length());
            this.lineStart = lineStart;
            this.lineEOLIndex = input.length();
            this.lineEndIndex = this.lineEOLIndex;
            incorporateLine(lineWithEOL);
            lineNumber++;
        }

        return finalizeAndProcess();
    }

    public Document parse(Reader input) throws IOException {
        BufferedReader bufferedReader;
        if (input instanceof BufferedReader) {
            bufferedReader = (BufferedReader) input;
        } else {
            bufferedReader = new BufferedReader(input);
        }

        StringBuilder file = new StringBuilder();
        char[] buffer = new char[16384];

        while (true) {
            int charsRead = bufferedReader.read(buffer);
            // issue #91
            if (charsRead < 0) break;
            file.append(buffer, 0, charsRead);
        }

        CharSequence source = BasedSequence.of(file.toString());
        return parse(source);
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public int getLineStart() {
        return lineStart;
    }

    public int getLineEndIndex() {
        return lineEndIndex;
    }

    @Override
    public BasedSequence getLine() {
        return line;
    }

    @Override
    public BasedSequence getLineWithEOL() {
        return lineWithEOL;
    }

    @Override
    public int getLineEolLength() {
        return lineEndIndex - lineEOLIndex;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getNextNonSpaceIndex() {
        return nextNonSpace;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getIndent() {
        return indent;
    }

    @Override
    public boolean isBlank() {
        return blank;
    }

    @Override
    public boolean isBlankLine() {
        return isBlankLine;
    }

    @Override
    public BlockParser getActiveBlockParser() {
        return activeBlockParsers.get(activeBlockParsers.size() - 1);
    }

    @Override
    public BlockParser getActiveBlockParser(Block node) {
        BlockParser blockParser = blockTracker.getKey(node);
        return blockParser == null || blockParser.isClosed() ? null : blockParser;
    }

    @Override
    public List<BlockParser> getActiveBlockParsers() {
        return activeBlockParsers;
    }

    /**
     * Analyze a line of text and update the document appropriately. We parse markdown text by calling this on each
     * line of input, then finalizing the document.
     *
     * @param ln sequence of the current line
     */
    private void incorporateLine(BasedSequence ln) {
        line = ln;
        index = 0;
        column = 0;
        columnIsInTab = false;

        // track lines of document
        if (trackDocumentLines) lineSegments.add(lineWithEOL);

        // For each containing block, try to parse the associated line start.
        // Bail out on failure: container will point to the last matching block.
        // Set all_matched to false if not all containers match.
        // The document will always match, can be skipped
        int matches = 1;
        BlankLine blankLine = null;

        findNextNonSpace();

        if (blank) {
            if (blankLinesInAst) {
                // line was blank
                blankLine = new BlankLine(lineWithEOL);
                documentBlockParser.getBlock().appendChild(blankLine);
            }
        }

        for (BlockParser blockParser : activeBlockParsers.subList(1, activeBlockParsers.size())) {
            boolean wasBlank = blank;

            findNextNonSpace();

            if (blank) {
                if (blankLinesInAst) {
                    if (blankLine == null) {
                        // line became blank
                        blankLine = new BlankLine(lineWithEOL);
                        documentBlockParser.getBlock().appendChild(blankLine);
                    }

                    if (!wasBlank && blockParser.getBlock() instanceof BlankLineContainer) {
                        blankLine.setClaimedBlankLine(blockParser.getBlock());
                    }
                }
            }

            // HACK: may work to detect when a real blank line follows a paragraph
            isBlankLine = wasBlank;

            BlockContinue result = blockParser.tryContinue(this);
            if (result instanceof BlockContinueImpl) {
                BlockContinueImpl blockContinue = (BlockContinueImpl) result;
                if (blockContinue.isFinalize()) {
                    finalize(blockParser);
                    return;
                } else {
                    if (blockContinue.getNewIndex() != -1) {
                        setNewIndex(blockContinue.getNewIndex());
                        if (!blank && blockParser.getBlock() instanceof BlankLineContainer) {
                            findNextNonSpace();
                            if (blank) {
                                blankLine = new BlankLine(lineWithEOL, blockParser.getBlock());
                                blockParser.getBlock().appendChild(blankLine);
                            }
                        }
                    } else if (blockContinue.getNewColumn() != -1) {
                        setNewColumn(blockContinue.getNewColumn());
                        if (!blank && blockParser.getBlock() instanceof BlankLineContainer) {
                            findNextNonSpace();
                            if (blank) {
                                blankLine = new BlankLine(lineWithEOL, blockParser.getBlock());
                                blockParser.getBlock().appendChild(blankLine);
                            }
                        }
                    }
                    matches++;

                    if (blankLine != null && (blankLinesInAst || blankLine.getClaimedBlankLine() == blockParser.getBlock())) {
                        if (blockParser.getBlock() instanceof BlankLineContainer) {
                            blockParser.getBlock().appendChild(blankLine);
                        }
                    }
                }
            } else {
                break;
            }
        }

        List<BlockParser> unmatchedBlockParsers = new ArrayList<>(activeBlockParsers.subList(matches, activeBlockParsers.size()));
        BlockParser lastMatchedBlockParser = activeBlockParsers.get(matches - 1);
        BlockParser blockParser = lastMatchedBlockParser;
        boolean allClosed = unmatchedBlockParsers.isEmpty();

        // Check to see if we've hit 2nd blank line; if so break out of list or any other block type that handles this
        if (blank && isLastLineBlank(blockParser.getBlock())) {
            List<BlockParser> matchedBlockParsers = new ArrayList<>(activeBlockParsers.subList(0, matches));
            breakOutOfLists(matchedBlockParsers);
        }

        // Unless last matched container is a code block, try new container starts,
        // adding children to the last matched container:
        boolean tryBlockStarts = blockParser.isInterruptible() || blockParser.isContainer();
        BlockParser lastPrefixClaimer = null;

        while (tryBlockStarts) {
            boolean wasBlank = blank;
            findNextNonSpace();

            if (blank && !wasBlank) lastPrefixClaimer = blockParser;

            // this is a little performance optimization:
            if (blank || (indent < myParsing.CODE_BLOCK_INDENT && Parsing.isLetter(line, nextNonSpace))) {
                setNewIndex(nextNonSpace);
                break;
            }

            BlockStartImpl blockStart = findBlockStart(blockParser);
            if (blockStart == null) {
                if (!(blockParser.isRawText() && blockParser.isInterruptible())) setNewIndex(nextNonSpace);
                break;
            }

            if (!allClosed) {
                finalizeBlocks(unmatchedBlockParsers);
                allClosed = true;
            }

            if (blockStart.getNewIndex() != -1) {
                setNewIndex(blockStart.getNewIndex());
            } else if (blockStart.getNewColumn() != -1) {
                setNewColumn(blockStart.getNewColumn());
            }

            if (blockStart.isReplaceActiveBlockParser()) {
                removeActiveBlockParser();
            }

            for (BlockParser newBlockParser : blockStart.getBlockParsers()) {
                blockParser = addChild(newBlockParser);
                tryBlockStarts = newBlockParser.isContainer();
            }
        }

        // What remains at the offset is a text line. Add the text to the
        // appropriate block.

        // First check for a lazy paragraph continuation:
        if (!allClosed && !blank && getActiveBlockParser().isParagraphParser()) {
            // lazy paragraph continuation
            addLine();
        } else {
            // finalize any blocks not matched
            if (!allClosed) {
                finalizeBlocks(unmatchedBlockParsers);
            }

            propagateLastLineBlank(blockParser, lastMatchedBlockParser);

            if (blank) {
                if (blockParser.getBlock() instanceof KeepTrailingBlankLineContainer) {
                    if (blankLine != null) {
                        blockParser.getBlock().appendChild(blankLine);
                    } else {
                        if (blockParser.isContainer() && lastPrefixClaimer == blockParser) {
                            // need to add it as a blank line if it is attributable to the block, otherwise there is no content
                            blankLine = new BlankLine(lineWithEOL, blockParser.getBlock());
                            blockParser.getBlock().appendChild(blankLine);
                        }
                    }
                }
            }

            if (!blockParser.isContainer()) {
                addLine();
            } else if (!blank) {
                // inlineParser paragraph container for line
                addChild(new ParagraphParser());
                addLine();
            }
        }
    }

    private void findNextNonSpace() {
        int i = index;
        int cols = column;

        blank = true;
        while (i < line.length()) {
            char c = line.charAt(i);
            switch (c) {
                case ' ':
                    i++;
                    cols++;
                    continue;
                case '\t':
                    i++;
                    cols += (4 - (cols % 4));
                    continue;
            }
            blank = false;
            break;
        }

        nextNonSpace = i;
        nextNonSpaceColumn = cols;
        indent = nextNonSpaceColumn - column;
    }

    private void setNewIndex(int newIndex) {
        if (newIndex >= nextNonSpace) {
            // We can start from here, no need to calculate tab stops again
            index = nextNonSpace;
            column = nextNonSpaceColumn;
        }
        while (index < newIndex && index != line.length()) {
            advance();
        }
        // If we're going to an index as opposed to a column, we're never within a tab
        columnIsInTab = false;
    }

    private void setNewColumn(int newColumn) {
        if (newColumn >= nextNonSpaceColumn) {
            // We can start from here, no need to calculate tab stops again
            index = nextNonSpace;
            column = nextNonSpaceColumn;
        }
        while (column < newColumn && index != line.length()) {
            advance();
        }
        if (column > newColumn) {
            // Last character was a tab and we overshot our target
            index--;
            column = newColumn;
            columnIsInTab = true;
        } else {
            columnIsInTab = false;
        }
    }

    private void advance() {
        char c = line.charAt(index);
        if (c == '\t') {
            index++;
            column += Parsing.columnsToNextTabStop(column);
        } else {
            index++;
            column++;
        }
    }

    /**
     * Add line content to the active block parser. We assume it can accept lines -- that check should be done before
     * calling this.
     */
    private void addLine() {
        BasedSequence content = lineWithEOL.subSequence(index);
        if (columnIsInTab) {
            // Our column is in a partially consumed tab. Expand the remaining columns (to the next tab stop) to spaces.
            BasedSequence rest = content.subSequence(1);
            int spaces = Parsing.columnsToNextTabStop(column);
            StringBuilder sb = new StringBuilder(spaces + rest.length());
            for (int i = 0; i < spaces; i++) {
                sb.append(' ');
            }
            //sb.append(rest);
            content = PrefixedSubSequence.prefixOf(sb.toString(), rest);
        }

        //getActiveBlockParser().addLine(content, content.baseSubSequence(lineEOL, lineEnd));
        //BasedSequence eol = content.baseSubSequence(lineEOL < lineEnd ? lineEnd - 1 : lineEnd, lineEnd).toMapped(EolCharacterMapper.INSTANCE);
        getActiveBlockParser().addLine(this, content);
    }

    private BlockStartImpl findBlockStart(BlockParser blockParser) {
        MatchedBlockParser matchedBlockParser = new MatchedBlockParserImpl(blockParser);
        for (BlockParserFactory blockParserFactory : blockParserFactories) {
            if (blockParser.canInterruptBy(blockParserFactory)) {
                BlockStart result = blockParserFactory.tryStart(this, matchedBlockParser);
                if (result instanceof BlockStartImpl) {
                    return (BlockStartImpl) result;
                }
            }
        }
        return null;
    }

    /**
     * Finalize a block. Close it and do any necessary postprocessing, e.g. creating string_content from strings,
     * setting the 'tight' or 'loose' status of a list, and parsing the beginnings of paragraphs for reference
     * definitions.
     *
     * @param blockParser block parser instance to finalize
     */
    private void finalize(BlockParser blockParser) {
        if (getActiveBlockParser() == blockParser) {
            deactivateBlockParser();
        }

        Block block = blockParser.getBlock();

        // move blank lines at end of block to parent if they are not claimed by the block
        if (block.getParent() != null) {
            Node lastChild = block.getLastChild();
            if (lastChild instanceof BlankLine) {
                if (((BlankLine) lastChild).getClaimedBlankLine() != block) {
                    // move them to parent
                    lastChild = lastChild.getFirstInChain();
                    block.insertChainAfter(lastChild);
                    block.setCharsFromContentOnly();
                }
            }
        }

        blockParser.closeBlock(this);
        blockParser.finalizeClosedBlock();

        // remove BlankLine nodes that are part of the block's content
        while (true) {
            Node next = block.getNext();
            if (next instanceof BlankLine && next.getEndOffset() <= block.getEndOffset()) {
                next.unlink();
            } else {
                break;
            }
        }
    }

    /**
     * Walk through a block & children recursively, parsing string content into inline content where appropriate.
     */
    private void processInlines() {
        for (BlockParser blockParser : blockTracker.allBlockParsers()) {
            blockParser.parseInlines(inlineParser);
        }
    }

    @Override
    public boolean endsWithBlankLine(Node block) {
        while (block != null) {
            if (isLastLineBlank(block)) {
                return true;
            }
            block = block.getLastBlankLineChild();
        }
        return false;
    }

    /**
     * Break out of all containing lists, resetting the tip of the document to the parent of the highest list, and
     * finalizing all the lists. (This is used to implement the "two blank lines break of of all lists" feature.)
     *
     * @param blockParsers list of block parsers to break out on double blank line
     */
    private void breakOutOfLists(List<BlockParser> blockParsers) {
        int lastList = -1;
        for (int i = blockParsers.size() - 1; i >= 0; i--) {
            BlockParser blockParser = blockParsers.get(i);
            if (blockParser.breakOutOnDoubleBlankLine()) {
                lastList = i;
            }
        }

        if (lastList != -1) {
            finalizeBlocks(blockParsers.subList(lastList, blockParsers.size()));
        }
    }

    /**
     * Add block parser of type T as a child of the currently active parsers. If the tip can't  accept children, close and finalize it and try
     * its parent, and so on til we find a block that can accept children.
     *
     * @param <T>         block parser type
     * @param blockParser new block parser to add as a child
     * @return block parser instance added as a child.
     */
    private <T extends BlockParser> T addChild(T blockParser) {
        while (!getActiveBlockParser().canContain(this, blockParser, blockParser.getBlock())) {
            finalize(getActiveBlockParser());
        }

        getActiveBlockParser().getBlock().appendChild(blockParser.getBlock());
        activateBlockParser(blockParser);

        return blockParser;
    }

    private void activateBlockParser(BlockParser blockParser) {
        activeBlockParsers.add(blockParser);
        if (!blockTracker.containsKey(blockParser)) {
            blockParserAdded(blockParser);
        }
    }

    private void deactivateBlockParser() {
        activeBlockParsers.remove(activeBlockParsers.size() - 1);
    }

    private void removeActiveBlockParser() {
        BlockParser old = getActiveBlockParser();
        deactivateBlockParser();

        blockParserRemoved(old);
        old.getBlock().unlink();
    }

    private void propagateLastLineBlank(BlockParser blockParser, BlockParser lastMatchedBlockParser) {
        if (blank) {
            Node lastChild = blockParser.getBlock().getLastChild();
            if (lastChild != null) setLastLineBlank(lastChild, true);
        }

        // Block quote lines are never blank as they start with >
        // and we don't count blanks in fenced code for purposes of tight/loose
        // lists or breaking out of lists. We also don't set lastLineBlank
        // on an empty list item.
        // now implemented by the block parsers to make it available to extensions
        boolean lastLineBlank = blank && blockParser.isPropagatingLastBlankLine(lastMatchedBlockParser);

        // Propagate lastLineBlank up through parents
        Node node = blockParser.getBlock();
        while (node != null) {
            setLastLineBlank(node, lastLineBlank);
            node = node.getParent();
        }
    }

    private void setLastLineBlank(Node node, boolean value) {
        lastLineBlank.put(node, value);
    }

    @Override
    public boolean isLastLineBlank(Node node) {
        Boolean value = lastLineBlank.get(node);
        return value != null && value;
    }

    /**
     * Finalize blocks of previous line.
     *
     * @return true.
     */
    private boolean finalizeBlocks(List<BlockParser> blockParsers) {
        for (int i = blockParsers.size() - 1; i >= 0; i--) {
            BlockParser blockParser = blockParsers.get(i);
            finalize(blockParser);
        }
        return true;
    }

    private static class ParagraphPreProcessorCache extends ItemFactoryMap<ParagraphPreProcessor, ParserState> {
        ParagraphPreProcessorCache(ParserState param) {
            super(param);
        }
    }

    /**
     * pre-process a paragraph block
     *
     * @param block        paragraph block to pre-process
     * @param stage        paragraph pre-processor dependency stage
     * @param processorMap paragraph pre-processor cache
     */
    private void preProcessParagraph(Paragraph block, List<ParagraphPreProcessorFactory> stage, ParagraphPreProcessorCache processorMap) {
        while (true) {
            boolean hadChanges = false;

            for (ParagraphPreProcessorFactory factory : stage) {
                ParagraphPreProcessor processor = processorMap.getItem(factory);

                int pos = processor.preProcessBlock(block, this);

                if (pos > 0) {
                    hadChanges = true;

                    // skip leading blanks
                    BasedSequence blockChars = block.getChars();
                    BasedSequence contentChars = blockChars.subSequence(pos + blockChars.countLeading(CharPredicate.WHITESPACE, pos, blockChars.length()));

                    if (contentChars.isBlank()) {
                        // all used up
                        block.unlink();
                        blockRemoved(block);
                        return;
                    } else {
                        // skip lines that were removed
                        int iMax = block.getLineCount();
                        int i;
                        for (i = 0; i < iMax; i++) {
                            if (block.getLineChars(i).getEndOffset() > contentChars.getStartOffset()) break;
                        }

                        if (i >= iMax) {
                            // all used up
                            block.unlink();
                            blockRemoved(block);
                            return;
                        } else if (block.getLineChars(i).getEndOffset() == contentChars.getStartOffset()) {
                            // full lines removed
                            block.setContent(block, i, iMax);
                        } else {
                            // need to change the first line of the line list
                            ArrayList<BasedSequence> lines = new ArrayList<>(iMax - i);
                            lines.addAll(block.getContentLines().subList(i, iMax));
                            int start = contentChars.getStartOffset() - lines.get(0).getStartOffset();
                            if (start > 0 && start < lines.get(0).length()) {
                                lines.set(0, lines.get(0).subSequence(start));
                            }

                            // now we copy the indents
                            int[] indents = new int[iMax - i];
                            System.arraycopy(block.getLineIndents(), i, indents, 0, indents.length);
                            block.setContentLines(lines);
                            block.setLineIndents(indents);
                            block.setChars(contentChars);
                        }
                    }
                }
            }

            if (!hadChanges || stage.size() < 2) break;
        }
    }

    private void preProcessParagraphs() {
        // here we run preProcessing stages
        if (blockTracker.getNodeClassifier().containsCategory(Paragraph.class)) {
            ParagraphPreProcessorCache processorMap = new ParagraphPreProcessorCache(this);
            for (List<ParagraphPreProcessorFactory> factoryStage : paragraphPreProcessorDependencies) {

                for (Paragraph paragraph : blockTracker.getNodeClassifier().getCategoryItems(Paragraph.class, Paragraph.class)) {
                    preProcessParagraph(paragraph, factoryStage, processorMap);
                }
            }
        }
    }

    private void preProcessBlocks() {
        // here we run preProcessing stages
        HashSet<Class<?>> blockTypes = new HashSet<>();
        for (List<BlockPreProcessorFactory> dependents : blockPreProcessorDependencies) {
            for (BlockPreProcessorFactory factory : dependents) {
                blockTypes.addAll(factory.getBlockTypes());
            }
        }

        BitSet preProcessBitSet = blockTracker.getNodeClassifier().categoriesBitSet(blockTypes);

        if (!preProcessBitSet.isEmpty()) {
            for (List<BlockPreProcessorFactory> dependents : blockPreProcessorDependencies) {
                for (BlockPreProcessorFactory factory : dependents) {
                    ReversibleIterable<Block> blockList = blockTracker.getNodeClassifier().getCategoryItems(Block.class, factory.getBlockTypes());
                    BlockPreProcessor blockPreProcessor = factory.apply(this);

                    for (Block block : blockList) {
                        blockPreProcessor.preProcess(this, block);
                    }
                }
            }
        }
    }

    //private static Logger LOG = Logger.getLogger(DocumentParser.class);

    private Document finalizeAndProcess() {
        finalizeBlocks(this.activeBlockParsers);

        //if (LOG.isDebugEnabled()) {
        //    LOG.debug("Parsed\n" + new AstCollectingVisitor().collectAndGetAstText(documentBlockParser.getBlock()));
        //}

        // need to run block pre-processors at this point, before inline processing
        currentPhase = ParserPhase.PRE_PROCESS_PARAGRAPHS;
        this.preProcessParagraphs();

        //if (LOG.isDebugEnabled()) {
        //    LOG.debug("Paragraphs PreProcessed\n" + new AstCollectingVisitor().collectAndGetAstText(documentBlockParser.getBlock()));
        //}

        currentPhase = ParserPhase.PRE_PROCESS_BLOCKS;
        this.preProcessBlocks();

        //if (LOG.isDebugEnabled()) {
        //    LOG.debug("Blocks PreProcessed\n" + new AstCollectingVisitor().collectAndGetAstText(documentBlockParser.getBlock()));
        //}

        // can naw run inline processing
        currentPhase = ParserPhase.PARSE_INLINES;
        this.processInlines();

        //if (LOG.isDebugEnabled()) {
        //    LOG.debug("Inline Processed\n" + new AstCollectingVisitor().collectAndGetAstText(documentBlockParser.getBlock()));
        //}

        currentPhase = ParserPhase.DONE;
        Document document = this.documentBlockParser.getBlock();
        inlineParser.finalizeDocument(document);

        return document;
    }
}
