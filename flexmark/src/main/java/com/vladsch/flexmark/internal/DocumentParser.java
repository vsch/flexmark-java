package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.*;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.*;
import com.vladsch.flexmark.parser.block.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentParser implements ParserState {

    public static final InlineParserFactory INLINE_PARSER_FACTORY = new InlineParserFactory() {
        @Override
        public InlineParser inlineParser(DataHolder options, BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors, LinkRefProcessorData linkRefProcessors) {
            return new CommonmarkInlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors);
        }
    };

    private static HashMap<BlockParserFactory, DataKey<Boolean>> CORE_FACTORIES_DATA_KEYS = new HashMap<>();
    static {
        CORE_FACTORIES_DATA_KEYS.put(new BlockQuoteParser.Factory(), Parser.BLOCK_QUOTE_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new HeadingParser.Factory(), Parser.HEADING_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new FencedCodeBlockParser.Factory(), Parser.FENCED_CODE_BLOCK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new HtmlBlockParser.Factory(), Parser.HTML_BLOCK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new ThematicBreakParser.Factory(), Parser.THEMATIC_BREAK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new ListBlockParser.Factory(), Parser.LIST_BLOCK_PARSER);
        CORE_FACTORIES_DATA_KEYS.put(new IndentedCodeBlockParser.Factory(), Parser.INDENTED_CODE_BLOCK_PARSER);
    }

    private static List<BlockParserFactory> CORE_FACTORIES = new ArrayList<>();
    static {
        CORE_FACTORIES.add(new BlockQuoteParser.Factory());
        CORE_FACTORIES.add(new HeadingParser.Factory());
        CORE_FACTORIES.add(new FencedCodeBlockParser.Factory());
        CORE_FACTORIES.add(new HtmlBlockParser.Factory());
        CORE_FACTORIES.add(new ThematicBreakParser.Factory());
        CORE_FACTORIES.add(new ListBlockParser.Factory());
        CORE_FACTORIES.add(new IndentedCodeBlockParser.Factory());
    }

    public static class ReferencePreProcessorFactory implements ParagraphPreProcessorFactory {
        @Override
        public boolean getAffectsDocumentProperties() {
            return true;
        }

        @Override
        public Set<Class<? extends ParagraphPreProcessorFactory>> getRunAfter() {
            return null;
        }

        @Override
        public ParagraphPreProcessor create(ParserState state) {
            return ((InlineParserImpl) state.getInlineParser());
        }
    }

    private static HashMap<DataKey<Boolean>, ParagraphPreProcessorFactory> CORE_PARAGRAPH_PRE_PROCESSORS = new HashMap<>();
    static {
        CORE_PARAGRAPH_PRE_PROCESSORS.put(Parser.REFERENCE_PARAGRAPH_PRE_PROCESSOR, new ReferencePreProcessorFactory());
    }

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

    private final List<BlockParserFactory> blockParserFactories;
    private final BlockPreProcessorFactories paragraphPreProcessorFactories;
    private final InlineParser inlineParser;
    private final DocumentBlockParser documentBlockParser;

    private List<BlockParser> activeBlockParsers = new ArrayList<>();
    private Set<BlockParser> allBlockParsers = new HashSet<>();
    private ArrayList<ParagraphParser> allParagraphParsers = new ArrayList<>();
    private Map<Node, Boolean> lastLineBlank = new HashMap<>();
    final private DataHolder options;

    public static class BlockPreProcessorFactories {
        final List<List<ParagraphPreProcessorFactory>> preProcessingStages;

        BlockPreProcessorFactories(List<List<ParagraphPreProcessorFactory>> preProcessingStages) {
            this.preProcessingStages = preProcessingStages;
        }
    }

    public DocumentParser(DataHolder options, List<BlockParserFactory> blockParserFactories, BlockPreProcessorFactories paragraphPreProcessorFactories, InlineParser inlineParser) {
        this.options = options;
        this.blockParserFactories = blockParserFactories;
        this.paragraphPreProcessorFactories = paragraphPreProcessorFactories;
        this.inlineParser = inlineParser;

        this.documentBlockParser = new DocumentBlockParser();
        activateBlockParser(this.documentBlockParser);
    }

    @Override
    public MutableDataHolder getProperties() {
        return documentBlockParser.getBlock();
    }

    public static List<BlockParserFactory> calculateBlockParserFactories(DataHolder options, List<BlockParserFactory> customBlockParserFactories) {
        List<BlockParserFactory> list = new ArrayList<>();
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        list.addAll(customBlockParserFactories);

        // need to keep core parsers in the right order
        for (BlockParserFactory factory : CORE_FACTORIES) {
            DataKey<Boolean> key = CORE_FACTORIES_DATA_KEYS.get(factory);

            if (key == null || options.get(key)) {
                list.add(factory);
            }
        }
        return list;
    }

    public static BlockPreProcessorFactories calculateBlockPreProcessors(DataHolder options, List<ParagraphPreProcessorFactory> blockPreProcessors, InlineParserFactory inlineParserFactory) {
        List<ParagraphPreProcessorFactory> list = new ArrayList<>();
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        list.addAll(blockPreProcessors);

        if (inlineParserFactory == INLINE_PARSER_FACTORY) {
            list.addAll(CORE_PARAGRAPH_PRE_PROCESSORS.keySet().stream().filter(options::get).map(key -> CORE_PARAGRAPH_PRE_PROCESSORS.get(key)).collect(Collectors.toList()));
        }

        if (list.size() == 0) {
            return new BlockPreProcessorFactories(Collections.EMPTY_LIST);
        } else if (list.size() == 1) {
            HashMap<Class<? extends Block>, List<ParagraphPreProcessorFactory>> nodeMap = new HashMap<>();
            ParagraphPreProcessorFactory preProcessor = list.get(0);
            List<ParagraphPreProcessorFactory> preProcessorFactories = Collections.singletonList(preProcessor);
            return new BlockPreProcessorFactories(Collections.singletonList(preProcessorFactories));
        } else {
            // resolve dependencies and node processing lists
            int preProcessorCount = blockPreProcessors.size();
            HashMap<Class<? extends ParagraphPreProcessorFactory>, Set<Class<? extends ParagraphPreProcessorFactory>>> dependencyMap = new HashMap<>(preProcessorCount);
            HashMap<Class<? extends ParagraphPreProcessorFactory>, ParagraphPreProcessorFactory> instanceMap = new HashMap<>(preProcessorCount);
            HashSet<Class<? extends ParagraphPreProcessorFactory>> independentProcessors = new HashSet<>(preProcessorCount);
            HashSet<Class<? extends ParagraphPreProcessorFactory>> dependentGlobalProcessors = new HashSet<>(preProcessorCount);
            HashSet<Class<? extends ParagraphPreProcessorFactory>> dependentProcessors = new HashSet<>(preProcessorCount);

            for (ParagraphPreProcessorFactory preProcessor : list) {
                if (instanceMap.containsKey(preProcessor.getClass())) {
                    throw new IllegalStateException("BlockPreProcessor class " + preProcessor.getClass() + " is duplicated. Only one instance can be present");
                }

                instanceMap.put(preProcessor.getClass(), preProcessor);

                Set<Class<? extends ParagraphPreProcessorFactory>> dependencies = preProcessor.getRunAfter();
                if (preProcessor.getAffectsDocumentProperties()) {
                    dependentGlobalProcessors.add(preProcessor.getClass());
                } else {
                    dependentProcessors.add(preProcessor.getClass());
                }

                if (dependencies != null && dependencies.size() > 0) dependencyMap.put(preProcessor.getClass(), dependencies);
            }

            ArrayList<List<ParagraphPreProcessorFactory>> preProcessingStages = new ArrayList<>();

            while (dependentGlobalProcessors.size() > 0) {
                // process these independents in unspecified order since they do not have dependencies
                ArrayList<ParagraphPreProcessorFactory> stageProcessors = new ArrayList<>();

                // collect block processors ready for processing
                for (Class<? extends ParagraphPreProcessorFactory> preProcessorClass : dependentGlobalProcessors) {
                    Set<Class<? extends ParagraphPreProcessorFactory>> dependencies = dependencyMap.get(preProcessorClass);
                    if (dependencies == null || independentProcessors.containsAll(dependencies)) {
                        stageProcessors.add(instanceMap.get(preProcessorClass));
                    }
                }

                if (stageProcessors.size() == 0) {
                    throw new IllegalStateException("have dependent global processors with circular or non-global dependencies " + dependentGlobalProcessors);
                }

                for (ParagraphPreProcessorFactory preProcessor : stageProcessors) {
                    dependentGlobalProcessors.remove(preProcessor.getClass());
                    independentProcessors.add(preProcessor.getClass());
                }
                preProcessingStages.add(stageProcessors);
            }

            // now we resolve any non-global processors
            while (dependentProcessors.size() > 0) {
                ArrayList<ParagraphPreProcessorFactory> stageProcessors = new ArrayList<>();

                // collect block processors ready for processing
                for (Class<? extends ParagraphPreProcessorFactory> preProcessorClass : dependentProcessors) {
                    Set<Class<? extends ParagraphPreProcessorFactory>> dependencies = dependencyMap.get(preProcessorClass);
                    if (dependencies == null || independentProcessors.containsAll(dependencies)) {
                        stageProcessors.add(instanceMap.get(preProcessorClass));
                    }
                }

                if (stageProcessors.size() == 0) {
                    throw new IllegalStateException("have dependent processors with circular dependencies " + dependentProcessors);
                }

                for (ParagraphPreProcessorFactory preProcessor : stageProcessors) {
                    dependentProcessors.remove(preProcessor.getClass());
                    independentProcessors.add(preProcessor.getClass());
                }
                preProcessingStages.add(stageProcessors);
            }

            return new BlockPreProcessorFactories(preProcessingStages);
        }
    }

    @Override
    public InlineParser getInlineParser() {
        return inlineParser;
    }

    /**
     * The main parsing function. Returns a parsed document AST.
     */
    public Document parse(CharSequence source) {
        BasedSequence input = source instanceof BasedSequence ? (BasedSequence) source : new SubSequence(source);
        int lineStart = 0;
        int lineBreak;
        int lineEOL;
        int lineEnd;
        lineNumber = 0;

        documentBlockParser.initializeDocument(options, input);
        inlineParser.initializeDocument(documentBlockParser.getBlock());

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
            file.append(buffer, 0, charsRead);
            if (charsRead < buffer.length) break;
        }

        CharSequence source = new StringSequence(file.toString());
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
    public BlockParser getActiveBlockParser() {
        return activeBlockParsers.get(activeBlockParsers.size() - 1);
    }

    /**
     * Analyze a line of text and update the document appropriately. We parse markdown text by calling this on each
     * line of input, then finalizing the document.
     */
    private void incorporateLine(BasedSequence ln) {
        line = ln;
        index = 0;
        column = 0;
        columnIsInTab = false;

        // For each containing block, try to parse the associated line start.
        // Bail out on failure: container will point to the last matching block.
        // Set all_matched to false if not all containers match.
        // The document will always match, can be skipped
        int matches = 1;
        for (BlockParser blockParser : activeBlockParsers.subList(1, activeBlockParsers.size())) {
            findNextNonSpace();

            BlockContinue result = blockParser.tryContinue(this);
            if (result instanceof BlockContinueImpl) {
                BlockContinueImpl blockContinue = (BlockContinueImpl) result;
                if (blockContinue.isFinalize()) {
                    finalize(blockParser);
                    return;
                } else {
                    if (blockContinue.getNewIndex() != -1) {
                        setNewIndex(blockContinue.getNewIndex());
                    } else if (blockContinue.getNewColumn() != -1) {
                        setNewColumn(blockContinue.getNewColumn());
                    }
                    matches++;
                }
            } else {
                break;
            }
        }

        List<BlockParser> unmatchedBlockParsers = new ArrayList<>(activeBlockParsers.subList(matches, activeBlockParsers.size()));
        BlockParser lastMatchedBlockParser = activeBlockParsers.get(matches - 1);
        BlockParser blockParser = lastMatchedBlockParser;
        boolean allClosed = unmatchedBlockParsers.isEmpty();

        // Check to see if we've hit 2nd blank line; if so break out of list:
        if (isBlank() && isLastLineBlank(blockParser.getBlock())) {
            List<BlockParser> matchedBlockParsers = new ArrayList<>(activeBlockParsers.subList(0, matches));
            breakOutOfLists(matchedBlockParsers);
        }

        // Unless last matched container is a code block, try new container starts,
        // adding children to the last matched container:
        boolean tryBlockStarts = blockParser.getBlock() instanceof Paragraph || blockParser.isContainer();
        while (tryBlockStarts) {
            findNextNonSpace();

            // this is a little performance optimization:
            if (isBlank() || (indent < Parsing.CODE_BLOCK_INDENT && Parsing.isLetter(line, nextNonSpace))) {
                setNewIndex(nextNonSpace);
                break;
            }

            BlockStartImpl blockStart = findBlockStart(blockParser);
            if (blockStart == null) {
                setNewIndex(nextNonSpace);
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
        if (!allClosed && !isBlank() && getActiveBlockParser() instanceof ParagraphParser) {
            // lazy paragraph continuation
            addLine();
        } else {
            // finalize any blocks not matched
            if (!allClosed) {
                finalizeBlocks(unmatchedBlockParsers);
            }
            propagateLastLineBlank(blockParser, lastMatchedBlockParser);

            if (!blockParser.isContainer()) {
                addLine();
            } else if (!isBlank()) {
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
            content = new PrefixedSubSequence(sb.toString(), rest);
        }

        //getActiveBlockParser().addLine(content, content.baseSubSequence(lineEOL, lineEnd));
        //BasedSequence eol = content.baseSubSequence(lineEOL < lineEnd ? lineEnd - 1 : lineEnd, lineEnd).toMapped(EolCharacterMapper.INSTANCE);
        getActiveBlockParser().addLine(this, content);
    }

    private BlockStartImpl findBlockStart(BlockParser blockParser) {
        MatchedBlockParser matchedBlockParser = new MatchedBlockParserImpl(blockParser);
        for (BlockParserFactory blockParserFactory : blockParserFactories) {
            BlockStart result = blockParserFactory.tryStart(this, matchedBlockParser);
            if (result instanceof BlockStartImpl) {
                return (BlockStartImpl) result;
            }
        }
        return null;
    }

    /**
     * Finalize a block. Close it and do any necessary postprocessing, e.g. creating string_content from strings,
     * setting the 'tight' or 'loose' status of a list, and parsing the beginnings of paragraphs for reference
     * definitions.
     */
    private void finalize(BlockParser blockParser) {
        if (getActiveBlockParser() == blockParser) {
            deactivateBlockParser();
        }

        blockParser.closeBlock(this);

        blockParser.finalizeClosedBlock();
    }

    /**
     * Walk through a block & children recursively, parsing string content into inline content where appropriate.
     */
    private void processInlines() {
        for (BlockParser blockParser : allBlockParsers) {
            blockParser.parseInlines(inlineParser);
        }
    }

    @Override
    public boolean endsWithBlankLine(Node block) {
        while (block != null) {
            if (isLastLineBlank(block)) {
                return true;
            }
            if (block instanceof ListBlock || block instanceof ListItem) {
                block = block.getLastChild();
            } else {
                break;
            }
        }
        return false;
    }

    /**
     * Break out of all containing lists, resetting the tip of the document to the parent of the highest list,
     * and finalizing all the lists. (This is used to implement the "two blank lines break of of all lists" feature.)
     */
    private void breakOutOfLists(List<BlockParser> blockParsers) {
        int lastList = -1;
        for (int i = blockParsers.size() - 1; i >= 0; i--) {
            BlockParser blockParser = blockParsers.get(i);
            if (blockParser instanceof ListBlockParser) {
                lastList = i;
            }
        }

        if (lastList != -1) {
            finalizeBlocks(blockParsers.subList(lastList, blockParsers.size()));
        }
    }

    /**
     * Add block of type tag as a child of the tip. If the tip can't  accept children, close and finalize it and try
     * its parent, and so on til we find a block that can accept children.
     */
    private <T extends BlockParser> T addChild(T blockParser) {
        while (!getActiveBlockParser().canContain(blockParser.getBlock())) {
            finalize(getActiveBlockParser());
        }

        getActiveBlockParser().getBlock().appendChild(blockParser.getBlock());
        activateBlockParser(blockParser);

        return blockParser;
    }

    private void activateBlockParser(BlockParser blockParser) {
        activeBlockParsers.add(blockParser);

        if (blockParser instanceof ParagraphParser && !allBlockParsers.contains(blockParser)) {
            allParagraphParsers.add((ParagraphParser) blockParser);
        }

        allBlockParsers.add(blockParser);
    }

    private void deactivateBlockParser() {
        activeBlockParsers.remove(activeBlockParsers.size() - 1);
    }

    private void removeActiveBlockParser() {
        BlockParser old = getActiveBlockParser();
        deactivateBlockParser();
        allBlockParsers.remove(old);

        old.getBlock().unlink();
    }

    private void propagateLastLineBlank(BlockParser blockParser, BlockParser lastMatchedBlockParser) {
        if (isBlank() && blockParser.getBlock().getLastChild() != null) {
            setLastLineBlank(blockParser.getBlock().getLastChild(), true);
        }

        Block block = blockParser.getBlock();

        // Block quote lines are never blank as they start with >
        // and we don't count blanks in fenced code for purposes of tight/loose
        // lists or breaking out of lists. We also don't set lastLineBlank
        // on an empty list item.
        boolean lastLineBlank = isBlank() &&
                !(block instanceof BlockQuote ||
                        block instanceof FencedCodeBlock ||
                        (block instanceof ListItem &&
                                block.getFirstChild() == null &&
                                blockParser != lastMatchedBlockParser));

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
     * Finalize blocks of previous line. Returns true.
     */
    private boolean finalizeBlocks(List<BlockParser> blockParsers) {
        for (int i = blockParsers.size() - 1; i >= 0; i--) {
            BlockParser blockParser = blockParsers.get(i);
            finalize(blockParser);
        }
        return true;
    }

    private void preProcessParagraph(Paragraph block, List<ParagraphPreProcessor> processors) {
        while (true) {
            boolean hadChanges = false;

            for (ParagraphPreProcessor processor : processors) {
                int pos = processor.preProcessBlock(block, this);

                if (pos > 0) {
                    hadChanges = true;
                    BasedSequence contentChars = block.getChars().subSequence(pos);

                    if (contentChars.isBlank()) {
                        // all used up
                        block.unlink();
                        hadChanges = false;
                        break;
                    } else {
                        // skip lines that were removed
                        int iMax = block.getLineCount();
                        int i;
                        for (i = 0; i < iMax; i++) {
                            if (block.getLineChars(i).getStartOffset() >= contentChars.getStartOffset()) break;
                        }

                        block.setContent(block, i, iMax);
                    }
                }
            }

            if (!hadChanges || processors.size() < 2) break;
        }
    }

    private void preProcessParagraphs() {
        // here we run preProcessing stages
        if (allParagraphParsers.size() > 0) {
            List<List<ParagraphPreProcessor>> paragraphPreProcessorStages = new ArrayList<>(paragraphPreProcessorFactories.preProcessingStages.size());
            for (List<ParagraphPreProcessorFactory> factoryStages : paragraphPreProcessorFactories.preProcessingStages) {
                ArrayList<ParagraphPreProcessor> stagePreProcessors = new ArrayList<>(factoryStages.size());
                for (ParagraphPreProcessorFactory factory : factoryStages) {
                    stagePreProcessors.add(factory.create(this));
                }
                paragraphPreProcessorStages.add(stagePreProcessors);
            }

            for (List<ParagraphPreProcessor> preProcessorStage : paragraphPreProcessorStages) {
                for (ParagraphParser paragraphParser : allParagraphParsers) {
                    if (allBlockParsers.contains(paragraphParser)) {
                        Paragraph block = paragraphParser.getBlock();
                        preProcessParagraph(block, preProcessorStage);
                    }
                }
            }
        }
    }

    private Document finalizeAndProcess() {
        finalizeBlocks(this.activeBlockParsers);

        // need to run block pre-processors at this point, before inline processing
        this.preProcessParagraphs();
        this.processInlines();

        Document document = this.documentBlockParser.getBlock();
        inlineParser.finalizeDocument(document);
        return document;
    }

    private static class MatchedBlockParserImpl implements MatchedBlockParser {
        private final BlockParser matchedBlockParser;

        @Override
        public List<BasedSequence> getParagraphLines() {
            if (matchedBlockParser instanceof ParagraphParser) {
                ParagraphParser paragraphParser = (ParagraphParser) matchedBlockParser;
                return paragraphParser.getContent().getLines();
            }
            return null;
        }

        public List<Integer> getParagraphEolLengths() {
            if (matchedBlockParser instanceof ParagraphParser) {
                ParagraphParser paragraphParser = (ParagraphParser) matchedBlockParser;
                return paragraphParser.getContent().getLineIndents();
            }
            return null;
        }

        public MatchedBlockParserImpl(BlockParser matchedBlockParser) {
            this.matchedBlockParser = matchedBlockParser;
        }

        @Override
        public BlockParser getMatchedBlockParser() {
            return matchedBlockParser;
        }

        @Override
        public BasedSequence getParagraphContent() {
            if (matchedBlockParser instanceof ParagraphParser) {
                ParagraphParser paragraphParser = (ParagraphParser) matchedBlockParser;
                return paragraphParser.getContent().getContents();
            }
            return null;
        }

        @Override
        public MutableDataHolder getParagraphDataHolder() {
            if (matchedBlockParser instanceof ParagraphParser) {
                ParagraphParser paragraphParser = (ParagraphParser) matchedBlockParser;
                return paragraphParser.getDataHolder();
            }
            return null;
        }
    }
}
