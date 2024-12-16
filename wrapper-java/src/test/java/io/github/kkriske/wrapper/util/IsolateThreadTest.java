package io.github.kkriske.wrapper.util;

import io.github.kkriske.library.ffi.Library;
import org.junit.jupiter.api.Test;

import java.lang.foreign.Arena;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsolateThreadTest {
    static {
        Loader.load();
    }

    @Test
    void testIsolateThread() {
        try (Arena arena = Arena.ofConfined()) {
            IsolateThread isolateThread = new IsolateThread(arena);
            int sum = Library.add(isolateThread.pointer(), 1, 2);
            assertEquals(3, sum);
        }
    }
}
