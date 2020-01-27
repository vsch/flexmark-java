package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ExplicitAttributeIdProvider {
    /**
     * Used by AttributesExtension to insert attributes for headings during merge
     *
     * @param node     node
     * @param id       explicit id
     * @param context  context
     * @param markdown markdown writer
     */
    void addExplicitId(@NotNull Node node, @Nullable String id, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown);
}
