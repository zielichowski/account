package com.zielichowski.account.domain

import com.zielichowski.account.infrastructure.persistence.AccountNotFoundException
import com.zielichowski.commons.CurrencyValidationException
import com.zielichowski.exchange.infrastructure.provider.MoneyExchangeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ApiErrorResponse(val status: Int, val message: String)

@ControllerAdvice
class AccountResponseExceptionHandler {

    @ExceptionHandler(AccountNotFoundException::class)
    fun accountNotFoundError(accountNotFoundException: AccountNotFoundException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity(ApiErrorResponse(404, accountNotFoundException.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MoneyExchangeException::class)
    fun exchangeException(ex: MoneyExchangeException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity(ApiErrorResponse(ex.statusCode.value(), ex.error), ex.statusCode)
    }

    @ExceptionHandler(CurrencyValidationException::class)
    fun currencyValidation(ex: CurrencyValidationException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity(ApiErrorResponse(400, ex.message!!), HttpStatus.BAD_REQUEST)
    }
}