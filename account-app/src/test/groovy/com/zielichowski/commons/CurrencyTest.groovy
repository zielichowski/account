package com.zielichowski.commons

import spock.lang.Specification

class CurrencyTest extends Specification {

    def "Should not allow to create different currency than PLN and USD"() {
        given: "String with currency name"
        def currencyCode = "GBP"

        when: "Creating currency"
        Currency.@Companion.of(currencyCode)

        then: "Exception is thrown"
        thrown(CurrencyValidationException)
    }

    def "Should allow to create currency in PLN and USD"() {
        given: "String with currency name"
        def currencyCode = "PLN"

        when: "Creating currency"
        def currency = Currency.@Companion.of(currencyCode)

        then: "Exception is thrown"
        currency == Currency.PLN
    }


}
