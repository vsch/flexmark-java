package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.Reference;

import java.util.Locale;

public class ReferenceRepository extends NodeRepository<Reference> {
    public final static ReferenceRepository NULL = new ReferenceRepository(ModifyBehavior.LOCKED);
    public final static PropertyKey<ReferenceRepository> PROPERTY_KEY = new PropertyKey<>("REFERENCES", NULL, new ValueFactory<ReferenceRepository>() {
        @Override
        public ReferenceRepository value() {
            return new ReferenceRepository(ModifyBehavior.DEFAULT);
        }
    });

    public ReferenceRepository(ModifyBehavior onDuplicate) {
        super(onDuplicate);
    }

    @Override
    public PropertyKey<ReferenceRepository> getPropertyKey() {
        return PROPERTY_KEY;
    }

    @Override
    public String normalizeKey(CharSequence key) {
        return super.normalizeKey(key).toLowerCase(Locale.ROOT);
    }
}
