package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import spock.lang.Specification
import spock.lang.Subject

class AccountFacadeTest extends Specification {

    @Subject
    def accountFacade = new AccountFacade(new TestAccountRepository(), new TestMoneyExchange())

    def "Should get account balance as USD"() {
        given: "Account with balance in PLN is created"
        def account = accountFacade.createAccount(new Money(100, Currency.PLN))

        and: "Expected result"
        def expectedResult = new Money(26.32, Currency.USD)

        when: "Getting balance as USD"
        def balanceAsUsd = accountFacade.getBalanceAs(account.accountId, Currency.USD)

        then: "Result is correct"
        balanceAsUsd == expectedResult
    }

    def "Should create account with balance in PLN"() {
        given: "Initial balance in PLN"
        def initBalance = new Money(100, Currency.PLN)

        and: "Expected result"
        def expectedResult = new AccountDto(UUID.randomUUID().toString(), initBalance)

        when: "Creating new account"
        def account = accountFacade.createAccount(initBalance)

        then: "Account balance is correct"
        account.money == expectedResult.money

    }

    def "Should not create account with balance in USD"() {
        given: "Initial balance in PLN"
        def initBalance = new Money(100, Currency.USD)

        when: "Creating new account"
        def account = accountFacade.createAccount(initBalance)

        then: "Account balance is correct"
        thrown(RuntimeException)

    }

}
