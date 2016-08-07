package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitorBase;
import com.vladsch.flexmark.util.NodeTracker;

import java.util.*;

public class NodeClassifierVisitor extends NodeVisitorBase implements NodeTracker {
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
        visit(node);
        myClassificationDone = true;
        return myClassifyingNodeTracker;
    }

    @Override
    public void visit(Node node) {
        visitChildren(node);
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
            node.getClass();
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
                        if (!bitSet.get(i) && !nodeAncestryBitSet.isMutable()) {
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
    public void visitChildren(Node parent) {
        if (!myClassificationDone) {
            // initial collection phase
            if (!(parent instanceof Document)) {
                myClassifyingNodeTracker.nodeAdded((Node) parent);
            }
        } else {
            // postProcessor modification update phase
        }

        if (parent.getFirstChild() != null) {
            pushNodeAncestry();
            if (updateNodeAncestry((Node) parent, myNodeAncestryBitSet)) {
                super.visitChildren(parent);
            }
            popNodeAncestry();
        } else {
            updateNodeAncestry((Node) parent, myNodeAncestryBitSet);
        }
    }
}
