package com.example.takegoal

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.enroll_sample.*
import kotlinx.android.synthetic.main.make_group_form.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.System.out
import java.util.regex.Pattern


class showSearchGroup : Activity() {
    companion object {
        //절대 경로 얻는 함수
        fun getRealPathFromURI(uri: Uri, context: Context): String {
            var proj = arrayOf(MediaStore.MediaColumns.DATA)
            var cursor = context.contentResolver.query(uri, proj, null, null, null)
            if(cursor == null) {
                return uri.path!!
            } else {
                cursor?.moveToNext()
                var columIndex = cursor?.getColumnIndexOrThrow(proj[0])
                return cursor?.getString(columIndex!!)!!
            }
        }
    }

    var photo: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var screenCheck = intent.getIntExtra("Check", -1)
        if (screenCheck == 0) {//그룹 생성화면
            var checkName: String? = null//그룹이름 바뀌는 것 방지
            setContentView(R.layout.make_group_form)
            var canMake = false//이름
            val permissionListener = object : PermissionListener {
                override fun onPermissionGranted() {
                    Toast.makeText(this@showSearchGroup, "카메라 권한이 허용됨", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                    Toast.makeText(this@showSearchGroup, "카메라 권한이 거부됨", Toast.LENGTH_SHORT).show()
                }
            }
            TedPermission.with(this@showSearchGroup)
                .setPermissionListener(permissionListener)//카메라허용
                .setRationaleMessage("카메라 권한이 허용되었습니다.")
                .setDeniedMessage("거부하셨습니다. 카메라 기능을 이용하지 못합니다")
                .setPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                ).check()
            checkGroup.setOnClickListener {
                // 중복검사
                MainActivity.service.group_check(makeyourGroupname.text.toString())
                    .enqueue(object : Callback<Bigdata> {
                        override fun onFailure(call: Call<Bigdata>, t: Throwable) {}
                        override fun onResponse(call: Call<Bigdata>, response: Response<Bigdata>) =
                            if (response.body()?.result != null) {//이미존재
                                showAlert(false)
                            } else {
                                //만들 수 있는 경우
                                canMake = true
                                checkName = makeyourGroupname.text.toString()
                                showAlert(true)
                            }
                    })
            }
            cameraButton2.setOnClickListener {
                var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 10)
            }
            galleryButton2.setOnClickListener {
                var galleryIntent = Intent(Intent.ACTION_PICK)
                galleryIntent.type = "image/*"
                startActivityForResult(galleryIntent, 5)
            }
            cancelButton2.setOnClickListener {
                onBackPressed()
            }
            makeButton2.setOnClickListener {
                //생성버튼 누를시에
                val makeOrCheck = AlertDialog.Builder(this)
                if(Pattern.matches("^[a-zA-Z가-힣0-9]{0,1}$",makeyourGroupname.text.toString())){
                    makeOrCheck.setMessage("그룹이름을 한 글자 이상으로 해주세요.")
                    makeOrCheck.show()
                }
                else{
                val alertShow = makeOrCheck.create()
                alertShow.setMessage("그룹 생성중입니다. 잠시만 기다려주세요")
                alertShow.show()
                if (canMake && makeyourGroupname.text.toString() == checkName) {
                    val userId = intent.getStringExtra("userId")
                    var array = ArrayList<String>().apply {
                        this.add(userId)
                    }
                    var body: MultipartBody.Part? = null
                    if (photo != null) {
                        val requestbody = RequestBody.create(MediaType.parse("image/*"), photo!!)
                        body = MultipartBody.Part.createFormData(
                            "upload",
                            photo!!.name,
                            requestbody
                        )
                    }
                    MainActivity.service.group_make(checkName, body, userId, array)
                        .enqueue(object : Callback<Bigdata> {
                            override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                            }

                            override fun onResponse(
                                call: Call<Bigdata>, response: Response<Bigdata>
                            ) {
                                alertShow.dismiss()
                                makeOrCheck.setMessage("그룹이 생성되었습니다.")
                                makeOrCheck.setPositiveButton(
                                    "확인"
                                ) { dialog, which ->
                                    var groupSample = GroupInform(
                                        makeyourGroupname.text.toString(),
                                        userId,
                                        response.body()?.result,
                                        array
                                    )
                                    val backToGroupPage =
                                        Intent(this@showSearchGroup, GroupPage::class.java)
                                    backToGroupPage.putExtra("group_sample", groupSample)
                                    setResult(5, backToGroupPage)
                                    finish()
                                }
                                makeOrCheck.show()
                            }
                        })
                } else {
                    makeOrCheck.setMessage("아이디 중복확인 및 확인해주세요")
                    makeOrCheck.setPositiveButton("확인") { d: DialogInterface, i: Int ->
                        alertShow.dismiss()
                    }
                    makeOrCheck.show()
                }}
            }
        } else {//가입화면
            setContentView(R.layout.enroll_sample)

            var groupData = intent.getSerializableExtra("group") as GroupInform
            val groupPicture=groupData.groupPicture
            val groupManager=groupData.groupmanager
            val groupName=groupData.groupname
            showmanager.text = groupManager
            showname.text = groupName
            Picasso.get().load(groupPicture).placeholder(R.drawable.timer)
                .error(R.drawable.defalut_photo).into(showPicture)
            cancelButton.setOnClickListener {
                onBackPressed()
            }
            pushButton.setOnClickListener {
                val messageAlert = AlertDialog.Builder(this)
                messageAlert.setPositiveButton("확인") { d: DialogInterface, I: Int ->
                    d.dismiss()
                }
                var myId = intent.getStringExtra("my_id")
                if (!groupData.groupuser.contains(myId)) {
                    var text = "${myId}님이 ${groupManager}에 가입신청했습니다."

                    MainActivity.service.sendMessage(myId!!, groupManager, groupName, text)
                        .enqueue(object : Callback<Bigdata> {
                            override fun onFailure(call: Call<Bigdata>, t: Throwable) {
                            }
                            override fun onResponse(
                                call: Call<Bigdata>,
                                response: Response<Bigdata>
                            ) {
                            }
                        })
                    messageAlert.setMessage("가입메세지가 전송되었습니다.")
                    messageAlert.show()
                } else {
                    messageAlert.setMessage("이미 가입되어있는 그룹입니다.")
                    messageAlert.show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 10) {
            //카메라-->는 나중에
            var data_file = data?.data!!
            var data_image = data?.extras?.get("data") as Bitmap
            data_image.compress(Bitmap.CompressFormat.JPEG, 50, out)
            out.close()
            //photo=data_image

        } else if (requestCode == 5 && resultCode == RESULT_OK) {//갤러리
            var photoUri = data?.data!!
            var filePath = File(getRealPathFromURI(photoUri, this))
            photoCheck.text=filePath.name
            photo = filePath
        }
    }

    fun showAlert(check: Boolean) {
        var possibleAlert = AlertDialog.Builder(this)
        possibleAlert.setPositiveButton("확인") { d: DialogInterface, i: Int ->
            d.dismiss()
        }
        if (check) {
            possibleAlert.setMessage("사용가능한 그룹이름입니다.")
        } else {
            possibleAlert.setMessage("사용할 수 없는 그룹이름입니다.")
        }
        possibleAlert.show()
    }
}
