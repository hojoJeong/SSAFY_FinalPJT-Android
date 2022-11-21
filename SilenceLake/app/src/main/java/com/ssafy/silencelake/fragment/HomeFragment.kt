package com.ssafy.silencelake.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.FragmentHomeBinding
import com.ssafy.silencelake.dto.ProductDto
import pyxis.uzuki.live.rollingbanner.RollingViewPagerAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    lateinit var mContext: Context
    private var bannerItemList = arrayListOf<String>()
    private var recommendedItemList = mutableListOf<ProductDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mContext = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBanner()
    }

    private fun initBanner() {
        val banner = binding.viewpagerBannerHomefg
        val bannerAdapter = BannerAdapter(requireContext(), bannerItemList)
        banner.setAdapter(bannerAdapter)
    }

    private fun initData(){
        initBannerItemData()
        initRecommendedItemData()
    }
    private fun initBannerItemData(){
        bannerItemList.add("수용이~")
        bannerItemList.add("호조~")
        bannerItemList.add("수용이~")
        bannerItemList.add("호조~")
        bannerItemList.add("수용이~")
    }

    private fun initRecommendedItemData(){
        recommendedItemList.add(ProductDto(1, "아메리카노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(2, "아메리카노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(3, "아메리카노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(4, "아메리카노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(5, "아메리카노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(5, "아메리카노", "coffee", 2000, "iceamericano"))
    }


    inner class BannerAdapter : RollingViewPagerAdapter<String> {
        constructor(context: Context, itemList: ArrayList<String>) : super(context, itemList)

        override fun getView(position: Int, item: String): View {
            val view = LayoutInflater.from(mContext).inflate(R.layout.container_banner, null, false)
            val container = view.findViewById<FrameLayout>(R.id.container_banner)
            val tvBannerItem = view.findViewById<TextView>(R.id.tv_benneritem_container)

            val bannerItem = getItem(position)
            val index = itemList.indexOf(bannerItem)
            tvBannerItem.text = bannerItem
            container.setBackgroundResource(R.drawable.background_banner)
            view.setOnClickListener {
                Toast.makeText(mContext, "수용아 해냈다", Toast.LENGTH_SHORT).show()
            }
            return view
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}