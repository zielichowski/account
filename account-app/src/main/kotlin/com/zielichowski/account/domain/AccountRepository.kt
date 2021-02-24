package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto

interface AccountRepository {
    fun save(accountDto: AccountDto)
    fun findById(accountId: String): AccountDto
}