package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.AttributeProviderAdapter;
import com.vladsch.flexmark.ast.util.AttributeProvidingHandler;
import com.vladsch.flexmark.ast.util.AttributeProvidingVisitor;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;

import static com.vladsch.flexmark.html.renderer.AttributablePart.LINK;
import static com.vladsch.flexmark.util.html.Attribute.LINK_STATUS_ATTR;

public class ZzzzzzAttributeProvider implements AttributeProvider {
    private final String missingTargetClass;
    private final String localOnlyTargetClass;
    private final AttributeProviderAdapter nodeAdapter;

    public ZzzzzzAttributeProvider(NodeRendererContext context) {
        DataHolder options = context.getOptions();
        this.localOnlyTargetClass = options.get(ZzzzzzExtension.LOCAL_ONLY_TARGET_CLASS);
        this.missingTargetClass = options.get(ZzzzzzExtension.MISSING_TARGET_CLASS);

        this.nodeAdapter = new AttributeProviderAdapter(
                new AttributeProvidingHandler<>(Image.class, new AttributeProvidingVisitor<Image>() {
                    @Override
                    public void setAttributes(Image node, AttributablePart part, Attributes attributes) {
                        ZzzzzzAttributeProvider.this.setLinkAttributes(node, part, attributes);
                    }
                }),
                new AttributeProvidingHandler<>(ImageRef.class, new AttributeProvidingVisitor<ImageRef>() {
                    @Override
                    public void setAttributes(ImageRef node, AttributablePart part, Attributes attributes) {
                        ZzzzzzAttributeProvider.this.setLinkAttributes(node, part, attributes);
                    }
                }),
                new AttributeProvidingHandler<>(LinkRef.class, new AttributeProvidingVisitor<LinkRef>() {
                    @Override
                    public void setAttributes(LinkRef node, AttributablePart part, Attributes attributes) {
                        ZzzzzzAttributeProvider.this.setLinkAttributes(node, part, attributes);
                    }
                }),
                new AttributeProvidingHandler<>(Link.class, new AttributeProvidingVisitor<Link>() {
                    @Override
                    public void setAttributes(Link node, AttributablePart part, Attributes attributes) {
                        ZzzzzzAttributeProvider.this.setLinkAttributes(node, part, attributes);
                    }
                })
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
            String linkStatus = attributes.getValue(LINK_STATUS_ATTR);
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
