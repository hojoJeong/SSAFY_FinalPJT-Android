package com.ssafy.silencelake.repository


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.viewModelScope

import com.ssafy.silencelake.dto.UserDto
import com.ssafy.silencelake.dto.UserResponseDto
import com.ssafy.silencelake.util.RetrofitCallback
import com.ssafy.silencelake.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LoginService_싸피"
class UserRepository {

    companion object{
        fun login(user: UserDto, callback: RetrofitCallback<UserDto>) {
            RetrofitUtil.userApi.login(user).enqueue(object : Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    val res = response.body()
                    if (response.code() == 200) {
                        if (res != null) {
                            callback.onSuccess(response.code(), res)
                        }
                    } else {
                        callback.onFailure(response.code())
                    }
                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    callback.onError(t)
                }
            })
        }

        fun signUpUser(user: UserDto, callback: RetrofitCallback<Boolean>) {
            RetrofitUtil.userApi.insert(user).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.code() == 200) {
                        callback.onSuccess(response.code(), response.body() ?: false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
        }

        suspend fun getUserInfo(id: String): UserResponseDto? {
                val response = RetrofitUtil.userApi.getUserInfo(id)
                if (response.isSuccessful) {
                    Log.d(ContentValues.TAG, "getUserResponseInfo: userResponse 호출 성공")
                    return response.body()!!
                } else {
                    Log.d(ContentValues.TAG, "getUserResponseInfo: userResponse 호출 실패")
                    return null
                }
        }
        fun checkDuplicatedId(id: String, callback: RetrofitCallback<Boolean>) {
            RetrofitUtil.userApi.isUsedId(id).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.code() == 200) {
                        callback.onSuccess(response.code(), response.body() ?: true)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }
}