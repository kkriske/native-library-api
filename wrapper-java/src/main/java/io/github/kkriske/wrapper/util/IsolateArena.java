package io.github.kkriske.wrapper.util;

import io.github.kkriske.library.ffi.Library;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class IsolateArena implements Arena {
    private final Arena arena;
    private final MemorySegment pIsolateThread;

    private IsolateArena(Arena arena, MemorySegment pIsolateThread) {
        this.arena = arena;
        this.pIsolateThread = pIsolateThread;
    }

    public static IsolateArena create() {
        Arena arena = Arena.ofConfined();
        MemorySegment ppIsolateThread = arena.allocate(Library.C_POINTER);
        int code = Library.graal_create_isolate(MemorySegment.NULL, MemorySegment.NULL, ppIsolateThread);
        if (code != 0) {
            arena.close();
            throw new IllegalStateException("Failed to create isolate thread");
        }
        MemorySegment pIsolateThread = ppIsolateThread.get(Library.C_POINTER, 0).asReadOnly();
        pIsolateThread.reinterpret(arena, Library::graal_tear_down_isolate);
        return new IsolateArena(arena, pIsolateThread);
    }

    public MemorySegment pIsolateThread() {
        return pIsolateThread;
    }

    @Override
    public MemorySegment allocate(long byteSize, long byteAlignment) {
        return arena.allocate(byteSize, byteAlignment);
    }

    @Override
    public MemorySegment.Scope scope() {
        return arena.scope();
    }

    @Override
    public void close() {
        arena.close();
    }
}
