package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.ArrayList;
import java.util.List;

public class ReferenceRepository extends NodeRepository<Reference> {
    public ReferenceRepository(DataHolder options) {
        super(Parser.REFERENCES_KEEP.getFrom(options));
    }

    @Override
    public DataKey<ReferenceRepository> getDataKey() {
        return Parser.REFERENCES;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return Parser.REFERENCES_KEEP;
    }

    @Override
    public String normalizeKey(CharSequence key) {
        return Escaping.normalizeReference(key, true);
    }

    @Override
    public List<Reference> getReferencedElements(final Node parent) {
        final ArrayList<Reference> references = new ArrayList<>();
        visitNodes(parent, new ValueRunnable<Node>() {
            @Override
            public void run(final Node value) {
                if (value instanceof RefNode) {
                    Reference reference = ((RefNode) value).getReferenceNode(ReferenceRepository.this);
                    if (reference != null) {
                        references.add(reference);
                    }
                }
            }
        }, LinkRef.class, ImageRef.class);
        return references;
    }
}
