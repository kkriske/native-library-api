package io.github.kkriske.library.nativeimage;

import org.graalvm.nativeimage.hosted.Feature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class LibraryMetadataFeature implements Feature {

    private static final String SIZE_EXT = ".size";
    private static final String HASH_EXT = ".sha256";

    @Override
    public void afterImageWrite(AfterImageWriteAccess access) {
        Path image = access.getImagePath();
        String filename = stripExtension(image.getFileName().toString());
        Path sizePath = image.resolveSibling(filename + SIZE_EXT);
        Path hashPath = image.resolveSibling(filename + HASH_EXT);
        try {
            Files.writeString(sizePath, String.valueOf(Files.size(image)));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] sha256 = digest.digest(Files.readAllBytes(image));
            String formatted = HexFormat.of().formatHex(sha256);
            Files.writeString(hashPath, formatted);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String stripExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot == -1) {
            return filename;
        }
        return filename.substring(0, dot);
    }
}
