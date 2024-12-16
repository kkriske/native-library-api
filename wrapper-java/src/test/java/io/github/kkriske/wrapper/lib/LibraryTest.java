package io.github.kkriske.wrapper.lib;

import io.github.kkriske.library.ffi.Library;
import io.github.kkriske.wrapper.util.Loader;
import org.junit.jupiter.api.Test;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryTest {
    static {
        Loader.load();
    }

    @Test
    void testAdd() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment ppIsolateThread = arena.allocate(Library.C_POINTER);
            Library.graal_create_isolate(MemorySegment.NULL, MemorySegment.NULL, ppIsolateThread);
            MemorySegment pIsolateThread = ppIsolateThread.get(Library.C_POINTER, 0);
            pIsolateThread.reinterpret(arena, Library::graal_tear_down_isolate);

            int sum = Library.add(pIsolateThread, 3, 4);
            assertEquals(7, sum);
        }
    }
}
