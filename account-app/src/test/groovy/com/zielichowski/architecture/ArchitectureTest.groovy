package com.zielichowski.architecture

import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.DependencyRules
import com.tngtech.archunit.library.GeneralCodingRules
import spock.lang.Specification

class ArchitectureTest extends Specification {

    def "should not throw generic exceptions"() {
        given:
        ArchRule archRule = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
    }

    def "should no accesses to upper package"() {
        given:
        ArchRule archRule = DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES
    }

    def "domain package should be hidden to outside world"() {
        given:
        ArchRule archRule =
                ArchRuleDefinition.classes()
                        .that().resideInAPackage("..domain..")
                        .should().onlyBeAccessed().byClassesThat().resideInAPackage("..domain..")
    }
}
