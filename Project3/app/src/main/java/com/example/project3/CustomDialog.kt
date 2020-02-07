package com.example.project3


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent



import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.upload_sample.view.*


class CustomDialog(var activity: Activity,context: Context):Dialog(context),View.OnClickListener{
    override fun onClick(v: View?) {

        val alert2=AlertDialog.Builder(context)
        val view2=layoutInflater.inflate(R.layout.upload_sample,null)

        view2.galleryButton.setOnClickListener{
            var OPEN_IMAGE=0
            val nextIntent= Intent(activity,PhotoModel::class.java)

            startActivity(activity,nextIntent,null)

        }
        alert2.setView(view2)
        alert2.show()

    }

}
