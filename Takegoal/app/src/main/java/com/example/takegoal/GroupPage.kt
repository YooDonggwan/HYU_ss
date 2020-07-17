package com.example.takegoal

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.make_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GroupPage : AppCompatActivity() {
    var userId: String? = null
    var groupArray = ArrayList<GroupInform>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.make_group)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //로그인화면으로부터 정보를 받음
        var userGroupList = intent.getStringArrayListExtra("groupList")
        userId = intent.getStringExtra("userId")
        //회원의 그룹정보를 가져옴
        MainActivity.service.find_show_group(userGroupList)
            .enqueue(object : Callback<GroupInformArray> {
                override fun onFailure(call: Call<GroupInformArray>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<GroupInformArray>,
                    response: Response<GroupInformArray>
                ) {
                    groupArray = response.body()?.result ?: ArrayList<GroupInform>()
                    adapterGroup(groupArray, 1)
                }
            })

        //메세지 있는지 확인
        MainActivity.service.getMessage(userId).enqueue(object : Callback<message> {
            override fun onFailure(call: Call<message>, t: Throwable) {
            }

            override fun onResponse(call: Call<message>, response: Response<message>) {
                if (response.body() != null) {
                    var messageArray = response.body()?.result ?: ArrayList()
                    //어뎁터 적용
                    messageRecyclerView.adapter =
                        MessageAdapter(messageArray) { m: MessageAdapter, i: Int ->
                            messageArray.removeAt(i)
                            m.notifyDataSetChanged()
                        }
                    messageRecyclerView.layoutManager = LinearLayoutManager(this@GroupPage)
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {//그룹찾기
                MainActivity.service.group_read(searchGroup.text.toString())
                    .enqueue(object : Callback<GroupInform> {
                        override fun onFailure(call: Call<GroupInform>, t: Throwable) {
                        }
                        override fun onResponse(
                            call: Call<GroupInform>,
                            response: Response<GroupInform>
                        ) {
                            if (response.body()?.groupname != null) {//그룹이 있는지 체크
                                groupCheck(true, response.body() as GroupInform, userId)
                            } else {
                                groupCheck(false, response.body() as GroupInform, null)
                            }
                        }
                    })
                return true
            }
            R.id.addbutton -> {
                //그룹생성란 보이게하기
                val makeGroupIntent = Intent(this, showSearchGroup::class.java)
                makeGroupIntent.putExtra("userId", userId)
                makeGroupIntent.putExtra("Check", 0)
                startActivityForResult(makeGroupIntent, 0)
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 5 && requestCode == 0) {
            var sample = data?.getSerializableExtra("group_sample") as GroupInform
            // var sample2=group_array
            groupArray.add(sample)
            adapterGroup(groupArray, 22)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }

    private fun groupCheck(check: Boolean, group: GroupInform, id: String?) {
        if (check) {
            var searchIntent = Intent(this, showSearchGroup::class.java)
            searchIntent.putExtra("group", group)
            searchIntent.putExtra("my_id", id)
            startActivity(searchIntent)
        } else {
            var noGroupAlert = AlertDialog.Builder(this)
            noGroupAlert.setMessage("해당하는 그룹이 없습니다")
            noGroupAlert.setPositiveButton("나가기") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            noGroupAlert.show()
        }
    }

    var adapter: ShowGroupAdapter? = null
    private fun adapterGroup(array: ArrayList<GroupInform>, check: Int) {
        if (check == 1) {
            val adapterSample = ShowGroupAdapter(array) {
                val inGroupIntent = Intent(this, InGroup::class.java)
                inGroupIntent.putExtra("member", it)
                inGroupIntent.putExtra("id", userId)
                startActivity(inGroupIntent)
            }
            adapter = adapterSample
            groupRecycleView.adapter = adapter
            groupRecycleView.layoutManager = LinearLayoutManager(this)
        } else {
            //새로 그룹을 만들었을 때 바로 적용이 가능하게
            adapter?.notifyItemChanged(array.size - 1, array[array.size - 1])
        }
    }
}
