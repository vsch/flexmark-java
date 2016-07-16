package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.ast.NodeRepository;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.options.DataKey;
import com.vladsch.flexmark.node.Reference;
import com.vladsch.flexmark.parser.Parser;

public class ReferenceRepository extends NodeRepository<Reference> {
    public ReferenceRepository(DataHolder options) {
        super(options);
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
}
