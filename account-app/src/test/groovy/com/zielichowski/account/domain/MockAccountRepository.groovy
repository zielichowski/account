package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto
import org.jetbrains.annotations.NotNull

import java.util.concurrent.ConcurrentHashMap

class MockAccountRepository implements AccountRepository {
    def map = new ConcurrentHashMap<String, AccountDto>()

    @Override
    void save(@NotNull AccountDto accountDto) {
        map.putIfAbsent(accountDto.accountId, accountDto)

    }

    @Override
    AccountDto findById(@NotNull String accountId) {
        return map[accountId]
    }
}
