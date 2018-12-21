package com.vladsch.flexmark.util;

import java.io.File;

import static com.vladsch.flexmark.util.Utils.suffixWith;

public class FileUtil {
    public static boolean isChildOf(File receiver, File ancestor) {
        return (suffixWith(receiver.getPath(), File.separator)).startsWith(suffixWith(ancestor.getPath(), File.separator));
    }

    public static String getNameOnly(File receiver) {
        String name = receiver.getName();
        int pos = name.lastIndexOf('.');
        return (pos > 0 && pos > name.lastIndexOf(File.separatorChar)) ? name.substring(0, pos) : name;
    }

    public static String getDotExtension(File receiver) {
        String name = receiver.getName();
        int pos = name.lastIndexOf('.');
        return (pos > 0 && pos > name.lastIndexOf(File.separatorChar)) ? name.substring(pos) : "";
    }

    public static String pathSlash(File receiver) {
        String path = receiver.getPath();
        int pos = path.lastIndexOf(File.separatorChar);
        return (pos != -1) ? path.substring(0, pos + 1) : "";
    }

    public static File plus(File receiver, String name) {
        return new File(receiver, name);
    }
}
