package io.github.kkriske.wrapper.util;

import java.net.URL;

public class Loader {
    static {
        String libName = System.mapLibraryName("library");
        System.out.println("Loading library: " + libName);
        URL lib = Loader.class.getResource("/io/github/kkriske/library/native/" + libName);
        System.out.println("Library location: " + lib.getPath());
        System.load(lib.getPath());
    }

    public static void load() {
    }
}
