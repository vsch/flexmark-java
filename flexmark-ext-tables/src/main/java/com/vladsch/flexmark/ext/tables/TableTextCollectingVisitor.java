package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.util.TextCollectingVisitor;

public class TableTextCollectingVisitor extends TextCollectingVisitor {
    public static final Class[] TABLE_LINE_BREAK_CLASSES = { TableBlock.class, TableRow.class, TableCaption.class };

    public TableTextCollectingVisitor(final Class... lineBreakNodes) {
        super(lineBreakNodes.length == 0 ? TABLE_LINE_BREAK_CLASSES : concatArrays(TABLE_LINE_BREAK_CLASSES, lineBreakNodes));
    }
}
