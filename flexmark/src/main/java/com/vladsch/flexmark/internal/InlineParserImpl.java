package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.inline.AsteriskDelimiterProcessor;
import com.vladsch.flexmark.internal.inline.UnderscoreDelimiterProcessor;
import com.vladsch.flexmark.internal.util.*;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.BlockPreProcessor;
import com.vladsch.flexmark.parser.DelimiterProcessor;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.ParserState;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InlineParserImpl implements InlineParser, BlockPreProcessor {

    protected static final String ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
    protected static final String REG_CHAR = "[^\\\\()\\x00-\\x20]";
    protected static final String IN_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
    protected static final String HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
    protected static final String PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
    protected static final String DECLARATION = "<![A-Z]+" + "\\s+[^>]*>";
    protected static final String CDATA = "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
    protected static final String HTMLTAG = "(?:" + Parsing.OPENTAG + "|" + Parsing.CLOSETAG + "|" + HTMLCOMMENT
            + "|" + PROCESSINGINSTRUCTION + "|" + DECLARATION + "|" + CDATA + ")";
    protected static final String ENTITY = "&(?:#x[a-f0-9]{1,8}|#[0-9]{1,8}|[a-z][a-z0-9]{1,31});";

    protected static final String ASCII_PUNCTUATION = "'!\"#\\$%&\\(\\)\\*\\+,\\-\\./:;<=>\\?@\\[\\\\\\]\\^_`\\{\\|\\}~";
    protected static final Pattern PUNCTUATION = Pattern
            .compile("^[" + ASCII_PUNCTUATION + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]");

    protected static final Pattern HTML_TAG = Pattern.compile('^' + HTMLTAG, Pattern.CASE_INSENSITIVE);

    protected static final Pattern LINK_TITLE = Pattern.compile(
            "^(?:\"(" + ESCAPED_CHAR + "|[^\"\\x00])*\"" +
                    '|' +
                    "'(" + ESCAPED_CHAR + "|[^'\\x00])*'" +
                    '|' +
                    "\\((" + ESCAPED_CHAR + "|[^)\\x00])*\\))");

    protected static final Pattern LINK_DESTINATION_BRACES = Pattern.compile(
            "^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ESCAPED_CHAR + '|' + "\\\\)*[>])");

    protected static final Pattern LINK_DESTINATION = Pattern.compile(
            "^(?:" + REG_CHAR + "+|" + ESCAPED_CHAR + "|\\\\|" + IN_PARENS_NOSP + ")*");

    protected static final Pattern LINK_LABEL = Pattern
            .compile("^\\[(?:[^\\\\\\[\\]]|" + ESCAPED_CHAR + "|\\\\){0,1000}\\]");

    protected static final Pattern ESCAPABLE = Pattern.compile('^' + Escaping.ESCAPABLE);

    protected static final Pattern ENTITY_HERE = Pattern.compile('^' + ENTITY, Pattern.CASE_INSENSITIVE);

    protected static final Pattern TICKS = Pattern.compile("`+");

    protected static final Pattern TICKS_HERE = Pattern.compile("^`+");

    protected static final Pattern EMAIL_AUTOLINK = Pattern
            .compile("^<([a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*)>");

    protected static final Pattern AUTOLINK = Pattern
            .compile("^<[a-zA-Z][a-zA-Z0-9.+-]{1,31}:[^<>\u0000-\u0020]*>");

    protected static final Pattern SPNL = Pattern.compile("^ *(?:\n *)?");

    protected static final Pattern UNICODE_WHITESPACE_CHAR = Pattern.compile("^[\\p{Zs}\t\r\n\f]");

    protected static final Pattern WHITESPACE = Pattern.compile("\\s+");

    protected static final Pattern FINAL_SPACE = Pattern.compile(" *$");

    protected static final Pattern LINE_END = Pattern.compile("^ *(?:\n|$)");

    protected final BitSet specialCharacters;
    protected final BitSet delimiterCharacters;
    protected final Map<Character, DelimiterProcessor> delimiterProcessors;

    /**
     * Link references by ID, needs to be built up using parseReference before calling parse.
     */
    protected ReferenceRepository referenceRepository;

    protected Node block;

    protected BasedSequence input;
    protected int index;

    /**
     * Stack of delimiters (emphasis, strong emphasis).
     */
    protected Delimiter delimiter;

    /**
     * Earliest possible bracket delimiter to go back to when searching for opener.
     */
    protected Delimiter bracketDelimiterBottom = null;

    protected ArrayList<BasedSequence> currentText;

    protected Document document;
    final protected Options options;

    @Override
    public void setDocument(Document document) {
        this.document = document;
        this.referenceRepository = document.getOrNew(ReferenceRepository.PROPERTY_KEY);
    }

    public ArrayList<BasedSequence> getCurrentText() {
        if (currentText == null) {
            currentText = new ArrayList<>();
        }

        return currentText;
    }

    public InlineParserImpl(Options options, BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors) {
        this.options = options;
        this.delimiterProcessors = delimiterProcessors;
        this.delimiterCharacters = delimiterCharacters;
        this.specialCharacters = specialCharacters;
    }

    public static BitSet calculateDelimiterCharacters(Options options, Set<Character> characters) {
        BitSet bitSet = new BitSet();
        for (Character character : characters) {
            bitSet.set(character);
        }
        return bitSet;
    }

    public static BitSet calculateSpecialCharacters(Options options, BitSet delimiterCharacters) {
        BitSet bitSet = new BitSet();
        bitSet.or(delimiterCharacters);
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

    public static Map<Character, DelimiterProcessor> calculateDelimiterProcessors(Options options, List<DelimiterProcessor> delimiterProcessors) {
        Map<Character, DelimiterProcessor> map = new HashMap<>();
        addDelimiterProcessors(Arrays.<DelimiterProcessor>asList(new AsteriskDelimiterProcessor(), new UnderscoreDelimiterProcessor()), map);
        addDelimiterProcessors(delimiterProcessors, map);
        return map;
    }

    private static void addDelimiterProcessors(Iterable<DelimiterProcessor> delimiterProcessors, Map<Character, DelimiterProcessor> map) {
        for (DelimiterProcessor delimiterProcessor : delimiterProcessors) {
            char opening = delimiterProcessor.getOpeningDelimiterChar();
            addDelimiterProcessorForChar(opening, delimiterProcessor, map);
            char closing = delimiterProcessor.getClosingDelimiterChar();
            if (opening != closing) {
                addDelimiterProcessorForChar(closing, delimiterProcessor, map);
            }
        }
    }

    private static void addDelimiterProcessorForChar(char delimiterChar, DelimiterProcessor toAdd, Map<Character, DelimiterProcessor> delimiterProcessors) {
        DelimiterProcessor existing = delimiterProcessors.put(delimiterChar, toAdd);
        if (existing != null) {
            throw new IllegalArgumentException("Delimiter processor conflict with delimiter char '" + delimiterChar + "'");
        }
    }

    /**
     * Parse content in block into inline children, using reference map to resolve references.
     */
    @Override
    public void parse(BasedSequence content, Node block) {
        this.block = block;
        this.input = content.trim();
        this.index = 0;
        this.delimiter = null;
        this.bracketDelimiterBottom = null;

        boolean moreToParse;
        do {
            moreToParse = parseInline();
        } while (moreToParse);
        flushTextNode();

        processDelimiters(null);
    }

    @Override
    public void preProcessBlock(Block block, ParserState state) {
        BasedSequence contentChars = block.getChars();

        // try parsing the beginning as link reference definitions:
        while (contentChars.length() > 3 && contentChars.charAt(0) == '[') {
            int pos = parseReference(block, contentChars);
            if (pos == 0) break;
            contentChars = contentChars.subSequence(pos, contentChars.length());
        }

        block.setChars(contentChars);
    }

    /**
     * Attempt to parse a link reference, modifying the internal reference map.
     *
     * @return how many characters were parsed as a reference, {@code 0} if none
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
        if (index != input.length() && match(LINE_END) == null) {
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
                atLineEnd = match(LINE_END) != null;
            }
        }

        if (!atLineEnd) {
            return 0;
        }

        String normalizedLabel = Escaping.normalizeReference(rawLabel);
        if (normalizedLabel.isEmpty()) {
            return 0;
        }

        Reference reference = new Reference(rawLabel, dest, title);

        // NOTE: whether first or last reference is kept is defined by the repository modify behavior setting
        // for CommonMark this is set in the setDocument() function of the inline parser
        referenceRepository.putRawKey(normalizedLabel, reference);

        block.insertBefore(reference);

        return index - startIndex;
    }

    protected void appendText(BasedSequence text) {
        getCurrentText().add(text);
    }

    protected void appendText(BasedSequence text, int beginIndex, int endIndex) {
        getCurrentText().add(text.subSequence(beginIndex, endIndex));
    }

    protected void appendNode(Node node) {
        flushTextNode();
        block.appendChild(node);
    }

    // In some cases, we don't want the text to be appended to an existing node, we need it separate
    protected Text appendSeparateText(BasedSequence text) {
        Text node = new Text(text);
        appendNode(node);
        return node;
    }

    protected void flushTextNode() {
        if (currentText != null) {
            block.appendChild(new Text(SegmentedSequence.of(currentText, SubSequence.NULL)));
            currentText = null;
        }
    }

    /**
     * Parse the next inline element in subject, advancing input index.
     * On success, add the result to block's children and return true.
     * On failure, return false.
     */
    protected boolean parseInline() {
        boolean res;
        char c = peek();
        if (c == '\0') {
            return false;
        }

        switch (c) {
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
            case '<':
                res = parseAutolink() || parseHtmlInline();
                break;
            case '&':
                res = parseEntity();
                break;
            default:
                boolean isDelimiter = delimiterCharacters.get(c);
                if (isDelimiter) {
                    DelimiterProcessor delimiterProcessor = delimiterProcessors.get(c);
                    res = parseDelimiters(delimiterProcessor, c);
                } else {
                    res = parseString();
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

    /**
     * If RE matches at current index in the input, advance index and return the match; otherwise return null.
     */
    protected BasedSequence match(Pattern re) {
        if (index >= input.length()) {
            return null;
        }
        Matcher matcher = re.matcher(input);
        matcher.region(index, input.length());
        boolean m = matcher.find();
        if (m) {
            index = matcher.end();
            MatchResult result = matcher.toMatchResult();
            return input.subSequence(result.start(), result.end());
        } else {
            return null;
        }
    }

    /**
     * Returns the char at the current input index, or {@code '\0'} in case there are no more characters.
     */
    protected char peek() {
        if (index < input.length()) {
            return input.charAt(index);
        } else {
            return '\0';
        }
    }

    /**
     * Parse zero or more space characters, including at most one newline.
     */
    private boolean spnl() {
        match(SPNL);
        return true;
    }

    /**
     * Parse a newline. If it was preceded by two spaces, return a hard line break; otherwise a soft line break.
     */
    protected boolean parseNewline() {
        index++; // assume we're at a \n

        // We're gonna add a new node in any case and we need to check the last text node, so flush outstanding text.
        flushTextNode();

        Node lastChild = block.getLastChild();
        // Check previous text for trailing spaces.
        // The "endsWith" is an optimization to avoid an RE match in the common case.
        if (lastChild != null && lastChild instanceof Text && lastChild.getChars().endsWith(" ")) {
            Text text = (Text) lastChild;
            BasedSequence literal = text.getChars();
            Matcher matcher = FINAL_SPACE.matcher(literal);
            int spaces = matcher.find() ? matcher.end() - matcher.start() : 0;
            appendNode(spaces >= 2 ? new HardLineBreak(input.subSequence(index - 3, index)) : new SoftLineBreak(input.subSequence(index - 1, index)));
            if (spaces > 0) {
                if (literal.length() > spaces) {
                    lastChild.setChars(literal.subSequence(0, literal.length() - spaces).trimEnd());
                } else {
                    lastChild.unlink();
                }
            }
        } else {
            appendNode(new SoftLineBreak(input.subSequence(index - 1, index)));
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
     */
    protected boolean parseBackslash() {
        index++;
        if (peek() == '\n') {
            appendNode(new HardLineBreak(input.subSequence(index, index + 1)));
            index++;
        } else if (index < input.length() && ESCAPABLE.matcher(input.subSequence(index, index + 1)).matches()) {
            appendText(input, index - 1, index + 1);
            index++;
        } else {
            appendText(input.subSequence(index - 1, index));
        }
        return true;
    }

    /**
     * Attempt to parse backticks, adding either a backtick code span or a literal sequence of backticks.
     */
    protected boolean parseBackticks() {
        BasedSequence ticks = match(TICKS_HERE);
        if (ticks == null) {
            return false;
        }
        int afterOpenTicks = index;
        BasedSequence matched;
        while ((matched = match(TICKS)) != null) {
            if (matched.equals(ticks)) {
                int ticksLength = ticks.length();
                BasedSequence content = input.subSequence(afterOpenTicks - ticksLength, index - ticksLength);
                Code node = new Code(input.subSequence(afterOpenTicks - ticksLength, afterOpenTicks), input.subSequence(afterOpenTicks, index - ticksLength), input.subSequence(index - ticksLength, index));
                appendNode(node);
                return true;
            }
        }

        // If we got here, we didn't match a closing backtick sequence.
        index = afterOpenTicks;
        appendText(ticks);
        return true;
    }

    /**
     * Attempt to parse delimiters like emphasis, strong emphasis or custom delimiters.
     */
    protected boolean parseDelimiters(DelimiterProcessor delimiterProcessor, char delimiterChar) {
        DelimiterRun res = scanDelimiters(delimiterProcessor, delimiterChar);
        if (res == null) {
            return false;
        }
        int numDelims = res.count;
        int startIndex = index;

        index += numDelims;
        Text node = appendSeparateText(input.subSequence(startIndex, index));

        // Add entry to stack for this opener
        this.delimiter = new Delimiter(input, node, this.delimiter, startIndex);
        this.delimiter.delimiterChar = delimiterChar;
        this.delimiter.numDelims = numDelims;
        this.delimiter.canOpen = res.canOpen;
        this.delimiter.canClose = res.canClose;
        if (this.delimiter.previous != null) {
            this.delimiter.previous.next = this.delimiter;
        }

        return true;
    }

    /**
     * Add open bracket to delimiter stack and add a text node to block's children.
     */
    protected boolean parseOpenBracket() {
        int startIndex = index;
        index++;

        Text node = appendSeparateText(input.subSequence(index - 1, index));

        // Add entry to stack for this opener
        this.delimiter = new Delimiter(input, node, this.delimiter, startIndex);
        this.delimiter.delimiterChar = '[';
        this.delimiter.numDelims = 1;
        this.delimiter.canOpen = true;
        this.delimiter.canClose = false;
        this.delimiter.allowed = true;
        if (this.delimiter.previous != null) {
            this.delimiter.previous.next = this.delimiter;
        }

        return true;
    }

    /**
     * If next character is [, and ! delimiter to delimiter stack and add a text node to block's children.
     * Otherwise just add a text node.
     */
    protected boolean parseBang() {
        int startIndex = index;
        index++;
        if (peek() == '[') {
            index++;

            Text node = appendSeparateText(input.subSequence(index - 2, index));

            // Add entry to stack for this opener
            this.delimiter = new Delimiter(input, node, this.delimiter, startIndex + 1);
            this.delimiter.delimiterChar = '!';
            this.delimiter.numDelims = 1;
            this.delimiter.canOpen = true;
            this.delimiter.canClose = false;
            this.delimiter.allowed = true;
            if (this.delimiter.previous != null) {
                this.delimiter.previous.next = this.delimiter;
            }
        } else {
            appendText(input.subSequence(index - 1, index));
        }
        return true;
    }

    /**
     * Try to match close bracket against an opening in the delimiter stack. Add either a link or image, or a
     * plain [ character, to block's children. If there is a matching delimiter, remove it from the delimiter stack.
     */
    protected boolean parseCloseBracket() {
        index++;
        int startIndex = index;

        boolean containsBracket = false;
        // look through stack of delimiters for a [ or ![
        Delimiter opener = this.delimiter;
        while (opener != bracketDelimiterBottom) {
            if (opener.delimiterChar == '[' || opener.delimiterChar == '!') {
                if (!opener.matched) {
                    break;
                }
                containsBracket = true;
            }
            opener = opener.previous;
        }

        if (opener == bracketDelimiterBottom) {
            // No matched opener, just return a literal.
            appendText(input.subSequence(index - 1, index));
            // No need to search same delimiters for openers next time.
            bracketDelimiterBottom = this.delimiter;
            return true;
        }

        if (!opener.allowed) {
            // Matching opener but it's not allowed, just return a literal.
            appendText(input.subSequence(index - 1, index));

            // We could remove the opener now, but that would complicate text node merging. So just skip it next time.
            opener.matched = true;
            return true;
        }

        // Check to see if we have a link/image
        BasedSequence dest = null;
        BasedSequence title = null;
        BasedSequence ref = null;
        boolean isLinkOrImage = false;
        boolean refIsBare = false;
        BasedSequence linkOpeningMarker = null;
        BasedSequence linkClosingMarker = null;
        String label = null;
        Reference reference = null;

        // Inline link?
        if (peek() == '(') {
            index++;
            linkOpeningMarker = input.subSequence(index - 1, index);
            spnl();
            if ((dest = parseLinkDestination()) != null) {
                spnl();
                // title needs a whitespace before
                if (WHITESPACE.matcher(input.subSequence(index - 1, index)).matches()) {
                    title = parseLinkTitle();
                    spnl();
                }
                if (peek() == ')') {
                    index++;
                    linkClosingMarker = input.subSequence(index - 1, index);
                    isLinkOrImage = true;
                }
            }
        } else { // maybe reference link
            // See if there's a link label
            int beforeLabel = index;
            int labelLength = parseLinkLabel();
            if (labelLength > 2) {
                ref = input.subSequence(beforeLabel, beforeLabel + labelLength);
            } else if (!containsBracket) {
                // Empty or missing second label can only be a reference if there's no unescaped bracket in it.
                ref = input.subSequence(opener.index, startIndex);
                refIsBare = true;
            }

            if (ref != null) {
                String normalizedLabel = Escaping.normalizeReference(ref);
                if (referenceRepository.containsKey(normalizedLabel)) {
                    reference = referenceRepository.get(normalizedLabel);
                    isLinkOrImage = true;
                } else if (opener.previous == null) {
                    // it is the outermost ref and is bare, if not bare then we treat
                    if (!refIsBare && peek() == '[') {
                        int beforeNext = index;
                        int nextLength = parseLinkLabel();
                        if (nextLength > 0) {
                            // not bare and not defined and followed by another [], roll back to before the label and make it just text
                            index = beforeLabel;
                        } else {
                            ref = input.subSequence(opener.index, startIndex);
                            refIsBare = true;
                            isLinkOrImage = true;
                        }
                    } else {
                        isLinkOrImage = true;
                    }
                }
            }
        }

        if (isLinkOrImage) {
            // If we got here, open is a potential opener
            boolean isImage = opener.delimiterChar == '!';
            Node linkOrImage = ref != null ? isImage ? new ImageRef() : new LinkRef() : isImage ? new Image() : new Link();

            // Flush text now. We don't need to worry about combining it with adjacent text nodes, as we'll wrap it in a
            // link or image node.
            flushTextNode();

            Node node = opener.node.getNext();
            while (node != null) {
                Node next = node.getNext();
                linkOrImage.appendChild(node);
                node = next;
            }
            appendNode(linkOrImage);

            if (linkOrImage instanceof RefNode) {
                // set up the parts
                RefNode refNode = (RefNode) linkOrImage;
                refNode.setReferenceChars(ref);

                if (!refIsBare) {
                    refNode.setTextChars(input.subSequence(opener.index, startIndex));
                }
            } else {
                // set dest and title
                InlineLinkNode inlineLinkNode = (InlineLinkNode) linkOrImage;
                inlineLinkNode.setUrlChars(dest);
                inlineLinkNode.setTitleChars(title);
                inlineLinkNode.setTextChars(input.subSequence(opener.index, startIndex));
            }
            linkOrImage.setCharsFromContent();

            // Process delimiters such as emphasis inside link/image
            processDelimiters(opener);
            removeDelimiterAndNode(opener);

            // Links within links are not allowed. We found this link, so there can be no other link around it.
            if (!isImage) {
                Delimiter delim = this.delimiter;
                while (delim != null) {
                    if (delim.delimiterChar == '[') {
                        // Disallow link opener. It will still get matched, but will not result in a link.
                        delim.allowed = false;
                    }
                    delim = delim.previous;
                }
            }

            return true;
        } else { // no link or image
            index = startIndex;
            appendText(input.subSequence(index - 1, index));
            // We could remove the opener now, but that would complicate text node merging.
            // E.g. `[link] (/uri)` isn't a link because of the space, so we want to keep appending text.
            opener.matched = true;
            return true;
        }
    }

    /**
     * Attempt to parse link destination, returning the string or null if no match.
     */
    protected BasedSequence parseLinkDestination() {
        BasedSequence res = match(LINK_DESTINATION_BRACES);
        if (res != null) { // chop off surrounding <..>:
            if (res.length() == 2) {
                return res;
            } else {
                return res;
            }
        } else {
            res = match(LINK_DESTINATION);
            if (res != null) {
                return res;
            } else {
                return null;
            }
        }
    }

    /**
     * Attempt to parse link title (sans quotes), returning the string or null if no match.
     */
    protected BasedSequence parseLinkTitle() {
        BasedSequence title = match(LINK_TITLE);
        if (title != null) {
            // chop off quotes from title and unescape:
            return title; //Escaping.unescapeString(title.substring(1, title.length() - 1));
        } else {
            return null;
        }
    }

    /**
     * Attempt to parse a link label, returning number of characters parsed.
     */
    protected int parseLinkLabel() {
        BasedSequence m = match(LINK_LABEL);
        return m == null ? 0 : m.length();
    }

    /**
     * Attempt to parse an autolink (URL or email in pointy brackets).
     */
    protected boolean parseAutolink() {
        BasedSequence m;
        if ((m = match(EMAIL_AUTOLINK)) != null) {
            MailLink node = new MailLink(m.subSequence(0, 1), m.subSequence(1, m.length() - 1), m.subSequence(m.length() - 1, m.length()));
            appendNode(node);
            return true;
        } else if ((m = match(AUTOLINK)) != null) {
            AutoLink node = new AutoLink(m.subSequence(0, 1), m.subSequence(1, m.length() - 1), m.subSequence(m.length() - 1, m.length()));
            appendNode(node);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempt to parse inline HTML.
     */
    protected boolean parseHtmlInline() {
        BasedSequence m = match(HTML_TAG);
        if (m != null) {
            HtmlInline node = new HtmlInline(m);
            appendNode(node);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempt to parse an entity, return Entity object if successful.
     */
    protected boolean parseEntity() {
        BasedSequence m;
        if ((m = match(ENTITY_HERE)) != null) {
            HtmlEntity node = new HtmlEntity(m);
            appendNode(node);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parse a run of ordinary characters, or a single character with a special meaning in markdown, as a plain string.
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
     * @return information about delimiter run, or {@code null}
     */
    DelimiterRun scanDelimiters(DelimiterProcessor delimiterProcessor, char delimiterChar) {
        int startIndex = index;

        int delimiterCount = 0;
        while (peek() == delimiterChar) {
            delimiterCount++;
            index++;
        }

        if (delimiterCount < delimiterProcessor.getMinDelimiterCount()) {
            index = startIndex;
            return null;
        }

        String before = startIndex == 0 ? "\n" : String.valueOf(input.charAt(startIndex - 1));

        char charAfter = peek();
        String after = charAfter == '\0' ? "\n" : String.valueOf(charAfter);

        // We could be more lazy here, in most cases we don't need to do every match case.
        boolean beforeIsPunctuation = PUNCTUATION.matcher(before).matches();
        boolean beforeIsWhitespace = UNICODE_WHITESPACE_CHAR.matcher(before).matches();
        boolean afterIsPunctuation = PUNCTUATION.matcher(after).matches();
        boolean afterIsWhitespace = UNICODE_WHITESPACE_CHAR.matcher(after).matches();

        boolean leftFlanking = !afterIsWhitespace &&
                !(afterIsPunctuation && !beforeIsWhitespace && !beforeIsPunctuation);
        boolean rightFlanking = !beforeIsWhitespace &&
                !(beforeIsPunctuation && !afterIsWhitespace && !afterIsPunctuation);

        boolean canOpen;
        boolean canClose;

        if (delimiterChar == '_') {
            canOpen = leftFlanking && (!rightFlanking || beforeIsPunctuation);
            canClose = rightFlanking && (!leftFlanking || afterIsPunctuation);
        } else {
            canOpen = leftFlanking && delimiterChar == delimiterProcessor.getOpeningDelimiterChar();
            canClose = rightFlanking && delimiterChar == delimiterProcessor.getClosingDelimiterChar();
        }

        index = startIndex;
        return new DelimiterRun(delimiterCount, canOpen, canClose);
    }

    protected void processDelimiters(Delimiter stackBottom) {
        Map<Character, Delimiter> openersBottom = new HashMap<>();

        // find first closer above stackBottom:
        Delimiter closer = this.delimiter;
        while (closer != null && closer.previous != stackBottom) {
            closer = closer.previous;
        }

        // move forward, looking for closers, and handling each
        while (closer != null) {
            char delimiterChar = closer.delimiterChar;

            DelimiterProcessor delimiterProcessor = delimiterProcessors.get(delimiterChar);
            if (!closer.canClose || delimiterProcessor == null) {
                closer = closer.next;
                continue;
            }

            char openingDelimiterChar = delimiterProcessor.getOpeningDelimiterChar();

            // found delimiter closer. now look back for first matching opener:
            boolean openerFound = false;
            Delimiter opener = closer.previous;
            while (opener != null && opener != stackBottom && opener != openersBottom.get(delimiterChar)) {
                if (opener.delimiterChar == openingDelimiterChar && opener.canOpen) {
                    openerFound = true;
                    break;
                }
                opener = opener.previous;
            }

            if (!openerFound) {
                // Set lower bound for future searches for openers:
                openersBottom.put(delimiterChar, closer.previous);
                if (!closer.canOpen) {
                    // We can remove a closer that can't be an opener,
                    // once we've seen there's no matching opener:
                    removeDelimiterKeepNode(closer);
                }
                closer = closer.next;
                continue;
            }

            int useDelims = delimiterProcessor.getDelimiterUse(opener.numDelims, closer.numDelims);
            if (useDelims <= 0) {
                // nope
                useDelims = 1;
            }

            Text openerNode = opener.node;
            Text closerNode = closer.node;

            // remove used delimiters from stack elts and inlines
            opener.numDelims -= useDelims;
            closer.numDelims -= useDelims;

            removeDelimitersBetween(opener, closer);

            opener.numDelims += useDelims;
            closer.numDelims += useDelims;

            delimiterProcessor.process(opener, closer, useDelims);

            opener.numDelims -= useDelims;
            closer.numDelims -= useDelims;

            // if opener has 0 delims, remove it and the inline
            if (opener.numDelims == 0) {
                removeDelimiterAndNode(opener);
            } else {
                // adjust number of characters in the node by keeping outer of numDelims
                opener.node.setChars(opener.node.getChars().subSequence(0, opener.numDelims));
            }

            if (closer.numDelims == 0) {
                Delimiter next = closer.next;
                removeDelimiterAndNode(closer);
                closer = next;
            } else {
                // adjust number of characters in the node by keeping outer of numDelims
                BasedSequence chars = closer.node.getChars();
                int length = chars.length();
                closer.node.setChars(chars.subSequence(length - closer.numDelims, length));
            }
        }

        // remove all delimiters
        while (delimiter != null && delimiter != stackBottom) {
            removeDelimiterKeepNode(delimiter);
        }
    }

    protected void removeDelimitersBetween(Delimiter opener, Delimiter closer) {
        Delimiter delimiter = closer.previous;
        while (delimiter != null && delimiter != opener) {
            Delimiter previousDelimiter = delimiter.previous;
            removeDelimiterKeepNode(delimiter);
            delimiter = previousDelimiter;
        }
    }

    /**
     * Remove the delimiter and the corresponding text node. For used delimiters, e.g. `*` in `*foo*`.
     */
    protected void removeDelimiterAndNode(Delimiter delim) {
        Text node = delim.node;
        Text previousText = delim.getPreviousNonDelimiterTextNode();
        Text nextText = delim.getNextNonDelimiterTextNode();
        if (previousText != null && nextText != null) {
            // Merge adjacent text nodes
            previousText.setChars(input.subSequence(previousText.getStartOffset(), nextText.getEndOffset()));
            nextText.unlink();
        }

        node.unlink();
        removeDelimiter(delim);
    }

    /**
     * Remove the delimiter but keep the corresponding node as text. For unused delimiters such as `_` in `foo_bar`.
     */
    protected void removeDelimiterKeepNode(Delimiter delim) {
        Text node = delim.node;
        Text previousText = delim.getPreviousNonDelimiterTextNode();
        Text nextText = delim.getNextNonDelimiterTextNode();
        if (previousText != null || nextText != null) {
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

    protected void removeDelimiter(Delimiter delim) {
        if (delim.previous != null) {
            delim.previous.next = delim.next;
        }
        if (delim.next == null) {
            // top of stack
            this.delimiter = delim.previous;
        } else {
            delim.next.previous = delim.previous;
        }
    }
}
