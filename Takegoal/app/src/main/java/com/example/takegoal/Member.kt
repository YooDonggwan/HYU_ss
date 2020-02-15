package com.example.takegoal

import java.io.Serializable

class Member(val name : String, val slogan : String, val photo : String,var point:Int=0,
             val Goal_List: ArrayList<GoalModel>, val id:Int
             ) {


}