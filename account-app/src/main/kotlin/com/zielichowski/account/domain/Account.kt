package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto
import com.zielichowski.account.api.AccountId
import com.zielichowski.account.api.AccountInitializationException
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money

internal class Account private constructor(
        private val accountId: AccountId,
        private val balance: Money
) {

    companion object {
        fun of(accountId: AccountId, initialBalance: Money): Account {
            if (initialBalance.currencyCode != Currency.PLN) {
                throw AccountInitializationException("Unable to initialize account with different currency than PLN!")
            }
            return Account(accountId, initialBalance)
        }
    }

    fun deposit(money: Money): Account {
        return Account(this.accountId, balance.add(money))
    }

    fun dto(): AccountDto {
        return AccountDto(this.accountId.id.toString(), balance)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (accountId != other.accountId) return false
        if (balance != other.balance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = accountId.hashCode()
        result = 31 * result + balance.hashCode()
        return result
    }

}

