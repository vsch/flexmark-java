package com.vladsch.flexmark.util.collection.iteration;

public interface IPositionListener {
    /**
     * Insert Notification
     *
     * @param index at which insert was performed, list contents and size at this point are already updated
     * @param count of elements inserted
     */
    void inserted(int index, int count);
    /**
     * Delete Notification
     *
     * @param index at which delete was performed, list contents and size at this point are already updated
     * @param count of elements inserted
     */
    void deleted(int index, int count);
}
