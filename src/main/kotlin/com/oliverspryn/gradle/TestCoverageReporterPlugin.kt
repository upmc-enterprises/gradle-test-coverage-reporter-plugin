package com.oliverspryn.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class TestCoverageReporterPlugin : Plugin<Project> {

    @Suppress("UnstableApiUsage") // create() is incubating
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "testCoverageReporter",
            TestCoverageReporterExtension::class.java,
            project.objects
        )

        project.tasks.create("extractTestReport", TestCoverageReporterTask::class.java, extension)
    }
}
