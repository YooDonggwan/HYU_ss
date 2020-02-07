package com.example.project3


import android.content.DialogInterface
import android.content.Intent

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*



class PersonalView:AppCompatActivity(){
companion object{
    var goalList = mutableListOf<GoalModel>(GoalModel("안녕하세요"))
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var adapter = GoalAdapter(this, goalList)
        IntegerList.adapter = adapter
        IntegerList.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)

            alert.setTitle("당신의 목표를 입력하세요")
            val myview = layoutInflater.inflate(R.layout.text_sample, null)
            alert.setView(myview)

            alert.setIcon(R.mipmap.ic_launcher)
            alert.setPositiveButton("저장") { dialogInterface: DialogInterface, i: Int ->
                var goalid: EditText = myview.findViewById(R.id.yourGoal)
                goalList.add(GoalModel(goalid.text.toString()))
                adapter.notifyDataSetChanged()
                Toast.makeText(this@PersonalView, "저장되었습니다", Toast.LENGTH_SHORT).show()
            }
            alert.setNegativeButton("취소") { dialog, id ->
                dialog.dismiss()
            }

            alert.show()
        }

    }}





