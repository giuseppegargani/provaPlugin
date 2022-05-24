package com.barsali.plugins.filediff

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

import javax.naming.spi.ObjectFactory


abstract class FileDiffExtension {
    abstract RegularFileProperty getFile1()
    abstract RegularFileProperty getFile2()
}

class FileDiffPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('fileDiff', FileDiffExtension)

        project.tasks.create('fileDiff', FileDiffTask) {
            file1 = project.fileDiff.file1
            file2 = project.fileDiff.file2
        }

    }
}
