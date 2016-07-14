package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.internal.util.dependency.DependencyHandler;
import com.vladsch.flexmark.internal.util.dependency.ResolvedDependencies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 2016-07-13.
 */
public class LinkResolverDependencyHandler extends DependencyHandler<LinkResolverFactory, LinkResolverDependencyHandler.LinkResolverDependencyStage, LinkResolverDependencyHandler.LinkResolverDependencies> {
    public static class LinkResolverDependencies extends ResolvedDependencies<LinkResolverDependencyStage> {
        ArrayList<LinkResolverFactory> myLinkResolverFactories;
        
        public LinkResolverDependencies(List<LinkResolverDependencyStage> dependentStages) {
            super(dependentStages);
            ArrayList<LinkResolverFactory> list = new ArrayList<>();
            for (LinkResolverDependencyStage stage : dependentStages) {
                list.addAll(stage.dependents);
            }
            
            myLinkResolverFactories = list;
        }
    }

    public static class LinkResolverDependencyStage {
        final private List<LinkResolverFactory> dependents;

        public LinkResolverDependencyStage(List<LinkResolverFactory> dependents) {
            // compute mappings
            this.dependents = dependents;
        }
    }

    @Override
    protected Class<? extends LinkResolverFactory> getDependentClass(LinkResolverFactory dependent) {
        return dependent.getClass();
    }

    @Override
    protected LinkResolverDependencies createResolvedDependencies(List<LinkResolverDependencyStage> stages) {
        return new LinkResolverDependencies(stages);
    }

    @Override
    protected LinkResolverDependencyStage createStage(List<LinkResolverFactory> dependents) {
        return new LinkResolverDependencyStage(dependents);
    }
    
    public static List<LinkResolverFactory> computeLinkResolvers(List<LinkResolverFactory> linkRendererFactories) {
        LinkResolverDependencyHandler resolver = new LinkResolverDependencyHandler();
        LinkResolverDependencies dependencies = resolver.resolveDependencies(linkRendererFactories);
        return dependencies.myLinkResolverFactories;
    }
}
