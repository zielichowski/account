package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto
import com.zielichowski.account.api.AccountId
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import com.zielichowski.exchange.domain.MoneyExchange
import java.util.*


internal class AccountFacade(
        private val accountRepository: AccountRepository,
        private val moneyExchange: MoneyExchange
) {
    fun createAccount(initialBalance: Money): AccountDto {
        val accountDto = Account.of(AccountId(UUID.randomUUID()), initialBalance).dto()
        accountRepository.save(accountDto)
        return accountDto
    }

    fun getBalanceAs(accountId: String, currency: Currency): Money {
        val accountDto = accountRepository.findById(accountId)
        return moneyExchange.exchange(accountDto.money, currency)
    }
}


