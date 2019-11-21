package com.oliverspryn.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class TestCoverageReporterTask @Inject constructor(
    private val extension: TestCoverageReporterExtension
) : DefaultTask() {

    @TaskAction
    fun extractFromReport() {
        println("Hello Earth!")

        // Core business logic here
    }
}
