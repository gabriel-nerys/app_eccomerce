package com.example.projeto_integrador_kt.Activitys.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador_kt.R
import com.example.projeto_integrador_kt.databinding.ViewholderSizeBinding

class SizeAdapter(val items: ArrayList<String>):
    RecyclerView.Adapter<SizeAdapter.Viewholder>() {

        private var selectedPosition= -1
        private var lastSelectedPosition=-1
        private lateinit var context: Context

    inner class Viewholder(val binding:ViewholderSizeBinding):
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: SizeAdapter.Viewholder, position: Int) {

        holder.binding.sizeTxt.text = items[position].toString()

        holder.binding.root.setOnClickListener{
            lastSelectedPosition=selectedPosition
            selectedPosition=position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if (selectedPosition==position){
            holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg_selected)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.purple))

        }else{
            holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = items.size
}