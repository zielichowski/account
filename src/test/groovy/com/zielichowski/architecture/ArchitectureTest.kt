package com.zielichowski.architecture

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchUnitRunner
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
import org.junit.runner.RunWith


@AnalyzeClasses(packages = ["com.zielichowski"])
@RunWith(ArchUnitRunner::class)
class ArchitectureTest {

    @ArchTest
    private val no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS

    @ArchTest
    val no_accesses_to_upper_package = NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES

    @ArchTest
    val classes_from_domain_package_should_be_hidden_to_other_packages =
            classes()
                    .that().resideInAPackage("..domain..")
                    .should().onlyBeAccessed().byClassesThat().resideInAPackage("..domain..")

}