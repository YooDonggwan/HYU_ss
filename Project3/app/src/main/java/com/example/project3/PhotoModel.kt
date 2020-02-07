package com.example.project3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.upload_sample.*

class PhotoModel:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_sample)
        var num:Intent=getIntent()
        var number=num.getIntExtra("number",-1)
        galleryButton.setOnClickListener{
            val nextIntent= Intent(Intent.ACTION_GET_CONTENT)
            nextIntent.setType("image/*")
           startActivityForResult(nextIntent,number)

        }
    }

    override fun onResume() {
        super.onResume()
        var num:Intent=getIntent()
        var number=num.getIntExtra("number",-1)
        goalPicture.setImageBitmap(PersonalView.goalList[number].Picture)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var num:Intent=getIntent()
        var number=num.getIntExtra("number",-1)
        if(resultCode== Activity.RESULT_OK&&requestCode!=-1){
            var currentImage: Uri?=data?.data
            try{
                val bitmap= MediaStore.Images.Media.getBitmap(contentResolver,currentImage)
                PersonalView.goalList[number].Picture=bitmap
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}