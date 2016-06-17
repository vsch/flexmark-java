package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.Factory;
import com.vladsch.flexmark.node.Node;

public interface CharacterNodeFactory extends Factory<Node> {
    boolean skipNext(char c);
    boolean skipPrev(char c);
    boolean wantSkippedWhitespace();
}
