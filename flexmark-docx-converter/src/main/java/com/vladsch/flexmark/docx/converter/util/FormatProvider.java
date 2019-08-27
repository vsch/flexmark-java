package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.wml.Style;

public interface FormatProvider<T> {

    /**
     * get the node for this formatting block
     *
     * @return node for this format provider
     */
    T getProviderFrame();

    /**
     * initialize internal stuff based on the parent for future use
     * optionally add elements to doc before main elements
     * any P creates will call the getPPr(), getParaRPr() and addP() for this provider
     * so don't create elements until you are ready to handle these calls
     */
    void open();

    /**
     * finalize, add elements to the document after main part as needed
     * after this method returns no more calls will be made to this provider
     */
    void close();

    /**
     * these are the hard-coded styles on which this block is based
     * used for reference purposes by children to figure out what they need to combine
     *
     * @return style id
     */
    String getStyleId();

    Style getStyle();
}
