package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.ResourceResolverManager;
import com.vladsch.flexmark.test.util.spec.ResourceUrlResolver;

public class FlexmarkResourceUrlResolver {
    public static void registerUrlResolvers() {
        ResourceResolverManager.registerUrlResolver(new TargetTestResourceUrlResolver());
        ResourceResolverManager.registerUrlResolver(new BuildTestResourceUrlResolver());
        ResourceResolverManager.registerUrlResolver(new OutTestResourcesUrlResolver());
    }

    private static class TargetTestResourceUrlResolver implements ResourceUrlResolver {
        static final String TEST_RESOURCES = "/target/test-classes/";
        public static final String SRC_TEST_RESOURCES = "/src/test/resources/";

        TargetTestResourceUrlResolver() { }

        @Override
        public String apply(String externalForm) {
            if (ResourceUrlResolver.isFileProtocol(externalForm)) {
                String noFileProtocol = ResourceUrlResolver.removeProtocol(externalForm);

                if (noFileProtocol.contains(TEST_RESOURCES)) {
                    return noFileProtocol.replaceFirst(TEST_RESOURCES, SRC_TEST_RESOURCES);
                }
            }

            return null;
        }
    }

    private static class BuildTestResourceUrlResolver implements ResourceUrlResolver {
        static final String TEST_RESOURCES = "/build/resources/test/";
        public static final String SRC_TEST_RESOURCES = "/src/test/resources/";

        BuildTestResourceUrlResolver() { }

        @Override
        public String apply(String externalForm) {
            if (ResourceUrlResolver.isFileProtocol(externalForm)) {
                String noFileProtocol = ResourceUrlResolver.removeProtocol(externalForm);

                if (noFileProtocol.contains(TEST_RESOURCES)) {
                    return noFileProtocol.replaceFirst(TEST_RESOURCES, SRC_TEST_RESOURCES);
                }
            }

            return null;
        }
    }

    private static class OutTestResourcesUrlResolver implements ResourceUrlResolver {
        static final String OUT_TEST = "/out/test/";
        public static final String SRC_TEST_RESOURCES = "/src/test/resources/";

        OutTestResourcesUrlResolver() { }

        @Override
        public String apply(String externalForm) {
            if (ResourceUrlResolver.isFileProtocol(externalForm)) {
                String noFileProtocol = ResourceUrlResolver.removeProtocol(externalForm);

                int pos = noFileProtocol.indexOf(OUT_TEST);
                if (pos > 0) {
                    int pathPos = noFileProtocol.indexOf("/", pos + OUT_TEST.length());
                    if (pathPos > 0) {
                        return noFileProtocol.substring(0, pos) + "/" + noFileProtocol.substring(pos + OUT_TEST.length(), pathPos) + SRC_TEST_RESOURCES + noFileProtocol.substring(pathPos + 1);
                    }
                }
            }

            return null;
        }
    }
}
