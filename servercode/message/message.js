var message=require('../models/message_group')
exports.postMessage=function(req,res){
    var addresser=req.body.addresser
    var receiver=req.body.receiver
    var groupname=req.body.groupname
    var text=req.body.Text
    new message({ addresser: addresser, receiver: receiver, groupname:groupname,text:text}).save((err,doc)=>{
        if(doc)console.log(doc)
        if(err)throw err
    })
}
exports.getMessage=function(req,res){
    var id=req.query.idaddresser
    message.find({receiver:id},(err,doc)=>{
        if(doc){
            res.send({"result":doc})
    }
        else{res.send({"result":null})}
        if(err)throw err
    })
}
exports.putMessage=function(req,res){
    let addresser_id = req.query.addresser_id
    let receiver_id = req.query.receiver_id
    let groupname = req.query.groupname
    message.deleteOne({ addresser: addresser_id, receiver: receiver_id },(err,doc)=>{
        if(doc){res.send({"result":"삭제"})}
        if(err)throw err
    })
    
}
