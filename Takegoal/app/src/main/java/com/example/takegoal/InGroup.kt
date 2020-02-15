package com.example.takegoal

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ingroup.*
class InGroup : AppCompatActivity() {
    companion object {
    var memberList = arrayListOf<Member>(
        // 그룹에 가입을 할 경우 이 리스트로 들어오도록 해야 함
        Member("Yoo", "I can do", "", Goal_List = ArrayList<GoalModel>(),id=0),
        Member("Hwang", "Pass", "", Goal_List = ArrayList<GoalModel>(),id=1)
    )
        var groupList=arrayListOf<GoalModel>()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingroup)
        var adapter=GroupGoalAdapter(groupList,this)
        groupView.adapter=adapter
        groupView.layoutManager=LinearLayoutManager(this)
    groupButton.setOnClickListener {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("그룹 목표를 입력하세요")
        val myview = layoutInflater.inflate(R.layout.text_sample, null)
        alert.setView(myview)
        alert.setIcon(R.mipmap.ic_launcher)
        alert.setPositiveButton("날짜 지정 및 저장") { dialogInterface: DialogInterface, i: Int ->
            var dateCheck= DatePickerDialog(this)
            dateCheck.setOnDateSetListener(object:DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    var goalid: EditText = myview.findViewById(R.id.yourGoal)
                    var Choose_date=" ~${year}-${String.format("%02d",month+1)}-${String.format("%02d",dayOfMonth)}"
                   memberList.forEach {
                       it.Goal_List.add(GoalModel(goalid.text.toString(),when_takegoal =Choose_date))
                   }
                    groupList.add(GoalModel(goalid.text.toString(),when_takegoal =Choose_date))
                    Toast.makeText(this@InGroup, "저장되었습니다", Toast.LENGTH_SHORT).show()

                }
            })
            dateCheck.show()
        }
        alert.setNegativeButton("취소") { dialog, id ->
            dialog.dismiss()
        }

        alert.show()
    }
//        memberList.forEach{
//                var point=0
//            PersonalView.user_hash.get(it.name)?.forEach {
//                var until_date= it.when_takegoal?.substring(2, it.when_takegoal?.length!!)
//                var date=LocalDate.parse(until_date, DateTimeFormatter.ISO_DATE)
//                if(date.isAfter(LocalDate.now())) {
//                    point++
//                }
//            }
//            it.point=point
//        }->이부분은 포인트 업데이트화 해본것
        val rvAdapter = InGroupRvAdapter(this, memberList) {member ->
            // 람다식이 맨 마지막 인자이기 때문에 소괄호 밖에서 꺼내어 중괄호로 따로 만듦
            val personalActivity_intent = Intent(this, PersonalView::class.java)
            personalActivity_intent.putExtra("member",member.id)
            //personalActivity_intent.putExtra() //여기에서 member안의 정보를 전달해주면 될듯함
            startActivity(personalActivity_intent)
        }
        mRecyclerView.adapter = rvAdapter

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)

        // 벌점을 확인하고 달마다 벌점 가장 높은 사람 선정하기
        //val plus_demerit = Intent




    }

    override fun onResume() {
        super.onResume()

        var lazyking_name = "${memberList[0].name}"
//        var member_name=memberList[0]
//        //여기서 벌점 가장높은사람계산
//        memberList.forEach{
//            if(member_name.point<it.point){
//                lazyking_name=it.name
//            }
//        }
        lazy_king.setText("이 달의 게으름 왕 : ${lazyking_name}")
        //이것은 데이터베이스에서 가져와서비교
    }
}