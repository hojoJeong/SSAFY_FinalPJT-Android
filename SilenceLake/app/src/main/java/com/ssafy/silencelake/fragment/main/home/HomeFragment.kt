package com.ssafy.silencelake.fragment.main.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.FragmentHomeBinding
import com.ssafy.silencelake.dto.ProductDto
import com.ssafy.silencelake.fragment.main.menu.detail.ProductDetailFragment
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
        initAdapter()
    }

    private fun initBanner() {
        val banner = binding.viewpagerBannerHomefg
        val bannerAdapter = BannerAdapter(requireContext(), bannerItemList)
        banner.setAdapter(bannerAdapter)
    }

    private fun initData() {
        initBannerItemData()
        initRecommendedItemData()
    }

    private fun initBannerItemData() {
        bannerItemList.add("banner_ssafy1")
        bannerItemList.add("banner_ssafy_ad")
        bannerItemList.add("banner_ssafy_logo")
    }

    private fun initRecommendedItemData() {
        recommendedItemList.add(ProductDto(1, "아메리카노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(2, "카푸치노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(3, "카라멜", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(4, "민트초코", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(5, "아메리카노", "coffee", 2000, "iceamericano"))
        recommendedItemList.add(ProductDto(5, "아메리카노", "coffee", 2000, "iceamericano"))
    }

    private fun initAdapter() {
        val adapter = RecommendedMenuAdapter(requireContext())
        val recommendedMenu = binding.rcvRecommendedHome
        recommendedMenu.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter.itemlist.addAll(recommendedItemList)
        adapter.onClickRecommendedItem = object : OnClickRecommendedItem {
            override fun onClick(product: ProductDto) {
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container_main, ProductDetailFragment()).addToBackStack(null).commit()
            }
        }

        recommendedMenu.adapter = adapter
    }


    inner class BannerAdapter : RollingViewPagerAdapter<String> {
        constructor(context: Context, itemList: ArrayList<String>) : super(context, itemList)

        override fun getView(position: Int, item: String): View {
            val view = LayoutInflater.from(mContext).inflate(R.layout.container_banner, null, false)
            val bannerImg = view.findViewById<ImageView>(R.id.img_banner_container)

            val bannerItem = getItem(position)
            val resId = requireContext().resources.getIdentifier(bannerItem, "drawable", requireContext().packageName)
            bannerImg.setImageResource(resId)
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