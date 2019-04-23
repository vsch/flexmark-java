package com.vladsch.flexmark.parser.block;

import java.util.function.Supplier;
import com.vladsch.flexmark.util.ast.Node;

public interface CharacterNodeFactory extends Supplier<Node> {
    boolean skipNext(char c);
    boolean skipPrev(char c);
    boolean wantSkippedWhitespace();
}
