package com.example.takegoal

import com.google.gson.Gson

fun main(){
    var json="{\"name\":\"john\"}"
    var gson= Gson()
    var use=gson.fromJson(json,user::class.java)
    println(use.name)
}
class user{
    var name:String?=null
}