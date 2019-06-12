package com.example.annonces_immobillieres

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(private val list: List<Announce>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val announce: Announce = list[position]
        holder.bind(announce)
    }

    override fun getItemCount(): Int = list.size

}


class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.announce_item, parent, false)) {
//    private var mTitleView: TextView? = null
//    private var mYearView: TextView? = null

    private var item_type: TextView? = null
    private var item_wilaya: TextView? = null
    private var item_description: TextView? = null
    private var item_date: TextView? = null
    private var item_image: ImageView


    init {
//        mTitleView = itemView.findViewById(R.id.list_title)
//        mYearView = itemView.findViewById(R.id.list_description)

        item_type = itemView.findViewById(R.id.item_type)
        item_wilaya = itemView.findViewById(R.id.item_wilaya)
        item_description = itemView.findViewById(R.id.item_description)
        item_date = itemView.findViewById(R.id.item_date)
        item_image = itemView.findViewById(R.id.item_image)
    }

    fun bind(announce: Announce) {
        item_type?.text = announce.type
        item_wilaya?.text = announce.wilaya
        item_description?.text = announce.description
        item_date?.text = announce.date
        item_image.setImageResource(announce.photo)
    }

}
