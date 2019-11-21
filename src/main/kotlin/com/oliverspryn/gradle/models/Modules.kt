package com.oliverspryn.gradle.models

open class Modules {
    val modules: MutableList<Module> = mutableListOf()

    fun module(name: String, reportPath: String) {
        modules.add(
            Module(
                name = name,
                reportPath = reportPath
            )
        )
    }
}
