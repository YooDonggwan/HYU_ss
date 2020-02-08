package com.example.project3

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.upload_sample.*

import java.io.IOException

import java.util.*
import android.graphics.Bitmap as Bitmap

class PhotoModel:AppCompatActivity(){
    var flag:Int?=null
//    var imageFilePath:String?=null
   val permissionListener=object: PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(getApplicationContext(),"카메라 권한이 허용됨",Toast.LENGTH_SHORT).show()
        }
        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT).show()
        }

    }
    //  private fun createImageFile(): File? {
//        var timeStamp:String=SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        var imageFileName="TakeGoal"+"_"+timeStamp
//        var storageDir:File?=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        var image:File=File.createTempFile(
//            imageFileName,
//            ".jpg",
//            storageDir
//        )
//        imageFilePath=image.absolutePath
//        return image
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        var num: Intent = getIntent()
        var number = num.getIntExtra("number", -1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_sample)
        //권한요청
        TedPermission.with(applicationContext).setPermissionListener(permissionListener)
            .setRationaleMessage("권한이 허용되었습니다.")
            .setDeniedMessage("거부하셨습니다. 카메라 기능을 이용하지 못합니다")
            .setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                , Manifest.permission.READ_EXTERNAL_STORAGE
            )

        galleryButton.setOnClickListener {
            flag=0
            val nextIntent = Intent(Intent.ACTION_GET_CONTENT)
            nextIntent.setType("image/*")
            startActivityForResult(nextIntent, number)
        }
        cameraButton.setOnClickListener {
            flag=1
        //    var photoUri: Uri? = null
            val camerIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camerIntent, number)
            //fileprovider 권한요청실패
//            if (camerIntent.resolveActivity(packageManager) != null) {
//                        var photofile: File? = null
//                        try {
//                            photofile = createImageFile()
//                        } catch (e: IOException) {
//
//                        }
//                        if (photofile != null) {
//                            photoUri = FileProvider.getUriForFile(
//                                this,
//                                packageName,
//                                photofile
//                            )
//                    camerIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//
//                }
//            }
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
        var confirmalert=AlertDialog.Builder(this)
       confirmalert.setIcon(R.mipmap.ic_launcher)
        confirmalert.setMessage("등록하시겠습니까?")
        confirmalert.setNegativeButton("아니오"){dialogInterface: DialogInterface, i: Int ->
            PersonalView.goalList[number].Picture=null
        }
        confirmalert.setPositiveButton("네"){ dialogInterface: DialogInterface, i: Int ->
        if(resultCode== Activity.RESULT_OK&&requestCode!=-1){
            if(flag==1){
                var extra:Bundle?= data?.extras
                var bitmap2 =extra?.get("data") as Bitmap
                PersonalView.goalList[number].Picture=bitmap2
            }
            else if(flag==0){
            var currentImage: Uri?=data?.data
            try{
                var bitmap= MediaStore.Images.Media.getBitmap(contentResolver,currentImage)
                PersonalView.goalList[number].Picture=bitmap
            }
           catch(e:IOException){
              e.printStackTrace()
          }}
       }
            goalPicture.setImageBitmap(PersonalView.goalList[number].Picture)
        }
    confirmalert.show()
    }
}