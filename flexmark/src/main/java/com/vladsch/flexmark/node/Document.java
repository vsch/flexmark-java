package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.*;

import java.util.List;
import java.util.Map;

public class Document extends Block implements MutablePropertyHolder {
    final private MutablePropertyHolderImpl propertyHolder = new MutablePropertyHolderImpl();

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
    public PropertyHolder getReadOnlyCopy() {
        return new PropertyHolderImpl(this);
    }

    @Override
    public Map<PropertyKey, Object> getProperties() {return propertyHolder.getProperties();}

    @Override
    public boolean contains(PropertyKey key) {return propertyHolder.contains(key);}

    @Override
    public <T> T get(PropertyKey<T> key) {return propertyHolder.get(key);}

    @Override
    public <T> T getOrDefault(PropertyKey<T> key) {return propertyHolder.getOrDefault(key);}

    @Override
    public <T> T getOrNew(PropertyKey<T> key) {return propertyHolder.getOrNew(key);}

    @Override
    public <T> MutablePropertyHolder set(PropertyKey<T> key, T value) { return propertyHolder.set(key, value);}

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
