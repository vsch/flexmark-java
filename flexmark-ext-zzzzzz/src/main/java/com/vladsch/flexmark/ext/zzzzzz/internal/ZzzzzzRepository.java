package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;

import java.util.*;

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
        Collections.sort(referencedZzzzzzBlocks, (f1, f2) -> f1.getFirstReferenceOffset() - f2.getFirstReferenceOffset());
        int ordinal = 0;
        for (ZzzzzzBlock zzzzzzBlock : referencedZzzzzzBlocks) {
            zzzzzzBlock.setZzzzzzOrdinal(++ordinal);
        }
    }

    public List<ZzzzzzBlock> getReferencedZzzzzzBlocks() {
        return referencedZzzzzzBlocks;
    }

    public ZzzzzzRepository(DataHolder options) {
        super(ZzzzzzExtension.ZZZZZZS_KEEP.getFrom(options));
    }

    @Override
    public DataKey<ZzzzzzRepository> getDataKey() {
        return ZzzzzzExtension.ZZZZZZS;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return ZzzzzzExtension.ZZZZZZS_KEEP;
    }

    @Override
    public Set<ZzzzzzBlock> getReferencedElements(Node parent) {
        HashSet<ZzzzzzBlock> references = new HashSet<>();
        //visitNodes(parent, new ValueRunnable<Node>() {
        //    @Override
        //    public void run(Node value) {
        //        if (value instanceof Zzzzzz) {
        //            //Reference reference = ((RefNode) value).getReferenceNode(ZzzzzzRepository.this);
        //            //if (reference != null) {
        //            //    references.add(reference);
        //            //}
        //        }
        //    }
        //}, Zzzzzz.class);
        return references;
    }
}
