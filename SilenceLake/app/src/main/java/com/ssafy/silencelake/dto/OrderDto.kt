package com.ssafy.silencelake.dto

import java.util.Date

data class OrderDto(
    val id: Int,
    var userId: String,
    var orderTable: String,
    var orderTime: Date,
    var completed: Char,
    var orderDetail: MutableList<OrderDetail>,
    var stamp: Stamp
) {

}