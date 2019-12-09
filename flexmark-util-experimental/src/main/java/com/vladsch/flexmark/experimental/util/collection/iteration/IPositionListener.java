package com.vladsch.flexmark.experimental.util.collection.iteration;

public interface IPositionListener {
    /**
     * insert Notification
     *
     * @param index at which insert was performed, list contents and size at this point are already updated
     * @param count of elements inserted
     */
    void inserted(int index, int count);

    /**
     * delete Notification
     *
     * @param index at which delete was performed, list contents and size at this point are already updated
     * @param count of elements deleted
     */
    void deleted(int index, int count);
}
