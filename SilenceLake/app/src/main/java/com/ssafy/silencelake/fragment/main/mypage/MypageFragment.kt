package com.ssafy.silencelake.fragment.main.mypage

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.activity.main.MainActivity
import com.ssafy.silencelake.databinding.FragmentMypageBinding
import com.ssafy.silencelake.databinding.ItemListRecentOrderBinding
import com.ssafy.silencelake.dto.*
import com.ssafy.smartstore.response.OrderDetailResponse

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var userInfo: UserDto
    private var orderList = mutableListOf<OrderDto>()
    private var orderDetailResponseList = mutableListOf<MutableList<OrderDetailResponse>>()
    private val userResponseViewModel by activityViewModels<UserResponseViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAdapter()
    }
    private fun initData(){
        userInfo = userResponseViewModel.userResponseDto.user
        orderList = userResponseViewModel.userResponseDto.order
        orderDetailResponseList = userResponseViewModel.orderDetailResponseList
    }

    private fun initView(){
        binding.apply {
            tvUsernameMypage.text = userInfo.name
            tvLevelMypagefg.text = (userInfo.stamps/10).toString()
            progressLevelMypagefg.progress = (userInfo.stamps % 10) * 10
            tvLogoutMypage.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            tvLogoutMypage.setOnClickListener {
                (context as MainActivity).logout()
            }
        }
    }

    private fun initAdapter(){
        val recentOrder = binding.rcvOrderhistoryMypagefg
        val adapter = RecentOrderAdapter()
        adapter.orderList.addAll(orderList)
        adapter.orderDetailList.addAll(orderDetailResponseList)
        recentOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.onFoldButtonLIstener = object : OnFoldButtonLIstener{
            override fun onClick(
                orderDetailList: MutableList<OrderDetailResponse>,
                binding: ItemListRecentOrderBinding,
                isExpended: Boolean
            ) {
                val isExpanded = isExpended
                val recentDetailAdapter = RecentOrderDetailAdapter(requireContext())
                val recentDetailContainer = binding.rcvRecentdetailRecentorder
                recentDetailAdapter.itemList.addAll(orderDetailList)
                recentDetailContainer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                ToggleAnimation.toggleArrow(binding.btnFoldRecentorder, !isExpanded)

                when(isExpanded){
                    true -> {
                        ToggleAnimation.collapse(recentDetailContainer)
                    }
                    false -> {
                        recentDetailContainer.adapter = recentDetailAdapter
                        ToggleAnimation.expand(recentDetailContainer)
                    }
                }
            }
        }
        recentOrder.adapter = adapter
    }
}