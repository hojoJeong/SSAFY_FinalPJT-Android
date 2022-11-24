package com.ssafy.silencelake.repository

import android.util.Log
import com.ssafy.silencelake.util.RetrofitUtil

private const val TAG = "FcmRepository_싸피"
class FcmRepository {
    companion object{
        suspend fun sendMessageTo(title: String, body: String, token: String){
            val response = RetrofitUtil.fcmApi.sendMessageTo(title, body, token)
            if(response.isSuccessful){
                Log.d(TAG, "sendMessageTo: ${response.message()}")
            }else{
                Log.d(TAG, "sendMessageTo: f")
            }
            
        }
        suspend fun sendMessageToAdmin(){
            val response = RetrofitUtil.fcmApi.sendMessageToAdmin()
            if(response.isSuccessful){
                Log.d(TAG, "sendMessageTo: ${response.message()}")
            }else{
                Log.d(TAG, "sendMessageTo: f")
            }

        }
    }
}