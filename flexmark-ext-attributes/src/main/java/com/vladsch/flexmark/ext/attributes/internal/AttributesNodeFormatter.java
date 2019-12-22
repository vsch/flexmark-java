package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.AnchorRefTarget;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.util.AnchorRefTargetBlockVisitor;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesDelimiter;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.formatter.internal.CoreNodeFormatter;
import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATED;
import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATION_SPANS;

public class AttributesNodeFormatter implements PhasedNodeFormatter, ExplicitAttributeIdProvider {
    public static final DataKey<Map<String, String>> ATTRIBUTE_TRANSLATION_MAP = new DataKey<>("ATTRIBUTE_TRANSLATION_MAP", HashMap::new);
    public static final DataKey<Map<String, String>> ATTRIBUTE_TRANSLATED_MAP = new DataKey<>("ATTRIBUTE_TRANSLATED_MAP", HashMap::new);
    public static final DataKey<Map<String, String>> ATTRIBUTE_ORIGINAL_ID_MAP = new DataKey<>("ATTRIBUTE_ORIGINAL_ID_MAP", HashMap::new);

    // need to have this one available in core formatter
    public static final DataKey<Map<String, String>> ATTRIBUTE_UNIQUIFICATION_ID_MAP = CoreNodeFormatter.ATTRIBUTE_UNIQUIFICATION_ID_MAP;

    public static final DataKey<Map<String, String>> ATTRIBUTE_UNIQUIFICATION_CATEGORY_MAP = new DataKey<>("ATTRIBUTE_UNIQUIFICATION_CATEGORY_MAP", HashMap::new);
    public static final DataKey<Integer> ATTRIBUTE_TRANSLATION_ID = new DataKey<>("ATTRIBUTE_TRANSLATION_ID", 0); // next attribute index

    private Map<String, String> attributeTranslationMap;
    private Map<String, String> attributeTranslatedMap;
    private Map<String, String> attributeOriginalIdMap;
    private Map<String, String> attributeUniquificationIdMap;
    private Map<String, String> attributeCategoryUniquificationMap;
    private int attributeOriginalId;
    final private AttributesFormatOptions formatOptions;

    public AttributesNodeFormatter(DataHolder options) {
        formatOptions = new AttributesFormatOptions(options);
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Nullable
    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        return Collections.singleton(FormattingPhase.COLLECT);
    }

    @Override
    public void addExplicitId(@NotNull Node node, @Nullable String id, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown) {
        if (id != null && node instanceof Heading) {
            // if our id != generated id we add explicit attributes if none are found already
            if (context.getRenderPurpose() == TRANSLATED) {
                if (hasNoIdAttribute(node) && attributeUniquificationIdMap != null) {
                    //System.out.println(String.format("Checking attribute id unique map for %s to %s, purpose: %s", this.toString(), attributeUniquificationIdMap.toString(), context.getRenderPurpose().toString()));
                    String uniqueId = attributeUniquificationIdMap.getOrDefault(id, id);
                    if (!uniqueId.equals(id)) {
                        markdown.append(" {.");
                        markdown.append(uniqueId);
                        markdown.append("}");
                    }
                }
            }
        }
    }

    boolean hasNoIdAttribute(Node node) {
        boolean haveIdAttribute = false;

        for (Node child : node.getChildren()) {
            if (child instanceof AttributesNode) {
                for (Node attr : child.getChildren()) {
                    if (attr instanceof AttributeNode) {
                        if (((AttributeNode) attr).isId()) {
                            haveIdAttribute = true;
                            break;
                        }
                    }
                }
                if (haveIdAttribute) break;
            }
        }
        return !haveIdAttribute;
    }

    @Override
    public void renderDocument(@NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown, @NotNull Document document, @NotNull FormattingPhase phase) {
        // reset storage for attribute keys and attributes map
        if (context.isTransformingText()) {
            context.getTranslationStore().set(ATTRIBUTE_TRANSLATION_ID, 0);
            attributeOriginalId = 0;

            if (phase == FormattingPhase.COLLECT) {
                if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                    context.getTranslationStore().set(ATTRIBUTE_TRANSLATION_MAP, new HashMap<String, String>());
                    context.getTranslationStore().set(ATTRIBUTE_TRANSLATED_MAP, new HashMap<String, String>());
                    context.getTranslationStore().set(ATTRIBUTE_ORIGINAL_ID_MAP, new HashMap<String, String>());

                    MergeContext mergeContext = context.getMergeContext();
                    if (mergeContext != null) {
                        // make ids unique if there is a list of documents
                        HashSet<String> mergedUniquified = new HashSet<>();
                        //HashSet<String> mergedCategories = new HashSet<>();

                        mergeContext.forEachPrecedingDocument(document, (docContext, doc, index) -> {
                            NodeAttributeRepository attributes = AttributesExtension.NODE_ATTRIBUTES.get(doc);
                            Map<String, String> idUniquificationMap = ATTRIBUTE_UNIQUIFICATION_ID_MAP.get(docContext.getTranslationStore());

                            for (List<AttributesNode> attributesNodes : attributes.values()) {
                                for (AttributesNode attributesNode : attributesNodes) {
                                    for (Node childNode : attributesNode.getChildren()) {
                                        if (childNode instanceof AttributeNode) {
                                            AttributeNode attributeNode = (AttributeNode) childNode;

                                            if (attributeNode.isId()) {
                                                // this one needs to be mapped
                                                String key = attributeNode.getValue().toString();
                                                String newKey = idUniquificationMap.getOrDefault(key, key);

                                                if (mergedUniquified.contains(newKey)) {
                                                    // will occur if an undefined attribute id is used by enum ref and defined in a later file
                                                } else {
                                                    mergedUniquified.add(newKey);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // add heading ids to contained ids
                            HtmlIdGenerator generator = context.getIdGenerator();
                            if (generator != null) {
                                new AnchorRefTargetBlockVisitor() {
                                    @Override
                                    protected void visit(AnchorRefTarget refTarget) {
                                        Node node = (Node) refTarget;
                                        if (hasNoIdAttribute(node)) {
                                            String key = generator.getId(node);

                                            if (key == null) {
                                                String text = refTarget.getAnchorRefText();
                                                key = generator.getId(text);
                                                refTarget.setAnchorRefId(key);
                                            }

                                            if (key != null) {
                                                String newKey = idUniquificationMap.getOrDefault(key, key);

                                                if (mergedUniquified.contains(newKey)) {
                                                    // will occur if an undefined attribute id is used by enum ref and defined in a later file
                                                } else {
                                                    mergedUniquified.add(newKey);
                                                }
                                            }
                                        }
                                    }
                                }.visit(document);
                            }
                        });

                        // now make ours unique
                        NodeAttributeRepository attributes = AttributesExtension.NODE_ATTRIBUTES.get(document);
                        Map<String, String> idUniquificationMap = ATTRIBUTE_UNIQUIFICATION_ID_MAP.get(context.getTranslationStore());
                        Map<String, String> categoryUniquificationMap = ATTRIBUTE_UNIQUIFICATION_CATEGORY_MAP.get(context.getTranslationStore());
                        Map<String, String> idMap = new HashMap<>();

                        for (List<AttributesNode> attributesNodes : attributes.values()) {
                            for (AttributesNode attributesNode : attributesNodes) {
                                for (Node childNode : attributesNode.getChildren()) {
                                    if (childNode instanceof AttributeNode) {
                                        AttributeNode attributeNode = (AttributeNode) childNode;

                                        if (attributeNode.isId()) {
                                            // this one needs to be unique
                                            BasedSequence valueChars = attributeNode.getValue();
                                            String key = valueChars.toString();
                                            String useKey = key;

                                            int pos = valueChars.indexOf(':');
                                            if (pos != -1) {
                                                String category = valueChars.subSequence(0, pos).toString();
                                                String id = valueChars.subSequence(pos + 1).toString();
                                                String uniqueCategory = category;

                                                // now may need to map category if enum ref format blocks clash
                                                uniqueCategory = categoryUniquificationMap.getOrDefault(category, category);

                                                useKey = String.format("%s:%s", uniqueCategory, id);
                                            }

                                            int i = 0;
                                            String newKey = useKey;

                                            while (mergedUniquified.contains(newKey)) {
                                                // need to uniquify
                                                newKey = String.format("%s%d", useKey, ++i);
                                            }

                                            if (i > 0 || !newKey.equals(key)) {
                                                idMap.put(key, newKey);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // add heading ids to contained ids
                        HtmlIdGenerator generator = context.getIdGenerator();
                        if (generator != null) {
                            new AnchorRefTargetBlockVisitor() {
                                @Override
                                protected void visit(AnchorRefTarget refTarget) {
                                    Node node = (Node) refTarget;

                                    if (hasNoIdAttribute(node)) {
                                        String key = generator.getId(node);

                                        if (key == null) {
                                            String text = refTarget.getAnchorRefText();
                                            key = generator.getId(text);
                                            refTarget.setAnchorRefId(key);
                                        }

                                        if (key != null) {
                                            int i = 0;
                                            String newKey = key;

                                            while (mergedUniquified.contains(newKey)) {
                                                // need to uniquify
                                                newKey = String.format("%s%d", key, ++i);
                                            }

                                            if (i > 0 || !newKey.equals(key)) {
                                                idMap.put(key, newKey);
                                            }
                                        }
                                    }
                                }
                            }.visit(document);
                        }

                        if (!idMap.isEmpty()) {
                            context.getTranslationStore().set(ATTRIBUTE_UNIQUIFICATION_ID_MAP, idMap);
                            //System.out.println(String.format("Setting attribute id unique map for %s to %s, purpose: %s, phase: %s", this.toString(), idMap.toString(), context.getRenderPurpose().toString(), phase.toString()));
                        }
                    }
                }
            }
        }

        attributeUniquificationIdMap = ATTRIBUTE_UNIQUIFICATION_ID_MAP.get(context.getTranslationStore());
        //System.out.println(String.format("Getting attribute id unique map for %s to %s, purpose: %s, phase: %s", this.toString(), attributeUniquificationIdMap.toString(), context.getRenderPurpose().toString(), phase.toString()));

        attributeTranslationMap = ATTRIBUTE_TRANSLATION_MAP.get(context.getTranslationStore());
        attributeTranslatedMap = ATTRIBUTE_TRANSLATED_MAP.get(context.getTranslationStore());
        attributeOriginalIdMap = ATTRIBUTE_ORIGINAL_ID_MAP.get(context.getTranslationStore());
    }

    // only registered if assignTextAttributes is enabled
    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<>();
        set.add(new NodeFormattingHandler<>(AttributesNode.class, AttributesNodeFormatter.this::render));
        set.add(new NodeFormattingHandler<>(AttributesDelimiter.class, AttributesNodeFormatter.this::render));
        return set;
    }

    public static String getEncodedIdAttribute(String category, String categoryId, NodeFormatterContext context, MarkdownWriter markdown) {
        Map<String, String> attributeTranslationMap = ATTRIBUTE_TRANSLATION_MAP.get(context.getTranslationStore());
        Map<String, String> attributeTranslatedMap = ATTRIBUTE_TRANSLATED_MAP.get(context.getTranslationStore());
        String id = getEncodedIdAttribute(category, categoryId, context, markdown, attributeTranslationMap, attributeTranslatedMap);

        if (context.getRenderPurpose() == TRANSLATED) {
            Map<String, String> idUniquificationMap = ATTRIBUTE_UNIQUIFICATION_ID_MAP.get(context.getTranslationStore());
            if (!idUniquificationMap.isEmpty()) {
                return idUniquificationMap.getOrDefault(id, id);
            }
        }
        return id;
    }

    private static String getEncodedIdAttribute(String category, String categoryId, NodeFormatterContext context, MarkdownWriter markdown, Map<String, String> attributeTranslationMap, Map<String, String> attributeTranslatedMap) {
        String encodedCategory = category;
        String encodedId = categoryId;
        int placeholderId = ATTRIBUTE_TRANSLATION_ID.get(context.getTranslationStore());

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
                String id = attributeOriginalIdMap.get(attribute);

                if (attributeUniquificationIdMap != null) {
                    return attributeUniquificationIdMap.getOrDefault(id, id);
                }
                return id;

            case FORMAT:
            default:
                return attribute;
        }
    }

    void render(AttributesNode node, NodeFormatterContext context, MarkdownWriter markdown) {
        Node previous = node.getPrevious();
        if (previous != null && !previous.getChars().isContinuedBy(node.getChars()) && !previous.getChars().endsWith(" ") && !node.getChars().startsWith(" ")) {
            markdown.append(' ');
        }

        if (context.isTransformingText()) {
            markdown.append(node.getOpeningMarker());
            boolean firstChild = true;
            for (Node child : node.getChildren()) {
                AttributeNode attributeNode = (AttributeNode) child;
                if (!firstChild) markdown.append(' ');
                if (attributeNode.isId()) {
                    // encode as X:N if has :, otherwise as non-translating id
                    BasedSequence valueChars = attributeNode.getValue();
                    int pos = valueChars.indexOf(':');
                    if (pos == -1) {
                        String encodedOriginal = getEncodedOriginalId(attributeNode.getChars().toString(), context);

                        if (context.getRenderPurpose() == TRANSLATED) {
                            if (!attributeUniquificationIdMap.isEmpty()) {
                                String idOnly = encodedOriginal.substring(1);
                                encodedOriginal = "#" + attributeUniquificationIdMap.getOrDefault(idOnly, idOnly);
                            }
                        }

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

                                if (attributeUniquificationIdMap != null) {
                                    //System.out.println(String.format("Rendering attribute id unique map for %s to %s, purpose: %s", this.toString(), attributeUniquificationIdMap.toString(), context.getRenderPurpose().toString()));

                                    if (!attributeUniquificationIdMap.isEmpty()) {
                                        String idOnly = encodedOriginal.substring(1);
                                        encodedOriginal = "#" + attributeUniquificationIdMap.getOrDefault(idOnly, idOnly);
                                    }
                                }

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
            // IMPORTANT: implement formatting options
            markdown.append(node.getChars());
        }

        Node next = node.getNext();
        if (next != null && !(next instanceof AttributesNode) && !node.getChars().isContinuedBy(next.getChars()) && !node.getChars().endsWith(" ") && !next.getChars().startsWith(" ")) {
            markdown.append(' ');
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new AttributesNodeFormatter(options);
        }
    }
}
