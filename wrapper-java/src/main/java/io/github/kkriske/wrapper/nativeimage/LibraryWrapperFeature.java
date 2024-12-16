package io.github.kkriske.wrapper.nativeimage;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeResourceAccess;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// WIP, no support for native image yet
public class LibraryWrapperFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        handleLibraryResources();
        handleFfiCalls();
    }

    private void handleLibraryResources() {
        String libraryName = System.mapLibraryName("library");
        String libraryResource = "io/github/kkriske/library/native/" + libraryName;
        String exportLocation = System.getProperty("io.github.kkriske.wrapper.exportPath", "");
        if (exportLocation.isEmpty()) {
            // Do not export the library and include it in native-image instead
            RuntimeResourceAccess.addResource(LibraryWrapperFeature.class.getModule(), libraryResource);
        } else {
            // export the required library to the specified path,
            // the user is responsible to make sure the created native-image can actually find it.
            Path targetPath = Paths.get(exportLocation, libraryName);
            try (InputStream in = LibraryWrapperFeature.class.getResourceAsStream(libraryResource)) {
                Files.createDirectories(targetPath.getParent());
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleFfiCalls() {
        //RuntimeForeignAccess.registerForDowncall(...);
    }
}
