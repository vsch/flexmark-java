package com.vladsch.flexmark.ext.d2;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class D2Node extends Node {
    private BasedSequence key;
    //private List<BasedSequence> values;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { key };
    }

    public D2Node(BasedSequence key, List<BasedSequence> values) {
        this.key = key;
        //this.values = values;
        for (BasedSequence value : values) {
            appendChild(new D2Value(value));
        }
    }

    public String getKey() {
        return key.toString();
    }

    public BasedSequence getKeySequence() {
        return key;
    }

    public void setKey(BasedSequence key) {
        this.key = key;
    }

    public List<String> getValues() {
        ArrayList<String> list = new ArrayList<>();
        Node child = getFirstChild();
        while (child != null) {
            list.add(child.getChars().toString());
            child = child.getNext();
        }
        return list;
    }

    public List<BasedSequence> getValuesSequences() {
        ArrayList<BasedSequence> list = new ArrayList<>();
        Node child = getFirstChild();
        while (child != null) {
            list.add(child.getChars());
            child = child.getNext();
        }
        return list;
    }
}
