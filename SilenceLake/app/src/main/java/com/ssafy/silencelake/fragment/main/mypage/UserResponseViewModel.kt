package com.ssafy.silencelake.fragment.main.mypage

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ssafy.silencelake.dto.UserResponseDto
import com.ssafy.silencelake.repository.UserRepository
import com.ssafy.silencelake.util.RetrofitUtil
import com.ssafy.smartstore.response.OrderDetailResponse
import com.ssafy.smartstore.service.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserResponseViewModel : ViewModel() {
    private var _userResponseDto = MutableLiveData<UserResponseDto>()
    val userResponseDto: LiveData<UserResponseDto>
        get() = _userResponseDto

    private var _orderDetailResponseList = MutableLiveData<List<List<OrderDetailResponse>>>()
    val orderDetailResponseList: LiveData<List<List<OrderDetailResponse>>>
        get() = _orderDetailResponseList

    fun getUserResponseInfo(id: String) = viewModelScope.launch {
        _userResponseDto.value = UserRepository.getUserInfo(id)
    }

    fun getOrderDetail() = viewModelScope.launch {
        var list = mutableListOf<List<OrderDetailResponse>>()
        var orderInfo = _userResponseDto.value!!.order
        for(i in 0 until orderInfo.size){
            list.add(OrderRepository.getOrderDetail(orderInfo[i].id))
        }
        _orderDetailResponseList.value = list
    }

}

