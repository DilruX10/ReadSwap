package com.example.readswap.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readswap.R
import com.example.readswap.models.BookModel

class BookAdapter(private var bookList: ArrayList<BookModel>) :

    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBook = bookList[position]
        holder.tvbookName.text = currentBook.bookName
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setFilteredList(mList: ArrayList<BookModel>){
        this.bookList = mList;
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvbookName : TextView = itemView.findViewById(R.id.tvbookName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}