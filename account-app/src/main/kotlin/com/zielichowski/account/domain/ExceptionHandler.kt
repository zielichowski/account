package com.zielichowski.account.domain

import com.zielichowski.account.infrastructure.persistence.AccountNotFoundException
import com.zielichowski.commons.CurrencyValidationException
import com.zielichowski.exchange.infrastructure.provider.MoneyExchangeException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ApiErrorResponse(val status: Int, val message: String)

@ControllerAdvice
class AccountResponseExceptionHandler {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountResponseExceptionHandler::class.java)
    }

    @ExceptionHandler(AccountNotFoundException::class)
    fun accountNotFoundError(accountNotFoundException: AccountNotFoundException): ResponseEntity<ApiErrorResponse> {
        logger.error("Caught exception: AccountNotFoundException with message: ${accountNotFoundException.message}")
        return ResponseEntity(ApiErrorResponse(404, accountNotFoundException.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MoneyExchangeException::class)
    fun exchangeException(ex: MoneyExchangeException): ResponseEntity<ApiErrorResponse> {
        logger.error("Caught exception: MoneyExchangeException with message: ${ex.error}")
        return ResponseEntity(ApiErrorResponse(ex.statusCode.value(), ex.error), ex.statusCode)
    }

    @ExceptionHandler(CurrencyValidationException::class)
    fun currencyValidation(ex: CurrencyValidationException): ResponseEntity<ApiErrorResponse> {
        logger.error("Caught exception: CurrencyValidationException with message: ${ex.message}")
        return ResponseEntity(ApiErrorResponse(400, ex.message!!), HttpStatus.BAD_REQUEST)
    }
}