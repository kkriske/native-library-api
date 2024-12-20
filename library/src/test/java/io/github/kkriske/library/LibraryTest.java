package io.github.kkriske.library;

import org.graalvm.nativeimage.Isolate;
import org.graalvm.nativeimage.IsolateThread;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledInNativeImage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnabledInNativeImage
//@ExtendWith(IsolateExtension.class)
class LibraryTest {

    @RegisterExtension
    IsolateExtension ext = new IsolateExtension();

    @Test
    void testAdd(/*IsolateExtension ext*/) {
        assertEquals(3, Library.add(ext.getThread(), 1, 2));
        assertEquals(7, Library.add(ext.getThread(), 3, 4));
    }
}
