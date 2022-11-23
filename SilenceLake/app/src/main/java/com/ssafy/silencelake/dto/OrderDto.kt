package com.ssafy.silencelake.dto

import java.util.Date

data class OrderDto(
    val id: Int,
    var userId: String,
    var orderTable: String,
    var orderTime: String,
    var completed: Char,
    var details: ArrayList<OrderDetail>,
) {
    constructor(
        userId: String,
        orderTable: String,
        orderDetail: MutableList<OrderDetail>
    ): this(0, userId, orderTable, "",'N',orderDetail as ArrayList<OrderDetail>)
}