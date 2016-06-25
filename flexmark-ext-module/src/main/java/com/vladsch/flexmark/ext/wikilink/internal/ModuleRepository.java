package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.Module;
import com.vladsch.flexmark.ext.wikilink.ModuleBlock;
import com.vladsch.flexmark.ext.wikilink.ModuleExtension;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.DataKey;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.internal.util.NodeRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ModuleRepository extends NodeRepository<ModuleBlock> {
    private ArrayList<ModuleBlock> referencedModuleBlocks = new ArrayList<>();

    public void addModuleReference(ModuleBlock moduleBlock, Module module) {
        if (!moduleBlock.isReferenced()) {
            referencedModuleBlocks.add(moduleBlock);
        }

        moduleBlock.setFirstReferenceOffset(module.getStartOffset());
    }

    public void resolveModuleOrdinals() {
        // need to sort by first referenced offset then set each to its ordinal position in the array+1
        referencedModuleBlocks.sort((f1, f2) -> f1.getFirstReferenceOffset() - f2.getFirstReferenceOffset());
        int ordinal = 0;
        for (ModuleBlock moduleBlock : referencedModuleBlocks) {
            moduleBlock.setModuleOrdinal(++ordinal);
        }
    }

    public List<ModuleBlock> getReferencedModuleBlocks() {
        return referencedModuleBlocks;
    }

    public ModuleRepository(DataHolder options) {
        super(options);
    }

    @Override
    public DataKey<ModuleRepository> getDataKey() {
        return ModuleExtension.MODULES;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return ModuleExtension.MODULES_KEEP;
    }
}
