package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.RefNode;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.HashSet;
import java.util.Set;

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
    public Set<Reference> getReferencedElements(final Node parent) {
        final HashSet<Reference> references = new HashSet<>();
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
