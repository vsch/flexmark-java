package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.util.TextCollectingVisitor;

/**
 * @deprecated use {@link com.vladsch.flexmark.util.ast.TextCollectingVisitor} from the utils library
 */
@Deprecated
public class TableTextCollectingVisitor extends TextCollectingVisitor {
    final public static Class<?>[] TABLE_LINE_BREAK_CLASSES = { TableBlock.class, TableRow.class, TableCaption.class };

    public TableTextCollectingVisitor(Class<?>... lineBreakNodes) {
        super(lineBreakNodes.length == 0 ? TABLE_LINE_BREAK_CLASSES : concatArrays(TABLE_LINE_BREAK_CLASSES, lineBreakNodes));
    }
}
