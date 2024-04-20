package org.kettingpowered.ketting.internal;

import java.io.File;

/**
 * @author C0D3 M4513R
 * @author JustRed32
 */
@SuppressWarnings("unused")
public class KettingFileVersioned {
    private static final String
            MC = KettingConstants.MINECRAFT_VERSION,
            FORGE = KettingConstants.FORGE_VERSION,
            MCP = KettingConstants.MCP_VERSION,
            MC_FORGE = MC + "-" + FORGE,
            MC_FORGE_KETTING = MC + "-" + FORGE + "-" + KettingConstants.KETTING_VERSION,
            MC_MCP = MC + "-" + MCP;
    public static final File MCP_BASE_DIR = new File(KettingFiles.MCP_BASE_DIR, MC_MCP + "/");
    public static final File NMS_BASE_DIR = new File(KettingFiles.NMS_BASE_DIR, MC + "/");
    public static final File NMS_PATCHES_DIR = new File(KettingFiles.NMS_BASE_DIR, MC_MCP + "/");

    public static final String KETTINGSERVER_PATH = "org/kettingpowered/server";

    public static final String NEOFORGE_UNIVERSAL_NAME = "neoforge-" + FORGE + "-universal.jar";

    public static final File
            NEOFORGE_UNIVERSAL_JAR = new File(KettingFiles.NEOFORGE_DIR, FORGE + "/" + NEOFORGE_UNIVERSAL_NAME),
            NEOFORGE_PATCHED_JAR = new File(KettingFiles.NEOFORGE_DIR, FORGE + "/neoforge-" + FORGE + "-server.jar"),
            NEOFORGE_KETTING_LIBS = new File(KettingFiles.KETTINGSERVER_NEOFORGE_DIR, MC_FORGE_KETTING + "/neoforge-" + MC_FORGE_KETTING + "-ketting-libraries.txt"),
            NEOFORGE_INSTALL_JSON = new File(KettingFiles.KETTINGSERVER_NEOFORGE_DIR, MC_FORGE_KETTING + "/neoforge-" + MC_FORGE_KETTING + "-installscript.json"),
            NEOFORM_MCP_ZIP = new File(MCP_BASE_DIR, "neoform-" + MC_MCP + ".zip"),
            MCP_ZIP = new File(MCP_BASE_DIR, "mcp_config-" + MC_MCP + ".zip"),
            SERVER_JAR = new File(NMS_BASE_DIR, "server-" + MC + ".jar"),
            SERVER_UNPACKED = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-unpacked.jar"),
            SERVER_SLIM = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-slim.jar"),
            SERVER_EXTRA = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-extra.jar"),
            SERVER_SRG = new File(NMS_PATCHES_DIR, "server-" + MC_MCP + "-srg.jar"),
            MCP_MAPPINGS = new File(MCP_BASE_DIR, "mappings.txt"),
            MOJANG_MAPPINGS = new File(NMS_PATCHES_DIR, "mappings.txt"),
            MERGED_MAPPINGS = new File(MCP_BASE_DIR, "mappings-merged.txt");

}
