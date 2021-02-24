package com.zielichowski.account.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaAccountRepository : JpaRepository<AccountEntity, String> {
}