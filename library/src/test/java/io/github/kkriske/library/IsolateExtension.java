package io.github.kkriske.library;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.Isolates;
import org.junit.jupiter.api.extension.*;

public class IsolateExtension implements BeforeEachCallback, AfterEachCallback/*, ParameterResolver*/ {
    private IsolateThread thread;

    @Override
    public void beforeEach(ExtensionContext context) {
        thread = Isolates.createIsolate(Isolates.CreateIsolateParameters.getDefault());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Isolates.tearDownIsolate(thread);
    }
/*
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return IsolateThread.class.equals(parameterContext.getParameter().getType());
    }

    @Override
    public IsolateThread resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return thread;
    }
*/
    public IsolateThread getThread() {
        return thread;
    }
}
