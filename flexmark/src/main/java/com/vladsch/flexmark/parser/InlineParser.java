package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.block.CharacterNodeFactory;
import com.vladsch.flexmark.parser.core.delimiter.Bracket;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

/**
 * Parser for inline content (text, links, emphasized text, etc).
 * <p><em>This interface is not intended to be implemented by clients.</em></p>
 */
public interface InlineParser extends LightInlineParser {

    void initializeDocument(@NotNull Document document);
    void finalizeDocument(@NotNull Document document);

    /**
     * @param input the content to parse as inline
     * @param node  the node to append resulting nodes to (as children)
     */
    void parse(@NotNull BasedSequence input, @NotNull Node node);

    @Nullable Delimiter getLastDelimiter();
    @Nullable Bracket getLastBracket();
    @Nullable List<Node> parseCustom(@NotNull BasedSequence input, @NotNull Node node, @NotNull BitSet customCharacters, @NotNull Map<Character, CharacterNodeFactory> nodeFactoryMap);
    void mergeTextNodes(@Nullable Node fromNode, @Nullable Node toNode);
    void mergeIfNeeded(@Nullable Text first, @Nullable Text last);
    @Nullable BasedSequence toEOL();
    boolean parseNewline();
    @Nullable BasedSequence parseLinkDestination();
    @Nullable BasedSequence parseLinkTitle();
    int parseLinkLabel();
    boolean parseAutolink();
    boolean parseHtmlInline();
    boolean parseEntity();
    void processDelimiters(@Nullable Delimiter stackBottom);
    void removeDelimitersBetween(@NotNull Delimiter opener, @NotNull Delimiter closer);
    void removeDelimiterAndNode(@NotNull Delimiter delim);
    void removeDelimiterKeepNode(@NotNull Delimiter delim);
    void removeDelimiter(@NotNull Delimiter delim);
}
