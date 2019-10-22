package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class AbbreviationRepository extends NodeRepository<AbbreviationBlock> {

    public AbbreviationRepository(DataHolder options) {
        super(AbbreviationExtension.ABBREVIATIONS_KEEP.get(options));
    }

    @NotNull
    @Override
    public DataKey<AbbreviationRepository> getDataKey() {
        return AbbreviationExtension.ABBREVIATIONS;
    }

    @NotNull
    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return AbbreviationExtension.ABBREVIATIONS_KEEP;
    }

    @NotNull
    @Override
    public Set<AbbreviationBlock> getReferencedElements(Node parent) {
        HashSet<AbbreviationBlock> references = new HashSet<>();
        visitNodes(parent, value -> {
            if (value instanceof Abbreviation) {
                AbbreviationBlock reference = ((Abbreviation) value).getReferenceNode(AbbreviationRepository.this);
                if (reference != null) {
                    references.add(reference);
                }
            }
        }, Abbreviation.class);
        return references;
    }
}
