package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.ext.enumerated.reference.internal.EnumeratedReferenceNodeRenderer;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class EnumeratedReferences {
    public static final String EMPTY_TYPE = "";
    public static final int[] EMPTY_ORDINALS = new int[0];

    private final EnumeratedReferenceRepository referenceRepository;
    private final HashMap<String, Integer> enumerationCounters;
    private final HashMap<String, int[]> enumeratedReferenceOrdinals;

    public EnumeratedReferences(DataHolder options) {
        referenceRepository = EnumeratedReferenceExtension.ENUMERATED_REFERENCES.getFrom(options);
        enumerationCounters = new HashMap<>();
        enumeratedReferenceOrdinals = new HashMap<>();
    }

    public void add(final String text) {
        String type = EnumeratedReferenceRepository.getType(text);

        String[] types = type.split(":");
        int[] ordinals = new int[types.length];

        // replace all types but the last with ordinal of that type
        StringBuilder nestedType = new StringBuilder();

        int iMax = types.length;
        for (int i = 0; i < iMax; i++) {
            String typeText = types[i];
            nestedType.append(typeText);

            String nestedTypeKey = nestedType.toString();

            if (i < iMax - 1) {
                Integer ordinalInt = enumerationCounters.get(nestedTypeKey);
                int typeOrdinal = ordinalInt == null ? 0 : ordinalInt;

                nestedType.append(':').append(typeOrdinal).append(':');
                ordinals[i] = typeOrdinal;
            } else {
                // last type gets defined if it does not exist
                int ordinal;

                if (!enumerationCounters.containsKey(nestedTypeKey)) {
                    enumerationCounters.put(nestedTypeKey, 1);
                    ordinal = 1;
                } else {
                    ordinal = enumerationCounters.get(nestedTypeKey) + 1;
                    enumerationCounters.put(nestedTypeKey, ordinal);
                }

                ordinals[i] = ordinal;
            }
        }

        // save the ordinal for this reference and type
        enumeratedReferenceOrdinals.put(text, ordinals);
    }

    /**
     * @param text anchor id with type reference
     * @return ordinal or 0 for anchor id
     * @deprecated only returns the last ordinal for the text, does not support compound ordinal numbering
     * use {@link #getEnumeratedReferenceOrdinals(String)} to get compound ordinals
     */
    @Deprecated
    public int getOrdinal(final String text) {
        final int[] ordinals = enumeratedReferenceOrdinals.get(text);
        return ordinals == null ? 0 : ordinals[ordinals.length - 1];
    }

    /**
     * Get format node for given type:id
     *
     * @param text reference text
     * @return enumerated reference block or null if not defined
     * @deprecated use {@link #getEnumeratedReferenceOrdinals(String)} instead which supports compound enumerated ordinals
     */
    @Deprecated
    public Node getFormatNode(final String text) {
        String type = EnumeratedReferenceRepository.getType(text);
        return referenceRepository.get(type);
    }

    public EnumeratedReferenceRendering[] getEnumeratedReferenceOrdinals(final String text) {
        String type = EnumeratedReferenceRepository.getType(text);

        String[] types = type.split(":");
        EnumeratedReferenceRendering[] renderings = new EnumeratedReferenceRendering[types.length];

        int[] ordinals = enumeratedReferenceOrdinals.get(text);

        if (ordinals == null) {
            ordinals = EMPTY_ORDINALS;
        }

        int iMax = types.length;
        for (int i = 0; i < iMax; i++) {
            String typeText = types[i];
            EnumeratedReferenceBlock referenceFormat = referenceRepository.get(typeText);
            int ordinal = i < ordinals.length ? ordinals[i] : 0;
            renderings[i] = new EnumeratedReferenceRendering(referenceFormat, typeText, ordinal);
        }

        return renderings;
    }

    public void renderReferenceOrdinals(final String text, EnumeratedOrdinalRenderer renderer) {
        EnumeratedReferenceRendering[] renderings = getEnumeratedReferenceOrdinals(text);
        renderReferenceOrdinals(renderings, renderer);
    }

    public static void renderReferenceOrdinals(EnumeratedReferenceRendering[] renderings, final EnumeratedOrdinalRenderer renderer) {
        renderer.startRendering(renderings);

        // need to accumulate all compound formats and output on final format's [#] 
        final ArrayList<CompoundEnumeratedReferenceRendering> compoundReferences = new ArrayList<>();
        
        final EnumeratedReferenceRendering lastRendering = renderings[renderings.length-1];
        
        for (EnumeratedReferenceRendering rendering : renderings) {
            int ordinal = rendering.referenceOrdinal;

            String defaultText = rendering.referenceType;

            boolean needSeparator = false;

            if (rendering != lastRendering) {
                if (rendering.referenceFormat != null) {
                    Node lastChild = rendering.referenceFormat.getLastChild();
                    while (lastChild != null && !(lastChild instanceof EnumeratedReferenceBase)) {
                        lastChild = lastChild.getLastChild();
                    }
                    needSeparator = lastChild instanceof EnumeratedReferenceBase && ((EnumeratedReferenceBase) lastChild).getText().isEmpty();
                } else {
                    needSeparator = true;
                }
            }
            
            compoundReferences.add(new CompoundEnumeratedReferenceRendering(ordinal, rendering.referenceFormat, defaultText, needSeparator));
        }

        final int iMax = compoundReferences.size() - 1;
        Runnable wasRunnable = renderer.getEnumOrdinalRunnable();
        
        renderer.setEnumOrdinalRunnable(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iMax; i++) {
                    CompoundEnumeratedReferenceRendering rendering = compoundReferences.get(i);
                    Runnable wasRunnable = renderer.getEnumOrdinalRunnable();
                    renderer.setEnumOrdinalRunnable(null);
                    renderer.render(rendering.ordinal, rendering.referenceFormat, rendering.defaultText, rendering.needSeparator);
                    renderer.setEnumOrdinalRunnable(wasRunnable);
                }
            }
        });

        CompoundEnumeratedReferenceRendering rendering = compoundReferences.get(iMax);
        renderer.render(rendering.ordinal, rendering.referenceFormat, rendering.defaultText, rendering.needSeparator);
        renderer.setEnumOrdinalRunnable(wasRunnable);
        
        renderer.endRendering();
    }
}
