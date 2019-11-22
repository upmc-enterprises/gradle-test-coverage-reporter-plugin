package com.oliverspryn.gradle

import org.dom4j.io.SAXReader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

open class TestCoverageReporterTask @Inject constructor(
    private val extension: TestCoverageReporterExtension
) : DefaultTask() {

    private val coverageTypeCache: HashMap<String, Float> = hashMapOf()

    @TaskAction
    fun extractFromReport() {
        if (!extension.enabled) return

        val includedCoverageTypes = extension.coverageTypes.map {
            it.toLowerCase()
        }

        for (module in extension.modules.modules) {
            val document = SAXReader().read(File(module.reportPath))
            val coverageTypes = document.rootElement.elements("counter")

            for (coverageType in coverageTypes) {
                val coverageTypeName = coverageType.attribute("type").value.toLowerCase().capitalize()
                if (!includedCoverageTypes.contains(coverageTypeName.toLowerCase())) continue

                val covered = coverageType.attribute("covered").value.toFloat()
                val missed = coverageType.attribute("missed").value.toFloat()
                val percentage = (covered / (covered + missed)) * 100f

                addToCache(coverageTypeName, percentage)
                println("${module.name} $coverageTypeName - ${"%.2f".format(percentage)}%")
            }

            println()
        }

        if (extension.modules.modules.count() <= 1) return
        val totalModules = extension.modules.modules.count().toFloat()

        for (key in coverageTypeCache.keys) {
            coverageTypeCache[key]?.let {
                val percentage = it / totalModules
                println("Summary ${key.capitalize()} - ${"%.2f".format(percentage)}%")
            }
        }

        println()
    }

    private fun addToCache(coverageTypeName: String, percentage: Float) {
        val name = coverageTypeName.toLowerCase()

        if (!coverageTypeCache.containsKey(name)) {
            coverageTypeCache[name] = 0f
        }

        coverageTypeCache[name]?.let {
            coverageTypeCache[name] = it + percentage
        }
    }
}
