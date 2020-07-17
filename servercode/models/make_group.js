const mongoose=require('mongoose')
var newgroup=new mongoose.Schema({
    groupname:String,
    groupPicture:String,
    groupuser: [],
    groupmanager:String 
   
})
module.exports=mongoose.model('newgroup',newgroup)