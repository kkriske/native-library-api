package io.github.kkriske.wrapper;

import io.github.kkriske.library.ffi.Library;
import io.github.kkriske.wrapper.util.IsolateArena;
import io.github.kkriske.wrapper.util.Loader;

public class Native {
    static {
        Loader.load();
    }

    private Native() {
    }

    public static int add(int a, int b) {
        try (var arena = IsolateArena.create()) {
            return Library.add(arena.pIsolateThread(), a, b);
        }
    }
}
