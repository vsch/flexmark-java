package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.users.GfmUsersExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;

public class GfmUsersIssuesSample {
    public static void main(String[] args) {
        final MutableDataHolder options = new MutableDataSet()
                .set(Parser.EXTENSIONS, Arrays.asList(StrikethroughExtension.create(), GfmUsersExtension.create(), GfmIssuesExtension.create(), AutolinkExtension.create()));
        final Parser parser = Parser.builder(options).build();
        final Document document = parser.parse("Hello, @world, and #1!");
        new NodeVisitor(new VisitHandler<?>[] { }) {
            @Override
            public void visit(Node node) {
                System.out.println("Node: " + node);
                super.visit(node);
            }
        }.visit(document);
    }
}
