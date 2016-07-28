package com.vladsch.flexmark.ext.gfm.tasklist.internal;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.internal.ReferencePreProcessorFactory;
import com.vladsch.flexmark.internal.util.Parsing;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.BulletListItem;
import com.vladsch.flexmark.node.ListItem;
import com.vladsch.flexmark.node.Paragraph;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessor;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

public class TaskListParagraphPreProcessor implements ParagraphPreProcessor {
    static class TaskListParsing extends Parsing {
        final Pattern TASK_LIST_MARKER;

        public TaskListParsing(DataHolder options) {
            super(options);
            TASK_LIST_MARKER = Pattern.compile("^\\[" + super.ADDITIONAL_CHARS_SET("?") + "[ xX]\\](?: |\t|\r|\n|$)");
        }
    }

    final TaskListParsing myParsing;
    final boolean convertOrderedListItems;

    public TaskListParagraphPreProcessor(DataHolder options) {
        myParsing = new TaskListParsing(options);
        convertOrderedListItems = options.get(TaskListExtension.CONVERT_ORDERED_LIST_ITEMS);
    }

    @Override
    public int preProcessBlock(Paragraph block, ParserState state) {
        Block listItem = block.getParent();
        if ((listItem instanceof BulletListItem || listItem instanceof ListItem && convertOrderedListItems) && block.getPrevious() == null) {
            // first paragraph of a bullet list item
            BasedSequence chars = block.getChars();
            //if (chars.length() >= 3 && chars.charAt(0) == '[' && chars.charAt(2) == ']'
            //        && (chars.charAt(1) == ' ' || chars.charAt(1) == 'x' || chars.charAt(1) == 'X')
            //        && (chars.length() <= 3 || chars.charAt(3) == ' ' || chars.charAt(3) == '\t' || chars.charAt(3) == '\r' || chars.charAt(3) == '\n')) {
            if (myParsing.TASK_LIST_MARKER.matcher(chars).find()) {
                // task item
                TaskListItem taskListItem = new TaskListItem((ListItem) listItem);
                taskListItem.setTight(((ListItem) listItem).isTight());
                BasedSequence taskOpeningMarker = chars.subSequence(0, 3);
                taskListItem.setTaskOpeningMarker(taskOpeningMarker);
                listItem.insertBefore(taskListItem);
                listItem.unlink();

                //TaskListItemMarker marker = new TaskListItemMarker(taskOpeningMarker);
                //block.insertBefore(marker);
                return 3 + chars.countChars(BasedSequenceImpl.WHITESPACE_CHARS, 3, chars.length());
            }
        }
        return 0;
    }

    public static class Factory implements ParagraphPreProcessorFactory {
        @Override
        public Set<Class<? extends ParagraphPreProcessorFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends ParagraphPreProcessorFactory>> getBeforeDependents() {
            return Collections.singleton(ReferencePreProcessorFactory.class);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public ParagraphPreProcessor create(ParserState state) {
            return new TaskListParagraphPreProcessor(state.getProperties());
        }
    }
}
