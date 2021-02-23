package com.zielichowski.commons

import spock.lang.Specification

import java.math.RoundingMode

class MoneyTest extends Specification {
    def 'should multiply money'() {
        given:
        def money = new Money(10.00, Currency.PLN)
        when:
        def result = money.multiplyBy(2)
        then:
        result == new Money(20.00, Currency.PLN)
    }

    def 'should add money'() {
        given:
        def money = new Money(10.00, Currency.PLN)
        when:
        def result = money.add(new Money(5, Currency.PLN))
        then:
        result == new Money(15, Currency.PLN)
    }

    def 'should subtract money'() {
        given:
        def money = new Money(10.00, Currency.PLN)
        when:
        def result = money.subtract(new Money(5, Currency.PLN))
        then:
        result == new Money(5, Currency.PLN)
    }

    def 'should choose greater money'() {
        given:
        def ten = new Money(10.00, Currency.PLN)
        def fifteen = new Money(15.00, Currency.PLN)
        when:
        def greaterThan = fifteen.greaterThan(ten)
        then:
        greaterThan
    }

    def 'should choose less money'() {
        given:
        def ten = new Money(10.00, Currency.PLN)
        def fifteen = new Money(15.00, Currency.PLN)
        when:
        def lessThan = ten.lessThan(fifteen)
        then:
        lessThan
    }

    def 'should throw exception on incompatible currency'() {
        given:
        def tenPln = new Money(10.00, Currency.PLN)
        def tenEur = new Money(10.00, Currency.USD)

        when:
        tenEur.hasCompatibleCurrency(tenPln)

        then:
        thrown(IllegalArgumentException)
    }

    def 'should not throw exception on compatible currency'() {
        given:
        def tenPln = new Money(10.00, Currency.PLN)
        def twoPln = new Money(2.00, Currency.PLN)
        when:
        twoPln.hasCompatibleCurrency(tenPln)
        then:
        noExceptionThrown()
    }

    def "should divide money without reminder"() {
        given:
        def thousandPln = new Money(1000.00, Currency.PLN)

        when:
        def divided = thousandPln.divideBy(new BigDecimal(9), 0, RoundingMode.UP)

        then:
        divided == new Money(112, Currency.PLN)
    }

    def "should throw exception in case of non-terminating decimal expansion"() {
        given:
        def thousandPln = new Money(1000.00, Currency.PLN)

        when:
        def divided = thousandPln.divideBy(new Money(90, Currency.PLN))

        then:
        thrown(ArithmeticException)
    }

    def "should convert to different currency with given rate"() {
        given:
        def thousandPln = new Money(1000.00, Currency.PLN)

        and:
        def rate = new Rate(BigDecimal.valueOf(3.69))

        and:
        def expectedResult = new Money(BigDecimal.valueOf(271), Currency.USD)

        when:
        def usdMoney = thousandPln.exchangeTo(Currency.USD, rate)

        then:
        usdMoney == expectedResult
    }
}
