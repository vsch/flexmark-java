package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.CopyOnWriteRef;
import com.vladsch.flexmark.util.collection.OrderedMap;
import com.vladsch.flexmark.util.collection.OrderedSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class NodeClassifierVisitor extends NodeVisitorBase implements NodeTracker {
    private final OrderedMap<Class<?>, Set<Class<?>>> myExclusionMap;
    private final OrderedSet<Class<?>> myExclusionSet;
    private final HashMap<Integer, BitSet> myNodeAncestryMap;
    private final Stack<BitSet> myNodeAncestryBitSetStack = new Stack<>();
    private final CopyOnWriteRef<BitSet> myNodeAncestryBitSet = new CopyOnWriteRef<>(new BitSet(), value -> value != null ? (BitSet) value.clone() : new BitSet());

    private static final BitSet EMPTY_SET = new BitSet();
    private boolean myClassificationDone = false;

    private final ClassifyingNodeTracker myClassifyingNodeTracker;

    public NodeClassifierVisitor(Map<Class<? extends Node>, Set<Class<?>>> exclusionMap) {
        myClassifyingNodeTracker = new ClassifyingNodeTracker(this, exclusionMap);
        myExclusionMap = myClassifyingNodeTracker.getExclusionMap();
        myNodeAncestryMap = myClassifyingNodeTracker.getNodeAncestryMap();
        myExclusionSet = myClassifyingNodeTracker.getExclusionSet();
    }

    public @NotNull ClassifyingNodeTracker classify(@NotNull Node node) {
        // no double dipping
        assert !myClassificationDone;
        visit(node);
        myClassificationDone = true;
        return myClassifyingNodeTracker;
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
        if (myClassificationDone) {
            if (node.getParent() == null) {
                throw new IllegalStateException("Node must be inserted into the document before calling node tracker nodeAdded functions");
            }

            if (!(node.getParent() instanceof Document)) {
                int parentIndex = myClassifyingNodeTracker.getItems().indexOf(node.getParent());
                if (parentIndex == -1) {
                    throw new IllegalStateException("Parent node: " + node.getParent() + " of " + node + " is not tracked, some post processor forgot to call tracker.nodeAdded().");
                }

                BitSet ancestorBitSet = myNodeAncestryMap.get(parentIndex);
                myNodeAncestryBitSet.setValue(ancestorBitSet);
            }

            // let'er rip to update the descendants
            myNodeAncestryBitSetStack.clear();
            visit(node);
        }
    }

    void pushNodeAncestry() {
        if (!myExclusionMap.isEmpty()) {
            myNodeAncestryBitSetStack.push(myNodeAncestryBitSet.getImmutable());
        }
    }

    void popNodeAncestry() {
        myNodeAncestryBitSet.setValue(myNodeAncestryBitSetStack.pop());
    }

    boolean updateNodeAncestry(Node node, CopyOnWriteRef<BitSet> nodeAncestryBitSet) {
        Node parent = node.getParent();
        if (!myExclusionMap.isEmpty() && !(node instanceof Document)) {
            // add flags if needed
            BitSet bitSet = nodeAncestryBitSet.getPeek();

            int index = myClassifyingNodeTracker.getItems().indexOf(node);
            if (index == -1) {
                throw new IllegalStateException("Node: " + node + " is not tracked, some post processor forgot to call tracker.nodeAdded().");
            }

            if (myExclusionSet != null && !myExclusionSet.isEmpty()) {
                Iterator<Class<?>> iterator = ((Set<Class<?>>) myExclusionSet).iterator();

                while (iterator.hasNext()) {
                    Class<?> nodeType = iterator.next();
                    if (nodeType.isInstance(node)) {
                        // get the index of this exclusion
                        int i = myExclusionSet.indexOf(nodeType);
                        assert i != -1;
                        if (!bitSet.get(i)) {
                            bitSet = nodeAncestryBitSet.getMutable();
                            bitSet.set(i);
                        }
                    }
                }
            }

            if (myClassificationDone && myNodeAncestryBitSetStack.size() > 1) {
                // see if we can stop
                // now store the stuff for the node index
                BitSet oldBitSet = myNodeAncestryMap.get(index);
                if (oldBitSet != null && oldBitSet.equals(bitSet)) {
                    // no need to process descendants of this node
                    return false;
                }
            }

            if (!bitSet.isEmpty()) {
                myNodeAncestryMap.put(index, nodeAncestryBitSet.getImmutable());
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
        if (!myClassificationDone) {
            // initial collection phase
            if (!(parent instanceof Document)) {
                myClassifyingNodeTracker.nodeAdded(parent);
            }
        } else {
            // postProcessor modification update phase
        }

        if (parent.getFirstChild() != null) {
            pushNodeAncestry();
            if (updateNodeAncestry(parent, myNodeAncestryBitSet)) {
                super.visitChildren(parent);
            }
            popNodeAncestry();
        } else {
            updateNodeAncestry(parent, myNodeAncestryBitSet);
        }
    }
}
