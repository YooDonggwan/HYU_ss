package com.example.takegoal

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.goal_sample.view.*

class GroupGoalAdapter(var list:ArrayList<GoalModel>,var activity: Activity) :RecyclerView.Adapter<GroupGoalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupGoalViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.goal_sample2,parent,false)
        return GroupGoalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }



    override fun onBindViewHolder(holder: GroupGoalViewHolder, position: Int) {
            holder.containerView.textView.text=list[position].goaltext
            holder.containerView.when_Take.text=list[position].when_takegoal
        if(list[position].Picture!=null){
            holder.containerView.goalCheck.setChecked(true)
        }
    }

}