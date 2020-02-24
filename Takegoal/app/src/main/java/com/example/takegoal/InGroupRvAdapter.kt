package com.example.takegoal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class InGroupRvAdapter(val context : Context, val memberList : ArrayList<Member>, val itemClick: (Member) -> Unit) : RecyclerView.Adapter<InGroupRvAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.ingroup_rv_item, parent, false)

        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(memberList[position], context)
    }

    inner class Holder(itemView: View, itemClick: (Member) -> Unit) : RecyclerView.ViewHolder(itemView) { // itemClick이라는 변수에 람다식 담았으므로 인자 타입 생략불가하므로 Member 타
        val member_demerit=itemView?.findViewById<TextView>(R.id.Points)
        val member_photo = itemView?.findViewById<ImageView>(R.id.photo)
        val member_name = itemView?.findViewById<TextView>(R.id.name)
        val member_slogan = itemView?.findViewById<TextView>(R.id.slogan)
        fun bind (member: Member, context: Context) {

            // 프로필 사진의 setImageResource에 들어갈 이미지 id를 파일명으로 찾고, 이미지 없는 경우 default_photo로 설정
            if(member.photo != ""){
                val resourceId = context.resources.getIdentifier(member.photo, "drawable", context.packageName)
                member_photo?.setImageResource(resourceId)
            }
            else{
                member_photo?.setImageResource(R.drawable.defalut_photo)
            }

            // 이름과 슬로건 연결
            member_name?.text = member.name
            //member_slogan?.text = member.slogan
            member_demerit?.text=member.point.toString()
            itemView.setOnClickListener {
                itemClick(member)
                // 이렇게 itemClick 설정만 해주고 클릭 시 일어나는 이벤트는 main class에서 어댑터와 연결할 때 설정해준다.
            }

        }

    }
}