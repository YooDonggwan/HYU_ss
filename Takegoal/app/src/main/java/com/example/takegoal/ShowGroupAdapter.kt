package com.example.takegoal


import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_group_holder.view.*


class ShowGroupViewholder(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer

 class ShowGroupAdapter(val groupList:ArrayList<GroupInform>,var itemClick:(GroupInform)->Unit) :RecyclerView.Adapter<ShowGroupViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowGroupViewholder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.user_group_holder,parent,false)
        return ShowGroupViewholder(view)
    }
        override fun getItemCount(): Int {
        return groupList.size
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ShowGroupViewholder, position: Int) {
        holder.containerView.holderName.text="그룹 이름: ${groupList[position].groupname}"
        holder.containerView.holderManager.text="그룹장:  ${groupList[position].groupmanager}"
        if(groupList[position].groupPicture!=null){
        Picasso.get().load(groupList[position].groupPicture).placeholder(R.drawable.timer).error(R.drawable.defalut_photo).into(holder.containerView.holder_Picture)
        }
        else{
            holder.containerView.holder_Picture.setImageResource(R.drawable.defalut_photo)
        }
       holder.containerView.setOnClickListener {
        }
        holder.containerView.relative.setOnClickListener{
            itemClick(groupList[position])
        }
    }

}