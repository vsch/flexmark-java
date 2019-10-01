package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacroDefinitionBlock;
import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class MacroDefinitionRepository extends NodeRepository<MacroDefinitionBlock> {
    private ArrayList<MacroDefinitionBlock> myReferencedMacroDefinitionBlocks = new ArrayList<>();

    public void addMacrosReference(MacroDefinitionBlock macroDefinitionBlock, MacroReference macros) {
        if (!macroDefinitionBlock.isReferenced()) {
            myReferencedMacroDefinitionBlocks.add(macroDefinitionBlock);
        }

        macroDefinitionBlock.setFirstReferenceOffset(macros.getStartOffset());
    }

    public void resolveMacrosOrdinals() {
        // need to sort by first referenced offset then set each to its ordinal position in the array+1
        Collections.sort(myReferencedMacroDefinitionBlocks, new MacroDefinitionBlockComparator());
        int ordinal = 0;
        for (MacroDefinitionBlock macroDefinitionBlock : myReferencedMacroDefinitionBlocks) {
            macroDefinitionBlock.setOrdinal(++ordinal);
        }
    }

    public List<MacroDefinitionBlock> getReferencedMacroDefinitionBlocks() {
        return myReferencedMacroDefinitionBlocks;
    }

    public MacroDefinitionRepository(DataHolder options) {
        super(MacrosExtension.MACRO_DEFINITIONS_KEEP.getFrom(options));
    }

    @Override
    public DataKey<MacroDefinitionRepository> getDataKey() {
        return MacrosExtension.MACRO_DEFINITIONS;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return MacrosExtension.MACRO_DEFINITIONS_KEEP;
    }

    private static class MacroDefinitionBlockComparator implements Comparator<MacroDefinitionBlock> {
        @Override
        public int compare(MacroDefinitionBlock f1, MacroDefinitionBlock f2) {
            return Integer.compare(f1.getFirstReferenceOffset(), f2.getFirstReferenceOffset());
        }
    }

    @Override
    public Set<MacroDefinitionBlock> getReferencedElements(Node parent) {
        HashSet<MacroDefinitionBlock> references = new HashSet<>();
        visitNodes(parent, value -> {
            if (value instanceof MacroReference) {
                MacroDefinitionBlock reference = ((MacroReference) value).getReferenceNode(MacroDefinitionRepository.this);
                if (reference != null) {
                    references.add(reference);
                }
            }
        }, MacroReference.class);
        return references;
    }
}
