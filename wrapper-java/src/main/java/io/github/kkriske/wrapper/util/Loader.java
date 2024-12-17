package io.github.kkriske.wrapper.util;

import java.net.URL;

public class Loader {
    static {
        String os = System.getProperty("os.name").toLowerCase();
        String libName;
        if (os.contains("win")) {
            libName = "library.dll";
        } else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            libName = "library.so";
        } else {
            throw new RuntimeException("Unsupported OS: " + os);
        }
        System.out.println("Loading library: " + libName);
        URL lib = Loader.class.getResource("/io/github/kkriske/library/native/" + libName);
        System.out.println("Library location: " + lib.getPath());
        System.load(lib.getPath());
    }

    public static void load() {
    }
}
