package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.formatter.options.ElementPlacement;
import com.vladsch.flexmark.formatter.options.ElementPlacementSort;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.*;

public abstract class NodeRepositoryFormatter<R extends NodeRepository<B>, B extends Node & ReferenceNode<R, B, N>, N extends Node & ReferencingNode<R, B>> implements PhasedNodeFormatter {
    public static final HashSet<FormattingPhase> FORMATTING_PHASES = new HashSet<>(Arrays.asList(
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
    public abstract void renderReferenceBlock(B node, NodeFormatterContext context, MarkdownWriter markdown);

    protected final R referenceRepository;
    protected final List<B> referenceList;
    protected final HashSet<Node> unusedReferences;
    protected final B lastReference;
    protected boolean recheckUndefinedReferences;
    protected boolean repositoryNodesDone;
    protected final Comparator<B> myComparator;

    public NodeRepositoryFormatter(DataHolder options) {
        referenceRepository = getRepository(options);
        referenceList = referenceRepository.values();
        lastReference = referenceList.isEmpty() ? null : referenceList.get(referenceList.size() - 1);
        unusedReferences = new HashSet<>();
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.getFrom(options);
        repositoryNodesDone = false;

        myComparator = new Comparator<B>() {
            @Override
            public int compare(final B o1, final B o2) {
                return o1.compareTo(o2);
            }
        };
    }

    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        return FORMATTING_PHASES;
    }

    @Override
    public void renderDocument(final NodeFormatterContext context, final MarkdownWriter markdown, final Document document, final FormattingPhase phase) {
        // here non-rendered elements can be collected so that they are rendered in another part of the document
        switch (phase) {
            case COLLECT:
                if (getReferencePlacement() != ElementPlacement.AS_IS && getReferenceSort() == ElementPlacementSort.SORT_UNUSED_LAST) {
                    // get all ref nodes and figure out which ones are unused
                    unusedReferences.addAll(referenceList);
                    final Iterable<? extends Node> nodes = context.nodesOfType(getNodeClasses());
                    for (Node node : nodes) {
                        N referencingNode = lastReference.getReferencingNode(node);
                        if (referencingNode != null) {
                            B referenceBlock = referencingNode.getReferenceNode(referenceRepository);
                            if (referenceBlock != null) {
                                unusedReferences.remove(referenceBlock);
                            }
                        }
                    }
                }
                break;

            case DOCUMENT_TOP:
                if (getReferencePlacement() == ElementPlacement.DOCUMENT_TOP) {
                    // put all footnotes here
                    formatReferences(context, markdown);
                }
                break;

            case DOCUMENT_BOTTOM:
                if (getReferencePlacement() == ElementPlacement.DOCUMENT_BOTTOM) {
                    // put all footnotes here
                    formatReferences(context, markdown);
                }
                break;

            default:
                break;
        }
    }

    private void formatReferences(final NodeFormatterContext context, MarkdownWriter markdown) {
        ArrayList<B> references = new ArrayList<>(referenceList);

        switch (getReferenceSort()) {
            case AS_IS:
                break;

            case SORT:
                Collections.sort(references, getReferenceComparator());
                break;

            case SORT_UNUSED_LAST:
                ArrayList<B> used = new ArrayList<>();
                ArrayList<B> unused = new ArrayList<>();
                for (B footnote : references) {
                    if (unusedReferences.contains(footnote)) {
                        unused.add(footnote);
                    } else {
                        used.add(footnote);
                    }
                }
                Collections.sort(used, getReferenceComparator());
                Collections.sort(unused, getReferenceComparator());
                references.clear();
                references.addAll(used);
                references.addAll(unused);
                break;
        }

        markdown.blankLine();
        for (B reference : references) {
            renderReferenceBlock(reference, context, markdown);
        }
        markdown.blankLine();
        repositoryNodesDone = true;
    }

    protected void renderReference(B node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!repositoryNodesDone) {
            switch (getReferencePlacement()) {
                case AS_IS:
                    renderReferenceBlock(node, context, markdown);
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
}
