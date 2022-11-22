package com.ssafy.silencelake.fragment.main.menu.detail

import android.os.Bundle
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

        initAdapter()
    }

    private fun initAdapter() {
        shoppingListViewModel.selectedProduct.observe(viewLifecycleOwner) {
            if (it[0].productCommentTotalCnt > 0) {
                val list = mutableListOf<CommentDto>()
                for (i in 0 until it.size) {
                    val comment = it[i].commentContent
                    val commentId = it[i].commentId
                    val productId = shoppingListViewModel.productId
                    val userId = it[i].userId
                    val productRating = it[i].productRatingAvg

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
                binding.rcvCommentComment.apply {
                    adapter = CommentAdapter(commentList)
                    layoutManager = LinearLayoutManager(requireContext())
                }
                list.clear()
            }

        }
    }
}