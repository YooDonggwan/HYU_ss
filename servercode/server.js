const express=require('express')
const app=express()
const portNum=process.env.PORT||3000
const route=require('./router/router')
const bodyParser=require('body-parser')
const mongoose=require('mongoose')
mongoose.Promise=global.Promise
mongoose.connect('mongodb://localhost/kibum')
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended:true}))
//true의 의미는 Any Type
//false는 String이나 Object를 받음
//app.use(route)
app.get('/home',(req,res)=>{
    res.sendFile(__dirname+'/images/image.jpg')
})
app.use(route)

app.listen(portNum,(err)=>{
    if(err)console.log(err)
    else {console.log("서버가 열렸습니다")}
})

