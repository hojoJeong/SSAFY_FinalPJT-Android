package com.ssafy.hw_android_network_05_hojojeong.util

import android.app.Dialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.ssafy.silencelake.databinding.DialogCafeInfoBinding
import java.io.IOException
import java.util.*

class CustomDialog(context: Context) {
    private val binding = DialogCafeInfoBinding.inflate(LayoutInflater.from(context))
    private val dialog = Dialog(context)

    fun showDialog() {
        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)

        dialog.show()
    }

    fun showCafeInfo(status: Boolean){
        if(status){
            binding.apply {
                tvRequestDialog.visibility = View.GONE
                tvWifipassDialog.visibility = View.VISIBLE
                tvToiletpassDialog.visibility = View.VISIBLE
            }
        } else{
            binding.apply {
                tvRequestDialog.visibility = View.VISIBLE
                tvWifipassDialog.visibility = View.GONE
                tvToiletpassDialog.visibility = View.GONE
            }
        }
    }


    fun confirmBtnEventListener(){
        binding.btnConfirmDialog.setOnClickListener {
            dialog.dismiss()
        }
    }
}