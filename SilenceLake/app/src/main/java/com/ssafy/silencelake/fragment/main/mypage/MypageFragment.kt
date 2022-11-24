package com.ssafy.silencelake.fragment.main.mypage

import android.content.ContentValues.TAG
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
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
import com.ssafy.silencelake.util.ApplicationClass
import com.ssafy.silencelake.util.SharedPreferencesUtil
import com.ssafy.smartstore.response.OrderDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG = "MypageFragment_싸피"
class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var userInfo: UserDto
    private lateinit var adapter: RecentOrderAdapter
    private var orderList = mutableListOf<OrderDto>()
    private var orderDetailResponseList = listOf<List<OrderDetailResponse>>()
    private val userResponseViewModel by activityViewModels<UserResponseViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        initAdapter()
        initData()
    }
    private fun initData(){
        val userId = SharedPreferencesUtil(requireContext()).getUser().id
        userResponseViewModel.getUserResponseInfo(userId)

        userResponseViewModel.userResponseDto.observe(viewLifecycleOwner){
            userInfo = userResponseViewModel.userResponseDto.value!!.user
            orderList = userResponseViewModel.userResponseDto.value!!.order
            userResponseViewModel.getOrderDetail()
            Log.d(TAG, "initData: orderList : $orderList")
            userResponseViewModel.orderDetailResponseList.observe(viewLifecycleOwner){
                orderDetailResponseList = userResponseViewModel.orderDetailResponseList.value?: emptyList()
                Log.d(TAG, "initData: orderdetailList : $orderDetailResponseList")
                Log.d(TAG, "initData: $orderDetailResponseList")
                adapter.orderList = orderList
                adapter.orderDetailList = orderDetailResponseList
                binding.rcvOrderhistoryMypagefg.adapter = adapter
                adapter.notifyDataSetChanged()
                initView()
            }
        }
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
        adapter = RecentOrderAdapter()
        binding.rcvOrderhistoryMypagefg.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.onFoldButtonLIstener = object : OnFoldButtonLIstener{
            override fun onClick(
                orderDetailList: List<OrderDetailResponse>,
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
    }
}