package com.zielichowski.exchange.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import spock.lang.Specification

class NbpMoneyExchangeTest extends Specification {

    def "Should exchange PLN to USD"() {
        given: "Mock nbp rate provider"
        def rateProvider = new MockNbpRateProvider()

        and: "Nbp exchange"
        def nbpExchange = new NbpMoneyExchange(rateProvider)

        and: "100 PLN0"
        def money = new Money(100, Currency.PLN)

        and: "Expected result"
        def expectedResult = new Money(27.03, Currency.USD)

        when: "Exchanging pln to usd"
        def exchangedMoney = nbpExchange.exchange(money, Currency.USD)

        then: "Result is correct"
        exchangedMoney == expectedResult
    }

}
