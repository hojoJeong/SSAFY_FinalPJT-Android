package com.ssafy.silencelake.dto

//DTO에서 stampList 빠져있음!!
data class UserDto(val id: String, val name: String, val pass: String, var stamps: Int) {
    constructor(): this("","","",0)
    constructor(id:String, pass:String):this(id, "",pass,0)
}