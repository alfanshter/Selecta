/*
package com.alfanshter.jatimpark.adapter

import android.R
import android.content.Context
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mView: View
    //set details to recycler view row
    fun setDetails(
        ctx: Context?,
        title: String?,
        description: String?,
        image: String?
    ) { //Views
        val mTitleTv: TextView = mView.findViewById(R.id.rTitleTv)
        val mDetailTv: TextView = mView.findViewById(R.id.rDescriptionTv)
        val mImageIv: ImageView = mView.findViewById(R.id.rImageView)
        //set data to views
        mTitleTv.text = title
        mDetailTv.text = description
        Picasso.get().load(image).into(mImageIv)
    }

    private var mClickListener: ClickListener? = null

    //interface to send callbacks
    interface ClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }

    fun setOnClickListener(clickListener: ClickListener?) {
        mClickListener = clickListener
    }

    init {
        mView = itemView
        //item click
        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }

        })
        //item long click
        itemView.setOnLongClickListener(OnLongClickListener { view ->
            mClickListener!!.onItemLongClick(view, adapterPosition)
            true
        })
    }
}
*/
