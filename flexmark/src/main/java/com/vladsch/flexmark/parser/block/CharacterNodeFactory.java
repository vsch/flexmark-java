package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.Factory;
import com.vladsch.flexmark.util.ast.Node;

public interface CharacterNodeFactory extends Factory<Node> {
    boolean skipNext(char c);
    boolean skipPrev(char c);
    boolean wantSkippedWhitespace();
}
