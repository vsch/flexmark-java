package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.NodeRepository;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceBlock;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class EnumeratedReferenceRepository extends NodeRepository<EnumeratedReferenceBlock> {
    private ArrayList<EnumeratedReferenceBlock> referencedEnumeratedReferenceBlocks = new ArrayList<EnumeratedReferenceBlock>();

    public List<EnumeratedReferenceBlock> getReferencedEnumeratedReferenceBlocks() {
        return referencedEnumeratedReferenceBlocks;
    }

    public EnumeratedReferenceRepository(DataHolder options) {
        //super(options == null ? KeepType.LAST : EnumeratedReferenceExtension.ENUMERATED_REFERENCES_KEEP.getFrom(options));
        super(EnumeratedReferenceExtension.ENUMERATED_REFERENCES_KEEP.getFrom(options));
    }

    @Override
    public DataKey<EnumeratedReferenceRepository> getDataKey() {
        return EnumeratedReferenceExtension.ENUMERATED_REFERENCES;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return EnumeratedReferenceExtension.ENUMERATED_REFERENCES_KEEP;
    }
}
