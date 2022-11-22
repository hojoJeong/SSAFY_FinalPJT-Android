package com.ssafy.silencelake.dto

data class UserDto(val id: String, val name: String, val pass: String,  var stamps: Int, var stampList: MutableList<Stamp>?,) {
    constructor(): this("","", "",  0,null,)
    constructor(id:String, pass:String):this(id, "",pass, 0, null)

}