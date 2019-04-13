package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.R;

public interface RunContainer {

    /**
     * add a new R. Don't change anything about it, just add it to its proper container
     *
     * @param r to be added to its parent container
     */
    void addR(R r);

    /**
     * Get the last R of the container or null if there is none
     *
     * @return last R or none
     */
    R getLastR();
}
