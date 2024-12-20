package io.github.kkriske.wrapper.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class LibraryMetadata {
    private static final String RESOURCES = "/io/github/kkriske/library/native/";
    private static final String LIB_NAME = "library";
    private static final String HASH_EXT = ".sha256";
    private static final String SIZE_EXT = ".size";
    private static final String LIB_EXT;
    private static final String HASH;
    private static final long SIZE;

    static {
        LIB_EXT = switch (OS.CURRENT) {
            case WINDOWS -> ".dll";
            case LINUX -> ".so";
        };

        try (InputStream hashIs = Loader.class.getResourceAsStream(RESOURCES + LIB_NAME + HASH_EXT);
             InputStream sizeIs = Loader.class.getResourceAsStream(RESOURCES + LIB_NAME + SIZE_EXT)) {
            HASH = new String(hashIs.readAllBytes(), StandardCharsets.UTF_8);
            SIZE = Long.parseLong(new String(sizeIs.readAllBytes(), StandardCharsets.UTF_8));
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LibraryMetadata() { /* util class */ }

    public static String libraryName() {
        return LIB_NAME;
    }

    public static String mappedLibraryName() {
        return libraryName() + LIB_EXT;
    }

    public static String libraryResource() {
        return RESOURCES + mappedLibraryName();
    }

    public static String libraryHash() {
        return HASH;
    }

    public static void validate(Path lib) throws ValidationException {
        try {
            long size = Files.size(lib);
            if (size != SIZE) {
                throw new IllegalStateException("Invalid size of library file: " + lib);
            }
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] sha256 = digest.digest(Files.readAllBytes(lib));
            String formatted = HexFormat.of().formatHex(sha256);
            if (!HASH.equals(formatted)) {
                throw new IllegalStateException("Invalid hash of library file: " + lib);
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}
