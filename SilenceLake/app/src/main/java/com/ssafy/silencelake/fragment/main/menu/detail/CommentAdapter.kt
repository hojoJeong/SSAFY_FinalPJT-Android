package com.ssafy.silencelake.fragment.main.menu.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.CommentListItemBinding
import com.ssafy.silencelake.dto.CommentDto

class CommentAdapter(var commentList: List<CommentDto>) :
    RecyclerView.Adapter<CommentAdapter.CommentHolder>() {
    private lateinit var itemClickListner: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

    inner class CommentHolder(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(data: CommentDto) {
            hideAllButton()
            binding.textNoticeContentComment.text = data.comment

            showButtonOnMyComment(1)

            acceptBtnEventListener()
            modifyBtnEventListener()
            deleteBtnEventListener()
        }

        private fun acceptBtnEventListener(){
            binding.ivModifyAcceptComment.setOnClickListener {
                hideAllButton()
                binding.apply {
                    editTextContentComment.visibility = View.VISIBLE
                    ivModifyAcceptComment.visibility = View.VISIBLE
                    ivModifyCancelComment.visibility = View.VISIBLE
                    textNoticeContentComment.visibility = View.GONE
                }
            }
        }

        private fun modifyBtnEventListener(){
            binding.ivModifyAcceptComment.setOnClickListener {
                hideAllButton()
                showButtonOnMyComment(1)
//                val comment = Comment(data.commentId, data.userId!!, -1, data.productRating.toFloat(), etCommentContent.text.toString())
//                itemClickListner.onAcceptClick(comment)
            }
        }

        private fun deleteBtnEventListener(){
            binding.ivDeleteComment.setOnClickListener {
//                itemClickListner.onDeleteClick(data.commentId)
            }
        }

        fun showButtonOnMyComment(userId: Int) {
            if (userId == 1) {
                binding.ivModifyComment.visibility = View.VISIBLE
                binding.ivDeleteComment.visibility = View.VISIBLE
            }
        }
        fun hideAllButton() {
            binding.apply {
                ivModifyCancelComment.visibility = View.GONE
                ivDeleteComment.visibility = View.GONE
                ivModifyComment.visibility = View.GONE
                ivModifyAcceptComment.visibility = View.GONE
                editTextContentComment.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view =
            CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolder(view)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bindInfo(commentList[position])
    }

    override fun getItemCount(): Int = commentList.size

    interface ItemClickListener {
        fun onAcceptClick(comment: CommentDto)
        fun onDeleteClick(id: Int)
        fun onModifyClick(comment: CommentDto)
    }
}
