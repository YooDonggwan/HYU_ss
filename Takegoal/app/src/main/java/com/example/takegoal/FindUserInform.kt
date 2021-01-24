package com.example.takegoal

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.SmsManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.change_password.*
import kotlinx.android.synthetic.main.find_id.*
import kotlinx.android.synthetic.main.find_password.*
import kotlinx.android.synthetic.main.input_message_sample.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class FindUserInform : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var checkNum = getIntent().getIntExtra("check", 0)

        var alert = AlertDialog.Builder(this).create()
        if (checkNum === 1) {
            setContentView(R.layout.find_id)
            find_findButton2.setOnClickListener {
                //아이디찾기 눌렀을때-->이메일로 비밀번호 전송
                var name = find_checkId.text.toString()
                var email = find_emailCheck.text.toString()
                var birth = find_birthCheck.text.toString()
                MainActivity.service.findId(name, email, birth).enqueue(object : Callback<Bigdata> {
                    override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                        println("실패")
                    }

                    override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) {
                        if (response.body() != null) {
                            alert.setMessage("회원님의 아이디는 ${response.body()?.result}")
                        } else {
                            alert.setMessage("입력하신 회원님의 정보를 확인해주십시오.")
                        }
                        alert.show()
                    }
                })
            }
            find_cancelButton2.setOnClickListener {
                //취소버튼
                onBackPressed()
            }
        } else {
            setContentView(R.layout.find_password)
            var flag = false
            var timer = object : CountDownTimer(60000, 1000) {
                override fun onFinish() {
                    onBackPressed()
                }

                override fun onTick(millisUntilFinished: Long) {
                    var count = millisUntilFinished / 1000
                    show_timer.text = "${count / 60}:${count % 60}"
                }
            }
            find_phonNum.addTextChangedListener(PhoneNumberFormattingTextWatcher())
            confirm_user.setOnClickListener {
                var id = find_IdCheck.text.toString()
                MainActivity.service.sendMessage2(
                    id,
                    find_birthCheck2.text.toString(),
                    find_nameCheck.text.toString(),
                    find_phonNum.text.toString()
                )
                    .enqueue(object : Callback<Bigdata> {
                        override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                            println("실패")
                        }

                        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                        override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) {

                            var alert = AlertDialog.Builder(this@FindUserInform)
                            if (response.body()?.result != null) {
                                alert.setView(
                                    layoutInflater.inflate(
                                        R.layout.input_message_sample,
                                        null
                                    )
                                )
                                alert.setMessage("인증번호가 발송되었습니다.")
                                var randomNumber = Math.floor(Math.random() * 1000000)
                                var message =
                                    "[Take your Goal]에서 인증번호를 보내드립니다. 인증번호:${randomNumber}"
                                SmsManager.getDefault().sendTextMessage(
                                    find_phonNum.text.toString(),
                                    null,
                                    message,
                                    null,
                                    null
                                )

                                alert.setPositiveButton("확인") { dialog, which ->
                                    if (put_confirmNumber.text as Double == randomNumber) {
                                        flag = true
                                        dialog.dismiss()
                                    }
                                }
                                alert.setNegativeButton("취소") { dialog, which ->
                                    dialog.dismiss()
                                }
                                alert.setOnDismissListener {
                                    timer.cancel()
                                }
                                timer.start()
                            } else {
                                alert.setMessage("입력정보를 확인해주세요.")
                            }
                            alert.show()
                        }

                    })
            }
            find_findButton.setOnClickListener {
                //비밀번호찾기 -->서버와연동
                var Alert = AlertDialog.Builder(this).create()
                if (!flag) {
                    Alert.setView(layoutInflater.inflate(R.layout.change_password, null))
                    ok_button.setOnClickListener {
                        if (!Pattern.matches(
                                "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()])(?=.*[a-zA-Z]).{8,20}$",
                                new_password.text.toString()
                            )
                        ) {
                            Toast.makeText(this, "비밀번호양식을 맞춰주세요", Toast.LENGTH_LONG).show()
                        } else if (new_password.text.toString().equals(new_password_2.text.toString())) {
                            Toast.makeText(this, "비밀번호가 일치한지 확인해주세요", Toast.LENGTH_LONG).show()
                        } else {
                            var id = find_IdCheck.text.toString()
                            var birth = find_birthCheck2.text.toString()
                            var name = find_nameCheck.text.toString()
                            var newpassword = new_password.text.toString()
                            MainActivity.service.findpassword(id, birth, name, newpassword)
                                .enqueue(object : Callback<Bigdata> {
                                    override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                                    }

                                    override fun onResponse(
                                        call: Call<Bigdata>,
                                        response: Response<Bigdata>
                                    ) {
                                        if (response.body()?.result != null) {
                                            Toast.makeText(
                                                this@FindUserInform,
                                                "비밀번호가 바뀌었습니다.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            onBackPressed()
                                        } else {
                                            Toast.makeText(
                                                this@FindUserInform,
                                                "입력정보를 다시 확인해주세요.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }

                                })

                        }
                    }
                    cancel_button.setOnClickListener {
                        Alert.dismiss()
                    }

                } else {
                    Alert.setMessage("입력정보를 확인해주세요.")
                }
                Alert.show()
            }
            find_cancelButton.setOnClickListener {
                timer.cancel()
                onBackPressed()
            }

        }
    }
}