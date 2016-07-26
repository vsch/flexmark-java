package com.vladsch.flexmark.html.renderer;

public class LinkStatus {
    final public static LinkStatus UNKNOWN = new LinkStatus("UNKNOWN");
    final public static LinkStatus VALID = new LinkStatus("VALID");
    final public static LinkStatus UNCHECKED = new LinkStatus("UNCHECKED");
    final public static LinkStatus NOT_FOUND = new LinkStatus("NOT_FOUND");
    
    final private String myName;

    public LinkStatus(String name) {
        this.myName = name;
    }

    public String getName() {
        return myName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkStatus)) return false;

        LinkStatus status = (LinkStatus) o;

        return myName.equals(status.myName);
    }

    @Override
    public int hashCode() {
        return myName.hashCode();
    }

    public boolean isStatus(String status) {
        return myName.equals(status);
    }
}
