package com.example.takegoal


data class Member(val name : String, val Id:String?=null, val photo : String?=null,var point:Int=0,
             val Goal_List: ArrayList<GoalModel>,val password:String?=null,
             val phonNum:String?=null,val email:String?=null

             )


