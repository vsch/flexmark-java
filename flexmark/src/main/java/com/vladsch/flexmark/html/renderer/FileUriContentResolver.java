package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.UriContentResolver;
import com.vladsch.flexmark.html.UriContentResolverFactory;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.dependency.LastDependent;
import com.vladsch.flexmark.util.misc.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class FileUriContentResolver implements UriContentResolver {
    public FileUriContentResolver(LinkResolverBasicContext context) {

    }

    @Override
    public @NotNull ResolvedContent resolveContent(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedContent content) {
        ResolvedLink resolvedLink = content.getResolvedLink();

        if (resolvedLink.getStatus() == LinkStatus.VALID) {
            // have the file 
            String url = resolvedLink.getUrl();
            if (url.startsWith("file:/")) {
                // handle Windows and OSX/Unix URI
                String substring = url.startsWith("file://") ? url.substring("file://".length()) : File.separatorChar == '\\' ? url.substring("file:/".length()) : url.substring("file:".length());
                File includedFile = new File(substring);
                if (includedFile.isFile() && includedFile.exists()) {
                    // need to read and parse the file
                    try {
                        return content.withContent(FileUtil.getFileContentBytesWithExceptions(includedFile)).withStatus(LinkStatus.VALID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return content;
    }

    public static class Factory implements UriContentResolverFactory {
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            // ensure that default file uri resolver is the last one in the list
            return Collections.singleton(LastDependent.class);
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @NotNull
        @Override
        public UriContentResolver apply(@NotNull LinkResolverBasicContext context) {
            return new FileUriContentResolver(context);
        }
    }
}
