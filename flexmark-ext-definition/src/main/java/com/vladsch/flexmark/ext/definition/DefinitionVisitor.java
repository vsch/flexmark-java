
package com.vladsch.flexmark.ext.definition;

public interface DefinitionVisitor {
    // void visit(Definition node);
    void visit(final DefinitionList node);
    void visit(final DefinitionTerm node);
    void visit(final DefinitionItem node);
}
