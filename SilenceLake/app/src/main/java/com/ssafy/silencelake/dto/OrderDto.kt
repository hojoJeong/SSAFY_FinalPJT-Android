package com.ssafy.silencelake.dto

import java.util.Date

data class OrderDto(
    val id: Int,
    var userId: String,
    var orderTable: String,
    var orderTime: Date?,
    var completed: Char,
    var details: ArrayList<OrderDetail>,
    var token: String
) {
    var isExpended = false
    constructor(
        userId: String,
        orderTable: String,
        orderDetail: MutableList<OrderDetail>,
        token: String
    ): this(0, userId, orderTable, null,'N',orderDetail as ArrayList<OrderDetail>, token)
}