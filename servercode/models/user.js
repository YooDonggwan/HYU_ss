var mongose=require('mongoose')
var UserSchema=new mongose.Schema({
    userName:String,
    password:String
})
//user라는  collection이 저장됨
module.exports=mongose.model('user',UserSchema)