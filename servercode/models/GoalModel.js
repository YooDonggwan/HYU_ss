const mongoose=require('mongoose')
var goalmodel=new mongoose.Schema({
    Id:String,
    group:String,
    goaltext:String,
    Achieve:Boolean,
    Picture:String,
    timeCheck:String,
    when_takegoal:String,
    isGroupGoal:Boolean
})
module.exports=mongoose.model('goalmodel',goalmodel)