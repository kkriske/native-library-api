package io.github.kkriske.library;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;

public class Library {
//    @CEntryPoint(name = "create_isolate", builtin = CEntryPoint.Builtin.CREATE_ISOLATE)
//    public static native long createIsolate();
//
//    @CEntryPoint(name = "tear_down_isolate", builtin = CEntryPoint.Builtin.TEAR_DOWN_ISOLATE)
//    public static native void tearDownIsolate(@CEntryPoint.IsolateThreadContext long thread);
//
//    @CEntryPoint(name = "add")
//    public static int add(@CEntryPoint.IsolateThreadContext long thread, int a, int b) {
//        return a + b;
//    }

    @CEntryPoint(name = "add")
    public static int add(IsolateThread thread, int a, int b) {
        return a + b;
    }
}
