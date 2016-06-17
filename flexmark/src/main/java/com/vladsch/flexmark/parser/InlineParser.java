package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

/**
 * Parser for inline content (text, links, emphasized text, etc).
 * <p><em>This interface is not intended to be implemented by clients.</em></p>
 */
public interface InlineParser {

    void initializeDocument(Document document);
    void finalizeDocument(Document document);

    /**
     * @param input the content to parse as inline
     * @param node the node to append resulting nodes to (as children)
     */
    void parse(BasedSequence input, Node node);

    List<Node> parseCustom(BasedSequence input, Node node, BitSet customCharacters, Map<Character, CharacterNodeFactory> nodeFactoryMap);
}
