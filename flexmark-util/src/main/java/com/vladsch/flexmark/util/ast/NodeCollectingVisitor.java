package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.ClassificationBag;
import com.vladsch.flexmark.util.collection.SubClassingBag;

import java.util.*;
import java.util.function.Function;

public class NodeCollectingVisitor {
    public static final Function<Node, Class> NODE_CLASSIFIER = Node::getClass;
    private static final Class[] EMPTY_CLASSES = new Class[0];

    private final HashMap<Class, List<Class>> mySubClassMap;
    private final HashSet<Class> myIncluded;
    private final HashSet<Class> myExcluded;
    private final ClassificationBag<Class, Node> myNodes;
    private final Class[] myClasses;

    public NodeCollectingVisitor(Set<Class> classes) {
        myClasses = classes.toArray(EMPTY_CLASSES);

        mySubClassMap = new HashMap<>();
        myIncluded = new HashSet<>();
        myIncluded.addAll(classes);

        for (Class clazz : classes) {
            ArrayList<Class> classList = new ArrayList<>(1);
            classList.add(clazz);
            mySubClassMap.put(clazz, classList);
        }

        myExcluded = new HashSet<>();

        myNodes = new ClassificationBag<>(NODE_CLASSIFIER);
    }

    public void collect(Node node) {
        visit(node);
    }

    public SubClassingBag<Node> getSubClassingBag() {
        return new SubClassingBag<>(myNodes, mySubClassMap);
    }

    private void visit(Node node) {
        Class nodeClass = node.getClass();
        if (myIncluded.contains(nodeClass)) {
            myNodes.add(node);
        } else if (!myExcluded.contains(nodeClass)) {
            // see if implements one of the original classes passed in
            for (Class clazz : myClasses) {
                if (clazz.isInstance(node)) {
                    // this class is included
                    myIncluded.add(nodeClass);
                    List<Class> classList = mySubClassMap.get(clazz);
                    if (classList == null) {
                        classList = new ArrayList<>(2);
                        classList.add(clazz);
                        classList.add(nodeClass);
                        mySubClassMap.put(clazz, classList);
                    } else {
                        classList.add(nodeClass);
                    }

                    myNodes.add(node);
                    visitChildren(node);
                    return;
                }
            }

            // not of interest, exclude for next occurrence
            myExcluded.add(nodeClass);
        }
        visitChildren(node);
    }

    private void visitChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = node.getNext();
            visit(node);
            node = next;
        }
    }
}
