package com.example.news

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class newsAdapter(private val listener: newsOnClick ): RecyclerView.Adapter<newsView>() {

    private val item: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        val viewHolder = newsView(view)
        view.setOnClickListener {
        listener.onItemClick(item[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: newsView, position: Int) {
        val current = item[position]
        holder.title.text = current.title
        holder.author.text = current.author
        Glide.with(holder.itemView.context).load(current.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun newsUpdate(updatedNews: ArrayList<News>){
        item.clear()
        item.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class newsView (itemView: View): RecyclerView.ViewHolder(itemView) {

    val title : TextView = itemView.findViewById(R.id.title)
    val author : TextView = itemView.findViewById(R.id.author)
    val image : ImageView = itemView.findViewById(R.id.imageView)

}

interface newsOnClick {
    fun onItemClick(item: News)
}