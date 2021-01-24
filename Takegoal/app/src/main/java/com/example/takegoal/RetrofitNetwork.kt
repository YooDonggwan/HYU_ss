package com.example.takegoal

import retrofit2.Call
import retrofit2.http.*

data class Bigdata(var result:String)
interface RetrofitNetwork {

    @POST("/user")
    fun listUser(@Body member: Member
    ): Call<Bigdata>
    @GET("/user")
    fun read(@Query("id")id:String):Call<Bigdata>
    @GET("/check")
    fun confrimUser(@Query("id")id:String,
                    @Query("password")password:String
                    ):Call<Bigdata>
//    @Field("name")name:String,
//    @Field("id")id:String,
//    @Field("point")point:Int,
//    @Field("goal_list")Goal_List:ArrayList<GoalModel>,
//    @Field("password")password:String,
//    @Field("phonNum")phonNum:String,
//    @Field("email")email:String

}