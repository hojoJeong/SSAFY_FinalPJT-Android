package com.ssafy.silencelake.fragment.main.menu.shoppinglist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.silencelake.activity.MainActivity
import com.ssafy.silencelake.databinding.FragmentShoppingListBinding

private const val TAG = "ShoppingListFragment_싸피"

class ShoppingListFragment : Fragment() {
    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private val activityViewModel by activityViewModels<ShoppingListViewModel>()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnTouchListener { _, _ ->
            true
        }
        shoppingListAdapter = ShoppingListAdapter()
        binding.rcvShoppinglistShoppingList.adapter = shoppingListAdapter
        if (activityViewModel.shoppingList.value != null) {
            shoppingListAdapter.list = activityViewModel.shoppingList.value!!
            shoppingListAdapter.notifyDataSetChanged()
        }
        binding.buttonCloseShoppingList.setOnClickListener {
            mainActivity.onBackPressed()
        }


        activityViewModel.shoppingList.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: observe")
            shoppingListAdapter.list = activityViewModel.shoppingList.value!!
            shoppingListAdapter.notifyDataSetChanged()
        }
        shoppingListAdapter.itemClickListener =
            object : ShoppingListAdapter.ShoppingListItemClickListener {
                override fun plusBtnClicked(pos: Int) {
                    Log.d(TAG, "plusBtnClicked: ")
                    activityViewModel.list[pos].menuCnt += 1
                    activityViewModel.updateShoppingList()
                }

                override fun minusBtnClicked(pos: Int) {
                    activityViewModel.list[pos].menuCnt -= 1
                    activityViewModel.updateShoppingList()
                }
            }
    }


}