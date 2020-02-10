package com.example.takegoal

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ingroup.*

class InGroup : AppCompatActivity() {

    var memberList = arrayListOf<Member>(
        // 그룹에 가입을 할 경우 이 리스트로 들어오도록 해야 함
        Member("Yoo", "I can do", ""),
        Member("Hwang", "Pass", "")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingroup)

        val rvAdapter = InGroupRvAdapter(this, memberList) {member ->
            // 람다식이 맨 마지막 인자이기 때문에 소괄호 밖에서 꺼내어 중괄호로 따로 만듦
            val personalActivity_intent = Intent(this, PersonalView::class.java)
            //personalActivity_intent.putExtra() //여기에서 member안의 정보를 전달해주면 될듯함
            startActivity(personalActivity_intent)
        }
        mRecyclerView.adapter = rvAdapter

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)

        // 벌점을 확인하고 달마다 벌점 가장 높은 사람 선정하기
        //val plus_demerit = Intent

        val lazyking_name = ""
        lazy_king.setText("이 달의 게으름 왕 : ")



    }
}