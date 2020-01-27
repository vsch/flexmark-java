package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.CopyOnWriteRef;
import com.vladsch.flexmark.util.collection.OrderedMap;
import com.vladsch.flexmark.util.collection.OrderedSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class NodeClassifierVisitor extends NodeVisitorBase implements NodeTracker {
    final private OrderedMap<Class<?>, Set<Class<?>>> exclusionMap;
    final private OrderedSet<Class<?>> exclusionSet;
    final private HashMap<Integer, BitSet> nodeAncestryMap;
    final private Stack<BitSet> nodeAncestryBitSetStack = new Stack<>();
    final private CopyOnWriteRef<BitSet> nodeAncestryBitSet = new CopyOnWriteRef<>(new BitSet(), value -> value != null ? (BitSet) value.clone() : new BitSet());

    final private static BitSet EMPTY_SET = new BitSet();
    private boolean isClassificationDone = false;

    final private ClassifyingNodeTracker classifyingNodeTracker;

    public NodeClassifierVisitor(Map<Class<? extends Node>, Set<Class<?>>> exclusionMap) {
        classifyingNodeTracker = new ClassifyingNodeTracker(this, exclusionMap);
        this.exclusionMap = classifyingNodeTracker.getExclusionMap();
        nodeAncestryMap = classifyingNodeTracker.getNodeAncestryMap();
        exclusionSet = classifyingNodeTracker.getExclusionSet();
    }

    public @NotNull ClassifyingNodeTracker classify(@NotNull Node node) {
        // no double dipping
        assert !isClassificationDone;
        visit(node);
        isClassificationDone = true;
        return classifyingNodeTracker;
    }

    @Override
    public void visit(@NotNull Node node) {
        visitChildren(node);
    }

    // @formatter:off
    @Override public void nodeRemoved(@NotNull Node node) { }
    @Override public void nodeRemovedWithChildren(@NotNull Node node) { }
    @Override public void nodeRemovedWithDescendants(@NotNull Node node) { }
    @Override public void nodeAddedWithChildren(@NotNull Node node) { nodeAdded(node); }
    @Override public void nodeAddedWithDescendants(@NotNull Node node) { nodeAdded(node); }
    // @formatter:on

    @Override
    public void nodeAdded(@NotNull Node node) {
        if (isClassificationDone) {
            if (node.getParent() == null) {
                throw new IllegalStateException("Node must be inserted into the document before calling node tracker nodeAdded functions");
            }

            if (!(node.getParent() instanceof Document)) {
                int parentIndex = classifyingNodeTracker.getItems().indexOf(node.getParent());
                if (parentIndex == -1) {
                    throw new IllegalStateException("Parent node: " + node.getParent() + " of " + node + " is not tracked, some post processor forgot to call tracker.nodeAdded().");
                }

                BitSet ancestorBitSet = nodeAncestryMap.get(parentIndex);
                nodeAncestryBitSet.setValue(ancestorBitSet);
            }

            // let'er rip to update the descendants
            nodeAncestryBitSetStack.clear();
            visit(node);
        }
    }

    void pushNodeAncestry() {
        if (!exclusionMap.isEmpty()) {
            nodeAncestryBitSetStack.push(nodeAncestryBitSet.getImmutable());
        }
    }

    void popNodeAncestry() {
        nodeAncestryBitSet.setValue(nodeAncestryBitSetStack.pop());
    }

    boolean updateNodeAncestry(Node node, CopyOnWriteRef<BitSet> nodeAncestryBitSet) {
        if (!exclusionMap.isEmpty() && !(node instanceof Document)) {
            // add flags if needed
            BitSet bitSet = nodeAncestryBitSet.getPeek();
            assert bitSet != null;

            int index = classifyingNodeTracker.getItems().indexOf(node);
            if (index == -1) {
                throw new IllegalStateException("Node: " + node + " is not tracked, some post processor forgot to call tracker.nodeAdded().");
            }

            if (exclusionSet != null && !exclusionSet.isEmpty()) {
                Iterator<Class<?>> iterator = ((Set<Class<?>>) exclusionSet).iterator();

                while (iterator.hasNext()) {
                    Class<?> nodeType = iterator.next();
                    if (nodeType.isInstance(node)) {
                        // get the index of this exclusion
                        int i = exclusionSet.indexOf(nodeType);
                        assert i != -1;
                        if (!bitSet.get(i)) {
                            bitSet = nodeAncestryBitSet.getMutable();
                            assert bitSet != null;

                            bitSet.set(i);
                        }
                    }
                }
            }

            if (isClassificationDone && nodeAncestryBitSetStack.size() > 1) {
                // see if we can stop
                // now store the stuff for the node index
                BitSet oldBitSet = nodeAncestryMap.get(index);
                if (oldBitSet != null && oldBitSet.equals(bitSet)) {
                    // no need to process descendants of this node
                    return false;
                }
            }

            if (!bitSet.isEmpty()) {
                nodeAncestryMap.put(index, nodeAncestryBitSet.getImmutable());
            }
        }

        return true;
    }

    /**
     * Visit the child nodes.
     *
     * @param parent the parent node whose children should be visited
     */
    @Override
    public void visitChildren(@NotNull Node parent) {
        if (!isClassificationDone) {
            // initial collection phase
            if (!(parent instanceof Document)) {
                classifyingNodeTracker.nodeAdded(parent);
            }
        }

        if (parent.getFirstChild() != null) {
            pushNodeAncestry();
            if (updateNodeAncestry(parent, nodeAncestryBitSet)) {
                super.visitChildren(parent);
            }
            popNodeAncestry();
        } else {
            updateNodeAncestry(parent, nodeAncestryBitSet);
        }
    }
}
