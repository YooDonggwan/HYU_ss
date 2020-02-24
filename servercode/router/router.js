const express=require('express')
const route=express.Router()
const controller=require('../controller/controller')
const auth=require('../auth/auth')
const multer=require('multer')
var storage=multer.diskStorage({
    destination:function(req,file,cb){
        cb(null,'upload')
    },
    filename:function(req,file,cb){
        cb(null,file.originalname)
    }
})
function filefilter(req,file,cb){
    if(file.mimetype='image/jpg'){
        cb(null,true)
    }
    else{cb(null,false)}
}
var upload=multer({storage:storage,fileFilter:filefilter})
route.route('/image').get( (req, res) => {
    res.sendFile(path.join(__dirname,'..','images','image.jpg'))
})
.post(upload.single('avatar'),(req,res,next)=>{
    res.send(req.file)
})
route.route('/check').get(controller.check)
route.route('/user').post(controller.create)
// .get(auth.isBaiscAuthenticated,controller.read)
    .get(controller.read)
    .put(auth.isBaiscAuthenticated,controller.update)
    .delete(auth.isBaiscAuthenticated,controller.delete)
//?의방식으로 데이터를 보낼때는 /test로 해서 query로 받음
// /:id 방식으로 param을 받아올수도 있음

module.exports=route