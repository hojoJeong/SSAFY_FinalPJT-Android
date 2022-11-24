package com.ssafy.silencelake.activity.admin

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.silencelake.repository.FcmRepository
import com.ssafy.silencelake.repository.UserRepository
import com.ssafy.smartstore.response.OrderDetailResponse
import com.ssafy.smartstore.service.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminViewModel : ViewModel() {
    private var _orderList = MutableLiveData<List<OrderDto>>()
    val orderList: LiveData<List<OrderDto>>
        get() = _orderList

    private var _orderDetailList = MutableLiveData<List<List<OrderDetailResponse>>>()
    val orderDetailList: LiveData<List<List<OrderDetailResponse>>>
        get() = _orderDetailList

    fun getUncompletedOrderList() = viewModelScope.launch {
        _orderList.value = OrderRepository.getUncompletedOrder() as MutableList<OrderDto>
    }

    fun getOrderDetailList() = viewModelScope.launch {
        val list = mutableListOf<List<OrderDetailResponse>>()
        for (i in 0 until (_orderList.value?.size ?: 0)) {
            list.add(OrderRepository.getOrderDetail(_orderList.value!![i].id))
        }
        _orderDetailList.value = list
    }

    fun updateOrder(orderId: Int, token: String) = viewModelScope.launch {
        OrderRepository.updateOrder(orderId)
        FcmRepository.sendMessageTo("📢알림📢","☕주문하신 커피가 나왔습니다☕", token)
        getUncompletedOrderList()
    }
    
    fun deleteOrder(orderId: Int, token: String) = viewModelScope.launch {
        OrderRepository.deleteOrder(orderId)
        FcmRepository.sendMessageTo("📢알림📢","사장님이 주문을 취소했습니다.", token)
        getUncompletedOrderList()
    }
}