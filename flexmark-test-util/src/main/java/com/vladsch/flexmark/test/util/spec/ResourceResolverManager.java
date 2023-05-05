package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.TestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Function;

import static com.vladsch.flexmark.test.util.spec.ResourceUrlResolver.hasProtocol;

public class ResourceResolverManager {
    /**
     * urlResolvers map test resource location url to source resource url to allow tests to  
     * output file URLs which refer to source location, not copies in test location
     */
    final private static ArrayList<Function<String, String>> urlResolvers = new ArrayList<>();
    
    public static void registerUrlResolver(@NotNull Function<String, String> resolver) {
        ResourceResolverManager.urlResolvers.add(resolver);
    }

    @NotNull
    public static String adjustedFileUrl(@NotNull URL url) {
        String externalForm = url.toExternalForm();
        String bestProtocolMatch = null;

        for (Function<String, String> resolver : urlResolvers) {
            String filePath = resolver.apply(externalForm);
            if (filePath == null) continue;

            if (hasProtocol(filePath) && bestProtocolMatch == null) {
                bestProtocolMatch = filePath;
            } else {
                File file = new File(filePath);
                if (file.exists()) {
                    return TestUtils.FILE_PROTOCOL + filePath;
                }
            }
        }

        return bestProtocolMatch != null ? bestProtocolMatch : externalForm;
    }
}
