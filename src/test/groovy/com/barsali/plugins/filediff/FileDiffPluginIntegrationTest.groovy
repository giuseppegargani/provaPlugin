package com.barsali.plugins.filediff

import org.gradle.internal.impldep.org.junit.Rule
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

//import org.junit.Rule
//import org.junit.rules.TemporaryFolder
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class FileDiffPluginIntegrationTest extends Specification {
    @TempDir
    File testProjectDir
    File buildFile

    def setup() {
        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
            plugins {
                id 'com.barsali.file-diff'
            }
        """
    }

    def "can  diff 2 files of same length"() {
        given:
        File testFile1 = new File(testProjectDir, 'testFile1.txt')
        testFile1.createNewFile()
        File testFile2 = new File(testProjectDir, 'testFile2.txt')
        testFile2.createNewFile()
        buildFile << """
            fileDiff {
                file1 = file('${testFile1.getName()}')
                file2 = file('${testFile2.getName()}')
            }
        """
        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('fileDiff')
                .withPluginClasspath()
                .build()
        then:
        result.output.contains("Files have the same size")
        result.task(":fileDiff").outcome == SUCCESS
    }
}
