package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.internal.util.ast.NodeRepository;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.options.DataKey;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ZzzzzzRepository extends NodeRepository<ZzzzzzBlock> {
    private ArrayList<ZzzzzzBlock> referencedZzzzzzBlocks = new ArrayList<>();

    public void addZzzzzzReference(ZzzzzzBlock zzzzzzBlock, Zzzzzz zzzzzz) {
        if (!zzzzzzBlock.isReferenced()) {
            referencedZzzzzzBlocks.add(zzzzzzBlock);
        }

        zzzzzzBlock.setFirstReferenceOffset(zzzzzz.getStartOffset());
    }

    public void resolveZzzzzzOrdinals() {
        // need to sort by first referenced offset then set each to its ordinal position in the array+1
        referencedZzzzzzBlocks.sort((f1, f2) -> f1.getFirstReferenceOffset() - f2.getFirstReferenceOffset());
        int ordinal = 0;
        for (ZzzzzzBlock zzzzzzBlock : referencedZzzzzzBlocks) {
            zzzzzzBlock.setZzzzzzOrdinal(++ordinal);
        }
    }

    public List<ZzzzzzBlock> getReferencedZzzzzzBlocks() {
        return referencedZzzzzzBlocks;
    }

    public ZzzzzzRepository(DataHolder options) {
        super(options);
    }

    @Override
    public DataKey<ZzzzzzRepository> getDataKey() {
        return ZzzzzzExtension.ZZZZZZS;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return ZzzzzzExtension.ZZZZZZS_KEEP;
    }
}
