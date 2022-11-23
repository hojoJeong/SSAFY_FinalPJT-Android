package com.ssafy.silencelake.repository


import android.util.Log

import com.ssafy.silencelake.dto.UserDto
import com.ssafy.silencelake.util.RetrofitCallback
import com.ssafy.silencelake.util.RetrofitUtil

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LoginService_싸피"
class UserRepository {

    companion object{
        fun login(user: UserDto, callback: RetrofitCallback<UserDto>) {
            RetrofitUtil.userService.login(user).enqueue(object : Callback<UserDto> {
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
            RetrofitUtil.userService.insert(user).enqueue(object : Callback<Boolean> {
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

        fun checkDuplicatedId(id: String, callback: RetrofitCallback<Boolean>) {
            RetrofitUtil.userService.isUsedId(id).enqueue(object : Callback<Boolean> {
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