package io.github.kkriske.wrapper.util;

public enum OS {
    WINDOWS,
    LINUX;

    public static final OS CURRENT;

    static {
        String os = System.getProperty("os.name").toLowerCase();
//        if (os.contains("mac") || os.contains("darwin")) {
//            CURRENT = OS.MACOS;
//        } else
        if (os.contains("win")) {
            CURRENT = OS.WINDOWS;
        } else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            CURRENT = OS.LINUX;
        } else {
            CURRENT = null;
            throw new RuntimeException("Unsupported OS: " + os);
        }
    }
}
