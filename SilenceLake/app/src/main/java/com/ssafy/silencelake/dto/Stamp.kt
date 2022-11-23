package com.ssafy.silencelake.dto

data class Stamp(val id: Int, val orderId: Int, var quantity: Int, val userId: String) {
    constructor(quantity: Int, userId: String):this(0, 0, quantity, userId)
}