package com.vladsch.flexmark.docx.converter;

import org.docx4j.wml.R;

public interface RunContainer {

    /**
     * add a new R. Don't change anything about it, just add it to its proper container
     *
     * @param r to be added to its parent container
     */
    void addR(R r);
}
