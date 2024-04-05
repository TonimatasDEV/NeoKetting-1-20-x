package org.kettingpowered.task

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.security.MessageDigest

abstract class GenerateLibs extends DefaultTask {
    public static void init(){
        File.metaClass.sha256 = { ->
            MessageDigest md = MessageDigest.getInstance('SHA-256')
            delegate.eachByte(4096) { byte[] bytes, int size ->
                md.update(bytes, 0, size)
            }
            return md.digest().encodeHex().toString()
        }
        File.metaClass.getSha256 = { !delegate.exists() ? null : delegate.sha256() }
        File.metaClass.sha512 = { ->
            MessageDigest md = MessageDigest.getInstance('SHA3-512')
            delegate.eachByte(4096) { byte[] bytes, int size ->
                md.update(bytes, 0, size)
            }
            return md.digest().encodeHex().toString()
        }
        File.metaClass.getSha512 = { !delegate.exists() ? null : delegate.sha512() }
    }
    
    @OutputFile abstract RegularFileProperty getOutput()

    GenerateLibs(){
        outputs.upToDateWhen {false}
        outputs.cacheIf {false}
        output.convention(getProject().getLayout().getBuildDirectory().file("ketting_libraries.txt"))
    }

    @TaskAction
    void genActions() {
        def entries = new HashMap<GString, GString> ()
        getProject().configurations.installer.resolvedConfiguration.resolvedArtifacts.each { dep->
            def art = dep.moduleVersion.id
            if ('junit'.equals(art.name) && 'junit'.equals(art.group)) return;
            def mavenId = "$art.group:$art.name:$art.version" + (dep.classifier != null ? ":$dep.classifier" : "") + (dep.extension != null ? "@$dep.extension" : "")
            def key = "$art.group:$art.name:" + (dep.classifier != null ? ":$dep.classifier" : "") + (dep.extension != null ? "@$dep.extension" : "")
            entries.put(key,"$dep.file.sha512\tSHA3-512\t$mavenId")
        }

        output.get().asFile.text = entries.values().join('\n')
    }
}