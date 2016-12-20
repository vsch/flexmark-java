package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EmojiJiraRenderer implements NodeRenderer {
    public static final HashMap<String, String> shortCutMap = new HashMap<>();
    static {
        shortCutMap.put("smile", ":)");
        shortCutMap.put("frowning", ":(");
        shortCutMap.put("stuck_out_tongue", ":P");
        shortCutMap.put("grinning", ":D");
        shortCutMap.put("wink", ";)");
        shortCutMap.put("thumbsup", "(y)");
        shortCutMap.put("thumbsdown", "(n)");
        shortCutMap.put("information_source", "(i)");
        shortCutMap.put("white_check_mark", "(/)");
        shortCutMap.put("x", "(x)");
        shortCutMap.put("warning", "(!)");
        shortCutMap.put("heavy_plus_sign", "(+)");
        shortCutMap.put("heavy_minus_sign", "(-)");
        shortCutMap.put("question", "(?)");
        shortCutMap.put("bulb", "(on)");
        shortCutMap.put("star", "(*)");
        shortCutMap.put("triangular_flag_on_post", "(flag)");
        shortCutMap.put("crossed_flags", "(flagoff)");
    }
    public EmojiJiraRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Emoji.class, new CustomNodeRenderer<Emoji>() {
            @Override
            public void render(Emoji node, NodeRendererContext context, HtmlWriter html) {
                EmojiJiraRenderer.this.render(node, context, html);
            }
        }));
        return set;
    }

    private void render(Emoji node, NodeRendererContext context, HtmlWriter html) {
        Emoji emoji = (Emoji) node;
        String shortcut = shortCutMap.get(emoji.getText().toString());
        if (shortcut == null) {
            // output as text
            html.text(":");
            context.renderChildren(node);
            html.text(":");
        } else {
            html.raw(shortcut);
        }
    }
}
