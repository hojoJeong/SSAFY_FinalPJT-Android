package com.ssafy.silencelake

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ProductMenuItemBinding

class ProductMenuAdapter(val context: Context, var list: List<Int>):RecyclerView.Adapter<ProductMenuAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgProductMenu = itemView.findViewById<ImageView>(R.id.img_product_menu)
        val productNameKor = itemView.findViewById<TextView>(R.id.text_name_kor_menu)
        val productNameEng = itemView.findViewById<TextView>(R.id.text_name_eng_menu)
        fun bindInfo(item: Int){
            imgProductMenu.setImageResource(R.drawable.choco)
            productNameKor.text = context.getString(R.string.product_name_kor)
            productNameEng.text = context.getString(R.string.product_name_eng)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductMenuItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply{
            bindInfo(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}