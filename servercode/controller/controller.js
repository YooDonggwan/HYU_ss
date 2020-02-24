var User=require('../models/user')
var newUser=require('../models/make_user')
const crypto=require('crypto')
const jwt=require('jsonwebtoken')
let jwtkey="5212"
exports.check=function(req,res){
    let id=req.query.id
    let password=req.query.password
    newUser.findOne({Id:id, password:password},(err,doc)=>{
        if(doc){
            res.send({"result":"맞음"})
        }
        if(err)throw err
    })
}
exports.create=function(req,res){
    
    let goal_list=req.body.Goal_List
    let Id = req.body.Id
    let email = req.body.email
    let name = req.body.name
    let password = req.body.password
    let point = req.body.point
    new newUser({Goal_List:goal_list,Id:Id,email:email,name:name,password:password,point:point}).save((err,doc)=>{
        if(doc)console.log(doc)
        if(err)console.log(err)
    })
    // let userName=req.body.username
    // let password=req.body.password
    // const hash=crypto.createHash('sha256')
    // hash.update(password)
    // let hashpassword=hash.digest('hex')
    // let key="rlqja"
    // const ciper=crypto.createCipher('aes192',key)
    // let ciper_password=ciper.update(password,'utf8','hex')
    // ciper_password+=ciper.final('hex')
    // new User({ userName: userName, password: hashpassword}).save((err,doc)=>{
    
    //     if(doc){//데이터가 들어갔는지 체크
    //         console.log(doc)
            // const decpier = crypto.createDecipher('aes192',key)
            // let deciped = decpier.update(doc.password,'hex','utf8')
            // deciped+=decpier.final('utf8')
            // console.log(deciped)
    //     }
    //     if(err){
    //         console.log(err)
    //     }
    // })
    res.send({"result":"성공했습니다."})
}
exports.read = function (req,res) {
    var id=String(req.query.id)
    
     newUser.findOne({Id:id},(err,doc)=>{
        if(doc){
            res.send({"result":id})
        }
        if(err){
           throw err
        }
    })
    // console.log(req.user)
    
    
}
exports.update = function (req,res) {
    res.send("계정이 업데이트되었습니다.")

}
exports.delete = function (req,res) {
    res.send("계정이 제거되었습니다.")
}