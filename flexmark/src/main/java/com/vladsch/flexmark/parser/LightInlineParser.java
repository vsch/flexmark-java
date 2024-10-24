package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface LightInlineParser {

  List<BasedSequence> getCurrentText();

  BasedSequence getInput();

  void setInput(BasedSequence input);

  int getIndex();

  void setIndex(int index);

  Node getBlock();

  BasedSequence match(Pattern re);

  BasedSequence[] matchWithGroups(Pattern re);

  Matcher matcher(Pattern re);

  char peek();

  char peek(int ahead);

  boolean flushTextNode();

  Document getDocument();

  void setDocument(Document document);

  InlineParserOptions getOptions();

  Parsing getParsing();

  void appendText(BasedSequence text);

  void appendText(BasedSequence text, int beginIndex, int endIndex);

  void appendNode(Node node);

  // In some cases, we don't want the text to be appended to an existing node, we need it separate

  Text appendSeparateText(BasedSequence text);

  void setBlock(Node block);

  void moveNodes(Node fromNode, Node toNode);

  boolean spnl();

  boolean nonIndentSp();

  boolean sp();

  boolean spnlUrl();

  BasedSequence toEOL();
}
