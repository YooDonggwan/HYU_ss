package com.example.takegoal


import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.personal_goal.*


class PersonalView:AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_goal)
        var id=getIntent().getIntExtra("member",-1)
        whoGoal.setText("${InGroup.memberList[id].name}님의 목표")
        addButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("당신의 목표를 입력하세요")
            val myview = layoutInflater.inflate(R.layout.text_sample, null)
            alert.setView(myview)
            alert.setIcon(R.mipmap.ic_launcher)


            alert.setPositiveButton("날짜 지정 및 저장") { dialogInterface: DialogInterface, i: Int ->
                var dateCheck=DatePickerDialog(this)
                dateCheck.setOnDateSetListener(object:DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        var goalid: EditText = myview.findViewById(R.id.yourGoal)
                        var Choose_date=" ~${year}-${String.format("%02d",month+1)}-${String.format("%02d",dayOfMonth)}"
                        InGroup.memberList[id].Goal_List.add(GoalModel(goalid.text.toString(),when_takegoal =Choose_date))
                        Toast.makeText(this@PersonalView, "저장되었습니다", Toast.LENGTH_SHORT).show()

                    }
                })
                dateCheck.show()
            }
            alert.setNegativeButton("취소") { dialog, id ->
                dialog.dismiss()
            }

            alert.show()
        }

    }

    override fun onResume() {
        super.onResume()
        var id=getIntent().getIntExtra("member",-1)
        var adapter =GoalAdapter(this, InGroup.memberList[id].Goal_List,id)
        IntegerList.adapter = adapter
        IntegerList.layoutManager = LinearLayoutManager(this)

    }

}