package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class EnumeratedReferenceRepository extends NodeRepository<EnumeratedReferenceBlock> {
    private ArrayList<EnumeratedReferenceBlock> referencedEnumeratedReferenceBlocks = new ArrayList<>();

    public static String getType(String text) {
        int pos = text.lastIndexOf(':');
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
        super(EnumeratedReferenceExtension.ENUMERATED_REFERENCES_KEEP.get(options));
    }

    @NotNull
    @Override
    public DataKey<EnumeratedReferenceRepository> getDataKey() {
        return EnumeratedReferenceExtension.ENUMERATED_REFERENCES;
    }

    @NotNull
    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return EnumeratedReferenceExtension.ENUMERATED_REFERENCES_KEEP;
    }

    @NotNull
    @Override
    public Set<EnumeratedReferenceBlock> getReferencedElements(Node parent) {
        HashSet<EnumeratedReferenceBlock> references = new HashSet<>();
        visitNodes(parent, value -> {
            if (value instanceof EnumeratedReferenceBase) {
                EnumeratedReferenceBlock reference = ((EnumeratedReferenceBase) value).getReferenceNode(EnumeratedReferenceRepository.this);
                if (reference != null) {
                    references.add(reference);
                }
            }
        }, EnumeratedReferenceText.class, EnumeratedReferenceLink.class);
        return references;
    }
}
