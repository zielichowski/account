package com.zielichowski.commons

import java.io.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

enum class Currency {
    PLN,
    USD
}

data class Rate(val value: BigDecimal)

class Money : Serializable, Exchangeable {

    var denomination: BigDecimal
    var currencyCode: Currency


    constructor(denomination: Double, currencyCode: Currency) : this(BigDecimal(denomination), currencyCode)

    constructor(denomination: BigDecimal, currencyCode: Currency) {
        this.denomination = denomination.setScale(2, RoundingMode.HALF_EVEN)
        this.currencyCode = currencyCode
    }

    fun multiplyBy(multiplier: Double): Money {
        return multiplyBy(BigDecimal(multiplier))
    }

    fun multiplyBy(multiplier: BigDecimal): Money {
        return Money(denomination.multiply(multiplier), currencyCode)
    }

    fun add(money: Money): Money {
        checkCurrencyCompatibility(money)
        return Money(denomination.add(money.denomination), currencyCode)
    }

    fun subtract(money: Money): Money {
        checkCurrencyCompatibility(money)
        return Money(denomination.subtract(money.denomination), currencyCode)
    }

    fun divideBy(divisor: BigDecimal, scale: Int, roundingMode: RoundingMode): Money {
        return Money(denomination.divide(divisor, scale, roundingMode), currencyCode)
    }

    fun divideBy(money: Money): Int {
        checkCurrencyCompatibility(money)
        return this.denomination.divide(money.denomination).intValueExact()
    }

    fun greaterThan(other: Money): Boolean {
        return denomination.compareTo(other.denomination) > 0
    }

    fun lessThan(other: Money): Boolean {
        return denomination.compareTo(other.denomination) < 0
    }

    fun hasCompatibleCurrency(money: Money): Boolean {
        checkCurrencyCompatibility(money)
        return true
    }

    override fun exchangeTo(target: Currency, rate: Rate): Money {
        return Money(this.denomination.divide(rate.value, RoundingMode.HALF_DOWN), target)
    }

    private fun checkCurrencyCompatibility(money: Money) {
        if (incompatibleCurrency(money)) {
            throw IllegalArgumentException("Currency mismatch : " + currencyCode + " -> " + money.currencyCode)
        }
    }

    private fun incompatibleCurrency(money: Money): Boolean {
        return currencyCode != money.currencyCode
    }

    override fun toString(): String {
        return String.format(Locale.ENGLISH, "%0$.2f %s", denomination, currencyCode)

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        if (denomination != other.denomination) return false
        if (currencyCode != other.currencyCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = denomination.hashCode()
        result = 31 * result + currencyCode.hashCode()
        return result
    }

    companion object {
        @JvmField
        val DEFAULT_CURRENCY: Currency = Currency.PLN

        @JvmField
        val ZERO = Money(BigDecimal.ZERO, DEFAULT_CURRENCY)
    }
}