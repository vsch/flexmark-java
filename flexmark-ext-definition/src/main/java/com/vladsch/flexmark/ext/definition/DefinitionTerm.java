package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A Definition block node
 */
public class DefinitionTerm extends ListItem {

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return Node.EMPTY_SEGMENTS;
    }

    public DefinitionTerm() {
    }

    public DefinitionTerm(BasedSequence chars) {
        super(chars);
    }

    public DefinitionTerm(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
    }

    @Override
    public boolean isParagraphWrappingDisabled(Paragraph node, ListOptions listOptions, DataHolder options) {
        return true;
    }
}
