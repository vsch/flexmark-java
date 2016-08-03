package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.ast.AttributeProviderAdapter;
import com.vladsch.flexmark.internal.util.ast.AttributeProvidingHandler;
import com.vladsch.flexmark.internal.util.options.Attributes;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.node.*;

import static com.vladsch.flexmark.html.renderer.AttributablePart.LINK;
import static com.vladsch.flexmark.internal.util.options.Attribute.LINK_STATUS;

public class ZzzzzzAttributeProvider implements AttributeProvider {
    final private String missingTargetClass;
    final private String localOnlyTargetClass;
    final private AttributeProviderAdapter nodeAdapter;

    public ZzzzzzAttributeProvider(NodeRendererContext context) {
        DataHolder options = context.getOptions();
        this.localOnlyTargetClass = options.get(ZzzzzzExtension.LOCAL_ONLY_TARGET_CLASS);
        this.missingTargetClass = options.get(ZzzzzzExtension.MISSING_TARGET_CLASS);

        this.nodeAdapter = new AttributeProviderAdapter(
                new AttributeProvidingHandler<>(Image.class, this::setLinkAttributes),
                new AttributeProvidingHandler<>(ImageRef.class, this::setLinkAttributes),
                new AttributeProvidingHandler<>(LinkRef.class, this::setLinkAttributes),
                new AttributeProvidingHandler<>(Link.class, this::setLinkAttributes)
        );
    }

    @Override
    public void setAttributes(Node node, AttributablePart part, Attributes attributes) {
        nodeAdapter.setAttributes(node, part, attributes);
    }

    private void setLinkAttributes(LinkNode node, AttributablePart part, Attributes attributes) {
        setLinkAttributes(part, attributes);
    }

    private void setLinkAttributes(AttributablePart part, Attributes attributes) {
        if (part == LINK) {
            String linkStatus = attributes.getValue(LINK_STATUS);
            if (LinkStatus.NOT_FOUND.isStatus(linkStatus)) {
                attributes.addValue("class", missingTargetClass);
            } else if (ZzzzzzExtension.LOCAL_ONLY.isStatus(linkStatus)) {
                attributes.addValue("class", localOnlyTargetClass);
            }
        }
    }

    public static class Factory extends IndependentAttributeProviderFactory {
        //@Override
        //public Set<Class<? extends AttributeProviderFactory>> getAfterDependents() {
        //    return null;
        //}
        //
        //@Override
        //public Set<Class<? extends AttributeProviderFactory>> getBeforeDependents() {
        //    return null;
        //}
        //
        //@Override
        //public boolean affectsGlobalScope() {
        //    return false;
        //}

        @Override
        public AttributeProvider create(NodeRendererContext context) {
            return new ZzzzzzAttributeProvider(context);
        }
    }
}
