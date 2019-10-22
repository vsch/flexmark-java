package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

public interface ExplicitAttributeIdProvider {
    /**
     * Used by AttributesExtension to insert attributes for headings during merge
     *
     * @param node
     * @param id
     * @param context
     * @param markdown
     */
    void addExplicitId(@NotNull Node node, @NotNull String id, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown);
}
