package com.zielichowski.commons


interface Exchangeable {
    fun exchangeTo(target: Currency, rate: Rate): Money
}