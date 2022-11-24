package com.ssafy.silencelake.activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.databinding.ActivityAdminBinding
import com.ssafy.silencelake.databinding.ItemListAdminBinding
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.silencelake.fragment.main.mypage.ToggleAnimation
import com.ssafy.smartstore.response.OrderDetailResponse

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var adminAdapter: AdminOrderListAdapter
    private var orderDetailResponseList = listOf<List<OrderDetailResponse>>()
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
        initView()
    }

    private fun initData(){
        adminViewModel.getUncompletedOrderList()

        adminViewModel.orderList.observe(this){ orderList ->
            adminViewModel.getOrderDetailList()
            adminViewModel.orderDetailList.observe(this){
                adminAdapter.orderList = orderList
                adminAdapter.orderDetailList = it as MutableList<MutableList<OrderDetailResponse>>
                refreshAdapter()
                adminAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun refreshAdapter(){
        binding.rcvContainerAdmin.apply {
            layoutManager = LinearLayoutManager(this@AdminActivity, LinearLayoutManager.VERTICAL, false)
            adapter = adminAdapter
        }
        adminAdapter.onClick = object : AdminOrderListAdapter.onBtnClickListener{
            override fun onFoldBtnClickListener(orderDetailList: List<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean) {
                initAdminOrderDetailAdapter(orderDetailList, binding, isExpended)
            }

            override fun onCompleteBtnClickListener() {
                TODO("Not yet implemented")
            }

            override fun onCancelBtnClickListener() {
                TODO("Not yet implemented")
            }
        }
    }

    private fun initView(){
        //일 매출 livedata로 갱신
    }

    private fun initAdminOrderDetailAdapter(orderDetailList: List<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean){
        val adminDetailAdapter = AdminOrderDetailListAdapter(this)
        val rcvAdminDetail = binding.rcvRecentdetailAdmin
        adminDetailAdapter.orderDetailList = orderDetailList
        rcvAdminDetail.layoutManager = LinearLayoutManager(this@AdminActivity, LinearLayoutManager.VERTICAL, false)

        ToggleAnimation.toggleArrow(binding.btnFoldAdmin, isExpended)
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