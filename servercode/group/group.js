const makegroup = require('../models/make_group')
const user = require('../models/make_user')
const fs = require('fs-extra')
const formidable = require('formidable')
exports.namecheck = function (req, res) {
    var name = req.query.groupname
    makegroup.findOne({ groupname: name }, (err, doc) => {
        if (doc) {
            res.send({ "result": doc.groupname })
        }
        else {
            res.send({ "result": null })
        }
    })
}
exports.getSearch = function (req, res) {
    var groupname = req.query.groupname
   
    makegroup.findOne({ groupname: groupname }, (err, doc) => {
        console.log(doc)
        if (doc) {
            
            res.send({
                "groupname": doc.groupname, "groupPicture": doc.groupPicture, "groupmanager": doc.groupmanager,
                "groupuser": doc.groupuser
            })
        }
        else{
            res.send({"groupname":null})
        }
        
     
        if (err) throw err
    })

}
exports.postMake = function (req, res) {
    var groupPicture;
    var form = new formidable.IncomingForm()
    form.uploadDir = 'images/'
    form.keepExtensions = true
    form.on('file', (field, file) => {
        if (file != null) {
            fs.rename(file.path, form.uploadDir + '/' + file.name);
            groupPicture = "http://192.168.0.102:3000" + "/" + file.name;
           
        }
        else {
            groupPicture = null
        }
    })
        .parse(req, function (err, fields, files) {
            new makegroup({
                groupname: req.query.groupname, groupPicture: groupPicture, groupuser: req.query.groupuser,
                groupmanager: req.query.groupmanager
            }).save((err, doc) => {
                if (doc) {
                    user.updateOne({ Id: req.query.groupmanager }, { '$push': { group_list: req.query.groupname } }, (err, doc) => {
                        if (doc) {
                            res.send({ "result": groupPicture })
                        }
                        if (err) throw err
                    })
                }
                if (err) console.log(err)
            })
        })
    
    
}

exports.showgroup = function (req, res) {
    var grouplist = req.query.groupname

    makegroup.find({ groupname: grouplist }, { _id: false }, (err, doc) => {
        if (err) throw err
        else {

            res.send({ "result": doc })
        }
    })

}