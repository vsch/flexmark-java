package com.vladsch.flexmark.experimental.util.collection.iteration;


import java.util.List;

public class PositionList<T> extends PositionListBase<T, Position<T>> {
    public PositionList(List<T> list) {
        super(list, Position::new);
    }
}
