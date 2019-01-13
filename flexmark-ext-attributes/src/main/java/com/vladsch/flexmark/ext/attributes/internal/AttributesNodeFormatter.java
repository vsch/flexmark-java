package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributesDelimiter;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.*;

import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATION_SPANS;

public class AttributesNodeFormatter implements PhasedNodeFormatter {
    public static final DataKey<Map<String, String>> ATTRIBUTE_TRANSLATION_MAP = new DataKey<Map<String, String>>("ATTRIBUTE_TRANSLATION_MAP", new HashMap<String, String>()); // assign attributes to text if previous is not a space
    public static final DataKey<Map<String, String>> ATTRIBUTE_TRANSLATED_MAP = new DataKey<Map<String, String>>("ATTRIBUTE_TRANSLATED_MAP", new HashMap<String, String>()); // assign attributes to text if previous is not a space
    public static final DataKey<Map<String, String>> ATTRIBUTE_ORIGINAL_ID_MAP = new DataKey<Map<String, String>>("ATTRIBUTE_ORIGINAL_ID_MAP", new HashMap<String, String>()); // assign attributes to text if previous is not a space
    public static final DataKey<Integer> ATTRIBUTE_TRANSLATION_ID = new DataKey<>("ATTRIBUTE_TRANSLATION_ID", 0); // next attribute index

    private Map<String, String> attributeTranslationMap;
    private Map<String, String> attributeTranslatedMap;
    private Map<String, String> attributeOriginalIdMap;
    private int attributeOriginalId;

    public AttributesNodeFormatter(DataHolder options) {

    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        return Collections.singleton(FormattingPhase.COLLECT);
    }

    @Override
    public void renderDocument(final NodeFormatterContext context, final MarkdownWriter markdown, final Document document, final FormattingPhase phase) {
        // reset storage for attribute keys and attributes map
        if (context.isTransformingText()) {
            if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                context.getTranslationStore().set(ATTRIBUTE_TRANSLATION_MAP, new HashMap<String, String>());
                context.getTranslationStore().set(ATTRIBUTE_TRANSLATED_MAP, new HashMap<String, String>());
                context.getTranslationStore().set(ATTRIBUTE_ORIGINAL_ID_MAP, new HashMap<String, String>());
            }
            context.getTranslationStore().set(ATTRIBUTE_TRANSLATION_ID, 0);
            attributeOriginalId = 0;

            attributeTranslationMap = context.getTranslationStore().get(ATTRIBUTE_TRANSLATION_MAP);
            attributeTranslatedMap = context.getTranslationStore().get(ATTRIBUTE_TRANSLATED_MAP);
            attributeOriginalIdMap = context.getTranslationStore().get(ATTRIBUTE_ORIGINAL_ID_MAP);
        }
    }

    // only registered if assignTextAttributes is enabled
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<NodeFormattingHandler<?>>();
        set.add(new NodeFormattingHandler<AttributesNode>(AttributesNode.class, new CustomNodeFormatter<AttributesNode>() {
            @Override
            public void render(AttributesNode node, NodeFormatterContext context, MarkdownWriter markdown) {
                AttributesNodeFormatter.this.render(node, context, markdown);
            }
        }));
        set.add(new NodeFormattingHandler<AttributesDelimiter>(AttributesDelimiter.class, new CustomNodeFormatter<AttributesDelimiter>() {
            @Override
            public void render(AttributesDelimiter node, NodeFormatterContext context, MarkdownWriter markdown) {
                AttributesNodeFormatter.this.render(node, context, markdown);
            }
        }));
        return set;
    }

    public static String getEncodedIdAttribute(String category, String categoryId, NodeFormatterContext context, MarkdownWriter markdown) {
        Map<String, String> attributeTranslationMap = context.getTranslationStore().get(ATTRIBUTE_TRANSLATION_MAP);
        Map<String, String> attributeTranslatedMap = context.getTranslationStore().get(ATTRIBUTE_TRANSLATED_MAP);
        return getEncodedIdAttribute(category, categoryId, context, markdown, attributeTranslationMap, attributeTranslatedMap);
    }

    private static String getEncodedIdAttribute(String category, String categoryId, NodeFormatterContext context, MarkdownWriter markdown, Map<String, String> attributeTranslationMap, Map<String, String> attributeTranslatedMap) {
        String encodedCategory = category;
        String encodedId = categoryId;
        int placeholderId = context.getTranslationStore().get(ATTRIBUTE_TRANSLATION_ID);

        switch (context.getRenderPurpose()) {
            case TRANSLATION_SPANS:
                if (!attributeTranslationMap.containsKey(category)) {
                    encodedCategory = String.format(context.getFormatterOptions().translationIdFormat, ++placeholderId);
                    attributeTranslationMap.put(category, encodedCategory);
                    attributeTranslatedMap.put(encodedCategory, category);
                } else {
                    encodedCategory = attributeTranslationMap.get(category);
                }

                if (categoryId != null && !attributeTranslationMap.containsKey(categoryId)) {
                    encodedId = String.format(context.getFormatterOptions().translationIdFormat, ++placeholderId);
                    attributeTranslationMap.put(categoryId, encodedId);
                    attributeTranslatedMap.put(encodedId, categoryId);
                } else {
                    encodedId = attributeTranslationMap.get(categoryId);
                }
                break;

            case TRANSLATED_SPANS:
                // return encoded non-translating text
                //encodedCategory = category;
                //encodedId = categoryId;
                break;

            case TRANSLATED:
                encodedCategory = attributeTranslatedMap.get(category);
                if (categoryId != null) {
                    encodedId = attributeTranslatedMap.get(categoryId);
                }
                break;

            case FORMAT:
            default:
                //encodedCategory = category;
                //encodedId = categoryId;
        }

        context.getTranslationStore().set(ATTRIBUTE_TRANSLATION_ID, placeholderId);

        if (encodedId == null) {
            return encodedCategory;
        } else {
            return encodedCategory + ':' + encodedId;
        }
    }

    private String getEncodedOriginalId(String attribute, NodeFormatterContext context) {
        switch (context.getRenderPurpose()) {
            case TRANSLATION_SPANS:
                // return encoded non-translating text
                String encodedAttribute = "#" + String.format(context.getFormatterOptions().translationIdFormat, ++attributeOriginalId);
                attributeOriginalIdMap.put(encodedAttribute, attribute);
                return encodedAttribute;

            case TRANSLATED_SPANS:
                // return encoded non-translating text
                return "#" + String.format(context.getFormatterOptions().translationIdFormat, ++attributeOriginalId);

            case TRANSLATED:
                ++attributeOriginalId;
                return attributeOriginalIdMap.get(attribute);

            case FORMAT:
            default:
                return attribute;
        }
    }

    void render(AttributesNode node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            markdown.append(node.getOpeningMarker());
            boolean firstChild = true;
            for (Node child: node.getChildren()) {
                AttributeNode attributeNode = (AttributeNode) child;
                if (!firstChild) markdown.append(' ');
                if (attributeNode.isId()) {
                    // encode as X:N if has :, otherwise as non-translating id
                    BasedSequence valueChars = attributeNode.getValue();
                    int pos = valueChars.indexOf(':');
                    if (pos == -1) {
                        String encodedOriginal = getEncodedOriginalId(attributeNode.getChars().toString(), context);
                        markdown.append(encodedOriginal);
                    } else {
                        String category = valueChars.subSequence(0, pos).toString();
                        String id = valueChars.subSequence(pos + 1).toString();
                        String encoded = getEncodedIdAttribute(category, id, context, markdown, attributeTranslationMap, attributeTranslatedMap);
                        switch (context.getRenderPurpose()) {
                            case TRANSLATION_SPANS:
                            case TRANSLATED_SPANS:
                                // return encoded non-translating text
                                String encodedAttribute = "#" + encoded;
                                attributeOriginalIdMap.put(encodedAttribute, attributeNode.getChars().toString());
                                markdown.append('#').append(encoded);
                                break;

                            case TRANSLATED:
                                String encodedOriginal = attributeOriginalIdMap.get("#" + valueChars.toString());
                                markdown.append(encodedOriginal == null ? attributeNode.getChars().toString() : encodedOriginal);
                                break;

                            case FORMAT:
                            default:
                                markdown.append(attributeNode.getChars());
                        }
                    }
                } else {
                    // encode the whole thing as a class
                    markdown.appendNonTranslating(".", attributeNode.getChars());
                }
                firstChild = false;
            }
            markdown.append(node.getClosingMarker());
        } else {
            markdown.append(node.getChars());
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new AttributesNodeFormatter(options);
        }
    }
}
