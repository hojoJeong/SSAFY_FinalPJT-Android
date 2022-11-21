package com.ssafy.silencelake.fragment.main.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.databinding.FragmentMypageBinding
import com.ssafy.silencelake.databinding.ItemListRecentOrderBinding
import com.ssafy.silencelake.dto.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var userInfo: UserDto
    private var orderList = mutableListOf<OrderDto>()
    private var orderDetailList = mutableListOf<OrderDetail>()
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUserData()
        initOrderData()
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
    private fun initUserData(){
        userInfo = UserDto("id 01", "이수용", "pass 01", 63)
    }

    private fun initOrderData(){
        orderDetailList.add(OrderDetail(1, 1, 1, 3))
        orderDetailList.add(OrderDetail(2, 1, 2, 4))
        orderDetailList.add(OrderDetail(3, 1, 3, 2))
        orderDetailList.add(OrderDetail(3, 1, 3, 2))


        val stamp = Stamp(1, 1, 2, "id 01")
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.03.18", stamp, "id 01"))
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.04.23", stamp, "id 01"))
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.03.18", stamp, "id 01"))
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.04.23", stamp, "id 01"))
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.03.18", stamp, "id 01"))
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.04.23", stamp, "id 01"))
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.03.18", stamp, "id 01"))
        orderList.add(OrderDto('N', orderDetailList, 1, "1번 테이블", "2022.04.23", stamp, "id 01"))
    }

    private fun initView(){
        binding.apply {
            tvUsernameMypage.text = userInfo.name
            tvLevelMypagefg.text = (userInfo.stamps / 10).toString()
            progressLevelMypagefg.progress = (userInfo.stamps % 10) * 10
        }
    }

    private fun initAdapter(){
        val recentOrder = binding.rcvOrderhistoryMypagefg
        val adapter = RecentOrderAdapter()
        adapter.orderList.addAll(orderList)
        recentOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.onFoldButtonLIstener = object : OnFoldButtonLIstener{
            override fun onClick(
                orderDetailList: MutableList<OrderDetail>,
                binding: ItemListRecentOrderBinding
            ) {
                val recentDetailAdapter = RecentOrderDetailAdapter(requireContext())
                val recentDetailContainer = binding.rcvRecentdetailRecentorder
                recentDetailAdapter.itemList.addAll(orderDetailList)
                recentDetailContainer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                ToggleAnimation.toggleArrow(binding.btnFoldRecentorder, !isExpanded)

                when(isExpanded){
                    true -> {
                        ToggleAnimation.collapse(recentDetailContainer)
                        isExpanded = false
                    }
                    false -> {
                        recentDetailContainer.adapter = recentDetailAdapter
                        ToggleAnimation.expand(recentDetailContainer)
                        isExpanded = true
                    }
                }
            }
        }
        recentOrder.adapter = adapter
    }
}