package com.vladsch.flexmark.docx.converter.util;

import java.util.List;

public interface ContentContainer {
    /**
     * Get the content list
     * @return list of content
     */
    List<Object> getContent();

    /**
     * Get the last added content element.
     *
     * May not be the last element of {@link #getContent()} if elements
     * are being inserted into the middle of content.
     *
     * @return last content element or null if none
     */
    Object getLastContentElement();

    /**
     * Add element to content
     *
     * May not be added at the end of content but inserted into the content list
     *
     */
    void addContentElement(Object element);
}
