package org.kettingpowered.launcher.utils;

import org.jetbrains.annotations.NotNull;
import org.kettingpowered.launcher.KettingLauncher;
import org.kettingpowered.launcher.internal.utils.HashUtils;
import org.kettingpowered.launcher.lang.I18n;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class JarUtils {
    public static void extractJarContent(@NotNull String from, @NotNull File to) throws IOException {
        try (InputStream internalFile = KettingLauncher.class.getClassLoader().getResourceAsStream(from)) {
            if (internalFile == null)
                throw new IOException(I18n.get("error.launcher.bundled_file_not_found", from));

            byte[] internalFileContent = internalFile.readAllBytes();
            if (!to.exists() || !HashUtils.getHash(to, "SHA3-512").equals(HashUtils.getHash(new ByteArrayInputStream(internalFileContent), "SHA3-512"))) {
                //noinspection ResultOfMethodCallIgnored
                to.getParentFile().mkdirs();
                //noinspection ResultOfMethodCallIgnored
                to.createNewFile();

                try (FileOutputStream fos = new FileOutputStream(to)) {
                    fos.write(internalFileContent);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(I18n.get("error.launcher.hash_algorithm_not_found"), e);
        }
    }
}
