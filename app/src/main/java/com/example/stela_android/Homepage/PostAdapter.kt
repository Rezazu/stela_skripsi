package com.example.stela_android.Homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stela_android.R

//class PostAdapter (private val list: ArrayList<PostResponse>): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
//    inner class PostViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
//        fun bind(postResponse: PostResponse){
//            with(itemView){
//                val text = "no_tiket: ${postResponse.id}\n" +
//                        "title: ${postResponse.title}\n"
//
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
//        return PostViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
//        holder.bind(list[position])
//    }
//
//    override fun getItemCount(): Int=list.size
//}