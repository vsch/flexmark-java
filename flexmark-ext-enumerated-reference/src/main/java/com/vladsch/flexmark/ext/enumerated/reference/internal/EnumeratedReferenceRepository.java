package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeRepository;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class EnumeratedReferenceRepository extends NodeRepository<EnumeratedReferenceBlock> {
    private ArrayList<EnumeratedReferenceBlock> referencedEnumeratedReferenceBlocks = new ArrayList<EnumeratedReferenceBlock>();

    public static String getType(final String text) {
        int pos = text.indexOf(':');
        if (pos > 0) {
            return text.subSequence(0, pos).toString();
        } else {
            // use empty type
            return EnumeratedReferences.EMPTY_TYPE;
        }
    }

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

    @Override
    public Set<EnumeratedReferenceBlock> getReferencedElements(final Node parent) {
        final HashSet<EnumeratedReferenceBlock> references = new HashSet<>();
        visitNodes(parent, new ValueRunnable<Node>() {
            @Override
            public void run(final Node value) {
                if (value instanceof EnumeratedReferenceBase) {
                    EnumeratedReferenceBlock reference = ((EnumeratedReferenceBase) value).getReferenceNode(EnumeratedReferenceRepository.this);
                    if (reference != null) {
                        references.add(reference);
                    }
                }
            }
        }, EnumeratedReferenceText.class, EnumeratedReferenceLink.class);
        return references;
    }
}
