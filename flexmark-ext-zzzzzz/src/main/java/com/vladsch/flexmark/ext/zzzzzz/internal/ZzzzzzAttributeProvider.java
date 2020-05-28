package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.AttributeProviderAdapter;
import com.vladsch.flexmark.ast.util.AttributeProvidingHandler;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.html.renderer.AttributablePart.LINK;
import static com.vladsch.flexmark.util.html.Attribute.LINK_STATUS_ATTR;

public class ZzzzzzAttributeProvider implements AttributeProvider {
    final private String missingTargetClass;
    final private String localOnlyTargetClass;
    final private AttributeProviderAdapter nodeAdapter;

    public ZzzzzzAttributeProvider(LinkResolverContext context) {
        DataHolder options = context.getOptions();
        this.localOnlyTargetClass = ZzzzzzExtension.LOCAL_ONLY_TARGET_CLASS.get(options);
        this.missingTargetClass = ZzzzzzExtension.MISSING_TARGET_CLASS.get(options);

        this.nodeAdapter = new AttributeProviderAdapter(
                new AttributeProvidingHandler<>(Image.class, this::setLinkAttributes),
                new AttributeProvidingHandler<>(ImageRef.class, this::setLinkAttributes),
                new AttributeProvidingHandler<>(LinkRef.class, this::setLinkAttributes),
                new AttributeProvidingHandler<>(Link.class, this::setLinkAttributes)
        );
    }

    @Override
    public void setAttributes(@NotNull Node node, @NotNull AttributablePart part, @NotNull MutableAttributes attributes) {
        nodeAdapter.setAttributes(node, part, attributes);
    }

    private void setLinkAttributes(LinkNode node, AttributablePart part, MutableAttributes attributes) {
        setLinkAttributes(part, attributes);
    }

    private void setLinkAttributes(RefNode node, AttributablePart part, MutableAttributes attributes) {
        setLinkAttributes(part, attributes);
    }

    private void setLinkAttributes(AttributablePart part, MutableAttributes attributes) {
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
        //public Set<Class<?>> getAfterDependents() {
        //    return null;
        //}
        //
        //@Override
        //public Set<Class<?>> getBeforeDependents() {
        //    return null;
        //}
        //
        //@Override
        //public boolean affectsGlobalScope() {
        //    return false;
        //}

        @NotNull
        @Override
        public AttributeProvider apply(@NotNull LinkResolverContext context) {
            return new ZzzzzzAttributeProvider(context);
        }
    }
}
