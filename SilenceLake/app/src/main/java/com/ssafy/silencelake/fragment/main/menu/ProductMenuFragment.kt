package com.ssafy.silencelake.fragment.main.menu

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.ssafy.silencelake.activity.MainActivity
import com.ssafy.silencelake.databinding.FragmentProductMenuBinding
import com.ssafy.silencelake.fragment.main.menu.ProductMenuAdapter
import com.ssafy.smartstore.service.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "ProductMenuFragment_싸피"
class ProductMenuFragment : Fragment() {
    private lateinit var binding: FragmentProductMenuBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var menuAdapter: ProductMenuAdapter
    private val productViewModel by activityViewModels<ProductMenuViewModel>()

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

        productViewModel.getProductList()
        menuAdapter = ProductMenuAdapter(requireContext(), listOf())
        binding.apply {
            rcvProductmenuMenu.adapter = menuAdapter
            rcvProductmenuMenu.layoutManager = LinearLayoutManager(context)
            rcvProductmenuMenu.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
        productViewModel.menuProductList.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: it")
            menuAdapter.list = it
            menuAdapter.notifyDataSetChanged()
        }

        binding.fab.setOnClickListener {
            mainActivity.openShoppingList()
        }
    }

}