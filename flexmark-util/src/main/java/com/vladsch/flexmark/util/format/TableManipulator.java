package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.ast.Node;

public interface TableManipulator {
    void apply(MarkdownTable table, Node tableNoe);

    TableManipulator NULL = (table, tableNoe) -> {

    };
}
