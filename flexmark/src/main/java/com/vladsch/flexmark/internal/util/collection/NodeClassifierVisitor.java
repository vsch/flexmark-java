package com.vladsch.flexmark.internal.util.collection;

import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.node.AbstractVisitor;
import com.vladsch.flexmark.node.Node;

import java.util.*;

public class NodeClassifierVisitor extends AbstractVisitor implements NodeTracker {
    final private OrderedMap<Class<?>, Set<Class<?>>> myExclusionMap;
    final private OrderedSet<Class<?>> myExclusionSet;
    final private HashMap<Integer, BitSet> myNodeAncestryMap;
    final private Stack<BitSet> myNodeAncestryBitSetStack = new Stack<>();
    final private CopyOnWriteRef<BitSet> myNodeAncestryBitSet = new CopyOnWriteRef<>(new BitSet(), value -> value != null ? (BitSet) value.clone() : new BitSet());

    final private static BitSet EMPTY_SET = new BitSet();
    private boolean myClassificationDone = false;

    final private ClassifyingNodeTracker myClassifyingNodeTracker;

    public NodeClassifierVisitor(Map<Class<? extends Node>, Set<Class<?>>> exclusionMap) {
        myClassifyingNodeTracker = new ClassifyingNodeTracker(this, exclusionMap);
        myExclusionMap = myClassifyingNodeTracker.getExclusionMap();
        myNodeAncestryMap = myClassifyingNodeTracker.getNodeAncestryMap();
        myExclusionSet = myClassifyingNodeTracker.getExclusionSet();
    }

    public ClassifyingNodeTracker classify(Node node) {
        // no double dipping
        assert !myClassificationDone;
        node.accept(this);
        myClassificationDone = true;
        return myClassifyingNodeTracker;
    }

    // @formatter:off
    @Override public void nodeRemoved(Node node) { } 
    @Override public void nodeRemovedWithChildren(Node node) { } 
    @Override public void nodeRemovedWithDescendants(Node node) { }
    @Override public void nodeAddedWithChildren(Node node) { nodeAdded(node); }
    @Override public void nodeAddedWithDescendants(Node node) { nodeAdded(node); }
    // @formatter:on

    @Override
    public void nodeAdded(Node node) {
        if (myClassificationDone) {
            if (node.getParent() == null) {
                throw new IllegalStateException("Node must be inserted into the document before calling node tracker nodeAdded functions");
            }

            int parentIndex = myClassifyingNodeTracker.getItems().indexOf(node.getParent());
            if (parentIndex == -1) {
                throw new IllegalStateException("Parent node: " + node.getParent() + " of " + node + " is not tracked, some post processor forgot to call tracker.nodeAdded().");
            }

            BitSet ancestorBitSet = myNodeAncestryMap.get(parentIndex);
            myNodeAncestryBitSet.setValue(ancestorBitSet);

            // let'er rip to update the descendants
            myNodeAncestryBitSetStack.clear();
            node.accept(this);
        }
    }

    void pushNodeAncestry() {
        if (!myExclusionMap.isEmpty()) {
            if (!myNodeAncestryBitSet.getPeek().isEmpty()) {
                // have something in the set
                myNodeAncestryBitSetStack.push(myNodeAncestryBitSet.getImmutable());
            }
        }
    }

    void popNodeAncestry() {
        myNodeAncestryBitSet.setValue(myNodeAncestryBitSetStack.pop());
    }

    boolean updateNodeAncestry(Node node, CopyOnWriteRef<BitSet> nodeAncestryBitSet) {
        if (!myExclusionMap.isEmpty()) {
            // add flags if needed
            node.getClass();
            BitSet bitSet = nodeAncestryBitSet.getPeek();

            Set<Class<?>> excludedAncestors = myExclusionMap.get(node.getClass());
            Iterator<Class<?>> iterator = excludedAncestors.iterator();

            while (iterator.hasNext()) {
                Class<?> nodeType = iterator.next();
                if (nodeType.isInstance(node)) {
                    // get the index of this exclusion
                    int i = myExclusionSet.indexOf(nodeType);
                    assert i != -1;
                    if (!bitSet.get(i) && !nodeAncestryBitSet.isMutable()) {
                        bitSet = nodeAncestryBitSet.getMutable();
                        bitSet.set(i);
                    }
                }
            }

            int index = myClassifyingNodeTracker.getItems().indexOf(node);
            if (index == -1) {
                throw new IllegalStateException("Node: " + node + " is not tracked, some post processor forgot to call tracker.nodeAdded().");
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
    protected void visitChildren(Node parent) {
        if (!myClassificationDone) {
            // initial collection phase
            myClassifyingNodeTracker.nodeAdded(parent);
        } else {
            // postProcessor modification update phase
        }

        if (parent.hasChildren()) {
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
