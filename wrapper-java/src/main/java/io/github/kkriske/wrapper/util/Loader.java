package io.github.kkriske.wrapper.util;

import org.graalvm.nativeimage.ImageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Loader {

    static {
        if (ImageInfo.inImageBuildtimeCode()) {
            throw new RuntimeException("Cannot execute library loader during native-image build.");
        }
        try {
            Path libPath = unpackLib();
            LibraryMetadata.validate(libPath);
            System.out.println("Library location: " + libPath);
            System.load(libPath.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
    }

    private static Path unpackLib() throws IOException, LibraryMetadata.ValidationException {
        String resource = LibraryMetadata.libraryResource();
        System.out.println("Unpacking library: " + resource);
        String libraryName = LibraryMetadata.libraryName();
        Path unpackLocation = Path.of(
                System.getProperty("java.io.tmpdir"),
                libraryName + '-' + LibraryMetadata.libraryHash(),
                LibraryMetadata.mappedLibraryName());
        boolean valid = false;
        if (Files.exists(unpackLocation)) {
            try {
                LibraryMetadata.validate(unpackLocation);
                valid = true;
            } catch (LibraryMetadata.ValidationException ex) {
                System.out.println("Validation of existing library failed");
                System.out.println(ex.getMessage());
                Files.delete(unpackLocation);
            }
        }
        if (valid) {
            return unpackLocation;
        }
        System.out.println("Unpacking library location: " + unpackLocation);
        InputStream is = Loader.class.getResourceAsStream(resource);
        if (is == null) {
            System.out.println("Unpacking library not supported.");
            throw new UnsatisfiedLinkError("Cannot load library: " + libraryName);
        }
        Files.createDirectories(unpackLocation.getParent());
        try (is; OutputStream os = Files.newOutputStream(unpackLocation)) {
            is.transferTo(os);
        }
        LibraryMetadata.validate(unpackLocation);
        return unpackLocation;
    }
}
