package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountId
import com.zielichowski.account.api.AccountInitializationException
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import spock.lang.Specification

class AccountTest extends Specification {

    def "Should create account in PLN"() {
        given: "One PLN"
        def money = new Money(BigDecimal.ONE, Currency.PLN)

        and: "Random id"
        def accountId = new AccountId(UUID.randomUUID())

        when: "Creating new account"
        def account = Account.@Companion.of(accountId, money)

        then: "Account is created"
        account != null

        and: "No exception is thrown"
        noExceptionThrown()
    }

    def "Should not create account in USD"() {
        given: "One USD"
        def money = new Money(BigDecimal.ONE, Currency.USD)
        and: "Random id"
        def accountId = new AccountId(UUID.randomUUID())

        when: "Creating new account"
        def account = Account.@Companion.of(accountId, money)

        then: "Exception is thrown"
        thrown(AccountInitializationException)
    }

    def "Should deposit money in PLN"() {
        given: "Random id"
        def accountId = new AccountId(UUID.randomUUID())

        and: "Account in PLN"
        def account = Account.@Companion.of(accountId, Money.ZERO)

        and: "Money to deposit"
        def money = new Money(100, Currency.PLN)

        and: "Expected account after deposit"
        def expectedAccount = Account.@Companion.of(accountId, new Money(100, Currency.PLN))

        when: "Deposit money"
        def accountAfterDeposit = account.deposit(money)

        then: "Money is added to account"
        accountAfterDeposit == expectedAccount
    }


}
