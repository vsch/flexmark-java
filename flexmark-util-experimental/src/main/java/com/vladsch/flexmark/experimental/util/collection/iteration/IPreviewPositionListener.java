package com.vladsch.flexmark.experimental.util.collection.iteration;

public interface IPreviewPositionListener extends IPositionListener {
    /**
     * before delete Notification
     *
     * @param index at which delete will be performed, list contents and size at this point have not changed
     * @param count of elements deleted
     */
    void deleting(int index, int count);

    /**
     * Before set Notification
     * <p>
     * NOTE: the returned class should be the same or a subclass of original object's class
     *
     * @param index at which set is being performed, list contents and size at this point are not updated
     * @param value new value for the element
     * @return value to use for setting the element
     */
    Object changing(int index, Object value);

    /**
     * After set Notification
     * <p>
     * NOTE: the returned class should be the same or a subclass of original object's class
     *
     * @param index    at which set is being performed, list contents and size at this point are already updated
     * @param oldValue value at index before setting
     * @param newValue value at index before setting
     */
    void changed(int index, Object oldValue, Object newValue);
}
