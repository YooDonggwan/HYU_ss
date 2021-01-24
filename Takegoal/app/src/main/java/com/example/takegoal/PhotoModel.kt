package com.example.takegoal

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.upload_sample.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import android.graphics.Bitmap as Bitmap

class PhotoModel:AppCompatActivity(){
    var flag:Int?=null

    //여기도 이름정보를 가져와서 데이터베이스에서 가져오기
   val permissionListener=object: PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(getApplicationContext(),"카메라 권한이 허용됨",Toast.LENGTH_SHORT).show()
        }
        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT).show()
        }
}
    override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_sample)
        var number = getIntent().getIntExtra("number", -1)
        var id=getIntent().getIntExtra("id",-1)
        //권한요청
        TedPermission.with(getApplicationContext()).setPermissionListener(permissionListener)
            .setRationaleMessage("권한이 허용되었습니다.")
            .setDeniedMessage("거부하셨습니다. 카메라 기능을 이용하지 못합니다")
            .setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                , Manifest.permission.READ_EXTERNAL_STORAGE
            ).check()

        galleryButton.setOnClickListener {
            flag=0
            val nextIntent = Intent(Intent.ACTION_GET_CONTENT)
            nextIntent.setType("image/*")
            startActivityForResult(nextIntent, number)
        }
        cameraButton.setOnClickListener {
            flag=1
            val camerIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camerIntent, number)
        }

    }

    override fun onResume() {
        super.onResume()
        var number = getIntent().getIntExtra("number", -1)
        var id=getIntent().getIntExtra("id",-1)
        goalPicture.setImageBitmap(InGroup.memberList[id].Goal_List.get(number).Picture )
        timeView.text=  InGroup.memberList[id].Goal_List.get(number).timeCheck
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var number = getIntent().getIntExtra("number", -1)
        var id=getIntent().getIntExtra("id",-1)
        var picture= InGroup.memberList[id].Goal_List.get(number).Picture
        if(resultCode== Activity.RESULT_OK||requestCode!=-1) {
            var confirmalert = AlertDialog.Builder(this)
            confirmalert.setIcon(R.mipmap.ic_launcher)
            confirmalert.setMessage("등록하시겠습니까?\n(단! 등록 시간은 현재로됩니다)")
            confirmalert.setNegativeButton("아니오") { dialogInterface: DialogInterface, i: Int ->
                picture = null
            }
            confirmalert.setPositiveButton("네") { dialogInterface: DialogInterface, i: Int ->
                if (resultCode == Activity.RESULT_OK && requestCode != -1) {
                    if (flag == 1) {
                        var extra: Bundle? = data?.extras
                        var bitmap2 = extra?.get("data") as Bitmap
                        picture = bitmap2
                    } else if (flag == 0) {
                        var currentImage: Uri? = data?.data
                        try {
                            var bitmap =
                                MediaStore.Images.Media.getBitmap(contentResolver, currentImage)
                            picture = bitmap
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
                InGroup.memberList[id].Goal_List.get(number).Picture=picture
                goalPicture.setImageBitmap(picture)
                val formatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시 mm분 등록")
                InGroup.memberList[id].Goal_List.get(number).timeCheck = LocalDateTime.now().format(formatter)
                timeView.setText(InGroup.memberList[id].Goal_List.get(number).timeCheck)
            }
            confirmalert.show()
        }}
}