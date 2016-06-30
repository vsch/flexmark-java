package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Text;

/**
 * A task list item marker
 */
public class TaskListItemMarker extends Text {
    public TaskListItemMarker(BasedSequence chars) {
        super(chars);

        if (chars.length() != 3 || chars.charAt(0) != '[' || chars.charAt(2) != ']' || (chars.charAt(1) != ' ' && chars.charAt(1) != 'x' && chars.charAt(1) != 'X')) {
            throw new IllegalArgumentException("TaskListItemMarkers can only be [ ], [x] or [X]");
        }
    }

    public boolean isItemDoneMarker() {
        return !(getChars().charAt(1) == ' ');
    }

    @Override
    protected String toStringAttributes() {
        return super.toStringAttributes() + " isItemDone=" + isItemDoneMarker();
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        out.append(" isItemDone=").append(isItemDoneMarker());
        super.getAstExtra(out);
    }
}
