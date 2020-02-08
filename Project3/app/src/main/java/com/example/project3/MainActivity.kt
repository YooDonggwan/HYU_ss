package com.example.project3


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat


import kotlinx.android.synthetic.main.activity_sub.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)


        readyButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                val nextIntent= Intent(this@MainActivity,PersonalView::class.java)
                   startActivity(nextIntent)

            }

        })

    }}


