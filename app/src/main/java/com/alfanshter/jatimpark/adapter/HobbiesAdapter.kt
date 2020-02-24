package com.alfanshter.jatimpark.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.alfanshter.jatimpark.Model.ModelRecycler
import com.alfanshter.jatimpark.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*


class HobbiesAdapter(val context: Context, val hobbies : List<ModelRecycler>): RecyclerView.Adapter<HobbiesAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent , false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hobbies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hobby = hobbies[position]
        holder.setData(hobby , position)
    }

    inner class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var currentHobby : ModelRecycler? = null
        var CurrentPosition : Int = 0

        init {
            //ketika item di klik
            itemView.setOnClickListener {
                Toast.makeText(context, currentHobby!!.title + "Click !" , Toast.LENGTH_SHORT).show()
            }

            //share data
/*
            itemView.imgShare.setOnClickListener {
                val message : String = "My hobby is " + currentHobby!!.title
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.type = "text/plan"
                context.startActivity(Intent.createChooser(intent, "please select app : "))
            }
*/
        }

        fun setData(hobby: ModelRecycler? , pos : Int){
            itemView.txvTitle.text = hobby!!.title
            itemView.txvdesc.text = hobby!!.diskrpsi
            itemView.gambar.setImageResource(android.R.drawable.btn_star)
            this.currentHobby = hobby
            this.CurrentPosition = pos
        }
    }

}