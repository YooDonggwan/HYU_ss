const passport=require('passport')
const Basic=require('passport-http').BasicStrategy
const User=require('../models/user')
const crypto = require('crypto')
passport.use(new Basic(
    function(id,password,callback){
            const hash = crypto.createHash('sha256')
            hash.update(password)
             let hash_password = hash.digest('hex')
        User.findOne({ userName: id, password: hash_password},(err,doc)=>{
            if(doc){
           callback(null,doc)}
      })
    }
))  
exports.isBaiscAuthenticated=passport.authenticate('basic',{session:false})