package com.ssafy.silencelake.fragment.main.menu.detail

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.databinding.FragmentCommentBinding
import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.silencelake.fragment.main.menu.shoppinglist.ShoppingListViewModel

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private val shoppingListViewModel by activityViewModels<ShoppingListViewModel>()
    private var commentList = listOf<CommentDto>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingListViewModel.selectedProduct.observe(viewLifecycleOwner){
            initAdapter()
        }
    }

    private fun initAdapter() {
        val selectProductList = shoppingListViewModel.selectedProduct.value
        if (selectProductList!![0].productCommentTotalCnt > 0) {
            val list = mutableListOf<CommentDto>()
            for (i in selectProductList.indices) {
                val comment = selectProductList[i].commentContent
                val commentId = selectProductList[i].commentId
                val productId = shoppingListViewModel.productId
                val userId = selectProductList[i].userId
                val productRating = selectProductList[i].productRatingAvg

                list.add(
                    CommentDto(
                        commentId,
                        userId!!,
                        productId,
                        productRating.toFloat(),
                        comment!!
                    )
                )
            }
            commentList = list
            Log.d(TAG, "initAdapter: comment : $commentList")
            val rcvComment = binding.rcvCommentComment
            val adapter = CommentAdapter(requireContext())
            adapter.commentList = commentList as MutableList<CommentDto>
            Log.d(TAG, "initAdapter adapter list : ${adapter.commentList}")
            rcvComment.layoutManager = LinearLayoutManager(requireContext())
            rcvComment.adapter = adapter
        }


    }
}