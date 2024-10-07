package com.vladsch.flexmark.util.misc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.jetbrains.annotations.NotNull;

public class FileUtil {
  @NotNull
  public static byte[] getFileContentBytesWithExceptions(File receiver) throws IOException {
    return Files.readAllBytes(receiver.toPath());
  }
}
