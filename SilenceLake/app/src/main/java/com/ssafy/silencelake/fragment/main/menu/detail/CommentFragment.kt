package com.ssafy.silencelake.fragment.main.menu.detail

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.silencelake.databinding.FragmentCommentBinding
import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.silencelake.fragment.main.menu.shoppinglist.ShoppingListViewModel
import com.ssafy.silencelake.util.SharedPreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.notify

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private val shoppingListViewModel by activityViewModels<ShoppingListViewModel>()
    private val commentViewModel by activityViewModels<CommentViewModel>()
    private var commentList = listOf<CommentDto>()
    private lateinit var adapter: CommentAdapter
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

        initAdapter()

        shoppingListViewModel.selectedProduct.observe(viewLifecycleOwner) {
            insertCommentBtnListener()
            adapter.notifyDataSetChanged()
        }

    }

    private fun initAdapter() {
        commentViewModel.getComment(shoppingListViewModel.productId)
        val selectProductList = shoppingListViewModel.selectedProduct.value
        if (selectProductList!![0].productCommentTotalCnt > 0) {
            val list = mutableListOf<CommentDto>()
            for (i in selectProductList.indices) {
                val comment = selectProductList[i].commentContent
                val commentId = selectProductList[i].commentId
                val productId = shoppingListViewModel.productId
                val userId = selectProductList[i].userId
                val productRating = selectProductList[i].productRating

                list.add(CommentDto(commentId, userId!!, productId, productRating.toFloat(), comment!!))
            }
            commentList = list
            val rcvComment = binding.rcvCommentComment
            adapter = CommentAdapter(requireContext())
            adapter.commentList = commentList as MutableList<CommentDto>
            rcvComment.layoutManager = LinearLayoutManager(requireContext())
            rcvComment.adapter = adapter

            adapter.itemClickListner = object : CommentAdapter.ItemClickListener {
                override fun onDeleteClick(id: Int) {
                    deleteComment(id)
                }
                override fun onModifyClick(comment: CommentDto) {
                    updateComment(comment)
                }
            }
        }
    }

    private fun insertCommentBtnListener() {
            binding.ivInsertComment.setOnClickListener {
                val contents = binding.etvCommentCommentfg.text.toString()
                if (contents.isEmpty()) {
                    Toast.makeText(requireContext(), "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    val userId = SharedPreferencesUtil(requireContext()).getUser().id
                    val productId = shoppingListViewModel.productId
                    val rating = binding.ratingbarSmallComment.rating
                    val contents = binding.etvCommentCommentfg.text.toString()

                    val comment = CommentDto(0, userId, productId, rating, contents)
                    commentViewModel.insertComment(comment)

                    shoppingListViewModel.getSelectedProduct(shoppingListViewModel.productId)
                    binding.etvCommentCommentfg.text.clear()
                }
            }
    }

    private fun updateComment(commentDto: CommentDto){
        commentViewModel.updateComment(commentDto)
        shoppingListViewModel.getSelectedProduct(shoppingListViewModel.productId)
    }

    private fun deleteComment(commentId: Int){
        commentViewModel.deleteComment(commentId)
        shoppingListViewModel.getSelectedProduct(shoppingListViewModel.productId)
    }
}