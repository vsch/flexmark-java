package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class GfmUsersIssuesSample {
    public static void main(String[] args) {
        MutableDataHolder options = new MutableDataSet()
                .set(Parser.EXTENSIONS, Arrays.asList(StrikethroughExtension.create(), GfmUsersExtension.create(), GfmIssuesExtension.create(), AutolinkExtension.create()));
        Parser parser = Parser.builder(options).build();
        Document document = parser.parse("Hello, @world, and #1!");
        new NodeVisitor(new VisitHandler<?>[] { }) {
            @Override
            public void processNode(@NotNull Node node, boolean withChildren, @NotNull BiConsumer<Node, Visitor<Node>> processor) {
                System.out.println("Node: " + node);
                super.processNode(node, withChildren, processor);
            }
        }.visit(document);
    }
}
