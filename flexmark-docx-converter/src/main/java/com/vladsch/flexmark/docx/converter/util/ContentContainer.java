package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;

import java.util.List;

public interface ContentContainer {
    /**
     * Get the content list
     *
     * @return list of content
     */
    List<Object> getContent();

    /**
     * Get the containers relationship. Needed for footnotes that contain links and probably images
     *
     * @return relationship part for the container.
     */
    RelationshipsPart getRelationshipsPart();

    /*
     * Get the part
     */
    Part getContainerPart();

    /**
     * Get the last added content element.
     * <p>
     * May not be the last element of {@link #getContent()} if elements
     * are being inserted into the middle of content.
     *
     * @return last content element or null if none
     */
    Object getLastContentElement();

    /**
     * Add element to content
     * <p>
     * May not be added at the end of content but inserted into the content list
     *
     * @param element element to add
     */
    void addContentElement(Object element);
}
