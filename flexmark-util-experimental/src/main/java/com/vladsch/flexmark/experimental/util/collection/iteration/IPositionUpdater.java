package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.sequence.PositionAnchor;

import java.util.Iterator;
import java.util.List;

public interface IPositionUpdater<T, P extends IPositionHolder<T, P>> extends IPreviewPositionListener {
    void addPositionListener(IPositionListener listener);
    void removePositionListener(IPositionListener listener);

    List<T> getList();

    default P getPosition(int index) {
        return getPosition(index, PositionAnchor.CURRENT);
    }

    void unframe(P position);

    P getPosition(int index, PositionAnchor anchor);

    void clear();

    Iterator<P> iterator(P position);
}
