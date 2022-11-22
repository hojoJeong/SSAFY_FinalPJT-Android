package com.ssafy.silencelake.fragment.main.menu.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.silencelake.databinding.FragmentProductDetailBinding
import com.ssafy.silencelake.fragment.main.menu.shoppinglist.ShoppingListViewModel
import com.ssafy.smartstore.response.MenuDetailWithCommentResponse


class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val activityViewModel by activityViewModels<ShoppingListViewModel>()
    private val tabTitleArray = arrayOf(
        "ORDER",
        "COMMENT"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.viewPagerOrder
        val tabLayout = binding.tabLayoutOrder

        binding.root.setOnTouchListener{_,_ ->
            true
        }
        activityViewModel.selectedProduct.observe(viewLifecycleOwner){
            initView(it[0])
            viewPager.adapter = ProductDetailViewPagerAdapter(childFragmentManager, lifecycle)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitleArray[position]
            }.attach()
        }
    }

    fun initView(item: MenuDetailWithCommentResponse){
        Glide.with(binding.root)
            .load(item.productImg)
            .into(binding.imgProductOrder)
        binding.textProductNameKorOrder.text = item.productName
    }

}