package com.ssafy.silencelake.fragment.main.menu.detail

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.CommentListItemBinding
import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.silencelake.util.SharedPreferencesUtil

class CommentAdapter( val context: Context) :
    RecyclerView.Adapter<CommentAdapter.CommentHolder>() {
    var commentList = mutableListOf<CommentDto>()
    lateinit var itemClickListner: ItemClickListener
    val mContext = context

    inner class CommentHolder(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var id: String
        fun bindInfo(data: CommentDto) {
            hideAllButton()
            Log.d(TAG, "bindInfo: ${data.comment}")
            binding.textNoticeContentComment.text = data.comment

            id = data.userId
            showButtonOnMyComment(id)

            modifyAcceptBtnEventListener(data)
            modifyCancelBtnEventListener()
            modifyBtnEventListener()
            deleteBtnEventListener(data.id)
        }

        private fun modifyBtnEventListener(){
            binding.ivModifyComment.setOnClickListener {
                hideAllButton()
                showEditState()
//                val comment = Comment(data.commentId, data.userId!!, -1, data.productRating.toFloat(), etCommentContent.text.toString())
//                itemClickListner.onAcceptClick(comment)
            }
        }

        private fun deleteBtnEventListener(id: Int){
            binding.ivDeleteComment.setOnClickListener {
                itemClickListner.onDeleteClick(id)
            }
        }

        private fun modifyAcceptBtnEventListener(data: CommentDto){
            binding.ivModifyAcceptComment.setOnClickListener {
                val commentId = data.id
                val userId = data.userId
                val productId = data.productId
                val rating = data.rating
                val contents = binding.editTextContentComment.text.toString()
                val comment = CommentDto(commentId, userId, productId, rating, contents)
                Log.d(TAG, "modifyAcceptBtnEventListener: $comment")
                itemClickListner.onModifyClick(comment)
                binding.editTextContentComment.text.clear()
                hideAllButton()
                binding.textNoticeContentComment.visibility = View.VISIBLE
                showButtonOnMyComment(data.userId)
            }
        }

        private fun modifyCancelBtnEventListener(){
            binding.ivModifyCancelComment.setOnClickListener {
                hideAllButton()
                binding.textNoticeContentComment.visibility = View.VISIBLE
                showButtonOnMyComment(id)
            }
        }

        fun hideAllButton() {
            binding.apply {
                ivModifyAcceptComment.visibility = View.GONE
                editTextContentComment.visibility = View.GONE
                ivModifyCancelComment.visibility = View.GONE
                ivDeleteComment.visibility = View.GONE
                ivModifyComment.visibility = View.GONE
            }
        }

        private fun showButtonOnMyComment(id: String) {
            val userId = SharedPreferencesUtil(mContext).getUser().id
            if (userId == id) {
                binding.ivModifyComment.visibility = View.VISIBLE
                binding.ivDeleteComment.visibility = View.VISIBLE
            }
        }

        private fun showEditState(){
            binding.apply {
                textNoticeContentComment.visibility = View.GONE
                ivModifyAcceptComment.visibility = View.VISIBLE
                editTextContentComment.visibility = View.VISIBLE
                ivModifyCancelComment.visibility = View.VISIBLE
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

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${commentList.size}")
        return commentList.size
    }

    interface ItemClickListener {
        fun onDeleteClick(id: Int)
        fun onModifyClick(comment: CommentDto)
    }
}
