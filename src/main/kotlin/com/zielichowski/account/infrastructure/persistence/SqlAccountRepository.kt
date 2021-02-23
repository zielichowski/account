package com.zielichowski.account.infrastructure.persistence

import com.zielichowski.account.api.AccountDto
import com.zielichowski.account.domain.AccountRepository
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import java.math.BigDecimal

internal class SqlAccountRepository(private val jpaAccountRepository: JpaAccountRepository) : AccountRepository {
    override fun save(accountDto: AccountDto) {
        jpaAccountRepository.save(AccountEntity(accountDto.accountId, accountDto.money.denomination.toPlainString(), accountDto.money.currencyCode.name))
    }

    override fun findById(accountId: String): AccountDto {
        return jpaAccountRepository
                .findById(accountId)
                .map { AccountDto(it.accountId, Money(BigDecimal(it.amount), Currency.valueOf(it.currency))) }
                .orElseThrow { AccountNotFoundException("Unable to find account with id: $accountId") }
    }
}

class AccountNotFoundException(message: String) : RuntimeException(message)