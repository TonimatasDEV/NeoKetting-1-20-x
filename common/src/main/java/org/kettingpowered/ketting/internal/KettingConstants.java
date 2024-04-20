package org.kettingpowered.ketting.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * @author C0D3 M4513R
 * @author JustRed32
 */
@SuppressWarnings("unused")
public class KettingConstants {
    public static final String NAME = "Ketting";
    public static final String BRAND = "KettingPowered";
    public static final String SITE_LINK = "https://github.com/kettingpowered/";
    public static final String VERSION;
    public static final String MINECRAFT_VERSION;
    public static final String BUKKIT_PACKAGE_VERSION;
    public static final String BUKKIT_VERSION;
    public static final String FORGE_VERSION;
    public static final String KETTING_VERSION;
    public static final String MCP_VERSION;
    public static final String INSTALLER_LIBRARIES_FOLDER = "libraries";
    public static final String KETTING_GROUP = "org.kettingpowered";
    public static final String KETTINGSERVER_GROUP = KETTING_GROUP + ".server";
    static{
        final HashMap<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> typeMap = MajorMinorPatchVersion.parseKettingServerVersionList(KettingFiles.getKettingServerVersions(KettingFiles.KETTINGSERVER_NEOFORGE_DIR).stream());
        final Map.Entry<MajorMinorPatchVersion<Integer>, List<Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>>>> mcEntry = typeMap.entrySet().iterator().next();
        MINECRAFT_VERSION = mcEntry.getKey().toString();
        if (mcEntry.getValue().isEmpty()) throw new IllegalStateException("Found a Minecraft Version, but no Ketting Server versions for that version. This should not happen.");
        else if (mcEntry.getValue().size() > 1) throw new IllegalStateException("Found multiple Ketting Server versions for NeoForge for the Minecraft Version "+MINECRAFT_VERSION+" .");
        Tuple<MajorMinorPatchVersion<Integer>, MajorMinorPatchVersion<Integer>> version = mcEntry.getValue().get(0);
        FORGE_VERSION = version.t1().toString();
        KETTING_VERSION = version.t2().toString();
        VERSION = MINECRAFT_VERSION + "-" + KETTING_VERSION;

        final String[] mcv = MINECRAFT_VERSION.split("\\.");
        mcv[mcv.length-1] = "R"+mcv[mcv.length-1];
        BUKKIT_PACKAGE_VERSION = String.join("_", mcv);
        BUKKIT_VERSION = MINECRAFT_VERSION + "-R0.1-SNAPSHOT";
        
        final String UNIVERSAL_NAME = "neoforge" + "-" + FORGE_VERSION + "-universal.jar";

        try (final JarFile jarFile = new JarFile(new File(KettingFiles.NEOFORGE_DIR, FORGE_VERSION + "/" + UNIVERSAL_NAME))){
            final String fullVersion = (String) jarFile.getManifest().getEntries().get("org/kettingpowered/ketting/").getValue("Implementation-Version");
            MCP_VERSION = fullVersion.substring(fullVersion.lastIndexOf('-')+1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
