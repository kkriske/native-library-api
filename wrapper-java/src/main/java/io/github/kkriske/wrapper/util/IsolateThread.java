package io.github.kkriske.wrapper.util;

import io.github.kkriske.library.ffi.Library;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class IsolateThread {
    private final MemorySegment pIsolateThread;

    public IsolateThread(Arena arena) {
        MemorySegment ppIsolateThread = arena.allocate(Library.C_POINTER);
        int code = Library.graal_create_isolate(MemorySegment.NULL, MemorySegment.NULL, ppIsolateThread);
        if (code != 0) {
            throw new IllegalStateException("Failed to create isolate thread");
        }
        pIsolateThread = ppIsolateThread.get(Library.C_POINTER, 0).asReadOnly();
        pIsolateThread.reinterpret(arena, Library::graal_tear_down_isolate);
    }

    public MemorySegment pointer() {
        return pIsolateThread;
    }
}
