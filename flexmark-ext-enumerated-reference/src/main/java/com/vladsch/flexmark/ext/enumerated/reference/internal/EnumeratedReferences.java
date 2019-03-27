package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.util.options.DataHolder;

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
     *                      use {@link #getEnumeratedReferenceOrdinals(String)} to get compound ordinals
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
    
    public void renderReferenceOrdinals(final String text, final String defaultFormat, EnumeratedOrdinalRenderer renderer) {
        EnumeratedReferenceRendering[] renderings = getEnumeratedReferenceOrdinals(text);
        renderReferenceOrdinals(renderings, defaultFormat, renderer);
    }
    
    public static void renderReferenceOrdinals(EnumeratedReferenceRendering[] renderings, final String defaultFormat, EnumeratedOrdinalRenderer renderer) {
        boolean needSeparator = false;
        
        String useDefaultFormat = defaultFormat == null ? "%s %d" : defaultFormat;
        
        renderer.startRendering(renderings);
        
        for (EnumeratedReferenceRendering rendering : renderings) {
            int ordinal = rendering.referenceOrdinal;
            
            String defaultText = String.format(useDefaultFormat, rendering.referenceType, rendering.referenceOrdinal);
            
            renderer.render(ordinal, rendering.referenceFormat, defaultText, needSeparator);
            
            if (rendering.referenceFormat != null) {
                Node lastChild = rendering.referenceFormat.getLastChild();
                while (lastChild != null && !(lastChild instanceof EnumeratedReferenceBase)) {
                    lastChild = lastChild.getLastChild();
                }
                
                needSeparator = lastChild instanceof EnumeratedReferenceBase && ((EnumeratedReferenceBase) lastChild).getText().isEmpty();
            } else {
                char c = defaultText.charAt(defaultText.length() - 1);
                needSeparator = Character.isUnicodeIdentifierPart(c);
            }
        }
        
        renderer.endRendering();
    } 
}
