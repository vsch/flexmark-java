package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacroDefinitionBlock;
import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class MacroDefinitionRepository extends NodeRepository<MacroDefinitionBlock> {
    final private ArrayList<MacroDefinitionBlock> myReferencedMacroDefinitionBlocks = new ArrayList<>();

    public void addMacrosReference(MacroDefinitionBlock macroDefinitionBlock, MacroReference macros) {
        if (!macroDefinitionBlock.isReferenced()) {
            myReferencedMacroDefinitionBlocks.add(macroDefinitionBlock);
        }

        macroDefinitionBlock.setFirstReferenceOffset(macros.getStartOffset());
    }

    public void resolveMacrosOrdinals() {
        // need to sort by first referenced offset then set each to its ordinal position in the array+1
        myReferencedMacroDefinitionBlocks.sort(Comparator.comparing(MacroDefinitionBlock::getFirstReferenceOffset));
        int ordinal = 0;
        for (MacroDefinitionBlock macroDefinitionBlock : myReferencedMacroDefinitionBlocks) {
            macroDefinitionBlock.setOrdinal(++ordinal);
        }
    }

    public List<MacroDefinitionBlock> getReferencedMacroDefinitionBlocks() {
        return myReferencedMacroDefinitionBlocks;
    }

    public MacroDefinitionRepository(DataHolder options) {
        super(MacrosExtension.MACRO_DEFINITIONS_KEEP.get(options));
    }

    @NotNull
    @Override
    public DataKey<MacroDefinitionRepository> getDataKey() {
        return MacrosExtension.MACRO_DEFINITIONS;
    }

    @NotNull
    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return MacrosExtension.MACRO_DEFINITIONS_KEEP;
    }

    @NotNull
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
