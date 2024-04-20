package org.kettingpowered.ketting.internal;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author C0D3 M4513R
 * @author JustRed32
 */
@SuppressWarnings("unused")
public class KettingFiles {
    public static final String DATA_DIR = "data/";
    public static final File MAIN_FOLDER_FILE, SERVER_JAR_DIR;
    public static final File LAUNCH_DIR;

    static {
        String path = KettingFiles.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        int indexJarSep = path.indexOf('!');
        if (indexJarSep>=0) path = path.substring(0, indexJarSep);
        final String fileProto = "file://";
        int indexFile = path.indexOf(fileProto);
        if (indexFile>=0) path = path.substring(fileProto.length());
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        SERVER_JAR_DIR = new File(path)
                .getParentFile()
                .getParentFile()
                .getParentFile()
                .getParentFile()
                .getParentFile()
                .getParentFile()
                .getAbsoluteFile();
        MAIN_FOLDER_FILE = SERVER_JAR_DIR;

        LAUNCH_DIR = new File(System.getProperty("user.dir"));
    }
    public static final File LIBRARIES_DIR = new File(SERVER_JAR_DIR, KettingConstants.INSTALLER_LIBRARIES_FOLDER).getAbsoluteFile();
    public static final String LIBRARIES_PATH = LIBRARIES_DIR.getAbsolutePath() + "/";
    public static final String LOGS_PATH = new File(LAUNCH_DIR, "logs").getAbsolutePath() + "/";
    public static final File KETTINGSERVER_BASE_DIR = new File(LIBRARIES_PATH, "org/kettingpowered/server");
    public static final File NEOFORGE_DIR = new File(LIBRARIES_PATH, "net/neoforged/neoforge");
    public static final File KETTINGSERVER_NEOFORGE_DIR = new File(KettingFiles.KETTINGSERVER_BASE_DIR, "neoforge");
    public static final File INSTALL_DIR = new File(KETTINGSERVER_BASE_DIR, "install/");
    public static final File MCP_BASE_DIR = new File(KettingFiles.LIBRARIES_PATH, "net/neoforged/neoform/");
    public static final File NMS_BASE_DIR = new File(KettingFiles.LIBRARIES_PATH, "net/minecraft/server/");
    public static final File FANCY_MOD_LOADER_DIR = new File(LIBRARIES_PATH, "net/neoforged/fancymodloader/loader");

    public static final File
            SERVER_EULA = new File(LAUNCH_DIR, "eula.txt"),
            SERVER_LZMA = new File(INSTALL_DIR, "server.lzma"),
            STORED_HASHES = new File(INSTALL_DIR, "hashes.txt"),
            PATCHER_LOGS = new File(LOGS_PATH, "install.txt");
    
    public static List<String> getKettingServerVersions(){
        return getKettingServerVersions(KETTINGSERVER_NEOFORGE_DIR);
    }

    public static List<String> getKettingServerVersions(File dir){
        final File[] kettingVersions = dir.listFiles(File::isDirectory);
        if (kettingVersions == null) return Collections.emptyList();
        return Arrays.stream(kettingVersions)
                .map(File::getName)
                .distinct()
                .toList();
    }
}
