package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.ClassificationBag;
import com.vladsch.flexmark.util.collection.SubClassingBag;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class NodeCollectingVisitor {
    final public static Function<Node, Class<?>> NODE_CLASSIFIER = Node::getClass;
    final private static Class<?>[] EMPTY_CLASSES = new Class<?>[0];

    final private @NotNull HashMap<Class<?>, List<Class<?>>> subClassMap;
    final private @NotNull HashSet<Class<?>> included;
    final private @NotNull HashSet<Class<?>> excluded;
    final private @NotNull ClassificationBag<Class<?>, Node> nodes;
    final private @NotNull Class<?>[] classes;

    public NodeCollectingVisitor(@NotNull Set<Class<?>> classes) {
        this.classes = classes.toArray(EMPTY_CLASSES);

        subClassMap = new HashMap<>();
        included = new HashSet<>();
        included.addAll(classes);

        for (Class<?> clazz : classes) {
            ArrayList<Class<?>> classList = new ArrayList<>(1);
            classList.add(clazz);
            subClassMap.put(clazz, classList);
        }

        excluded = new HashSet<>();
        nodes = new ClassificationBag<>(NODE_CLASSIFIER);
    }

    public void collect(@NotNull Node node) {
        visit(node);
    }

    public SubClassingBag<Node> getSubClassingBag() {
        return new SubClassingBag<>(nodes, subClassMap);
    }

    private void visit(@NotNull Node node) {
        Class<?> nodeClass = node.getClass();
        if (included.contains(nodeClass)) {
            nodes.add(node);
        } else if (!excluded.contains(nodeClass)) {
            // see if implements one of the original classes passed in
            for (Class<?> clazz : classes) {
                if (clazz.isInstance(node)) {
                    // this class is included
                    included.add(nodeClass);
                    List<Class<?>> classList = subClassMap.get(clazz);
                    if (classList == null) {
                        classList = new ArrayList<>(2);
                        classList.add(clazz);
                        classList.add(nodeClass);
                        subClassMap.put(clazz, classList);
                    } else {
                        classList.add(nodeClass);
                    }

                    nodes.add(node);
                    visitChildren(node);
                    return;
                }
            }

            // not of interest, exclude for next occurrence
            excluded.add(nodeClass);
        }
        visitChildren(node);
    }

    private void visitChildren(@NotNull Node parent) {
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
