package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.internal.util.options.Attributes;
import com.vladsch.flexmark.node.Node;

/**
 * Created by vlad on 2016-07-13.
 */
public class LinkRendering {
    final private LinkType myLinkType;
    final private String myUrl;
    final private String myText;
    final private Boolean myIsResolved;
    final private Attributes myAttributes;
    final private Node myNode;

    public LinkRendering(LinkType linkType, String url, Node node) {
        this(linkType, url, "", null, null, node);
    }

    public LinkRendering(LinkType linkType, String url, Attributes attributes, Node node) {
        this(linkType, url, "", true, attributes, node);
    }

    public LinkRendering(LinkType linkType, String url, String text, Node node) {
        this(linkType, url, text, null, null, node);
    }

    public LinkRendering(LinkType linkType, String url, String text, Attributes attributes, Node node) {
        this(linkType, url, text, true, attributes, node);
    }

    public LinkRendering(LinkType linkType, String url, String text, Boolean isResolved, Attributes attributes, Node node) {
        myLinkType = linkType;
        myUrl = url;
        myText = text;
        myIsResolved = isResolved;
        myAttributes = attributes == null ? new Attributes() : new Attributes(attributes);
        myNode = node;
    }

    public LinkType getLinkType() {
        return myLinkType;
    }

    public String getUrl() {
        return myUrl;
    }

    public String getText() {
        return myText;
    }

    public Attributes getAttributes() {
        return myAttributes;
    }

    public Node getNode() {
        return myNode;
    }

    public Boolean getIsResolved() {
        return myIsResolved;
    }

    // @formatter:off
    public LinkRendering withLinkType(LinkType myLinkType) { return myLinkType == this.myLinkType ? this : new LinkRendering(myLinkType, myUrl, myText, myIsResolved, myAttributes, myNode ); }
    public LinkRendering withUrl(String myUrl) { return myUrl.equals(this.myUrl) ? this : new LinkRendering(myLinkType, myUrl, myText, myIsResolved, myAttributes, myNode ); }
    public LinkRendering withText(String myText) { return myText.equals(this.myText) ? this : new LinkRendering(myLinkType, myUrl, myText, myIsResolved, myAttributes, myNode ); }
    public LinkRendering withIsResolved(Boolean myIsResolved) { return (myIsResolved == null && this.myIsResolved == null || myIsResolved != null && this.myIsResolved != null && myIsResolved == (boolean)this.myIsResolved) ? this : new LinkRendering(myLinkType, myUrl, myText, myIsResolved, myAttributes, myNode ); }
    public LinkRendering withAttributes(Attributes myAttributes) { return myAttributes == this.myAttributes ? this : new LinkRendering(myLinkType, myUrl, myText, myIsResolved, myAttributes, myNode ); }
    public LinkRendering withNode(Node myNode) { return myNode == this.myNode ? this : new LinkRendering(myLinkType, myUrl, myText, myIsResolved, myAttributes, myNode ); }
    // @formatter:on
}
