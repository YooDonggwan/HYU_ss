var mongoose=require('mongoose')
var schema=new mongoose.Schema({
    addresser:String,
    receiver:String,
    groupname:String,
    text:String
})
module.exports=mongoose.model('message',schema)