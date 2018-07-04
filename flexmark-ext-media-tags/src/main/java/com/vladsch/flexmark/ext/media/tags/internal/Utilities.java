package com.vladsch.flexmark.ext.media.tags.internal;

final class Utilities {
    private Utilities() {
    }

    static String resolveAudioType(String source) {
        int period = source.lastIndexOf(".");
        if (period == -1) return null;

        String extension = source.substring(period + 1, source.length());
        switch (extension) {
            case "opus":
                return "audio/ogg; codecs=opus";
            case "weba":
                return "audio/webm";
            case "webm":
                return "audio/webm; codecs=opus";
            case "ogg":
                return "audio/ogg";
            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            case "flac":
                return "audio/flac";
            default:
                return null;
        }
    }

    static String resolveVideoType(String source) {
        int period = source.lastIndexOf(".");
        if (period == -1) return null;

        String extension = source.substring(period + 1, source.length());
        switch (extension) {
            case "mp4":
                return "video/mp4";
            case "webm":
                return "video/webm";
            case "ogv":
                return "video/ogg";
            case "3gp":
                return "video/3gp";
            default:
                return null;
        }
    }
}
