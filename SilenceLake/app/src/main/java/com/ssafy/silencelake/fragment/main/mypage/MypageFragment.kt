package com.ssafy.silencelake.fragment.main.mypage

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.FragmentMypageBinding
import com.ssafy.silencelake.databinding.ItemListRecentOrderBinding
import com.ssafy.silencelake.databinding.ItemListRecentOrderDetailBinding
import com.ssafy.silencelake.dto.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var detailBinding: ItemListRecentOrderDetailBinding
    private lateinit var userInfo: UserDto
    private var orderList = mutableListOf<OrderDto>()
    private var orderDetailList = mutableListOf<OrderDetail>()
    private var toggle = false
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        var degree = 0f
        adapter.orderList.addAll(orderList)
        recentOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.onFoldButtonLIstener = object : OnFoldButtonLIstener{
            override fun onClick(
                orderDetailList: MutableList<OrderDetail>,
                binding: ItemListRecentOrderBinding
            ) {
                degree += 180
                binding.btnFoldRecentorder.rotation = degree
                val detailAdapter = RecentOrderDetailAdapter(requireContext())
                val detailContainer = binding.rcvRecentdetailRecentorder
                detailAdapter.itemList.addAll(orderDetailList)
                detailContainer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                detailContainer.adapter = detailAdapter

                when(toggle){
                    true -> {
                        val valueAnimator = ValueAnimator.ofInt(orderDetailList.size*333, 0)

                        valueAnimator.setDuration(250)
                        valueAnimator.addUpdateListener {
                            detailContainer.apply {
                                layoutParams.height = it.getAnimatedValue() as Int
                                requestLayout()
                                visibility = View.GONE
                            }
                        }
                        valueAnimator.start()
                        toggle = false
                    }
                    false -> {
                        val valueAnimator = ValueAnimator.ofInt(0, orderDetailList.size*333)
                        valueAnimator.setDuration(250)
                        valueAnimator.addUpdateListener {
                            detailContainer.apply {
                                layoutParams.height = it.getAnimatedValue() as Int
                                requestLayout()
                                visibility = View.VISIBLE
                            }
                        }
                        valueAnimator.start()
                        toggle = true
                    }
                }
            }
        }
        recentOrder.adapter = adapter

    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MypageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}