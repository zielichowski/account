package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto
import com.zielichowski.account.api.AccountId
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import com.zielichowski.exchange.domain.MoneyExchange
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*


internal class AccountFacade(
        private val accountRepository: AccountRepository,
        private val moneyExchange: MoneyExchange
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountFacade::class.java)
    }

    fun createAccount(initialBalance: Money): AccountDto {
        val accountDto = Account.of(AccountId(UUID.randomUUID()), initialBalance).dto()
        accountRepository.save(accountDto)
        return accountDto
    }

    fun getBalanceAs(accountId: String, currency: Currency): Money {
        logger.info("Getting balance in currency: $currency for account with id: $accountId")
        val accountDto = accountRepository.findById(accountId)
        return moneyExchange.exchange(accountDto.money, currency)
    }
}


