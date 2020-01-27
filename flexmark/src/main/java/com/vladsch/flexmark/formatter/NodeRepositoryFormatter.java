package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATED;
import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATION_SPANS;

public abstract class NodeRepositoryFormatter<R extends NodeRepository<B>, B extends Node & ReferenceNode<R, B, N>, N extends Node & ReferencingNode<R, B>> implements PhasedNodeFormatter {
    final public static HashSet<FormattingPhase> FORMATTING_PHASES = new HashSet<>(Arrays.asList(
            FormattingPhase.COLLECT,
            FormattingPhase.DOCUMENT_TOP,
            FormattingPhase.DOCUMENT_BOTTOM
    ));

    public Comparator<B> getReferenceComparator() {
        return myComparator;
    }

    public abstract R getRepository(DataHolder options);
    public abstract ElementPlacement getReferencePlacement();
    public abstract ElementPlacementSort getReferenceSort();
    protected abstract void renderReferenceBlock(B node, NodeFormatterContext context, MarkdownWriter markdown);

    /**
     * Whether references should be made unique
     *
     * @return true if yes, false if leave all references as is
     */
    protected boolean makeReferencesUnique() {
        return true;
    }

    protected final R referenceRepository;
    protected final List<B> referenceList;
    protected final HashSet<Node> unusedReferences;
    protected final B lastReference;
    protected boolean recheckUndefinedReferences;
    protected boolean repositoryNodesDone;
    protected final Comparator<B> myComparator;

    private Map<String, String> referenceTranslationMap;
    protected Map<String, String> referenceUniqificationMap;
    final private DataKey<Map<String, String>> myReferenceMapKey;
    final private DataKey<Map<String, String>> myReferenceUniqificationMapKey;

    protected ElementPlacement getTranslationReferencePlacement(NodeFormatterContext context) {
        if (context.isTransformingText()) {
            return ElementPlacement.AS_IS;
        } else {
            return getReferencePlacement();
        }
    }

    public String modifyTransformedReference(String transformedReferenceId, NodeFormatterContext context) {
        return transformedReferenceId;
    }

    private void renderReferenceBlockUnique(B node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getRenderPurpose() == TRANSLATED) {
            context.postProcessNonTranslating(id -> {
                if (referenceUniqificationMap != null) {
                    String uniqueS = referenceUniqificationMap.getOrDefault(id, id);
                    return uniqueS;
                }
                return id;
            }, () -> {
                renderReferenceBlock(node, context, markdown);
            });
        } else {
            renderReferenceBlock(node, context, markdown);
        }
    }

    protected String transformReferenceId(String nodeText, NodeFormatterContext context) {
        if (context.isTransformingText()) {
            String transformed;

            switch (context.getRenderPurpose()) {
                case TRANSLATION_SPANS:
                case TRANSLATED_SPANS:
                    if (referenceTranslationMap != null) {
                        if (referenceTranslationMap.containsKey(nodeText)) {
                            transformed = referenceTranslationMap.get(nodeText);
                        } else {
                            transformed = context.transformNonTranslating(null, nodeText, null, null).toString();
                            referenceTranslationMap.put(nodeText, transformed);
                        }
                    } else {
                        transformed = context.transformNonTranslating(null, nodeText, null, null).toString();
                    }
                    return modifyTransformedReference(transformed, context);

                case TRANSLATED:
                    String untransformed = modifyTransformedReference(nodeText, context);
                    String s = context.transformNonTranslating(null, untransformed, null, null).toString();

                    // apply uniquification
                    // disable otherwise may get double mapping
                    if (!context.isPostProcessingNonTranslating() && referenceUniqificationMap != null && referenceUniqificationMap.containsKey(s)) {
                        String uniqueS = referenceUniqificationMap.get(s);
                        return uniqueS;
                    }
                    return s;

                case FORMAT:
                default:
                    break;
            }
        }
        return nodeText;
    }

    public NodeRepositoryFormatter(DataHolder options, DataKey<Map<String, String>> referenceMapKey, DataKey<Map<String, String>> uniquificationMapKey) {
        myReferenceMapKey = referenceMapKey;
        myReferenceUniqificationMapKey = uniquificationMapKey;
        referenceRepository = getRepository(options);
        referenceList = referenceRepository.values();
        lastReference = referenceList.isEmpty() ? null : referenceList.get(referenceList.size() - 1);
        unusedReferences = new HashSet<>();
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.get(options);
        repositoryNodesDone = false;

        myComparator = Comparable::compareTo;
    }

    @Nullable
    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        return FORMATTING_PHASES;
    }

    @Override
    public void renderDocument(@NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown, @NotNull Document document, @NotNull FormattingPhase phase) {
        // here non-rendered elements can be collected so that they are rendered in another part of the document
        if (context.isTransformingText() && myReferenceMapKey != null) {
            if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                context.getTranslationStore().set(myReferenceMapKey, new HashMap<String, String>());
            }
            referenceTranslationMap = myReferenceMapKey.get(context.getTranslationStore());
        }

        switch (phase) {
            case COLLECT:
                referenceUniqificationMap = null;

                if (context.isTransformingText() && myReferenceUniqificationMapKey != null && makeReferencesUnique()) {
                    if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                        // need to uniquify the ids across documents
                        MergeContext mergeContext = context.getMergeContext();

                        if (mergeContext != null) {
                            uniquifyIds(context, markdown, document);
                        }
                    }

                    referenceUniqificationMap = myReferenceUniqificationMapKey.get(context.getTranslationStore());
                }

                if (getTranslationReferencePlacement(context).isChange() && getReferenceSort().isUnused()) {
                    // get all ref nodes and figure out which ones are unused
                    unusedReferences.addAll(referenceList);
                    Set<Class<?>> nodeClasses = getNodeClasses();
                    if (nodeClasses != null) {
                        Iterable<? extends Node> nodes = context.nodesOfType(nodeClasses);
                        for (Node node : nodes) {
                            N referencingNode = lastReference == null ? null : lastReference.getReferencingNode(node);
                            if (referencingNode != null) {
                                B referenceBlock = referencingNode.getReferenceNode(referenceRepository);
                                if (referenceBlock != null) {
                                    unusedReferences.remove(referenceBlock);
                                }
                            }
                        }
                    }
                }
                break;

            case DOCUMENT_TOP:
                if (getTranslationReferencePlacement(context) == ElementPlacement.DOCUMENT_TOP) {
                    // put all footnotes here
                    formatReferences(context, markdown);
                }
                break;

            case DOCUMENT_BOTTOM:
                if (getTranslationReferencePlacement(context) == ElementPlacement.DOCUMENT_BOTTOM) {
                    // put all footnotes here
                    formatReferences(context, markdown);
                }
                break;

            default:
                break;
        }
    }

    private void formatReferences(NodeFormatterContext context, MarkdownWriter markdown) {
        ArrayList<B> references = new ArrayList<>(referenceList);

        ElementPlacementSort referenceSort = getReferenceSort();
        switch (referenceSort) {
            case AS_IS:
                break;

            case SORT:
                references.sort(getReferenceComparator());
                break;

            case SORT_UNUSED_LAST:
            case SORT_DELETE_UNUSED:
            case DELETE_UNUSED:
                ArrayList<B> used = new ArrayList<>();
                ArrayList<B> unused = new ArrayList<>();
                for (B reference : references) {
                    if (!unusedReferences.contains(reference)) {
                        used.add(reference);
                    } else if (!referenceSort.isDeleteUnused()) {
                        unused.add(reference);
                    }
                }

                if (referenceSort.isSort()) {
                    used.sort(getReferenceComparator());

                    if (!referenceSort.isDeleteUnused()) {
                        unused.sort(getReferenceComparator());
                    }
                }

                references.clear();
                references.addAll(used);

                if (!referenceSort.isDeleteUnused()) {
                    references.addAll(unused);
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + referenceSort);
        }

        markdown.blankLine();
        for (B reference : references) {
            renderReferenceBlockUnique(reference, context, markdown);
        }
        markdown.blankLine();
        repositoryNodesDone = true;
    }

    protected void renderReference(B node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!repositoryNodesDone) {
            switch (getTranslationReferencePlacement(context)) {
                case AS_IS:
                    renderReferenceBlockUnique(node, context, markdown);
                    if (node.getNext() == null || node.getNext().getClass() != node.getClass()) {
                        markdown.blankLine();
                    }
                    break;

                case GROUP_WITH_FIRST:
                    // must be the first since we are here
                    formatReferences(context, markdown);
                    break;

                case GROUP_WITH_LAST:
                    if (node == lastReference) {
                        formatReferences(context, markdown);
                    }
                    break;
            }
        }
    }

    /**
     * Compute needed id map to make reference ids unique across documents[] up to entry equal to document
     * and store this map in document property so that it can be retrieved from the document later when computing
     * the map by documents after this document in the list.
     *
     * @param context
     * @param markdown
     * @param document
     */
    protected void uniquifyIds(NodeFormatterContext context, MarkdownWriter markdown, Document document) {
        // collect ids and values to uniquify references up to our document
        R combinedRefs = getRepository(new DataSet()); // create an empty repository
        Map<String, String> idMap = new HashMap<>();

        MergeContext mergeContext = context.getMergeContext();
        mergeContext.forEachPrecedingDocument(document, (docContext, doc, index) -> {
            NodeRepository<B> docRefs = getRepository(doc);
            Map<String, String> uniquificationMap = myReferenceUniqificationMapKey.get(docContext.getTranslationStore());
            NodeRepository.transferReferences(combinedRefs, docRefs, true, uniquificationMap);
        });

        // now map our ids that clash to unique ids by appending increasing integers to id
        R repository = getRepository(document);
        for (Map.Entry<String, B> entry : repository.entrySet()) {
            String key = entry.getKey();
            String newKey = key;
            int i = 0;

            while (combinedRefs.containsKey(newKey)) {
                // need to uniquify
                newKey = String.format("%s%d", key, ++i);
            }

            if (i > 0) {
                // have conflict, remap
                idMap.put(key, newKey);
            }
        }

        if (!idMap.isEmpty()) {
            // save for later use
            context.getTranslationStore().set(myReferenceUniqificationMapKey, idMap);
        }
    }
}
