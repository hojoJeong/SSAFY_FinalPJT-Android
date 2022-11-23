package com.ssafy.silencelake.activity.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.smartstore.response.OrderDetailResponse
import kotlinx.coroutines.launch

class AdminViewModel: ViewModel() {
    private var _orderList = MutableLiveData<MutableList<OrderDto>>()
    val orderList: MutableLiveData<MutableList<OrderDto>>
        get() = _orderList

    private var _orderDetailList = MutableLiveData<MutableList<OrderDetailResponse>>()
    val orderDetailList: MutableLiveData<MutableList<OrderDetailResponse>>
     get() = _orderDetailList

    fun getOrderList() = viewModelScope.launch {

    }

    fun getOrderDetailList() = viewModelScope.launch {

    }
}