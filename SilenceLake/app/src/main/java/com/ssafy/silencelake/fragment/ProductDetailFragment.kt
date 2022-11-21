package com.ssafy.silencelake.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.silencelake.databinding.FragmentProductDetailBinding
import com.ssafy.silencelake.util.ProductDetailViewPagerAdapter


class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val tabTitleArray = arrayOf(
        "ORDER",
        "COMMENT"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.viewPagerOrder
        val tabLayout = binding.tabLayoutOrder

        viewPager.adapter = ProductDetailViewPagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }
}