package com.example.takegoal


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.Html.fromHtml
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test.setOnClickListener {


        }
        //makeUser.text = fromHtml("<u>"+"회원가입"+"</u>")
        makeUser.setOnClickListener{
            val nextIntent=Intent(this,MakeUser::class.java)
            startActivity(nextIntent)
        }
        loginButton.setOnClickListener {
            var Check=false
            //로그인 눌렀을때 일어나는일--디비와 소통
            var putIde=putId.text.toString()
            var password=putPassword.text.toString()
            var retrofit=Retrofit.Builder().baseUrl("http://172.30.1.2:3000")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service=retrofit.create(RetrofitNetwork::class.java)
            service.confrimUser(putIde,password).enqueue(object:Callback<Bigdata>{
                override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                println("실패22")
                }
                override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) {
                if(response.body()?.result.toString()=="맞음"){
                    Check=true
                }
                }

            })
              Handler().postDelayed({if(Check){
                  println("들어가짐")
                  Check=false
                  val nextIntent = Intent(this, InGroup::class.java)
                  startActivity(nextIntent)
              }},500)

        }
        readyButton.setOnClickListener{
            val nextIntent = Intent(this, InGroup::class.java)
            startActivity(nextIntent)
        }

    }}


