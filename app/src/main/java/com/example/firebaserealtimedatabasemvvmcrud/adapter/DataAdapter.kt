package com.example.firebaserealtimedatabasemvvmcrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedatabasemvvmcrud.MainActivity
import com.example.firebaserealtimedatabasemvvmcrud.R
import com.example.firebaserealtimedatabasemvvmcrud.model.Data

class DataAdapter(private var data:List<Data>, private val itemClickListener: MainActivity) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    interface ItemClickListener {
        fun onEditItemClick(data: Data)
        fun onDeleteItemClick(data: Data)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studid= itemView.findViewById<TextView>(R.id.idTV)
        val name = itemView.findViewById<TextView>(R.id.nameTv)
        val email = itemView.findViewById<TextView>(R.id.emailTV)
        val subject = itemView.findViewById<TextView>(R.id.subjectTV)
        val subjectcode = itemView.findViewById<TextView>(R.id.codeTV)

        val editBtn = itemView.findViewById<ImageButton>(R.id.editBtn)
        val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteBtn)
    }
    fun updateData(newData: List<Data>) {

        this.data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {

        val item = data[position]
        holder.studid.text = item.studid
        holder.name.text = item.name
        holder.email.text = item.email
        holder.subject.text = item.subject
        holder.subjectcode.text = item.code
        holder.editBtn.setOnClickListener {

            itemClickListener.onEditItemClick(item)
        }
        holder.deleteBtn.setOnClickListener {

            itemClickListener.onDeleteItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return  data.size
    }


}