package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashMap;

public class EnumeratedReferences {
    public static final String EMPTY_TYPE = "";
    private final EnumeratedReferenceRepository referenceRepository;
    private final HashMap<String, Integer> enumerationCounters;
    private final HashMap<String, Integer> enumeratedReferenceOrdinals;

    public EnumeratedReferences(DataHolder options) {
        referenceRepository = EnumeratedReferenceExtension.ENUMERATED_REFERENCES.getFrom(options);
        enumerationCounters = new HashMap<>();
        enumeratedReferenceOrdinals = new HashMap<>();
    }

    public void add(final String text) {
        String type = EnumeratedReferenceRepository.getType(text);
        int ordinal;
        if (!enumerationCounters.containsKey(type)) {
            enumerationCounters.put(type, 2);
            ordinal = 1;
        } else {
            ordinal = enumerationCounters.get(type);
            enumerationCounters.put(type, ordinal + 1);
        }

        // save the ordinal for this reference and type
        enumeratedReferenceOrdinals.put(text, ordinal);
    }

    public int getOrdinal(final String text) {
        final Integer ordinal = enumeratedReferenceOrdinals.get(text);
        return ordinal == null ? 0 : ordinal;
    }

    /**
     * Get format node for given type:id
     *
     * @param text reference text
     * @return enumerated reference block or null if not defined
     */
    public Node getFormatNode(final String text) {
        String type = EnumeratedReferenceRepository.getType(text);
        return referenceRepository.get(type);
    }
}
