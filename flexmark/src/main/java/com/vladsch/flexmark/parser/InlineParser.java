package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.core.delimiter.Bracket;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.parser.block.CharacterNodeFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for inline content (text, links, emphasized text, etc).
 * <p><em>This interface is not intended to be implemented by clients.</em></p>
 */
public interface InlineParser {

    void initializeDocument(Parsing parsing, Document document);
    void finalizeDocument(Document document);

    /**
     * @param input the content to parse as inline
     * @param node the node to append resulting nodes to (as children)
     */
    void parse(BasedSequence input, Node node);

    BasedSequence getInput();
    int getIndex();
    void setIndex(int index);
    Delimiter getLastDelimiter();
    Bracket getLastBracket();
    Document getDocument();
    InlineParserOptions getOptions();
    Parsing getParsing();
    Node getBlock();
    List<Node> parseCustom(BasedSequence input, Node node, BitSet customCharacters, Map<Character, CharacterNodeFactory> nodeFactoryMap);
    void mergeTextNodes(Node fromNode, Node toNode);
    void mergeIfNeeded(Text first, Text last);
    void moveNodes(Node fromNode, Node toNode);
    void appendText(BasedSequence text, int beginIndex, int endIndex);
    void appendNode(Node node);
    // In some cases, we don't want the text to be appended to an existing node, we need it separate
    Text appendSeparateText(BasedSequence text);
    boolean flushTextNode();
    BasedSequence match(Pattern re);
    BasedSequence[] matchWithGroups(Pattern re);
    Matcher matcher(Pattern re);
    char peek();
    char peek(int ahead);
    boolean spnl();
    boolean nonIndentSp();
    boolean sp();
    boolean spnlUrl();
    BasedSequence toEOL();
    boolean parseNewline();
    BasedSequence parseLinkDestination();
    BasedSequence parseLinkTitle();
    int parseLinkLabel();
    boolean parseAutolink();
    boolean parseHtmlInline();
    boolean parseEntity();
    void processDelimiters(Delimiter stackBottom);
    void removeDelimitersBetween(Delimiter opener, Delimiter closer);
    void removeDelimiterAndNode(Delimiter delim);
    void removeDelimiterKeepNode(Delimiter delim);
    void removeDelimiter(Delimiter delim);
}
