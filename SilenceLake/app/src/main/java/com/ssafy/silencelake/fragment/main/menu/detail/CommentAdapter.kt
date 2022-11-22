package com.ssafy.silencelake.fragment.main.menu.detail

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.CommentListItemBinding
import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.silencelake.util.RetrofitUtil
import com.ssafy.silencelake.util.SharedPreferencesUtil

class CommentAdapter( val context: Context) :
    RecyclerView.Adapter<CommentAdapter.CommentHolder>() {
    var commentList = mutableListOf<CommentDto>()
    private lateinit var itemClickListner: ItemClickListener
    val mContext = context

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

    inner class CommentHolder(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var id: String
        fun bindInfo(data: CommentDto) {
            hideAllButton()
            Log.d(TAG, "bindInfo: ${data.comment}")
            binding.textNoticeContentComment.text = data.comment

            id = data.userId
            showButtonOnMyComment(id)

            modifyAcceptBtnEventListener()
            modifyCancelBtnEventListener()
            modifyBtnEventListener()
            deleteBtnEventListener(data.id)
        }

        private fun modifyAcceptBtnEventListener(){
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

        private fun modifyCancelBtnEventListener(){
            binding.ivModifyCancelComment.setOnClickListener {
                hideAllButton()
                binding.textNoticeContentComment.visibility = View.VISIBLE
                showButtonOnMyComment(id)
            }
        }

        private fun modifyBtnEventListener(){
            binding.ivModifyComment.setOnClickListener {
                hideAllButton()
                binding.apply {
                    textNoticeContentComment.visibility = View.GONE
                    ivModifyAcceptComment.visibility = View.VISIBLE
                    editTextContentComment.visibility = View.VISIBLE
                    ivModifyCancelComment.visibility = View.VISIBLE
                }
//                val comment = Comment(data.commentId, data.userId!!, -1, data.productRating.toFloat(), etCommentContent.text.toString())
//                itemClickListner.onAcceptClick(comment)
            }
        }

        private fun deleteBtnEventListener(id: Int){
            binding.ivDeleteComment.setOnClickListener {

            }
        }

        fun showButtonOnMyComment(id: String) {
            val userId = SharedPreferencesUtil(mContext).getUser().id
            if (userId == id) {
                binding.ivModifyComment.visibility = View.VISIBLE
                binding.ivDeleteComment.visibility = View.VISIBLE
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
        fun onAcceptClick(comment: CommentDto)
        fun onDeleteClick(id: Int)
        fun onModifyClick(comment: CommentDto)
    }
}
