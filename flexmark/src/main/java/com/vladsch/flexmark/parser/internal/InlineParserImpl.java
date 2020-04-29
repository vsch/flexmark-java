package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.ast.util.TextNodeConverter;
import com.vladsch.flexmark.parser.*;
import com.vladsch.flexmark.parser.block.CharacterNodeFactory;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessor;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.parser.core.delimiter.AsteriskDelimiterProcessor;
import com.vladsch.flexmark.parser.core.delimiter.Bracket;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.parser.core.delimiter.UnderscoreDelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.DependencyResolver;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;

public class InlineParserImpl extends LightInlineParserImpl implements InlineParser, ParagraphPreProcessor {
    protected final BitSet originalSpecialCharacters;
    protected final BitSet delimiterCharacters;
    protected final Map<Character, DelimiterProcessor> delimiterProcessors;
    protected final LinkRefProcessorData linkRefProcessorsData;
    protected List<LinkRefProcessor> linkRefProcessors = null;
    protected Map<Character, List<InlineParserExtension>> inlineParserExtensions = null;
    protected List<InlineParserExtensionFactory> inlineParserExtensionFactories = null;
    protected LinkDestinationParser linkDestinationParser = null;

    // used to temporarily override handling of special characters by custom ParagraphPreProcessors
    protected BitSet specialCharacters;
    protected BitSet customCharacters = null;
    protected Map<Character, CharacterNodeFactory> customSpecialCharacterFactoryMap = null;
    protected ArrayList<Node> customSpecialCharacterNodes = null;

    /**
     * Link references by ID, needs to be built up using parseReference before calling parse.
     */
    protected ReferenceRepository referenceRepository;

    /**
     * Top delimiter (emphasis, strong emphasis or custom emphasis). (Brackets are on a separate stack, different
     * from the algorithm described in the spec.)
     */
    protected Delimiter lastDelimiter;

    /**
     * Top opening bracket (<code>[</code> or <code>![)</code>).
     */
    private Bracket lastBracket;

    public InlineParserImpl(
            DataHolder options,
            BitSet specialCharacters,
            BitSet delimiterCharacters,
            Map<Character, DelimiterProcessor> delimiterProcessors,
            LinkRefProcessorData linkRefProcessorsData,
            List<InlineParserExtensionFactory> inlineParserExtensionFactories
    ) {
        super(options);
        this.delimiterProcessors = delimiterProcessors;
        this.linkRefProcessorsData = linkRefProcessorsData;
        this.delimiterCharacters = delimiterCharacters;
        this.originalSpecialCharacters = specialCharacters;
        this.specialCharacters = specialCharacters;
        this.inlineParserExtensionFactories = !inlineParserExtensionFactories.isEmpty() ? inlineParserExtensionFactories : null;

        if (this.options.useHardcodedLinkAddressParser) {
            this.linkDestinationParser = new LinkDestinationParser(this.options.linksAllowMatchedParentheses, this.options.spaceInLinkUrls, this.options.parseJekyllMacrosInUrls, this.options.intellijDummyIdentifier);
        }
    }

    @Override
    public void initializeDocument(@NotNull Document document) {
        this.document = document;
        this.referenceRepository = Parser.REFERENCES.get(document);

        linkRefProcessors = new ArrayList<>(linkRefProcessorsData.processors.size());
        for (LinkRefProcessorFactory factory : linkRefProcessorsData.processors) {
            linkRefProcessors.add(factory.apply(document));
        }

        // create custom processors
        if (inlineParserExtensionFactories != null) {
            Map<Character, List<InlineParserExtensionFactory>> extensions = calculateInlineParserExtensions(document, inlineParserExtensionFactories);
            inlineParserExtensions = new HashMap<>(extensions.size());
            Map<InlineParserExtensionFactory, InlineParserExtension> parserExtensionMap = new HashMap<>();
            for (Map.Entry<Character, List<InlineParserExtensionFactory>> entry : extensions.entrySet()) {
                List<InlineParserExtension> extensionList = new ArrayList<>(entry.getValue().size());
                for (InlineParserExtensionFactory factory : entry.getValue()) {
                    InlineParserExtension parserExtension = parserExtensionMap.get(factory);
                    if (parserExtension == null) {
                        parserExtension = factory.apply(this);
                        parserExtensionMap.put(factory, parserExtension);
                    }
                    extensionList.add(parserExtension);
                }

                inlineParserExtensions.put(entry.getKey(), extensionList);

                // set it as special character
                specialCharacters.set(entry.getKey());
            }
        }
    }

    @Override
    public void finalizeDocument(@NotNull Document document) {
        assert this.referenceRepository == Parser.REFERENCES.get(document);

        if (inlineParserExtensions != null) {
            for (List<InlineParserExtension> extensionList : inlineParserExtensions.values()) {
                for (InlineParserExtension extension : extensionList) {
                    extension.finalizeDocument(this);
                }
            }
        }
    }

    @Override
    public Delimiter getLastDelimiter() {
        return lastDelimiter;
    }

    @Override
    public Bracket getLastBracket() {
        return lastBracket;
    }

    @Nullable
    @Override
    public List<Node> parseCustom(@NotNull BasedSequence input, @NotNull Node node, @NotNull BitSet customCharacters, @NotNull Map<Character, CharacterNodeFactory> nodeFactoryMap) {
        this.customCharacters = customCharacters;
        this.specialCharacters.or(customCharacters);
        this.customSpecialCharacterFactoryMap = nodeFactoryMap;
        this.customSpecialCharacterNodes = null;
        parse(input, node);
        this.specialCharacters = this.originalSpecialCharacters;
        this.customSpecialCharacterFactoryMap = null;
        this.customCharacters = null;
        return this.customSpecialCharacterNodes;
    }

    /**
     * Parse content in block into inline children, using reference map to resolve references.
     */
    @Override
    public void parse(@NotNull BasedSequence content, @NotNull Node block) {
        this.block = block;
        this.input = content.trim();
        this.index = 0;
        this.lastDelimiter = null;
        this.lastBracket = null;

        boolean customOnly = (block instanceof DoNotDecorate);// || block.getAncestorOfType(DoNotDecorate.class) != null;

        boolean moreToParse;
        do {
            moreToParse = parseInline(customOnly);
        } while (moreToParse);

        processDelimiters(null);
        flushTextNode();

        if (!customOnly) {
            if (inlineParserExtensions != null) {
                for (List<InlineParserExtension> extensionList : inlineParserExtensions.values()) {
                    for (InlineParserExtension extension : extensionList) {
                        extension.finalizeBlock(this);
                    }
                }
            }
        }

        // merge nodes if needed
        mergeTextNodes(block.getFirstChild(), block.getLastChild());
    }

    @Override
    public void mergeTextNodes(@Nullable Node fromNode, @Nullable Node toNode) {
        Text first = null;
        Text last = null;

        Node node = fromNode;
        while (node != null) {
            if (node instanceof Text) {
                Text text = (Text) node;
                if (first == null) {
                    first = text;
                }
                last = text;
            } else {
                mergeIfNeeded(first, last);
                first = null;
                last = null;
            }

            if (node == toNode) {
                break;
            }

            node = node.getNext();
        }

        mergeIfNeeded(first, last);
    }

    @Override
    public void mergeIfNeeded(Text first, Text last) {
        if (first != null && last != null && first != last) {
            ArrayList<BasedSequence> sb = new ArrayList<>();
            sb.add(first.getChars());
            Node node = first.getNext();
            Node stop = last.getNext();
            while (node != stop) {
                sb.add(node.getChars());
                Node unlink = node;
                node = node.getNext();
                unlink.unlink();
            }
            BasedSequence literal = SegmentedSequence.create(first.getChars(), sb);
            first.setChars(literal);
        }
    }

    /*
     *  ParagraphPreProcessor implementation
     */
    @Override
    public int preProcessBlock(Paragraph block, ParserState state) {
        BasedSequence contentChars = block.getChars();

        // try parsing the beginning as link reference definitions:
        int leadingSpaces = contentChars.countLeading(CharPredicate.SPACE_TAB);
        int length = contentChars.length();

        while (leadingSpaces <= 3 && length > 3 + leadingSpaces && contentChars.charAt(leadingSpaces) == '[') {
            if (leadingSpaces > 0) {
                contentChars = contentChars.subSequence(leadingSpaces, length);
                length -= leadingSpaces;
            }

            int pos = parseReference(block, contentChars);
            if (pos == 0) break;
            contentChars = contentChars.subSequence(pos, length);
            length = contentChars.length();
            leadingSpaces = contentChars.countLeading(CharPredicate.SPACE_TAB);
        }

        return contentChars.getStartOffset() - block.getStartOffset();
    }

    /**
     * Attempt to parse a reference definition, modifying the internal reference map.
     *
     * @param block the block whose text is being parsed for references
     * @param s     sequence of the blocks characters
     * @return number of characters were parsed as a reference from the start of the sequence, {@code 0} if none
     */
    protected int parseReference(Block block, BasedSequence s) {
        this.input = s;
        this.index = 0;
        BasedSequence dest;
        BasedSequence title;
        int matchChars;
        int startIndex = index;

        // label:
        matchChars = parseLinkLabel();
        if (matchChars == 0) {
            return 0;
        }

        // colon:
        if (peek() != ':') {
            return 0;
        }

        BasedSequence rawLabel = input.subSequence(0, matchChars + 1);
        index++;

        // link url
        spnl();

        dest = parseLinkDestination();

        if (dest == null || dest.length() == 0) {
            return 0;
        }

        int beforeTitle = index;
        spnl();
        title = parseLinkTitle();
        if (title == null) {
            // rewind before spaces
            index = beforeTitle;
        }

        boolean atLineEnd = true;
        if (index != input.length() && match(myParsing.LINE_END) == null) {
            if (title == null) {
                atLineEnd = false;
            } else {
                // the potential title we found is not at the line end,
                // but it could still be a legal link reference if we
                // discard the title
                title = null;
                // rewind before spaces
                index = beforeTitle;
                // and instead check if the link URL is at the line end
                atLineEnd = match(myParsing.LINE_END) != null;
            }
        }

        if (!atLineEnd) {
            return 0;
        }

        String normalizedLabel = Escaping.normalizeReferenceChars(rawLabel, true);
        if (normalizedLabel.isEmpty()) {
            return 0;
        }

        Reference reference = new Reference(rawLabel, dest, title);

        // NOTE: whether first or last reference is kept is defined by the repository modify behavior setting
        // for CommonMark this is set in the initializeDocument() function of the inline parser
        referenceRepository.put(normalizedLabel, reference);

        block.insertBefore(reference);

        return index - startIndex;
    }

    /**
     * Parse the next inline element in subject, advancing input index.
     * On success, add the result to block's children and return true.
     * On failure, return false.
     *
     * @return false on failure true on success
     */
    protected boolean parseInline() {
        return parseInline(false);
    }

    protected boolean parseInline(boolean customOnly) {
        boolean res = false;

        char c = peek();
        if (c == SequenceUtils.NUL) {
            return false;
        }

        if (!customOnly && inlineParserExtensions != null) {
            List<InlineParserExtension> extensions = inlineParserExtensions.get(c);
            if (extensions != null) {
                for (InlineParserExtension extension : extensions) {
                    res = extension.parse(this);
                    if (res) return true;
                }
            }
        }

        if (customCharacters != null && customCharacters.get(c)) {
            res = processCustomCharacters();
            if (!res) {
                index++;
                // When we get here, it's only for a single special character that turned out to not have a special meaning.
                // So we shouldn't have a single surrogate here, hence it should be ok to turn it into a String.
                appendText(input.subSequence(index - 1, index));
            } else {
                // Issue: #376, need to clear any delimiter stack since whatever is not used is not a delimiter
                processDelimiters(null);
                this.lastDelimiter = null;
            }

            return true;
        }

        switch (c) {
            case '\r':
            case '\n':
                res = parseNewline();
                break;
            case '\\':
                res = parseBackslash();
                break;
            case '`':
                res = parseBackticks();
                break;
            case '[':
                res = parseOpenBracket();
                break;
            case '!':
                res = parseBang();
                break;
            case ']':
                res = parseCloseBracket();
                break;
            case '<': {
                // first we check custom special characters for < delimiters and only allow 2 consecutive ones to allow anchor links and HTML processing
                boolean isDelimiter = delimiterCharacters.get(c);
                if (isDelimiter && peek(1) == '<') {
                    DelimiterProcessor delimiterProcessor = delimiterProcessors.get(c);
                    res = parseDelimiters(delimiterProcessor, c);
                } else {
                    res = parseAutolink() || parseHtmlInline();
                }
            }
            break;
            case '&':
                res = parseEntity();
                break;
            default: {
                // first we check custom special characters
                boolean isDelimiter = delimiterCharacters.get(c);
                if (isDelimiter) {
                    DelimiterProcessor delimiterProcessor = delimiterProcessors.get(c);
                    res = parseDelimiters(delimiterProcessor, c);
                } else {
                    res = parseString();
                }
            }
            break;
        }

        if (!res) {
            index++;
            // When we get here, it's only for a single special character that turned out to not have a special meaning.
            // So we shouldn't have a single surrogate here, hence it should be ok to turn it into a String.
            appendText(input.subSequence(index - 1, index));
        }

        return true;
    }

    private boolean processCustomCharacters() {
        char c = peek();
        CharacterNodeFactory factory = customSpecialCharacterFactoryMap.get(c);
        if (factory == null) return false;

        Node node = factory.get();
        node.setChars(input.subSequence(index, index + 1));

        if (currentText != null) {
            BasedSequence prevText = SegmentedSequence.create(node.getChars(), currentText);
            currentText = null;

            // see if need to trim some off the end
            int pos = prevText.length();
            BasedSequence skipped = null;
            while (pos > 0 && factory.skipPrev(prevText.charAt(pos - 1))) pos--;
            if (pos < prevText.length()) {
                skipped = prevText.subSequence(pos);
                prevText = prevText.subSequence(0, pos);
            }

            block.appendChild(new Text(prevText));
            if (skipped != null && factory.wantSkippedWhitespace()) {
                block.appendChild(new WhiteSpace(skipped));
            }
        }

        appendNode(node);
        if (customSpecialCharacterNodes == null) customSpecialCharacterNodes = new ArrayList<>();
        customSpecialCharacterNodes.add(node);

        int pos = index + 1;
        do {
            index++;
            c = peek();
        }
        while (c != SequenceUtils.NUL && factory.skipNext(c));

        if (pos < index && factory.wantSkippedWhitespace()) {
            block.appendChild(new WhiteSpace(input.subSequence(pos, index)));
        }

        return true;
    }

    /**
     * Parse a newline. If it was preceded by two spaces, append a hard line break; otherwise a soft line break.
     *
     * @return true
     */
    @Override
    public boolean parseNewline() {
        boolean crLf = index < input.length() - 1 && input.charAt(index + 1) == '\n';
        int crLfDelta = crLf ? 1 : 0;
        index += 1 + crLfDelta;

        // We're gonna add a new node in any case and we need to check the last text node, so flush outstanding text.
        flushTextNode();

        Node lastChild = block.getLastChild();
        // Check previous text for trailing spaces.
        // The "endsWith" is an optimization to avoid an RE match in the common case.
        if (lastChild instanceof Text && (lastChild.getChars().endsWith(" "))) {
            Text text = (Text) lastChild;
            BasedSequence literal = text.getChars();
            Matcher matcher = myParsing.FINAL_SPACE.matcher(literal);
            int spaces = matcher.find() ? matcher.end() - matcher.start() : 0;
            appendNode(spaces >= 2 ? new HardLineBreak(input.subSequence(index - (options.hardLineBreakLimit ? 3 + crLfDelta : spaces + 1 + crLfDelta), index)) : new SoftLineBreak(input.subSequence(index - 1 - crLfDelta, index)));
            if (spaces > 0) {
                if (literal.length() > spaces) {
                    lastChild.setChars(literal.subSequence(0, literal.length() - spaces).trimEnd());
                } else {
                    lastChild.unlink();
                }
            }
        } else {
            appendNode(new SoftLineBreak(input.subSequence(index - 1 - crLfDelta, index)));
        }

        // gobble leading spaces in next line
        while (peek() == ' ') {
            index++;
        }
        return true;
    }

    /**
     * Parse a backslash-escaped special character, adding either the escaped  character, a hard line break
     * (if the backslash is followed by a newline), or a literal backslash to the block's children.
     *
     * @return true
     */
    protected boolean parseBackslash() {
        index++;
        if (peek() == '\n' || peek() == '\r') {
            int charsMatched = peek(1) == '\n' ? 2 : 1;
            appendNode(new HardLineBreak(input.subSequence(index - 1, index + charsMatched)));
            index += charsMatched;
        } else if (index < input.length() && myParsing.ESCAPABLE.matcher(input.subSequence(index, index + 1)).matches()) {
            appendText(input, index - 1, index + 1);
            index++;
        } else {
            appendText(input.subSequence(index - 1, index));
        }
        return true;
    }

    /**
     * Attempt to parse backticks, adding either a backtick code span or a literal sequence of backticks.
     *
     * @return true if matched backticks, false otherwise
     */
    protected boolean parseBackticks() {
        BasedSequence ticks = match(myParsing.TICKS_HERE);
        if (ticks == null) {
            return false;
        }
        int afterOpenTicks = index;
        BasedSequence matched;
        while ((matched = match(myParsing.TICKS)) != null) {
            if (matched.equals(ticks)) {
                int ticksLength = ticks.length();
                BasedSequence codeText = input.subSequence(afterOpenTicks, index - ticksLength);
                Code node = new Code(input.subSequence(afterOpenTicks - ticksLength, afterOpenTicks), codeText, input.subSequence(index - ticksLength, index));

                if (options.codeSoftLineBreaks) {
                    // add softbreaks to code ast
                    int length = codeText.length();
                    int lastPos = 0;
                    while (lastPos < length) {
                        int softBreak = codeText.indexOfAny(CharPredicate.ANY_EOL, lastPos);
                        int pos = softBreak == -1 ? length : softBreak;

                        Text textNode = new Text(codeText.subSequence(lastPos, pos));
                        node.appendChild(textNode);

                        lastPos = pos;
                        if (lastPos >= length) break;
                        if (codeText.charAt(lastPos) == '\r') {
                            lastPos++;
                            if (lastPos >= length) break;
                            if (codeText.charAt(lastPos) == '\n') lastPos++;
                        } else {
                            lastPos++;
                        }

                        if (lastPos >= length) break;

                        if (pos < lastPos) {
                            SoftLineBreak softLineBreak = new SoftLineBreak(codeText.subSequence(softBreak, lastPos));
                            node.appendChild(softLineBreak);
                        }
                    }
                } else {
                    Text textNode = new Text(codeText);
                    node.appendChild(textNode);
                }

                appendNode(node);
                return true;
            }
        }

        // If we got here, we didn't match a closing backtick sequence.
        index = afterOpenTicks;
        appendText(ticks);
        return true;
    }

    private static class DelimiterData {
        final int count;
        final boolean canClose;
        final boolean canOpen;

        DelimiterData(int count, boolean canOpen, boolean canClose) {
            this.count = count;
            this.canOpen = canOpen;
            this.canClose = canClose;
        }
    }

    /**
     * Attempt to parse delimiters like emphasis, strong emphasis or custom delimiters.
     *
     * @param delimiterProcessor delimiter processor instance
     * @param delimiterChar      delimiter character being processed
     * @return true if processed characters false otherwise
     */
    protected boolean parseDelimiters(DelimiterProcessor delimiterProcessor, char delimiterChar) {
        DelimiterData res = scanDelimiters(delimiterProcessor, delimiterChar);
        if (res == null) {
            return false;
        }
        int numDelims = res.count;
        int startIndex = index;

        index += numDelims;
        Text node = appendSeparateText(input.subSequence(startIndex, index));

        // Add entry to stack for this opener
        this.lastDelimiter = new Delimiter(input, node, delimiterChar, res.canOpen, res.canClose, this.lastDelimiter, startIndex);
        this.lastDelimiter.setNumDelims(numDelims);
        if (this.lastDelimiter.getPrevious() != null) {
            this.lastDelimiter.getPrevious().setNext(this.lastDelimiter);
        }
        return true;
    }

    /**
     * Add open bracket to delimiter stack and add a text node to block's children.
     *
     * @return true
     */
    protected boolean parseOpenBracket() {
        int startIndex = index;
        index++;

        Text node = appendSeparateText(input.subSequence(index - 1, index));

        // Add entry to stack for this opener
        addBracket(Bracket.link(input, node, startIndex, lastBracket, lastDelimiter));
        return true;
    }

    /**
     * If next character is [, and ! delimiter to delimiter stack and add a text node to block's children.
     * Otherwise just add a text node.
     *
     * @return true if processed characters false otherwise
     */
    protected boolean parseBang() {
        int startIndex = index;
        index++;
        if (peek() == '[') {
            index++;

            Text node = appendSeparateText(input.subSequence(index - 2, index));

            // Add entry to stack for this opener
            addBracket(Bracket.image(input, node, startIndex + 1, lastBracket, lastDelimiter));
        } else {
            appendText(input.subSequence(index - 1, index));
        }
        return true;
    }

    private void addBracket(Bracket bracket) {
        if (lastBracket != null) {
            lastBracket.setBracketAfter(true);
        }
        lastBracket = bracket;
    }

    private void removeLastBracket() {
        lastBracket = lastBracket.getPrevious();
    }

    static class ReferenceProcessorMatch {
        final public LinkRefProcessor processor;
        final public BasedSequence nodeChars;
        final public boolean wantExclamation;

        public ReferenceProcessorMatch(LinkRefProcessor processor, boolean wantExclamation, BasedSequence nodeChars) {
            this.processor = processor;
            this.nodeChars = nodeChars;
            this.wantExclamation = wantExclamation;
        }
    }

    private ReferenceProcessorMatch matchLinkRef(Bracket opener, int startIndex, int lookAhead, int nesting) {
        if (linkRefProcessorsData.nestingIndex.length == 0) return null;

        ReferenceProcessorMatch match = null;
        BasedSequence textNoBang = null;
        BasedSequence textWithBang = null;
        boolean wantBang;

        int iMax = linkRefProcessorsData.processors.size();
        int startProc = linkRefProcessorsData.nestingIndex[lookAhead + nesting];
        for (int i = startProc; i < iMax; i++) {
            LinkRefProcessor linkProcessor = linkRefProcessors.get(i);
            BasedSequence nodeChars;

            if (lookAhead + nesting < linkProcessor.getBracketNestingLevel()) break;

            wantBang = linkProcessor.getWantExclamationPrefix();

            // preview the link ref
            if (opener.isImage() && wantBang) {
                // this one has index off by one for the leading !
                if (textWithBang == null) textWithBang = input.subSequence(opener.getStartIndex() - 1 - lookAhead, startIndex + lookAhead);
                nodeChars = textWithBang;
            } else {
                if (wantBang && opener.getStartIndex() >= lookAhead + 1 && input.charAt(opener.getStartIndex() - 1 - lookAhead) == '!') {
                    if (textWithBang == null) textWithBang = input.subSequence(opener.getStartIndex() - 1 - lookAhead, startIndex + lookAhead);
                    nodeChars = textWithBang;
                } else {
                    if (textNoBang == null) textNoBang = input.subSequence(opener.getStartIndex() - lookAhead, startIndex + lookAhead);
                    nodeChars = textNoBang;
                }
            }

            if (linkProcessor.isMatch(nodeChars)) {
                match = new ReferenceProcessorMatch(linkProcessor, wantBang, nodeChars);
                break;
            }
        }
        return match;
    }

    /**
     * Try to match close bracket against an opening in the delimiter stack. Add either a link or image, or a
     * plain [ character, to block's children. If there is a matching delimiter, removeIndex it from the delimiter stack.
     * <p>
     * Also handles custom link ref processing
     *
     * @return true
     */
    protected boolean parseCloseBracket() {
        index++;
        int startIndex = index;
        int nestedBrackets;

        // look through stack of delimiters for a [ or ![
        Bracket opener = this.lastBracket;
        if (opener == null) {
            // No matching opener, just return a literal.
            appendText(input.subSequence(index - 1, index));
            return true;
        }

        if (!opener.isAllowed()) {
            // Matching opener but it's not allowed, just return a literal.
            appendText(input.subSequence(index - 1, index));
            removeLastBracket();
            return true;
        }

        nestedBrackets = 0;

        // Check to see if we have a link/image
        BasedSequence dest = null;
        BasedSequence title = null;
        BasedSequence ref = null;
        boolean isLinkOrImage = false;
        boolean refIsBare = false;
        ReferenceProcessorMatch linkRefProcessorMatch = null;
        boolean refIsDefined = false;
        BasedSequence linkOpener = BasedSequence.NULL;
        BasedSequence linkCloser = BasedSequence.NULL;
        BasedSequence bareRef = BasedSequence.NULL;
        BasedSequence imageUrlContent = null;

        // Inline link?
        int preSpaceIndex = index;

        // May need to skip spaces
        if (options.spaceInLinkElements && peek() == ' ') {
            sp();
        }

        if (peek() == '(') {
            int savedIndex = index;

            linkOpener = input.subSequence(index, index + 1);
            index++;
            spnl();
            if ((dest = parseLinkDestination()) != null) {
                if (options.parseMultiLineImageUrls && opener.isImage() && !dest.startsWith("<") && dest.endsWith("?") && spnlUrl()) {
                    // possible multi-line image url
                    int contentStart = index;
                    int contentEnd = contentStart;
                    BasedSequence multiLineTitle;

                    while (true) {
                        sp();
                        multiLineTitle = parseLinkTitle();
                        if (multiLineTitle != null) sp();

                        if (peek() == ')') {
                            linkCloser = input.subSequence(index, index + 1);
                            index++;
                            imageUrlContent = input.subSequence(contentStart, contentEnd);
                            title = multiLineTitle;
                            isLinkOrImage = true;
                            break;
                        }

                        BasedSequence restOfLine = toEOL();
                        if (restOfLine == null) break;
                        contentEnd = index;
                    }
                } else {
                    spnl();
                    // title needs a whitespace before
                    if (myParsing.WHITESPACE.matcher(input.subSequence(index - 1, index)).matches()) {
                        title = parseLinkTitle();
                        spnl();
                    }

                    // test for spaces in url making it invalid, otherwise anything else goes
                    if (peek() == ')') {
                        linkCloser = input.subSequence(index, index + 1);
                        index++;
                        isLinkOrImage = true;
                    } else {
                        // back out, no match
                        index = savedIndex;
                    }
                }
            } else {
                index = savedIndex;
            }
        } else {
            index = preSpaceIndex;
        }

        if (!isLinkOrImage) {
            // maybe reference link, need to see if it matches a custom processor or need to skip this reference because it will be processed on the next char
            // as something else, like a wiki link
            if (!options.matchLookaheadFirst) {
                linkRefProcessorMatch = matchLinkRef(opener, startIndex, 0, nestedBrackets);
            }

            if (linkRefProcessorMatch != null) {
                // have a match, then no look ahead for next matches
            } else {
                // need to figure out max nesting we should test based on what is max processor desire and max available
                // nested inner ones are always only []
                int maxWanted = linkRefProcessorsData.maxNesting;
                int maxAvail = 0;

                if (maxWanted > nestedBrackets) {
                    // need to see what is available
                    Bracket nested = opener;
                    while (nested.getPrevious() != null && nested.getStartIndex() == nested.getPrevious().getStartIndex() + 1 && peek(maxAvail) == ']') {
                        nested = nested.getPrevious();
                        maxAvail++;
                        if (maxAvail + nestedBrackets == maxWanted || nested.isImage()) break;
                    }
                }

                for (int nesting = maxAvail + 1; nesting-- > 0; ) {
                    linkRefProcessorMatch = matchLinkRef(opener, startIndex, nesting, nestedBrackets);

                    if (linkRefProcessorMatch != null) {
                        if (nesting > 0) {
                            while (nesting-- > 0) {
                                index++;
                                lastBracket.getNode().unlink();
                                removeLastBracket();
                            }
                            opener = lastBracket;
                        }
                        break;
                    }
                }
            }

            if (linkRefProcessorMatch == null) {
                // See if there's a link label
                int beforeLabel = index;
                int labelLength = parseLinkLabel();
                if (labelLength > 2) {
                    ref = input.subSequence(beforeLabel, beforeLabel + labelLength);
                } else if (!opener.isBracketAfter()) {
                    // Empty or missing second label can only be a reference if there's no unescaped bracket in it.
                    bareRef = input.subSequence(beforeLabel, beforeLabel + labelLength);
                    if (opener.isImage()) {
                        // this one has index off by one for the leading !
                        ref = input.subSequence(opener.getStartIndex() - 1, startIndex);
                    } else {
                        ref = input.subSequence(opener.getStartIndex(), startIndex);
                    }
                    refIsBare = true;
                }

                if (ref != null) {
                    String normalizedLabel = Escaping.normalizeReferenceChars(ref, true);
                    if (referenceRepository.containsKey(normalizedLabel)) {
                        BasedSequence sequence = input.subSequence(opener.getStartIndex(), startIndex);
                        boolean containsLinks = containsLinkRefs(refIsBare ? ref : sequence, opener.getNode().getNext(), true);
                        isLinkOrImage = !containsLinks;
                        refIsDefined = true;
                    } else {
                        // need to test if we are cutting in the middle of some other delimiters matching, if we are not then we will make this into a tentative
                        if (!opener.isStraddling(ref)) {
                            // link ref, otherwise we will break
                            // it is the innermost ref and is bare, if not bare then we treat it as a ref

                            if (!refIsBare && peek() == '[') {
                                int nextLength = parseLinkLabel();
                                if (nextLength > 0) {
                                    // not bare and not defined and followed by another [], roll back to before the label and make it just text
                                    index = beforeLabel;
                                } else {
                                    // undefined ref, create a tentative one but only if does not contain any other link refs
                                    boolean containsLinks = containsLinkRefs(ref, opener.getNode().getNext(), null);
                                    if (!containsLinks) {
                                        refIsBare = true;
                                        isLinkOrImage = true;
                                    }
                                }
                            } else {
                                // undefined ref, bare or followed by empty [], create a tentative link ref but only if does not contain any other link refs
                                boolean containsLinks = containsLinkRefs(ref, opener.getNode().getNext(), null);
                                if (!containsLinks) {
                                    isLinkOrImage = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isLinkOrImage || linkRefProcessorMatch != null) {
            // If we got here, open is a potential opener
            // Flush text now. We don't need to worry about combining it with adjacent text nodes, as we'll wrap it in a
            // link or image node.
            flushTextNode();

            Node insertNode;
            boolean isImage = opener.isImage();

            if (linkRefProcessorMatch != null) {
                if (!linkRefProcessorMatch.wantExclamation && isImage) {
                    appendText(input.subSequence(opener.getStartIndex() - 1, opener.getStartIndex()));
                    opener.getNode().setChars(opener.getNode().getChars().subSequence(1));
                    //opener.image = false;
                    isImage = false;
                }

                insertNode = linkRefProcessorMatch.processor.createNode(linkRefProcessorMatch.nodeChars);
            } else {
                insertNode = ref != null ? isImage ? new ImageRef() : new LinkRef() : isImage ? new Image() : new Link();
            }

            {
                Node node = opener.getNode().getNext();
                while (node != null) {
                    Node next = node.getNext();
                    insertNode.appendChild(node);
                    node = next;
                }
            }

            if (linkRefProcessorMatch != null) {
                // may need to adjust children's text because some characters were part of the processor's opener/closer
                if (insertNode.hasChildren()) {
                    BasedSequence original = insertNode.getChildChars();
                    BasedSequence text = linkRefProcessorMatch.processor.adjustInlineText(document, insertNode);

                    // may need to remove some delimiters if they span across original and changed text boundary or if now they are outside text boundary
                    Delimiter delimiter = lastDelimiter;
                    while (delimiter != null) {
                        Delimiter prevDelimiter = delimiter.getPrevious();

                        BasedSequence delimiterChars = delimiter.getInput().subSequence(delimiter.getStartIndex(), delimiter.getEndIndex());
                        if (original.containsAllOf(delimiterChars)) {
                            if (!text.containsAllOf(delimiterChars) || !linkRefProcessorMatch.processor.allowDelimiters(delimiterChars, document, insertNode)) {
                                // remove it
                                removeDelimiterKeepNode(delimiter);
                            }
                        }

                        delimiter = prevDelimiter;
                    }

                    if (!text.containsAllOf(original)) {
                        // now need to truncate child text
                        for (Node node : insertNode.getChildren()) {
                            BasedSequence nodeChars = node.getChars();
                            if (text.containsSomeOf(nodeChars)) {
                                if (!text.containsAllOf(nodeChars)) {
                                    // truncate the contents to intersection of node's chars and adjusted chars
                                    BasedSequence chars = text.intersect(nodeChars);
                                    node.setChars(chars);
                                }
                            } else {
                                // remove the node
                                node.unlink();
                            }
                        }
                    }
                }
            }
            appendNode(insertNode);

            if (insertNode instanceof RefNode) {
                // set up the parts
                RefNode refNode = (RefNode) insertNode;
                refNode.setReferenceChars(ref);
                if (refIsDefined) refNode.setDefined(true);

                if (!refIsBare) {
                    refNode.setTextChars(input.subSequence(isImage ? opener.getStartIndex() - 1 : opener.getStartIndex(), startIndex));
                } else if (!bareRef.isEmpty()) {
                    refNode.setTextOpeningMarker(bareRef.subSequence(0, 1));
                    refNode.setTextClosingMarker(bareRef.endSequence(1));
                }
                insertNode.setCharsFromContent();
            } else if (insertNode instanceof InlineLinkNode) {
                // set dest and title
                InlineLinkNode inlineLinkNode = (InlineLinkNode) insertNode;
                inlineLinkNode.setUrlChars(dest);
                inlineLinkNode.setTitleChars(title);
                inlineLinkNode.setLinkOpeningMarker(linkOpener);
                inlineLinkNode.setLinkClosingMarker(linkCloser);
                inlineLinkNode.setTextChars(isImage ? input.subSequence(opener.getStartIndex() - 1, startIndex) : input.subSequence(opener.getStartIndex(), startIndex));

                if (imageUrlContent != null) {
                    ((Image) insertNode).setUrlContent(imageUrlContent);
                }

                insertNode.setCharsFromContent();
            }

            // Process delimiters such as emphasis inside link/image
            processDelimiters(opener.getPreviousDelimiter());
            Node toRemove = opener.getNode();
            removeLastBracket();

            if (linkRefProcessorMatch != null) {
                linkRefProcessorMatch.processor.updateNodeElements(document, insertNode);
            }

            // Links within links are not allowed. We found this link, so there can be no other link around it.
            if (insertNode instanceof Link) {
                Bracket bracket = this.lastBracket;
                while (bracket != null) {
                    if (!bracket.isImage()) {
                        // Disallow link opener. It will still get matched, but will not result in a link.
                        bracket.setAllowed(false);
                    }
                    bracket = bracket.getPrevious();
                }

                // collapse any link refs contained in this link, they are duds, link takes precedence
                // TODO: add a test to see if all link refs should be collapsed or just undefined ones
                collapseLinkRefChildren(insertNode, true, true);
            } else if (insertNode instanceof RefNode) {
                // have a link ref, collapse to text any tentative ones contained in it, they are duds
                collapseLinkRefChildren(insertNode, true, true);
            }

            toRemove.unlink();
            return true;
        } else { // no link or image
            index = startIndex;
            appendText(input.subSequence(index - 1, index));
            removeLastBracket();
            return true;
        }
    }

    protected static boolean containsLinkRefs(BasedSequence nodeChars, Node next, Boolean isDefined) {
        int startOffset = nodeChars.getStartOffset();
        int endOffset = nodeChars.getEndOffset();
        while (next != null) {
            if (next instanceof LinkRef && (isDefined == null || ((LinkRef) next).isDefined() == isDefined) && !(next.getStartOffset() >= endOffset || next.getEndOffset() <= startOffset)) {
                return true;
            }
            next = next.getNext();
        }
        return false;
    }

    protected static void collapseLinkRefChildren(Node node, Boolean isTentative, boolean trimFirstLastChild) {
        Node child = node.getFirstChild();
        boolean hadCollapse = false;
        while (child != null) {
            Node nextChild = child.getNext();
            if (child instanceof LinkRefDerived && (isTentative == null || isTentative == ((RefNode) child).isTentative())) {
                // need to collapse this one, moving its text contents to text
                collapseLinkRefChildren(child, isTentative, false);
                child.unlink();

                TextNodeConverter list = new TextNodeConverter(child.getChars());
                list.addChildrenOf(child);
                if (nextChild != null) {
                    list.insertMergedBefore(nextChild);
                } else {
                    list.appendMergedTo(node);
                }
                hadCollapse = true;
            }
            child = nextChild;
        }

        if (hadCollapse) {
            TextNodeConverter.mergeTextNodes(node);
        }

        if (trimFirstLastChild) {
            // trim first and last child text
            Node firstChild = node.getFirstChild();
            Node lastChild = node.getLastChild();

            if (firstChild == lastChild) {
                if (firstChild != null && !(firstChild instanceof DoNotTrim)) firstChild.setChars(firstChild.getChars().trim());
            } else {
                if (firstChild != null && !(firstChild instanceof DoNotTrim)) firstChild.setChars(firstChild.getChars().trimStart());
                if (lastChild != null && !(lastChild instanceof DoNotTrim)) lastChild.setChars(lastChild.getChars().trimEnd());
            }
        }
    }

    /**
     * Attempt to parse link destination,
     *
     * @return the string or null if no match.
     */
    @Override
    public BasedSequence parseLinkDestination() {
        BasedSequence res = match(myParsing.LINK_DESTINATION_ANGLES);
        if (res != null) {
            return res;
        } else {
            if (linkDestinationParser != null) {
                BasedSequence match = linkDestinationParser.parseLinkDestination(input, index);
                index += match.length();
                return match;
            } else {
                boolean spaceInUrls = options.spaceInLinkUrls;
                if (options.linksAllowMatchedParentheses) {
                    // allow matched parenthesis
                    // fix for issue of stack overflow when parsing long input lines, by implementing non-recursive scan
                    BasedSequence matched = match(myParsing.LINK_DESTINATION_MATCHED_PARENS);
                    if (matched != null) {
                        int openCount = 0;
                        int iMax = matched.length();
                        for (int i = 0; i < iMax; i++) {
                            char c = matched.charAt(i);
                            if (c == '\\') {
                                if (i + 1 < iMax && myParsing.ESCAPABLE.matcher(matched.subSequence(i + 1, i + 2)).matches()) {
                                    // escape
                                    i++;
                                }
                            } else if (c == '(') {
                                openCount++;
                            } else if (c == ')') {
                                if (openCount == 0) {
                                    // truncate to this and leave ')' to be parsed
                                    index -= iMax - i;
                                    matched = matched.subSequence(0, i);
                                    break;
                                }
                                openCount--;
                            }
                        }

                        return spaceInUrls ? matched.trimEnd(CharPredicate.SPACE) : matched;
                    }

                    return null;
                } else {
                    // spec 0.27 compatibility
                    BasedSequence matched = match(myParsing.LINK_DESTINATION);
                    return matched != null && spaceInUrls ? matched.trimEnd(CharPredicate.SPACE) : matched;
                }
            }
        }
    }

    /**
     * Attempt to parse link title (sans quotes),
     *
     * @return the string or null if no match.
     */
    @Override
    public BasedSequence parseLinkTitle() {
        BasedSequence title = match(myParsing.LINK_TITLE);
        if (title != null) {
            // chop off quotes from title and unescape:
            return title; //Escaping.unescapeString(title.substring(1, title.length() - 1));
        } else {
            return null;
        }
    }

    /**
     * Attempt to parse a link label
     *
     * @return number of characters parsed.
     */
    @Override
    public int parseLinkLabel() {
        BasedSequence m = match(myParsing.LINK_LABEL);
        return m == null ? 0 : m.length();
    }

    /**
     * Attempt to parse an autolink (URL or email in pointy brackets).
     *
     * @return true if processed characters false otherwise
     */
    @Override
    public boolean parseAutolink() {
        BasedSequence m;
        if ((m = match(myParsing.EMAIL_AUTOLINK)) != null) {
            MailLink node = new MailLink(m.subSequence(0, 1), m.subSequence(1, m.length() - 1), m.subSequence(m.length() - 1, m.length()));
            appendNode(node);
            return true;
        } else if ((m = match(myParsing.AUTOLINK)) != null) {
            AutoLink node = new AutoLink(m.subSequence(0, 1), m.subSequence(1, m.length() - 1), m.subSequence(m.length() - 1, m.length()));
            appendNode(node);
            return true;
        } else if (options.wwwAutoLinkElement && (m = match(myParsing.WWW_AUTOLINK)) != null) {
            AutoLink node = new AutoLink(m.subSequence(0, 1), m.subSequence(1, m.length() - 1), m.subSequence(m.length() - 1, m.length()));
            appendNode(node);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempt to parse inline HTML.
     *
     * @return true if processed characters false otherwise
     */
    @Override
    public boolean parseHtmlInline() {
        BasedSequence m = match(myParsing.HTML_TAG);
        if (m != null) {
            // separate HTML comment from herd
            HtmlInlineBase node;
            if (m.startsWith("<!--") && m.endsWith("-->")) {
                node = new HtmlInlineComment(m);
            } else {
                node = new HtmlInline(m);
            }
            appendNode(node);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempt to parse an entity, return Entity object if successful.
     *
     * @return true if processed characters false otherwise
     */
    @Override
    public boolean parseEntity() {
        BasedSequence m;
        if ((m = match(myParsing.ENTITY_HERE)) != null) {
            HtmlEntity node = new HtmlEntity(m);
            appendNode(node);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parse a run of ordinary characters, or a single character with a special meaning in markdown, as a plain string.
     *
     * @return true if processed characters false otherwise
     */
    protected boolean parseString() {
        int begin = index;
        int length = input.length();
        while (index != length) {
            if (specialCharacters.get(input.charAt(index))) {
                break;
            }
            index++;
        }
        if (begin != index) {
            appendText(input, begin, index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Scan a sequence of characters with code delimiterChar, and return information about the number of delimiters
     * and whether they are positioned such that they can open and/or close emphasis or strong emphasis.
     *
     * @param delimiterProcessor delimiter processor instance
     * @param delimiterChar      delimiter character being scanned
     * @return information about delimiter run, or {@code null}
     */
    protected DelimiterData scanDelimiters(DelimiterProcessor delimiterProcessor, char delimiterChar) {
        int startIndex = index;

        int delimiterCount = 0;
        while (peek() == delimiterChar) {
            delimiterCount++;
            index++;
        }

        if (delimiterCount < delimiterProcessor.getMinLength()) {
            index = startIndex;
            return null;
        }

        String before = startIndex == 0 ? SequenceUtils.EOL : String.valueOf(input.charAt(startIndex - 1));

        char charAfter = peek();
        String after = charAfter == SequenceUtils.NUL ? SequenceUtils.EOL : String.valueOf(charAfter);

        // We could be more lazy here, in most cases we don't need to do every match case.
        boolean beforeIsPunctuation;
        boolean afterIsPunctuation;
        boolean leftFlanking;
        boolean rightFlanking;
        boolean beforeIsWhitespace = myParsing.UNICODE_WHITESPACE_CHAR.matcher(before).matches();
        boolean afterIsWhitespace = myParsing.UNICODE_WHITESPACE_CHAR.matcher(after).matches();

        if (options.inlineDelimiterDirectionalPunctuations) {
            beforeIsPunctuation = myParsing.PUNCTUATION_OPEN.matcher(before).matches();
            afterIsPunctuation = myParsing.PUNCTUATION_CLOSE.matcher(after).matches();

            leftFlanking = !afterIsWhitespace &&
                    (!afterIsPunctuation || beforeIsWhitespace || beforeIsPunctuation);
            rightFlanking = !beforeIsWhitespace &&
                    (!beforeIsPunctuation || afterIsWhitespace || afterIsPunctuation);
        } else {
            beforeIsPunctuation = myParsing.PUNCTUATION.matcher(before).matches();
            afterIsPunctuation = myParsing.PUNCTUATION.matcher(after).matches();

            leftFlanking = !afterIsWhitespace &&
                    !(afterIsPunctuation && !beforeIsWhitespace && !beforeIsPunctuation);
            rightFlanking = !beforeIsWhitespace &&
                    !(beforeIsPunctuation && !afterIsWhitespace && !afterIsPunctuation);
        }

        boolean canOpen;
        boolean canClose;

        canOpen = delimiterChar == delimiterProcessor.getOpeningCharacter() && delimiterProcessor.canBeOpener(before, after, leftFlanking, rightFlanking, beforeIsPunctuation, afterIsPunctuation, beforeIsWhitespace, afterIsWhitespace);
        canClose = delimiterChar == delimiterProcessor.getClosingCharacter() && delimiterProcessor.canBeCloser(before, after, leftFlanking, rightFlanking, beforeIsPunctuation, afterIsPunctuation, beforeIsWhitespace, afterIsWhitespace);

        index = startIndex;

        if (canOpen || canClose || !delimiterProcessor.skipNonOpenerCloser()) {
            return new DelimiterData(delimiterCount, canOpen, canClose);
        } else {
            return null;
        }
    }

    @Override
    public void processDelimiters(Delimiter stackBottom) {
        Map<Character, Delimiter> openersBottom = new HashMap<>();

        // find first closer above stackBottom:
        Delimiter closer = lastDelimiter;
        while (closer != null && closer.getPrevious() != stackBottom) {
            closer = closer.getPrevious();
        }

        // move forward, looking for closers, and handling each
        while (closer != null) {
            char delimiterChar = closer.getDelimiterChar();

            DelimiterProcessor delimiterProcessor = delimiterProcessors.get(delimiterChar);
            if (!closer.canClose() || delimiterProcessor == null) {
                closer = closer.getNext();
                continue;
            }

            char openingDelimiterChar = delimiterProcessor.getOpeningCharacter();

            // found delimiter closer. now look back for first matching opener:
            int useDelims = 0;
            boolean openerFound = false;
            boolean potentialOpenerFound = false;
            Delimiter opener = closer.getPrevious();
            while (opener != null && opener != stackBottom && opener != openersBottom.get(delimiterChar)) {
                if (opener.canOpen() && opener.getDelimiterChar() == openingDelimiterChar) {
                    potentialOpenerFound = true;
                    useDelims = delimiterProcessor.getDelimiterUse(opener, closer);

                    if (useDelims > 0) {
                        openerFound = true;
                        break;
                    }
                }
                opener = opener.getPrevious();
            }

            if (!openerFound) {
                if (!potentialOpenerFound) {
                    // Set lower bound for future searches for openers.
                    // Only do this when we didn't even have a potential
                    // opener (one that matches the character and can open).
                    // If an opener was rejected because of the number of
                    // delimiters (e.g. because of the "multiple of 3" rule),
                    // we want to consider it next time because the number
                    // of delimiters can change as we continue processing.
                    openersBottom.put(delimiterChar, closer.getPrevious());
                    if (!closer.canOpen()) {
                        // We can remove a closer that can't be an opener,
                        // once we've seen there's no matching opener:
                        removeDelimiterKeepNode(closer);
                    }
                }
                closer = closer.getNext();
                continue;
            }

            // Remove number of used delimiters from stack and inline nodes.
            opener.setNumDelims(opener.getNumDelims() - useDelims);
            closer.setNumDelims(closer.getNumDelims() - useDelims);

            removeDelimitersBetween(opener, closer);
            //// The delimiter processor can re-parent the nodes between opener and closer,
            //// so make sure they're contiguous already.
            //mergeTextNodes(openerNode.getNext(), closerNode.getPrevious());

            opener.setNumDelims(opener.getNumDelims() + useDelims);
            closer.setNumDelims(closer.getNumDelims() + useDelims);

            delimiterProcessor.process(opener, closer, useDelims);

            opener.setNumDelims(opener.getNumDelims() - useDelims);
            closer.setNumDelims(closer.getNumDelims() - useDelims);

            // No delimiter characters left to process, so we can remove delimiter and the now empty node.
            if (opener.getNumDelims() == 0) {
                removeDelimiterAndNode(opener);
            } else {
                // adjust number of characters in the node by keeping outer of numDelims
                opener.getNode().setChars(opener.getNode().getChars().subSequence(0, opener.getNumDelims()));
            }

            if (closer.getNumDelims() == 0) {
                Delimiter next = closer.getNext();
                removeDelimiterAndNode(closer);
                closer = next;
            } else {
                // adjust number of characters in the node by keeping outer of numDelims
                BasedSequence chars = closer.getNode().getChars();
                int length = chars.length();
                closer.getNode().setChars(chars.subSequence(length - closer.getNumDelims(), length));
                closer.setIndex(closer.getIndex() + useDelims);
            }
        }

        // removeIndex all delimiters
        while (lastDelimiter != null && lastDelimiter != stackBottom) {
            removeDelimiterKeepNode(lastDelimiter);
        }
    }

    @Override
    public void removeDelimitersBetween(@NotNull Delimiter opener, @NotNull Delimiter closer) {
        Delimiter delimiter = closer.getPrevious();
        while (delimiter != null && delimiter != opener) {
            Delimiter previousDelimiter = delimiter.getPrevious();
            removeDelimiterKeepNode(delimiter);
            delimiter = previousDelimiter;
        }
    }

    /**
     * Remove the delimiter and the corresponding text node. For used delimiters, e.g. `*` in `*foo*`.
     *
     * @param delim delimiter to remove
     */
    @Override
    public void removeDelimiterAndNode(@NotNull Delimiter delim) {
        Text node = delim.getNode();
        Text previousText = delim.getPreviousNonDelimiterTextNode();
        Text nextText = delim.getNextNonDelimiterTextNode();
        if (previousText != null && nextText != null) {
            // Merge adjacent text nodes
            previousText.setChars(input.baseSubSequence(previousText.getStartOffset(), nextText.getEndOffset()));
            nextText.unlink();
        }

        node.unlink();
        removeDelimiter(delim);
    }

    /**
     * Remove the delimiter but keep the corresponding node as text. For unused delimiters such as `_` in `foo_bar`.
     *
     * @param delim delimiter being processed
     */
    @Override
    public void removeDelimiterKeepNode(@NotNull Delimiter delim) {
        Node node;
        DelimiterProcessor delimiterProcessor = delimiterProcessors.get(delim.getDelimiterChar());
        node = delimiterProcessor != null ? delimiterProcessor.unmatchedDelimiterNode(this, delim) : null;
        if (node != null) {
            if (node != delim.getNode()) {
                // replace node
                delim.getNode().insertAfter(node);
                delim.getNode().unlink();
            }
        } else {
            node = delim.getNode();
        }

        Text previousText = delim.getPreviousNonDelimiterTextNode();
        Text nextText = delim.getNextNonDelimiterTextNode();
        if (node instanceof Text && (previousText != null || nextText != null)) {
            // Merge adjacent text nodes into one
            if (nextText != null && previousText != null) {
                node.setChars(input.baseSubSequence(previousText.getStartOffset(), nextText.getEndOffset()));
                previousText.unlink();
                nextText.unlink();
            } else if (previousText != null) {
                node.setChars(input.baseSubSequence(previousText.getStartOffset(), node.getEndOffset()));
                previousText.unlink();
            } else {
                node.setChars(input.baseSubSequence(node.getStartOffset(), nextText.getEndOffset()));
                nextText.unlink();
            }
        }

        removeDelimiter(delim);
    }

    @Override
    public void removeDelimiter(@NotNull Delimiter delim) {
        if (delim.getPrevious() != null) {
            delim.getPrevious().setNext(delim.getNext());
        }
        if (delim.getNext() == null) {
            // top of stack
            this.lastDelimiter = delim.getPrevious();
        } else {
            delim.getNext().setPrevious(delim.getPrevious());
        }
    }

    static Map<Character, List<InlineParserExtensionFactory>> calculateInlineParserExtensions(DataHolder options, List<InlineParserExtensionFactory> extensionFactories) {
        Map<Character, List<InlineParserExtensionFactory>> extensionMap = new HashMap<>();

        for (InlineParserExtensionFactory factory : extensionFactories) {
            CharSequence chars = factory.getCharacters();
            for (int i = 0; i < chars.length(); i++) {
                char c = chars.charAt(i);
                List<InlineParserExtensionFactory> list = extensionMap.computeIfAbsent(c, k -> new ArrayList<>());
                list.add(factory);
            }
        }

        Map<Character, List<InlineParserExtensionFactory>> extensions = new HashMap<>();
        for (Character c : extensionMap.keySet()) {
            List<InlineParserExtensionFactory> list = extensionMap.get(c);
            List<InlineParserExtensionFactory> resolvedList = DependencyResolver.resolveFlatDependencies(list, null, null);
            extensions.put(c, resolvedList);
        }

        return extensions;
    }

    public static BitSet calculateDelimiterCharacters(DataHolder options, Set<Character> characters) {
        BitSet bitSet = new BitSet();
        for (Character character : characters) {
            bitSet.set(character);
        }
        return bitSet;
    }

    public static BitSet calculateSpecialCharacters(DataHolder options, BitSet delimiterCharacters) {
        BitSet bitSet = new BitSet();
        bitSet.or(delimiterCharacters);
        bitSet.set('\r');
        bitSet.set('\n');
        bitSet.set('`');
        bitSet.set('[');
        bitSet.set(']');
        bitSet.set('\\');
        bitSet.set('!');
        bitSet.set('<');
        bitSet.set('&');
        return bitSet;
    }

    public static Map<Character, DelimiterProcessor> calculateDelimiterProcessors(DataHolder options, List<DelimiterProcessor> delimiterProcessors) {
        Map<Character, DelimiterProcessor> map = new HashMap<>();
        //addDelimiterProcessors(Arrays.asList(new AsteriskDelimiterProcessor(), new UnderscoreDelimiterProcessor()), map);
        if (Parser.ASTERISK_DELIMITER_PROCESSOR.get(options)) {
            addDelimiterProcessors(Collections.singletonList(new AsteriskDelimiterProcessor(Parser.STRONG_WRAPS_EMPHASIS.get(options))), map);
        }
        if (Parser.UNDERSCORE_DELIMITER_PROCESSOR.get(options)) {
            addDelimiterProcessors(Collections.singletonList(new UnderscoreDelimiterProcessor(Parser.STRONG_WRAPS_EMPHASIS.get(options))), map);
        }

        addDelimiterProcessors(delimiterProcessors, map);
        return map;
    }

    // nothing to add, this is for extensions.
    public static LinkRefProcessorData calculateLinkRefProcessors(DataHolder options, List<LinkRefProcessorFactory> linkRefProcessors) {
        if (linkRefProcessors.size() > 1) {
            List<LinkRefProcessorFactory> sortedLinkProcessors = new ArrayList<>(linkRefProcessors.size());
            sortedLinkProcessors.addAll(linkRefProcessors);

            int[] maxNestingLevelRef = new int[] { 0 };

            Collections.sort(sortedLinkProcessors, (p1, p2) -> {
                int lv1 = p1.getBracketNestingLevel(options);
                int lv2 = p2.getBracketNestingLevel(options);
                int maxLevel = maxNestingLevelRef[0];
                if (maxLevel < lv1) maxLevel = lv1;
                if (maxLevel < lv2) maxLevel = lv2;
                maxNestingLevelRef[0] = maxLevel;

                if (lv1 == lv2) {
                    // processors that want exclamation before the [ have higher priority
                    if (!p1.getWantExclamationPrefix(options)) lv1++;
                    if (!p2.getWantExclamationPrefix(options)) lv2++;
                }
                return Integer.compare(lv1, lv2);
            });

            int maxNestingLevel = maxNestingLevelRef[0];

            int maxReferenceLinkNesting = maxNestingLevel;
            int[] nestingLookup = new int[maxNestingLevel + 1];

            maxNestingLevel = -1;
            int index = 0;
            for (LinkRefProcessorFactory linkProcessor : sortedLinkProcessors) {
                if (maxNestingLevel < linkProcessor.getBracketNestingLevel(options)) {
                    maxNestingLevel = linkProcessor.getBracketNestingLevel(options);
                    nestingLookup[maxNestingLevel] = index;
                    if (maxNestingLevel == maxReferenceLinkNesting) break;
                }
                index++;
            }

            return new LinkRefProcessorData(sortedLinkProcessors, maxReferenceLinkNesting, nestingLookup);
        } else if (linkRefProcessors.size() > 0) {
            int maxNesting = linkRefProcessors.get(0).getBracketNestingLevel(options);
            int[] nestingLookup = new int[maxNesting + 1];
            return new LinkRefProcessorData(linkRefProcessors, maxNesting, nestingLookup);
        } else {
            return new LinkRefProcessorData(linkRefProcessors, 0, new int[0]);
        }
    }

    private static void addDelimiterProcessors(List<? extends DelimiterProcessor> delimiterProcessors, Map<Character, DelimiterProcessor> map) {
        for (DelimiterProcessor delimiterProcessor : delimiterProcessors) {
            char opening = delimiterProcessor.getOpeningCharacter();
            addDelimiterProcessorForChar(opening, delimiterProcessor, map);
            char closing = delimiterProcessor.getClosingCharacter();
            if (opening != closing) {
                addDelimiterProcessorForChar(closing, delimiterProcessor, map);
            }
        }
    }

    private static void addDelimiterProcessorForChar(char delimiterChar, DelimiterProcessor toAdd, Map<Character, DelimiterProcessor> delimiterProcessors) {
        DelimiterProcessor existing = delimiterProcessors.put(delimiterChar, toAdd);
        if (existing != null) {
            if (existing.getClass() != toAdd.getClass()) {
                throw new IllegalArgumentException("Delimiter processor conflict with delimiter char '" + delimiterChar + "', existing " + existing.getClass().getCanonicalName() + ", added " + toAdd.getClass().getCanonicalName());
            } else {
                // warning???
                System.out.println("Delimiter processor for char '" + delimiterChar + "', added more than once " + existing.getClass().getCanonicalName());
            }
        }
    }
}
