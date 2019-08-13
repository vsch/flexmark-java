package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.ast.Node;

import java.util.function.Supplier;

public interface CharacterNodeFactory extends Supplier<Node> {
    boolean skipNext(char c);
    boolean skipPrev(char c);
    boolean wantSkippedWhitespace();
}
