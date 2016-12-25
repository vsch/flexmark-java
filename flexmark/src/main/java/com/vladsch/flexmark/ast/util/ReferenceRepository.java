package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.NodeRepository;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

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
