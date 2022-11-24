package com.ssafy.silencelake.activity.admin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.api.FirebaseTokenApi
import com.ssafy.silencelake.databinding.ActivityAdminBinding
import com.ssafy.silencelake.databinding.ItemListAdminBinding
import com.ssafy.silencelake.fragment.main.mypage.ToggleAnimation
import com.ssafy.silencelake.util.ApplicationClass
import com.ssafy.smartstore.response.OrderDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "AdminActivity_싸피"
class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var adminAdapter: AdminOrderListAdapter
    private val adminViewModel by viewModels<AdminViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: ")

        init()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        adminViewModel.getUncompletedOrderList()
        Log.d(TAG, "onNewIntent: ")
    }

    companion object{
        // ratrofit  수업 후 network 에 업로드 할 수 있도록 구성
        fun uploadToken(token:String){
            // 새로운 토큰 수신 시 서버로 전송
            ApplicationClass.myToken = token
            Log.d(TAG, "uploadToken: ${ApplicationClass.myToken}")
            val storeService = ApplicationClass.retrofit.create(FirebaseTokenApi::class.java)
            storeService.uploadToken(token).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        val res = response.body()
                        Log.d(TAG, "onResponse: $res")
                    } else {
                        Log.d(TAG, "onResponse: Error Code ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d(TAG, t.message ?: "토큰 정보 등록 중 통신오류")
                }
            })
        }
    }
    private fun init(){
        adminAdapter = AdminOrderListAdapter()
        binding.rcvContainerAdmin.apply {
            layoutManager = LinearLayoutManager(this@AdminActivity)
            adapter = adminAdapter
        }
        adminAdapter.onBtnClickListener = object : AdminOrderListAdapter.OnBtnClickListener{
            override fun onFoldBtnClickListener(orderDetailList: List<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean) {
                initAdminOrderDetailAdapter(orderDetailList, binding, isExpended)
            }

            override fun onCompleteBtnClickListener(orderId: Int, token: String) {
                adminViewModel.updateOrder(orderId, token)
            }

            override fun onCancelBtnClickListener(orderId: Int, token: String) {
                adminViewModel.deleteOrder(orderId, token)
            }
        }
        initData()
    }

    private fun initData(){
        var unCompleteOrderCount = 0
        adminViewModel.getUncompletedOrderList()
        adminViewModel.orderList.observe(this){ orderList ->
            adminViewModel.getOrderDetailList()
            adminViewModel.orderDetailList.observe(this){
                adminAdapter.orderList = orderList
                adminAdapter.orderDetailList = it
                adminAdapter.notifyDataSetChanged()
                unCompleteOrderCount = orderList.size
                binding.tvStackAdmin.text = "미완료 주문 : $unCompleteOrderCount 건"
            }
        }
    }



    private fun initAdminOrderDetailAdapter(orderDetailList: List<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean){
        val adminDetailAdapter = AdminOrderDetailListAdapter(this)
        val rcvAdminDetail = binding.rcvRecentdetailAdmin
        adminDetailAdapter.orderDetailList = orderDetailList
        rcvAdminDetail.layoutManager = LinearLayoutManager(this@AdminActivity)

        ToggleAnimation.toggleArrow(binding.btnFoldAdmin, !isExpended)
        when(isExpended){
            true -> {
                ToggleAnimation.collapse(binding.rcvRecentdetailAdmin)
            }
            false -> {
                rcvAdminDetail.adapter = adminDetailAdapter
                ToggleAnimation.expand(rcvAdminDetail)
            }
        }
    }
}