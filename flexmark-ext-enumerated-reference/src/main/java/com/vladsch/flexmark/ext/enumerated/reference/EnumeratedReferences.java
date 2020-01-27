package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class EnumeratedReferences {
    final public static String EMPTY_TYPE = "";
    final public static int[] EMPTY_ORDINALS = new int[0];

    final private EnumeratedReferenceRepository referenceRepository;
    final private HashMap<String, Integer> enumerationCounters;
    final private HashMap<String, int[]> enumeratedReferenceOrdinals;

    public EnumeratedReferences(DataHolder options) {
        referenceRepository = EnumeratedReferenceExtension.ENUMERATED_REFERENCES.get(options);
        enumerationCounters = new HashMap<>();
        enumeratedReferenceOrdinals = new HashMap<>();
    }

    public void add(String text) {
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

    public EnumeratedReferenceRendering[] getEnumeratedReferenceOrdinals(String text) {
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

    public void renderReferenceOrdinals(String text, EnumeratedOrdinalRenderer renderer) {
        EnumeratedReferenceRendering[] renderings = getEnumeratedReferenceOrdinals(text);
        renderReferenceOrdinals(renderings, renderer);
    }

    public static void renderReferenceOrdinals(EnumeratedReferenceRendering[] renderings, EnumeratedOrdinalRenderer renderer) {
        renderer.startRendering(renderings);

        // need to accumulate all compound formats and output on final format's [#]
        ArrayList<CompoundEnumeratedReferenceRendering> compoundReferences = new ArrayList<>();

        EnumeratedReferenceRendering lastRendering = renderings[renderings.length - 1];

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

        int iMax = compoundReferences.size() - 1;
        Runnable wasRunnable = renderer.getEnumOrdinalRunnable();

        renderer.setEnumOrdinalRunnable(() -> {
            for (int i = 0; i < iMax; i++) {
                CompoundEnumeratedReferenceRendering rendering = compoundReferences.get(i);
                Runnable wasRunnable1 = renderer.getEnumOrdinalRunnable();
                renderer.setEnumOrdinalRunnable(null);
                renderer.render(rendering.ordinal, rendering.referenceFormat, rendering.defaultText, rendering.needSeparator);
                renderer.setEnumOrdinalRunnable(wasRunnable1);
            }
        });

        CompoundEnumeratedReferenceRendering rendering = compoundReferences.get(iMax);
        renderer.render(rendering.ordinal, rendering.referenceFormat, rendering.defaultText, rendering.needSeparator);
        renderer.setEnumOrdinalRunnable(wasRunnable);

        renderer.endRendering();
    }
}
