package com.zielichowski.account.infrastructure.persistence

import com.zielichowski.account.domain.AccountRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class AccountPersistenceConfig {

    @Bean
    fun accountRepo(jpaAccountRepository: JpaAccountRepository): AccountRepository {
        return SqlAccountRepository(jpaAccountRepository)
    }

}