package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.block.CharacterNodeFactory;
import com.vladsch.flexmark.parser.core.delimiter.Bracket;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

/**
 * Parser for inline content (text, links, emphasized text, etc).
 *
 * <p><em>This interface is not intended to be implemented by clients.</em>
 */
public interface InlineParser extends LightInlineParser {
  void initializeDocument(Document document);

  void finalizeDocument(Document document);

  /**
   * @param input the content to parse as inline
   * @param node the node to append resulting nodes to (as children)
   */
  void parse(BasedSequence input, Node node);

  Delimiter getLastDelimiter();

  Bracket getLastBracket();

  List<Node> parseCustom(
      BasedSequence input,
      Node node,
      BitSet customCharacters,
      Map<Character, CharacterNodeFactory> nodeFactoryMap);

  void mergeTextNodes(Node fromNode, Node toNode);

  void mergeIfNeeded(Text first, Text last);

  @Override
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
