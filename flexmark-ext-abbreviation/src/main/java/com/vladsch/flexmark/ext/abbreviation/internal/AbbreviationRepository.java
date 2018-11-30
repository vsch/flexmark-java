package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeRepository;
import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.ArrayList;
import java.util.List;

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
    public List<AbbreviationBlock> getReferencedElements(final Node parent) {
        final ArrayList<AbbreviationBlock> references = new ArrayList<>();
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
