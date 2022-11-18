package com.ssafy.silencelake

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.ssafy.silencelake.databinding.FragmentProductMenuBinding

class ProductMenuFragment : Fragment() {
    private lateinit var binding: FragmentProductMenuBinding
    private lateinit var mainActivity: MainActivity
    val list = listOf<Int>(1,2,3,4,5,6,7,8)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcvProductmenuMenu.adapter = ProductMenuAdapter(requireContext(), list)
        val decoration = DividerItemDecoration(requireContext(), VERTICAL)
        binding.rcvProductmenuMenu.addItemDecoration(decoration)
        binding.fab.setOnClickListener {
            mainActivity.openShoppingList()
        }
    }
}