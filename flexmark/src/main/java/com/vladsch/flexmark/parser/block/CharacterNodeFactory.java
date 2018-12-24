package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.Factory;

public interface CharacterNodeFactory extends Factory<Node> {
    boolean skipNext(char c);
    boolean skipPrev(char c);
    boolean wantSkippedWhitespace();
}
