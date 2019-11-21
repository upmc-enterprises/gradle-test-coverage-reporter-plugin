package com.oliverspryn.gradle

import org.dom4j.io.SAXReader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

open class TestCoverageReporterTask @Inject constructor(
    private val extension: TestCoverageReporterExtension
) : DefaultTask() {

    @TaskAction
    fun extractFromReport() {
        if (!extension.enabled) return

        for (module in extension.modules.modules) {
            val document = SAXReader().read(File(module.reportPath))
            val metrics = document.rootElement.elements("counter")

            for (metric in metrics) {
                val covered = metric.attribute("covered").value.toFloat()
                val metricName = metric.attribute("type").value.toLowerCase().capitalize()
                val missed = metric.attribute("missed").value.toFloat()
                val percentage = (covered / (covered + missed)) * 100f

                println("${module.name} $metricName - ${"%.2f".format(percentage)}%")
            }

            println()
        }
    }
}
