package com.ssafy.silencelake.fragment.main.menu.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.R

class CommentAdapter(var list: List<Int>) :
    RecyclerView.Adapter<CommentAdapter.CommentHolder>() {
    interface ItemClickListener {
//        fun onAcceptClick(comment: Comment)
//        fun onDeleteClick(id: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

    inner class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cancelButton = itemView.findViewById<ImageView>(R.id.iv_modify_cancel_comment)
        val deleteButton = itemView.findViewById<ImageView>(R.id.iv_delete_comment)
        val modifyButton = itemView.findViewById<ImageView>(R.id.iv_modify_comment)
        val acceptButton = itemView.findViewById<ImageView>(R.id.iv_modify_accept_comment)
        val etCommentContent = itemView.findViewById<EditText>(R.id.editText_content_comment)
        val noticeContent = itemView.findViewById<TextView>(R.id.text_noticeContent_comment)

        fun bindInfo(data: Int) {
            hideAllButton()
            itemView.apply {
                noticeContent.text = data.toString()
                showButtonOnMyComment(1)
            }
            modifyButton.setOnClickListener {
                hideAllButton()
                etCommentContent.visibility = View.VISIBLE
                acceptButton.visibility = View.VISIBLE
                cancelButton.visibility = View.VISIBLE
                noticeContent.visibility = View.GONE
            }
            acceptButton.setOnClickListener {
                hideAllButton()
                showButtonOnMyComment(1)
//                val comment = Comment(data.commentId, data.userId!!, -1, data.productRating.toFloat(), etCommentContent.text.toString())
//                itemClickListner.onAcceptClick(comment)
            }
            deleteButton.setOnClickListener {
//                itemClickListner.onDeleteClick(data.commentId)
            }
        }
        fun showButtonOnMyComment(userId: Int){
            if(userId == 1){
                modifyButton.visibility = View.VISIBLE
                deleteButton.visibility = View.VISIBLE
            }
        }
        fun hideAllButton() {
            cancelButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
            modifyButton.visibility = View.GONE
            acceptButton.visibility = View.GONE
            etCommentContent.visibility = View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)
        return CommentHolder(view)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bindInfo(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
