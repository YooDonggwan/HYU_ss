package com.example.takegoal
import retrofit2.Call
import retrofit2.http.*

data class Response(val result:String?=null)
interface HowService {//api 관리
    @GET("/howl")
    fun getRequest(@Query("name")name:String): Call<Response>
    @GET("/howl/{id}")
    fun getParamRequset(@Path("id")id:String):Call<Response>
    @POST("/howl")
    @FormUrlEncoded()
    fun postRequest(@Field("id")id:String,@Field("password")password:String):Call<Response>
    @PUT("/howl/{id}")
    fun putRequest(@Path("id")id:String,@Field("content")content:String):Call<Response>
    @DELETE("/howl/{id}")
    fun deleteRequest(@Path("id")id:String):Call<Response>

}