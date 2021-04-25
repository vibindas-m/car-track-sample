package com.example.car.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.car.R
import com.example.car.ui.model.UserDataModel
import com.google.android.material.card.MaterialCardView

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.BookListHolder>() {

    lateinit var listener: OnItemClickListener
    private lateinit var items: List<UserDataModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return BookListHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BookListHolder, position: Int) {
        val userDataModel = items[position]

        holder.titleText.text = userDataModel.userName
        holder.authorText.text = userDataModel.mobile
        holder.cardView.setOnClickListener {
            listener.onClick(it, userDataModel)
        }
    }

    fun setData(list: List<UserDataModel>) {
        this.items = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: UserDataModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class BookListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.userName)
        val authorText: TextView = itemView.findViewById(R.id.userMobile)
        val cardView: MaterialCardView = itemView.findViewById(R.id.userListCard)
    }
}




