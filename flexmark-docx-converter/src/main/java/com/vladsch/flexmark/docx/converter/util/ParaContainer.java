package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.P;

public interface ParaContainer {

    /**
     * add a new P. Don't change anything about it, just add it to its proper container
     *
     * @param p to be added to its parent container
     */
    void addP(P p);

    /**
     * Get the last P of the container or null if there is none
     *
     * @return last P or none
     */
    P getLastP();
}
