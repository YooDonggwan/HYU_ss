package com.example.takegoal

import android.app.AlertDialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.make_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MakeUser :AppCompatActivity(){
    var AlreadyId=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.make_user)
        var retrofit=Retrofit.Builder().baseUrl("http://172.30.1.2:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service=retrofit.create(RetrofitNetwork::class.java)

        confirmButton.setOnClickListener {
            //중복검사

         var Id=idText.text.toString()
            service.read(Id).enqueue(object:Callback<Bigdata>{
                override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                println(t.message)

                }
                override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) {
                    var value=response.body()?.result.toString()
                println(value)
                    if(value==Id){
                    AlreadyId=false
                }
                }
            })
            Handler().postDelayed({
            if(!AlreadyId){//아이디가 존재할경우
                var alert=AlertDialog.Builder(this)
                alert.setMessage("이미 존재하는 Id입니다.")
                alert.setIcon(R.mipmap.ic_launcher)
                alert.setPositiveButton("예") { dialogInterface: DialogInterface, i: Int ->
                    AlreadyId = true
                    idText.setText("")
                    Toast.makeText(this, "다시 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                alert.show()
            }

            if(AlreadyId){//아이디가 존재하지않을경우
                var alert=AlertDialog.Builder(this)
                alert.setMessage("사용가능한 아이디입니다.")
                alert.setIcon(R.mipmap.ic_launcher)
                alert.setPositiveButton("예") { dialogInterface: DialogInterface, i: Int ->
                    AlreadyId = true
                    Toast.makeText(this, "다시 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                alert.show()
            }},1000)
        }
        completeUser.setOnClickListener {
            //회원가입-->값 입력, 데이터베이스에 저장
            var Id=idText.text.toString()
            var password=password.text.toString()
            var name=nameText.text.toString()
            var phoneNum=phone.text.toString()
            var email=emailText.text.toString()
            var make_member=Member(name,Id,Goal_List=ArrayList<GoalModel>(),password=password,email=email)
            service.listUser(make_member)?.enqueue(object: Callback<Bigdata>{
                override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                    println("실패")
                }
                override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) {
                onBackPressed()
                }

            })

        }
    }

}