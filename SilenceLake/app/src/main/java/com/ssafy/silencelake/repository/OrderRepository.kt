package com.ssafy.smartstore.service

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.silencelake.repository.FcmRepository
import com.ssafy.silencelake.util.RetrofitUtil
import com.ssafy.smartstore.response.LatestOrderResponse
import com.ssafy.smartstore.response.OrderDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "OrderService_싸피"

class OrderRepository {

    companion object {
        fun insertOder(order: OrderDto) {
            RetrofitUtil.orderApi.makeOrder(order).enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse: 주문 성공!")
                        Log.d(TAG, "onResponse: 주문 : $order")
                        CoroutineScope(Dispatchers.Default).launch {
                            FcmRepository.sendMessageToAdmin()
                            Log.d(TAG, "onResponse: sendMessageToAdmin")
                        }
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.d(TAG, "onFailure: 주문 입력 통신 오류")
                }
            })
        }

        suspend fun updateOrder(orderId: Int){
            val response = RetrofitUtil.orderApi.updateOrder(orderId)
            if(response.isSuccessful){
                Log.d(ContentValues.TAG, "updateOrder: 주문 처리 완료")
            } else{
                Log.d(ContentValues.TAG, "updateOrder: 주문 처리 실패")
            }
        }

        suspend fun deleteOrder(orderId: Int){
            val response = RetrofitUtil.orderApi.deleteOrder(orderId)
            if(response.isSuccessful){
                Log.d(TAG, "deleteOrder: 주문 취소 완료")
            } else{
                Log.d(TAG, "deleteOrder: 주문 취소 실패")
            }
        }


        // 최근 한달간 주문내역 가져오는 API
        fun getLastMonthOrder(userId: String): LiveData<List<LatestOrderResponse>> {
            val responseLiveData: MutableLiveData<List<LatestOrderResponse>> = MutableLiveData()
            val latestOrderRequest: Call<List<LatestOrderResponse>> =
                RetrofitUtil.orderApi.getLastMonthOrder(userId)

            latestOrderRequest.enqueue(object : Callback<List<LatestOrderResponse>> {
                override fun onResponse(
                    call: Call<List<LatestOrderResponse>>,
                    response: Response<List<LatestOrderResponse>>
                ) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            // 가공 필요 orderDate 를 기준으로 정렬, o_img 하나로 축약 필요
                            //orderId를 기준으로 새로운 리스트 만들어서 넘기기
                            responseLiveData.value = makeLatestOrderList(res)
                        }
                        Log.d(TAG, "onResponse: $res")
                    } else {
                        Log.d(TAG, "onResponse: Error Code ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<LatestOrderResponse>>, t: Throwable) {
                    Log.d(TAG, t.message ?: "최근 주문 내역 받아오는 중 통신오류")
                }
            })

            return responseLiveData
        }

        // 최근 주문 목록에서 총가격, 주문 개수 구하여 List로 반환한다.
        // 반환되는 List의 경우 화면에서 보여주는 최근 주문 목록 List이다.
        private fun makeLatestOrderList(latestOrderList: List<LatestOrderResponse>): List<LatestOrderResponse> {
            val hm = HashMap<Int, LatestOrderResponse>()
            latestOrderList.forEach { order ->
                if (hm.containsKey(order.orderId)) {
                    val tmp = hm[order.orderId]!!
                    tmp.orderCnt += order.orderCnt
                    tmp.totalPrice += order.productPrice * order.orderCnt
                    hm[order.orderId] = tmp
                } else {
                    order.totalPrice = order.productPrice * order.orderCnt
                    hm[order.orderId] = order
                }
            }
            val list = ArrayList<LatestOrderResponse>(hm.values)
            list.sortWith { o1, o2 -> o2.orderDate.compareTo(o1.orderDate) }
            return list
        }

        suspend fun getOrderDetail(orderId: Int): List<OrderDetailResponse> {
            val response = RetrofitUtil.orderApi.getOrderDetail(orderId)
            if (response.isSuccessful) {
                Log.d(ContentValues.TAG, "getOrderDetail: orderDetail 호출 성공")
                return response.body() ?: emptyList()
            } else {
                Log.d(ContentValues.TAG, "getOrderDetail: orderDetail 호출 실패")
                return emptyList()
            }
        }

        suspend fun getUncompletedOrder(): List<OrderDto> {
            val response = RetrofitUtil.orderApi.getUnCompletedOrder()
            if (response.isSuccessful) {
                Log.d(TAG, "getUncompletedOrder: Success to UnCompleted Order")
                return response.body()?: emptyList()
            } else {
                Log.d(TAG, "getUncompletedOrder: Fail to UnCompleted Order")
                return emptyList()
            }
        }
    }
}