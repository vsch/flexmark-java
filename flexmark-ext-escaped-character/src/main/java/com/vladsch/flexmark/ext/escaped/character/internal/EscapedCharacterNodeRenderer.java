package com.vladsch.flexmark.ext.escaped.character.internal;

import com.vladsch.flexmark.ext.escaped.character.EscapedCharacter;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class EscapedCharacterNodeRenderer implements NodeRenderer {
    final private EscapedCharacterOptions options;

    public EscapedCharacterNodeRenderer(DataHolder options) {
        this.options = new EscapedCharacterOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(EscapedCharacter.class, this::render));

        return set;
    }

    private void render(EscapedCharacter node, NodeRendererContext context, HtmlWriter html) {
        html.text(node.getChars().unescape());
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new EscapedCharacterNodeRenderer(options);
        }
    }
}
