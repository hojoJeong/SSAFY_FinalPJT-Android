package com.ssafy.silencelake.fragment.main.mypage

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ssafy.silencelake.dto.UserResponseDto
import com.ssafy.silencelake.util.RetrofitUtil
import com.ssafy.smartstore.response.OrderDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserResponseViewModel : ViewModel() {
    lateinit var userResponseDto: UserResponseDto
    var orderDetailResponseList = mutableListOf<MutableList<OrderDetailResponse>>()
    fun getUserResponseInfo(id: String) {
        viewModelScope.launch {
            val response = RetrofitUtil.userService.getUserInfo(id)
            if (response.isSuccessful) {
                Log.d(TAG, "getUserResponseInfo: userResponse 호출 성공")
                userResponseDto = response.body()!!
                for(i in 0 until userResponseDto.order.size){
                    withContext(Dispatchers.Main){
                        getOrderDetail(userResponseDto.order[i].id)
                    }
                }

            } else {
                Log.d(TAG, "getUserResponseInfo: userResponse 호출 실패")
            }
        }
    }

    fun getOrderDetail(orderId: Int){
        viewModelScope.launch {
            val response = RetrofitUtil.orderService.getOrderDetail(orderId)
            if (response.isSuccessful){
                Log.d(TAG, "getOrderDetail: orderDetail 호출 성공")
                orderDetailResponseList.add(response.body() as MutableList<OrderDetailResponse>)
            } else{
                Log.d(TAG, "getOrderDetail: orderDetail 호출 실패")
            }
        }
    }
}

