package io.github.kkriske.wrapper.util;

import io.github.kkriske.library.ffi.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsolateArenaTest {
    static {
        Loader.load();
    }

    @Test
    void testIsolateThread() {
        try (var arena = IsolateArena.create()) {
            int sum = Library.add(arena.pIsolateThread(), 1, 2);
            assertEquals(3, sum);
        }
    }
}
