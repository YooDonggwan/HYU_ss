package com.example.takegoal



import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.goal_sample.view.*


class GoalAdapter(var activity: Activity,var list:ArrayList<GoalModel>,val id:Int) :RecyclerView.Adapter<GoalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.goal_sample,parent,false)
        return GoalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
      }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.containerView.goalButton.setOnClickListener {
            val toIntent= Intent(activity,PhotoModel::class.java)//nextIntent->toIntent
           toIntent.putExtra("number",position)
            toIntent.putExtra("id",id)
            startActivity(activity,toIntent,null)

        }
        if(list[position].Picture!=null){
            holder.containerView.goalCheck.setChecked(true)
        }
        holder.containerView.textView.text = list[position].goaltext
        holder.containerView.when_Take.text=list[position].when_takegoal



    }


    }

