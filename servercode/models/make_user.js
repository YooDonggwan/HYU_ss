const mongoose=require('mongoose')
var new_user=new mongoose.Schema({
    Goal_List:[],
    Id:String,
    email:String,
    name:String,
    password:String,
    point:Number
})
module.exports=mongoose.model('newuser',new_user)