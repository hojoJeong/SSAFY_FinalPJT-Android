package com.ssafy.silencelake.activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ssafy.silencelake.databinding.ActivityAdminBinding
import com.ssafy.silencelake.databinding.ItemListAdminBinding
import com.ssafy.silencelake.databinding.ItemListDetailAdminBinding
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.silencelake.fragment.main.mypage.ToggleAnimation
import com.ssafy.smartstore.response.OrderDetailResponse

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var adminAdapter: AdminOrderListAdapter
    private var orderList = mutableListOf<OrderDto>()
    private var orderDetailResponseList = mutableListOf<MutableList<OrderDetailResponse>>()
    private val adminViewModel by viewModels<AdminViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        initData()
        initAdapter()
        initView()

        adminViewModel.orderList.observe(this){
            adminAdapter.orderList = it
            adminAdapter.notifyDataSetChanged()
        }
    }

    private fun initData(){

    }

    private fun initAdapter(){
        adminAdapter = AdminOrderListAdapter()
        adminAdapter.orderList = orderList
        adminAdapter.orderDetailList = orderDetailResponseList
        binding.rcvContainerAdmin.apply {
            layoutManager = LinearLayoutManager(this@AdminActivity, LinearLayoutManager.VERTICAL, false)
            adapter = adminAdapter
        }
        adminAdapter.onClick = object : AdminOrderListAdapter.onBtnClickListener{
            override fun onFoldBtnClickListener(orderDetailList: MutableList<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean) {
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

    private fun initAdminOrderDetailAdapter(orderDetailList: MutableList<OrderDetailResponse>, binding: ItemListAdminBinding, isExpanded: Boolean){
        val adminDetailAdapter = AdminOrderDetailListAdapter()
        adminDetailAdapter.orderDetailList = orderDetailList
        val rcvAdminDetail = binding.rcvRecentdetailAdmin
        rcvAdminDetail.layoutManager = LinearLayoutManager(this@AdminActivity, LinearLayoutManager.VERTICAL, false)

        ToggleAnimation.toggleArrow(binding.btnFoldAdmin, !isExpanded)
        when(isExpanded){
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