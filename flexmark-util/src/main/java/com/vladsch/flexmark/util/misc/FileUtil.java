package com.vladsch.flexmark.util.misc;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.vladsch.flexmark.util.misc.Utils.suffixWith;

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

    public static String getFileContent(File receiver) {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            InputStream inputStream = new FileInputStream(receiver);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            streamReader.close();
            inputStream.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
