package com.ssafy.silencelake.fragment.main.mypage

import com.ssafy.silencelake.databinding.ItemListRecentOrderBinding
import com.ssafy.silencelake.dto.OrderDetail

interface OnFoldButtonLIstener {
    fun onClick(orderDetailList: MutableList<OrderDetail>, binding: ItemListRecentOrderBinding)
}