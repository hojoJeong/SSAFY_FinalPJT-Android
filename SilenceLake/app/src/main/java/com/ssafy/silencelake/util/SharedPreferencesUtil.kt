package com.ssafy.silencelake.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.silencelake.dto.UserDto

class SharedPreferencesUtil(context: Context) {
    val SHARED_PREFERENCES_NAME = "silencelake_preferences"
    val COOKIES_KEY_NAME = "cookies"

    var preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    fun addUser(user: UserDto){
        val editor = preferences.edit()
        editor.putString("id", user.id)
        editor.putString("name", user.name)
        editor.apply()
    }
    fun getUser(): UserDto{
        val id = preferences.getString("id", "")
        if(id != ""){
            val name = preferences.getString("name", "")
            return UserDto(id!!, name!!, "", 0)
        }else{
            return UserDto()
        }
    }
    fun deleteUser(){
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
    fun addUserCookie(cookies: HashSet<String>){
        val editor = preferences.edit()
        editor.putStringSet(COOKIES_KEY_NAME, cookies)
        editor.apply()
    }

    fun getUserCookie(): MutableSet<String>?{
        return preferences.getStringSet(COOKIES_KEY_NAME, HashSet())
    }
    fun deleteUserCookie(){
        preferences.edit().remove(COOKIES_KEY_NAME).apply()
    }
}