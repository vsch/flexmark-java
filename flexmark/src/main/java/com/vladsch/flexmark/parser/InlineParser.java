package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.block.CharacterNodeFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

/**
 * Parser for inline content (text, links, emphasized text, etc).
 * <p><em>This interface is not intended to be implemented by clients.</em></p>
 */
public interface InlineParser {

    void initializeDocument(Parsing parsing, Document document);
    void finalizeDocument(Document document);

    /**
     * @param input the content to parse as inline
     * @param node the ast to append resulting nodes to (as children)
     */
    void parse(BasedSequence input, Node node);

    List<Node> parseCustom(BasedSequence input, Node node, BitSet customCharacters, Map<Character, CharacterNodeFactory> nodeFactoryMap);
}
