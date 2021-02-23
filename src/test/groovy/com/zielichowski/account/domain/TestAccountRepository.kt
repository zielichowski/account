package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

internal class TestAccountRepository : AccountRepository {
    private val map: ConcurrentMap<String, AccountDto> = ConcurrentHashMap()

    override fun save(accountDto: AccountDto) {
        map.putIfAbsent(accountDto.accountId, accountDto)
    }

    override fun findById(accountId: String): AccountDto {
        return map[accountId]!!
    }
}