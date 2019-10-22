package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.ResourceResolverManager;
import com.vladsch.flexmark.test.util.spec.ResourceUrlResolver;

import static com.vladsch.flexmark.test.util.spec.ResourceUrlResolver.isFileProtocol;
import static com.vladsch.flexmark.test.util.spec.ResourceUrlResolver.removeProtocol;

public class FlexmarkResourceUrlResolver {
    public static void registerUrlResolvers() {
        ResourceResolverManager.registerUrlResolver(new TargetTestClassUrlResolver());
        ResourceResolverManager.registerUrlResolver(new OutTestResourcesUrlResolver());
    }

    private static class TargetTestClassUrlResolver implements ResourceUrlResolver {
        static final String TARGET_TEST_CLASSES = "/target/test-classes/";

        TargetTestClassUrlResolver() {}

        @Override
        public String apply(String externalForm) {
            if (isFileProtocol(externalForm)) {
                String noFileProtocol = removeProtocol(externalForm);

                if (noFileProtocol.contains(TARGET_TEST_CLASSES)) {
                    return noFileProtocol.replace(TARGET_TEST_CLASSES, "/src/test/resources/");
                }
            }

            return null;
        }
    }

    private static class OutTestResourcesUrlResolver implements ResourceUrlResolver {
        static final String OUT_TEST = "/out/test/";

        OutTestResourcesUrlResolver() {}

        @Override
        public String apply(String externalForm) {
            if (isFileProtocol(externalForm)) {
                String noFileProtocol = removeProtocol(externalForm);

                int pos = noFileProtocol.indexOf(OUT_TEST);
                if (pos > 0) {
                    int pathPos = noFileProtocol.indexOf("/", pos + OUT_TEST.length());
                    if (pathPos > 0) {
                        return noFileProtocol.substring(0, pos) + "/" + noFileProtocol.substring(pos + OUT_TEST.length(), pathPos) + "/src/test/resources/" + noFileProtocol.substring(pathPos + 1);
                    }
                }
            }

            return null;
        }
    }
}
