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

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private val shoppingListViewModel by activityViewModels<ShoppingListViewModel>()
    private val commentViewModel by activityViewModels<CommentViewModel>()
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

        binding.ratingbarSmallComment.rating = shoppingListViewModel.selectedProduct.value!![0].productRatingAvg.toFloat()
        initAdapter()
        insertCommentBtnListener()

        commentViewModel.comment.observe(viewLifecycleOwner) {
            adapter.commentList = it as MutableList<CommentDto>
            adapter.notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        val commentList = commentViewModel.comment.value
        if (commentList != null) {
            adapter = CommentAdapter(requireContext())
            adapter.commentList = commentList as MutableList<CommentDto>

            val rcvComment = binding.rcvCommentComment
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
                val productId = commentViewModel.productId
                val rating = binding.ratingbarSmallComment.rating
                val contents = binding.etvCommentCommentfg.text.toString()

                val comment = CommentDto(0, userId, productId, rating, contents)
                commentViewModel.insertComment(comment)
                binding.etvCommentCommentfg.text.clear()
            }
        }
    }

    private fun updateComment(commentDto: CommentDto) {
        commentViewModel.updateComment(commentDto)
    }

    private fun deleteComment(commentId: Int) {
        commentViewModel.deleteComment(commentId)
    }
}