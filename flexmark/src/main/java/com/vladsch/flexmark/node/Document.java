package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.PropertyHolder;
import com.vladsch.flexmark.internal.util.PropertyHolderImpl;
import com.vladsch.flexmark.internal.util.PropertyKey;

import java.util.HashMap;
import java.util.List;

public class Document extends Block implements PropertyHolder {
    final private PropertyHolderImpl propertyHolder = new PropertyHolderImpl();

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public Document() {

    }

    public Document(BasedSequence chars) {
        super(chars);
    }

    public Document(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public Document(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public HashMap<PropertyKey, Object> getProperties() {return propertyHolder.getProperties();}

    @Override
    public boolean contains(PropertyKey key) {return propertyHolder.contains(key);}

    @Override
    public <T> T getValue(PropertyKey<T> key) {return propertyHolder.getValue(key);}

    @Override
    public <T> T getValueOrDefault(PropertyKey<T> key) {return propertyHolder.getValueOrDefault(key);}

    @Override
    public <T> void setProperty(PropertyKey<T> key, T value) {propertyHolder.setProperty(key, value);}

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
