package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class AbbreviationRepository extends NodeRepository<AbbreviationBlock> {

    public AbbreviationRepository(DataHolder options) {
        super(AbbreviationExtension.ABBREVIATIONS_KEEP.getFrom(options));
    }

    @Override
    public DataKey<AbbreviationRepository> getDataKey() {
        return AbbreviationExtension.ABBREVIATIONS;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return AbbreviationExtension.ABBREVIATIONS_KEEP;
    }

    @Override
    public Set<AbbreviationBlock> getReferencedElements(final Node parent) {
        final HashSet<AbbreviationBlock> references = new HashSet<>();
        visitNodes(parent, new ValueRunnable<Node>() {
            @Override
            public void run(final Node value) {
                if (value instanceof Abbreviation) {
                    AbbreviationBlock reference = ((Abbreviation) value).getReferenceNode(AbbreviationRepository.this);
                    if (reference != null) {
                        references.add(reference);
                    }
                }
            }
        }, Abbreviation.class);
        return references;
    }
}
