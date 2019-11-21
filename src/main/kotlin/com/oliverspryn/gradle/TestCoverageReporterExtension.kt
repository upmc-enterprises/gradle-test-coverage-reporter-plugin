package com.oliverspryn.gradle

import com.oliverspryn.gradle.models.Modules
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

@Suppress("UnstableApiUsage") // ObjectFactory is incubating
open class TestCoverageReporterExtension @Inject constructor(
    objectFactory: ObjectFactory
) {

    // region Model

    var coverageTypes = listOf("instruction")
    var enabled = true
    var modules: Modules = objectFactory.newInstance(Modules::class.java)

    // endregion

    // region Convenience Methods

    fun coverageTypes(vararg types: String) {
        coverageTypes = types.toList()
    }

    fun modules(action: Action<Modules>) {
        action.execute(modules)
    }

    // endregion
}
