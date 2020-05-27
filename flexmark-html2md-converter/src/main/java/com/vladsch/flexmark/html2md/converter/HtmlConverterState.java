package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.html.Attributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Node;

import java.util.LinkedList;
import java.util.List;

public class HtmlConverterState {
    final @NotNull Node myParent;
    final List<Node> myElements;
    int myIndex;
    final @NotNull Attributes myAttributes;
    private @Nullable LinkedList<Runnable> myPrePopActions;

    HtmlConverterState(@NotNull Node parent) {
        myParent = parent;
        myElements = parent.childNodes();
        myIndex = 0;
        myAttributes = new Attributes();
        myPrePopActions = null;
    }

    public Node getParent() {
        return myParent;
    }

    public List<Node> getElements() {
        return myElements;
    }

    public int getIndex() {
        return myIndex;
    }

    public Attributes getAttributes() {
        return myAttributes;
    }

    public LinkedList<Runnable> getPrePopActions() {
        return myPrePopActions;
    }

    public void addPrePopAction(Runnable action) {
        if (myPrePopActions == null) {
            myPrePopActions = new LinkedList<>();
        }
        myPrePopActions.add(action);
    }

    public void runPrePopActions() {
        if (myPrePopActions != null) {
            int iMax = myPrePopActions.size();
            for (int i = iMax; i-- > 0; ) {
                myPrePopActions.get(i).run();
            }
        }
    }

    @Override
    public String toString() {
        return "State{" +
                "myParent=" + myParent +
                ", myElements=" + myElements +
                ", myIndex=" + myIndex +
                ", myAttributes=" + myAttributes +
                '}';
    }
}
