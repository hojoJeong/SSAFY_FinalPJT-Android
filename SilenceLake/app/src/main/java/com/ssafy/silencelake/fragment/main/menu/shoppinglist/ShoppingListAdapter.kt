package com.ssafy.silencelake.fragment.main.menu.shoppinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.ShoppingListItemBinding
import com.ssafy.silencelake.dto.ShoppingCart

class ShoppingListAdapter: RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>(){
    var list = emptyList<ShoppingCart>()
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imgProductMenu = itemView.findViewById<ImageView>(R.id.img_product_shoppingList)
        val productNameKor = itemView.findViewById<TextView>(R.id.text_name_kor_shoppingList)
        val productNameEng = itemView.findViewById<TextView>(R.id.text_name_eng_shoppingList)
        val totalPrice = itemView.findViewById<TextView>(R.id.text_totalPrice_shoppingList)
        val btnPlus = itemView.findViewById<ImageButton>(R.id.button_plus_shoppingList)
        val btnMinus = itemView.findViewById<ImageButton>(R.id.button_minus_shoppingList)
        val quantity = itemView.findViewById<TextView>(R.id.text_quantity_shoppingList)
        fun bindInfo(item: ShoppingCart){
            Glide.with(itemView)
                .load(item.menuImg)
                .into(imgProductMenu)
            productNameKor.text = item.menuName
            productNameEng.text = ""
            btnPlus.setOnClickListener {
                quantity.text = "${quantity.text.toString().toInt() + 1}"
                totalPrice.text = "${quantity.text.toString().toInt() * 1200}원"
            }
            btnMinus.setOnClickListener {
                quantity.text = "${quantity.text.toString().toInt() - 1}"
                totalPrice.text = "${quantity.text.toString().toInt() * 1200}원"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ShoppingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindInfo(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}