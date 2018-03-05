package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.util.options.DataHolder;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class AdmonitionRepository {
    private HashSet<String> referencedAdmonitionQualifiers = new LinkedHashSet<>();

    public Set<String> getReferencedAdmonitionQualifiers() {
        return referencedAdmonitionQualifiers;
    }

    public void addAdmonitionQualifier(final String qualifier) {
        referencedAdmonitionQualifiers.add(qualifier);
    }

    public AdmonitionRepository() {

    }
}
