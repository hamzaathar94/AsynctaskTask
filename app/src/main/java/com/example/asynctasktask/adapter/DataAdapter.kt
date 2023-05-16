package com.example.asynctasktask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asynctasktask.databinding.ListItemBinding
import com.example.asynctasktask.model.DataModelItem


class DataAdapter(val std: ArrayList<DataModelItem>)
    :RecyclerView.Adapter<DataAdapter.MyViewHolder>(){
        class MyViewHolder(var binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return std.size
    }
    fun getSize(size:Long) {
        if (size<=0){
            return
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data=std[position]
        holder.binding.txttitle.text=data.title.toString()
        holder.binding.txtpath.text=data.path.toString()
       // Glide.with(parent.context).load(data.path).into(holder.binding.imageView)
    }

}