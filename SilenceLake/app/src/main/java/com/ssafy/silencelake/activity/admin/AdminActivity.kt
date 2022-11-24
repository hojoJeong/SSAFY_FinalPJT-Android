package com.ssafy.silencelake.activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.databinding.ActivityAdminBinding
import com.ssafy.silencelake.databinding.ItemListAdminBinding
import com.ssafy.silencelake.fragment.main.mypage.ToggleAnimation
import com.ssafy.smartstore.response.OrderDetailResponse


class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var adminAdapter: AdminOrderListAdapter
    private val adminViewModel by viewModels<AdminViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        adminAdapter = AdminOrderListAdapter()

        initData()
    }

    private fun initData(){
        var unCompleteOrderTimes = 0
        adminViewModel.getUncompletedOrderList()
        adminViewModel.orderList.observe(this){ orderList ->
            adminViewModel.getOrderDetailList()
            adminViewModel.orderDetailList.observe(this){
                adminAdapter.orderList = orderList
                adminAdapter.orderDetailList = it as MutableList<MutableList<OrderDetailResponse>>
                refreshAdapter()
                adminAdapter.notifyDataSetChanged()

                unCompleteOrderTimes = orderList.size
                binding.tvStackAdmin.text = "미완료 주문 : $unCompleteOrderTimes 건"
            }
        }
    }

    private fun refreshAdapter(){
        binding.rcvContainerAdmin.apply {
            layoutManager = LinearLayoutManager(this@AdminActivity, LinearLayoutManager.VERTICAL, false)
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
    }

    private fun initAdminOrderDetailAdapter(orderDetailList: List<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean){
        val adminDetailAdapter = AdminOrderDetailListAdapter(this)
        val rcvAdminDetail = binding.rcvRecentdetailAdmin
        adminDetailAdapter.orderDetailList = orderDetailList
        rcvAdminDetail.layoutManager = LinearLayoutManager(this@AdminActivity, LinearLayoutManager.VERTICAL, false)

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